package com.wisdom.common.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class LoginDTO implements Serializable {

    /**
     * appid
     */
    @NotNull(message = "appid不能为空")
    private String appid;

    /**
     * appid
     */
    @NotNull(message = "code不能为空")
    private String code;

    /**
     * 头像
     */
    @TableField(exist = false)
    private String avatar;

    /**
     * 昵称
     */
    @TableField(exist = false)
    private String nickname;

    /**
     * 是否是第一次
     */
    @TableField(exist = false)
    private String isOne;

}