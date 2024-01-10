package com.wisdom.system.service;

import com.wisdom.system.domain.entity.SysLogininfor;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统访问记录 服务类
 * </p>
 *
 * @author wisdom
 * @since 2023-11-09
 */
public interface ISysLogininforService extends IService<SysLogininfor> {

    //    @Override
    void insertLogininfor(SysLogininfor logininfor);
}
