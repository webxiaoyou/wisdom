package com.wisdom.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wisdom.common.domain.entity.SysMenu;

import java.util.List;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author wisdom
 * @since 2023-09-21
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<String> selectPermsByUserId(Long id);

    List<SysMenu> selectAllRouterMenu();

    List<SysMenu> selectRouterMenuTreeByUserId(Long userId);

    List<Long> selectAllRoleMenu(Long i);
}
