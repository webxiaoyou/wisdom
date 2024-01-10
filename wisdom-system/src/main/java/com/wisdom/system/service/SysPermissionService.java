package com.wisdom.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wisdom.common.constants.UserConstants;
import com.wisdom.common.domain.entity.SysMenu;
import com.wisdom.common.domain.entity.SysUser;
import com.wisdom.system.mapper.SysMenuMapper;
import com.wisdom.system.mapper.SysRoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户权限处理
 *
 * @author ruoyi
 */
@RequiredArgsConstructor
@Service
public class SysPermissionService {

    private final SysMenuMapper sysMenuMapper;
    private final SysRoleMapper sysRoleMapper;
    /**
     * 获取角色数据权限
     *
     * @param user 用户信息
     * @return 角色权限信息
     */
    public List<String> selectPermsByUserId(SysUser user) {
        if (user.isAdmin()) {
            // 如果用户是管理员，返回所有权限
            return listAdminPerms();
        }
        //否则返回所具有的权限
        return sysMenuMapper.selectPermsByUserId(user.getId() );
    }

    /**
     * 获取菜单数据权限
     *
     * @param user 用户信息
     * @return 菜单权限信息
     */
    public List<String> selectRoleKeyByUserId(SysUser user) {
        //判断是否是管理员 如果是返回集合中只需要有admin
        if (user.isAdmin()) {
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }else{

        }
        //否则查询用户所具有的角色信息
        return sysRoleMapper.selectRoleKeyByUserId(user.getId());
    }

    /**
     * 验证是否是管理员
     *
     * @return
     */
    private List<String> listAdminPerms() {
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SysMenu::getMenuType, UserConstants.TYPE_MENU, UserConstants.TYPE_BUTTON)
                .eq(SysMenu::getStatus, UserConstants.NORMAL);
        List<SysMenu> menus = sysMenuMapper.selectList(wrapper);;
        return menus.stream().map(SysMenu::getPerms).collect(Collectors.toList());
    }
}
