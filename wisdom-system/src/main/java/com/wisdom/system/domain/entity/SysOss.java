package com.wisdom.system.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * OSS对象存储表
 * </p>
 *
 * @author wisdom
 * @since 2023-10-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_oss")

public class SysOss implements Serializable {

    private static final long serialVersionUID = 1L;

    
    @TableId(value = "oss_id", type = IdType.AUTO)
    private Long ossId;

    
    private String fileName;

    
    private String originalName;

    
    private String fileSuffix;

    
    private String url;

    
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    
    @TableField(fill = FieldFill.INSERT)
    private String updateBy;

    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    
    private String service;


}
