// 详情用法请参考：https://www.uviewui.com/js/http.html
const http = uni.$u.http

const API ={
	LOGIN : '/login',  //登录接口
	MANUALLOGIN:'/manualLogin',  //非首次注册登录
	LOGINUPAVATAR: '/upAvatar?avatar=', //非首次登录后进行头像地址更新
	LOGOUT:'/logout',  //退出登录
	USERLIST : '/user/list'
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

export const getUserList = (data) => http.get(API.USERLIST,data)