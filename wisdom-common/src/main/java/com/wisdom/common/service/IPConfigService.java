package com.wisdom.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.common.domain.dto.PConfigDTO;
import com.wisdom.common.domain.entity.PConfig;
import com.wisdom.common.domain.vo.PConfigVo;

import java.util.List;

/**
 * <p>
 * 小程序配置表 服务类
 * </p>
 *
 * @author wisdom
 * @since 2023-11-13
 */
public interface IPConfigService extends IService<PConfig> {

    /**
     * 获取配置信息
     * @return
     */
    List<PConfigVo> listWxConfig();

    /**
     * 列表查询
     *
     * @param pConfigDTO 查询条件
     * @return 列表结果
     */
    ResponseResult selectAll(PConfigDTO pConfigDTO);

    /**
     * 新增数据
     *
     * @param pConfig 实体对象
     * @return 新增结果
     */
    ResponseResult add(PConfig pConfig);

    /**
     * 修改数据
     *
     * @param pConfig 实体对象
     * @return 修改结果
     */
    ResponseResult edit(PConfig pConfig);
}
