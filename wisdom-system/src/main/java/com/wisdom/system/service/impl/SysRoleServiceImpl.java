package com.wisdom.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wisdom.common.domain.entity.SysRole;
import com.wisdom.system.domain.vo.SysRoleDictVo;
import com.wisdom.system.service.ISysRoleMenuService;
import com.wisdom.system.service.ISysRoleService;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.system.domain.dto.SysRoleDto;
import com.wisdom.system.domain.dto.SysRoleSaveDto;
import com.wisdom.system.domain.entity.SysRoleMenu;
import com.wisdom.common.domain.vo.PageVo;
import com.wisdom.common.enums.AppHttpCodeEnum;
import com.wisdom.system.mapper.SysRoleMapper;
import com.wisdom.common.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author wisdom
 * @since 2023-09-21
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {
    @Autowired
    private ISysRoleMenuService sysRoleMenuService;

//    @Override
//    public List<String> selectRoleKeyByUserId(Long id) {
//        //判断是否是管理员 如果是返回集合中只需要有admin
//        if (id == 1L) {
//            List<String> roleKeys = new ArrayList<>();
//            roleKeys.add("admin");
//            return roleKeys;
//        }
//        //否则查询用户所具有的角色信息
//        return getBaseMapper().selectRoleKeyByUserId(id);
//    }

    @Override
    public ResponseResult selectAll(SysRoleDto sysRoleDto) {
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(sysRoleDto.getStatus()), SysRole::getStatus, sysRoleDto.getStatus());
        queryWrapper.like(StringUtils.hasText(sysRoleDto.getRoleName()), SysRole::getRoleName, sysRoleDto.getRoleName());
        queryWrapper.like(StringUtils.hasText(sysRoleDto.getRoleKey()), SysRole::getRoleKey, sysRoleDto.getRoleKey());
        // 添加按起始时间和结束时间查询条件（仅在不为null时添加）
        if (sysRoleDto.getParams() != null ) {
            queryWrapper.between(sysRoleDto.getParams().get(0) != null && sysRoleDto.getParams().get(1) != null,
                    SysRole::getCreateTime, sysRoleDto.getParams().get(0), sysRoleDto.getParams().get(1));
        }

        // 在查询时按照 order_num 进行升序排序
        queryWrapper.orderByAsc(SysRole::getRoleSort);
        Page page = new Page(sysRoleDto.getPageNum(), sysRoleDto.getPageSize());
        page(page, queryWrapper);
        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public List<SysRole> getNe(Long id, String roleName, String roleKey) {
        // 创建查询条件
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(id != null, SysRole::getId, id); // 排除当前修改的数据
        queryWrapper.and(qw -> qw.eq(SysRole::getRoleName, roleName).or().eq(SysRole::getRoleKey, roleKey));
        return this.list(queryWrapper);
    }

    public SysRole existings(SysRoleSaveDto sysRoleSaveDto) {
        // 创建角色对象并设置角色信息
        SysRole newRole = new SysRole();
        newRole.setId(sysRoleSaveDto.getId());
        newRole.setRoleName(sysRoleSaveDto.getRoleName());
        newRole.setRoleKey(sysRoleSaveDto.getRoleKey());
        newRole.setRoleSort(sysRoleSaveDto.getRoleSort());
        newRole.setStatus(sysRoleSaveDto.getStatus());
        newRole.setRemark(sysRoleSaveDto.getRemark());
        return newRole;
    }

    @Override
    public ResponseResult add(SysRoleSaveDto sysRoleSaveDto) {
        // 首先检查是否已存在相同名称或编码的字典
        List<SysRole> existingDict = getNe(sysRoleSaveDto.getId(), sysRoleSaveDto.getRoleName(), sysRoleSaveDto.getRoleKey());
        if (!existingDict.isEmpty()) {
            // 如果已存在相同名称或编码的字典，返回操作失败的响应
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_ERROR);
        }
        SysRole newRole = existings(sysRoleSaveDto);
        // dictId为空，表示是新增操作
        // 执行新增操作
        boolean success = save(newRole);
        // 如果 MenuIds 为 null，表示没有传递，则执行默认逻辑
        if (sysRoleSaveDto.getMenuIds() != null && !sysRoleSaveDto.getMenuIds().isEmpty()) {

            // 遍历 menuIds 数组，为每个菜单创建权限并关联到角色
            for (Integer menuId : sysRoleSaveDto.getMenuIds()) {
                SysRoleMenu roleMenu = new SysRoleMenu();
                roleMenu.setRoleId(newRole.getId());
                roleMenu.setMenuId(Long.valueOf(menuId));
                // 插入权限信息到权限表 (sys_role_menu)
                sysRoleMenuService.save(roleMenu);
            }
        }
        if (success) {
            return ResponseResult.okResult(AppHttpCodeEnum.ADD_SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.UP_ERROR);
        }
    }

    @Override
    public ResponseResult edit(SysRoleSaveDto sysRoleSaveDto) {
        // 首先检查是否已存在相同名称或编码的字典
        List<SysRole> existingDict = getNe(sysRoleSaveDto.getId(), sysRoleSaveDto.getRoleName(), sysRoleSaveDto.getRoleKey());
        if (!existingDict.isEmpty()) {
            // 如果已存在相同名称或编码的字典，返回操作失败的响应
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_ERROR);
        }
        // 创建角色对象并设置角色信息
        SysRole newRole = existings(sysRoleSaveDto);
        // 首先查询数据库中原始的数据
        SysRole originalPost = getById(newRole.getId());

        if (originalPost == null) {
            // 如果原始数据不存在，返回操作失败的响应
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_NULL);
        }

        // 执行修改操作
        boolean success = updateById(newRole);
// 如果 MenuIds 为 null，表示没有传递，则执行默认逻辑
        if (sysRoleSaveDto.getMenuIds() != null && !sysRoleSaveDto.getMenuIds().isEmpty()) {
            // 删除该角色之前关联的所有菜单权限
            sysRoleMenuService.removeByRoleId(newRole.getId());
            // 遍历 menuIds 数组，为每个菜单创建权限并关联到角色
            for (Integer menuId : sysRoleSaveDto.getMenuIds()) {
                SysRoleMenu roleMenu = new SysRoleMenu();
                roleMenu.setRoleId(newRole.getId());
                roleMenu.setMenuId(Long.valueOf(menuId));
                // 插入权限信息到权限表 (sys_role_menu)
                sysRoleMenuService.save(roleMenu);
            }
        } else {
            sysRoleMenuService.removeByRoleId(newRole.getId());
        }

        if (success) {
            return ResponseResult.okResult(AppHttpCodeEnum.UP_SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.UP_ERROR);
        }
    }

    @Override
    public ResponseResult selectAllDict() {
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        List<SysRole> roles = list(queryWrapper);
        List<SysRoleDictVo> list = BeanCopyUtils.copyBeanList(roles, SysRoleDictVo.class);
        return ResponseResult.okResult(list);
    }


}
