package com.wisdom.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wisdom.common.domain.entity.PFloor;

import java.util.List;

/**
 * 楼层信息表(PFloor)表数据库访问层
 *
 * @author wisdom
 * @since 2023-12-02 21:16:24
 */
public interface PFloorMapper extends BaseMapper<PFloor> {

    List getbuildingId(Long buildingId);
}
