<template>
	<view>
		<u-modal :show="authenticationModalShow" width="500rpx" :showConfirmButton="false" closeOnClickOverlay
			@close="close">
			<template #default>
				<view class="u-flex u-flex-column ">
					<image src="@/static/tenement/kong/authentication.svg" mode="" style="margin-top: -80rpx;"></image>
					<view class="title">您还未进行业主认证</view>
					<view class="description">业主认证，可享受更多功能</view>
					<view class="btn">
						<u-button text="立即业主认证" type="primary" color="#2672FA" @click="toAuthentication"></u-button>
					</view>
				</view>
			</template>
		</u-modal>
	</view>
</template>

<script>
	import {
		mapState,
		mapMutations
	} from 'vuex'
	export default {

		data() {
			return {}
		},
		computed: {
			...mapState(['PrimaryColor', 'userInfo', 'authenticationModalShow']),
		},
		watch: {},
		methods: {
			...mapMutations(['setAuthenticationModalShow','userInfo']),
			close() {
				console.log('closeLogin')
				this.setAuthenticationModalShow(false);

			},
			toAuthentication(){
				uni.navigateTo({
					url:'/pagesPackageA/authentication/authentication'
				})
			}
		}
	}
</script>

<style scoped lang="less">
	.title {
		margin-top: -100rpx;
		margin-bottom: 20rpx;
		font-weight: bold;

	}

	.description {
		font-size: 28rpx;
		color: #808080;

	}

	.btn {
		width: 50%;
		margin-top: 60rpx;
		// padding: 20rpx;
		// margin: 20rpx 20rpx;
	}
</style>
