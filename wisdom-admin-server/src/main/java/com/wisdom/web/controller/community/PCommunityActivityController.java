package com.wisdom.web.controller.community;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.common.domain.entity.PCommunityActivity;
import com.wisdom.common.domain.dto.PCommunityActivityDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.wisdom.common.service.PCommunityActivityService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 社区活动信息表
 * 
 * 
 * @Author wisdom
 */
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/pcommunity_activity")
public class PCommunityActivityController {

    private final PCommunityActivityService pCommunityActivityService;

    /**
     * 列表查询
     *
     * @param pCommunityActivityDTO 查询条件
     * @return 列表结果
     */
    @SaCheckPermission("community:pcommunity_activity:list")
    @GetMapping("/list")
    public ResponseResult selectAll(PCommunityActivityDTO pCommunityActivityDTO) {
        return pCommunityActivityService.selectAll(pCommunityActivityDTO);
    }

    /**
     * 根据ID获取详情
     *
     * @param activityId 主键ID
     * @return 详情结果
     */
    @SaCheckPermission("community:pcommunity_activity:query")
    @GetMapping(value = "/{activityId}")
    public ResponseResult getInfo(@PathVariable Long activityId) {
        PCommunityActivity byId = pCommunityActivityService.getById(activityId);

        // 将startTime和endTime的值赋给params字段
        List<LocalDateTime> params = new ArrayList<>();
        params.add(byId.getStartTime());
        params.add(byId.getEndTime());
        byId.setParams(params);
        return ResponseResult.okResult(byId);
    }

    /**
     * 新增数据
     *
     * @param pCommunityActivity 实体对象
     * @return 新增结果
     */
    @SaCheckPermission("community:pcommunity_activity:add")
    @PostMapping("/save")
    public ResponseResult saveData(@RequestBody PCommunityActivity pCommunityActivity) {
        return pCommunityActivityService.add(pCommunityActivity);
    }

    /**
     * 修改数据
     *
     * @param pCommunityActivity 实体对象
     * @return 修改结果
     */
    @SaCheckPermission("community:pcommunity_activity:edit")
    @PutMapping("/update")
    public ResponseResult updateData(@RequestBody PCommunityActivity pCommunityActivity) {
        return pCommunityActivityService.edit(pCommunityActivity);
    }

    /**
     * 删除数据
     *
     * @param idList 主键集合
     * @return 删除结果
     */
    @SaCheckPermission("community:pcommunity_activity:remove")
    @DeleteMapping("/delete")
    public ResponseResult delete(@RequestParam("idList") List<Long> idList) {
        return ResponseResult.okResult(pCommunityActivityService.removeByIds(idList));
    }
}


