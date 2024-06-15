<!-- common 瓷片区 -首页-认养-->
<template>
	<view class="common-ceramic-chip body-padding">
		<!-- 一行一个 -->
		<view v-if="styleType === 1" class="section-theme1" @tap="getChangeSkip(dataList[0].url)">
			<image mode="widthFix" :src="dataList[0].pic" />
		</view>

		<!-- 一行两个 -->
		<view v-if="styleType === 2" class="section-theme2">
			<view v-for="item in dataList.slice(0,2)" :key="item.id" class="section-list"
				@tap="getChangeSkip(item.url)">
				<!-- '/pages/indexs/mall-integral/index' -->
				<image class="section-list-img" mode="widthFix" :src="item.pic" />
			</view>
		</view>

		<!-- 一行三个 -->
		<view v-if="styleType === 5" class="section-theme3">
			<view class="section-list" v-for="item in dataList" :key="item.id" @tap="getChangeSkip(item.url)">
				<image class="section-list-img" mode="widthFix" :src="item.pic" />
			</view>
		</view>

		<!-- 左1右2 -->
		<view v-if="styleType === 3" class="section-theme4">
			<view class="section-left" @tap="getChangeSkip(dataList[0].url)">
				<image class="img" mode="widthFix" :src="dataList[0].pic" />
			</view>

			<view class="section-list-box">
				<view v-for="(item,index) in dataList.slice(1,3)" :key="item.id" class="section-list"
					@tap="getChangeSkip(item.url)">
					<image class="img" mode="widthFix" :src="item.pic" />
				</view>
			</view>

		</view>

		<!-- 左2右1 -->
		<view v-if="styleType === 4" class="section-theme4">
			<view class="section-list-box" style="margin-right: 20rpx;">
				<view v-for="(item,index) in dataList.slice(0,2)" :key="item.id" class="section-list"
					@tap="getChangeSkip(item.url)">
					<image class="img" mode="widthFix" :src="item.pic" />
				</view>
			</view>

			<view class="section-left" style="margin-right: 0;" @tap="getChangeSkip(dataList[2].url)">
				<image class="img" mode="widthFix" :src="dataList[2].pic" />
			</view>
		</view>

		<!-- 左2右2 -->
		<view v-if="styleType === 6" class="section-theme6">
			<view v-for="(item,index) in dataList" :key="item.id" class="section-list" @tap="getChangeSkip(item.url)">
				<image class="section-list-img" mode="widthFix" :src="item.pic" />
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		name: "common-ceramic-chip",
		props: {
			styleType: {
				type: Number,
				default: 1
			},
			dataList: {
				type: Array,
				default: () => [],
			}
		},
		methods: {
			getChangeSkip(url) {
				console.log('url', url);
				this.judgeLogin(() => {
					this.judgeAuthentication(() => {
						if (!!url) uni.navigateTo({
							url: url
						});
					})

					// uni.navigateTo({
					// 	url: url
					// });
				});

			}
		}
	}
</script>

<style lang="scss" scoped>
	.common-ceramic-chip {
		width: 100%;
		margin-bottom: 40rpx;
	}


	// 样式1 一行一个
	.section-theme1 {
		width: 100%;

		image {
			width: 100%;
		}
	}

	// 样式2 一行两个
	.section-theme2 {
		width: 100%;
		display: flex;
		justify-content: space-between;

		.section-list {
			width: 332rpx;
			height: auto;

			.section-list-img {
				width: 100%;
				height: 100%;
			}
		}
	}

	// 样式3 一行三个
	.section-theme3 {
		width: 100%;
		display: flex;
		justify-content: space-between;

		.section-list {
			width: 216rpx;
			border-radius: 4px;

			.section-list-img {
				width: 100%;
				height: 100%;
			}
		}
	}

	// 样式4 左1 右2
	.section-theme4 {
		width: 100%;
		display: flex;

		.section-left {
			width: 332rpx;
			margin-right: 20rpx;

			.img {
				width: 100%;
				height: 100%;
			}
		}

		.section-list-box :nth-child(1) {
			margin-bottom: 10rpx;
		}

		.section-list-box {
			width: 332rpx;

			.section-list {
				width: 332rpx;
				display: flex;
				flex-direction: column;

				.img {
					width: 100%;
					height: 100%;
				}
			}
		}

	}

	// 样式6 左2右2
	.section-theme6 {
		width: 100%;
		display: flex;
		flex-wrap: wrap;

		:nth-child(odd) {
			margin-right: 20rpx;
		}

		.section-list {
			width: 332rpx;
			height: auto;
			margin-bottom: 24rpx;

			.section-list-img {
				width: 100%;
				height: 100%;
			}
		}
	}
</style>
