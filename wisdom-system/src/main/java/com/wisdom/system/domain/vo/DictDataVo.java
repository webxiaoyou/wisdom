package com.wisdom.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DictDataVo {
    
    private Long dictCode;

    
    private Integer dictSort;

    
    private String dictLabel;

    
    private String dictValue;

    
    private String dictType;

    
    private String cssClass;

    
    private String listClass;

    
    private String isDefault;

    
    private String status;

    
    private String createBy;

    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    
    private String updateBy;

    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    
    private String remark;


}
