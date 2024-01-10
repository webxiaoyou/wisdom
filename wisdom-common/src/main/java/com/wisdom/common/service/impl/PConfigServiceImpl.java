package com.wisdom.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.common.domain.dto.PConfigDTO;
import com.wisdom.common.domain.entity.PConfig;
import com.wisdom.common.domain.vo.PConfigVo;
import com.wisdom.common.domain.vo.PageVo;
import com.wisdom.common.enums.AppHttpCodeEnum;
import com.wisdom.common.mapper.PConfigMapper;
import com.wisdom.common.service.IPConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wisdom.common.utils.BeanCopyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 小程序配置表 服务实现类
 * </p>
 *
 * @author wisdom
 * @since 2023-11-13
 */

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Service
public class PConfigServiceImpl extends ServiceImpl<PConfigMapper, PConfig> implements IPConfigService {

    private final PConfigMapper baseMapper;

    @Override
    public List<PConfigVo> listWxConfig() {
        LambdaQueryWrapper<PConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PConfig::getType,"小程序").eq(PConfig::getStatus,"0");
        List<PConfig> list = baseMapper.selectList(wrapper);
        return BeanCopyUtils.copyBeanList(list, PConfigVo.class);
    }

    /**
     * 列表查询
     *
     * @param pConfigDTO 查询条件
     * @return 列表结果
     */
    @Override
    public ResponseResult selectAll(PConfigDTO pConfigDTO) {
        LambdaQueryWrapper<PConfig> queryWrapper = new LambdaQueryWrapper<>();
        // 根据实际需求添加查询条件
         queryWrapper.eq(StringUtils.hasText(pConfigDTO.getStatus()), PConfig::getStatus, pConfigDTO.getStatus());
         queryWrapper.eq(pConfigDTO.getWid()!=null, PConfig::getWid, pConfigDTO.getWid());
         queryWrapper.eq(StringUtils.hasText(pConfigDTO.getSecret()), PConfig::getSecret, pConfigDTO.getSecret());
         queryWrapper.like(StringUtils.hasText(pConfigDTO.getType()), PConfig::getType, pConfigDTO.getType());
//         queryWrapper.like(StringUtils.hasText(pConfigDTO.getPostName()), PConfig::getPostName, pConfigDTO.getPostName());
        //添加按起始时间和结束时间查询条件（仅在不为null时添加）
        if (pConfigDTO.getParams() != null ) {
            queryWrapper.between(pConfigDTO.getParams().get(0) != null && pConfigDTO.getParams().get(1) != null,
                    PConfig::getCreateTime, pConfigDTO.getParams().get(0), pConfigDTO.getParams().get(1));
        }
        Page page = new Page(pConfigDTO.getPageNum(), pConfigDTO.getPageSize());
        page(page, queryWrapper);
        // 对整个列表的 secret 进行脱敏处理
        List<PConfig> records = page.getRecords();
        records.forEach(config -> config.setSecret(desensitizeSecret(config.getSecret())));
        PageVo pageVo = new PageVo(records, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }
    /**
     * 脱敏处理 secret 字段
     *
     * @param secret 原始 secret
     * @return 脱敏后的 secret
     */
    private String desensitizeSecret(String secret) {
        // 实现你的脱敏逻辑，例如只显示部分字符
        // 这里仅为示例，实际应根据你的安全策略和需求进行实现
        if (secret != null && secret.length() > 8) {
            return secret.substring(0, 6) + "********" + secret.substring(secret.length() - 4);
        }
        return secret;
    }
    /**
     * 排除内容
     *
     * @param
     * @return 修改结果
     */
    public List<PConfig> getNe(Long id) {
        LambdaQueryWrapper<PConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(id != null, PConfig::getId, id); // 排除当前修改的数据
        // 添加其他条件，比如：
        // queryWrapper.and(qw -> qw.eq(PConfig::getPostCode, postCode).or().eq(PConfig::getPostName, postName));
        return this.list(queryWrapper);
    }

    /**
     * 新增数据
     *
     * @param pConfig 实体对象
     * @return 新增结果
     */
    @Override
    public ResponseResult add(PConfig pConfig) {
//        List<PConfig> existingData = getNe(pConfig.getId());
//
//        if (!existingData.isEmpty()) {
//            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_ERROR);
//        }

        boolean success = save(pConfig);

        if (success) {
            return ResponseResult.okResult(AppHttpCodeEnum.ADD_SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.ADD_ERROR);
        }
    }

    /**
     * 修改数据
     *
     * @param pConfig 实体对象
     * @return 新增结果
     */
    @Override
    public ResponseResult edit(PConfig pConfig) {
//        List<PConfig> existingData = getNe(pConfig.getId());
//
//        if (!existingData.isEmpty()) {
//            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_NULL);
//        }

        PConfig originalData = getById(pConfig.getId());

        if (originalData == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_NULL);
        }

        boolean success = updateById(pConfig);

        if (success) {
            return ResponseResult.okResult(AppHttpCodeEnum.UP_SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.UP_ERROR);
        }
    }
}
