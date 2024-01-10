package com.wisdom.web.controller.tenement;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.common.domain.entity.PProperty;
import com.wisdom.common.domain.dto.PPropertyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.wisdom.common.service.PPropertyService;
import java.util.List;

/**
 * 
 * 物业项目信息表
 * 
 * 
 * @Author wisdom
 */
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/tenement/pproperty")
public class PPropertyController {

    private final PPropertyService pPropertyService;

    /**
     * 列表查询
     *
     * @param pPropertyDTO 查询条件
     * @return 列表结果
     */
    @SaCheckPermission("system:pproperty:list")
    @GetMapping("/list")
    public ResponseResult selectAll(PPropertyDTO pPropertyDTO) {
        return pPropertyService.selectAll(pPropertyDTO);
    }

    /**
     * 字典列表
     * @param propertyId
     * @return
     */
    @GetMapping("/dict/list")
    public ResponseResult selectAll(@RequestParam(value = "propertyId", required = false) Long propertyId) {
        return pPropertyService.selectDictAll(propertyId);
    }
    /**
     * 根据ID获取详情
     *
     * @param propertyId 主键ID
     * @return 详情结果
     */
    @SaCheckPermission("system:pproperty:query")
    @GetMapping(value = "/{propertyId}")
    public ResponseResult getInfo(@PathVariable Long propertyId) {
        return ResponseResult.okResult(pPropertyService.getById(propertyId));
    }

    /**
     * 新增数据
     *
     * @param pProperty 实体对象
     * @return 新增结果
     */
    @SaCheckPermission("system:pproperty:add")
    @PostMapping("/save")
    public ResponseResult saveData(@RequestBody PProperty pProperty) {
        return pPropertyService.add(pProperty);
    }

    /**
     * 修改数据
     *
     * @param pProperty 实体对象
     * @return 修改结果
     */
    @SaCheckPermission("system:pproperty:edit")
    @PutMapping("/update")
    public ResponseResult updateData(@RequestBody PProperty pProperty) {
        return pPropertyService.edit(pProperty);
    }

    /**
     * 删除数据
     *
     * @param idList 主键集合
     * @return 删除结果
     */
    @SaCheckPermission("system:pproperty:remove")
    @DeleteMapping("/delete")
    public ResponseResult delete(@RequestParam("idList") List<Long> idList) {
        return ResponseResult.okResult(pPropertyService.removeByIds(idList));
    }
}


