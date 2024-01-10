package com.wisdom.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.common.domain.entity.PProperty;
import com.wisdom.common.domain.dto.PPropertyDTO;

/**
 * 物业项目信息表(PProperty)表服务接口
 *
 * @author wisdom
 * @since 2023-12-02 14:04:48
 */
public interface PPropertyService extends IService<PProperty> {

   /**
    * 列表查询
    *
    * @param pPropertyDTO 查询条件
    * @return 列表结果
    */
    ResponseResult selectAll(PPropertyDTO pPropertyDTO);

   /**
    * 新增数据
    *
    * @param pProperty 实体对象
    * @return 新增结果
    */
    ResponseResult add(PProperty pProperty);

   /**
    * 修改数据
    *
    * @param pProperty 实体对象
    * @return 修改结果
    */
    ResponseResult edit(PProperty pProperty);


    ResponseResult selectDictAll(Long propertyId);
}

