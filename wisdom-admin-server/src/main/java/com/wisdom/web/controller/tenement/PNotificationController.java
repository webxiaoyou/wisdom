package com.wisdom.web.controller.tenement;
import java.time.LocalDateTime;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.common.domain.dto.PNotificationDTO;
import com.wisdom.common.domain.entity.PNotification;
import com.wisdom.common.service.PNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 
 * 物业通知表
 * 
 * 
 * @Author wisdom
 */
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/pnotification")
public class PNotificationController {

    private final PNotificationService pNotificationService;

    /**
     * 列表查询
     *
     * @param pNotificationDTO 查询条件
     * @return 列表结果
     */
    @SaCheckPermission("tenement:pnotification:list")
    @GetMapping("/list")
    public ResponseResult selectAll(PNotificationDTO pNotificationDTO) {
        return pNotificationService.selectAll(pNotificationDTO);
    }

    /**
     * 根据ID获取详情
     *
     * @param notificationId 主键ID
     * @return 详情结果
     */
    @SaCheckPermission("tenement:pnotification:query")
    @GetMapping(value = "/{notificationId}")
    public ResponseResult getInfo(@PathVariable Long notificationId) {
        return ResponseResult.okResult(pNotificationService.getById(notificationId));
    }

    /**
     * 新增数据
     *
     * @param pNotification 实体对象
     * @return 新增结果
     */
    @SaCheckPermission("tenement:pnotification:add")
    @PostMapping("/save")
    public ResponseResult saveData(@RequestBody PNotification pNotification) {
        return pNotificationService.add(pNotification);
    }

    /**
     * 修改数据
     *
     * @param pNotification 实体对象
     * @return 修改结果
     */
    @SaCheckPermission("tenement:pnotification:edit")
    @PutMapping("/update")
    public ResponseResult updateData(@RequestBody PNotification pNotification) {
        return pNotificationService.edit(pNotification);
    }

    /**
     * 删除数据
     *
     * @param idList 主键集合
     * @return 删除结果
     */
    @SaCheckPermission("tenement:pnotification:remove")
    @DeleteMapping("/delete")
    public ResponseResult delete(@RequestParam("idList") List<Long> idList) {
        return ResponseResult.okResult(pNotificationService.removeByIds(idList));
    }
}


