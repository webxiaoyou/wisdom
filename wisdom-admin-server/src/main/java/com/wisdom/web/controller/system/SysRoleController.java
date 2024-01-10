package com.wisdom.web.controller.system;


import cn.dev33.satoken.annotation.SaCheckPermission;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.system.domain.dto.SysRoleDto;
import com.wisdom.system.domain.dto.SysRoleSaveDto;
import com.wisdom.system.service.ISysRoleMenuService;
import com.wisdom.system.service.ISysRoleService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色权限
 *
 * @author wisdom
 */
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/system/role")
public class SysRoleController {

    private final ISysRoleService sysRoleService;


    /**
     * 查询所有角色列表
     *
     * @param sysRoleDto 角色查询条件
     * @return 角色列表
     */
    @SaCheckPermission("system:role:list")
    @GetMapping("/list")
    public ResponseResult selectAll(SysRoleDto sysRoleDto) {
        return sysRoleService.selectAll(sysRoleDto);
    }

    /**
     * 查询所有角色字典列表
     *
     * @return 角色字典列表
     */
    @GetMapping("/dict/list")
    public ResponseResult selectAllDict() {
        return sysRoleService.selectAllDict();
    }

    /**
     * 根据角色ID查询角色信息
     *
     * @param postId 角色ID
     * @return 角色信息
     */
    @SaCheckPermission("system:role:query")
    @GetMapping(value = "/{postId}")
    public ResponseResult getInfo(@PathVariable Long postId) {
        return ResponseResult.okResult(sysRoleService.getById(postId));
    }

    /**
     * 新增角色
     *
     * @param sysRoleSaveDto 角色信息
     * @return 新增结果
     */
    @SaCheckPermission("system:role:add")
    @PostMapping("/save")
    public ResponseResult saveData(@RequestBody SysRoleSaveDto sysRoleSaveDto) {
        return sysRoleService.add(sysRoleSaveDto);
    }

    /**
     * 修改角色
     *
     * @param sysRoleSaveDto 角色信息
     * @return 修改结果
     */
    @SaCheckPermission("system:role:edit")
    @PutMapping("/update")
    public ResponseResult updateData(@RequestBody SysRoleSaveDto sysRoleSaveDto) {
        if (sysRoleSaveDto.getId() == 1L) {
            return ResponseResult.errorResult(403, "不允许修改管理员");
        }
        return sysRoleService.edit(sysRoleSaveDto);
    }

    /**
     * 删除角色
     *
     * @param idList 待删除角色ID列表
     * @return 删除结果
     */
    @SaCheckPermission("system:role:remove")
    @DeleteMapping("delete")
    public ResponseResult delete(@RequestParam("idList") List<Long> idList) {
        if (idList.contains(1L)) {
            return ResponseResult.errorResult(403, "不允许删除超级管理员");
        }
        return ResponseResult.okResult(this.sysRoleService.removeByIds(idList));
    }

}
