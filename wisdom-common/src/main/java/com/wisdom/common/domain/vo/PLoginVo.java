package com.wisdom.common.domain.vo;



import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * <p>
 * 登录返回信息
 * </p>
 *
 * @author wisdom
 * @since 2023-10-26
 */

@Data
@AllArgsConstructor
public class PLoginVo {

    
    private String token;
    private PUserVo pUserVo;
//    private String isFistTime;
}
