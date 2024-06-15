import Http, { Axios } from '@/utils/axios';

enum ApiDict {

    //字典类型
    DICTTYPELIST = '/system/dict/type/list', //列表
    DICTTYPEINFO = '/system/dict/type/typeInfo/',  //ID查询查询
    DICTTYPESA = '/system/dict/type/save', //新增
    DICTTYPEUP = '/system/dict/type/update', //修改
    DICTTYPE = '/system/dict/type/',  //类型查询
    DICTTYPEDel = '/system/dict/type/delete',

    // 字典数据
    DICTDATALIST = '/system/dict/data/list', //列表
    DICTDATAINFO = '/system/dict/data/dataInfo/',  //ID查询查询
    DICTDATASA = '/system/dict/data/save', //新增
    DICTDATAUP = '/system/dict/data/update', //修改
    DICTDATATYPE = '/system/dict/data/',  //类型查询
    DICTDATADel = '/system/dict/data/delete',


    // 用户管理
    USERLIST = '/system/user/list', //列表
    USERDICTLIST = '/system/user/dict/list', //列表
    USERID = '/system/user/',  //详情
    USERSA = '/system/user/save',  //新增
    USERUP = '/system/user/update',  //修改
    USERDel = '/system/user/delete/', //删除
    USERPASSWORDUP = '/system/user/up/password',
    USERPASSWORDRESET = '/system/user/adminUp/password',
    USERAVATAR ='/system/user/avatar',  //头像上传

    //角色管理
    ROLELIST = '/system/role/list',
    ROLEDICTLIST = '/system/role/dict/list',
    ROLEID = '/system/role/',
    ROLESA = '/system/role/save',
    ROLEUP = '/system/role/update',
    ROLEDel = '/system/role/delete',


    //岗位管理
    POSTLIST = '/system/post/list',
    POSTDICTLIST = '/system/post/dict/list',
    POSTID = '/system/post/',
    POSTSA = '/system/post/save',
    POSTUP = '/system/post/update',
    POSTDel = '/system/post/delete',

    //菜单管理
    MENULIST = '/system/menu/list',
    MENUID = '/system/menu/',
    MENUSA = '/system/menu/save',
    MENUUP = '/system/menu/update',
    MENUDel = '/system/menu/delete',
    MENU_GET_TREE = '/system/menu/roleMenuTreeselect',  //菜单角色权限获取

    //文件管理
    FILELIST = '/system/oss/list',
    FILEID = '/system/oss/',
    FILESUPLOAD = '/system/oss/upload',  //上传
    FILESDOWNLAD = '/system/oss/download/',  //下载
    FILEDEL = '/system/oss/delete',  //删除

    //文件配置
    FILECONFIGLIST = '/system/oss/config/list',
    FILECONFIGID = '/system/oss/config/',
    FILECONFIGSA = '/system/oss/config/save',
    FILECONFIGUP = '/system/oss/config/update',
    FILECONFIGDel = '/system/oss/config/delete',
    FILECONFIGSTATUS='/system/oss/config/changeStatus',  //修改状态


    
}
// 可自定义头部
// const headers={
//     'Content-Type': 'application/json;charset=UTF-8',
// }
// export const login = (data, params) => {
//     return Axios.get(Api.yam + data.times, params);
// }
// export const login = (data: LoginParams) => {
//     return Axios.post(Api.Login, data),headers;
// };
// export const sys_login = (data: LoginParams) => {
//     return Axios.post(Api.Login, data);
// };
export const sys_dict_type_list = (data) => {
    return Axios.get(ApiDict.DICTTYPELIST, data);
}
export const sys_dict_type_info = (data) => {
    return Axios.get(ApiDict.DICTTYPEINFO + data);
}
export const sys_dict_type = (data) => {
    return Axios.get(ApiDict.DICTTYPE + '?dictType=' + data);
}
export const sys_dict_type_sa = (data) => {
    return Axios.post(ApiDict.DICTTYPESA, data);
}
export const sys_dict_type_up = (data) => {
    return Axios.put(ApiDict.DICTTYPEUP, data);
}
export const sys_dict_type_del = (data) => {
    return Axios.delete(ApiDict.DICTTYPEDel + "?idList=" + data);
};


export const sys_dict_data_list = (data) => {
    return Axios.get(ApiDict.DICTDATALIST, data);
};
export const sys_dict_data_info = (data) => {
    return Axios.get(ApiDict.DICTDATAINFO + data);
}
export const sys_dict_data_type = (data) => {
    return Axios.get(ApiDict.DICTDATATYPE + '?dictType=' + data);
}
export const sys_dict_data_sa = (data) => {
    return Axios.post(ApiDict.DICTDATASA, data);
}
export const sys_dict_data_up = (data) => {
    return Axios.put(ApiDict.DICTDATAUP, data);
}
export const sys_dict_data_del = (data) => {
    return Axios.delete(ApiDict.DICTDATADel + "?idList=" + data);
};

