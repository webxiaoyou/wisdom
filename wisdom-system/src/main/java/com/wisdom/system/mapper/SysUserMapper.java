package com.wisdom.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wisdom.common.domain.entity.SysUser;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author wisdom
 * @since 2023-09-19
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser selectUserByUserName(String account);


    SysUser selectUserById(Long id);
}
