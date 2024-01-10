package com.wisdom.web.controller.tenement;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.common.domain.entity.PPropertyUnit;
import com.wisdom.common.domain.dto.PPropertyUnitDTO;
import com.wisdom.common.mapper.PPropertyUnitMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.wisdom.common.service.PPropertyUnitService;
import java.util.List;

/**
 * 
 * 房屋信息表
 * 
 * 
 * @Author wisdom
 */
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/pproperty_unit")
public class PPropertyUnitController {

    private final PPropertyUnitService pPropertyUnitService;

    private final PPropertyUnitMapper pPropertyUnitMapper;

    /**
     * 列表查询
     *
     * @param pPropertyUnitDTO 查询条件
     * @return 列表结果
     */
    @SaCheckPermission("tenement:pproperty_unit:list")
    @GetMapping("/list")
    public ResponseResult selectAll(PPropertyUnitDTO pPropertyUnitDTO) {
        return pPropertyUnitService.selectAll(pPropertyUnitDTO);
    }

    /**
     * 字典列表
     * @param
     * @return
     */
    @GetMapping("/dict/list")
    public ResponseResult selectDictAll(PPropertyUnitDTO pPropertyUnitDTO) {
        return pPropertyUnitService.selectDictAll(pPropertyUnitDTO);
    }

    /**
     * 根据ID获取详情
     *
     * @param unitId 主键ID
     * @return 详情结果
     */
    @SaCheckPermission("tenement:pproperty_unit:query")
    @GetMapping(value = "/{unitId}")
    public ResponseResult getInfo(@PathVariable Long unitId) {
        return ResponseResult.okResult(pPropertyUnitMapper.selectUserAndPUOne(unitId));
    }

    /**
     * 新增数据
     *
     * @param pPropertyUnit 实体对象
     * @return 新增结果
     */
    @SaCheckPermission("tenement:pproperty_unit:add")
    @PostMapping("/save")
    public ResponseResult saveData(@RequestBody PPropertyUnit pPropertyUnit) {
        return pPropertyUnitService.add(pPropertyUnit);
    }

    /**
     * 修改数据
     *
     * @param pPropertyUnit 实体对象
     * @return 修改结果
     */
    @SaCheckPermission("tenement:pproperty_unit:edit")
    @PutMapping("/update")
    public ResponseResult updateData(@RequestBody PPropertyUnit pPropertyUnit) {
        return pPropertyUnitService.edit(pPropertyUnit);
    }

    /**
     * 删除数据
     *
     * @param idList 主键集合
     * @return 删除结果
     */
    @SaCheckPermission("tenement:pproperty_unit:remove")
    @DeleteMapping("/delete")
    public ResponseResult delete(@RequestParam("idList") List<Long> idList) {
        return ResponseResult.okResult(pPropertyUnitService.removeByIds(idList));
    }
}


