package com.wisdom.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wisdom.system.mapper.SysMenuMapper;
import com.wisdom.system.domain.entity.SysRoleMenu;
import com.wisdom.system.mapper.SysRoleMapper;
import com.wisdom.system.mapper.SysRoleMenuMapper;
import com.wisdom.system.service.ISysRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色和菜单关联表 服务实现类
 * </p>
 *
 * @author wisdom
 * @since 2023-09-21
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {

    @Autowired
    SysRoleMapper sysRoleMapper;

    @Autowired
    SysMenuMapper sysMenuMapper;

    @Override
    public void removeByRoleId(Long id) {
        // 构建删除条件，删除角色ID为指定角色ID的所有菜单权限记录
        QueryWrapper<SysRoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", id);

        // 调用 remove 方法执行删除操作
         this.remove(queryWrapper);
    }

    @Override
    public List<Long> selectMenuListByRoleId(Long roleId) {
        List<Long> list = null;
        if (roleId == 1L) {
            //如果是 获取所有符合要求的Meny菜单
            list = sysMenuMapper.selectAllRoleMenu(1L);
        }else{
            list = sysRoleMapper.selectMenuListByRoleId(roleId);
        }
        return list;
    }
}
