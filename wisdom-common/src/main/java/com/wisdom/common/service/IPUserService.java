package com.wisdom.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.common.domain.dto.PUserDto;
import com.wisdom.common.domain.entity.PUser;
import com.wisdom.common.domain.vo.PUserVo;

/**
 * <p>
 * 小程序用户信息表 服务类
 * </p>
 *
 * @author wisdom
 * @since 2023-11-13
 */
public interface IPUserService extends IService<PUser> {

    PUserVo getByopenid(String openid);

    /**
     * 列表查询
     *
     * @param pUserDTO 查询条件
     * @return 列表结果
     */
    ResponseResult selectAll(PUserDto pUserDTO);

    /**
     * 新增数据
     *
     * @param pUser 实体对象
     * @return 新增结果
     */
    ResponseResult add(PUser pUser);

    /**
     * 修改数据
     *
     * @param pUser 实体对象
     * @return 修改结果
     */
    ResponseResult edit(PUser pUser);

}
