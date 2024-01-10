package com.wisdom.system.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * <p>
 * 对象存储配置表
 * </p>
 *
 * @author wisdom
 * @since 2023-10-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_oss_config")

public class SysOssConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    
    @TableId(value = "oss_config_id", type = IdType.AUTO)
    private Long ossConfigId;

    
    @NotNull (message = "配置key内容不能为空")
    private String configKey;

    
    @NotNull (message = "accessKey内容不能为空")
    private String accessKey;

    
    @NotNull (message = "secretKey内容不能为空")
    private String secretKey;

    
    @NotNull (message = "配置桶名称内容不能为空")
    private String bucketName;

    
    private String prefix;

    
    @NotNull (message = "访问站点内容不能为空")
    private String endpoint;

    
    private String domain;

    
    private String isHttps;

    
    private String region;

    
    private String accessPolicy;

    
    private String status;

    
    private String ext1;

    
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    
    @TableField(fill = FieldFill.INSERT)
    private String updateBy;

    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    
    private String remark;


}
