<template>
	<view class="page">
		<!-- 公共组件-每个页面必须引入 -->
		<public-module></public-module>
        <u-parse :content="content"></u-parse>
	</view>
</template>

<script>
import { mapState, mapMutations } from 'vuex';
export default {
	data() {
		return {
            content: `
                <p>用户协议</p>
                <img src="https://cdn.uviewui.com/uview/swiper/2.jpg" />
            `,
		};
	},
	//第一次加载
	onLoad(e) {
        
	},
	//页面显示
	onShow() {},
	//方法
	methods: {

	}
};
</script>
<style lang="scss" scoped>
.page{
    min-height: 100vh;
    background-color: #fff;
    padding: 24rpx;
    font-size: 32rpx;
    color: #666;
    line-height: 1.6;
}
</style>
