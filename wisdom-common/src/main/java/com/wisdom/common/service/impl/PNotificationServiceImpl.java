package com.wisdom.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.common.domain.dto.PNotificationDTO;
import com.wisdom.common.domain.entity.PNotification;
import com.wisdom.common.domain.vo.PageVo;
import com.wisdom.common.enums.AppHttpCodeEnum;
import com.wisdom.common.mapper.PNotificationMapper;
import com.wisdom.common.service.PNotificationService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 物业通知表(PNotification)表服务实现类
 *
 * @author wisdom
 * @since 2023-12-19 14:03:16
 */
@Service("pNotificationService")
public class PNotificationServiceImpl extends ServiceImpl<PNotificationMapper, PNotification> implements PNotificationService {
    
    /**
    * 列表查询
    *
    * @param pNotificationDTO 查询条件
    * @return 列表结果
    */
    @Override
    public ResponseResult selectAll(PNotificationDTO pNotificationDTO) {
        LambdaQueryWrapper<PNotification> queryWrapper = new LambdaQueryWrapper<>();
        // 根据实际需求添加查询条件
        queryWrapper.eq(StringUtils.hasText(pNotificationDTO.getTitle()), PNotification::getTitle, pNotificationDTO.getTitle());
        queryWrapper.eq(StringUtils.hasText(pNotificationDTO.getCreateBy()), PNotification::getCreateBy, pNotificationDTO.getCreateBy());

        queryWrapper.eq(StringUtils.hasText(pNotificationDTO.getNotificationType()), PNotification::getNotificationType, pNotificationDTO.getNotificationType());
        queryWrapper.eq(pNotificationDTO.getPropertyId() != null, PNotification::getPropertyId, pNotificationDTO.getPropertyId());

        // 在这里添加对 target_id 的判断
        if (pNotificationDTO.getTargetIds() != null && !pNotificationDTO.getTargetIds().isEmpty()) {
            queryWrapper.in(PNotification::getTargetId, pNotificationDTO.getTargetIds());
        }
        // queryWrapper.like(StringUtils.hasText(pNotificationDTO.getPostCode()), PNotification::getPostCode, pNotificationDTO.getPostCode());
       // queryWrapper.like(StringUtils.hasText(pNotificationDTO.getPostName()), PNotification::getPostName, pNotificationDTO.getPostName());
        //添加按起始时间和结束时间查询条件（仅在不为null时添加）
//        if (pNotificationDTO.getParams() != null ) {
//            queryWrapper.between(pNotificationDTO.getParams().get(0) != null && pNotificationDTO.getParams().get(1) != null,
//                    PNotification::getCreateTime, pNotificationDTO.getParams().get(0), pNotificationDTO.getParams().get(1));
//        }
        Page page = new Page(pNotificationDTO.getPageNum(), pNotificationDTO.getPageSize());
        page(page, queryWrapper);
        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }
    
 /**
 * 排除内容
 *
 * @param 
 * @return 修改结果
 */
public List<PNotification> getNe(Long notificationId) {
    LambdaQueryWrapper<PNotification> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.ne(notificationId != null, PNotification::getNotificationId, notificationId); // 排除当前修改的数据
    // 添加其他条件，比如：
    // queryWrapper.and(qw -> qw.eq(PNotification::getPostCode, postCode).or().eq(PNotification::getPostName, postName));
    return this.list(queryWrapper);
}

    /**
    * 新增数据
    *
    * @param pNotification 实体对象
    * @return 新增结果
    */
    @Override
    public ResponseResult add(PNotification pNotification) {
//        List<PNotification> existingData = getNe(pNotification.getNotificationId());
//
//        if (!existingData.isEmpty()) {
//            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_ERROR);
//        }

        boolean success = save(pNotification);

        if (success) {
            return ResponseResult.okResult(AppHttpCodeEnum.ADD_SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.ADD_ERROR);
        }
    }

  /**
    * 修改数据
    *
    * @param pNotification 实体对象
    * @return 新增结果
    */
    @Override
    public ResponseResult edit(PNotification pNotification) {
//         List<PNotification> existingData = getNe(pNotification.getNotificationId());
//
//        if (!existingData.isEmpty()) {
//            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_NULL);
//        }

        PNotification originalData = getById(pNotification.getNotificationId());

        if (originalData == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_NULL);
        }

        boolean success = updateById(pNotification);

        if (success) {
            return ResponseResult.okResult(AppHttpCodeEnum.UP_SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.UP_ERROR);
        }
    }
}
