package com.wisdom.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wisdom.common.domain.vo.PPropertyUnitAndPUserVo;
import com.wisdom.common.mapper.PPropertyUnitMapper;
import com.wisdom.common.service.PPropertyUnitService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.common.domain.vo.PageVo;
import com.wisdom.common.enums.AppHttpCodeEnum;
import com.wisdom.common.domain.entity.PPropertyUnit;
import com.wisdom.common.domain.dto.PPropertyUnitDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 房屋信息表(PPropertyUnit)表服务实现类
 *
 * @author wisdom
 * @since 2023-12-03 20:02:35
 */
@Service("pPropertyUnitService")
public class PPropertyUnitServiceImpl extends ServiceImpl<PPropertyUnitMapper, PPropertyUnit> implements PPropertyUnitService {
    
    /**
    * 列表查询
    *
    * @param pPropertyUnitDTO 查询条件
    * @return 列表结果
    */
    @Override
    public ResponseResult selectAll(PPropertyUnitDTO pPropertyUnitDTO) {
        // 执行查询
        List<PPropertyUnitAndPUserVo> resultList = baseMapper.selectUserAndPUList(pPropertyUnitDTO);

        // 创建 Page 对象，手动设置总记录数
        Page<PPropertyUnitAndPUserVo> page = new Page<>(pPropertyUnitDTO.getPageNum(), pPropertyUnitDTO.getPageSize());
        page.setRecords(resultList);
        page.setTotal(resultList.size()); // 设置总记录数，此处需要根据实际情况设置

        // 构建返回结果
        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }
    
 /**
 * 排除内容
 *
 * @param 
 * @return 修改结果
 */
public List<PPropertyUnit> getNe(Long unitId) {
    LambdaQueryWrapper<PPropertyUnit> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.ne(unitId != null, PPropertyUnit::getUnitId, unitId); // 排除当前修改的数据
    // 添加其他条件，比如：
    // queryWrapper.and(qw -> qw.eq(PPropertyUnit::getPostCode, postCode).or().eq(PPropertyUnit::getPostName, postName));
    return this.list(queryWrapper);
}

    /**
    * 新增数据
    *
    * @param pPropertyUnit 实体对象
    * @return 新增结果
    */
    @Override
    public ResponseResult add(PPropertyUnit pPropertyUnit) {
//        List<PPropertyUnit> existingData = getNe(pPropertyUnit.getUnitId());
//
//        if (!existingData.isEmpty()) {
//            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_ERROR);
//        }

        boolean success = save(pPropertyUnit);

        if (success) {
            return ResponseResult.okResult(AppHttpCodeEnum.ADD_SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.ADD_ERROR);
        }
    }

  /**
    * 修改数据
    *
    * @param pPropertyUnit 实体对象
    * @return 新增结果
    */
    @Override
    public ResponseResult edit(PPropertyUnit pPropertyUnit) {
//         List<PPropertyUnit> existingData = getNe(pPropertyUnit.getUnitId());
//
//        if (!existingData.isEmpty()) {
//            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_NULL);
//        }

        PPropertyUnit originalData = getById(pPropertyUnit.getUnitId());

        if (originalData == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_NULL);
        }

        boolean success = updateById(pPropertyUnit);

        if (success) {
            return ResponseResult.okResult(AppHttpCodeEnum.UP_SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.UP_ERROR);
        }
    }

    @Override
    public ResponseResult selectDictAll(PPropertyUnitDTO pPropertyUnitDTO) {
        // 执行查询
        List<PPropertyUnitAndPUserVo> resultList = baseMapper.selectDictUserAndPUList(pPropertyUnitDTO);

        // 使用 Map 重写键
        List<Map<String, Object>> list = resultList.stream()
                .map(map -> {
                    // 只保留需要的字段，并重命名为"value"和"label"
                    Map<String, Object> resultMap = new HashMap<>();
                    resultMap.put("value", map.getUnitId());
                    if(pPropertyUnitDTO.getIsBuilding()){
                        // 例如，将 "building_number" 和 "some_other_field" 拼接为 label
                        resultMap.put("label",  map.getUserName());
                    }else{
                        // 例如，将 "building_number" 和 "some_other_field" 拼接为 label
                        resultMap.put("label", map.getBuildingNumber() + "-" + map.getUserName());
                    }


                    return resultMap;
                })
                .collect(Collectors.toList());

        return ResponseResult.okResult(list);
    }
}
