<template>
	<view>
		<u-alert title="为保证您的账户安全，请先完成实名认证" type="warning"></u-alert>
		<u-alert :title="formS.remark" type="error" v-if="userInfo.user.authenticationStatus=='2'"></u-alert>
		<view class="u-p-24">
			<view class="a-steps">
				<u-steps :current="stepsStatic">
					<u-steps-item title="身份验证"></u-steps-item>
					<u-steps-item title="业主认证"></u-steps-item>
					<u-steps-item title="认证完成"></u-steps-item>
				</u-steps>
			</view>
			<view class="a-steps m-t-20" v-if="stepsStatic===0">
				<view style="margin-bottom: 40rpx;">证件信息</view>
				<view class="upload-container">
					<u-form labelPosition="left" labelWidth="auto" :model="form" :rules="rules" ref="uForm">
						<u-form-item label="姓名:　" required prop="userName" borderBottom ref="userName">
							<u-input v-model="form.userName" placeholder="请输入姓名" border="none"></u-input>
						</u-form-item>
						<u-form-item label="手机号码:　" required prop="tel" borderBottom ref="tel">
							<u-input v-model="form.tel" placeholder="请输入姓名" border="none"></u-input>
						</u-form-item>
						<u-form-item label="身份证号:　" required prop="identity" borderBottom ref="identity">
							<u-input v-model="form.identity" border="none" placeholder="请输入身份证号"
								@click="keyCardShow = true" />
							<u-keyboard ref="uKeyboardCard" mode="identity" v-model="keyCardShow"
								@change="valChangeCard" @backspace="backspaceCard" :mask="false">
							</u-keyboard>
						</u-form-item>
					</u-form>
				</view>
			</view>
			<view class="a-steps m-t-20" v-if="stepsStatic===1">
				<view v-if="userInfo.user.authenticationStatus=='3'">
					<u-empty text="正在审核中" mode="list" icon="http://cdn.uviewui.com/uview/empty/list.png">
					</u-empty>
				</view>
				<view v-else>
					<view style="margin-bottom: 40rpx;">证件信息</view>
					<view class="upload-container">
						<u-form labelPosition="left" labelWidth="auto" :model="formS" :rules="rulesS" ref="uFormS">
							<u-form-item label="小区:　" required prop="propertyName" borderBottom
								@click="onSelect(0);hideKeyboard()" ref="propertyName">
								<u--input v-model="formS.propertyName" disabled disabledColor="#ffffff"
									placeholder="请选择小区" border="none"></u--input>
								<u-icon slot="right" name="arrow-right"></u-icon>
							</u-form-item>
							<u-form-item label="楼栋:　" required prop="buildingNumber" borderBottom
								@click="onSelect(1);hideKeyboard()" ref="buildingNumber">
								<u--input v-model="formS.buildingNumber" disabled disabledColor="#ffffff"
									placeholder="请选择楼栋" border="none"></u--input>
								<u-icon slot="right" name="arrow-right"></u-icon>
							</u-form-item>
							<u-form-item label="楼层:　" required prop="floorNumber" borderBottom
								@click="onSelect(2);hideKeyboard()" ref="floorNumber">
								<u--input v-model="formS.floorNumber" disabled disabledColor="#ffffff"
									placeholder="请选择楼层" border="none"></u--input>
								<u-icon slot="right" name="arrow-right"></u-icon>
							</u-form-item>
							<u-form-item label="房产信息:　" required prop="imageUrlArr" borderBottom ref="imageUrlArr">
								<!-- 添加上传组件，可以使用u-upload或其他组件 -->
								<u-upload :fileList="formS.imageUrlArr" @afterRead="afterRead" @delete="deletePic"
									name="1" multiple :maxCount="1"></u-upload>
							</u-form-item>

						</u-form>
					</view>
				</view>
			</view>
			<view style="width: 100%;margin-top: 80rpx;">
				<u-button text="提交" type="primary" color="#2672FA" @click="submit"
					v-if="userInfo.user.authenticationStatus!='3' && stepsStatic!=2"></u-button>
				<u-button text="返回上一层" type="primary" color="#5dd382" @click="onReturn" v-if="stepsStatic==2">
				</u-button>
			</view>
		</view>
		<u-picker :show="show" ref="uPicker" :columns="columns" @confirm="confirm" keyName="label" @cancel="show=false">
		</u-picker>
	</view>
