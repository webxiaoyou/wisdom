package com.wisdom.proprletor.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.common.domain.dto.PBuildingDictDTO;
import com.wisdom.common.domain.dto.PFloorDTO;
import com.wisdom.common.service.PBuildingService;
import com.wisdom.common.service.PFloorService;
import com.wisdom.common.service.PPropertyService;
import com.wisdom.system.domain.dto.DictDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 小程序配置表 前端控制器
 * </p>
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

    @GetMapping("list")
    public ResponseResult selectAll() {
        return ResponseResult.okResult("hello word");
    }

    /**
     * 小区列表
     * @param propertyId
     * @return
     */
    @GetMapping("/roperty/dict/list")
    public ResponseResult selectAll(@RequestParam(value = "propertyId", required = false) Long propertyId) {
        return pPropertyService.selectDictAll(propertyId);
    }

    /**
     * 小区栋数列表
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
     * @param pFloorDTO 查询条件
     * @return 列表结果
     */
//    @SaCheckPermission("tenement:pbuilding:list")
    @GetMapping("/floor/list")
    public ResponseResult selectAll(PFloorDTO pFloorDTO) {
        return pFloorService.selectAll(pFloorDTO);
    }


}
