package com.wisdom.system.service;

import com.wisdom.common.domain.ResponseResult;
import com.wisdom.system.domain.entity.SysPost;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wisdom.system.domain.vo.PostVo;

import java.util.List;

/**
 * <p>
 * 岗位信息表 服务类
 * </p>
 *
 * @author wisdom
 * @since 2023-09-24
 */
public interface ISysPostService extends IService<SysPost> {

    ResponseResult selectAll(PostVo postVo);


    List<SysPost> getNe(Long postId, String postName, String postCode);

    ResponseResult add(SysPost sysPost);

    ResponseResult edit(SysPost sysPost);

    ResponseResult selectAllDict();
}
