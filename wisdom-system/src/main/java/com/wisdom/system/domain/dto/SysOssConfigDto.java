package com.wisdom.system.domain.dto;

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
public class SysOssConfigDto {

    
    private Integer pageNum = 1;

    
    private Integer pageSize = 15;


    
    private String configKey;


    
    private String bucketName;


    
    private String status;



}
