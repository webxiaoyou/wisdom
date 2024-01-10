package com.wisdom.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wisdom.common.domain.entity.SysRole;

import java.util.List;

/**
 * <p>
 * 角色信息表 Mapper 接口
 * </p>
 *
 * @author wisdom
 * @since 2023-09-21
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<String> selectRoleKeyByUserId(Long id);

    List<Long> selectMenuListByRoleId(Long roleId);
}
