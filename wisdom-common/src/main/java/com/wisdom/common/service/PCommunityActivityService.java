package com.wisdom.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.common.domain.entity.PCommunityActivity;
import com.wisdom.common.domain.dto.PCommunityActivityDTO;

/**
 * 社区活动信息表(PCommunityActivity)表服务接口
 *
 * @author wisdom
 * @since 2024-01-04 20:48:29
 */
public interface PCommunityActivityService extends IService<PCommunityActivity> {

   /**
    * 列表查询
    *
    * @param pCommunityActivityDTO 查询条件
    * @return 列表结果
    */
    ResponseResult selectAll(PCommunityActivityDTO pCommunityActivityDTO);

   /**
    * 新增数据
    *
    * @param pCommunityActivity 实体对象
    * @return 新增结果
    */
    ResponseResult add(PCommunityActivity pCommunityActivity);

   /**
    * 修改数据
    *
    * @param pCommunityActivity 实体对象
    * @return 修改结果
    */
    ResponseResult edit(PCommunityActivity pCommunityActivity);


}

