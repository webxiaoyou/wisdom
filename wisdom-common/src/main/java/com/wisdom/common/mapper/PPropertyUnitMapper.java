package com.wisdom.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wisdom.common.domain.dto.PPropertyUnitDTO;
import com.wisdom.common.domain.entity.PPropertyUnit;
import com.wisdom.common.domain.vo.PPropertyUnitAndPUserVo;

import java.util.List;

/**
 * 房屋信息表(PPropertyUnit)表数据库访问层
 *
 * @author wisdom
 * @since 2023-12-03 20:02:35
 */
public interface PPropertyUnitMapper extends BaseMapper<PPropertyUnit> {


    List<PPropertyUnitAndPUserVo> selectUserAndPUList(PPropertyUnitDTO pPropertyUnitDTO);

    PPropertyUnitAndPUserVo selectUserAndPUOne(Long unitId);

    List<PPropertyUnitAndPUserVo> selectDictUserAndPUList(PPropertyUnitDTO pPropertyUnitDTO);
}
