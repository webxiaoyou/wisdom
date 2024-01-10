package com.wisdom.system.domain.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysOssConfigVo {

    
    private Long ossConfigId;

    
    private String configKey;

    
    private String accessKey;

    
    private String secretKey;

    
    private String bucketName;

    
    private String prefix;

    
    private String endpoint;

    
    private String domain;

    
    private String isHttps;

    
    private String region;

    
    private String accessPolicy;

    
    private String status;

    
    private String ext1;

    
    private String createBy;

    
    private LocalDateTime createTime;

    
    private String updateBy;

    
    private LocalDateTime updateTime;

    
    private String remark;

}
