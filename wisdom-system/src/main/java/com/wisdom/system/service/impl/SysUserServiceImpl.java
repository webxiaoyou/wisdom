package com.wisdom.system.service.impl;

import cn.dev33.satoken.secure.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.common.domain.entity.SysUser;
import com.wisdom.common.domain.vo.PageVo;
import com.wisdom.common.enums.AppHttpCodeEnum;
import com.wisdom.common.utils.BeanCopyUtils;
import com.wisdom.common.utils.StringUtils;
import com.wisdom.system.domain.dto.SysUserDto;
import com.wisdom.system.domain.entity.SysUserRole;
import com.wisdom.system.domain.vo.SysUserInfoVo;
import com.wisdom.system.mapper.SysUserMapper;
import com.wisdom.system.service.ISysUserRoleService;
import com.wisdom.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author wisdom
 * @since 2023-09-19
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    private final  ISysUserRoleService sysUserRoleService;

    private final SysUserMapper sysUserMapper;

    @Override
    public ResponseResult selectAll(SysUser userInfoVo) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(userInfoVo.getStatus()), SysUser::getStatus, userInfoVo.getStatus());
        queryWrapper.eq(StringUtils.isNotEmpty(userInfoVo.getPosition()), SysUser::getPosition, userInfoVo.getPosition());
        queryWrapper.like(StringUtils.isNotEmpty(userInfoVo.getName()), SysUser::getName, userInfoVo.getName());
        queryWrapper.like(StringUtils.isNotEmpty(userInfoVo.getPhone()), SysUser::getPhone, userInfoVo.getPhone());
//        添加按起始时间和结束时间查询条件（仅在不为null时添加）
        if (userInfoVo.getParams() != null ) {
            queryWrapper.between(userInfoVo.getParams().get(0) != null && userInfoVo.getParams().get(1) != null,
                    SysUser::getCreateTime, userInfoVo.getParams().get(0), userInfoVo.getParams().get(1));
        }
        Page page = new Page(userInfoVo.getPageNum(), userInfoVo.getPageSize());
        page(page, queryWrapper);
        List list = BeanCopyUtils.copyBeanList(page.getRecords(), SysUserInfoVo.class);
        PageVo pageVo = new PageVo(list, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public List<SysUser> getNe(Long id, String account) {
        // 创建查询条件
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(id != null, SysUser::getId, id); // 排除当前修改的数据
        queryWrapper.and(qw -> qw.eq(SysUser::getAccount, account));
        return this.list(queryWrapper);
    }

    //    新增
    @Override
    public ResponseResult add(SysUserDto SysUserDto) {
        // 首先检查是否已存在相同名称或编码的字典
        List<SysUser> existingDict = getNe(SysUserDto.getId(), SysUserDto.getAccount());

        if (!existingDict.isEmpty()) {
            // 如果已存在相同名称或编码的字典，返回操作失败的响应
            return ResponseResult.errorResult(504, "账号已存在");
        }

        SysUser user = BeanCopyUtils.copyBean(SysUserDto, SysUser.class);

        // 在新增用户时，生成密码并保存到数据库
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String newPassword = passwordEncoder.encode(user.getPassword());
//        user.setPassword(newPassword);
        user.setPassword(BCrypt.hashpw(user.getPassword()));

        boolean success = save(user);
        if (SysUserDto.getRoleIds() != null || !SysUserDto.getRoleIds().isEmpty()) {
            // 遍历 roleIds 数组，为每个菜单创建权限并关联到角色
            for (Integer roleId : SysUserDto.getRoleIds()) {
                SysUserRole userRole = new SysUserRole();
                userRole.setUserId(user.getId());
                userRole.setRoleId(Long.valueOf(roleId));

                // 插入权限信息到权限表 (sys_role_menu)
                sysUserRoleService.save(userRole);
            }
        }
        if (success) {
            return ResponseResult.okResult(AppHttpCodeEnum.ADD_SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.ADD_ERROR);
        }
    }

    @Override
    public ResponseResult edit(SysUserDto SysUserDto) {
        // 首先检查是否已存在相同名称或编码的字典
        List<SysUser> existingDict = getNe(SysUserDto.getId(), SysUserDto.getAccount());

        if (!existingDict.isEmpty()) {
            // 如果已存在相同名称或编码的字典，返回操作失败的响应
            return ResponseResult.errorResult(504, "账号已存在");
        }
        // 首先查询数据库中原始的数据
        SysUser originalUser = getById(SysUserDto.getId());

        if (originalUser == null) {
            // 如果原始数据不存在，返回操作失败的响应
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_NULL);
        }
        SysUser user = BeanCopyUtils.copyBean(SysUserDto, SysUser.class);
        // 检查是否传递了密码字段，如果传递了，则将密码字段置空，以保持原密码
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(originalUser.getPassword());
        }
        // 执行修改操作
        boolean success = updateById(user);
// 如果 roleIds 为 null，表示没有传递，则执行默认逻辑
        if (SysUserDto.getRoleIds() != null && !SysUserDto.getRoleIds().isEmpty()) {
            // 删除该角色之前关联的所有菜单权限
            sysUserRoleService.removeByRoleId(SysUserDto.getId());
            // 遍历 roleIds 数组，为每个菜单创建权限并关联到角色
            for (Integer roleId : SysUserDto.getRoleIds()) {
                SysUserRole userRole = new SysUserRole();
                userRole.setUserId(SysUserDto.getId());
                userRole.setRoleId(Long.valueOf(roleId));

                // 插入权限信息到权限表 (sys_role_menu)
                sysUserRoleService.save(userRole);
            }
        }
        if (success) {
            return ResponseResult.okResult(AppHttpCodeEnum.UP_SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.UP_ERROR);
        }
    }

    @Override
    public boolean updateUserAvatar(String name, String avatar) {
        return baseMapper.update(null,
                new LambdaUpdateWrapper<SysUser>()
                        .set(SysUser::getAvatar, avatar)
                        .eq(SysUser::getAccount, name)) > 0;
    }

    @Override
    public SysUser selectUserById(Long id) {
        return sysUserMapper.selectUserById(id);
    }


}
