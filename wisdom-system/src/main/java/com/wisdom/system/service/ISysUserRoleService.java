package com.wisdom.system.service;

import com.wisdom.system.domain.entity.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户和角色关联表 服务类
 * </p>
 *
 * @author wisdom
 * @since 2023-09-26
 */
public interface ISysUserRoleService extends IService<SysUserRole> {

    void removeByRoleId(Long id);

    List<SysUserRole> getByUserId(Long postId);
}
