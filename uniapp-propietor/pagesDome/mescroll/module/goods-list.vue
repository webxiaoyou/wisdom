<template>
    <view>
        <view class="goodsBox u-flex" v-for="(item,index) in list" :key="index">
            <image class="goodsImg" :src="item.goodsImage"></image>
            <view class="info u-flex u-flex-m">
                <view class="title">{{item.goodsName}}</view>
                <view class="desc u-line-1">{{item.goodsDescription || '暂无详情'}}</view>
            </view>
        </view>
    </view>
</template>

<script>
export default {
    props:{
        list:{
            type:Array,
            default:[]
        }
    },
    data() {
        return {

        }
    },
    //第一次加载
    created(e) {
        
    },
    methods: {
        onJump(url){
            uni.navigateTo({
                url:url
            })
        },
    }
}
</script>
<style lang="scss" scoped>
.goodsBox{
    padding: 50rpx 32rpx;
    border-bottom: 2rpx solid #f5f5f5;
    background:#fff;
    .goodsImg{
        width: 80rpx;
        height: 80rpx;
        border-radius: 40rpx;
    }
    .info{
        height: 80rpx;
        flex-direction: column;
        justify-content: space-between;
        align-items: flex-start;
        padding-left: 20rpx;
        .title{
            font-size: 30rpx;
            color: #333;
        }
        .desc{
            width: 100%;
            font-size: 26rpx;
            color: #666;
        }
    }
}
</style>
