package com.wisdom.system.service;

import com.wisdom.common.domain.entity.SysUser;
import com.wisdom.system.domain.dto.SysUserDto;
import com.wisdom.common.domain.ResponseResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author wisdom
 * @since 2023-09-19
 */
public interface ISysUserService extends IService<SysUser> {

    ResponseResult selectAll(SysUser sysUser);


    List<SysUser> getNe(Long id, String account);

    ResponseResult add(SysUserDto SysUserDto);

    ResponseResult edit(SysUserDto SysUserDto);

    boolean updateUserAvatar(String name, String avatar);

    SysUser selectUserById(Long id);
}
