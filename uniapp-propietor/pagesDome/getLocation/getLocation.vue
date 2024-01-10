<template>
    <view>
        <!-- 公共组件-每个页面必须引入 -->
        <public-module></public-module>
        <view>
            <view class="box" v-if="address">address:{{address}}</view>
            <view class="box" v-if="location.lat">location:{{location.lat}},{{location.lng}}</view>
            <view class="box" v-if="adcode">当前区号:{{adcode}}</view>
        </view>
        <view class="titleBtn" @click="getAllLocation">获取定位</view>
    </view>
</template>

<script>
import { mapState, mapMutations } from 'vuex';
import { loGetLocation } from '@/config/common';
export default {
    data() {
        return {
            address:'',//地址
            location:{},//坐标
            adcode:'',//区号
        };
    },
    //第一次加载
    onLoad(e) {
        
    },
    //页面显示
    onShow() {},
    //方法
    methods: {
        onJump(url){
            uni.navigateTo({
                url:url
            })
        },
        getAllLocation(){
            //获取定位信息
            loGetLocation(res=>{
                console.log(res,'loGetLocation')
                if(res.ad_info){
                    // 地址逆解析必须配置您的腾讯地图key和sk
                    this.location = res.location
                    this.address = res.address
                    this.adcode = res.ad_info.adcode
                }else{
                    this.location = {
                        lat:res.latitude,
                        lng:res.longitude,
                    }
                }
            },err=>{},true) 
        }
    }
};
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
.box{
    padding: 10rpx 24rpx;
    color: #333;
}
</style>
