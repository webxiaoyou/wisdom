package com.wisdom.system.service;

import com.wisdom.common.domain.ResponseResult;
import com.wisdom.system.domain.dto.SysOssDto;
import com.wisdom.system.domain.entity.SysOss;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wisdom.system.domain.vo.SysOssVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * <p>
 * OSS对象存储表 服务类
 * </p>
 *
 * @author wisdom
 * @since 2023-10-28
 */
public interface ISysOssService extends IService<SysOss> {

    SysOssVo upload(MultipartFile file);

    ResponseResult selectAll(SysOssDto sysOssDto);

    ResponseResult deleteWithValidByIds(List<Long> idList);

    void download(Long ossId, HttpServletResponse response) throws UnsupportedEncodingException;
}
