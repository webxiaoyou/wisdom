package com.wisdom.proprletor.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.common.domain.entity.PComplaintSuggestion;
import com.wisdom.common.domain.entity.PPropertyUnit;
import com.wisdom.common.domain.entity.PUser;
import com.wisdom.common.domain.entity.SysUser;
import com.wisdom.common.enums.AppHttpCodeEnum;
import com.wisdom.common.mapper.PPropertyUnitMapper;
import com.wisdom.common.service.IPUserService;
import com.wisdom.common.service.PComplaintSuggestionService;
import com.wisdom.common.service.PPropertyUnitService;
import com.wisdom.common.utils.BeanCopyUtils;
import com.wisdom.system.domain.entity.SysUserRole;
import com.wisdom.system.domain.vo.SysUserInfoVo;
import com.wisdom.system.service.ISysUserService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * 小程序用户信息表 前端控制器
 *
 *
 * @author wisdom
 * @since 2023-11-13
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class PUserController {

    private final IPUserService userService;
    private final PPropertyUnitService pPropertyUnitService;

    private final PComplaintSuggestionService pComplaintSuggestionService;

    private final ISysUserService sysUserService;
    /**
     * 用户认证
     * @return
     */
    @PostMapping("/authentication")
    public ResponseResult authentication(@RequestBody PUser pUser) {
                return userService.authentication(pUser);
    }

    /**
     * 业主认证
     *
     * @param pPropertyUnit 实体对象
     * @return 新增结果
     */
    @PostMapping("/authentication/unit")
    public ResponseResult addEdit(@RequestBody PPropertyUnit pPropertyUnit) {
        return pPropertyUnitService.addEdit(pPropertyUnit);
    }

    /**
     * 业主认证根据ID获取详情
     *
     * @param userId 主键ID
     * @return 详情结果
     */
    @GetMapping(value = "/authentication/unit/{userId}")
    public ResponseResult getInfo(@PathVariable Long userId) {
        return pPropertyUnitService.getUserInfo(userId);
    }

    /**
     * 投诉/建议
     *
     * @param pComplaintSuggestion 实体对象
     * @return 新增结果
     */
    @PostMapping("/suggestion/add")
    public ResponseResult saveData(@RequestBody PComplaintSuggestion pComplaintSuggestion) {
        return pComplaintSuggestionService.add(pComplaintSuggestion);
    }

    /**
     * 根据维修师傅ID查询用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @GetMapping(value = "/system/user/{id}")
    public ResponseResult getSysInfo(@PathVariable Long id) {
        SysUser originalPost = sysUserService.getById(id);
        if (originalPost == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_NULL);
        }
        SysUserInfoVo userInfoVo = BeanCopyUtils.copyBean(originalPost, SysUserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }
}
