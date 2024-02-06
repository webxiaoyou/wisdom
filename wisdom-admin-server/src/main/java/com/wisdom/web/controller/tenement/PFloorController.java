package com.wisdom.web.controller.tenement;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.common.domain.entity.PFloor;
import com.wisdom.common.domain.dto.PFloorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.wisdom.common.service.PFloorService;
import java.util.List;

/**
 * 
 * 楼层信息表
 * 
 * 
 * @Author wisdom
 */
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/pfloor")
public class PFloorController {

    private final PFloorService pFloorService;

    /**
     * 列表查询
     *
     * @param pFloorDTO 查询条件
     * @return 列表结果
     */
    @SaCheckPermission("tenement:pbuilding:list")
    @GetMapping("/list")
    public ResponseResult selectAll(PFloorDTO pFloorDTO) {
        return pFloorService.selectAll(pFloorDTO);
    }

    /**
     * 字典列表
     * @param buildingId
     * @return
     */
    @GetMapping("/dict/list")
    public ResponseResult selectAll(@RequestParam(value = "buildingId", required = false) Long buildingId) {
        return pFloorService.selectDictAll(buildingId);
    }
    /**
     * 根据ID获取详情
     *
     * @param floorId 主键ID
     * @return 详情结果
     */
    @SaCheckPermission("tenement:pbuilding:query")
    @GetMapping(value = "/{floorId}")
    public ResponseResult getInfo(@PathVariable Long floorId) {
        return ResponseResult.okResult(pFloorService.getById(floorId));
    }


    /**
     * 新增数据
     *
     * @param pFloor 实体对象
     * @return 新增结果
     */
    @SaCheckPermission("tenement:pbuilding:add")
    @PostMapping("/save")
    public ResponseResult saveData(@RequestBody PFloor pFloor) {
        return pFloorService.add(pFloor);
    }

    /**
     * 修改数据
     *
     * @param pFloor 实体对象
     * @return 修改结果
     */
    @SaCheckPermission("tenement:pbuilding:edit")
    @PutMapping("/update")
    public ResponseResult updateData(@RequestBody PFloor pFloor) {
        return pFloorService.edit(pFloor);
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
        return ResponseResult.okResult(pFloorService.removeByIds(idList));
    }
}


