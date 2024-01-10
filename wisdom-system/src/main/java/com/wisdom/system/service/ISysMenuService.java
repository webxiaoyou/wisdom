package com.wisdom.system.service;

import com.wisdom.common.domain.entity.SysMenu;
import com.wisdom.system.domain.dto.SysMenuDto;
import com.wisdom.system.domain.vo.SysMenuDictVo;
import com.wisdom.common.domain.ResponseResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author wisdom
 * @since 2023-09-21
 */
public interface ISysMenuService extends IService<SysMenu> {

//    List<String> selectPermsByUserId(Long id);

    List<SysMenu> selectRouterMenuTreeByUserId(Long userId);

    ResponseResult selectAll(SysMenuDto menuDto);

//    List<SysMenu> getNe(Long id, String menuName, String perms);

    List<SysMenuDictVo> buildMenuTreeSelect(List<SysMenu> menus, long parentId);

    boolean hasChildByMenuId(List<Long> idList);

    ResponseResult add(SysMenu sysMenu);

    ResponseResult edit(SysMenu sysMenu);
}
