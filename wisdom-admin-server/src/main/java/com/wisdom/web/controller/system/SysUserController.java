package com.wisdom.web.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.secure.BCrypt;
import cn.hutool.core.io.FileUtil;
import com.wisdom.common.domain.dto.PUserDto;
import com.wisdom.common.domain.entity.SysUser;
import com.wisdom.common.helper.LoginHelper;
import com.wisdom.common.utils.StringUtils;
import com.wisdom.common.utils.file.MimeTypeUtils;
import com.wisdom.system.domain.vo.SysOssVo;
import com.wisdom.system.domain.vo.SysUserInfoVo;
import com.wisdom.system.service.ISysOssService;
import com.wisdom.system.service.ISysUserRoleService;
import com.wisdom.system.service.ISysUserService;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.system.domain.dto.SysUserPasswordDto;
import com.wisdom.system.domain.dto.SysUserDto;
import com.wisdom.system.domain.entity.SysUserRole;
import com.wisdom.common.enums.AppHttpCodeEnum;
import com.wisdom.common.utils.BeanCopyUtils;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
// import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * sys用户信息
 *
 * @author wisdom
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/user")
public class SysUserController {

    private final ISysUserService userService;

    private final ISysUserRoleService sysUserRoleService;

    private final ISysOssService sysOssService;
    /**
     * 查询所有用户列表
     *
     * @param sysUser 用户查询条件
     * @return 用户列表
     */
    @SaCheckPermission("system:user:list")
    @GetMapping("list")
    public ResponseResult selectAll(SysUser sysUser){
        return userService.selectAll(sysUser);
    }

    /**
     * 物业人员查询查询
     *
     * @param sysUser 查询条件
     * @return 列表结果
     */
    @GetMapping("/dict/list")
    public ResponseResult selectDictAll(SysUser sysUser) {
        return userService.selectAll(sysUser);
    }

    /**
     * 根据用户ID查询用户信息
     *
     * @param postId 用户ID
     * @return 用户信息
     */
    @SaCheckPermission("system:user:query")
    @GetMapping(value = "/{postId}")
    public ResponseResult getInfo(@PathVariable Long postId) {
        SysUser originalPost = userService.getById(postId);
        if (originalPost == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_NULL);
        }
        SysUserInfoVo userInfoVo = BeanCopyUtils.copyBean(originalPost, SysUserInfoVo.class);
        List<SysUserRole> userRoles = sysUserRoleService.getByUserId(postId);
        List<Long> roleIds = userRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
        userInfoVo.setRoleIds(roleIds);
        return ResponseResult.okResult(userInfoVo);
    }

    /**
     * 新增用户
     *
     * @param sysUserDto 用户信息
     * @return 新增结果
     */
    @SaCheckPermission("system:user:add")
    @PostMapping("/save")
    public ResponseResult saveData(@RequestBody SysUserDto sysUserDto) {
        return userService.add(sysUserDto);
    }

    /**
     * 修改用户信息
     *
     * @param sysUserDto 用户信息
     * @return 修改结果
     */
    @PutMapping("/update")
    public ResponseResult updateData(@RequestBody SysUserDto sysUserDto) {
        if (sysUserDto.getId() == 1L) {
            return ResponseResult.errorResult(403,"不允许修改管理员");
        }
        return userService.edit(sysUserDto);
    }

    /**
     * 修改用户密码
     *
     * @param sysUserPasswordDto 密码信息
     * @return 修改结果
     */
    @PostMapping("up/password")
    public ResponseResult updatePassword(@RequestBody SysUserPasswordDto sysUserPasswordDto) {
        SysUser user = userService.getById(sysUserPasswordDto.getId());
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_NULL);
        }
        if (!sysUserPasswordDto.getNewPassword().equals(sysUserPasswordDto.getConfirmPassword())) {
            return ResponseResult.errorResult(500, "新密码与确认新密码不匹配");
        }
        if (!BCrypt.checkpw(sysUserPasswordDto.getPassword(), user.getPassword())){
            return ResponseResult.errorResult(500, "旧密码错误");
        }
        user.setPassword(BCrypt.hashpw(sysUserPasswordDto.getNewPassword()));
        boolean success = userService.updateById(user);
        if (success) {
            return ResponseResult.okResult(AppHttpCodeEnum.UP_SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.UP_ERROR);
        }
    }

    /**
     * 超管修改用户密码
     *
     * @param sysUserPasswordDto 密码信息
     * @return 修改结果
     */
    @SaCheckPermission("system:user:resetPwd")
    @PostMapping("adminUp/password")
    public ResponseResult isAdminupdatePassword(@RequestBody SysUserPasswordDto sysUserPasswordDto) {
        SysUser user = userService.getById(sysUserPasswordDto.getId());
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_NULL);
        }
        if (!LoginHelper.isAdmin()) {
            return ResponseResult.errorResult(500, "没有权限修改用户数据");
        }

        user.setPassword(BCrypt.hashpw(sysUserPasswordDto.getPassword()));
        boolean success = userService.updateById(user);
        if (success) {
            return ResponseResult.okResult(AppHttpCodeEnum.UP_SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.UP_ERROR);
        }
    }

    /**
     * 删除用户
     *
     * @param idList 待删除用户ID列表
     * @return 删除结果
     */
    @DeleteMapping("delete")
    public ResponseResult delete(@RequestParam("idList") List<Long> idList) {
        if (idList.contains(1L)) {
            return ResponseResult.errorResult(403,"不允许删除超级管理员");
        }
        return ResponseResult.okResult(this.userService.removeByIds(idList));
    }

    /**
     * 用户头像上传
     *
     * @param avatarfile 用户头像文件
     * @return 上传结果
     */
    @PutMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseResult avatar(@RequestPart("avatarfile") MultipartFile avatarfile) {
        Map<String, Object> ajax = new HashMap<>();
        if (!avatarfile.isEmpty()) {
            String extension = FileUtil.extName(avatarfile.getOriginalFilename());
            if (!StringUtils.equalsAnyIgnoreCase(extension, MimeTypeUtils.IMAGE_EXTENSION)) {
                return ResponseResult.errorResult(500,"文件格式不正确，请上传" + Arrays.toString(MimeTypeUtils.IMAGE_EXTENSION) + "格式");
            }
            SysOssVo oss = sysOssService.upload(avatarfile);
            String avatar = oss.getUrl();
            if (userService.updateUserAvatar(LoginHelper.getAccount(), avatar)) {
                ajax.put("imgUrl", avatar);
                return ResponseResult.okResult(ajax);
            }
        }
        return ResponseResult.errorResult(500,"上传图片异常，请联系管理员");
    }

}