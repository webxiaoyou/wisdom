<template>
    <view class="page">
        <!-- 公共组件-每个页面必须引入 -->
        <public-module></public-module>
        <view class="name">mescroll分页</view>
        <view class="cell-group">
            <view class="cell-item u-flex" @click="onJump('/pagesDome/mescroll/mescrollBody')">
                <view class="title u-flex-m">mescroll-body分页基础使用</view>
                <u-icon name="arrow-right" size="14" color="#333"></u-icon>
            </view>
            <view class="cell-item u-flex" @click="onJump('/pagesDome/mescroll/mescrollUni')">
                <view class="title u-flex-m">mescroll-uni tab+swiper切换分页</view>
                <u-icon name="arrow-right" size="14" color="#333"></u-icon>
            </view>
        </view>
    </view>
</template>

<script>
export default {
    data() {
        return {

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
