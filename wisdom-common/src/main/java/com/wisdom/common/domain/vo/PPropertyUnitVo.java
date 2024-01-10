package com.wisdom.common.domain.vo;

import java.time.LocalDateTime;

import java.time.LocalDateTime;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 房屋信息表(PPropertyUnit)内容返回实体类表
 *
 * @author wisdom
 * @since 2023-12-03 20:02:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PPropertyUnitVo implements Serializable {

    private static final long serialVersionUID = 1L;

             
    /** 
    * 房屋ID 
    */    
    @TableId(value = "unit_id", type = IdType.AUTO) 
    private Long unitId; 
    
               
    /** 
    * 楼栋ID 
    */           
    @TableField("building_id") 
    private Long buildingId;
            
    /** 
    * 楼栋编号 
    */           
    @TableField("building_number") 
    private String buildingNumber;
            
    /** 
    * 楼层ID 
    */           
    @TableField("floor_id") 
    private Long floorId;
            
    /** 
    * 楼层编号 
    */           
    @TableField("floor_number") 
    private Integer floorNumber;
            
    /** 
    * 房屋编号 
    */           
    @TableField("unit_number") 
    private String unitNumber;
            
    /** 
    * 业主ID 
    */           
    @TableField("owner_id") 
    private Long ownerId;
            
    /** 
    * 认证状态（0未认证 1已认证 2认证未通过） 
    */           
    @TableField("authentication_status") 
    private String authenticationStatus;
            
    /** 
    * 图片地址 
    */           
    @TableField("image_url") 
    private String imageUrl;
            
    /** 
    * 备注 
    */           
    @TableField("remark") 
    private String remark;
            
    /** 
    * 创建者 
    */           
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
            
    /** 
    * 创建时间 
    */           
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
            
    /** 
    * 更新者 
    */           
    @TableField(fill = FieldFill.INSERT)
    private String updateBy;
            
    /** 
    * 更新时间 
    */           
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
            
    /** 
    * 删除标志（0代表存在 1代表删除） 
    */           
    @TableField("del_flag") 
    private String delFlag;
            
    /** 
    * 物业项目ID 
    */           
    @TableField("property_id") 
    private Long propertyId;
            
    /** 
    * 物业项目名称 
    */           
    @TableField("property_name") 
    private String propertyName;
       
}
