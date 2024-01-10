package com.wisdom.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.common.domain.entity.PRepair;
import com.wisdom.common.domain.dto.PRepairDTO;

/**
 * 报修信息表(PRepair)表服务接口
 *
 * @author wisdom
 * @since 2023-12-25 13:53:27
 */
public interface PRepairService extends IService<PRepair> {

   /**
    * 列表查询
    *
    * @param pRepairDTO 查询条件
    * @return 列表结果
    */
    ResponseResult selectAll(PRepairDTO pRepairDTO);

   /**
    * 新增数据
    *
    * @param pRepair 实体对象
    * @return 新增结果
    */
    ResponseResult add(PRepair pRepair);

   /**
    * 修改数据
    *
    * @param pRepair 实体对象
    * @return 修改结果
    */
    ResponseResult edit(PRepair pRepair);


}

