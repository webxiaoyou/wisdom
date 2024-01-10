<template>
    <view>
		<!-- #ifdef MP-WEIXIN -->
		<view style="padding: 24rpx;">
		    <button type="primary" class="btn" @click="showExcitationAd">显示激励视频广告</button>
		</view>
		<view style="padding: 24rpx;">
		    <button type="primary" class="btn" @click="showInsertScreenAd">显示插屏广告</button>
		</view>
		<f-ad ref="FAD" :typeArray="[1,2,3,4,5,6]" @excitationAdCallback="excitationAdCallback"></f-ad>
		<!-- #endif -->
		
		<!-- #ifndef MP-WEIXIN -->
		<view style="padding: 24px;">请在微信小程序打开</view>
		<!-- #endif -->
    </view>
</template>

<script>
import { mapState, mapMutations } from 'vuex';
import base from '@/config/baseUrl';
// #ifdef MP-WEIXIN
// 小程序广告等待作者小程序累计流量超1000开放
import fAd from '@/components/module/f-ad/f-ad';
// #endif
export default {
	components:{
	    // #ifdef MP-WEIXIN
	    fAd,
	    // #endif
	},
    data() {
        return {
            baseUrl:base.baseUrl,
            systemInfo:base.systemInfo,

        }
    },
    computed:{
        ...mapState(['userInfo'])
    },
    onLoad() {

    },
    methods: {
		// 显示激励广告
		showExcitationAd(){
		    this.$refs.FAD.showExcitationAd()
		},
		// 激励广告回调
		excitationAdCallback(e){
		    if(e==1){
		        console.log('激励回调--播放完成')
		    }else if(e==2){
		        console.log('激励回调--中途退出')
		    }
		},
		// 显示插屏广告
		showInsertScreenAd(){
		    this.$refs.FAD.showInsertScreenAd()
		},
        
    },

}
</script>

<style lang="scss" scoped>

</style>