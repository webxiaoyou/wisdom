<template>
	<view>
		<u-popup :show="showWxLogin" mode="bottom" round="44rpx" :closeable="true" @close="onClose">
			<view class="popupBox">
				<!-- #ifndef MP-WEIXIN -->
				<view class="logo u-m-b-20">
					<image class="img" src="/static/logo.png"></image>
				</view>
				<!-- #endif -->
				<view class="h2 bold">获取您的昵称、头像</view>
				<view class="h3">获取用户头像、昵称，主要用于向用户提供具有辨识度的用户中心界面</view>
				<!-- #ifdef MP-WEIXIN -->
				<view class="form">
					<view class="input u-flex">
						<view class="h2">头像</view>
						<view class="info">
							<button class="avatar-wrapper" open-type="chooseAvatar" @chooseavatar="onChooseAvatar">
								<image class="avatar" :src="avatarUrl || '../../../static/head.png'"></image>
							</button>
						</view>
						<u-icon name="arrow-right" size="32rpx" color="#999"></u-icon>
					</view>
					<view class="input u-flex">
						<view class="h2">昵称</view>
						<view class="info">
							<input type="nickname" v-module="nickName" placeholder="请输入昵称" @blur="onBlur" />
						</view>
					</view>
				</view>
				<view class="submit" :style="{background:PrimaryColor}" @click="submit">确定</view>
				<!-- #endif -->
				<!-- #ifdef MP-TOUTIAO -->
				<view class="submit" @click="getTouTiaoInfo">授权登录</view>
				<!-- #endif -->
				<!-- #ifdef MP-ALIPAY -->
				<button class="submit" open-type="getAuthorize" scope="userInfo"
					@getAuthorize="getAlipayInfo">授权登录</button>
				<!-- #endif -->
			</view>
		</u-popup>
	</view>
</template>

