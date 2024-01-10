package com.wisdom.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统访问记录
 * </p>
 *
 * @author wisdom
 * @since 2023-11-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_logininfor")

public class SysLogininfor implements Serializable {

    private static final long serialVersionUID = 1L;

    
    @TableId(value = "info_id", type = IdType.AUTO)
    private Long infoId;

    
    private String userName;

    
    private String ipaddr;

    
    private String loginLocation;

    
    private String browser;

    
    private String os;

    
    private String status;

    
    private String msg;

    
    private LocalDateTime loginTime;


}
