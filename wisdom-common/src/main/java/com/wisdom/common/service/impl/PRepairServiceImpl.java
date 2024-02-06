package com.wisdom.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wisdom.common.mapper.PRepairMapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.common.domain.vo.PageVo;
import com.wisdom.common.enums.AppHttpCodeEnum;
import com.wisdom.common.domain.entity.PRepair;
import com.wisdom.common.domain.dto.PRepairDTO;

import java.util.List;
import com.wisdom.common.service.PRepairService;

/**
 * 报修信息表(PRepair)表服务实现类
 *
 * @author wisdom
 * @since 2023-12-25 13:53:27
 */
@Service("pRepairService")
public class PRepairServiceImpl extends ServiceImpl<PRepairMapper, PRepair> implements PRepairService {
    
    /**
    * 列表查询
    *
    * @param pRepairDTO 查询条件
    * @return 列表结果
    */
    @Override
    public ResponseResult selectAll(PRepairDTO pRepairDTO) {
        LambdaQueryWrapper<PRepair> queryWrapper = new LambdaQueryWrapper<>();
        // 根据实际需求添加查询条件
       queryWrapper.eq(pRepairDTO.getPropertyId()!=null, PRepair::getPropertyId, pRepairDTO.getPropertyId());
        queryWrapper.eq(pRepairDTO.getUserId()!=null, PRepair::getUserId, pRepairDTO.getUserId());
        queryWrapper.eq(pRepairDTO.getStatus()!=null, PRepair::getStatus, pRepairDTO.getStatus());
        queryWrapper.eq(pRepairDTO.getHandlerId()!=null, PRepair::getHandlerId, pRepairDTO.getHandlerId());
       // queryWrapper.like(StringUtils.hasText(pRepairDTO.getPostCode()), PRepair::getPostCode, pRepairDTO.getPostCode());
       // queryWrapper.like(StringUtils.hasText(pRepairDTO.getPostName()), PRepair::getPostName, pRepairDTO.getPostName());
        //添加按起始时间和结束时间查询条件（仅在不为null时添加）
//        if (pRepairDTO.getParams() != null ) {
//            queryWrapper.between(pRepairDTO.getParams().get(0) != null && pRepairDTO.getParams().get(1) != null,
//                    PRepair::getCreateTime, pRepairDTO.getParams().get(0), pRepairDTO.getParams().get(1));
//        }
        Page page = new Page(pRepairDTO.getPageNum(), pRepairDTO.getPageSize());
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
public List<PRepair> getNe(Long repairId) {
    LambdaQueryWrapper<PRepair> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.ne(repairId != null, PRepair::getRepairId, repairId); // 排除当前修改的数据
    // 添加其他条件，比如：
    // queryWrapper.and(qw -> qw.eq(PRepair::getPostCode, postCode).or().eq(PRepair::getPostName, postName));
    return this.list(queryWrapper);
}

    /**
    * 新增数据
    *
    * @param pRepair 实体对象
    * @return 新增结果
    */
    @Override
    public ResponseResult add(PRepair pRepair) {
//        List<PRepair> existingData = getNe(pRepair.getRepairId());
//
//        if (!existingData.isEmpty()) {
//            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_ERROR);
//        }

        boolean success = save(pRepair);

        if (success) {
            return ResponseResult.okResult(AppHttpCodeEnum.ADD_SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.ADD_ERROR);
        }
    }

  /**
    * 修改数据
    *
    * @param pRepair 实体对象
    * @return 新增结果
    */
    @Override
    public ResponseResult edit(PRepair pRepair) {
//         List<PRepair> existingData = getNe(pRepair.getRepairId());
//
//        if (!existingData.isEmpty()) {
//            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_NULL);
//        }

        PRepair originalData = getById(pRepair.getRepairId());

        if (originalData == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_NULL);
        }

        boolean success = updateById(pRepair);

        if (success) {
            return ResponseResult.okResult(AppHttpCodeEnum.UP_SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.UP_ERROR);
        }
    }
}
