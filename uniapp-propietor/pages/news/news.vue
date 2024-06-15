<template>
	<view>
		<!-- 公共组件-每个页面必须引入 -->
		<public-module></public-module>
		<view class="u-p-24">
			<u-empty mode="list" icon="http://cdn.uviewui.com/uview/empty/list.png" v-if="notifications.length==0">
			</u-empty>
			<template v-else>
				<view v-for="notification in notifications" :key="notification.notificationId" class="notification-item"
					@tap="toDetails(notification.notificationId)">
					<view class="avatar"
						:style="{ 'background-color': notification.notificationType == '1' ? '#1d93fa' : '#5dd382' }">
						<!-- Display '通知' for type 0 and '公告' for type 1 -->
						{{ notification.notificationType == '1' ? '通知' : '公告' }}
					</view>
					<view class="notification-content">
						<u--text bold :lines="1" :text="notification.title"></u--text>
						<view class="notification-body">通知类型：<text
								:style="{color:notification.targetType=='0'?'#1d93fa':'#fa7c21'}">{{notification.targetType=='0'?getDictLabel(dictValues,notification.targetId):'用户'}}</text>
						</view>
					</view>
				</view>
			</template>

		</view>

		<f-tabbar :isFillHeight="false"></f-tabbar>
	</view>
</template>

<script>
	import fTabbar from '@/components/module/f-tabbar/f-tabbar';
	import {
		getNotificationList,
		getBuildingDictList
	} from '@/config/api.js';
	import {
		mapState,
		mapMutations,
	} from 'vuex'
	export default {
		computed: {
			...mapState(['PrimaryColor', 'userInfo']),
		},
		components: {
			fTabbar
		},
		data() {
			return {
				notifications: [],
				dictValues:[]
			};
		},
		onLoad() {
			this.RBuildingDictList();
		},
		onPullDownRefresh() {
			this.judgeLogin(() => {
				this.judgeAuthentication(() => {
					this.NotificationList();
				})
			});
			setTimeout(function() {
				uni.stopPullDownRefresh();
			}, 1000);
		},
		onShow() {
			this.judgeLogin(() => {
				this.judgeAuthentication(() => {
					this.NotificationList();
				})
			});
		},
		methods: {
			NotificationList() {
				let data = {
					propertyId: this.userInfo.user.propertyId,
					targetIds: [this.userInfo.user.unitId, ...this.userInfo.user.buildingIds],
				}
				getNotificationList({
					params: data
				}).then(res => {
					this.notifications = res.data.rows
				})
			},
			//小区栋数字典
			RBuildingDictList() {
				getBuildingDictList().then(res => {
					this.dictValues = res.data
					console.log(res)
				})
			},
			getDictLabel(distList, dictValue) {
				const dict = distList || [];
				const item = dict.find(item => item.value == dictValue);
				return item ? item.label : value;
			},
			toDetails(url) {
				uni.navigateTo({
					url: './details?notificationId=' + url
				})
			}
		},
	};
</script>

<style lang="less" scoped>
	.notification-item {
		display: flex;
		align-items: center;
		margin-bottom: 20rpx;
		background-color: #fefffd;
		// border: 1px solid #ccc;
		padding: 10px;
		border-radius: 8px;

		.avatar {
			width: 40px;
			height: 40px;
			margin-right: 10px;
			border-radius: 50%;
			overflow: hidden;
			color: #fff;
			display: flex;
			align-items: center;
			justify-content: center;
			font-size: 14px;
		}

		.notification-content {
			flex: 1;
		}

		.notification-header {
			font-size: 18px;
			font-weight: bold;
			margin-bottom: 8px;
		}

		.notification-body {
			font-size: 14px;
			color: #818288;
		}
	}
</style>
