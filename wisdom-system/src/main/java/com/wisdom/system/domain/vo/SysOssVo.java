package com.wisdom.system.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysOssVo {



    
    private Long ossId;

    
    private String fileName;

    
    private String originalName;

    
    private String fileSuffix;

    
    private String url;

    
    private String createBy;

    
    private LocalDateTime createTime;

    
    private String updateBy;

    
    private LocalDateTime updateTime;

    
    private String service;

}
