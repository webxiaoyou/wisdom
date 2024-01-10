import { getTabbarHeight } from '@/plugins/utils';
let baseUrl = "";
if (process.env.NODE_ENV === 'development') {
	// 开发环境
	// baseUrl = 'https://api.yichengshi.cn'
    baseUrl = 'http://localhost:7777/wisdom/proprietor'
} else if (process.env.NODE_ENV === 'production') {
	// 生产环境
	baseUrl = 'https://api.yichengshi.cn'
}

let systemInfo = {
    ...getTabbarHeight(),
    // #ifdef MP-ALIPAY
    navBarH: uni.getSystemInfoSync().statusBarHeight + uni.getSystemInfoSync().titleBarHeight, //菜单栏总高度--单位px
    titleBarHeight: uni.getSystemInfoSync().titleBarHeight, //标题栏高度--单位px
    // #endif
    // #ifndef MP-ALIPAY
    navBarH: uni.getSystemInfoSync().statusBarHeight + 44, //菜单栏总高度--单位px
    titleBarHeight: 44, //标题栏高度--单位px
    // #endif
};
// 平台
// #ifdef MP-WEIXIN
systemInfo.platform = 'weixin'
// #endif
// #ifdef MP-ALIPAY
systemInfo.platform = 'alipay'
// #endif
// #ifdef MP-TOUTIAO
systemInfo.platform = 'toutiao'
// #endif
// #ifdef APP-PLUS
systemInfo.platform = 'plus'
// #endif
console.log(systemInfo,'systemInfo')
const courtConfig = {
    publicAppId:'wx4884cd92eb835a6d',//公众号appId
	baseUrl: baseUrl,//域名
    systemInfo:systemInfo,//系统信息
    mapData:{
        key:'',//地图key
        sk:'',
    },
    share: {
    	title: '基于uview2.0快速开发框架',    //分享标题
    	desc: "基于uview2.0快速开发框架详情",  //分享详情
    	link: "", // 分享链接
    	imgUrl: "", // 分享图
    }
};
export default Object.assign({}, courtConfig);
