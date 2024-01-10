package com.wisdom.system.service;

import com.wisdom.common.domain.ResponseResult;
import com.wisdom.system.domain.dto.DictTypeDto;
import com.wisdom.system.domain.entity.SysDictType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 字典类型表 服务类
 * </p>
 *
 * @author wisdom
 * @since 2023-09-22
 */
public interface ISysDictTypeService extends IService<SysDictType> {

    ResponseResult GetTypeList(DictTypeDto dictTypeDto);

    ResponseResult selectDictTypeById(Long dictId);

    ResponseResult selectDictDataByType(String dictType);

    SysDictType getDictByName(String dictName,String dictType);

    List<SysDictType> getNe(Long dictId, String dictName, String dictType);

    ResponseResult add(SysDictType dictType);

    ResponseResult edit(SysDictType dictType);
}
