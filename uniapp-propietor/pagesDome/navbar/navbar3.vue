<template>
    <view>
        <!--  :isShowTransparentTitle="false"设置此属性，透明背景时，title不显示 -->
        <f-navbar title="上拉渐变显示navbar背景" fontColor="#fff" bgColor="#fe461d" :scrollTop="scrollTop" navbarType='5'></f-navbar>
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
            scrollTop:0
        }
    },
    onLoad() {

    },
    methods: {

    },
    onPageScroll(e) {
    	this.scrollTop = e.scrollTop;
    },
}
</script>

<style scoped lang="scss">
.images{
    width: 750rpx;
}
</style>
