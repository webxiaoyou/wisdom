package com.wisdom.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wisdom.common.mapper.PFloorMapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.common.domain.vo.PageVo;
import com.wisdom.common.enums.AppHttpCodeEnum;
import com.wisdom.common.domain.entity.PFloor;
import com.wisdom.common.domain.dto.PFloorDTO;

import java.util.List;
import com.wisdom.common.service.PFloorService;
import org.springframework.util.StringUtils;

/**
 * 楼层信息表(PFloor)表服务实现类
 *
 * @author wisdom
 * @since 2023-12-02 21:16:24
 */
@Service("pFloorService")
public class PFloorServiceImpl extends ServiceImpl<PFloorMapper, PFloor> implements PFloorService {
    
    /**
    * 列表查询
    *
    * @param pFloorDTO 查询条件
    * @return 列表结果
    */
    @Override
    public ResponseResult selectAll(PFloorDTO pFloorDTO) {
        LambdaQueryWrapper<PFloor> queryWrapper = new LambdaQueryWrapper<>();
        // 根据实际需求添加查询条件
        queryWrapper.eq(pFloorDTO.getBuildingId()!=null, PFloor::getBuildingId, pFloorDTO.getBuildingId());
        queryWrapper.eq(pFloorDTO.getFloorNumber()!=null, PFloor::getFloorNumber, pFloorDTO.getFloorNumber());
       // queryWrapper.like(StringUtils.hasText(pFloorDTO.getPostCode()), PFloor::getPostCode, pFloorDTO.getPostCode());
       // queryWrapper.like(StringUtils.hasText(pFloorDTO.getPostName()), PFloor::getPostName, pFloorDTO.getPostName());
        //添加按起始时间和结束时间查询条件（仅在不为null时添加）
//        if (pFloorDTO.getParams() != null ) {
//            queryWrapper.between(pFloorDTO.getParams().get(0) != null && pFloorDTO.getParams().get(1) != null,
//                    PFloor::getCreateTime, pFloorDTO.getParams().get(0), pFloorDTO.getParams().get(1));
//        }
        Page page = new Page(pFloorDTO.getPageNum(), pFloorDTO.getPageSize());
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
public List<PFloor> getNe(Long floorId) {
    LambdaQueryWrapper<PFloor> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.ne(floorId != null, PFloor::getFloorId, floorId); // 排除当前修改的数据
    // 添加其他条件，比如：
    // queryWrapper.and(qw -> qw.eq(PFloor::getPostCode, postCode).or().eq(PFloor::getPostName, postName));
    return this.list(queryWrapper);
}

    /**
    * 新增数据
    *
    * @param pFloor 实体对象
    * @return 新增结果
    */
    @Override
    public ResponseResult add(PFloor pFloor) {
//        List<PFloor> existingData = getNe(pFloor.getFloorId());
//
//        if (!existingData.isEmpty()) {
//            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_ERROR);
//        }

        boolean success = save(pFloor);

        if (success) {
            return ResponseResult.okResult(AppHttpCodeEnum.ADD_SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.ADD_ERROR);
        }
    }

  /**
    * 修改数据
    *
    * @param pFloor 实体对象
    * @return 新增结果
    */
    @Override
    public ResponseResult edit(PFloor pFloor) {
//         List<PFloor> existingData = getNe(pFloor.getFloorId());
//
//        if (!existingData.isEmpty()) {
//            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_NULL);
//        }

        PFloor originalData = getById(pFloor.getFloorId());

        if (originalData == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_NULL);
        }

        boolean success = updateById(pFloor);

        if (success) {
            return ResponseResult.okResult(AppHttpCodeEnum.UP_SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.UP_ERROR);
        }
    }

    @Override
    public ResponseResult getBuildingId(Long buildingId) {
        List<PFloor> floors = baseMapper.getbuildingId(buildingId);
        return ResponseResult.okResult(floors);
    }
}
