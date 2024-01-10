package com.wisdom.proprletor.controller;


import com.wisdom.common.domain.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 小程序用户信息表 前端控制器
 * </p>
 *
 * @author wisdom
 * @since 2023-11-13
 */
@RestController
@RequestMapping("/user")
public class PUserController {

    /**
     * 获取用户列表信息
     * @return
     */
    @GetMapping("list")
    public ResponseResult selectAll() {
        return ResponseResult.okResult("hello word");
    }
}
