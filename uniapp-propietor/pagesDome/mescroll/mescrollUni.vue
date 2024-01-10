<template>
    <view class="page">
        <!-- 公共组件-每个页面必须引入 -->
        <public-module></public-module>
        <view class="search u-flex">
        	<u-search placeholder="请输入要的搜索关键词" :showAction="false" v-model="keyword" @search="onSearch"></u-search>
        	<view class="select u-flex" @click="onSearch">
        		<f-icon name="shaixuan" size="36" color="#000"></f-icon>
        		<view class="text">筛选</view>
        	</view>
        </view>
        <view class="tabs">
        	<u-tabs :list="tabsList" :current="tabIndex" lineColor="#d7a725" :scrollable="false" :inactiveStyle="{color: '#303133'}" :activeStyle="{color: '#000000'}" :itemStyle="{flex: 1, height: '44px'}" @click="tabsClick"></u-tabs>
        </view>
        <swiper :style="{height: height}" :current="tabIndex" @change="swiperChange">
        	<swiper-item v-for="(tab,i) in tabsList" :key="i">
        		<mescroll-item ref="mescrollItem" :i="i" :index="tabIndex" :tabs="tabsList" :height="height" :keyword="keyword"></mescroll-item>
        	</swiper-item>
        </swiper>

        
    </view>
</template>

<script>
import { mapState, mapMutations } from 'vuex';
import MescrollItem from "./module/mescrollUni-item.vue";
export default {
    components:{
        MescrollItem
    },
    data() {
        return {
            height: "", // 需要固定swiper的高度
            keyword:'',
            tabsList:[{
            	name: '全部',
            }, {
            	name: '热销',
            }, {
            	name: '特价'
            }, {
            	name: '上新'
            }],
            tabIndex:0,
        };
    },
    //第一次加载
    onLoad(e) {
        // 需要固定swiper的高度
        // #ifndef MP-ALIPAY
        this.height = (uni.getSystemInfoSync().windowHeight - uni.upx2px(88) - 44) + 'px'
        // #endif
        // #ifdef MP-ALIPAY
        this.height = (uni.getSystemInfoSync().windowHeight - uni.getSystemInfoSync().statusBarHeight -  uni.getSystemInfoSync().titleBarHeight - uni.upx2px(88) - 46) + 'px'
        // #endif
    },
    //页面显示
    onShow() {},
    //方法
    methods: {
        onJump(url){
            uni.navigateTo({
                url:url
            })
        },
        //搜索框搜索
        onSearch(){
            if(!this.keyword){
                this.$u.toast('搜索的内容不能为空~')
                return
            }
        	var httpData = {
        		keyword:this.keyword,
        	}
            this.$refs.mescrollItem[this.tabIndex].doSearch()
            console.log(this.$refs,'this.$refs')
        },
        tabsClick(e){
        	console.log(e)
        	this.tabIndex = e.index
        },
        // 轮播菜单
        swiperChange(e){
        	this.tabIndex = e.detail.current
        },
    }
};
</script>
<style lang="scss">
.search{
    height: 88rpx;
	padding: 24rpx 24rpx 0;
    background: #fff;
	.select{
		width: 88rpx;
		flex-direction: column;
		justify-content: center;
		.text{
			font-size: 20rpx;
			color: #000000;
			line-height: 1;
		}
	}
}
.tabs{
    background: #fff;
}
</style>
