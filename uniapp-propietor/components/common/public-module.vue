<template>
	<view>
		<!-- 加载动画组件 -->
		<f-loading></f-loading>
		<!-- 登录弹窗组件 -->
		<f-login></f-login>
		<!-- 微信登录弹窗组件 -->
		<f-wxLogin></f-wxLogin>
		<!-- 物业认证模态框 -->
		<fAuthentication></fAuthentication>
	</view>
</template>

<script>
	import {
		mapState,
		mapMutations
	} from 'vuex';
	import fLoading from "@/components/common/loading"
	import fLogin from "@/components/module/f-login/f-login.vue"
	import fWxLogin from "@/components/module/f-wxLogin/f-wxLogin.vue"
	import fAuthentication from "@/components/module/f-authentication/f-authentication.vue"
	export default {
		components: {
			fLoading,
			fLogin,
			fWxLogin,
			fAuthentication
		},
		data() {
			return {}
		},
		methods: {

		}
	};
</script>
<style>

</style>
