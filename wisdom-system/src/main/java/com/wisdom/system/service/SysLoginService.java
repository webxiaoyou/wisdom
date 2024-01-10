package com.wisdom.system.service;


import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wisdom.common.constants.CacheConstants;
import com.wisdom.common.constants.Constants;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.common.domain.dto.RoleDTO;
import com.wisdom.common.domain.entity.SysUser;
import com.wisdom.common.domain.event.LogininforEvent;
import com.wisdom.common.domain.model.LoginUser;
import com.wisdom.common.enums.AppHttpCodeEnum;
import com.wisdom.common.enums.DeviceType;
import com.wisdom.common.enums.LoginType;
import com.wisdom.common.enums.UserStatus;
import com.wisdom.common.exception.SystemException;
import com.wisdom.common.helper.LoginHelper;
import com.wisdom.common.utils.DateUtils;
import com.wisdom.common.utils.ServletUtils;
import com.wisdom.common.utils.redis.RedisUtils;
import com.wisdom.common.utils.spring.SpringUtils;
import com.wisdom.system.mapper.SysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Slf4j
@Service
public class SysLoginService {

    @Autowired
    private SysUserMapper userMapper;

    /**
     * 与application连接的内容
     */
    @Value("${user.password.maxRetryCount}")
    private Integer maxRetryCount;

    /**
     * 与application连接的内容
     */
    @Value("${user.password.lockTime}")
    private Integer lockTime;

    @Autowired
    private SysPermissionService permissionService;

    public ResponseResult login(String account, String password, String code, String uuid) {
        HttpServletRequest request = ServletUtils.getRequest();
        SysUser user = loadUserByUsername(account);
        checkLogin(LoginType.PASSWORD, account, () -> !BCrypt.checkpw(password, user.getPassword()));
        // 此处可根据登录用户的数据不同 自行创建 loginUser
        LoginUser loginUser = buildLoginUser(user);
        // 生成token
        LoginHelper.loginByDevice(loginUser, DeviceType.PC);
        recordLogininfor(account, Constants.LOGIN_SUCCESS, String.format("登录成功"));
        recordLoginInfo(user.getId(), account);
        Map<String, Object> map = new HashMap<>();
        map.put(Constants.TOKEN, StpUtil.getTokenValue());
        return ResponseResult.okResult(map);
    }

    /**
     * 登录校验
     */
    private void checkLogin(LoginType loginType, String account, Supplier<Boolean> supplier) {
        String errorKey = CacheConstants.PWD_ERR_CNT_KEY + account;
        String loginFail = Constants.LOGIN_FAIL;

        // 获取用户登录错误次数(可自定义限制策略 例如: key + username + ip)
        int errorNumber = ObjectUtil.defaultIfNull(RedisUtils.getCacheObject(errorKey), 0);
        // 锁定时间内登录 则踢出
        if (errorNumber >= maxRetryCount) {
            recordLogininfor(account, loginFail, "密码重试次数超过限制，最大重试次数为 " + maxRetryCount + "，锁定时间为 " + lockTime + " 分钟。");
            throw new SystemException(500, "密码重试次数超过限制，最大重试次数为 " + maxRetryCount + "，锁定时间为 " + lockTime + " 分钟。");
        }

        if (supplier.get()) {
            // 是否第一次
            errorNumber++;
            RedisUtils.setCacheObject(errorKey, errorNumber, Duration.ofMinutes(lockTime));
            // 达到规定错误次数 则锁定登录
            if (errorNumber >= maxRetryCount) {
                recordLogininfor(account, loginFail, "密码重试次数超过限制，最大重试次数为 " + maxRetryCount + "，锁定时间为 " + lockTime + " 分钟。");
                throw new SystemException(500, "密码重试次数超过限制，最大重试次数为 " + maxRetryCount + "，锁定时间为 " + lockTime + " 分钟。");
            } else {
                // 未达到规定错误次数 则递增
                recordLogininfor(account, loginFail, "密码输入错误 " + errorNumber + " 次");
                throw new SystemException(500, "密码输入错误 " + errorNumber + " 次");
            }
        }

        // 登录成功 清空错误次数
        RedisUtils.deleteObject(errorKey);
    }

    /**
     * 查询用户状态
     *
     * @param account
     * @return
     */
    private SysUser loadUserByUsername(String account) {
        SysUser user = userMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                .select(SysUser::getAccount, SysUser::getStatus)
                .eq(SysUser::getAccount, account));
        if (ObjectUtil.isNull(user)) {
            log.info("登录用户：{} 不存在.", account);
            throw new SystemException("用户不存在");
        } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", account);
            throw new SystemException(AppHttpCodeEnum.LOGIN_STATUS);
        }
        return userMapper.selectUserByUserName(account);
    }

    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息内容
     * @return
     */
    private void recordLogininfor(String username, String status, String message) {
        LogininforEvent logininforEvent = new LogininforEvent();
        logininforEvent.setUsername(username);
        logininforEvent.setStatus(status);
        logininforEvent.setMessage(message);
        logininforEvent.setRequest(ServletUtils.getRequest());
        SpringUtils.context().publishEvent(logininforEvent);
    }

    /**
     * 构建登录用户
     */
    private LoginUser buildLoginUser(SysUser user) {
        LoginUser loginUser = new LoginUser();
        loginUser.setId(user.getId());
        loginUser.setDeptId(user.getDeptId());
        loginUser.setAccount(user.getAccount());
        loginUser.setUserType(user.getUserType());
        loginUser.setName(user.getName());
        loginUser.setMenuPermission(permissionService.selectPermsByUserId(user));
        loginUser.setRolePermission(permissionService.selectRoleKeyByUserId(user));
//        loginUser.setDeptName(ObjectUtil.isNull(user.getDept()) ? "" : user.getDept().getDeptName());
        List<RoleDTO> roles = BeanUtil.copyToList(user.getRoleIds(), RoleDTO.class);
        loginUser.setRoles(roles);
        return loginUser;
    }

    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(Long userId, String account) {
        SysUser sysUser = new SysUser();
        sysUser.setId(userId);
        sysUser.setLoginIp(ServletUtils.getClientIP());
        sysUser.setLoginDate(LocalDateTime.now());
        sysUser.setUpdateBy(account);
        userMapper.updateById(sysUser);
    }

    /**
     * 退出登录
     *
     * @return
     */

    public ResponseResult logout() {
        try {
            LoginUser loginUser = LoginHelper.getLoginUser();
            StpUtil.logout();
            recordLogininfor(loginUser.getAccount(), Constants.LOGOUT, "操作成功");
        } catch (NotLoginException ignored) {
        }
        return ResponseResult.okResult();
    }
}