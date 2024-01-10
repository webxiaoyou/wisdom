package com.wisdom.web.controller.system;


import cn.dev33.satoken.annotation.SaCheckPermission;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.common.domain.entity.SysMenu;
import com.wisdom.system.domain.dto.SysMenuDto;
import com.wisdom.system.mapper.SysMenuMapper;
import com.wisdom.system.service.ISysMenuService;
import com.wisdom.system.service.ISysRoleMenuService;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 菜单
 *
 * @author wisdom
 */
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/system/menu")
public class SysMenuController {


    private final ISysMenuService sysMenuService;

    private final SysMenuMapper sysMenuMapper;

    private final ISysRoleMenuService sysRoleMenuService;


    /**
     * 查询所有菜单列表
     *
     * @param menuDto 菜单查询条件
     * @return 菜单列表
     */
    @SaCheckPermission("system:menu:list")
    @GetMapping("/list")
    public ResponseResult selectAll(SysMenuDto menuDto) {
        return sysMenuService.selectAll(menuDto);
    }

    /**
     * 根据菜单ID查询菜单信息
     *
     * @param menuId 菜单ID
     * @return 菜单信息
     */
    @SaCheckPermission("system:menu:query")
    @GetMapping(value = "/{menuId}")
    public ResponseResult getInfo(@PathVariable Long menuId) {
        return ResponseResult.okResult(sysMenuService.getById(menuId));
    }

    /**
     * 新增菜单
     *
     * @param sysMenu 菜单信息
     * @return 新增结果
     */
    @SaCheckPermission("system:menu:add")
    @PostMapping("/save")
    public ResponseResult saveData(@RequestBody SysMenu sysMenu) {
        return sysMenuService.add(sysMenu);
    }

    /**
     * 修改菜单
     *
     * @param sysMenu 菜单信息
     * @return 修改结果
     */
    @SaCheckPermission("system:menu:edit")
    @PutMapping("/update")
    public ResponseResult updateData(@RequestBody SysMenu sysMenu) {
        return sysMenuService.edit(sysMenu);
    }

    /**
     * 删除菜单
     *
     * @param idList 待删除菜单ID列表
     * @return 删除结果
     */
    @SaCheckPermission("system:menu:remove")
    @DeleteMapping("delete")
    public ResponseResult delete(@RequestParam("idList") List<Long> idList) {
        if (sysMenuService.hasChildByMenuId(idList)) {
            return ResponseResult.errorResult(504, "存在子菜单,不允许删除");
        }
        return ResponseResult.okResult(this.sysMenuService.removeByIds(idList));
    }

    /**
     * 角色菜单树选择
     *
     * @param roleId 角色ID
     * @return 角色菜单树选择结果
     */
    @GetMapping(value = "/roleMenuTreeselect")
    public ResponseResult<Map<String, Object>> roleMenuTreeselect(@RequestParam(value = "roleId", required = false) Long roleId) {
        Map<String, Object> ajax = new HashMap<>();
        ajax.put("checkedKeys", roleId != null ? sysRoleMenuService.selectMenuListByRoleId(roleId) : new ArrayList<>());
        ajax.put("menus", sysMenuService.buildMenuTreeSelect(sysMenuMapper.selectList(null), 0));
        return ResponseResult.okResult(ajax);
    }


}
