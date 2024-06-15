<template>
	<view class="u-p-24">
		<view class="a-steps m-t-20">
			<view style="margin-bottom: 40rpx;">报修详情</view>
			<view class="container">
				<view class="m-t-1">报修小区：<text class="colors">{{getDictLabel(dictValues[0],details.propertyId)}}</text>
				</view>
				<view class="m-t-1">报修单元：<text class="colors">{{getDictLabel(dictValues[1],details.buildingId)}}</text>
				</view>
				<view class="m-t-1">报修楼层：<text class="colors">{{getDictLabel(dictValues[2],details.floorId)}}</text>
				</view>
				<view class="m-t-1">报修状态：<text
						:style="{ color: statusLabelColor(details.status) }">{{ statusLabel(details.status) }}</text>
				</view>
				<view class="m-t-1">报修类型：<text class="colors">{{details.category}}</text>

				</view>
				<view class="m-t-1">报修标题：<text class="colors">{{details.title}}</text>
				</view>
				<view class="m-t-1">用户手机号：<text class="colors">{{details.tel}}</text>
				</view>
				<view class="m-t-1">报修内容：<text class="colors">{{details.description}}</text>
				</view>
				<view class="m-t-1">图片描述：
				</view>
				<template v-if="details.imageUrls">
					<view class="u-flex m-t-1">
						<view class="m-r-1" v-for="(item,index) in details.imageUrls.split(',')" :key='index'>
							<u-image :showLoading="true" style="margin-right: 20rpx;" radius="10rpx" :src="item"
								width="160rpx" height="160rpx">
							</u-image>
						</view>
					</view>
				</template>
			</view>
		</view>

		<view class="a-steps m-t-20" style="margin-top: 30rpx;" v-if="details.handlerId && details.status == '1' || details.handlerId && details.status ==
						'2'">
			<view style="margin-bottom: 40rpx;">师傅信息</view>
			<view class="container">

				<view class="m-t-1">师傅姓名：<text class="colors">{{sysUserinfo.name}}</text>
				</view>
				<view class="m-t-1">师傅手机号：<text class="colors">{{sysUserinfo.phone}}</text>
				</view>
				<view class="m-t-1" v-if="details.remarks">处理结果：<text class="colors">{{details.remarks || ''}}</text>
				</view>


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
		getPreairId,
		getProperTydictList,
		getBuildingDictList,
		getFloorDictList,
		getSysUserId
	} from '@/config/api.js';
	export default {
		computed: {
			...mapState(['PrimaryColor', 'userInfo']),
		},
		data() {
			return {
				repairId: '',
				details: {},
				sysUserinfo: {},
				dictValues: [],
			}
		},
		onLoad(e) {
			this.repairId = e.repairId
		},
		async mounted() {
			await this.RProperTydictList();
			await this.RBuildingDictList();
			await this.RFloorDictList();
			await this.RPreairId();
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
			RPreairId() {
				getPreairId(this.repairId).then(res => {
					this.details = res.data
					if (res.data.handlerId && res.data.status == '1' || res.data.handlerId && res.data.status ==
						'2') {
						this.RPSysUser(res.data.handlerId)
					}
				})
			},
			RPSysUser(id) {
				getSysUserId(id).then(res => {
					console.log(res)
					this.sysUserinfo = res.data
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
			//小区楼层字典
			RFloorDictList() {
				getFloorDictList().then(res => {
					this.dictValues[2] = res.data
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
