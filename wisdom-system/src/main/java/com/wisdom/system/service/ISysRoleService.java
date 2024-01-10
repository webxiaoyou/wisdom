package com.wisdom.system.service;

import com.wisdom.common.domain.ResponseResult;
import com.wisdom.common.domain.entity.SysRole;
import com.wisdom.system.domain.dto.SysRoleDto;
import com.wisdom.system.domain.dto.SysRoleSaveDto;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色信息表 服务类
 * </p>
 *
 * @author wisdom
 * @since 2023-09-21
 */
public interface ISysRoleService extends IService<SysRole> {

//    List<String> selectRoleKeyByUserId(Long id);

    ResponseResult selectAll(SysRoleDto sysRoleDto);

    List<SysRole> getNe(Long id, String roleName, String roleKey);

    ResponseResult add(SysRoleSaveDto sysRoleSaveDto);

    ResponseResult edit(SysRoleSaveDto sysRoleSaveDto);

    ResponseResult selectAllDict();
}
