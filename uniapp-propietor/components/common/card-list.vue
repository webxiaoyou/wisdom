<template>
	<view>
		<view class="activity u-flex">
			<view class="title u-flex-m">社区活动</view>
			<!-- 	<view class="word">更多</view>
			<u-icon name="arrow-right" :size="13" color="#999"></u-icon> -->
		</view>
		<u-list @scrolltolower="scrolltolower" height="auto" :pagingEnabled="true" v-if="dataList.length>0">
			<u-list-item v-for="(item, index) in dataList" :key="item.activityId">
				<view class="activity-list u-p-24" :style="'background-image: url('+item.coverUrl+') '"
					@click="onItem(item.activityId)">
					<view class="title ">{{item.title}}</view>
					<text
						class="description">活动时间：{{$u.timeFormat(item.startTime, 'yyyy.mm.dd') +' - ' +$u.timeFormat(item.endTime, 'yyyy.mm.dd')}}</text>
					<!-- <view class="description">{{item.description}}</view> -->
				</view>
			</u-list-item>
		</u-list>
		<view v-else>
			<u-empty text="未进行物业认证" mode="permission" icon="http://cdn.uviewui.com/uview/empty/permission.png"></u-empty>
		</view>
	</view>
</template>

<script>
	export default {
		props: {
			dataList: {
				type: Array,
				default: () => [],
			}
		},
		data() {
			return {

			}
		},
		methods: {
			scrolltolower() {
				console.log("到底了")
			},
			onItem(id) {
				this.$emit('onItem', id)
			}
		}
	}
</script>

<style lang="less" scoped>
	.activity {
		display: flex;
		align-items: center;
		margin-bottom: 40rpx;

		&::before {
			content: '';
			display: block;
			margin-right: 10rpx;
			width: 8rpx;
			height: 1em; // 使用1em来与字体相同的高度
			background-color: #007AFF;
			border-radius: 20rpx;
		}

		.title {
			font-size: 32rpx;
			font-weight: bold;
		}

		.word {
			font-size: 24rpx;
			color: #999;
		}
	}

	.activity-list {
		width: 100%;
		height: 400rpx;
		border-radius: 20rpx;
		overflow: hidden;
		margin-bottom: 40rpx;
		position: relative;
		background-size: cover;
		background-position: center;
		display: flex;
		flex-direction: column;
		justify-content: flex-end;

		/* 可以根据需要调整图片的位置 */
		/* 底部渐变 */
		&::before {
			content: '';
			position: absolute;
			bottom: 0;
			left: 0;
			width: 100%;
			height: 300rpx;
			/* 调整渐变的高度 */
			background: linear-gradient(to top, rgba(0, 0, 0, 1) 0%, rgba(255, 255, 255, 0) 100%);

		}

		.title {
			z-index: 1;
			color: #fdfdfd;
			font-size: 32rpx;
			font-weight: bold;
			white-space: nowrap;
			overflow: hidden;
			text-overflow: ellipsis;
		}

		.description {
			z-index: 1;
			color: #e6e6e6;
			font-size: 14px;
			white-space: nowrap;
			overflow: hidden;
			text-overflow: ellipsis;
		}
	}
</style>
