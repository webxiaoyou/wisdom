package com.wisdom.common.domain.dto;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class PBuildingDictDTO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 物业项目ID
     */
    @TableId(value = "property_id", type = IdType.AUTO)
    private Long propertyId;

    /** 
    * 楼栋ID 
    */           
    @TableField("building_id") 
    private Long buildingId;

       
}
