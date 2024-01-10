package com.wisdom.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wisdom.common.constants.UserConstants;
import com.wisdom.system.domain.dto.DictDataDto;
import com.wisdom.system.mapper.SysDictDataMapper;
import com.wisdom.system.service.ISysDictDataService;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.system.domain.entity.SysDictData;
import com.wisdom.common.domain.vo.PageVo;
import com.wisdom.common.enums.AppHttpCodeEnum;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 字典数据表 服务实现类
 * </p>
 *
 * @author wisdom
 * @since 2023-09-22
 */
@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements ISysDictDataService {


    /**
     * 获取列表
     * @param dictDataDto
     * @return
     */
    @Override
    public ResponseResult GetTypeList(DictDataDto dictDataDto) {
        LambdaQueryWrapper<SysDictData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(dictDataDto.getStatus()),SysDictData::getStatus,dictDataDto.getStatus());
        queryWrapper.like(StringUtils.hasText(dictDataDto.getDictLabel()),SysDictData::getDictLabel,dictDataDto.getDictLabel());
        queryWrapper.like(StringUtils.hasText(dictDataDto.getDictType()),SysDictData::getDictType,dictDataDto.getDictType());
        Page page = new Page(dictDataDto.getPageNum(), dictDataDto.getPageSize());
        page(page,queryWrapper);
        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    /**
     * 类型查询
     * @param dictType
     * @return
     */
    @Override
    public ResponseResult selectDictDataByType(String dictType) {
        LambdaQueryWrapper<SysDictData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDictData::getStatus, UserConstants.NORMAL).eq(SysDictData::getDictType,dictType);
        return ResponseResult.okResult(this.list(queryWrapper));
    }

//    /**
//     * 验证数据库中是否有已存在相同的 名称与类型
//     * @param dictLabel
//     * @param getDictValue
//     * @return
//     */
//    @Override
//    public List<SysDictData> getDictByName(String dictLabel, String getDictValue) {
//        LambdaQueryWrapper<SysDictData> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(SysDictData::getDictLabel, dictLabel).or().eq(SysDictData::getDictValue, getDictValue);
//        return this.list(queryWrapper);
//    }



    /**
     * 通过id查询
     * @param dictId
     * @return
     */
    @Override
    public ResponseResult selectDictTypeById(Long dictId) {
        if (dictId !=null){
            SysDictData sysDictData = baseMapper.selectById(dictId);
            return ResponseResult.okResult(sysDictData);
        }else{
            return ResponseResult.okResult(500,"请输入你要查询的条件");
        }
    }
    @Override
    public List<SysDictData> getDictByName(String dictType, String dictLabel, String getDictValue) {
        // 先查询所有与 dictType 相关的数据
        LambdaQueryWrapper<SysDictData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDictData::getDictType, dictType);
        List<SysDictData> dictDataList = this.list(queryWrapper);

        // 过滤出名称匹配的数据，只要 dictLabel 或者 getDictValue 匹配其中一个即可
        List<SysDictData> matchingData = dictDataList.stream()
                .filter(data -> data.getDictLabel().equals(dictLabel) || data.getDictValue().equals(getDictValue))
                .collect(Collectors.toList());

        return matchingData;
    }
    @Override
    public List<SysDictData> getNe(Long dictCode, String dictType, String dictLabel, String dictValue) {
        // 创建查询条件
        LambdaQueryWrapper<SysDictData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(dictCode != null, SysDictData::getDictCode, dictCode).eq(SysDictData::getDictType, dictType); // 排除当前修改的数据
        queryWrapper.and(qw -> qw.eq(SysDictData::getDictLabel, dictLabel).or().eq(SysDictData::getDictValue, dictValue));
        return this.list(queryWrapper);
    }

    @Override
    public ResponseResult add(SysDictData dictData) {
        // 首先检查是否已存在相同名称或编码的字典
        List<SysDictData> existingDict = getNe(dictData.getDictCode(), dictData.getDictType(), dictData.getDictLabel(),dictData.getDictValue());

        if (!existingDict.isEmpty()) {
            // 如果已存在相同名称或编码的字典，返回操作失败的响应
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_ERROR);
        }
        // dictId为空，表示是新增操作
        // 执行新增操作
        boolean success = save(dictData);

        if (success) {
            return ResponseResult.okResult(AppHttpCodeEnum.UP_SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.UP_ERROR);
        }
    }

    @Override
    public ResponseResult edit(SysDictData dictData) {
        // 首先检查是否已存在相同名称或编码的字典
        List<SysDictData> existingDict = getNe(dictData.getDictCode(), dictData.getDictType(), dictData.getDictLabel(),dictData.getDictValue());

        if (!existingDict.isEmpty()) {
            // 如果已存在相同名称或编码的字典，返回操作失败的响应
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_ERROR);
        }

        // dictId不为空，表示是修改操作
        // 首先查询数据库中原始的数据
        SysDictData originalPost = getById(dictData.getDictCode());

        if (originalPost == null) {
            // 如果原始数据不存在，返回操作失败的响应
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_NULL);
        }

        // 执行修改操作
        boolean success = updateById(dictData);

        if (success) {
            return ResponseResult.okResult(AppHttpCodeEnum.UP_SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.UP_ERROR);
        }
    }

}
