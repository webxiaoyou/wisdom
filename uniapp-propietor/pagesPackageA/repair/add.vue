<template>
	<view>
		<view class="a-steps m-t-20">

			<view style="margin-bottom: 40rpx;">报修填写</view>
			<view class="upload-container">
				<u-form labelPosition="left" labelWidth="auto" :model="formS" :rules="rules" ref="uForm">
					<u-form-item label="小区:　" prop="propertyName" required borderBottom
						@click="onSelect(0);hideKeyboard()" ref="propertyName">
						<u--input v-model="formS.propertyName" required disabled disabledColor="#ffffff"
							placeholder="请选择小区" border="none"></u--input>
						<u-icon slot="right" name="arrow-right"></u-icon>
					</u-form-item>
					<u-form-item label="单元:　" prop="buildingNumber" required borderBottom
						@click="onSelect(1);hideKeyboard()" ref="buildingNumber">
						<u--input v-model="formS.buildingNumber" required disabled disabledColor="#ffffff"
							placeholder="请选择单元" border="none"></u--input>
						<u-icon slot="right" name="arrow-right"></u-icon>
					</u-form-item>
					<u-form-item label="楼层:　" required prop="floorNumber" borderBottom
						@click="onSelect(2);hideKeyboard()" ref="floorNumber">
						<u--input v-model="formS.floorNumber" disabled disabledColor="#ffffff" placeholder="请选择楼层"
							border="none"></u--input>
						<u-icon slot="right" name="arrow-right"></u-icon>
					</u-form-item>
					<u-form-item label="类型:　" required prop="category" borderBottom
						@click="showCategory = true;hideKeyboard()" ref="category">
						<u--input v-model="formS.category" disabled disabledColor="#ffffff" placeholder="请选择类型"
							border="none"></u--input>
						<u-icon slot="right" name="arrow-right"></u-icon>
					</u-form-item>
					<u-form-item label="标题:　" prop="title" required borderBottom ref="title">
						<u-input v-model="formS.title" placeholder="请输入姓名" border="none"></u-input>
					</u-form-item>
					<u-form-item label="手机号:　" required prop="tel" borderBottom ref="tel">
						<u-input v-model="formS.tel" placeholder="请输入手机号" border="none"></u-input>
					</u-form-item>
					<u-form-item label="内容:　" required prop="description" borderBottom ref="description">
						<u-textarea v-model="formS.description" placeholder="请输入维修内容" count></u-textarea>
					</u-form-item>
					<u-form-item label="图片描述:　" prop="imageUrlss" borderBottom ref="imageUrlss">
						<!-- 添加上传组件，可以使用u-upload或其他组件 -->
						<u-upload :fileList="formS.imageUrlss" @afterRead="afterRead" @delete="deletePic" name="1"
							multiple :maxCount="3"></u-upload>
					</u-form-item>

				</u-form>
			</view>

		</view>
		<view style="width: 100%;margin-top: 80rpx;" class="m-t-20">
			<u-button text="提交" type="primary" color="#2672FA" @click="submit"></u-button>
		</view>
		<u-picker :show="show" ref="uPicker" :columns="columns" @confirm="confirm" keyName="label" @cancel="show=false">
		</u-picker>
		<u-picker :show="showCategory" ref="uPickers" :columns="columnsCategory" @confirm="confirmCategory"
			keyName="dictLabel" @cancel="showCategory=false">
		</u-picker>
	</view>
</template>

