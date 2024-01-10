package com.wisdom.system.service;

import com.wisdom.system.domain.entity.SysRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色和菜单关联表 服务类
 * </p>
 *
 * @author wisdom
 * @since 2023-09-21
 */
public interface ISysRoleMenuService extends IService<SysRoleMenu> {

    void removeByRoleId(Long id);

    List<Long> selectMenuListByRoleId(Long roleId);
}
