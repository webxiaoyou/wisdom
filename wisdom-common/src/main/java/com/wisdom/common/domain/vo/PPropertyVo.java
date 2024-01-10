package com.wisdom.common.domain.vo;


import java.time.LocalDateTime;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 物业项目信息表(PProperty)内容返回实体类表
 *
 * @author wisdom
 * @since 2023-12-02 14:04:48
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PPropertyVo implements Serializable {

    private static final long serialVersionUID = 1L;

             
    /** 
    * 物业项目ID 
    */    
    @TableId(value = "property_id", type = IdType.AUTO) 
    private Long propertyId; 
    
               
    /** 
    * 物业项目名称 
    */           
    @TableField("property_name") 
    private String propertyName;
            
    /** 
    * 物业项目地址 
    */           
    @TableField("address") 
    private String address;
            
    /** 
    * 项目经理姓名 
    */           
    @TableField("manager_name") 
    private String managerName;

    /**
     * 项目经理联系方式
     */
    @TableField("phone")
    private String phone;
}
