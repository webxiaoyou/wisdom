package com.wisdom.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wisdom.common.mapper.PPropertyMapper;
import com.wisdom.common.service.PPropertyService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.common.domain.vo.PageVo;
import com.wisdom.common.enums.AppHttpCodeEnum;
import com.wisdom.common.domain.entity.PProperty;
import com.wisdom.common.domain.dto.PPropertyDTO;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 物业项目信息表(PProperty)表服务实现类
 *
 * @author wisdom
 * @since 2023-12-02 14:04:48
 */
@Service("pPropertyService")
public class PPropertyServiceImpl extends ServiceImpl<PPropertyMapper, PProperty> implements PPropertyService {
    
    /**
    * 列表查询
    *
    * @param pPropertyDTO 查询条件
    * @return 列表结果
    */
    @Override
    public ResponseResult selectAll(PPropertyDTO pPropertyDTO) {
        LambdaQueryWrapper<PProperty> queryWrapper = new LambdaQueryWrapper<>();
        // 根据实际需求添加查询条件
        queryWrapper.eq(StringUtils.hasText(pPropertyDTO.getManagerName()), PProperty::getManagerName, pPropertyDTO.getManagerName());
        queryWrapper.eq(StringUtils.hasText(pPropertyDTO.getPropertyName()), PProperty::getPropertyName, pPropertyDTO.getPropertyName());
        queryWrapper.like(StringUtils.hasText(pPropertyDTO.getAddress()), PProperty::getAddress, pPropertyDTO.getAddress());
//        queryWrapper.like(StringUtils.hasText(pPropertyDTO.getPostName()), PProperty::getPostName, pPropertyDTO.getPostName());
        //添加按起始时间和结束时间查询条件（仅在不为null时添加）
//        if (pPropertyDTO.getParams() != null ) {
//            queryWrapper.between(pPropertyDTO.getParams().get(0) != null && pPropertyDTO.getParams().get(1) != null,
//                    PProperty::getCreateTime, pPropertyDTO.getParams().get(0), pPropertyDTO.getParams().get(1));
//        }
        Page page = new Page(pPropertyDTO.getPageNum(), pPropertyDTO.getPageSize());
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
public List<PProperty> getNe(Long propertyId) {
    LambdaQueryWrapper<PProperty> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.ne(propertyId != null, PProperty::getPropertyId, propertyId); // 排除当前修改的数据
    // 添加其他条件，比如：
    // queryWrapper.and(qw -> qw.eq(PProperty::getPostCode, postCode).or().eq(PProperty::getPostName, postName));
    return this.list(queryWrapper);
}

    /**
    * 新增数据
    *
    * @param pProperty 实体对象
    * @return 新增结果
    */
    @Override
    public ResponseResult add(PProperty pProperty) {
//        List<PProperty> existingData = getNe(pProperty.getPropertyId());
//
//        if (!existingData.isEmpty()) {
//            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_ERROR);
//        }

        boolean success = save(pProperty);

        if (success) {
            return ResponseResult.okResult(AppHttpCodeEnum.ADD_SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.ADD_ERROR);
        }
    }

  /**
    * 修改数据
    *
    * @param pProperty 实体对象
    * @return 新增结果
    */
    @Override
    public ResponseResult edit(PProperty pProperty) {
//         List<PProperty> existingData = getNe(pProperty.getPropertyId());
//
//        if (!existingData.isEmpty()) {
//            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_NULL);
//        }

        PProperty originalData = getById(pProperty.getPropertyId());

        if (originalData == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_NULL);
        }

        boolean success = updateById(pProperty);

        if (success) {
            return ResponseResult.okResult(AppHttpCodeEnum.UP_SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.UP_ERROR);
        }
    }

    /**
     * 字典列表
     * @param propertyId
     * @return
     */
    @Override
    public ResponseResult selectDictAll(Long propertyId) {
        LambdaQueryWrapper<PProperty> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(propertyId != null,PProperty::getPropertyId,propertyId);;

        // 使用 Map 重写键
        List<Map<String, Object>> list = this.listMaps(queryWrapper).stream()
                .map(map -> {
                    // 只保留需要的字段，并重命名为"value"和"label"
                    Map<String, Object> resultMap = new HashMap<>();
                    resultMap.put("value", map.get("property_id"));
                    resultMap.put("label", map.get("property_name"));
                    return resultMap;
                })
                .collect(Collectors.toList());

        return ResponseResult.okResult(list);
    }
}
