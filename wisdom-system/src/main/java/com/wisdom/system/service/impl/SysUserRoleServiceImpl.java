package com.wisdom.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wisdom.system.service.ISysUserRoleService;
import com.wisdom.system.domain.entity.SysUserRole;
import com.wisdom.system.mapper.SysUserRoleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户和角色关联表 服务实现类
 * </p>
 *
 * @author wisdom
 * @since 2023-09-26
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

    @Override
    public void removeByRoleId(Long id) {
        // 构建删除条件，删除角色ID为指定角色ID的所有菜单权限记录
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", id);

        // 调用 remove 方法执行删除操作
        this.remove(queryWrapper);
    }

    @Override
    public List<SysUserRole> getByUserId(Long postId) {
        LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserRole::getUserId,postId);
        return list(queryWrapper);
    }
}
