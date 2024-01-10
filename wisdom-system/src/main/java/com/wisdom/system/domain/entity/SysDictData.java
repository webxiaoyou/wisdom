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
 * 字典数据表
 * </p>
 *
 * @author wisdom
 * @since 2023-09-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_dict_data")

public class SysDictData implements Serializable {

    private static final long serialVersionUID = 1L;

    
    @TableId(value = "dict_code", type = IdType.AUTO)
    private Long dictCode;

    
    private Integer dictSort;

    
    @NotBlank(message = "字典标签不能为空")
    @Size(min = 0, max = 100, message = "字典标签长度不能超过{max}个字符")
    private String dictLabel;

    
    @NotBlank(message = "字典键值不能为空")
    @Size(min = 0, max = 100, message = "字典键值长度不能超过{max}个字符")
    private String dictValue;

    
    @NotBlank(message = "字典类型不能为空")
    @Size(min = 0, max = 100, message = "字典类型长度不能超过{max}个字符")
    private String dictType;

    
    @Size(min = 0, max = 100, message = "样式属性长度不能超过{max}个字符")
    private String cssClass;

    
    private String listClass;

    
    private String isDefault;

    
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
