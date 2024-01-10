package com.wisdom.web.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.system.domain.dto.DictTypeDto;
import com.wisdom.system.domain.entity.SysDictType;
import com.wisdom.system.service.ISysDictTypeService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据字典类型信息
 */
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/system/dict/type")
public class SysDictTypeController {

    private final ISysDictTypeService sysDictTypeService;


    /**
     * 获取字典类型列表
     *
     * @param dictTypeDto 字典类型查询条件
     * @return 字典类型列表
     */
    @SaCheckPermission("system:dict:list")
    @GetMapping("/list")
    public ResponseResult getTypeList(DictTypeDto dictTypeDto) {
        return sysDictTypeService.GetTypeList(dictTypeDto);
    }

    /**
     * 根据字典类型获取字典信息
     *
     * @param dictType 字典类型
     * @return 字典信息
     */
    @SaCheckPermission("system:dict:type")
    @GetMapping(value = "/{dictType}")
    public ResponseResult getTypeInfo(@PathVariable String dictType) {
        return sysDictTypeService.selectDictDataByType(dictType);
    }

    /**
     * 根据字典ID获取字典类型信息
     *
     * @param dictId 字典ID
     * @return 字典类型信息
     */
    @SaCheckPermission("system:dict:query")
    @GetMapping(value = "/typeInfo/{dictId}")
    public ResponseResult getInfo(@PathVariable Long dictId) {
        return sysDictTypeService.selectDictTypeById(dictId);
    }

    /**
     * 保存字典类型数据
     *
     * @param dictType 字典类型数据
     * @return 保存结果
     */
    @SaCheckPermission("system:dict:add")
    @PostMapping("/save")
    public ResponseResult saveData(@RequestBody SysDictType dictType) {
        return sysDictTypeService.add(dictType);
    }

    /**
     * 更新字典类型数据
     *
     * @param dictType 字典类型数据
     * @return 更新结果
     */
    @SaCheckPermission("system:dict:edit")
    @PutMapping("/update")
    public ResponseResult updateData(@RequestBody SysDictType dictType) {
        return sysDictTypeService.edit(dictType);
    }

    /**
     * 批量删除字典类型数据
     *
     * @param idList 待删除的字典类型ID列表
     * @return 删除结果
     */
    @SaCheckPermission("system:dict:remove")
    @DeleteMapping("/delete")
    public ResponseResult deleteList(@RequestParam("idList") List<Long> idList) {
        sysDictTypeService.removeByIds(idList);
        return ResponseResult.okResult(200, "删除成功");
    }

}
