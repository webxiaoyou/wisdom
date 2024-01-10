package com.wisdom.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wisdom.system.domain.vo.SysPostDictVo;
import com.wisdom.system.service.ISysPostService;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.system.domain.entity.SysPost;
import com.wisdom.common.domain.vo.PageVo;
import com.wisdom.system.domain.vo.PostVo;
import com.wisdom.common.enums.AppHttpCodeEnum;
import com.wisdom.system.mapper.SysPostMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wisdom.common.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 岗位信息表 服务实现类
 * </p>
 *
 * @author wisdom
 * @since 2023-09-24
 */
@Service
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost> implements ISysPostService {

    @Override
    public ResponseResult selectAll(PostVo postVo) {
        LambdaQueryWrapper<SysPost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(postVo.getStatus()), SysPost::getStatus, postVo.getStatus());
        queryWrapper.like(StringUtils.hasText(postVo.getPostCode()), SysPost::getPostCode, postVo.getPostCode());
        queryWrapper.like(StringUtils.hasText(postVo.getPostName()), SysPost::getPostName, postVo.getPostName());
        // 在查询时按照 order_num 进行升序排序
        queryWrapper.orderByAsc(SysPost::getPostSort);

        Page page = new Page(postVo.getPageNum(), postVo.getPageSize());
        page(page, queryWrapper);

        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }




    @Override
    public List<SysPost> getNe(Long postId, String postName, String postCode) {
        // 创建查询条件
        LambdaQueryWrapper<SysPost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(postId != null, SysPost::getPostId, postId); // 排除当前修改的数据
        queryWrapper.and(qw -> qw.eq(SysPost::getPostCode, postCode).or().eq(SysPost::getPostName, postName));
        return this.list(queryWrapper);
    }

    @Override
    public ResponseResult add(SysPost sysPost) {
        // 首先检查是否已存在相同名称或编码的字典
        List<SysPost> existingDict = getNe(sysPost.getPostId(), sysPost.getPostName(), sysPost.getPostCode());

        if (!existingDict.isEmpty()) {
            // 如果已存在相同名称或编码的字典，返回操作失败的响应
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_ERROR);
        }
        // dictId为空，表示是新增操作
        // 执行新增操作
        boolean success = save(sysPost);

        if (success) {
            return ResponseResult.okResult(AppHttpCodeEnum.ADD_SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.ADD_ERROR);
        }
    }

    @Override
    public ResponseResult edit(SysPost sysPost) {
        // 首先检查是否已存在相同名称或编码的字典
        List<SysPost> existingDict = getNe(sysPost.getPostId(), sysPost.getPostName(), sysPost.getPostCode());

        if (!existingDict.isEmpty()) {
            // 如果已存在相同名称或编码的字典，返回操作失败的响应
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_NULL);
        }

        // dictId不为空，表示是修改操作
        // 首先查询数据库中原始的数据
        SysPost originalPost = getById(sysPost.getPostId());

        if (originalPost == null) {
            // 如果原始数据不存在，返回操作失败的响应
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_NULL);
        }

        // 执行修改操作
        boolean success = updateById(sysPost);

        if (success) {
            return ResponseResult.okResult(AppHttpCodeEnum.UP_SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.UP_ERROR);
        }
    }

    @Override
    public ResponseResult selectAllDict() {
        LambdaQueryWrapper<SysPost> queryWrapper = new LambdaQueryWrapper<>();
        List<SysPost> posts = list(queryWrapper);
        List<SysPostDictVo> list = BeanCopyUtils.copyBeanList(posts, SysPostDictVo.class);
        return ResponseResult.okResult(list);
    }
}
