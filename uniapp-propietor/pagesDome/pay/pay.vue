<template>
    <view>
        <!-- 公共组件-每个页面必须引入 -->
        <public-module></public-module>
        <view class="titleBtn" @click="onWxPay">微信小程序支付</view>
        <view class="titleBtn" @click="onAliPay">支付宝支付</view>
        <view class="titleBtn" @click="onToutiaoPay">头条小程序支付</view>
        <view class="titleBtn" @click="onAppWxPay">app微信支付</view>
        <view class="titleBtn" @click="onH5WxPay">H5微信公众号支付</view>
        <view class="titleBtn" @click="openPay">打开支付弹窗(支持钱包支付)</view>
        
        <u-popup :show="isPayPopup" mode="bottom"  @close="isPayPopup=false" round="10">
            <f-pay :show="isPayPopup" :payMoney="999" :tradeNo="tradeNo"></f-pay>
        </u-popup>
    </view>
</template>

<script>
import { mapState, mapMutations } from 'vuex';
import { setPay,wxPublicPay } from '@/config/pay'; //支付需进入设置您的支付接口
import fPay from '@/components/module/f-pay/f-pay';
export default {
    components:{
        fPay
    },
    data() {
        return {
            tradeNo:'16fw61cqew6464wdc',//支付单号
            isPayPopup:false,
        }
    },
    onLoad() {

    },
    methods: {
        // 微信小程序支付
        onWxPay(){
            // #ifdef MP-WEIXIN
            this.onPay('mpwxpay',this.tradeNo)
            // #endif
            // #ifndef MP-WEIXIN
            this.$u.toast('微信小程序才能用哦~')
            // #endif
        },
        // 支付宝小程序支付
        onAliPay(){
            // #ifdef MP-ALIPAY || APP
            this.onPay('alipay',this.tradeNo)
            // #endif
            // #ifndef MP-ALIPAY || APP
            this.$u.toast('支付宝小程序或APP才能用哦~')
            // #endif
        },
        // 头条小程序支付
        onToutiaoPay(){
            // #ifdef MP-TOUTIAO
            this.onPay('toutiao',this.tradeNo)
            // #endif
            // #ifndef MP-TOUTIAO
            this.$u.toast('头条小程序才能用哦~')
            // #endif
        },
        // app微信支付
        onAppWxPay(){
            // #ifdef APP-PLUS
            this.onPay('wxpay',this.tradeNo)
            // #endif
            // #ifndef APP-PLUS
            this.$u.toast('app才能用哦~')
            // #endif
        },
        // 微信公众号支付
        onH5WxPay(){
            var that = this
            // #ifdef H5
            wxPublicPay({
                tradeNo:that.tradeNo,
            },res => {
                // 支付的回调
                if(res.success){
                    uni.$u.toast('支付成功')
                }else{
                    uni.$u.toast('支付失败')
                }
            })
            // #endif
            // #ifndef H5
            this.$u.toast('微信公众号才能用哦~')
            // #endif
        },
        //支付(app、小程序)
        onPay(type,tradeNo){
        	setPay({
        		type: type,  // 支付平台
        		tradeNo: tradeNo // 订单编号
        	},res => {
        		// 小程序支付的回调
        		if(res.success){
                    uni.$u.toast('支付成功')
        		}else{
                    uni.$u.toast('支付失败')
        		}
        	})
        },
        openPay(){
            this.isPayPopup = true
        }
    },
}
</script>

<style lang="scss" scoped>
.titleBtn{
    margin: 24rpx;
    width: 702rpx;
    padding: 20rpx 24rpx;
    background-color: #007AFF;
    color: #fff;
    font-size: 24rpx;
    text-align: center;
}
</style>
