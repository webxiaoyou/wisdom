<template>
    <view class="page">
        <view class="title">此组件是下载其他作者组件，改编布局且可以全局使用showModal</view>
        <view class="text">注意：此组件只支持app</view>
        <view class="text">解决安卓原生showModal很难看且'确定'在左边</view>
        <view class="text">请main.js全局引入,才能生效</view>
        <view class="text">
            import f_show_modal from '@/components/module/show_modal/f_show_modal.js'
            Vue.use(f_show_modal)
        </view>
        <view class="btn" @click="onKnow">知道了</view>
        <view class="btn red" @click="determine">确定</view>
    </view>
</template>

<script>
export default {
    data(){
        return {}
    },
    methods:{
        onKnow(){
            // #ifndef APP-PLUS
            uni.showModal({
                title: '提示',
                content: '请在app测试',
                confirmText: "知道了",
                showCancel: false,
                success: function(res) {}
            })
            // #endif
            // #ifdef APP-PLUS
            uni.$showModal({
                content:'您已进入app调试showModal~',
                delCancel: true,
                confirmVal: '知道了',
            }).then(res=>{
                console.log('知道了')
            }).catch(res=>{})
            // #endif
        },
        determine(){
            // #ifndef APP-PLUS
            uni.showModal({
                title: '提示',
                content: '请在app测试',
                success(res) {}
            })
            // #endif
            // #ifdef APP-PLUS
            uni.$showModal({
                confirmVal:'确定',
                cancelVal:'取消',
                content:'您认为此插件可以吗？',
            }).then(res=>{
                console.log('确定')
            }).catch(res=>{
                console.log('取消')
            })
            // #endif
        }
    }
}
</script>

<style>
.page{
    padding: 32rpx;
}
.title{
    font-size: 24rpx;
    color: #666;
    padding-bottom: 20rpx;
}
.text{
    font-size: 24rpx;
    color: #999;
}
.btn{
    margin-top: 32rpx;
    width: 250rpx;
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
