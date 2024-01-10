package com.wisdom.web.controller.tenement;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.common.domain.dto.PBuildingDictDTO;
import com.wisdom.common.domain.entity.PBuilding;
import com.wisdom.common.domain.dto.PBuildingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.wisdom.common.service.PBuildingService;
import java.util.List;

/**
 * 
 * 楼栋信息表
 * 
 * 
 * @Author wisdom
 */
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/pbuilding")
public class PBuildingController {

    private final PBuildingService pBuildingService;

    /**
     * 列表查询
     *
     * @param pBuildingDTO 查询条件
     * @return 列表结果
     */
    @SaCheckPermission("tenement:pbuilding:list")
    @GetMapping("/list")
    public ResponseResult selectAll(PBuildingDTO pBuildingDTO) {
        return pBuildingService.selectAll(pBuildingDTO);
    }

    /**
     * 字典列表
     * @param pBuildingDictDTO
     * @return
     */
    @GetMapping("/dict/list")
    public ResponseResult selectDictAll(PBuildingDictDTO pBuildingDictDTO) {
        return pBuildingService.selectDictAll(pBuildingDictDTO);
    }

    /**
     * 根据ID获取详情
     *
     * @param buildingId 主键ID
     * @return 详情结果
     */
    @SaCheckPermission("tenement:pbuilding:query")
    @GetMapping(value = "/{buildingId}")
    public ResponseResult getInfo(@PathVariable Long buildingId) {
        return ResponseResult.okResult(pBuildingService.getById(buildingId));
    }

    /**
     * 新增数据
     *
     * @param pBuilding 实体对象
     * @return 新增结果
     */
    @SaCheckPermission("tenement:pbuilding:add")
    @PostMapping("/save")
    public ResponseResult saveData(@RequestBody PBuilding pBuilding) {
        return pBuildingService.add(pBuilding);
    }

    /**
     * 修改数据
     *
     * @param pBuilding 实体对象
     * @return 修改结果
     */
    @SaCheckPermission("tenement:pbuilding:edit")
    @PutMapping("/update")
    public ResponseResult updateData(@RequestBody PBuilding pBuilding) {
        return pBuildingService.edit(pBuilding);
    }

    /**
     * 删除数据
     *
     * @param idList 主键集合
     * @return 删除结果
     */
    @SaCheckPermission("tenement:pbuilding:remove")
    @DeleteMapping("/delete")
    public ResponseResult delete(@RequestParam("idList") List<Long> idList) {
        return ResponseResult.okResult(pBuildingService.removeByIds(idList));
    }
}


