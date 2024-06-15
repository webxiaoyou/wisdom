<template>
	<view class="u-p-24">
		<view class="a-steps m-t-20">
			<!-- <view style="margin-bottom: 40rpx;">通知详情</view> -->
			<view class="container">
				<view class="m-t-1">通知小区：<text class="colors">{{getDictLabel(dictValues[0],details.propertyId)}}</text>
				</view>
				<view class="m-t-1">公告类型：<text
						class="colors">{{details.notificationType == '1' ? '通知' : '公告'}}</text>
				</view>
				<view class="m-t-1">通知标题：<text class="colors">{{details.title}}</text>
				</view>
				<view class="m-t-1">通知类型：<text
						:style="{color:details.targetType=='0'?'#1d93fa':'#fa7c21'}">{{details.targetType=='0'?getDictLabel(dictValues[1],details.targetId):'用户'}}</text>

				</view>

			</view>
			<view class="m-t-1" style="margin-bottom: 20rpx;">通知内容：</text>
			</view>
			<u-parse :content="details.content"></u-parse>
		</view>
	</view>
	</view>
</template>

<script>
	import {
		mapState,
		mapMutations,
	} from 'vuex'

	import {
		getNotificationId,
		getProperTydictList,
		getBuildingDictList,
		getFloorDictList,
	} from '@/config/api.js';
	export default {
		computed: {
			...mapState(['PrimaryColor', 'userInfo']),
		},
		data() {
			return {
				notificationId: '',
				details: {},
				sysUserinfo: {},
				dictValues: [],
			}
		},
		onLoad(e) {
			this.notificationId = e.notificationId
		},
		async mounted() {
			await this.RProperTydictList();
			await this.RBuildingDictList();
			// await this.RFloorDictList();
			await this.NotificationId();
		},
		methods: {
			statusLabelColor(status) {
				switch (status) {
					case '0':
						return '#2c9dff';
					case '1':
						return '#FF8C00';
					case '2':
						return '#04cc43';
					default:
						return '#7dc4f3';
				}
			},
			statusLabel(status) {
				switch (status) {
					case '0':
						return '待处理';
					case '1':
						return '处理中';
					case '2':
						return '已完成';
					default:
						return '';
				}
			},
			NotificationId() {
				getNotificationId(this.notificationId).then(res => {
					this.details = res.data
					
				})
			},
			
			//小区字典
			RProperTydictList() {
				getProperTydictList().then(res => {
					this.dictValues[0] = res.data
					console.log(res)
				})
			},
			//小区栋数字典
			RBuildingDictList() {
				getBuildingDictList().then(res => {
					this.dictValues[1] = res.data
					console.log(res)
				})
			},

			getDictLabel(distList, dictValue) {
				const dict = distList || [];
				const item = dict.find(item => item.value == dictValue);
				return item ? item.label : value;
			}
		}
	}
</script>
<style lang="less" scoped>
	.a-steps {
		width: 100%;
		background-color: #FFFFFF;
		padding: 40rpx 0;
		box-sizing: border-box;
		border-radius: 20rpx;
	}

	.m-t-20 {
		// margin-top: 40rpx;
		padding: 40rpx;
	}

	.colors {
		color: #109ffe;
	}

	.m-t-1 {
		margin-top: 20rpx;
	}

	.m-r-1 {
		margin-right: 20rpx;
	}
</style>
