// 详情用法请参考：https://www.uviewui.com/js/http.html
const http = uni.$u.http

const API ={
	LOGIN : '/login',  //登录接口
	MANUALLOGIN:'/manualLogin',  //非首次注册登录
	LOGINUPAVATAR: '/upAvatar?avatar=', //非首次登录后进行头像地址更新
	LOGOUT:'/logout',  //退出登录
	USERLIST : '/user/list' ,//获取用户列表
	PCOMMUNITYACTIVITYLIST:'/pcommunity_activity/list', //获取社区活动列表
	PCOMMUNITYACTIVITYID:'/pcommunity_activity', //获取社区活动列表
	AUTHENTICATION:'/user/authentication', //用户认证
	AUTHENTICATIONUNIT:'/user/authentication/unit', //业主认证
	AUTHENTICATIONUNITID:'/user/authentication/unit', //获取业主认证信息
	PROPERTYDICTLIST:'/user/config/property/dict/list', //获取小区字典
	BUILDINGDICTLIST:'/user/config/building/dict/list', //获取小区栋数字典
    FLOORDICTLIST:'/user/config/floor/dict/list',  //获取小区楼层字典
	SUGGESTIONADD:'/user/suggestion/add', //投诉/建议
	PREPAIRLIST:'/prepair/list' , //报修列表查询
	PREPAIRADD:'/prepair/save', //添加报修
	PREPAIRID:'/prepair', //报修详情
	SYSUSERID:'/user/system/user', //报修详情
	SYSDICTDATA:'/user/config/system/dict/data', //字典数据内容
	NOTIFICATIONLIST:'/pnotification/list', //物业消息通知
	NOTIFICATIONID:'/pnotification', //物业消息通知
	PPROPERTYUNITLIST:'/pproperty_unit/list',  //楼房信息
	PPROPERTYUNITID:'/pproperty_unit',  //楼房信息
	PVISITORINITATIONCODE:'/pvisitor_invitation/code' //获取邀请码
}

// post请求，获取菜单
export const postShopInfo = (params, config = {}) => http.post('/edcapi/Shop_shopDeatil', params, config)
// get请求，获取菜单，注意：get请求的配置等，都在第二个参数中，详见前面解释
export const getShopInfo = (data) => http.get('/edcapi/Shop_shopDeatil', data)

/**
 * 登录接口
 */
export const postUserLogin = (params, config = {}) => http.post(API.LOGIN,params)

/**
 * 非首次注册登录
 */
export const postUserManualLogin = (params, config = {}) => http.post(API.MANUALLOGIN,params)

/**
 * 更新登录过程中的头像信息
 */
export const getUserLoginUpAvatar = (data,config) => http.get(API.LOGINUPAVATAR+data.url+'&id='+data.id+'&ossId='+data.ossId)

/**
 * 退出登录
 */
export const postUserLogout = (params, config = {}) => http.post(API.LOGOUT,params)

/**
 * 获取用户列表
 */
export const getUserList = (data) => http.get(API.USERLIST,data)

/**
 * 获取社区活动
 */
export const getPCActivityList = (data) => http.get(API.PCOMMUNITYACTIVITYLIST,data)

/**
 * 业主认证详情
 */
export const getPCActivityId = (data) => http.get(API.PCOMMUNITYACTIVITYID+'/'+data)

/**
 * 用户认证
 */
export const postUserAuthentication = (params, config = {}) => http.post(API.AUTHENTICATION,params)

/**
 * 业主认证
 */
export const postUserAuthenticationUnit = (params, config = {}) => http.post(API.AUTHENTICATIONUNIT,params)

/**
 * 业主认证详情
 */
export const getUserAuthenticationUnitId = (data) => http.get(API.AUTHENTICATIONUNITID+'/'+data)


/**
 * 获取小区字典
 */
export const getProperTydictList = (data) => http.get(API.PROPERTYDICTLIST,data)

/**
 * 获取小区栋数字典
 */
export const getBuildingDictList = (data) => http.get(API.BUILDINGDICTLIST,data)

/**
 * 获取小区楼层字典
 */
export const getFloorDictList = (data) => http.get(API.FLOORDICTLIST,data)

/**
 * 投诉
 */
export const postSuggestionAdd = (params, config = {}) => http.post(API.SUGGESTIONADD,params)

/**
 * 获取报修数据内容
 */
export const getPreairList = (data) => http.get(API.PREPAIRLIST,data)

/**
 * 添加报修
 */
export const postPreairAdd = (params, config = {}) => http.post(API.PREPAIRADD,params)

/**
 * 获取字典内容
 */
export const getSysDictData = (data) => http.get(API.SYSDICTDATA+'/'+data)

/**
 * 报修详情
 */
export const getPreairId = (data) => http.get(API.PREPAIRID+'/'+data)

/**
 * 报修师傅信息
 */
export const getSysUserId = (data) => http.get(API.SYSUSERID+'/'+data)

/**
 * 获取物业通知内容
 */
export const getNotificationList = (data) => http.get(API.NOTIFICATIONLIST,data)

/**
 * 获取物业通知详情
 */
export const getNotificationId = (data) => http.get(API.NOTIFICATIONID+'/'+data)


/**
 * 获取楼房信息
 */
export const getPpropertyUnitList = (data) => http.get(API.PPROPERTYUNITLIST,data)

/**
 * 获取物业通知详情
 */
export const getPpropertyUnitId = (data) => http.get(API.PPROPERTYUNITID+'/'+data)

/**
 * 添加报修
 */
export const postPvisitorInvitationCode = (params, config = {}) => http.post(API.PVISITORINITATIONCODE,params)


