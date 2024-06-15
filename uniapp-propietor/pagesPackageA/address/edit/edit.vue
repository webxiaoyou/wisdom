<template>
	<view>
		<view class="content">
			<view class="row u-flex">
				<view class="nominal">
					收件人
				</view>
				<view class="input">
					<input placeholder="请输入收件人姓名" type="text" v-model="name" />
				</view>
			</view>
			<view class="row u-flex">
				<view class="nominal">
					电话号码
				</view>
				<view class="input">
					<input placeholder="请输入收件人电话号码" type="text" v-model="tel" />
				</view>
			</view>
			<view class="row u-flex">
				<view class="nominal">
					所在地区
				</view>
				<view class="input" @tap="chooseCity">
					{{region.label}}
				</view>
				
			</view>
			<view class="row u-flex">
				<view class="nominal">
					详细地址
				</view>
				<view class="input">
					<textarea v-model="detailed" auto-height="true" placeholder="输入详细地址"></textarea>
				</view>
			</view>
			<view class="row u-flex">
				<view class="nominal">
					设置默认地址
				</view>
				<view class="input switch">
					<switch color="#f06c7a" :checked="isDefault" @change="isDefaultChange" />
				</view>
			</view>
			<view class="row u-flex" v-if="editType=='edit'" @tap="del">
				<view class="del">
					删除收货地址
				</view>
			</view>
		</view>
		<view class="save u-flex" :style="{paddingBottom:systemInfo.tabbarPaddingB+'px'}" @tap="save">
			<view class="btn u-flex">
				保存地址
			</view>
		</view>
        <f-city-picker ref="fCityPicker" :pickerValueDefault="cityPickerValue" @onConfirm="onConfirm" themeColor="#007AFF"></f-city-picker>
	</view>
</template>

<script>
import base from '@/config/baseUrl.js';
import fCityPicker from '@/components/module/f-city-picker/f-city-picker.vue'
export default {
    components: {
        fCityPicker
    },
    data() {
        return {
            systemInfo:base.systemInfo,
            editType:'edit',
            id:'',
            name:'',
            tel:'',
            detailed:'',
            isDefault:false,
            cityPickerValue: [0, 0, 0],
            region:{label:"请点击选择地址",value:[],cityCode:""}
        };
    },
    onLoad(e) {
        //获取传递过来的参数
        this.editType = e.type;
        if(e.type=='edit'){
            uni.getStorage({
                key:'address',
                success: (e) => {
                    console.log(e.data.address.region.value,'addressgetStorage')
                    this.id = e.data.id;
                    this.name = e.data.name;
                    this.tel = e.data.tel;
                    this.detailed = e.data.address.detailed;
                    this.isDefault = e.data.isDefault;
                    this.cityPickerValue = e.data.address.region.value;
                    this.region = e.data.address.region;
                }
            })
        }
        
    },
    methods: {
        chooseCity() {
            this.$refs.fCityPicker.showPicker()
        },
        onConfirm(e) {
            console.log(e)
            this.region = {
                label:e.label,
                value:e.value,
                cityCode:e.areaCode,
            };
            this.cityPickerValue = e.value;
            console.log(this.region,'this.region')
        },
        isDefaultChange(e){
            this.isDefault = e.detail.value;
        },
        del(){
            uni.showModal({
                title: '删除提示',
                content: '你将删除这个收货地址',
                success: (res)=>{
                    if (res.confirm) {
                        uni.setStorage({
                            key:'delAddress',
                            data:{id:this.id},
                            success() {
                                uni.navigateBack();
                            }
                        })
                    } else if (res.cancel) {
                        console.log('用户点击取消');
                    }
                }
            });
            
        },
        save(){
            let data={"name":this.name,"head":this.name.substr(0,1),"tel":this.tel,"address":{"region":this.region,"detailed":this.detailed},"isDefault":this.isDefault}
            if(this.editType=='edit'){
                data.id = this.id
            }
            if(!data.name){
                uni.showToast({title:'请输入收件人姓名',icon:'none'});
                return ;
            }
            if(!data.tel){
                uni.showToast({title:'请输入收件人电话号码',icon:'none'});
                return ;
            }
            if(!data.address.detailed){
                uni.showToast({title:'请输入收件人详细地址',icon:'none'});
                return ;
            }
            if(data.address.region.value.length==0){
                uni.showToast({title:'请选择收件地址',icon:'none'});
                return ;
            }
            uni.showLoading({
                title:'正在提交'
            })
            //实际应用中请提交ajax,模板定时器模拟提交效果
            setTimeout(()=>{
                uni.setStorage({
                    key:'saveAddress',
                    data:data,
                    success() {
                        uni.hideLoading();
                        uni.navigateBack();
                    }
                })
            },300)
            
            
        }
    },
    

};
</script>
<style lang="scss">
.save{
    position: fixed;
    bottom: 0;
    width: 100%;
    height: 120upx;
    display: flex;
    justify-content: center;
    align-items: center;
    .btn{
        box-shadow: 0upx 5upx 10upx rgba(0,0,0,0.4);
        width: 70%;
        height: 80upx;
        border-radius: 80upx;
        background-color: #f06c7a;
        color: #fff;
        justify-content: center;
        align-items: center;
        .icon{
            height: 80upx;
            color: #fff;
            font-size: 30upx;
            justify-content: center;
            align-items: center;
        }
        font-size: 30upx;
    }
}
.content{
    display: flex;
    flex-wrap: wrap;
    view{
        display: flex;
    }
    .row{
        width: 100%;
        padding: 0 24rpx;
        border-top: solid 1upx #eee;
        .nominal{
            width: 30%;
            height: 120upx;
            font-weight: 200;
            font-size: 30upx;
            align-items: center;
        }
        .input{
            width: 70%;
            padding: 20upx 0;
            align-items: center;
            font-size: 30upx;
            &.switch{
                justify-content: flex-end;
            }
            .textarea{
                margin: 20upx 0;
                min-height: 120upx;
            }
        }
        .del{
            width: 100%;
            height: 100upx;
            justify-content: center;
            align-items: center;
            font-size: 36upx;
            color: #f06c7a;
            background-color: rgba(255,0,0,0.05);
            border-bottom: solid 1upx #eee;
        }
    }
}
	
</style>
