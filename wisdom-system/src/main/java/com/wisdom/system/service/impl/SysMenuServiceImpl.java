package com.wisdom.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wisdom.common.constants.UserConstants;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.common.domain.entity.SysMenu;
import com.wisdom.common.enums.AppHttpCodeEnum;
import com.wisdom.common.exception.SystemException;
import com.wisdom.common.helper.LoginHelper;
import com.wisdom.system.domain.dto.SysMenuDto;
import com.wisdom.system.domain.vo.SysMenuDictVo;
import com.wisdom.system.mapper.SysMenuMapper;
import com.wisdom.system.service.ISysMenuService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author wisdom
 * @since 2023-09-21
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

//    @Override
//    public List<String> selectPermsByUserId(Long id) {
//        if (id == 1L) {
//            // 如果用户是管理员，返回所有权限
//            return listAdminPerms();
//        }
//            //否则返回所具有的权限
//        return getBaseMapper().selectPermsByUserId(id);
//    }

    @Override
    public List<SysMenu> selectRouterMenuTreeByUserId(Long userId) {
        SysMenuMapper menuMapper = getBaseMapper();
        List<SysMenu> menus;
        //验证是否是管理员
        if (LoginHelper.isAdmin()) {
            //如果是 获取所有符合要求的Meny菜单
            menus = menuMapper.selectAllRouterMenu();
        } else {
            //否则 获取当前用户所具有的Menu
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }
        // 构建tree
//        先找出第一层的菜单 然后再去找他们的子菜单并设置到childer属性中
        List<SysMenu> menusTree = builderMenuTree(menus, 0L);
        return menusTree;
    }

    @Override
    public ResponseResult selectAll(SysMenuDto menuDto) {
        List<SysMenu> list = new ArrayList<>();
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(menuDto.getMenuName()), SysMenu::getMenuName, menuDto.getMenuName());
        queryWrapper.eq(StringUtils.hasText(menuDto.getStatus()), SysMenu::getStatus, menuDto.getStatus());

        // 在查询时按照 order_num 进行升序排序
        queryWrapper.orderByAsc(SysMenu::getOrderNum);
        list = list(queryWrapper);

        if (!StringUtils.hasText(menuDto.getMenuName())) {
            list = builderMenuTree(list, 0L);
        }

        return ResponseResult.okResult(list);
    }


    public List<SysMenu> getNe(Long id, String menuName) {
        // 创建查询条件
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(id != null, SysMenu::getId, id).eq(SysMenu::getDelFlag, UserConstants
                .DEL_STATUS_NORMAL);
        queryWrapper.and(qw -> qw.eq(SysMenu::getMenuName, menuName));
        return this.list(queryWrapper);
    }


    /**
     * 通过传递id查询相关内容
     *
     * @param menus
     * @param parentId
     * @return
     */
    private List<SysMenu> builderMenuTree(List<SysMenu> menus, long parentId) {
        List<SysMenu> sysMenuList = menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> {
                    List<SysMenu> children = getChildren(menu, menus);
                    if (!children.isEmpty()) {
                        menu.setChildren(children);
                    }
                    return menu;
                })
                .collect(Collectors.toList());
        return sysMenuList;
    }

    /**
     * 获取存入参数 子Menu集合
     *
     * @param menu
     * @param menus
     * @return
     */
    private List<SysMenu> getChildren(SysMenu menu, List<SysMenu> menus) {
        List<SysMenu> menuList = menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m -> {
                    List<SysMenu> children = getChildren(m, menus);
                    if (!children.isEmpty()) {
                        m.setChildren(children);
                    }
                    return m;
                })
                .collect(Collectors.toList());
        return menuList;
    }


