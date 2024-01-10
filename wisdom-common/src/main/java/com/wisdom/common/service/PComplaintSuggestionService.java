package com.wisdom.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.common.domain.entity.PComplaintSuggestion;
import com.wisdom.common.domain.dto.PComplaintSuggestionDTO;

/**
 * 投诉建议信息表(PComplaintSuggestion)表服务接口
 *
 * @author wisdom
 * @since 2024-01-02 22:12:00
 */
public interface PComplaintSuggestionService extends IService<PComplaintSuggestion> {

   /**
    * 列表查询
    *
    * @param pComplaintSuggestionDTO 查询条件
    * @return 列表结果
    */
    ResponseResult selectAll(PComplaintSuggestionDTO pComplaintSuggestionDTO);

   /**
    * 新增数据
    *
    * @param pComplaintSuggestion 实体对象
    * @return 新增结果
    */
    ResponseResult add(PComplaintSuggestion pComplaintSuggestion);

   /**
    * 修改数据
    *
    * @param pComplaintSuggestion 实体对象
    * @return 修改结果
    */
    ResponseResult edit(PComplaintSuggestion pComplaintSuggestion);


}

