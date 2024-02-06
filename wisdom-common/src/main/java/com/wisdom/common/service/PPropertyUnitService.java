package com.wisdom.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.common.domain.entity.PPropertyUnit;
import com.wisdom.common.domain.dto.PPropertyUnitDTO;

/**
 * 房屋信息表(PPropertyUnit)表服务接口
 *
 * @author wisdom
 * @since 2023-12-03 20:02:35
 */
public interface PPropertyUnitService extends IService<PPropertyUnit> {

   /**
    * 列表查询
    *
    * @param pPropertyUnitDTO 查询条件
    * @return 列表结果
    */
    ResponseResult selectAll(PPropertyUnitDTO pPropertyUnitDTO);

   /**
    * 新增数据
    *
    * @param pPropertyUnit 实体对象
    * @return 新增结果
    */
    ResponseResult add(PPropertyUnit pPropertyUnit);

   /**
    * 修改数据
    *
    * @param pPropertyUnit 实体对象
    * @return 修改结果
    */
    ResponseResult edit(PPropertyUnit pPropertyUnit);


    ResponseResult selectDictAll(PPropertyUnitDTO pPropertyUnitDTO);

    ResponseResult addEdit(PPropertyUnit pPropertyUnit);

    ResponseResult getUserInfo(Long userId);
}

