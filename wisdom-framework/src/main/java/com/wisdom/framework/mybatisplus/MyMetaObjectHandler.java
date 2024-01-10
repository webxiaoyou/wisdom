package com.wisdom.framework.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.wisdom.common.helper.LoginHelper;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        String userName = null;
        try {

            userName = LoginHelper.getAccount();
        } catch (Exception e) {
            e.printStackTrace();
            userName = "admin";//表示是自己创建
        }
        this.setFieldValByName("createTime", now, metaObject);
        this.setFieldValByName("createBy",userName , metaObject);
        this.setFieldValByName("updateTime", now, metaObject);
        this.setFieldValByName("updateBy", userName, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        this.setFieldValByName("updateTime", now, metaObject);
        this.setFieldValByName("updateBy", LoginHelper.getAccount(), metaObject);
    }
}