</template>

<script>
	import {
		mapState,
		mapMutations,
	} from 'vuex'
	import {
		postUserAuthentication,
		getProperTydictList,
		getBuildingDictList,
		getFloorDictList,
		postUserAuthenticationUnit,
		getUserAuthenticationUnitId
	} from '@/config/api.js';
	import {
		uploadFilePromise
	} from '@/plugins/upload/upload.js'
	export default {
		computed: {
			...mapState(['PrimaryColor', 'userInfo']),
		},
		data() {
			return {
				stepsStatic: 0, //当前所在进度
				form: { //身份验证表单内容
					userName: '',
					tel: '',
					identity: '',
					userId: '',
					authenticated: 0
				},
				rules: { //身份验证规则
					userName: [{
							required: true,
							message: '请输入姓名',
							trigger: ['blur', 'change'],
						},
						// 如果需要，可以添加其他规则
					],
					tel: [{
							required: true,
							message: '请输入手机号',
							trigger: ['blur', 'change'],
						},
						{
							pattern: /^1[0-9]{10}$/,
							message: '手机号格式不正确',
							trigger: ['blur', 'change'],
						},
					],
					identity: [{
							required: true,
							message: '请输入身份证',
							trigger: ['blur', 'change'],
						},
						{
							// 自定义验证函数
							validator: (rule, value, callback) => {
								// 返回true表示校验通过，返回false表示不通过
								return this.$u.test.idCard(value)
							},
							message: '身份证不正确',
							// 触发器可以同时用blur和change
							trigger: ['blur', 'change'],
						}
					],


				},
				show: false,
				loading: false,
				columns: [],
				dictValues: [], // 请求字典得到的内容
				pickerStatic: 0, //记录选中状态
				formS: {
					propertyId: '', //小区ID
					propertyName: '', //小区名称
					buildingNumber: '', //楼栋编号
					buildingId: '', //楼栋ID
					floorNumber: '', //楼层编号
					floorId: '', //楼层ID
					imageUrl: '', //房产照片
					imageUrlArr: [], //房产存储
					ownerId: '',
					remark: '',
					authenticationStatus: 0,
				}, //物业认证
				rulesS: { //身份验证规则
					propertyName: [{
							required: true,
							message: '请选择物业小区',
							trigger: ['blur', 'change'],
						},
						// 如果需要，可以添加其他规则
					],
					buildingNumber: [{
							required: true,
							message: '请选择小区单元',
							trigger: ['blur', 'change'],
						},
						// 如果需要，可以添加其他规则
					],
					floorNumber: [{
							required: true,
							message: '请选择单元楼层',
							trigger: ['blur', 'change'],
						},
						// 如果需要，可以添加其他规则
					],
					imageUrlArr: [{
							required: true,
							message: '请上传房产证件',
							trigger: ['blur', 'change'],
							type: 'array', // 声明类型为数组
						},
						// 如果需要，可以添加其他规则
					],

				},
			};
		},
		onLoad() {
			this.RProperTydictList();
			if (this.userInfo.user.authenticated == "1") {
				this.stepsStatic = 0
				return;
			} else if (this.userInfo.user.authenticationStatus != "1") {
				this.RUserAuthenticationUnitId();
				this.stepsStatic = 1
				return;
			} else {
				this.RUserAuthenticationUnitId();
				this.stepsStatic = 2
				return;
			}
		},
		mounted() {},
		methods: {
			...mapMutations(['PrimaryColor', 'userInfo', 'setUserInfo']),
			onReturn() {
				uni.navigateBack({
					delta: 1
				})
			},
			RUserAuthenticationUnitId() {
				getUserAuthenticationUnitId(this.userInfo?.user?.userId).then(res => {
					if (res.data) {
						const {
							propertyId,
							propertyName,
							buildingNumber,
							buildingId,
							floorNumber,
							floorId,
							imageUrl,
							ownerId,
							authenticationStatus,
							remark,
							unitId
						} = res.data;
						this.formS = {
							propertyId,
							propertyName,
							buildingNumber,
							buildingId,
							floorNumber: JSON.stringify(floorNumber),
							floorId,
							imageUrl,
							ownerId,
							authenticationStatus,
							remark,
							imageUrlArr: [],
							unitId
						};
						if (authenticationStatus == '1') {
							this.userInfo.user.propertyId = propertyId
							this.userInfo.user.propertyName = propertyName,
								this.userInfo.user.buildingNumber = buildingNumber,
								this.userInfo.user.buildingId = buildingId,
								this.userInfo.user.floorNumber = JSON.stringify(floorNumber),
								this.userInfo.user.floorId = floorId,
								this.userInfo.user.imageUrl = imageUrl,
								this.userInfo.user.ownerId = ownerId,
								this.stepsStatic = 2
						}
						// 修改authenticated属性为0
						this.userInfo.user.authenticationStatus = authenticationStatus;
						// 触发mutation，更新userInfo
						this.setUserInfo(this.userInfo);
						// 其他代码...
					}


					console.log(res)
				})
			},
			valChangeCard(val) {
				// 将每次按键的值拼接到value变量中，注意+=写法
				this.form.identity += val;
				this.triggerSingleFieldValidate("identity")
			},
			// 退格键被点击
			backspaceCard() {
				// 删除value的最后一个字符
				if (this.form.identity.length) this.form.identity = this.form.identity.substr(0, this.form.identity
					.length - 1);
				this.triggerSingleFieldValidate("identity")
			},
			//验证
			triggerSingleFieldValidate(refName) {
				setTimeout(() => {
					this.$refs[refName].onFieldChange()
				}, 200)
			},
			submit() {

				if (this.stepsStatic == 0) {
					this.$refs.uForm.validate().then(res => {
						this.userAuthentication();
					}).catch(errors => {
						uni.$u.toast('校验失败')
					})
				} else {
					this.$refs.uFormS.validate().then(res => {
						// uni.$u.toast('校验通过')
						this.userAuthenticationUnit();
					}).catch(errors => {
						uni.$u.toast('校验失败')
					})
				}


			},

			//身份认证提交
			userAuthentication() {
				this.form.userId = this.userInfo?.user?.userId
				postUserAuthentication(this.form).then((res) => {
					// 修改authenticated属性为0
					this.userInfo.user.authenticated = '0';
					this.userInfo.user.identity = this.form.identity;
					this.userInfo.user.userName = this.form.userName;
					this.userInfo.user.tel = this.form.tel;

					// 触发mutation，更新userInfo
					this.setUserInfo(this.userInfo);
					this.stepsStatic = 1
					uni.$u.toast(res.msg)
				}).catch((err) => {
					uni.$u.toast(err)
				})
			},
			//业主认证提交
			userAuthenticationUnit() {
				this.formS.ownerId = this.userInfo?.user?.userId
				this.formS.authenticationStatus = 3
				postUserAuthenticationUnit(this.formS).then((res) => {
					// 修改authenticated属性为0
					this.userInfo.user.authenticationStatus = '3';

					// 触发mutation，更新userInfo
					this.setUserInfo(this.userInfo);
					this.stepsStatic = 1
					uni.$u.toast(res.msg)
				}).catch((err) => {
					uni.$u.toast(err)
				})
			},
			//业主内容
			// 回调参数为包含columnIndex、value、values
			confirm(e) {
				console.log('confirm', e)
				if (this.pickerStatic == 0) {
					this.formS.propertyId = e.value[0].value; //小区ID
					this.formS.propertyName = e.value[0].label; //小区名称
					this.formS.buildingId = '';
					this.formS.buildingNumber = '';
					this.formS.floorId = '';
					this.formS.floorNumber = '';
					this.dictValues[2] = [];
					this.RBuildingDictList(e.value[0].value);
				} else if (this.pickerStatic == 1) {
					this.formS.buildingId = e.value[0].value; //小区ID
					this.formS.buildingNumber = e.value[0].label; //小区名称
					this.RFloorDictList(e.value[0].value);
				} else if (this.pickerStatic == 2) {
					this.formS.floorId = e.value[0].value; //小区ID
					this.formS.floorNumber = JSON.stringify(e.value[0].label); //小区名称
				}
				this.show = false
			},
			//选择弹框
			onSelect(e) {
				this.pickerStatic = e;
				this.columns = []
				if (e == 0) {
					this.columns = [this.dictValues[0]]
				} else if (e == 1) {
					this.columns = [this.dictValues[1]]
				} else if (e == 2) {
					this.columns = [this.dictValues[2]]
				}
				this.show = true
			},
			//小区字典
			RProperTydictList() {
				getProperTydictList().then(res => {
					this.dictValues[0] = res.data
					console.log(res)
				})
			},
			//小区栋数字典
			RBuildingDictList(e) {
				getBuildingDictList({
					params: {
						propertyId: e
					}
				}).then(res => {
					this.dictValues[1] = res.data
					console.log(res)
				})
			},
			// 删除图片
			deletePic(event) {
				this.formS[`imageUrlArr`].splice(event.index, 1)
			},
			//小区楼层字典
			RFloorDictList(e) {
				getFloorDictList({
					params: {
						buildingId: e
					}
				}).then(res => {
					this.dictValues[2] = res.data
					console.log(res)
				})
			},
			// 新增图片
			async afterRead(event) {

				// 当设置 multiple 为 true 时, file 为数组格式，否则为对象格式
				let lists = [].concat(event.file)
				let fileListLen = this.formS[`imageUrlArr`].length
				lists.map((item) => {
					this.formS[`imageUrlArr`].push({
						...item,
						status: 'uploading',
						message: '上传中'
					})
				})
				for (let i = 0; i < lists.length; i++) {
					const result = await this.uploadPhotoAndSubmit(lists[i].url)
					let item = this.formS[`imageUrlArr`][fileListLen]
					this.formS[`imageUrlArr`].splice(fileListLen, 1, Object.assign(item, {
						status: 'success',
						message: '',
						url: result
					}))
					fileListLen++
				}
			},
			//图片上传
			uploadPhotoAndSubmit(url) {
				// const filePath = this.formS.imageUrl[0].path;
				const formData = {
					Authorization: "Proprietor " + this.userInfo.token
				};
				return new Promise((resolve, reject) => {
					// 调用你的上传文件接口
					uploadFilePromise(url, null, null, formData).then(uploadRes => {
						this.formS.imageUrl = uploadRes.url
						resolve(uploadRes)
					}).catch(err => {
						uni.$u.toast('照片上传失败');
					});
				})

			},
		},
		onReady() {
			//如果需要兼容微信小程序，并且校验规则中含有方法等，只能通过setRules方法设置规则。
			this.$refs.uForm.setRules(this.rules);
			this.$refs.uFormS.setRules(this.rulesS); // 使用不同的规则对象
		},
	};
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
		margin-top: 40rpx;
		padding: 40rpx;
	}

	.upload-container {
		width: 100%;
		// background-color: #303133;
		display: flex;
		justify-content: space-between;
	}

	/deep/ .u-form {

		width: 100%;

	}
</style>
