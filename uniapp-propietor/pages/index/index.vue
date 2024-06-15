<template>
	<view class="content u-p-24">
		<!-- 公共组件-每个页面必须引入 -->
		<public-module></public-module>
		<div class="swiper ">
			<u-swiper :list="bannerList" indicator height="160" indicatorMode="line" circular></u-swiper>
		</div>
		<div class="itemBox ">
			<u-grid :col="4" :border="false">
				<u-grid-item v-for="(item,index) in moreFun" :key="item.id" @click="navClick(item.onPlate)">
					<view class="u-flex u-p-t-30 u-p-b-30"
						style="position: relative;flex-direction: column;justify-content: center;">
						<image style="width: 70rpx;height: 70rpx;" :src="item.icon" />
						<!-- <u-icon :name="item.icon" :size="28" :color="PrimaryColor"></u-icon> -->
						<view class="grid-text" style="color: #666;font-size: 22rpx;">{{item.word}}</view>
					</view>
				</u-grid-item>
				<!-- 使用自定义图标 -->
				<div class="custom-icon custom-icon-bianpinghuatubiaosheji-icon"></div>
			</u-grid>
		</div>

		<!-- 瓷片图  -->
		<CeramicChip :styleType="specialMenus.styleType" :dataList="specialMenus.marketing" />

		<!-- 列表 -->
		<cardList :dataList="cardDataList" @onItem="toDetails"></cardList>
		<f-tabbar></f-tabbar>
	</view>
</template>

