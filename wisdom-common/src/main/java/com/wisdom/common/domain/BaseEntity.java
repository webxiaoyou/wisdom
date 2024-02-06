package com.wisdom.common.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Entity基类
 *
 * @author wisdom
 */

@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private Integer pageNum = 1;

    @TableField(exist = false)
    private Integer pageSize = 15;
    /**
     * 请求参数
     */

    @TableField(exist = false)
    private List<LocalDateTime> params ;

    @TableField(exist = false)
    private String order;


}
