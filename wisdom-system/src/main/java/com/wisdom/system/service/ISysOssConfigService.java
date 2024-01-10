package com.wisdom.system.service;

import com.wisdom.common.domain.ResponseResult;
import com.wisdom.system.domain.dto.SysOssConfigDto;
import com.wisdom.system.domain.entity.SysOssConfig;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 对象存储配置表 服务类
 * </p>
 *
 * @author wisdom
 * @since 2023-10-28
 */
public interface ISysOssConfigService extends IService<SysOssConfig> {
    /**
     * 初始化OSS配置
     */
    void init();

    ResponseResult selectAll(SysOssConfigDto sysOssConfigDto);

    ResponseResult add(SysOssConfig sysOssConfig);

    ResponseResult edit(SysOssConfig sysOssConfig);

    List<SysOssConfig> getNe(Long ossConfig, String configKey);

    ResponseResult updateOssConfigStatus(SysOssConfig sysOssConfig);

    ResponseResult deleteWithValidByIds(List<Long> idList);
}