//    /**
//     * 验证是否是管理员
//     *
//     * @return
//     */
//    private List<String> listAdminPerms() {
//        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
//        wrapper.in(SysMenu::getMenuType, UserConstants.TYPE_MENU,UserConstants.TYPE_BUTTON)
//                .eq(SysMenu::getStatus, UserConstants.NORMAL);
//        List<SysMenu> menus = list(wrapper);
//        return menus.stream().map(SysMenu::getPerms).collect(Collectors.toList());
//    }


    /**
     * 通过传递id查询相关内容，返回 SysMenuDictVo 对象
     *
     * @param menus
     * @param parentId
     * @return
     */
    public List<SysMenuDictVo> buildMenuTreeSelect(List<SysMenu> menus, long parentId) {
        return menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> {
                    SysMenuDictVo menuDictVo = new SysMenuDictVo();
                    menuDictVo.setValue(menu.getId());
                    menuDictVo.setLabel(menu.getMenuName());

                    List<SysMenuDictVo> children = getbuildMenuChildren(menu, menus);
                    if (!children.isEmpty()) {
                        menuDictVo.setChildren(children);
                    }
                    return menuDictVo;
                })
                .collect(Collectors.toList());
    }

    @Override
    public boolean hasChildByMenuId(List<Long> idList) {
        // 构建 LambdaQueryWrapper 条件
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysMenu::getParentId, idList);
        // 调用 exists 方法检查是否存在满足条件的记录
        return baseMapper.exists(queryWrapper);
    }

    @Override
    public ResponseResult add(SysMenu sysMenu) {
        // 首先检查是否已存在相同名称或编码的字典
        List<SysMenu> existingDict = getNe(sysMenu.getId(), sysMenu.getMenuName());

        if (!existingDict.isEmpty()) {
            // 如果已存在相同名称或编码的字典，返回操作失败的响应
           throw  new SystemException(AppHttpCodeEnum.DATA_ERROR.getCode(),"失败菜单名称已存在");
        }
        // dictId为空，表示是新增操作
        // 执行新增操作
        boolean success = save(sysMenu);

        if (success) {
            return ResponseResult.okResult(AppHttpCodeEnum.ADD_SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.ADD_ERROR);
        }
    }

    @Override
    public ResponseResult edit(SysMenu sysMenu) {
        // 首先检查是否已存在相同名称或编码的字典
        List<SysMenu> existingDict = getNe(sysMenu.getId(), sysMenu.getMenuName());
        if (!existingDict.isEmpty()) {
            // 如果已存在相同名称或编码的字典，返回操作失败的响应
            throw  new SystemException(AppHttpCodeEnum.DATA_ERROR.getCode(),"菜单名称已存在");
        }
        if (sysMenu.getId().equals(sysMenu.getParentId())) {
            // 如果已存在相同名称或编码的字典，返回操作失败的响应
            throw  new SystemException(504, "上级菜单不能选择自己");
        }
        // dictId不为空，表示是修改操作
        // 首先查询数据库中原始的数据
        SysMenu originalPost = getById(sysMenu.getId());

        if (originalPost == null) {
            // 如果原始数据不存在，返回操作失败的响应
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_NULL);
        }
        // 执行修改操作
        boolean success = updateById(sysMenu);

        if (success) {
            return ResponseResult.okResult(AppHttpCodeEnum.UP_SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.UP_ERROR);
        }
    }


    /**
     * 获取存入参数 子 SysMenuDictVo 集合
     *
     * @param menu
     * @param menus
     * @return
     */
    private List<SysMenuDictVo> getbuildMenuChildren(SysMenu menu, List<SysMenu> menus) {
        return menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m -> {
                    SysMenuDictVo menuDictVo = new SysMenuDictVo();
                    menuDictVo.setValue(m.getId());
                    menuDictVo.setLabel(m.getMenuName());

                    List<SysMenuDictVo> children = getbuildMenuChildren(m, menus);
                    if (!children.isEmpty()) {
                        menuDictVo.setChildren(children);
                    }
                    return menuDictVo;
                })
                .collect(Collectors.toList());
    }


}
