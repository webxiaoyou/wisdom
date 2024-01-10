package com.wisdom.system.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.common.domain.vo.PageVo;
import com.wisdom.common.enums.AppHttpCodeEnum;
import com.wisdom.common.exception.SystemException;
import com.wisdom.common.utils.BeanCopyUtils;
import com.wisdom.common.utils.StringUtils;
import com.wisdom.common.utils.file.FileUtils;
import com.wisdom.common.utils.spring.SpringUtils;
import com.wisdom.oss.entity.UploadResult;
import com.wisdom.oss.entity.core.OssClient;
import com.wisdom.oss.factory.OssFactory;
import com.wisdom.system.domain.dto.SysOssDto;
import com.wisdom.system.domain.entity.SysOss;
import com.wisdom.system.domain.vo.SysOssVo;
import com.wisdom.system.mapper.SysOssMapper;
import com.wisdom.system.service.ISysOssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * <p>
 * OSS对象存储表 服务实现类
 * </p>
 *
 * @author wisdom
 * @since 2023-10-28
 */
@Service
public class SysOssServiceImpl extends ServiceImpl<SysOssMapper, SysOss> implements ISysOssService {

    @Autowired
    private SysOssMapper baseMapper;
    OssFactory ossFactory = new OssFactory();
    @Override
    public ResponseResult selectAll(SysOssDto sysOssDto) {
        LambdaQueryWrapper<SysOss> queryWrapper = buildQueryWrapper(sysOssDto);
        Page page = new Page(sysOssDto.getPageNum(), sysOssDto.getPageSize());
        page(page,queryWrapper);
        List list = BeanCopyUtils.copyBeanList(page.getRecords(), SysOssVo.class);
        PageVo pageVo = new PageVo(list, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }



    @Override
    public SysOssVo upload(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        String suffix = StringUtils.substring(originalFileName, originalFileName.lastIndexOf("."), originalFileName.length());
        // 使用实例方法获取 OssClient 实例
        OssClient storage = ossFactory.instance();
        UploadResult uploadResult = null;
        try {
            uploadResult = storage.uploadSuffix(file.getBytes(), suffix, file.getContentType());
        } catch (IOException e) {
            throw new SystemException(e.getMessage());
        }

        // 保存文件信息
        SysOss oss = new SysOss();
        oss.setUrl(uploadResult.getUrl());
        oss.setFileSuffix(suffix);
        oss.setFileName(uploadResult.getFilename());
        oss.setOriginalName(originalFileName);
        oss.setService(storage.getConfigKey());
        baseMapper.insert(oss);
        SysOssVo sysOssVo = BeanCopyUtils.copyBean(oss, SysOssVo.class);
        return sysOssVo;
    }
    @Override
    public ResponseResult deleteWithValidByIds(List<Long> idList) {

        List<SysOss> list = baseMapper.selectBatchIds(idList);
        for (SysOss sysOss : list) {
            OssClient storage = ossFactory.instance(sysOss.getService());
            storage.delete(sysOss.getUrl());
        }
        return ResponseResult.okResult(baseMapper.deleteBatchIds(idList) > 0);
    }

    @Override
    public void download(Long ossId, HttpServletResponse response) throws UnsupportedEncodingException {
        SysOss sysOss = SpringUtils.getAopProxy(this).getById(ossId);
        if (ObjectUtil.isNull(sysOss)) {
              ResponseResult.errorResult(AppHttpCodeEnum.FILE_DATA_NOT_NULL);
        }
        FileUtils.setAttachmentResponseHeader(response, sysOss.getOriginalName());
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE + "; charset=UTF-8");

        OssClient storage = ossFactory.instance();
        try(InputStream inputStream = storage.getObjectContent(sysOss.getUrl())) {
            int available = inputStream.available();
            IoUtil.copy(inputStream, response.getOutputStream(), available);
            response.setContentLength(available);
        } catch (Exception e) {
              ResponseResult.errorResult(500,e.getMessage());
        }
    }

    private LambdaQueryWrapper<SysOss> buildQueryWrapper(SysOssDto sysOssDto) {
        LambdaQueryWrapper<SysOss> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(sysOssDto.getFileName()),SysOss::getFileName,sysOssDto.getFileName());
        queryWrapper.like(StringUtils.isNotEmpty(sysOssDto.getOriginalName()),SysOss::getOriginalName,sysOssDto.getOriginalName());
        queryWrapper.eq(StringUtils.isNotEmpty(sysOssDto.getFileSuffix()),SysOss::getFileSuffix,sysOssDto.getFileSuffix());
        queryWrapper.eq(StringUtils.isNotEmpty(sysOssDto.getCreateBy()),SysOss::getCreateBy,sysOssDto.getCreateBy());
        queryWrapper.eq(StringUtils.isNotEmpty(sysOssDto.getService()),SysOss::getService,sysOssDto.getService());
        // 添加按起始时间和结束时间查询条件（仅在不为null时添加）
        if (sysOssDto.getParams() != null) {
            queryWrapper.between(sysOssDto.getParams().get(0) != null && sysOssDto.getParams().get(1) != null,
                    SysOss::getCreateTime, sysOssDto.getParams().get(0), sysOssDto.getParams().get(1));
        }
        return queryWrapper;
    }


}
