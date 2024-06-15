<template>
    <view>
        <f-navbar title="不固定导航栏" leftText="返回" fontColor="#fff" bgColor="#fe461d" navbarType='1'></f-navbar>
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
