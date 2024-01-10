<template>
	<view>
		<view class="content">
			<view class="list">
				<view class="row u-flex" v-for="(row,index) in addressList" :key="index" @tap="select(row)">
					<view class="left">
						<view class="head u-flex">
							{{row.head}}
						</view>
					</view>
					<view class="center">
						<view class="name-tel u-flex">
							<view class="name">{{row.name}}</view>
							<view class="tel">{{row.tel}}</view>
							<view class="default" v-if="row.isDefault">
								默认
							</view>
						</view>
						<view class="address">
							{{row.address.region.label}} {{row.address.detailed}}
						</view>
					</view>
					<view class="right">
						<view class="icon u-flex" @tap.stop="edit(row)">
							<f-icon name="edit" size="40"></f-icon>
						</view>
					</view>
				</view>
			</view>
		</view>
		<view class="add u-flex" :style="{paddingBottom:systemInfo.tabbarPaddingB+'px'}">
			<view class="btn u-flex" @tap="add">
				<view class="icon tianjia"></view>新增地址
			</view>
		</view>
	</view>
</template>
<script>
import base from '@/config/baseUrl.js';
export default {
    data() {
        return {
            systemInfo:base.systemInfo,
            isSelect:false,
            addressList:[
                {id:1,name:"小凡",head:"小",tel:"18816881688",address:{region:{"label":"浙江省-温州市-鹿城区","value":[10,2,0],"cityCode":"330302"},detailed:'深南大道1111号无名摩登大厦6楼A2'},isDefault:true},
                {id:2,name:"老王",head:"老",tel:"15812341234",address:{region:{"label":"广东省-深圳市-福田区","value":[18,2,1],"cityCode":"440304"},detailed:'深北小道2222号有名公寓502'},isDefault:false},
                {id:3,name:"大佬",head:"大",tel:"18155467897",address:{region:{"label":"广东省-深圳市-福田区","value":[18,2,1],"cityCode":"440304"},detailed:'深南大道1111号无名摩登大厦6楼A2'},isDefault:false},
                {id:4,name:"王小小",head:"王",tel:"13425654895",address:{region:{"label":"广东省-深圳市-福田区","value":[18,2,1],"cityCode":"440304"},detailed:'深南大道1111号无名摩登大厦6楼A2'},isDefault:false},
            ]
        };
    },
    onShow() {
        uni.getStorage({
            key:'delAddress',
            success: (e) => {
                let len = this.addressList.length;
                if(e.data.hasOwnProperty('id')){
                    for(let i=0;i<len;i++){
                        if(this.addressList[i].id==e.data.id){
                            this.addressList.splice(i,1);
                            break;
                        }
                    }
                }
                uni.removeStorage({
                    key:'delAddress'
                })
            }
        })
        uni.getStorage({
            key:'saveAddress',
            success: (e) => {
                let len = this.addressList.length;
                if(e.data.hasOwnProperty('id')){
                    for(let i=0;i<len;i++){
                        if(this.addressList[i].id==e.data.id){
                            this.addressList.splice(i,1,e.data);
                            break;
                        }
                    }
                }else{
                    let lastid = this.addressList[len-1];
                    lastid++;
                    e.data.id = lastid;
                    this.addressList.push(e.data);
                }
                uni.removeStorage({
                    key:'saveAddress'
                })
            }
        })
    },
    onLoad(e) {
        if(e.type=='select'){
            this.isSelect = true;
        }
    },
    methods:{
        edit(row){
            uni.setStorage({
                key:'address',
                data:row,
                success() {
                    uni.navigateTo({
                        url:"edit/edit?type=edit"
                    })
                }
            });
            
        },
        add(){
            uni.navigateTo({
                url:"edit/edit?type=add"
            })
        },
        select(row){
            //是否需要返回地址(从订单确认页跳过来选收货地址)
            if(!this.isSelect){
                return ;
            }
            uni.setStorage({
                key:'selectAddress',
                data:row,
                success() {
                    uni.navigateBack();
                }
            })
        }
    }
}
</script>

<style lang="scss" scoped>
	.add{
		position: fixed;
		bottom: 0;
		width: 100%;
		height: 120upx;
		justify-content: center;
		.btn{
			box-shadow: 0upx 5upx 10upx rgba(0,0,0,0.4);
			width: 70%;
			height: 80upx;
			border-radius: 80upx;
			background-color: #f06c7a;
			color: #fff;
			justify-content: center;
			.icon{
				height: 80upx;
				color: #fff;
				font-size: 30upx;
				justify-content: center;
				
			}
			font-size: 30upx;
		}
	}
	.list{
		flex-wrap: wrap;
		.row{
			padding: 20upx 24upx;
			.left{
				width: 90upx;
				flex-shrink: 0;
				.head{
					width: 70upx;
					height: 70upx;
					background:linear-gradient(to right,#ccc,#aaa);
					color: #fff;
					justify-content: center;
					border-radius: 60upx;
					font-size: 35upx;
				}
			}
			.center{
				width: 100%;
				flex-wrap: wrap;
				.name-tel{
					width: 100%;
					align-items: baseline;
					.name{
						font-size: 34upx;
					}
					.tel{
						margin-left: 30upx;
						font-size: 24upx;
						color: #777;
					}
					.default{
						font-size: 22upx;
						background-color: #f06c7a;
						color: #fff;
						padding: 0 18upx;
						border-radius: 24upx;
						margin-left: 20upx;
					}
				}
				.address{
					width: 100%;
					font-size: 24upx;
					align-items: baseline;
					color: #777;
				}
			}
			.right{
				flex-shrink: 0;
				margin-left: 20upx;
				.icon{
					justify-content: center;
					width: 80upx;
					height: 60upx;
					border-left: solid 1upx #aaa;
					font-size: 40upx;
					color: #777;
				}
			}
		}
	}
</style>
