<template>
    <view>
        <!-- 公共组件-每个页面必须引入 -->
        <public-module></public-module>
        <view class="tip">加入购物车抛物线动画，左右抛物线适配动画，自定义掉落点坐标，曲线流畅，适合多平台</view>
        <view class="item">
            <view class="btn" @click="onPlay($event)">左边动画</view>
            <view class="btn red" @click="onPlay($event)">右边动画</view>
        </view>
        <view class="item">
            <view class="btn" @click="onCustomPlay($event)">左边动画</view>
            <view class="btn red" @click="onCustomPlay($event)">右边动画</view>
        </view>

        <!-- 加入购物车动画 -->
        <cart-animation ref="cartAnimation"></cart-animation>
    </view>
</template>

<script>
import cartAnimation from '@/components/module/f-cartAnimation/f-cartAnimation'
export default {
    components:{
        cartAnimation
    },
    data() {
        return {
            // 自定义掉落点坐标(px)
            busPos:{
                x:uni.getSystemInfoSync().windowWidth - 190,
                y:uni.getSystemInfoSync().windowHeight + 100
            }
        }
    },
    onLoad() {

    },
    methods: {
        onPlay(event){
            this.$refs.cartAnimation.touchOnGoods(event);
        },
        // 自定义掉落坐标
        onCustomPlay(event){
            this.$refs.cartAnimation.touchOnGoods(event,this.busPos);
        },
    },
}
</script>

<style>
.tip{
    padding: 32rpx;
    font-size: 28rpx;
    color: #666;
}
.item{
    display: flex;
    justify-content: space-between;
}
.btn{
    margin: 32rpx;
    width: 140rpx;
    height: 80rpx;
    line-height: 80rpx;
    text-align: center;
    color: #fff;
    font-size: 28rpx;
    background-color: #007AFF;
    border-radius: 10rpx;
}
.red{
    background-color: red;
}
</style>
