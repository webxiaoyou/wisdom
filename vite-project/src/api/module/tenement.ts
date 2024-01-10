import Http, { Axios } from '@/utils/axios';

   enum ApiDict {
   //小程序用户配置
    USERCONFIGLIST = '/tenement/puser/pconfig/list',
    USERCONFIGID = '/tenement/puser/pconfig/',
    USERCONFIGSA = '/tenement/puser/pconfig/save',
    USERCONFIGUP = '/tenement/puser/pconfig/update',
    USERCONFIGDel = '/tenement/puser/pconfig/delete',

    //小程序用户信息表
    PUSERLIST = '/tenement/puser/list',
    PUSERID = '/tenement/puser/',
    PUSERSA = '/tenement/puser/save',
    PUSERUP = '/tenement/puser/update',
    PUSERDel = '/tenement/puser/delete',


    //物业小区
     PPROPERTYLIST = '/tenement/pproperty/list',
     PPROPERTYID = '/tenement/pproperty/',
     PPROPERTYSA = '/tenement/pproperty/save',
     PPROPERTYUP = '/tenement/pproperty/update',
     PPROPERTYDel = '/tenement/pproperty/delete',
     PPROPERTDICTLIST ='/tenement/pproperty/dict/list',


     //楼栋管理
     PBUILDINGLIST = '/pbuilding/list',
     PBUILDINGID = '/pbuilding/',
     PBUILDINGSA = '/pbuilding/save',
     PBUILDINGUP = '/pbuilding/update',
     PBUILDINGDel = '/pbuilding/delete',
     PBUILDINDICTLIST ='/pbuilding/dict/list',


     //楼层管理
     PFLOORLIST = '/pfloor/list',
     PFLOORID = '/pfloor/',
     PFLOORSA = '/pfloor/save',
     PFLOORUP = '/pfloor/update',
     PFLOORDel = '/pfloor/delete',

     //业主信息
     PPROPERTYUNITLIST = '/pproperty_unit/list',
     PPROPERTYUNITID = '/pproperty_unit/',
     PPROPERTYUNITSA = '/pproperty_unit/save',
     PPROPERTYUNITUP = '/pproperty_unit/update',
     PPROPERTYUNITDel = '/pproperty_unit/delete',
     PPROPERTYUNITDICTLIST ='/pproperty_unit/dict/list',

    //物业通知
     PNOTICELIST = '/pnotification/list',
     PNOTICEID = '/pnotification/',
     PNOTICESA = '/pnotification/save',
     PNOTICEUP = '/pnotification/update',
     PNOTICEDel = '/pnotification/delete',

     //物业报修
     PREPAIRLIST = '/prepair/list',
     PREPAIRID = '/prepair/',
     PREPAIRSA = '/prepair/save',
     PREPAIRUP = '/prepair/update',
     PREPAIRDel = '/prepair/delete',

     //投诉建议
     PCOMPLAINLIST = '/pcomplaint_suggestion/list',
     PCOMPLAINID = '/pcomplaint_suggestion/',
     PCOMPLAINSA = '/pcomplaint_suggestion/save',
     PCOMPLAINUP = '/pcomplaint_suggestion/update',
     PCOMPLAINDel = '/pcomplaint_suggestion/delete',

   }




   //小程序用户配置
export const sys_user_config_list = (data) => {
    return Axios.get(ApiDict.USERCONFIGLIST, data);
};
export const sys_user_config_id = (data) => {
    return Axios.get(ApiDict.USERCONFIGID + data);
};
export const sys_user_config_sa = (data) => {
    return Axios.post(ApiDict.USERCONFIGSA, data);
};
export const sys_user_config_up = (data) => {
    return Axios.put(ApiDict.USERCONFIGUP, data);
};
export const sys_user_config_del = (data) => {
    return Axios.delete(ApiDict.USERCONFIGDel + "?idList=" + data);
};

//小程序用户信息表
export const sys_puser_info_list = (data) => {
    return Axios.get(ApiDict.PUSERLIST, data);
};
export const sys_puser_info_id = (data) => {
    return Axios.get(ApiDict.PUSERID + data);
};
export const sys_puser_info_sa = (data) => {
    return Axios.post(ApiDict.PUSERSA, data);
};
export const sys_puser_info_up = (data) => {
    return Axios.put(ApiDict.PUSERUP, data);
};
export const sys_puser_info_del = (data) => {
    return Axios.delete(ApiDict.PUSERDel + "?idList=" + data);
};

