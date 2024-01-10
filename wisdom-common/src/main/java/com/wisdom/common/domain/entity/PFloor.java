package com.wisdom.common.domain.entity;


import java.util.Date;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.*;
import lombok.EqualsAndHashCode;
import lombok.Data;
import java.time.LocalDateTime;
/**
 * 楼层信息表(PFloor)表实体类
 *
 * @author wisdom
 * @since 2023-12-02 21:16:24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("p_floor")
public class PFloor  implements Serializable {

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
