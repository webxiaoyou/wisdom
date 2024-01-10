package com.wisdom.web.controller.system;


import cn.dev33.satoken.annotation.SaCheckPermission;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.system.domain.entity.SysPost;
import com.wisdom.system.domain.vo.PostVo;
import com.wisdom.system.service.ISysPostService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 岗位信息表
 *
 * @author wisdom
 */
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/system/post")
public class SysPostController {


    private final ISysPostService sysPostService;

    /**
     * 列表查询
     *
     * @param postVo
     * @return
     */
    @SaCheckPermission("system:post:list")
    @GetMapping("/list")
    public ResponseResult selectAll(PostVo postVo) {
        return sysPostService.selectAll(postVo);
    }

    /**
     * 获取字典信息
     * @return
     */
    @GetMapping("/dict/list")
    public ResponseResult selectAllDict() {
        return sysPostService.selectAllDict();
    }

    /**
     * 根据ID获取详情
     * @param postId
     * @return
     */
    
    @SaCheckPermission("system:post:query")
    @GetMapping(value = "/{postId}")
    public ResponseResult getInfo(@PathVariable Long postId) {
        return ResponseResult.okResult(sysPostService.getById(postId));
    }

    /**
     * 新增数据
     *
     * @param sysPost 实体对象
     * @return 新增结果
     */
    
    @SaCheckPermission("system:post:add")
    @PostMapping("/save")
    public ResponseResult saveData(@RequestBody SysPost sysPost) {
        return sysPostService.add(sysPost);
    }

    /**
     * 修改数据
     * @param sysPost
     * @return
     */
    
    @SaCheckPermission("system:post:edit")
    @PutMapping("/update")
    public ResponseResult updateData(@RequestBody SysPost sysPost) {
        return sysPostService.edit(sysPost);
    }


    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    
    @SaCheckPermission("system:post:remove")
    @DeleteMapping("delete")
    public ResponseResult delete(@RequestParam("idList") List<Long> idList) {
        return ResponseResult.okResult(this.sysPostService.removeByIds(idList));
    }
}
