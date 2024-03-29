package com.wisdom.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wisdom.common.constants.UserConstants;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.common.domain.dto.PUserDto;
import com.wisdom.common.domain.entity.PPropertyUnit;
import com.wisdom.common.domain.entity.PUser;
import com.wisdom.common.domain.vo.PPropertyUnitAndPUserVo;
import com.wisdom.common.domain.vo.PageVo;
import com.wisdom.common.enums.AppHttpCodeEnum;
import com.wisdom.common.mapper.PUserMapper;
import com.wisdom.common.service.IPUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wisdom.common.service.PPropertyUnitService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.wisdom.common.constants.UserConstants.AUTHENTICATION_SU_STATUS;

/**
 * <p>
 * 小程序用户信息表 服务实现类
 * </p>
 *
 * @author wisdom
 * @since 2023-11-13
 */
@AllArgsConstructor
@Service
public class PUserServiceImpl extends ServiceImpl<PUserMapper, PUser> implements IPUserService {

    private final PPropertyUnitService pPropertyUnitService;
    /**
     * 查询是否有该用户
     *
     * @param openid
     * @return
     */
    @Override
    public PPropertyUnitAndPUserVo getByopenid(String openid) {
        LambdaQueryWrapper<PUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PUser::getOpenid,openid);
        PUser pUser = getOne(wrapper);
        if (pUser != null) {
            LambdaQueryWrapper<PPropertyUnit> unitLambdaQueryWrapper = new LambdaQueryWrapper<>();
            unitLambdaQueryWrapper.eq(PPropertyUnit::getOwnerId,pUser.getUserId());
            unitLambdaQueryWrapper.eq(PPropertyUnit::getAuthenticationStatus, UserConstants.AUTHENTICATION_SU_STATUS);
            List<PPropertyUnit> propertyUnits = pPropertyUnitService.list(unitLambdaQueryWrapper);
            PPropertyUnitAndPUserVo pUserVo = new PPropertyUnitAndPUserVo();
            BeanUtils.copyProperties(pUser, pUserVo);
            if (!propertyUnits.isEmpty()) {
                BeanUtils.copyProperties(propertyUnits.get(0), pUserVo);
                // 将 unitIds 设置为单元的 ID 列表
                List<Long> buildingIds = new ArrayList<>();
                for (PPropertyUnit ropertyUnit : propertyUnits) {
                    buildingIds.add(ropertyUnit.getBuildingId());
                }
                pUserVo.setBuildingIds(buildingIds);
            }
            return pUserVo;
        } else {
            return null; // 或者抛出异常，具体取决于你的业务逻辑
        }
    }

    /**
     * 列表查询
     *
     * @param pUserDTO 查询条件
     * @return 列表结果
     */
    @Override
    public ResponseResult selectAll(PUserDto pUserDTO) {
        LambdaQueryWrapper<PUser> queryWrapper = new LambdaQueryWrapper<>();
        // 根据实际需求添加查询条件
         queryWrapper.eq(StringUtils.hasText(pUserDTO.getAccount()), PUser::getAccount, pUserDTO.getAccount());
        queryWrapper.eq(StringUtils.hasText(pUserDTO.getTel()), PUser::getTel, pUserDTO.getTel());
        queryWrapper.eq(StringUtils.hasText(pUserDTO.getLicensePlate()), PUser::getLicensePlate, pUserDTO.getLicensePlate());
        queryWrapper.eq(StringUtils.hasText(pUserDTO.getAuthenticated()), PUser::getAuthenticated, pUserDTO.getAuthenticated());


        // queryWrapper.eq(StringUtils.hasText(pUserDTO.getStatus()), PUser::getStatus, pUserDTO.getStatus());
        // queryWrapper.like(StringUtils.hasText(pUserDTO.getPostCode()), PUser::getPostCode, pUserDTO.getPostCode());
        // queryWrapper.like(StringUtils.hasText(pUserDTO.getPostName()), PUser::getPostName, pUserDTO.getPostName());
        //添加按起始时间和结束时间查询条件（仅在不为null时添加）
        if (pUserDTO.getParams() != null) {
            queryWrapper.between(pUserDTO.getParams().get(0) != null && pUserDTO.getParams().get(1) != null,
                    PUser::getCreateTime, pUserDTO.getParams().get(0), pUserDTO.getParams().get(1));
        }
        Page page = new Page(pUserDTO.getPageNum(), pUserDTO.getPageSize());
        page(page, queryWrapper);
        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    /**
     * 排除内容
     *
     * @param
     * @return 修改结果
     */
    public List<PUser> getNe(Long userId) {
        LambdaQueryWrapper<PUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(userId != null, PUser::getUserId, userId); // 排除当前修改的数据
        // 添加其他条件，比如：
        // queryWrapper.and(qw -> qw.eq(PUser::getPostCode, postCode).or().eq(PUser::getPostName, postName));
        return this.list(queryWrapper);
    }

    /**
     * 新增数据
     *
     * @param pUser 实体对象
     * @return 新增结果
     */
    @Override
    public ResponseResult add(PUser pUser) {
//        List<PUser> existingData = getNe(pUser.getUserId());
//
//        if (!existingData.isEmpty()) {
//            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_ERROR);
//        }

        boolean success = save(pUser);

        if (success) {
            return ResponseResult.okResult(AppHttpCodeEnum.ADD_SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.ADD_ERROR);
        }
    }

    /**
     * 修改数据
     *
     * @param pUser 实体对象
     * @return 新增结果
     */
    @Override
    public ResponseResult edit(PUser pUser) {
//        List<PUser> existingData = getNe(pUser.getUserId());
//
//        if (!existingData.isEmpty()) {
//            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_NULL);
//        }

        PUser originalData = getById(pUser.getUserId());

        if (originalData == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_NULL);
        }

        boolean success = updateById(pUser);

        if (success) {
            return ResponseResult.okResult(AppHttpCodeEnum.UP_SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.UP_ERROR);
        }
    }

    @Override
    public ResponseResult authentication(PUser pUser) {
        // 验证字段非空
        if (pUser.getUserId() == null || pUser.getUserName() == null || pUser.getIdentity() == null || pUser.getAuthenticated() == null) {
            return  ResponseResult.errorResult(500, "参数不能为空");
        }

        boolean success = updateById(pUser);

        if (success) {
            return  ResponseResult.okResult(200, "认证成功");
        } else {
            return ResponseResult.okResult(200, "认证更新失败");
        }
    }


}
