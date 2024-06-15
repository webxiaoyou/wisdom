<template>
    <view>
        <f-navbar title="导航栏" fontColor="#fff" bgColor="#fe461d"></f-navbar>
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

    },
}
</script>

<style scoped lang="scss">
.images{
    width: 750rpx;
}
</style>
