package com.wisdom.web.controller.system;

import cn.dev33.satoken.annotation.SaIgnore;
import com.wisdom.common.domain.ResponseResult;

import com.wisdom.common.domain.entity.SysMenu;
import com.wisdom.common.domain.entity.SysUser;
import com.wisdom.common.domain.model.LoginBody;
import com.wisdom.common.domain.model.LoginUser;
import com.wisdom.common.enums.AppHttpCodeEnum;
import com.wisdom.common.exception.SystemException;
import com.wisdom.common.helper.LoginHelper;
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

import java.util.List;
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


}