package com.wisdom.system.service;

import com.wisdom.system.domain.dto.DictDataDto;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.system.domain.entity.SysDictData;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 字典数据表 服务类
 * </p>
 *
 * @author wisdom
 * @since 2023-09-22
 */
public interface ISysDictDataService extends IService<SysDictData> {

    ResponseResult GetTypeList(DictDataDto dictDataDto);

    ResponseResult selectDictDataByType(String dictType);

    List<SysDictData> getDictByName(String dictType,String dictLabel, String getDictValue);

    ResponseResult selectDictTypeById(Long dictId);

    List<SysDictData> getNe(Long dictCode, String dictType, String dictLabel, String dictValue);

    ResponseResult add(SysDictData dictData);

    ResponseResult edit(SysDictData dictData);
}
