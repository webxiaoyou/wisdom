package com.wisdom.common.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 用户配置视图对象 f_config
 *
 * @author wisdom
 * @date 2023-11-13
 */
@Data
@ExcelIgnoreUnannotated
public class PConfigVo {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ExcelProperty(value = "id")
    private Long id;

    /**
     * 微信id
     */
    @ExcelProperty(value = "微信id")
    private Long wid;

    /**
     * 类型
     */
    @ExcelProperty(value = "类型")
    private String type;

    /**
     * 身份标识
     */
    @ExcelProperty(value = "身份标识")
    private String appid;

    /**
     * 身份秘钥
     */
    @ExcelProperty(value = "身份秘钥")
    private String secret;

    /**
     * 配置token
     */
    @ExcelProperty(value = "配置token")
    private String token;

    /**
     * aeskey
     */
    @ExcelProperty(value = "aeskey")
    private String aeskey;

    /**
     * 是否支付
     */
    @ExcelProperty(value = "是否支付")
    private String isPay;

    /**
     * 状态（0正常 1停用）
     */
    @ExcelProperty(value = "是否使用")
    private String status;


}