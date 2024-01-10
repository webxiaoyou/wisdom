package com.wisdom.system.domain.vo;


import com.baomidou.mybatisplus.annotation.TableField;

import com.wisdom.common.domain.entity.SysMenu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutersVo {

    
    @TableField("Menu")
    private List<SysMenu> menus;

}