// 用户管理
export const sys_user_list = (data) => {
    return Axios.get(ApiDict.USERLIST, data);
};
export const sys_user_dict_list = (data) => {
    return Axios.get(ApiDict.USERDICTLIST, data);
};
export const sys_user_id = (data) => {
    return Axios.get(ApiDict.USERID + data);
};
export const sys_user_sa = (data) => {
    return Axios.post(ApiDict.USERSA, data);
};
export const sys_user_up = (data) => {
    return Axios.put(ApiDict.USERUP, data);
};
export const sys_user_password_up = (data) => {
    return Axios.post(ApiDict.USERPASSWORDUP, data);
};
export const sys_user_password_reset = (data) => {
    return Axios.post(ApiDict.USERPASSWORDRESET, data);
}
export const sys_user_del = (data) => {
    return Axios.delete(ApiDict.USERDel + "?idList=" + data);
};
export const sys_user_avatar = (data) => {
    const headers = {
        'Content-Type': 'multipart/form-data'
    }
    return Axios.put(ApiDict.USERAVATAR , data,headers);
};

// 角色管理
export const sys_role_list = (data) => {
    return Axios.get(ApiDict.ROLELIST, data);
};
export const sys_role_dict_list = () => {
    return Axios.get(ApiDict.ROLEDICTLIST);
};
export const sys_role_id = (data) => {
    return Axios.get(ApiDict.ROLEID + data);
};
export const sys_role_sa = (data) => {
    return Axios.post(ApiDict.ROLESA, data);
};
export const sys_role_up = (data) => {
    return Axios.put(ApiDict.ROLEUP, data);
};
export const sys_role_del = (data) => {
    return Axios.delete(ApiDict.ROLEDel + "?idList=" + data);
};

//岗位管理
export const sys_post_list = (data) => {
    return Axios.get(ApiDict.POSTLIST, data);
};
export const sys_post_dict_list = () => {
    return Axios.get(ApiDict.POSTDICTLIST);
};
export const sys_post_id = (data) => {
    return Axios.get(ApiDict.POSTID + data);
};
export const sys_post_sa = (data) => {
    return Axios.post(ApiDict.POSTSA, data);
};
export const sys_post_up = (data) => {
    return Axios.put(ApiDict.POSTUP, data);
};
export const sys_post_del = (data) => {
    return Axios.delete(ApiDict.POSTDel + "?idList=" + data);
};

//菜单管理
export const sys_menu_list = (data) => {
    return Axios.get(ApiDict.MENULIST, data);
};
export const sys_menu_id = (data) => {
    return Axios.get(ApiDict.MENUID + data);
};
export const sys_menu_sa = (data) => {
    return Axios.post(ApiDict.MENUSA, data);
};
export const sys_menu_up = (data) => {
    return Axios.put(ApiDict.MENUUP, data);
};
export const sys_menu_del = (data) => {
    return Axios.delete(ApiDict.MENUDel + "?idList=" + data);
};
export const sys_menu_get_tree = (data?) => {
    return Axios.get(ApiDict.MENU_GET_TREE + "?roleId=" + data);
};

//文件管理
export const sys_file_list = (data) => {
    return Axios.get(ApiDict.FILELIST, data);
};
export const sys_file_id = (data) => {
    return Axios.get(ApiDict.FILEID + data);
};
export const sys_file_upload = (data) => {
    return Axios.post(ApiDict.FILESUPLOAD, data);
};
export const sys_file_downlad = (data:number) => {
    return Axios.download(ApiDict.FILESDOWNLAD + data);
};
export const sys_file_del = (data) => {
    return Axios.delete(ApiDict.FILEDEL + "?idList=" + data);
};

//文件配置
export const sys_file_config_list = (data) => {
    return Axios.get(ApiDict.FILECONFIGLIST, data);
};
export const sys_file_config_id = (data) => {
    return Axios.get(ApiDict.FILECONFIGID + data); 
};
export const sys_file_config_sa = (data) => {
    return Axios.post(ApiDict.FILECONFIGSA, data);
};
export const sys_file_config_up = (data) => {
    return Axios.put(ApiDict.FILECONFIGUP, data);  
};
export const sys_file_config_del = (data) => {
    return Axios.delete(ApiDict.FILECONFIGDel + "?idList=" + data);
};
export const sys_file_config_up_status = (data) => {
    return Axios.put(ApiDict.FILECONFIGSTATUS, data);  
};