<script>
	import {
		mapState,
		mapMutations
	} from 'vuex';
	import courtConfig from '@/config/baseUrl.js'
	import {
		postUserManualLogin,getUserLoginUpAvatar
	} from '@/config/api.js';
	import {uploadFilePromise} from '@/plugins/upload/upload.js'
	export default {
		computed: {
			...mapState(['showWxLogin']),
		},
		data() {
			return {
				PrimaryColor: '#1fba1a', //主题色
				avatarUrl: '', //图片文件地址
				nickName: '',  //名称
				avatarfile:null  //图片内容
			}
		},
		methods: {
			...mapMutations(['setShowWxLogin', 'setUserInfo','setHeadLogo']),
			onClose() {
				this.setShowWxLogin(false);
			},
			// 图片转64代码
			base64(url, type) {
			  return new Promise((resolve, reject) => {
			    wx.getFileSystemManager().readFile({
			      filePath: url, //选择图片返回的相对路径
			      encoding: 'base64', //编码格式
			      success: res => {
			        // resolve('data:image/' + type.toLocaleLowerCase() + ';base64,' + res.data)
			        resolve(res.data)
			      },
			      fail: res => reject(res.errMsg)
			    })
			  })
			},

			onChooseAvatar(e) {
				console.log(e)
				//该图片需要上传到自己服务器--此处没做处理
				this.avatarUrl = e.detail.avatarUrl
				// this.avatarfile=
				this.base64(e.detail.avatarUrl,"jpg").then(rs=>{
					// debugger
					this.avatarfile= rs
					console.log()
					
				})
				
			},
			onBlur(e) {
				console.log(e)
				this.nickName = e.detail.value
			},
			// 头条
			getTouTiaoInfo() {
				var that = this
				uni.getUserProfile({
					success(res) {
						console.log(res.userInfo, 'getUserProfile成功')
						let httpData = {
							nickName: res.userInfo.nickName,
							headLogo: res.userInfo.avatarUrl,
						}
						// uni.$u.http.post('/api/Login_capLogin',httpData).then(res => {
						// 储存登录信息
						let userInfo = {
							headLogo: res.avatarUrl,
							nickName: res.nickName,
							token: true, //token用于判断是否登录
						}
						console.log(userInfo, 'userInfo')
						that.setUserInfo(userInfo)
						uni.$emit("loginCallback") //全局登录监听回调方法
						uni.showToast({
							title: '登录成功~',
							icon: 'none'
						});
						that.onClose();
						// });
					},
					fail(err) {
						console.log(err, `getUserProfile 调用失败`);
					},
				});
			},
			// 支付宝
			getAlipayInfo(e) {
				var that = this
				uni.getOpenUserInfo({
					success: (res) => {
						uni.hideLoading()
						var data = JSON.parse(res.response).response // 以下方的报文格式解析两层 response
						let httpData = {
							nickName: data.nickName || '', //昵称
							headLogo: data.avatar || '', //头像
							gender: data.gender || '', //性别 0:未知 1:男 2:女
						}
						// uni.$u.http.post('/api/Login_capLogin',httpData).then(res => {
						// 储存登录信息
						let userInfo = {
							headLogo: res.avatarUrl,
							nickName: res.nickName,
							token: true, //token用于判断是否登录
						}
						console.log(userInfo, 'userInfo')
						that.setUserInfo(userInfo)
						uni.$emit("loginCallback") //全局登录监听回调方法
						uni.showToast({
							title: '登录成功~',
							icon: 'none'
						});
						that.onClose();
						// });
					},
					fail: (res) => {
						console.log(res, "失败")
					}
				})
			},
			// 微信小程序
			submit() {
				var that = this
				setTimeout(() => {
					if (!this.avatarUrl) {
						uni.showToast({
							title: '请上传头像~',
							icon: 'none'
						});
						return
					}
					if (!this.nickName) {
						uni.showToast({
							title: '请输入昵称~',
							icon: 'none'
						});
						return
					}
					uni.login({
						success(res) {
							let httpData = {
								code: res.code,
								avatar: that.avatarUrl,
								nickname: that.nickName,
								appid: courtConfig.publicAppId,
							};
							// uni.$u.http.post('/api/Login_capLogin',httpData).then(res => {
							postUserManualLogin(httpData).then(r => {
								// 储存登录信息
								let userInfo = {
									nickName: r.data?.puserVo?.nickname,
									user: r.data.puserVo,
									token: r.data.token, //token用于判断是否登录
								}
								console.log(userInfo, 'userInfo')
								that.setUserInfo(userInfo)
								uni.$emit("loginCallback") //全局登录监听回调方法
								uni.showToast({
									title: r.msg,
									icon: 'none'
								});
							
								that.onClose();
								uploadFilePromise(that.avatarUrl,null,null,{Authorization:"Proprietor "+r.data.token}).then(s=>{
								  that.setHeadLogo(s.url)
								 getUserLoginUpAvatar({url:s.url,id:r.data?.puserVo?.userId,ossId:s.ossId}).then(ress => {})
								})
								that.avatarUrl=""
								that.nickName=""
							})
							// });
						}
					})
				}, 100)
			},
		},
	}
</script>

<style lang="scss" scoped>
	.popupBox {
		width: 100%;
		padding: 50rpx 40rpx 30rpx;

		.logo {
			width: 90rpx;
			height: 90rpx;
			border-radius: 18rpx;
			overflow: hidden;

			.img {
				width: 90rpx;
				height: 90rpx;
			}
		}

		.h2 {
			color: #333;
			font-size: 32rpx;
		}

		.h3 {
			font-size: 26rpx;
			color: #999;
			line-height: 1.4;
			padding-top: 14rpx;
		}

		.bold {
			font-weight: bold;
		}

		.form {
			margin-top: 30rpx;
			border-top: 1px solid #EFEFEF;

			.input {
				padding: 24rpx 0;
				border-bottom: 1px solid #EFEFEF;

				.info {
					flex: 1;
					margin-left: 30rpx;

					.avatar-wrapper {
						width: 70rpx;
						height: 70rpx;
						border-radius: 10rpx;

						.avatar {
							width: 70rpx;
							height: 70rpx;
							border-radius: 10rpx;
						}
					}

					input {
						background: transparent;
						width: 100%;
						height: 70rpx;
						color: #333;
						font-size: 30rpx;
					}
				}
			}
		}
	}

	.submit {
		margin-top: 30rpx;
		width: 100%;
		height: 92rpx;
		border-radius: 10rpx;
		text-align: center;
		line-height: 92rpx;
		color: #fff;
		font-size: 32rpx;
		background: #E83F3C;
		border: none;
	}
</style>
