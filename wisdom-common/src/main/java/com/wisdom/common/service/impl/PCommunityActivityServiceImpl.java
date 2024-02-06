package com.wisdom.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wisdom.common.domain.entity.SysUser;
import com.wisdom.common.mapper.PCommunityActivityMapper;
import com.wisdom.common.service.PCommunityActivityService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.common.domain.vo.PageVo;
import com.wisdom.common.enums.AppHttpCodeEnum;
import com.wisdom.common.domain.entity.PCommunityActivity;
import com.wisdom.common.domain.dto.PCommunityActivityDTO;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 社区活动信息表(PCommunityActivity)表服务实现类
 *
 * @author wisdom
 * @since 2024-01-04 20:48:30
 */
@Service("pCommunityActivityService")
public class PCommunityActivityServiceImpl extends ServiceImpl<PCommunityActivityMapper, PCommunityActivity> implements PCommunityActivityService {
    
    /**
    * 列表查询
    *
    * @param pCommunityActivityDTO 查询条件
    * @return 列表结果
    */
    @Override
    public ResponseResult selectAll(PCommunityActivityDTO pCommunityActivityDTO) {

        LambdaQueryWrapper<PCommunityActivity> queryWrapper = new LambdaQueryWrapper<>();
        // 根据实际需求添加查询条件
        queryWrapper.eq(pCommunityActivityDTO.getPropertyId() != null, PCommunityActivity::getPropertyId,pCommunityActivityDTO.getPropertyId());
        queryWrapper.eq(StringUtils.hasText(pCommunityActivityDTO.getTitle()), PCommunityActivity::getTitle, pCommunityActivityDTO.getTitle());
        queryWrapper.eq(StringUtils.hasText(pCommunityActivityDTO.getLocation()), PCommunityActivity::getLocation, pCommunityActivityDTO.getLocation());
        if ("desc".equalsIgnoreCase(pCommunityActivityDTO.getOrder())) {
            queryWrapper.orderByDesc(PCommunityActivity::getCreateTime);
        } else if ("asc".equalsIgnoreCase(pCommunityActivityDTO.getOrder())) {
            queryWrapper.orderByAsc(PCommunityActivity::getCreateTime);
        }
        // queryWrapper.like(StringUtils.hasText(pCommunityActivityDTO.getPostCode()), PCommunityActivity::getPostCode, pCommunityActivityDTO.getPostCode());
       // queryWrapper.like(StringUtils.hasText(pCommunityActivityDTO.getPostName()), PCommunityActivity::getPostName, pCommunityActivityDTO.getPostName());
        //添加按起始时间和结束时间查询条件（仅在不为null时添加）
        if (pCommunityActivityDTO.getParams() != null && pCommunityActivityDTO.getParams().size() == 2) {
            queryWrapper.between(
                    PCommunityActivity::getStartTime,
                    pCommunityActivityDTO.getParams().get(0),
                    pCommunityActivityDTO.getParams().get(1)
            );
        }
        Page page = new Page(pCommunityActivityDTO.getPageNum(), pCommunityActivityDTO.getPageSize());
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
public List<PCommunityActivity> getNe(Long activityId) {
    LambdaQueryWrapper<PCommunityActivity> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.ne(activityId != null, PCommunityActivity::getActivityId, activityId); // 排除当前修改的数据
    // 添加其他条件，比如：
    // queryWrapper.and(qw -> qw.eq(PCommunityActivity::getPostCode, postCode).or().eq(PCommunityActivity::getPostName, postName));
    return this.list(queryWrapper);
}

    /**
    * 新增数据
    *
    * @param pCommunityActivity 实体对象
    * @return 新增结果
    */
    @Override
    public ResponseResult add(PCommunityActivity pCommunityActivity) {
//        List<PCommunityActivity> existingData = getNe(pCommunityActivity.getActivityId());
//
//        if (!existingData.isEmpty()) {
//            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_ERROR);
//        }

        //        添加按起始时间和结束时间查询条件（仅在不为null时添加）
        if (pCommunityActivity.getParams() != null ) {
            pCommunityActivity.setStartTime(pCommunityActivity.getParams().get(0));
            pCommunityActivity.setEndTime(pCommunityActivity.getParams().get(1));
        }

        boolean success = save(pCommunityActivity);

        if (success) {
            return ResponseResult.okResult(AppHttpCodeEnum.ADD_SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.ADD_ERROR);
        }
    }

  /**
    * 修改数据
    *
    * @param pCommunityActivity 实体对象
    * @return 新增结果
    */
    @Override
    public ResponseResult edit(PCommunityActivity pCommunityActivity) {
//         List<PCommunityActivity> existingData = getNe(pCommunityActivity.getActivityId());
//
//        if (!existingData.isEmpty()) {
//            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_NULL);
//        }

        PCommunityActivity originalData = getById(pCommunityActivity.getActivityId());

        if (originalData == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_NULL);
        }

        boolean success = updateById(pCommunityActivity);

        if (success) {
            return ResponseResult.okResult(AppHttpCodeEnum.UP_SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.UP_ERROR);
        }
    }
}
