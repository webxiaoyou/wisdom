package com.wisdom.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wisdom.common.constants.CacheNames;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.common.domain.vo.PageVo;
import com.wisdom.common.enums.AppHttpCodeEnum;
import com.wisdom.common.utils.BeanCopyUtils;
import com.wisdom.common.utils.JsonUtils;
import com.wisdom.common.utils.StringUtils;
import com.wisdom.common.utils.redis.CacheUtils;
import com.wisdom.common.utils.redis.RedisUtils;
import com.wisdom.oss.constant.OssConstant;
import com.wisdom.system.domain.dto.SysOssConfigDto;
import com.wisdom.system.domain.entity.SysOssConfig;
import com.wisdom.system.domain.vo.SysOssConfigVo;
import com.wisdom.system.mapper.SysOssConfigMapper;
import com.wisdom.system.service.ISysOssConfigService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 对象存储配置表 服务实现类
 * </p>
 *
 * @author wisdom
 * @since 2023-10-28
 */
@Service
public class SysOssConfigServiceImpl extends ServiceImpl<SysOssConfigMapper, SysOssConfig> implements ISysOssConfigService {

    /**
     * 项目启动时，初始化参数到缓存，加载配置类
     */
    @Override
    public void init() {
        List<SysOssConfig> list = baseMapper.selectList(null);
        // 加载OSS初始化配置
        for (SysOssConfig config : list) {
            String configKey = config.getConfigKey();
            if ("0".equals(config.getStatus())) {
                RedisUtils.setCacheObject(OssConstant.DEFAULT_CONFIG_KEY, configKey);
            }
            CacheUtils.put(CacheNames.SYS_OSS_CONFIG, config.getConfigKey(), JsonUtils.toJsonString(config));
        }
    }

    @Override
    public ResponseResult selectAll(SysOssConfigDto sysOssConfigDto) {
        LambdaQueryWrapper<SysOssConfig> queryWrapper = buildQueryWrapper(sysOssConfigDto);
        Page page = new Page(sysOssConfigDto.getPageNum(), sysOssConfigDto.getPageSize());
        page(page, queryWrapper);
        List list = BeanCopyUtils.copyBeanList(page.getRecords(), SysOssConfigVo.class);
        PageVo pageVo = new PageVo(list, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult add(SysOssConfig sysOssConfig) {
        // 首先检查是否已存在相同名称或编码的字典
        List<SysOssConfig> existingDict = getNe(sysOssConfig.getOssConfigId(), sysOssConfig.getConfigKey());

        if (!existingDict.isEmpty()) {
            // 如果已存在相同名称或编码的字典，返回操作失败的响应
            return ResponseResult.errorResult(504, "配置KYE已存在");
        }
        // 执行新增操作
        boolean success = save(sysOssConfig);
        if (success) {
            CacheUtils.put(CacheNames.SYS_OSS_CONFIG, sysOssConfig.getConfigKey(), JsonUtils.toJsonString(sysOssConfig));
//            RedisUtils.setCacheListPut(OssConstant.OSS_CONFIG_CACHE, sysOssConfig.getConfigKey(), JsonUtils.toJsonString(sysOssConfig));
            return ResponseResult.okResult(AppHttpCodeEnum.ADD_SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.ADD_ERROR);
        }
    }

    @Override
    public ResponseResult edit(SysOssConfig sysOssConfig) {
        // 首先检查是否已存在相同名称或编码的字典
        List<SysOssConfig> existingDict = getNe(sysOssConfig.getOssConfigId(), sysOssConfig.getConfigKey());

        if (!existingDict.isEmpty()) {
            // 如果已存在相同名称或编码的字典，返回操作失败的响应
            return ResponseResult.errorResult(504, "配置KYE已存在");
        }
        // dictId不为空，表示是修改操作
        // 首先查询数据库中原始的数据
        SysOssConfig originalPost = getById(sysOssConfig.getOssConfigId());

        if (originalPost == null) {
            // 如果原始数据不存在，返回操作失败的响应
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_NULL);
        }

        // 执行修改操作
        boolean success = updateById(sysOssConfig);
        if (success) {
            CacheUtils.put(CacheNames.SYS_OSS_CONFIG, sysOssConfig.getConfigKey(), JsonUtils.toJsonString(sysOssConfig));
//            RedisUtils.setCacheListPut(OssConstant.OSS_CONFIG_CACHE, sysOssConfig.getConfigKey(), JsonUtils.toJsonString(sysOssConfig));
            return ResponseResult.okResult(AppHttpCodeEnum.UP_SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.UP_ERROR);
        }
    }

    @Override
    public ResponseResult updateOssConfigStatus(SysOssConfig sysOssConfig) {
        // 首先查询数据库中原始的数据
        SysOssConfig originalPost = getById(sysOssConfig.getOssConfigId());

        if (originalPost == null) {
            // 如果原始数据不存在，返回操作失败的响应
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_NULL);
        }
        int row = baseMapper.update(null, new LambdaUpdateWrapper<SysOssConfig>()
                .set(SysOssConfig::getStatus, "1"));
        row += baseMapper.updateById(sysOssConfig);
        if (row > 0) {
            RedisUtils.setCacheObject(OssConstant.DEFAULT_CONFIG_KEY, sysOssConfig.getConfigKey());
            init();
//            RedisUtils.setCacheListPut(OssConstant.OSS_CONFIG_CACHE, sysOssConfig.getConfigKey(), JsonUtils.toJsonString(sysOssConfig));
        }
        // 执行修改操作
//        boolean success = updateById(updatedStatus);

        if (row > 0) {
            return ResponseResult.okResult(AppHttpCodeEnum.UP_SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.UP_ERROR);
        }
    }

