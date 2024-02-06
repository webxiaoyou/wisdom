package com.wisdom.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.common.domain.entity.PFloor;
import com.wisdom.common.domain.dto.PFloorDTO;

/**
 * 楼层信息表(PFloor)表服务接口
 *
 * @author wisdom
 * @since 2023-12-02 21:16:24
 */
public interface PFloorService extends IService<PFloor> {

   /**
    * 列表查询
    *
    * @param pFloorDTO 查询条件
    * @return 列表结果
    */
    ResponseResult selectAll(PFloorDTO pFloorDTO);

   /**
    * 新增数据
    *
    * @param pFloor 实体对象
    * @return 新增结果
    */
    ResponseResult add(PFloor pFloor);

   /**
    * 修改数据
    *
    * @param pFloor 实体对象
    * @return 修改结果
    */
    ResponseResult edit(PFloor pFloor);


    /**
     * 根据栋数ID查询楼层
     * @param buildingId
     * @return
     */
    ResponseResult getBuildingId(Long buildingId);

    ResponseResult selectDictAll(Long buildingId);
}

