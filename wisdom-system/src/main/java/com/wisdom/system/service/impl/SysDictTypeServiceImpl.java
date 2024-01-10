package com.wisdom.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wisdom.common.constants.UserConstants;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.system.domain.entity.SysDictType;
import com.wisdom.common.domain.vo.PageVo;
import com.wisdom.common.enums.AppHttpCodeEnum;
import com.wisdom.system.domain.dto.DictTypeDto;
import com.wisdom.system.mapper.SysDictTypeMapper;
import com.wisdom.system.service.ISysDictTypeService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 字典类型表 服务实现类
 * </p>
 *
 * @author wisdom
 * @since 2023-09-22
 */
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements ISysDictTypeService {

    /**
     * 获取列表
     *
     * @param dictTypeDto
     * @return
     */
    @Override
    public ResponseResult GetTypeList(DictTypeDto dictTypeDto) {
        LambdaQueryWrapper<SysDictType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(dictTypeDto.getStatus()), SysDictType::getStatus, dictTypeDto.getStatus());
        queryWrapper.like(StringUtils.hasText(dictTypeDto.getDictName()), SysDictType::getDictName, dictTypeDto.getDictName());
        queryWrapper.like(StringUtils.hasText(dictTypeDto.getDictType()), SysDictType::getDictType, dictTypeDto.getDictType());
        // 添加按起始时间和结束时间查询条件（仅在不为null时添加）
        if (dictTypeDto.getParams() != null) {
            queryWrapper.between(dictTypeDto.getParams().get(0) != null && dictTypeDto.getParams().get(1) != null,
                    SysDictType::getCreateTime, dictTypeDto.getParams().get(0), dictTypeDto.getParams().get(1));
        }
        Page page = new Page(dictTypeDto.getPageNum(), dictTypeDto.getPageSize());
        page(page, queryWrapper);
        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    /**
     * 通过id查询
     *
     * @param dictId
     * @return
     */
    @Override
    public ResponseResult selectDictTypeById(Long dictId) {
        if (dictId != null) {
            SysDictType sysDictType = baseMapper.selectById(dictId);
            return ResponseResult.okResult(sysDictType);
        } else {
            return ResponseResult.okResult(500, "请输入你要查询的条件");
        }
    }

    /**
     * 通过类型查询
     *
     * @param dictType
     * @return
     */
    @Override
    public ResponseResult selectDictDataByType(String dictType) {
        if (dictType != null) {
            LambdaQueryWrapper<SysDictType> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysDictType::getStatus,  UserConstants.NORMAL).eq(SysDictType::getDictType, dictType);
            return ResponseResult.okResult(queryWrapper);
        } else {
            return ResponseResult.okResult(500, "请输入你要查询的条件");
        }

    }

    /**
     * 验证数据库中是否有已存在相同的 名称与类型
     *
     * @param dictName
     * @param dictType
     * @return
     */
    @Override
    public SysDictType getDictByName(String dictName, String dictType) {
        LambdaQueryWrapper<SysDictType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDictType::getDictName, dictName).or().eq(SysDictType::getDictType, dictType);
        return this.getOne(queryWrapper);
    }

    @Override
    public List<SysDictType> getNe(Long dictId, String dictName, String dictType) {
        // 创建查询条件
        LambdaQueryWrapper<SysDictType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(dictId != null, SysDictType::getDictId, dictId); // 排除当前修改的数据
        queryWrapper.and(qw -> qw.eq(SysDictType::getDictType, dictType).or().eq(SysDictType::getDictName, dictName));
        return this.list(queryWrapper);
    }


    @Override
    public ResponseResult add(SysDictType dictType) {
        // 首先检查是否已存在相同名称或编码的字典
        List<SysDictType> existingDict = getNe(dictType.getDictId(), dictType.getDictName(), dictType.getDictType());

        if (!existingDict.isEmpty()) {
            // 如果已存在相同名称或编码的字典，返回操作失败的响应
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_ERROR);
        }
        // dictId为空，表示是新增操作
        // 执行新增操作
        boolean success = save(dictType);

        if (success) {
            return ResponseResult.okResult(AppHttpCodeEnum.UP_SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.UP_ERROR);
        }
    }

    @Override
    public ResponseResult edit(SysDictType dictType) {
        // 首先检查是否已存在相同名称或编码的字典
        List<SysDictType> existingDict = getNe(dictType.getDictId(), dictType.getDictName(), dictType.getDictType());

        if (!existingDict.isEmpty()) {
            // 如果已存在相同名称或编码的字典，返回操作失败的响应
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_ERROR);
        }

        // dictId不为空，表示是修改操作
        // 首先查询数据库中原始的数据
        SysDictType originalPost = getById(dictType.getDictId());

        if (originalPost == null) {
            // 如果原始数据不存在，返回操作失败的响应
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_NULL);
        }

        // 执行修改操作
        boolean success = updateById(dictType);

        if (success) {
            return ResponseResult.okResult(AppHttpCodeEnum.UP_SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.UP_ERROR);
        }
    }


//    @Override
//    public List<SysDictType> getDictByName(String dictLabel, String getDictValue) {
//        LambdaQueryWrapper<SysDictType> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(SysDictType::getDictName, dictLabel).or().eq(SysDictType::getDictName, getDictValue);
//        return this.list(queryWrapper);
//    }
}
