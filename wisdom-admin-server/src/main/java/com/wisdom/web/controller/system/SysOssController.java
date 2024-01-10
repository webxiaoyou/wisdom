package com.wisdom.web.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.util.ObjectUtil;
import com.wisdom.common.domain.ResponseResult;
//import com.wisdom.common.domain.vo.SysOssVo;
import com.wisdom.common.exception.SystemException;
import com.wisdom.system.domain.dto.SysOssDto;
import com.wisdom.system.domain.vo.SysOssVo;
import com.wisdom.system.service.ISysOssService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 文件上传
 *
 * @author wisdom
 */
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/system/oss")
public class SysOssController{


    private final ISysOssService sysOssService;

    /**
     * 分页查询所有数据
     * @param sysOssDto
     * @return
     */
    
    @SaCheckPermission("system:oss:list")
    @GetMapping("/list")
    public ResponseResult selectAll(SysOssDto sysOssDto){
        return sysOssService.selectAll(sysOssDto);
    }

    /**
     * 根据ID获取详情
     * @param ossId
     * @return
     */
    
    @SaCheckPermission("system:oss:query")
    @GetMapping(value = "/{ossId}")
    public ResponseResult getInfo(@PathVariable Long ossId) {
        return ResponseResult.okResult(sysOssService.getById(ossId));
    }
    /**
     * 上传OSS对象存储
     *
     * @param file 文件
     */
    
//    @SaCheckPermission("system:oss:upload")
    @PostMapping("/upload")
    public ResponseResult upload(@RequestPart("file") MultipartFile file) {
        if (ObjectUtil.isNull(file)) {
            throw new SystemException("上传文件不能为空");
        }
        SysOssVo oss = sysOssService.upload(file);
        Map<String, String> map = new HashMap<>(2);
        map.put("url", oss.getUrl());
        map.put("fileName", oss.getOriginalName());
        map.put("ossId", oss.getOssId().toString());
        return ResponseResult.okResult(map);
    }

    /**
     * 下载OSS对象
     *
     * @param ossId OSS对象ID
     */
//    @SaCheckPermission("system:oss:download")
    
    @GetMapping("/download/{ossId}")
    public void download(@PathVariable Long ossId, HttpServletResponse response) throws UnsupportedEncodingException {
        sysOssService.download(ossId,response);
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
        return sysOssService.deleteWithValidByIds(idList);
    }
}