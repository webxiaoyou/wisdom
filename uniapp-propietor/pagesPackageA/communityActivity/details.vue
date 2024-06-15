<template>

	<view>
		<f-navbar title="文章详情" fontColor="#303133" transparentTitleColor="#fff" bgColor="#fff"
			:scrollTop="scrollTop" navbarType='5'></f-navbar>
		<view class="top_bu">
			<image class="images" mode="widthFix" :src="details.coverUrl"></image>
		</view>
		<view class="contnet u-p-24">
			<view class="title">{{details.title}}</view>
			<view class="description">{{details.description}}</view>
			<view>活动地点： </view>
			<u--text prefixIcon="map-fill"  margin="20rpx 0"   :iconStyle="{color:'#109ffe'}" :text="details.location"></u--text>
			<!-- <view class="activityTime"> {{}}</view> -->
			<view >活动时间： </view>
			<u--text prefixIcon="clock" margin="20rpx 0"  :iconStyle="{color:'#fea50a'}" :text="$u.timeFormat(details.params[0], 'yyyy年mm月dd日') +' - ' +$u.timeFormat(details.params[1], 'yyyy年mm月dd日')"></u--text>
			<!-- <view class="activityTime"> {{$u.timeFormat(details.params[0], 'yyyy年mm月dd日')}} - {{$u.timeFormat(details.params[1], 'yyyy年mm月dd日')}}</view> -->
		<view>
			<u-gap height="2" bgColor="#bbb" marginBottom="20"></u-gap>
			<u-parse :content="details.content"></u-parse>
		</view>
		</view>
	</view>
</template>

<script>
	import fNavbar from '@/components/module/f-navbar/f-navbar';
	import {
		getPCActivityId
	} from '@/config/api.js';
	export default {
		components: {
			fNavbar
		},
		data() {
			return {
				scrollTop: 0,
				activityId: '',
				details: {},
			}
		},
		onLoad(e) {
			this.activityId = e.id
			this.gPCActivityList();
		},
		onPageScroll(e) {
			this.scrollTop = e.scrollTop;
		},
		methods: {
			gPCActivityList() {
				getPCActivityId(this.activityId).then(res => {
					if (res.code === 200) {
						this.details = res.data
					}
				})

			},
		}
	}
</script>

<style scoped lang="less">
	.images {
		width: 750rpx;
		height: 250rpx;
	}
	.contnet{
		.title{
			font-size: 38rpx;
			font-weight: bold;
		}
		.description{
			margin: 16rpx 0;
			color: #767676;
		}
		.activityTime{
			font-size: 24rpx;
			margin: 20rpx 0;
		}
	}
</style>
