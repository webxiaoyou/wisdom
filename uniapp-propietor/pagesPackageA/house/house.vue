<template>
	<view class="u-p-24">
		<view v-for="(item, index) in dataList" :key="index" class="card" @click="handleClickCard(item)">
			<!-- 左边渐变色背景 -->
			<!-- <view :style="{ background: getGradientColor(item.status) }" class="gradient-bg"></view> -->

			<!-- 右边内容 -->
			<view class="content">
				<view class="info">
					<view>
						<text class="title">{{ item.userName }}</text>
						<view class="category" style="display: flex; align-items: center;">房产信息
							<view style="margin: 0 20rpx;">|</view>
							<u--text size="14" class="category" margin="20rpx 0"
								:color="statusLabelColor(item.authenticationStatus)"
								:text="statusLabel(item.authenticationStatus)">
							</u--text>
						</view>
					</view>
					<view>
						<u--text prefixIcon="map-fill" size="14" :iconStyle="{color:'#109ffe'}"
							:text="getDictLabel(dictValues[0],item.propertyId) +'-' + getDictLabel(dictValues[1],item.buildingId)+'-' + getDictLabel(dictValues[2],item.floorId)">
						</u--text>
					</view>
				</view>
				<view class="status">
					<view  v-if="item.authenticationStatus==='2'" style="color: #109ffe;" @click="toEdit(item.unitId)">去修改</view>
					<u--image :showLoading="true" :src="item.imageUrl" width="80px" height="80px"
						@click="onLookImg(item.imageUrl)" v-else></u--image>
						
				</view>
			</view>
		</view>

		<view class="btn_fixation">
			<view style="bottom: 60rpx;">
				<u-button type="primary" text="添加房产信息" @click="toAdd"></u-button>
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
		getPpropertyUnitList,
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
				dataList: [],
				dictValues: [],
			}
		},
		onLoad() {

		},
		async mounted() {
			await this.RProperTydictList();
			await this.RBuildingDictList();
			await this.RFloorDictList();
			await this.PpropertyUnitList();
		},
		onShow() {
			this.PpropertyUnitList();
		},
		methods: {
			...mapMutations(['PrimaryColor', 'userInfo', 'setUserInfo']),
			toAdd() {
				uni.navigateTo({
					url: './add'
				})
			},
			toEdit(id) {
				uni.navigateTo({
					url: './add?unitId='+id
				})
			},
			
			//用户房产
			PpropertyUnitList(e) {
				getPpropertyUnitList({
					params: {
						propertyId: this.userInfo.user.propertyId,
						ownerId: this.userInfo.user.userId
					}
				}).then(res => {
					this.dataList = res.data.rows
					// this.dictValues[1] = res.data
					console.log(res)
				})
			},
			statusLabel(status) {
				switch (status) {
					case '0':
						return '未认证';
					case '1':
						return '已认证';
					case '2':
						return '认证未通过';
					case '3':
						return '审核中';

					default:
						return '';
				}
			},
			statusLabelColor(status) {
				switch (status) {
					case '0':
						return '#2c9dff';
					case '1':
						return '#04cc43';
					case '2':
						return '#cc391c';
					case '3':
						return '#FF8C00';
					default:
						return '#7dc4f3';
				}
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
			onLookImg(url) {
				uni.previewImage({
					urls: [url]
				})
			},
			handleClickCard(item) {
				uni.navigateTo({
					url: './details?repairId=' + item.repairId
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

<style scoped lang="less">
	.card-list {
		/* 设置列表的样式，可以根据需要添加滚动效果等 */
	}

	.card {
		position: relative;
		display: flex;
		height: 240rpx;
		/* 设置卡片的高度 */
		margin-bottom: 10px;

		/* 设置卡片之间的间距 */
		&:nth-last-child(2) {
			margin-bottom: 80px;
		}
	}

	.gradient-bg {
		border-radius: 20rpx 0 0 20rpx;
		position: absolute;
		top: 0;
		left: 0;
		width: 10px;
		/* 左边渐变色背景的宽度 */
		height: 100%;
	}

	.content {
		flex: 1;
		display: flex;
		padding: 20rpx 40rpx;
		box-sizing: border-box;
		background-color: #FFFFFF;
		border-radius: 20rpx;

		.info {
			display: flex;
			flex-direction: column;
			justify-content: space-between;
			flex: 1;

			.title {
				font-size: 16px;
				font-weight: bold;
			}

			.category,
			.time,
				{
				// margin-top: 5px;
				font-size: 14px;
				color: #666;
			}

			.address {}
		}



		.status {
			display: flex;
			align-items: center;
			margin-top: 10px;
			font-size: 14px;
			color: #2ECC71;
			/* 设置状态的颜色，可以根据具体需求调整 */
		}
	}

	.btn_fixation {
		width: 705rpx;
		position: fixed;
		height: 150rpx;
		bottom: 0rpx;
		background: #F5F5F5;
		// background-color: #FFFFFF;

	}
</style>
