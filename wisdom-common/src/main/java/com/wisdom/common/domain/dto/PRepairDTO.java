package com.wisdom.common.domain.dto;

import java.time.LocalDateTime;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.wisdom.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 报修信息表(PRepair)内容返回实体类表
 *
 * @author wisdom
 * @since 2023-12-25 13:56:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PRepairDTO extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 报修单唯一标识符
     */
    @TableId(value = "repair_id", type = IdType.AUTO)
    private Long repairId;


    /**
     * 报修用户的唯一标识符
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 物业项目的唯一标识符
     */
    @TableField("property_id")
    private Long propertyId;

    /**
     * 报修位置所在楼栋的唯一标识符
     */
    @TableField("building_id")
    private Long buildingId;

    /**
     * 报修位置所在楼层的唯一标识符
     */
    @TableField("floor_id")
    private Long floorId;

    /**
     * 报修类别
     */
    @TableField("category")
    private String category;

    /**
     * 报修描述，用户对问题的文字描述
     */
    @TableField("description")
    private String description;

    /**
     * 存储上传的图片地址
     */
    @TableField("image_urls")
    private String imageUrls;

    /**
     * 报修状态，如待处理、处理中、已完成等
     */
    @TableField("status")
    private String status;

    /**
     * 处理报修的物业人员的唯一标识符
     */
    @TableField("handler_id")
    private Long handlerId;

    /**
     * 报修单处理完成时间
     */
    @TableField("handled_at")
    private LocalDateTime handledAt;

    /**
     * 处理备注，记录物业人员的处理结果或备注信息
     */
    @TableField("remarks")
    private String remarks;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    @TableField(fill = FieldFill.INSERT)
    private String updateBy;
    /**
     * 手机
     */
    @TableField("tel")
    private String tel;
}