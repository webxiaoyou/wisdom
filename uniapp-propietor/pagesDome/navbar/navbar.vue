<template>
    <view class="content">
        <!-- 公共组件-每个页面必须引入 -->
        <public-module></public-module>
        <f-navbar title="导航栏展示列表" navbarType='3'></f-navbar>
        <view class="name">导航展示dome</view>
        <view class="cell-group">
            <view class="cell-item u-flex" @click="onJump('/pagesDome/navbar/navbar1')">
                <view class="title u-flex-m">导航基础使用</view>
                <u-icon name="arrow-right" size="14" color="#333"></u-icon>
            </view>
            <view class="cell-item u-flex" @click="onJump('/pagesDome/navbar/navbar2')">
                <view class="title u-flex-m">不固定导航</view>
                <u-icon name="arrow-right" size="14" color="#333"></u-icon>
            </view>
            <view class="cell-item u-flex" @click="onJump('/pagesDome/navbar/navbar3')">
                <view class="title u-flex-m">上拉渐变显示navbar背景</view>
                <u-icon name="arrow-right" size="14" color="#333"></u-icon>
            </view>
			<view class="cell-item u-flex" @click="onJump('/pagesDome/navbar/navbar8')">
			    <view class="title u-flex-m">上拉渐变显示navbar白色背景</view>
			    <u-icon name="arrow-right" size="14" color="#333"></u-icon>
			</view>
            <view class="cell-item u-flex" @click="onJump('/pagesDome/navbar/navbar4')">
                <view class="title u-flex-m">自定义左侧按钮事件</view>
                <u-icon name="arrow-right" size="14" color="#333"></u-icon>
            </view>
            <view class="cell-item u-flex" @click="onJump('/pagesDome/navbar/navbar5')">
                <view class="title u-flex-m">自定义左侧插槽slot</view>
                <u-icon name="arrow-right" size="14" color="#333"></u-icon>
            </view>
            <view class="cell-item u-flex" @click="onJump('/pagesDome/navbar/navbar6')">
                <view class="title u-flex-m">导航背景渐变</view>
                <u-icon name="arrow-right" size="14" color="#333"></u-icon>
            </view>
            <view class="cell-item u-flex" @click="onJump('/pagesDome/navbar/navbar7')">
                <view class="title u-flex-m">导航栏图片背景</view>
                <u-icon name="arrow-right" size="14" color="#333"></u-icon>
            </view>
        </view>
    </view>
</template>

<script>
import fNavbar from '@/components/module/f-navbar/f-navbar';
export default {
    components:{
        fNavbar
    },
    data() {
        return {
            scrollTop:0
        }
    },
    onLoad() {

    },
    methods: {
        onJump(url){
            uni.navigateTo({
                url:url
            })
        }
    },
}
</script>

<style scoped lang="scss">
.name{
    padding: 24rpx;
    font-size: 32rpx;
    font-weight: bold;
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
