package com.wisdom.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.common.domain.dto.PNotificationDTO;
import com.wisdom.common.domain.entity.PNotification;

/**
 * 物业通知表(PNotification)表服务接口
 *
 * @author wisdom
 * @since 2023-12-19 14:03:16
 */
public interface PNotificationService extends IService<PNotification> {

   /**
    * 列表查询
    *
    * @param pNotificationDTO 查询条件
    * @return 列表结果
    */
    ResponseResult selectAll(PNotificationDTO pNotificationDTO);

   /**
    * 新增数据
    *
    * @param pNotification 实体对象
    * @return 新增结果
    */
    ResponseResult add(PNotification pNotification);

   /**
    * 修改数据
    *
    * @param pNotification 实体对象
    * @return 修改结果
    */
    ResponseResult edit(PNotification pNotification);


}