<script>
	import {
		mapState,
		mapMutations,
	} from 'vuex'
	import {
		getProperTydictList,
		getBuildingDictList,
		getFloorDictList,
		postPreairAdd,
		getSysDictData
	} from '@/config/api.js';
	import {
		uploadFilePromise
	} from '@/plugins/upload/upload.js'

	export default {
		data() {
			return {
				formS: {
					propertyId: '', //小区ID
					buildingId: '', //楼栋ID
					floorId: '', //楼层ID
					imageUrlss: [], //照片存储
					imageUrls: [],
					status: 0,
					description: '',
					floorNumber: '',
					propertyName: '',
					buildingNumber: '',
					tel: '',
					title: '',
					userId: '',
					category: '',
				}, //物业认证
				rules: { //身份验证规则
					propertyName: [{
						required: true,
						message: '请选择小区',
						trigger: ['change', 'blur']
					}],
					buildingNumber: [{
						required: true,
						message: '请选择楼栋',
						trigger: ['change', 'blur']
					}],
					floorNumber: [{
						required: true,
						message: '请选择楼层',
						trigger: ['change', 'blur']
					}],
					tel: [{
							required: true,
							message: '请输入手机号',
							trigger: ['change', 'blur']
						},
						{
							min: 11,
							message: '请输入11位的手机号'
						}
					],
					title: [{
						required: true,
						message: '请输入姓名',
						trigger: ['change', 'blur']
					}],
					description: [{
						required: true,
						message: '请输入内容',
						trigger: ['change', 'blur']
					}]
				},
				show: false,
				showCategory: false, //弹框 类型
				columns: [],
				columnsCategory: [], //弹框数据内容
				dictValues: [], // 请求字典得到的内容
				pickerStatic: 0, //记录选中状态
			}
		},

		computed: {
			...mapState(['PrimaryColor', 'userInfo']),
		},
		onLoad() {
			this.RProperTydictList();
			this.SysDictData();
		},
		methods: {
			...mapMutations(['PrimaryColor', 'userInfo', 'setUserInfo']),

			//弹出类型框回调
			confirmCategory(e) {
				this.$set(this.formS, 'category', e.value[0].dictLabel) //小区ID
				this.showCategory = false;
			},
			//弹出框回调
			confirm(e) {
				console.log('confirm', e)
				if (this.pickerStatic == 0) {
					this.$set(this.formS, 'propertyId', e.value[0].value) //小区ID
					this.$set(this.formS, 'propertyName', e.value[0].label) //小区名称
					this.formS.buildingId = '';
					this.formS.buildingNumber = '';
					this.formS.floorId = '';
					this.formS.floorNumber = '';
					this.dictValues[2] = [];
					this.RBuildingDictList(e.value[0].value);
				} else if (this.pickerStatic == 1) {
					this.$set(this.formS, 'buildingId', e.value[0].value) //单元ID
					this.$set(this.formS, 'buildingNumber', e.value[0].label) //单元名称
					this.RFloorDictList(e.value[0].value);
				} else if (this.pickerStatic == 2) {
					this.$set(this.formS, 'floorId', e.value[0].value) //楼层ID
					this.$set(this.formS, 'floorNumber', JSON.stringify(e.value[0].label)) //楼层名称
				}
				this.show = false
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
			hideKeyboard() {
				uni.hideKeyboard()
			},
			// 删除图片
			deletePic(event) {
				this.formS[`imageUrlss`].splice(event.index, 1)
			},
			SysDictData() {
				getSysDictData(
					'prepair_category'
				).then(res => {
					this.$set(this, 'columnsCategory', [res.data]) //小区名称
					// this.columnsCategory = res.data
				})
			},
			// 新增图片
			async afterRead(event) {

				// 当设置 multiple 为 true 时, file 为数组格式，否则为对象格式
				let lists = [].concat(event.file)
				let fileListLen = this.formS[`imageUrlss`].length
				lists.map((item) => {
					this.formS[`imageUrlss`].push({
						...item,
						status: 'uploading',
						message: '上传中'
					})
				})
				for (let i = 0; i < lists.length; i++) {
					const result = await this.uploadPhotoAndSubmit(lists[i].url)
					let item = this.formS[`imageUrlss`][fileListLen]
					this.formS[`imageUrlss`].splice(fileListLen, 1, Object.assign(item, {
						status: 'success',
						message: '',
						url: result.url
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
						resolve(uploadRes)
					}).catch(err => {
						uni.$u.toast('照片上传失败');
					});
				})

			},
			submit() {
				this.$refs.uForm.validate().then(res => {
					// console.log(res)
					this.formS.imageUrlss.forEach((s => {
						this.formS.imageUrls.push(s.url)
					}))
					this.formS.userId = this.userInfo.user.userId
					this.PreairAdd();
				}).catch(errors => {
					uni.$u.toast('校验失败')
				})
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
			PreairAdd() {
				if (this.formS.imageUrlss.length === 0) {
					this.formS.imageUrls = []
				} else {
					this.formS.imageUrls.join(',')
				}
				postPreairAdd(this.formS).then(res => {
					if (res.code === 200) {
						setTimeout(() => {
							uni.navigateBack({
								delta: 1
							})
						}, 1000)
						uni.$u.toast('提交成功')
					}
				})
			},
		},
		onReady() {
			//如果需要兼容微信小程序，并且校验规则中含有方法等，只能通过setRules方法设置规则。
			this.$refs.uForm.setRules(this.rules)
		},
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

	/deep/ .u-form {

		width: 100%;

	}
</style>
