package com.wisdom.web.controller.tenement;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.common.domain.entity.PRepair;
import com.wisdom.common.domain.dto.PRepairDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.wisdom.common.service.PRepairService;
import java.util.List;

/**
 * 
 * 报修信息表
 * 
 * 
 * @Author wisdom
 */
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/prepair")
public class PRepairController {

    private final PRepairService pRepairService;

    /**
     * 列表查询
     *
     * @param pRepairDTO 查询条件
     * @return 列表结果
     */
    @SaCheckPermission("tenement:prepair:list")
    @GetMapping("/list")
    public ResponseResult selectAll(PRepairDTO pRepairDTO) {
        return pRepairService.selectAll(pRepairDTO);
    }

    /**
     * 根据ID获取详情
     *
     * @param repairId 主键ID
     * @return 详情结果
     */
    @SaCheckPermission("tenement:prepair:query")
    @GetMapping(value = "/{repairId}")
    public ResponseResult getInfo(@PathVariable Long repairId) {
        return ResponseResult.okResult(pRepairService.getById(repairId));
    }

    /**
     * 新增数据
     *
     * @param pRepair 实体对象
     * @return 新增结果
     */
    @SaCheckPermission("tenement:prepair:add")
    @PostMapping("/save")
    public ResponseResult saveData(@RequestBody PRepair pRepair) {
        return pRepairService.add(pRepair);
    }

    /**
     * 修改数据
     *
     * @param pRepair 实体对象
     * @return 修改结果
     */
    @SaCheckPermission("tenement:prepair:edit")
    @PutMapping("/update")
    public ResponseResult updateData(@RequestBody PRepair pRepair) {
        return pRepairService.edit(pRepair);
    }

    /**
     * 删除数据
     *
     * @param idList 主键集合
     * @return 删除结果
     */
    @SaCheckPermission("tenement:prepair:remove")
    @DeleteMapping("/delete")
    public ResponseResult delete(@RequestParam("idList") List<Long> idList) {
        return ResponseResult.okResult(pRepairService.removeByIds(idList));
    }
}


