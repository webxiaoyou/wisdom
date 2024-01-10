<template>
	<view class="content">
        <!-- 公共组件-每个页面必须引入 -->
        <public-module></public-module>
        <view class="u-flex">
            <view class="name">功能入口</view>
            <view class="link" @longpress="copy('https://ext.dcloud.net.cn/plugin?id=7164')">长按复制链接去下载源码</view>
        </view>
        <view class="cell-group">
            <view class="cell-item u-flex" @click="onJump(item.url)"
            :style="[{animation: 'show  ' + ((index+1)*0.2+.2) + 's'}]" v-for="(item,index) in list" :key="index">
                <view class="title u-flex-m">{{item.title}}</view>
                <u-icon name="arrow-right" size="14" color="#333"></u-icon>
            </view>
        </view>

        <!-- #ifdef MP-WEIXIN -->
		<view class="u-p-t-50">
			<f-ad ref="FAD" :typeArray="[1]" @excitationAdCallback="excitationAdCallback"></f-ad>
		</view>
        <!-- #endif -->
        <f-tabbar></f-tabbar>
	</view>
</template>

<script>
import fTabbar from '@/components/module/f-tabbar/f-tabbar';
// #ifdef MP-WEIXIN
// 小程序流量主
import fAd from '@/components/module/f-ad/f-ad';
// #endif
export default {
    components:{
        fTabbar,
        // #ifdef MP-WEIXIN
        fAd,
        // #endif
    },
    data() {
        return {
            list:[{
                title:'canvas万能方法，简单易用',
                url:'/pagesDome/canvas/canvas'
            },{
                title:'sticky吸顶组件，组件中用也不会失灵',
                url:'/pagesDome/sticky/sticky'
            },{
                title:'showModal自定义，只兼容app',
                url:'/pagesDome/showModal/showModal'
            },{
                title:'微信、支付宝小程序手机号个人信息授权登录、验证码登录、静默登录',
                url:'/pagesDome/login/login'
            },{
                title:'uni.$u.http接口示例',
                url:'/pagesDome/http/http'
            },{
                title:'自定义导航栏navbar',
                url:'/pagesDome/navbar/navbar'
            },{
                title:'万能支付',
                url:'/pagesDome/pay/pay'
            },{
                title:'mescroll下拉刷新上拉加载分页',
                url:'/pagesDome/mescroll/mescroll'
            },{
                title:'加入购物车抛物线动画',
                url:'/pagesDome/cartAnimation/cartAnimation'
            },{
                title:'获取定位信息',
                url:'/pagesDome/getLocation/getLocation'
            },{
                title:'车牌号输入+键盘+新能源',
                url:'/pagesDome/carNumber/carNumber'
            },{
                title:'签名（横屏）',
                url:'/pagesDome/signature/signature'
            },{
                title:'微信流量主',
                url:'/pagesDome/wxAd/wxAd'
            }],
        }
    },
    onLoad() {
        // 隐藏原生的tabbar
        uni.hideTabBar();
    },
    methods: {
        onJump(url){
            uni.navigateTo({
                url:url
            })
        },
        // 拷贝
        copy(notice) {
            uni.setClipboardData({
                data: notice,
                success(res) {
                    uni.showToast({
                        title: '复制成功',
                        duration: 1000
                    })
                }
            })
        }
    },

}
</script>

<style lang="scss" scoped>
.name{
    padding: 24rpx;
    font-size: 32rpx;
    font-weight: bold;
}
.link{
    font-size: 24rpx;
    color: #007AFF;
}
.cell-group{
    .cell-item{
        border-bottom: 2rpx solid #eee;
        background-color: #fff;
        border-radius: 10rpx;
        padding: 20rpx 24rpx;
        .title{
            color: #333;
            font-size: 28rpx;
            padding: 0 10rpx;
            font-weight: bold;
            margin: 0;
        }
        .more{
            font-size: 24rpx;
            color: #999;
        }
    }
}
</style>
