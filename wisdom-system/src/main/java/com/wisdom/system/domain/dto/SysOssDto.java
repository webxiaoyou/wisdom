package com.wisdom.system.domain.dto;

import com.wisdom.common.domain.BaseEntity;
import com.wisdom.system.domain.entity.DefaultPaging;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysOssDto extends BaseEntity {

//    private static final long serialVersionUID = 1L;
//
//    
//    private Long ossId;

    
    private String fileName;

    
    private String originalName;

    
    private String fileSuffix;
//
//    
//    private String url;

    
    private String createBy;

    
    private LocalDateTime createTime;

//    
//    private String updateBy;

    
    private LocalDateTime updateTime;

    
    private String service;

}
