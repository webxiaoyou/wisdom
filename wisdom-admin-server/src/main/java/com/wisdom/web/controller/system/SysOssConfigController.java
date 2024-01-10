package com.wisdom.web.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.system.domain.dto.SysOssConfigDto;
import com.wisdom.system.domain.entity.SysOssConfig;
import com.wisdom.system.service.ISysOssConfigService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

//import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件上传配置
 *
 * @author wisdom
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/oss/config")
public class SysOssConfigController {


    private final ISysOssConfigService iSysOssConfigService;

    /**
     * 分页查询所有数据
     * @param sysOssConfigDto
     * @return
     */
    
    @SaCheckPermission("system:oss:list")
    @GetMapping("/list")
    public ResponseResult selectAll(SysOssConfigDto sysOssConfigDto){
        return iSysOssConfigService.selectAll(sysOssConfigDto);
    }

    /**
     * 根据ID获取详情
     * @param ossId
     * @return
     */
    
    @SaCheckPermission("system:oss:query")
    @GetMapping(value = "/{ossId}")
    public ResponseResult getInfo(@PathVariable Long ossId) {
        return ResponseResult.okResult(iSysOssConfigService.getById(ossId));
    }

    /**
     * 新增
     * @param sysOssConfig
     * @return
     */

    
    @SaCheckPermission("system:oss:add")
    @PostMapping("/save")
    public ResponseResult saveData( @RequestBody SysOssConfig sysOssConfig, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 校验失败，返回错误信息
            List<FieldError> errors = bindingResult.getFieldErrors();
            String errorMessages = errors.stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(", ")); // 将错误信息用逗号分隔连接成一个字符串
            return ResponseResult.errorResult(400, errorMessages);
        } else {
            // 调用服务方法，因为已通过校验
            return iSysOssConfigService.add(sysOssConfig);
        }
    }
    /**
     * 修改
     * @param sysOssConfig
     * @return
     */
    
    @SaCheckPermission("system:oss:edit")
    @PutMapping("/update")
    public ResponseResult updateData( @RequestBody SysOssConfig sysOssConfig , BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 校验失败，返回错误信息
            List<FieldError> errors = bindingResult.getFieldErrors();
            String errorMessages = errors.stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(", ")); // 将错误信息用逗号分隔连接成一个字符串
            return ResponseResult.errorResult(400, errorMessages);
        } else {
            // 调用服务方法，因为已通过校验
            return iSysOssConfigService.edit(sysOssConfig);
        }
    }

    /**
     * 状态修改
     */
    @SaCheckPermission("system:oss:edit")
    
    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody SysOssConfig sysOssConfig) {
        return iSysOssConfigService.updateOssConfigStatus(sysOssConfig);
    }


    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    
    @SaCheckPermission("system:oss:remove")
    @DeleteMapping("delete")
    public ResponseResult delete(@RequestParam("idList") List<Long> idList) {
//        return ResponseResult.okResult(iSysOssConfigService.removeByIds(idList));
        return iSysOssConfigService.deleteWithValidByIds(idList);
    }
}