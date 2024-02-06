package com.wisdom.proprletor.controller;


import com.wisdom.common.domain.ResponseResult;
import com.wisdom.common.domain.dto.PBuildingDictDTO;
import com.wisdom.common.domain.dto.PFloorDTO;
import com.wisdom.common.service.PBuildingService;
import com.wisdom.common.service.PFloorService;
import com.wisdom.common.service.PPropertyService;
import com.wisdom.system.service.ISysDictDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 小程序配置表 前端控制器
 *
 * @author wisdom
 * @since 2023-11-13
 */
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/user/config")
public class PConfigController {

    private final PPropertyService pPropertyService;
    private final PBuildingService pBuildingService;

    private final PFloorService pFloorService;

    private final ISysDictDataService sysDictDataService;


    @GetMapping("list")
    public ResponseResult selectAll() {
        return ResponseResult.okResult("hello word");
    }

    /**
     * 小区列表
     *
     * @param propertyId
     * @return
     */
    @GetMapping("/property/dict/list")
    public ResponseResult selectAll(@RequestParam(value = "propertyId", required = false) Long propertyId) {
        return pPropertyService.selectDictAll(propertyId);
    }

    /**
     * 小区栋数列表
     *
     * @param pBuildingDictDTO
     * @return
     */
    @GetMapping("/building/dict/list")
    public ResponseResult selectDictAll(PBuildingDictDTO pBuildingDictDTO) {
        return pBuildingService.selectDictAll(pBuildingDictDTO);
    }

    /**
     * 小区楼层列表
     *
     * @param buildingId
     * @return
     */
    @GetMapping("/floor/dict/list")
    public ResponseResult selectDictAll(@RequestParam(value = "buildingId", required = false) Long buildingId) {
        return pFloorService.selectDictAll(buildingId);
    }

    /**
     * 根据字典类型获取字典信息
     *
     * @param dictType 字典类型
     * @return 字典信息
     */
//    @SaCheckPermission("system:dict:type")
    @GetMapping(value = "/system/dict/data/{dictType}")
    public ResponseResult getTypeInfo(@PathVariable String dictType) {
        return sysDictDataService.selectDictDataByType(dictType);
    }

}
