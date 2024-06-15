<template>
	<view class="u-p-24">
		<view class="u-box " style="padding: 40rpx;">
			<u-subsection :list="list" mode="subsection" :current="form.isSuggest" @change="sectionChange">
			</u-subsection>
			<!-- <u-radio-group v-model="form.isSuggest" placement="row">
				<u-radio name="0" label="投诉"  :customStyle="{marginRight: '40rpx'}"></u-radio>
				
				<u-radio name="1" label="建议"></u-radio>
			</u-radio-group> -->
			<view style="margin-bottom: 80rpx;"></view>
			<u--form labelPosition="top" :model="form" :rules="rules" labelWidth="auto" ref="uForm">
				<u-form-item label="填写内容" prop="content" borderBottom ref="content">
					<u--textarea placeholder="请输入你的投诉内容/建议内容" v-model="form.content" count></u--textarea>
				</u-form-item>
				<u-button @click="submit" type="primary" color="#2672FA" :customStyle="{marginTop:'40rpx'}">提交
				</u-button>
			</u--form>
		</view>

	</view>
</template>

<script>
	import {
		mapState,
		mapMutations,
	} from 'vuex'
	import {
		postSuggestionAdd
	} from '@/config/api.js';
	export default {
		data() {
			return {
				list: ['投诉', '建议'],
				form: {
					isSuggest: 0,
					propertyId: '',
					userId: '',
					content: '',

				},
				rules: {
					content: {
						type: 'string',
						required: true,
						message: '请填投诉内容',
						trigger: ['blur', 'change']
					}

				},
			}
		},
		computed: {
			...mapState(['PrimaryColor', 'userInfo']),
		},
		methods: {
			...mapMutations(['PrimaryColor', 'userInfo', 'setUserInfo']),
			//投诉/建议状态
			sectionChange(index) {
				this.form.isSuggest = index;
			},
			//表单提交
			submit() {
				this.$refs.uForm.validate().then(res => {
					this.form.userId = this.userInfo?.user?.userId
					this.form.propertyId = this.userInfo?.user?.propertyId
					postSuggestionAdd(this.form).then(res => {
						uni.$u.toast('提交成功')
						setTimeout(() => {
							uni.navigateBack({
								delta: 1
							})
						}, 1000)
					})
				}).catch(errors => {
					uni.$u.toast('校验失败')
				})
			}
		},
		onReady() {
			//如果需要兼容微信小程序，并且校验规则中含有方法等，只能通过setRules方法设置规则。
			this.$refs.uForm.setRules(this.rules)
		},
	}
</script>

<style scoped lang="less">
	.u-box {
		width: 100%;
		background-color: #FFFFFF;
		padding: 40rpx 0;
		box-sizing: border-box;
		border-radius: 20rpx;
	}
</style>
