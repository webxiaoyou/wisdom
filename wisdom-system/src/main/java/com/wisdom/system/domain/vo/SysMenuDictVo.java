package com.wisdom.system.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysMenuDictVo {


    
    private Long value;

    
    private String label;

    
    //    查询的时候告诉它不要查询这个字段
    @TableField(exist = false)
    private List<SysMenuDictVo> children;

}