    @Override
    public ResponseResult deleteWithValidByIds(List<Long> idList) {
        if (CollUtil.containsAny(idList, OssConstant.SYSTEM_DATA_IDS)) {
            return ResponseResult.errorResult(403, "系统内置, 不可删除!");
        }
        List<SysOssConfig> list = CollUtil.newArrayList();
        for (Long configId : idList) {
            SysOssConfig config = baseMapper.selectById(configId);
            list.add(config);
        }
        boolean b = removeByIds(idList);
        if (b) {
//            list.forEach(sysOssConfig ->
//                    RedisUtils.delCacheMapValue(OssConstant.OSS_CONFIG_CACHE, sysOssConfig.getConfigKey()));
            list.forEach(sysOssConfig ->
                    CacheUtils.evict(CacheNames.SYS_OSS_CONFIG, sysOssConfig.getConfigKey()));
        }
        return ResponseResult.okResult(b);
    }

    @Override
    public List<SysOssConfig> getNe(Long ossConfig, String configKey) {
        // 创建查询条件
        LambdaQueryWrapper<SysOssConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(ossConfig != null, SysOssConfig::getOssConfigId, ossConfig); // 排除当前修改的数据
        queryWrapper.and(qw -> qw.eq(SysOssConfig::getConfigKey, configKey));
        return this.list(queryWrapper);
    }


    private LambdaQueryWrapper<SysOssConfig> buildQueryWrapper(SysOssConfigDto sysOssConfigDto) {
        LambdaQueryWrapper<SysOssConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(sysOssConfigDto.getConfigKey()), SysOssConfig::getConfigKey, sysOssConfigDto.getConfigKey());
        queryWrapper.eq(StringUtils.isNotEmpty(sysOssConfigDto.getBucketName()), SysOssConfig::getBucketName, sysOssConfigDto.getBucketName());
        queryWrapper.eq(StringUtils.isNotEmpty(sysOssConfigDto.getStatus()), SysOssConfig::getStatus, sysOssConfigDto.getStatus());
        return queryWrapper;
    }
}
