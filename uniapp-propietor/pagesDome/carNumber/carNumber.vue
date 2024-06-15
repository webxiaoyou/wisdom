<template>
    <view>
        <!-- 公共组件-每个页面必须引入 -->
        <public-module></public-module>
        <view class="content">
            <view class="title">车牌号</view>
            <f-car-number @keyboardChange="keyboardChange" @backspace="backspace"></f-car-number>
        </view>
        
    </view>
</template>

<script>
import { mapState, mapMutations } from 'vuex';
import fCarNumber from '@/components/module/f-car-number/f-car-number';
export default {
    components:{
        fCarNumber
    },
    data() {
        return {
            carId:[],
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
        keyboardChange(e){
            console.log(e)
        },
        backspace(e){
            console.log(e)
        },
    }
};
</script>
<style lang="scss" scoped>
.content{
    margin-top: 30rpx;
    padding: 40rpx 30rpx;
    background-color: #fff;
}
.title{
    padding-bottom: 20rpx;
    color: #333;
    font-size: 32rpx;
}
</style>
