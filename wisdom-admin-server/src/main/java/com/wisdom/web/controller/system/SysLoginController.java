package com.wisdom.web.controller.system;

import cn.dev33.satoken.annotation.SaIgnore;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wisdom.common.domain.ResponseResult;

import com.wisdom.common.domain.dto.PRepairDTO;
import com.wisdom.common.domain.entity.*;
import com.wisdom.common.domain.model.LoginBody;
import com.wisdom.common.domain.model.LoginUser;
import com.wisdom.common.enums.AppHttpCodeEnum;
import com.wisdom.common.exception.SystemException;
import com.wisdom.common.helper.LoginHelper;
import com.wisdom.common.service.PComplaintSuggestionService;
import com.wisdom.common.service.PPropertyUnitService;
import com.wisdom.common.service.PRepairService;
import com.wisdom.common.utils.BeanCopyUtils;
import com.wisdom.system.domain.entity.SysUserRole;
import com.wisdom.system.domain.vo.AdminUserInfoVo;
import com.wisdom.system.domain.vo.RoutersVo;
import com.wisdom.system.domain.vo.SysUserInfoVo;
import com.wisdom.system.service.*;

import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 登录验证
 *
 * @author wisdom
 */
@Validated
@RequiredArgsConstructor
@RestController
public class SysLoginController {

    private final SysLoginService loginService;

    private final ISysMenuService sysMenuService;

    private final ISysUserService userService;

    private final ISysUserRoleService sysUserRoleService;

    private final PRepairService pRepairService;

    private final PPropertyUnitService pPropertyUnitService;

    private final PComplaintSuggestionService pComplaintSuggestionService;

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @SaIgnore
    @PostMapping("/login")
    public ResponseResult login(@Validated @RequestBody LoginBody loginBody){
        if(!StringUtils.hasText(loginBody.getAccount())){
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
//        //将明文密码进行加密
//        String hashpw = hashpw(loginBody.getPassword());
////将明文密码与加密后的密码进行比较
//        boolean checkpw = BCrypt.checkpw(loginBody.getPassword(), hashpw);
        return loginService.login(loginBody.getAccount(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
    }


    /**
     * 获取当前登录用户的详细信息
     *
     * @return 当前登录用户的详细信息
     */
    @GetMapping("/getInfo")
    public ResponseResult getInfo() {
        // 获取当前登录的用户
        LoginUser loginUser = LoginHelper.getLoginUser();
        // 获取用户信息
        SysUser user = userService.selectUserById(loginUser.getId());
        SysUserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, SysUserInfoVo.class);
        List<SysUserRole> userRoles = sysUserRoleService.getByUserId(loginUser.getId());
        List<Long> roleIds = userRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
        userInfoVo.setRoleIds(roleIds);
        // 封装数据返回
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(loginUser.getMenuPermission(), loginUser.getRolePermission(), userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }

    /**
     * 获取用户路由信息
     *
     * @return 用户路由信息
     */
    @GetMapping("/getRouters")
    public ResponseResult<RoutersVo> getRouters() {
        // 获取当前登录用户的ID
        Long userId = LoginHelper.getUserId();
        // 查询用户可访问的菜单作为路由信息
        List<SysMenu> menus = sysMenuService.selectRouterMenuTreeByUserId(userId);
        return ResponseResult.okResult(new RoutersVo(menus));
    }

    /**
     * 用户登出
     *
     * @return 登出结果
     */
    @PostMapping("/logout")
    public ResponseResult logout() {
        return loginService.logout();
    }

    /**
     * 控制台内容
     *
     * @return 用户路由信息
     */
    @GetMapping("/getIndex")
    public ResponseResult getindex() {
        // 获取当前登录用户的ID
        Long userId = LoginHelper.getUserId();
        LoginUser loginUser = LoginHelper.getLoginUser();
        // 要检测是否包含 ['admin', 'text']
        //业主信息
        List<PPropertyUnit> propertyUnit =new ArrayList<>();
        //获取报修信息
        List<String> rolesToCheck = Arrays.asList("superAdmin","admin");
        LambdaQueryWrapper<PRepair> queryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<PComplaintSuggestion> querySuggestionWrapper = new LambdaQueryWrapper<>();
        querySuggestionWrapper.eq( PComplaintSuggestion::getStatus, "1");
        if (loginUser.getRolePermission().stream().anyMatch(rolesToCheck::contains)){
            queryWrapper.eq(loginUser.getDeptId()!=null, PRepair::getPropertyId, loginUser.getDeptId());
            queryWrapper.ne(PRepair::getStatus, "2");
            LambdaQueryWrapper<PPropertyUnit> queryUnitWrapper = new LambdaQueryWrapper<>();
            queryUnitWrapper.eq(PPropertyUnit::getAuthenticationStatus,"1");
            queryUnitWrapper.eq(PPropertyUnit::getDelFlag,"0");
            propertyUnit = pPropertyUnitService.list(queryUnitWrapper);
        }else{
            // 根据实际需求添加查询条件
            queryWrapper.eq(loginUser.getDeptId()!=null, PRepair::getPropertyId, loginUser.getDeptId());
            queryWrapper.eq( PRepair::getStatus, "1");
            queryWrapper.eq(loginUser.getId()!=null, PRepair::getHandlerId, loginUser.getId());
            querySuggestionWrapper.eq(loginUser.getDeptId()!=null, PComplaintSuggestion::getPropertyId, loginUser.getDeptId());

        }
        List<PRepair> PRepairList = pRepairService.list(queryWrapper);
        List<PComplaintSuggestion> suggestionList = pComplaintSuggestionService.list(querySuggestionWrapper);
        Map<Object, Object> map = new HashMap<>();
        if (PRepairList != null && !PRepairList.isEmpty()) {
            map.put("repair", PRepairList);
        }

        if (propertyUnit != null && !propertyUnit.isEmpty()) {
            map.put("propertyUnit", propertyUnit);
        }

        if (suggestionList != null && !suggestionList.isEmpty()) {
            map.put("suggestion", suggestionList);
        }
        return ResponseResult.okResult(map);
    }
}