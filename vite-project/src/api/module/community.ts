import Http, { Axios } from '@/utils/axios';

enum ApiDict {

    //社区活动
    ACTIVITYLIST = '/pcommunity_activity/list', //列表
    ACTIVITYINFO = '/pcommunity_activity/',  //ID查询查询
    ACTIVITYSA = '/pcommunity_activity/save', //新增
    ACTIVITYUP = '/pcommunity_activity/update', //修改
    DICTTYPEDel = '/pcommunity_activity/delete',

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
    return Axios.delete(ApiDict.DICTTYPEDel + "?idList=" + data);
};
