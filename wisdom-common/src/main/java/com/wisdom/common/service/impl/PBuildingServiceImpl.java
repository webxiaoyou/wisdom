package com.wisdom.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wisdom.common.domain.dto.PBuildingDictDTO;
import com.wisdom.common.mapper.PBuildingMapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.common.domain.vo.PageVo;
import com.wisdom.common.enums.AppHttpCodeEnum;
import com.wisdom.common.domain.entity.PBuilding;
import com.wisdom.common.domain.dto.PBuildingDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.wisdom.common.service.PBuildingService;
import org.springframework.util.StringUtils;

/**
 * 楼栋信息表(PBuilding)表服务实现类
 *
 * @author wisdom
 * @since 2023-12-02 20:54:07
 */
@Service("pBuildingService")
public class PBuildingServiceImpl extends ServiceImpl<PBuildingMapper, PBuilding> implements PBuildingService {
    
    /**
    * 列表查询
    *
    * @param pBuildingDTO 查询条件
    * @return 列表结果
    */
    @Override
    public ResponseResult selectAll(PBuildingDTO pBuildingDTO) {
        LambdaQueryWrapper<PBuilding> queryWrapper = new LambdaQueryWrapper<>();
        // 根据实际需求添加查询条件
        queryWrapper.eq(StringUtils.hasText(pBuildingDTO.getBuildingNumber()), PBuilding::getBuildingNumber, pBuildingDTO.getBuildingNumber());
        queryWrapper.eq(pBuildingDTO.getPropertyId()!=null, PBuilding::getPropertyId, pBuildingDTO.getPropertyId());

        // queryWrapper.like(StringUtils.hasText(pBuildingDTO.getPostCode()), PBuilding::getPostCode, pBuildingDTO.getPostCode());
       // queryWrapper.like(StringUtils.hasText(pBuildingDTO.getPostName()), PBuilding::getPostName, pBuildingDTO.getPostName());
        //添加按起始时间和结束时间查询条件（仅在不为null时添加）
//        if (pBuildingDTO.getParams() != null ) {
//            queryWrapper.between(pBuildingDTO.getParams().get(0) != null && pBuildingDTO.getParams().get(1) != null,
//                    PBuilding::getCreateTime, pBuildingDTO.getParams().get(0), pBuildingDTO.getParams().get(1));
//        }
        Page page = new Page(pBuildingDTO.getPageNum(), pBuildingDTO.getPageSize());
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
public List<PBuilding> getNe(Long buildingId) {
    LambdaQueryWrapper<PBuilding> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.ne(buildingId != null, PBuilding::getBuildingId, buildingId); // 排除当前修改的数据
    // 添加其他条件，比如：
    // queryWrapper.and(qw -> qw.eq(PBuilding::getPostCode, postCode).or().eq(PBuilding::getPostName, postName));
    return this.list(queryWrapper);
}

    /**
    * 新增数据
    *
    * @param pBuilding 实体对象
    * @return 新增结果
    */
    @Override
    public ResponseResult add(PBuilding pBuilding) {
//        List<PBuilding> existingData = getNe(pBuilding.getBuildingId());
//
//        if (!existingData.isEmpty()) {
//            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_ERROR);
//        }

        boolean success = save(pBuilding);

        if (success) {
            return ResponseResult.okResult(AppHttpCodeEnum.ADD_SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.ADD_ERROR);
        }
    }

  /**
    * 修改数据
    *
    * @param pBuilding 实体对象
    * @return 新增结果
    */
    @Override
    public ResponseResult edit(PBuilding pBuilding) {
//         List<PBuilding> existingData = getNe(pBuilding.getBuildingId());
//
//        if (!existingData.isEmpty()) {
//            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_NULL);
//        }

        PBuilding originalData = getById(pBuilding.getBuildingId());

        if (originalData == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_NULL);
        }

        boolean success = updateById(pBuilding);

        if (success) {
            return ResponseResult.okResult(AppHttpCodeEnum.UP_SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.UP_ERROR);
        }
    }

    @Override
    public ResponseResult selectDictAll(PBuildingDictDTO pBuildingDictDTO) {
        LambdaQueryWrapper<PBuilding> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(pBuildingDictDTO.getBuildingId() != null,PBuilding::getBuildingId,pBuildingDictDTO.getBuildingId());
        queryWrapper.eq(pBuildingDictDTO.getPropertyId() != null,PBuilding::getPropertyId,pBuildingDictDTO.getPropertyId());;

        // 使用 Map 重写键
        List<Map<String, Object>> list = this.listMaps(queryWrapper).stream()
                .map(map -> {
                    // 只保留需要的字段，并重命名为"value"和"label"
                    Map<String, Object> resultMap = new HashMap<>();
                    resultMap.put("value", map.get("building_id"));
                    resultMap.put("label", map.get("building_number"));
                    return resultMap;
                })
                .collect(Collectors.toList());

        return ResponseResult.okResult(list);
    }
}
