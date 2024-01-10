package com.wisdom.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wisdom.common.mapper.PComplaintSuggestionMapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.common.domain.vo.PageVo;
import com.wisdom.common.enums.AppHttpCodeEnum;
import com.wisdom.common.domain.entity.PComplaintSuggestion;
import com.wisdom.common.domain.dto.PComplaintSuggestionDTO;

import java.util.List;
import com.wisdom.common.service.PComplaintSuggestionService;

/**
 * 投诉建议信息表(PComplaintSuggestion)表服务实现类
 *
 * @author wisdom
 * @since 2024-01-02 22:12:00
 */
@Service("pComplaintSuggestionService")
public class PComplaintSuggestionServiceImpl extends ServiceImpl<PComplaintSuggestionMapper, PComplaintSuggestion> implements PComplaintSuggestionService {
    
    /**
    * 列表查询
    *
    * @param pComplaintSuggestionDTO 查询条件
    * @return 列表结果
    */
    @Override
    public ResponseResult selectAll(PComplaintSuggestionDTO pComplaintSuggestionDTO) {
        LambdaQueryWrapper<PComplaintSuggestion> queryWrapper = new LambdaQueryWrapper<>();
        // 根据实际需求添加查询条件
       // queryWrapper.eq(StringUtils.hasText(pComplaintSuggestionDTO.getStatus()), PComplaintSuggestion::getStatus, pComplaintSuggestionDTO.getStatus());
       // queryWrapper.like(StringUtils.hasText(pComplaintSuggestionDTO.getPostCode()), PComplaintSuggestion::getPostCode, pComplaintSuggestionDTO.getPostCode());
       // queryWrapper.like(StringUtils.hasText(pComplaintSuggestionDTO.getPostName()), PComplaintSuggestion::getPostName, pComplaintSuggestionDTO.getPostName());
        //添加按起始时间和结束时间查询条件（仅在不为null时添加）
        if (pComplaintSuggestionDTO.getParams() != null ) {
            queryWrapper.between(pComplaintSuggestionDTO.getParams().get(0) != null && pComplaintSuggestionDTO.getParams().get(1) != null,
                    PComplaintSuggestion::getCreateTime, pComplaintSuggestionDTO.getParams().get(0), pComplaintSuggestionDTO.getParams().get(1));
        }
        Page page = new Page(pComplaintSuggestionDTO.getPageNum(), pComplaintSuggestionDTO.getPageSize());
        page(page, queryWrapper);
        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }
    
 /**
 * 排除内容
 *
 * @param 
 * @return 修改结果
 */
public List<PComplaintSuggestion> getNe(Long id) {
    LambdaQueryWrapper<PComplaintSuggestion> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.ne(id != null, PComplaintSuggestion::getId, id); // 排除当前修改的数据
    // 添加其他条件，比如：
    // queryWrapper.and(qw -> qw.eq(PComplaintSuggestion::getPostCode, postCode).or().eq(PComplaintSuggestion::getPostName, postName));
    return this.list(queryWrapper);
}

    /**
    * 新增数据
    *
    * @param pComplaintSuggestion 实体对象
    * @return 新增结果
    */
    @Override
    public ResponseResult add(PComplaintSuggestion pComplaintSuggestion) {
        List<PComplaintSuggestion> existingData = getNe(pComplaintSuggestion.getId());

        if (!existingData.isEmpty()) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_ERROR);
        }

        boolean success = save(pComplaintSuggestion);

        if (success) {
            return ResponseResult.okResult(AppHttpCodeEnum.ADD_SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.ADD_ERROR);
        }
    }

  /**
    * 修改数据
    *
    * @param pComplaintSuggestion 实体对象
    * @return 新增结果
    */
    @Override
    public ResponseResult edit(PComplaintSuggestion pComplaintSuggestion) {
         List<PComplaintSuggestion> existingData = getNe(pComplaintSuggestion.getId());

        if (!existingData.isEmpty()) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_NULL);
        }

        PComplaintSuggestion originalData = getById(pComplaintSuggestion.getId());

        if (originalData == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_NULL);
        }

        boolean success = updateById(pComplaintSuggestion);

        if (success) {
            return ResponseResult.okResult(AppHttpCodeEnum.UP_SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.UP_ERROR);
        }
    }
}
