package com.wisdom.web.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.system.domain.dto.DictDataDto;
import com.wisdom.system.domain.entity.SysDictData;
import com.wisdom.system.service.ISysDictDataService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据字典信息
 */
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/system/dict/data")
public class SysDictDataController {

    private final ISysDictDataService sysDictDataService;

    /**
     * 获取字典数据列表
     *
     * @param dictDataDto 字典数据查询条件
     * @return 字典数据列表
     */
//    @SaCheckPermission("system:dict:list")
    @GetMapping("/list")
    public ResponseResult getTypeList(DictDataDto dictDataDto) {
        return sysDictDataService.GetTypeList(dictDataDto);
    }

    /**
     * 根据字典类型获取字典信息
     *
     * @param dictType 字典类型
     * @return 字典信息
     */
//    @SaCheckPermission("system:dict:type")
    @GetMapping(value = "/{dictType}")
    public ResponseResult getTypeInfo(@PathVariable String dictType) {
        return sysDictDataService.selectDictDataByType(dictType);
    }

    /**
     * 保存字典数据
     *
     * @param dictData 字典数据
     * @return 保存结果
     */
    @SaCheckPermission("system:dict:add")
    @PostMapping("/save")
    public ResponseResult saveData(@RequestBody SysDictData dictData) {
        return sysDictDataService.add(dictData);
    }

    /**
     * 更新字典数据
     *
     * @param dictData 字典数据
     * @return 更新结果
     */
    @SaCheckPermission("system:dict:edit")
    @PutMapping("/update")
    public ResponseResult updateData(@RequestBody SysDictData dictData) {
        return sysDictDataService.edit(dictData);
    }

    /**
     * 根据字典ID获取字典信息
     *
     * @param dictId 字典ID
     * @return 字典信息
     */
    @SaCheckPermission("system:dict:query")
    @GetMapping(value = "/dataInfo/{dictId}")
    public ResponseResult getInfo(@PathVariable Long dictId) {
        return sysDictDataService.selectDictTypeById(dictId);
    }

    /**
     * 批量删除字典数据
     *
     * @param idList 待删除的字典ID列表
     * @return 删除结果
     */
    @SaCheckPermission("system:dict:remove")
    @DeleteMapping("/delete")
    public ResponseResult deleteList(@RequestParam("idList") List<Long> idList) {
        sysDictDataService.removeByIds(idList);
        return ResponseResult.okResult(200, "删除成功");
    }

}
