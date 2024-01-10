<template>
    <view>
        <!-- 公共组件-每个页面必须引入 -->
        <public-module></public-module>
        <view class="titleBtn" @click="openLogin">打开登录弹窗</view>
    </view>
</template>

<script>
import { mapState, mapMutations } from 'vuex';
export default {
    data() {
        return {
        }
    },
    onLoad() {

    },
    methods: {
        ...mapMutations(['setLoginPopupShow']),
        openLogin(){
            this.setLoginPopupShow(true)
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
</style>
