<template>
	<view class="u-p-24 bg" :style="'background-image: url('+bg+') '">
		<!-- 文字描述部分 -->
		<view class="invite-description">
			<view class="title">访客通行</view>
			<view class="content">在这里你可以您所在小区下单元的开门权限分享给好友，好友输入邀请码即可开门，方便快捷!</view>
		</view>

		<!-- 开门授权卡片 -->
		<view class="invite-card u-p-24">
			<view class="card-title">开门授权</view>
			<view class="card-swiper">
				<view class="card-content">
					<view v-for="(item, index) in card1Data" :key="item.unitId" class="sub-card"
						@click="toggleSelect(1, item.buildingId,item.unitId)"
						:class="{ selected: form.authorizedDoors.includes(item.buildingId) }">
						{{ item.buildingNumber }}
					</view>
				</view>
			</view>

		</view>

		<!-- 钥匙分享时常卡片 -->
		<view class="invite-card u-p-24">
			<view class="card-title">钥匙分享时常</view>
			<view class="card-content">
				<view v-for="(item, index) in card2Data" :key="item.id" class="sub-card"
					@click="toggleSelect(2, item.num,null)" :class="{ selected: form.durationHours === item.num }">
					{{ item.title }}
				</view>
			</view>
		</view>


		<!-- 底部按钮 -->
		<view class="btn_fixation">
			<view style="bottom: 60rpx;">
				<u-button type="primary" text="给分享访客开门" @click="openDoor"></u-button>
			</view>
		</view>

		<u-modal :show="show" :closeOnClickOverlay="true" @close="closes" :showConfirmButton="false">
			<template #default>
				<view class="modal-container">
					<!-- 标题 -->
					<view class="modal-title">{{getDictLabel(dictValues[0],codeInfo.propertyId)}}</view>

					<!-- 邀请码 -->
					<view>访客邀请码为
						<view class="invite-code">
							{{codeInfo.invitationCode}}
						</view>

					</view>

					<!-- 开门授权 -->
					<view class="authorize-content">
						<view class="authorize-title">授权单元</view>
						<view class="authorize-swiper">
							<view class="card-content" v-if="codeInfo.authorizedDoors">
								<view v-for="(item, index) in codeInfo.authorizedDoors.split(',')" :key="index"
									class="authorize-item">
									{{ getDictLabel(dictValues[1],item) }}
								</view>
							</view>
						</view>
						<!-- <swiper class="" :duration="500" indicator-dots>
							<swiper-item>
								

							</swiper-item>
						</swiper> -->
					</view>

					<!-- 钥匙邀请码有效期 -->
					<view class="expire-time">
						此钥匙邀请码有效期时长 <text class="expire-time-text"> {{codeInfo.durationHours}}小时 </text>
					</view>

					<!-- 复制按钮 -->
					<view class="copy-button" @click="copyContent">复制内容</view>
				</view>
			</template>
		</u-modal>
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
		postPvisitorInvitationCode
	} from '@/config/api.js';
	export default {
		computed: {
			...mapState(['PrimaryColor', 'userInfo']),
		},
		data() {
			return {
				show: false,
				bg: require('@/static/tenement/blurry-gradient-haikei.svg'), //图片地址
				card1Data: [], // 用于卡片1的数据
				card2Data: [{
						id: 1,
						title: "1小时",
						num: 1,
					},
					{
						id: 2,
						title: "3小时",
						num: 3,
					},
					{
						id: 3,
						title: "6小时",
						num: 6,
					},
					{
						id: 4,
						title: "12小时",
						num: 12,
					}
				], // 用于卡片2的数据
				dictValues: [], //存储小区信息
				form: {
					unitId: [], //房屋ID
					ownerId: this.$store.state.userInfo.user.userId, //业主ID
					propertyId: this.$store.state.userInfo.user.propertyId, //物业小区
					authorizedDoors: [], //被授权的门ID
					durationHours: '', //权限时长
					invitationStatus: 0, //邀请状态
				},
				codeInfo: {} //存储邀请信息
			};
		},
		async mounted() {
			await this.RProperTydictList();
			await this.RBuildingDictList();
			await this.PpropertyUnitList();
		},
		methods: {
			closes(){
				this.show=false;
			},
			toggleSelect(cardType, index, unitId) {
				// 切换选择状态
				if (cardType === 1) {
					const selectedIndex = this.form.authorizedDoors.indexOf(index);
					const selectedIndexs = this.form.unitId.indexOf(unitId);
					if (selectedIndex !== -1 && selectedIndexs !== -1) {
						this.form.authorizedDoors.splice(selectedIndex, 1);
						this.form.unitId.splice(selectedIndexs, 1);
					} else {
						this.form.authorizedDoors.push(index);
						this.form.unitId.push(unitId);
					}
				} else if (cardType === 2) {
					this.form.durationHours = index;
				}
			},
			PpropertyUnitList(e) {
				getPpropertyUnitList({
					params: {
						propertyId: this.userInfo.user.propertyId,
						ownerId: this.userInfo.user.userId,
						authenticationStatus: 1
					}
				}).then(res => {
					this.card1Data = res?.data?.rows
					// this.dictValues[1] = res.data
					console.log(res)
				})
			},
			// 复制内容的逻辑
			copyContent() {
				const authorizedDoorsText = this.codeInfo.authorizedDoors.split(',').map(item => this.getDictLabel(this
					.dictValues[1], item)).join('、');
				const notice =
					`通行小区为：${this.getDictLabel(this.dictValues[0],this.codeInfo.propertyId)}\n访客邀请码为：${this.codeInfo.invitationCode}\n授权单元：${authorizedDoorsText}\n此钥匙邀请码有效期时长为：${this.codeInfo.durationHours}小时\n注意：凭此访客邀请码可在进入小区/单元/乘坐电梯使用`;
				uni.setClipboardData({
					data: notice,
					success(res) {
						uni.showToast({
							title: '复制成功',
							duration: 1000
						})
					}
				})
			},
			openDoor() {
				// 点击底部按钮的逻辑处理，此处可以使用this.selectedCard1和this.selectedCard2获取选择结果
				// console.log('给分享访客开门');
				if (this.form.authorizedDoors.length === 0) {
					uni.$u.toast('请选择授权单元');
					return;
				} else if (this.form.durationHours == '' || this.form.durationHours == 0) {
					uni.$u.toast('请选择分享时长');
					return;
				}
				this.PvisitorInvitationCode();
				console.log('选中的开门授权：', this.form.authorizedDoors);
				console.log('选中的钥匙分享时常：', this.form.durationHours);
			},
			PvisitorInvitationCode() {
				postPvisitorInvitationCode(this.form).then((res) => {
					console.log(res)
					if (res.code == 200) {
						this.show = true;
						this.codeInfo = res.data
					}
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
				return item ? item.label : dictValue;
			}
		},
	};
</script>
<style lang="less" scoped>
	.bg {
		background-image: url(@/static/tenement/blurry-gradient-haikei.svg);
		background-size: cover;
		background-repeat: no-repeat;
		width: 100%;
		height: 100%;
		position: absolute;
		top: 0;
		left: 0;
		z-index: -1;
		/* 将背景图层级设为-1，使其他内容在其上层显示 */
	}

	.invite-description {
		width: 375rpx;
		// background-color: #f5f5f5;
		margin-bottom: 40rpx;

		.title {
			font-size: 18px;
			font-weight: bold;
			margin-bottom: 10px;
			color: #fcfcfc;
		}

		.content {
			font-size: 14px;
			color: #fcfcfc;
		}
	}



	.invite-card {
		width: 100%;
		background-color: #fff;
		border-radius: 10px;
		box-sizing: border-box;
		text-align: center;
		margin-bottom: 20px;

		.card-title {
			font-size: 18px;
			font-weight: bold;
			margin: 20rpx 0 40rpx 0;
		}

		.card-content {
			display: flex;
			font-size: 14px;
			justify-content: space-around;
		}

		.sub-card {
			width: 30%;
			background-color: #f5f5f5;
			padding: 10px;
			border-radius: 5px;
			margin-bottom: 10px;
			box-sizing: border-box;

			&.selected {
				background-color: #2672fa;
				color: #fff;
			}
		}

		.card-swiper {
			height: calc(100vh - 900rpx);
			overflow-y: scroll;
			/* 计算 swiper 的高度，减去上下的间距 */
			margin-bottom: 10px;
		}
	}



	.modal-container {
		width: 80%;
		text-align: center;
		padding: 20rpx;

		.modal-title {
			font-size: 18px;
			font-weight: bold;
			color: #2672fa;
			margin-bottom: 20rpx;
		}

		.invite-code {
			font-size: 16px;
			color: #ff9900;
			margin: 40rpx 0;
		}



		.authorize-content {
			text-align: center;

			.authorize-title {
				font-size: 16px;
				font-weight: bold;
				margin: 20rpx 0 40rpx 0;
			}

			.authorize-swiper {
				height: 100px;
				margin-bottom: 20rpx;
				// overflow: hidden;
				overflow-y: scroll;

				.card-content {
					display: flex;
					flex-wrap: wrap;
					font-size: 14px;
					justify-content: space-around;

					.authorize-item {
						width: 45%;
						margin: 0 10rpx;
						background-color: #f5f5f5;
						padding: 10rpx 5rpx;
						border-radius: 5rpx;
						margin-bottom: 10rpx;
						box-sizing: border-box;
					}
				}
			}


		}

		.expire-time {
			font-size: 14px;
			margin-bottom: 40rpx;

			.expire-time-text {
				color: #ff0000;
				font-weight: bold;
			}
		}

		.copy-button {
			background-color: #2672fa;
			color: #ffffff;
			padding: 10rpx;
			border-radius: 5rpx;
			cursor: pointer;
		}
	}


	.bottom-btn {
		width: 705rpx;
		position: fixed;
		height: 150rpx;
		bottom: 0rpx;
		// background: #F5F5F5;
	}
</style>
