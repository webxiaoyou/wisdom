package com.wisdom.web.controller.tenement;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.common.domain.entity.PConfig;
import com.wisdom.common.domain.dto.PConfigDTO;
import com.wisdom.common.service.IPConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 小程序配置
 * @Author wisdom
 */
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/tenement/puser/pconfig")
public class PConfigController {

    private final IPConfigService pConfigService;

    /**
     * 列表查询
     *
     * @param pConfigDTO 查询条件
     * @return 列表结果
     */
    @SaCheckPermission("system:pconfig:list")
    @GetMapping("/list")
    public ResponseResult selectAll(PConfigDTO pConfigDTO) {
        return pConfigService.selectAll(pConfigDTO);
    }

    /**
     * 根据ID获取详情
     *
     * @param id 主键ID
     * @return 详情结果
     */
    @SaCheckPermission("system:pconfig:query")
    @GetMapping(value = "/{id}")
    public ResponseResult getInfo(@PathVariable Long id) {
        return ResponseResult.okResult(pConfigService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param pConfig 实体对象
     * @return 新增结果
     */
    @SaCheckPermission("system:pconfig:add")
    @PostMapping("/save")
    public ResponseResult saveData(@RequestBody PConfig pConfig) {
        return pConfigService.add(pConfig);
    }

    /**
     * 修改数据
     *
     * @param pConfig 实体对象
     * @return 修改结果
     */
    @SaCheckPermission("system:pconfig:edit")
    @PutMapping("/update")
    public ResponseResult updateData(@RequestBody PConfig pConfig) {
        return pConfigService.edit(pConfig);
    }

    /**
     * 删除数据
     *
     * @param idList 主键集合
     * @return 删除结果
     */
    @SaCheckPermission("system:pconfig:remove")
    @DeleteMapping("/delete")
    public ResponseResult delete(@RequestParam("idList") List<Long> idList) {
        return ResponseResult.okResult(pConfigService.removeByIds(idList));
    }
}