<script>
	import fTabbar from '@/components/module/f-tabbar/f-tabbar';
	import CeramicChip from '@/components/common/ceramic-chip.vue';
	import cardList from '@/components/common/card-list.vue';
	// #ifdef MP-WEIXIN
	// 小程序流量主
	import fAd from '@/components/module/f-ad/f-ad';
	// #endif
	import {
		getPCActivityList
	} from '@/config/api.js';
	import {
		mapState,
		mapMutations
	} from 'vuex';
	export default {
		components: {
			fTabbar,
			CeramicChip,
			cardList,
			// #ifdef MP-WEIXIN
			fAd,
			// #endif
		},
		computed: {
			...mapState(['PrimaryColor', 'userInfo', 'authenticationModalShow']),
		},

		data() {
			return {
				bannerList: [
					'https://cdn.uviewui.com/uview/swiper/swiper3.png',
					'https://cdn.uviewui.com/uview/swiper/swiper2.png',
					'https://cdn.uviewui.com/uview/swiper/swiper1.png',
				],
				//金刚区
				moreFun: [{
						id: 1,
						icon: require('@/static/tenement/kong/bianpinghuatubiaosheji.svg'),
						word: '业主认证',
						onPlate: 'authentication',
					},
					{
						id: 2,
						icon: require('@/static/tenement/kong/fangkeyuyuebeifen.svg'),
						word: '访客邀请',
						onPlate: 'goVisitorInvitation',
					},
					// {
					// 	icon: require('@/static/tenement/kong/tousujianyi.svg'),
					// 	word: '投诉建议',
					// 	onPlate: 'onHelp',
					// },
					{
						id: 3,
						icon: require('@/static/tenement/kong/phone.svg'),
						word: '联系物业',
						onPlate: 'makePhoneCall',
					},
				],

				specialMenus: { // 瓷片区数据
					marketing: [{
							id: 3,
							pic: require('@/static/tenement/tile/service.png'), //图片地址
							url: '/pagesPackageA/repair/list' //跳转链接
						},
						{
							id: 1,
							pic: require('@/static/tenement/tile/complaintAndAdvice.png'), //图片地址
							url: '/pagesPackageA/suggest/suggest' //跳转链接
						}, {
							id: 2,
							pic: require('@/static/tenement/tile/happiness.png'), //图片地址
							url: '' //跳转链接
						}
					],
					styleType: 3 // 排列方式
				},

				//列表内容
				cardDataList: [],
			}
		},
		onLoad() {
			// 隐藏原生的tabbar
			uni.hideTabBar();
		},
		onPullDownRefresh() {
			this.gPCActivityList();
			setTimeout(function() {
				uni.stopPullDownRefresh();
			}, 1000);
		},
		onShow() {
			// 在页面被激活时触发的操作
			this.gPCActivityList();
		},
		mounted() {
			// this.gPCActivityList();
		},
		methods: {
			...mapMutations(['PrimaryColor', 'userInfo']),
			onJump(url) {
				uni.navigateTo({
					url: url
				})
			},
			// 拷贝
			copy(notice) {
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
			/**
			 * @param {Object} 金刚区点击跳转
			 */
			navClick(e) {
				var url = ''
				this.judgeLogin(() => {
					this.judgeAuthentication(() => {
						if (e == 'authentication') {
							this.$u.toast('你完成认证')
						} else if (e == 'goVisitorInvitation') {
							this.onJump('/pagesPackageA/visitorInvitation/visitorInvitation')
						} else if (e == 'makePhoneCall') {
							uni.makePhoneCall({
								phoneNumber: '8888888' //仅为示例
							});
						}
					})

					// uni.navigateTo({
					// 	url: url
					// });
				});
				// if (e == 'goMyAddressList') {
				// 	this.$u.toast('您点击了收货地址~')
				// 	this.upac();
				// } else if (e == 'goCashCouponList') {
				// 	this.$u.toast('您点击了我的优惠券~')
				// 	this.ups();
				// } else if (e == 'onHelp') {
				// 	// #ifndef MP-WEIXIN
				// 	console.log('小程序客服~')
				// 	// #endif
				// } else if (e == 'goFeedback') {
				// 	this.$u.toast('您点击了意见反馈~')
				// } else if (e == 'goAbout') {
				// 	this.$u.toast('您点击了关于我们~')
				// }
				// url!='' && this.onJump(url)
			},
			gPCActivityList() {
				this.cardDataList = []
				this.judgeAuthentication(() => {
					let data = {
						params: {
							propertyId: this.userInfo?.user?.propertyId,
							order:'desc'
						}
					}
					getPCActivityList(data).then(res => {
						if (res.code === 200) {
							
							this.bannerList = res.data.rows.slice(0, 3).map(item => item.coverUrl);
							this.cardDataList = res.data.rows
						}
					})
				})

			},
			toDetails(e) {
				uni.navigateTo({
					url: '/pagesPackageA/communityActivity/details?id=' + e
				})
				console.log(e)
			}
		},

	}
</script>

<style lang="scss" scoped>
	.swiper {}

	.itemBox {
		background: #fff;
		padding: 0 24rpx;
		border-radius: 20rpx;
		overflow: hidden;
		margin-top: 50rpx;
		margin-bottom: 50rpx;

		.titleBox {
			padding: 32rpx 0;
			border-bottom: 1rpx solid #eee;

			.title {
				font-size: 28rpx;
				font-weight: bold;
			}

			.word {
				font-size: 24rpx;
				color: #999;
			}
		}

		.grid-text {
			font-size: 24rpx;
			color: #333;
			padding-top: 10rpx;
		}
	}

	// .name{
	//     padding: 24rpx;
	//     font-size: 32rpx;
	//     font-weight: bold;
	// }
	// .link{
	//     font-size: 24rpx;
	//     color: #007AFF;
	// }
	// .cell-group{
	//     .cell-item{
	//         border-bottom: 2rpx solid #eee;
	//         background-color: #fff;
	//         border-radius: 10rpx;
	//         padding: 20rpx 24rpx;
	//         .title{
	//             color: #333;
	//             font-size: 28rpx;
	//             padding: 0 10rpx;
	//             font-weight: bold;
	//             margin: 0;
	//         }
	//         .more{
	//             font-size: 24rpx;
	//             color: #999;
	//         }
	//     }
	// }
</style>
