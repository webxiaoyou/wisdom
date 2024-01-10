package com.wisdom.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.common.domain.dto.PBuildingDictDTO;
import com.wisdom.common.domain.entity.PBuilding;
import com.wisdom.common.domain.dto.PBuildingDTO;

/**
 * 楼栋信息表(PBuilding)表服务接口
 *
 * @author wisdom
 * @since 2023-12-02 20:54:07
 */
public interface PBuildingService extends IService<PBuilding> {

   /**
    * 列表查询
    *
    * @param pBuildingDTO 查询条件
    * @return 列表结果
    */
    ResponseResult selectAll(PBuildingDTO pBuildingDTO);

   /**
    * 新增数据
    *
    * @param pBuilding 实体对象
    * @return 新增结果
    */
    ResponseResult add(PBuilding pBuilding);

   /**
    * 修改数据
    *
    * @param pBuilding 实体对象
    * @return 修改结果
    */
    ResponseResult edit(PBuilding pBuilding);


    ResponseResult selectDictAll(PBuildingDictDTO pBuildingDictDTO);
}

