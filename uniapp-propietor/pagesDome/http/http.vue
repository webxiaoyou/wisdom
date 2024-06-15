<template>
    <view>
        <!-- 公共组件-每个页面必须引入 -->
        <public-module></public-module>
        <view class="titleBtn" @click="getShpInfoPost">启用uni.$u.http.post</view>
        <view class="titleBtn" @click="getShpInfoGet">启用uni.$u.http.get</view>
		<view class="value">
			{{value}}
		</view>
    </view>
</template>

<script>
import { mapState, mapMutations } from 'vuex';
import { postShopInfo, getShopInfo } from '@/config/api.js';

export default {
    data() {
        return {
			value:''
        }
    },
    onLoad() {

    },
    methods: {
		// post请求
        getShpInfoPost(){
            let httpData = {
                shopId: '1275'
            }
			// 直接使用
            // uni.$u.http.post('/edcapi/Shop_shopDeatil', httpData).then((res) => {
            // 	console.log(res,'shpInfo')
            // }).catch((err) =>{
            // 	console.log(err,'catch')
            // })
			
			// api集中管理使用
			postShopInfo(httpData).then((res) => {
				console.log(res,'shpInfo')
				this.value = JSON.stringify(res)
			}).catch((err) =>{
				console.log(err,'catch')
			})
        },
		// get请求
        getShpInfoGet(){
            let httpData = {
                shopId: '1275'
            }
			// 直接使用
            // uni.$u.http.get('/edcapi/Shop_shopDeatil', {params: httpData, custom: {isFactory:false}}).then((res) => {
            // 	console.log(res,'shpInfo')
            // }).catch((err) =>{
            // 	console.log(err,'catch')
            // })
			
			// api集中管理使用
			getShopInfo({params: httpData,  custom: {isFactory:false}}).then((res) => {
				console.log(res,'shpInfo')
				this.value = JSON.stringify(res)
			}).catch((err) =>{
				console.log(err,'catch')
			})
        }
    },
}
</script>

<style>
.titleBtn{
    margin: 24rpx;
    width: 702rpx;
    padding: 20rpx 24rpx;
    background-color: #007AFF;
    color: #fff;
    font-size: 24rpx;
    text-align: center;
}
.value{
	margin: 24rpx;
	color: #666;
	font-size: 24rpx;
}
</style>
