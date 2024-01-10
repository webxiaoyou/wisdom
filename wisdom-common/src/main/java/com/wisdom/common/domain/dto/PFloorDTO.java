package com.wisdom.common.domain.dto;


import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.wisdom.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 楼层信息表(PFloor)内容返回实体类表
 *
 * @author wisdom
 * @since 2023-12-02 21:16:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PFloorDTO extends BaseEntity {

    private static final long serialVersionUID = 1L;

             
    /** 
    * 楼层ID 
    */    
    @TableId(value = "floor_id", type = IdType.AUTO) 
    private Long floorId; 
    
               
    /** 
    * 楼栋ID 
    */           
    @TableField("building_id") 
    private Long buildingId;
            
    /** 
    * 楼层编号 
    */           
    @TableField("floor_number") 
    private Integer floorNumber;

    /**
     * 楼栋编号
     */
    @TableField("building_number")
    private String buildingNumber;
       
}