//物业小区
export const sys_pproperty_list = (data) => {
    return Axios.get(ApiDict.PPROPERTYLIST, data);
};
export const sys_pproperty_id = (data) => {
    return Axios.get(ApiDict.PPROPERTYID + data);
};
export const sys_pproperty_sa = (data) => {
    return Axios.post(ApiDict.PPROPERTYSA, data);
};
export const sys_pproperty_up = (data) => {
    return Axios.put(ApiDict.PPROPERTYUP, data);
};
export const sys_pproperty_del = (data) => {
    return Axios.delete(ApiDict.PPROPERTYDel + "?idList=" + data);
};
export const sys_pproperty_dict_id = (data='') => {
    return Axios.get(ApiDict.PPROPERTDICTLIST + data);
};

//楼栋管理
export const tmt_pbuilding_list = (data) => {
    return Axios.get(ApiDict.PBUILDINGLIST, data);
};
export const tmt_pbuilding_id = (data) => {
    return Axios.get(ApiDict.PBUILDINGID + data);
};
export const tmt_pbuilding_sa = (data) => {
    return Axios.post(ApiDict.PBUILDINGSA, data);
};
export const tmt_pbuilding_up = (data) => {
    return Axios.put(ApiDict.PBUILDINGUP, data);
};
export const tmt_pbuilding_del = (data) => {
    return Axios.delete(ApiDict.PBUILDINGDel + "?idList=" + data);
};
export const tmt_pbuilding_dict_id = (data={}) => {
    return Axios.get(ApiDict.PBUILDINDICTLIST , data);
};

//楼层管理
export const tmt_pfloor_list = (data) => {
    return Axios.get(ApiDict.PFLOORLIST, data);
};
export const tmt_pfloor_id = (data) => {
    return Axios.get(ApiDict.PFLOORID + data);
};
export const tmt_pfloor_sa = (data) => {
    return Axios.post(ApiDict.PFLOORSA, data);
};
export const tmt_pfloor_up = (data) => {
    return Axios.put(ApiDict.PFLOORUP, data);
};
export const tmt_pfloor_del = (data) => {
    return Axios.delete(ApiDict.PFLOORDel + "?idList=" + data);
};

//业主信息
export const tmt_pproperty_unit_list = (data) => {
    return Axios.get(ApiDict.PPROPERTYUNITLIST, data);
};
export const tmt_pproperty_unit_id = (data) => {
    return Axios.get(ApiDict.PPROPERTYUNITID + data);   
};
export const tmt_pproperty_unit_sa = (data) => {
    return Axios.post(ApiDict.PPROPERTYUNITSA, data);
};
export const tmt_pproperty_unit_up = (data) => {
    return Axios.put(ApiDict.PPROPERTYUNITUP, data);
};
export const tmt_pproperty_unit_del = (data) => {
    return Axios.delete(ApiDict.PPROPERTYUNITDel + "?idList=" + data);
};
export const tmt_pproperty_unit_dict_list = (data) => {
    return Axios.get(ApiDict.PPROPERTYUNITDICTLIST,data);
}


//物业通知
export const tmt_pnotice_list = (data) => {
    return Axios.get(ApiDict.PNOTICELIST, data);
};
export const tmt_pnotice_id = (data) => {
    return Axios.get(ApiDict.PNOTICEID + data);
};
export const tmt_pnotice_sa = (data) => {
    return Axios.post(ApiDict.PNOTICESA, data);
};
export const tmt_pnotice_up = (data) => {
    return Axios.put(ApiDict.PNOTICEUP, data);
};
export const tmt_pnotice_del = (data) => {
    return Axios.delete(ApiDict.PNOTICEDel + "?idList=" + data);
};


//物业报修
export const tmt_prepair_list = (data) => {
    return Axios.get(ApiDict.PREPAIRLIST, data);
};
export const tmt_prepair_id = (data) => {
    return Axios.get(ApiDict.PREPAIRID + data);
};
export const tmt_prepair_sa = (data) => {
    return Axios.post(ApiDict.PREPAIRSA, data);
};
export const tmt_prepair_up = (data) => {
    return Axios.put(ApiDict.PREPAIRUP, data);
};
export const tmt_prepair_del = (data) => {
    return Axios.delete(ApiDict.PREPAIRDel + "?idList=" + data);
};

//投诉建议
export const tmt_pcomplaint_list = (data) => {
    return Axios.get(ApiDict.PCOMPLAINLIST, data);
};
export const tmt_pcomplaint_id = (data) => {
    return Axios.get(ApiDict.PCOMPLAINID + data);
};
export const tmt_pcomplaint_sa = (data) => {
    return Axios.post(ApiDict.PCOMPLAINSA, data);
};
export const tmt_pcomplaint_up = (data) => {
    return Axios.put(ApiDict.PCOMPLAINUP, data);
};
export const tmt_pcomplaint_del = (data) => {
    return Axios.delete(ApiDict.PCOMPLAINDel + "?idList=" + data);
};