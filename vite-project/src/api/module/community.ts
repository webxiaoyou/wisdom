import Http, { Axios } from '@/utils/axios';

enum ApiDict {

    //社区活动
    ACTIVITYLIST = '/pcommunity_activity/list', //列表
    ACTIVITYINFO = '/pcommunity_activity/',  //ID查询查询
    ACTIVITYSA = '/pcommunity_activity/save', //新增
    ACTIVITYUP = '/pcommunity_activity/update', //修改
    ACTIVITYDel = '/pcommunity_activity/delete', //删除
    
    //访客记录
    VISITORLIST = '/pvisitor_invitation/list', //列表
    VISITORINFO = '/pvisitor_invitation/',  //ID查询查询
    VISITORSA = '/pvisitor_invitation/save', //新增
    VISITORUP = '/pvisitor_invitation/update', //修改
    VISITORUDel='/pvisitor_invitation/delete' //删除



}


//社区活动
export const cmy_activity_list = (data) => {
    return Axios.get(ApiDict.ACTIVITYLIST, data);
};
export const cmy_activity_info = (data) => {
    return Axios.get(ApiDict.ACTIVITYINFO + data); 
};
export const cmy_activity_sa = (data) => {
    return Axios.post(ApiDict.ACTIVITYSA, data);
};
export const cmy_activity_up = (data) => {
    return Axios.put(ApiDict.ACTIVITYUP, data);  
};
export const cmy_activity_del = (data) => {
    return Axios.delete(ApiDict.ACTIVITYDel + "?idList=" + data);
};

//访客记录
export const cmy_visitor_list = (data) => {
    return Axios.get(ApiDict.VISITORLIST, data);
};
export const cmy_visitor_info = (data) => {
    return Axios.get(ApiDict.ACTIVITYINFO + data); 
};
export const cmy_visitor_sa = (data) => {
    return Axios.post(ApiDict.ACTIVITYSA, data);
};
export const cmy_visitor_up = (data) => {
    return Axios.put(ApiDict.ACTIVITYUP, data);  
};
export const cmy_visitor_del = (data) => {
    return Axios.delete(ApiDict.ACTIVITYDel + "?idList=" + data);
};