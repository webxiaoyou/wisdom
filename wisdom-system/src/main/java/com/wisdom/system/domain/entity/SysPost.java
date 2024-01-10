package com.wisdom.system.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * <p>
 * 岗位信息表
 * </p>
 *
 * @author wisdom
 * @since 2023-09-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_post")

public class SysPost implements Serializable {

    private static final long serialVersionUID = 1L;

    
    @TableId(value = "post_id", type = IdType.AUTO)
    private Long postId;

    
    @NotBlank(message = "岗位编码不能为空")
    @Size(min = 0, max = 100, message = "岗位编码长度不能超过{max}个字符")
    private String postCode;

    
    @NotBlank(message = "岗位名称不能为空")
    @Size(min = 0, max = 100, message = "岗位名称长度不能超过{max}个字符")
    private String postName;

    
    private Integer postSort;

    
    private String status;

    
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
