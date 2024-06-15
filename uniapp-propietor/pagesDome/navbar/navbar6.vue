<template>
    <view>
        <f-navbar title="导航栏-背景渐变" fontColor="#fff" gradient="linear-gradient(-90deg, yellow, red)"></f-navbar>
        <view class="main">
            <view v-for="item of 3">
                <image class="images" mode="widthFix" src="../../static/mt1.jpg"></image>
                <image class="images" mode="widthFix" src="../../static/mt2.jpg"></image>
                <image class="images" mode="widthFix" src="../../static/mt3.jpg"></image>
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

        }
    },
    onLoad() {

    },
    methods: {
        leftClick(){
            uni.showModal({
                title: '温馨提示',
                content: '您点击了左侧自定义左侧插槽，需要返回吗',
                success: function (res) {
                    if (res.confirm) {
                        uni.navigateBack()
                    } else if (res.cancel) {
                        console.log('用户点击取消');
                    }
                }
            });
        }
    },
}
</script>

<style scoped lang="scss">
.images{
    width: 750rpx;
}
.actName{
    max-width: 100rpx;
    font-size: 28rpx;
    color:#fff;
    padding-left: 5rpx;
}
</style>
