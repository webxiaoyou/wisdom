package com.wisdom.common.domain.vo;


import java.time.LocalDateTime;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 楼栋信息表(PBuilding)内容返回实体类表
 *
 * @author wisdom
 * @since 2023-12-02 20:54:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PBuildingVo implements Serializable {

    private static final long serialVersionUID = 1L;

             
    /** 
    * 楼栋ID 
    */    
    @TableId(value = "building_id", type = IdType.AUTO) 
    private Long buildingId; 
    
               
    /** 
    * 物业项目ID 
    */           
    @TableField("property_id") 
    private Long propertyId;
            
    /** 
    * 楼栋编号 
    */           
    @TableField("building_number") 
    private String buildingNumber;

    /**
     * 物业项目名称
     */
    @TableField("property_name")
    private String propertyName;
}
