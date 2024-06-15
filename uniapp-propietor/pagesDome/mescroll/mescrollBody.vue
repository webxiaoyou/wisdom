<template>
    <view class="page">
        <!-- 分页更多用法请查看官网 http://www.mescroll.com-->
        <mescroll-body ref="mescrollRef" @init="mescrollInit" :down="downOption" @down="downCallback" @up="upCallback">
            <goods-list :list="list"></goods-list>
        </mescroll-body>
    </view>
</template>

<script>
import MescrollMixin from "@/uni_modules/mescroll-uni/components/mescroll-uni/mescroll-mixins.js";
import goodsList from "./module/goods-list.vue";
export default {
    mixins: [MescrollMixin], // 使用mixin (在main.js注册全局组件)
    components:{
        goodsList
    },
    data() {
        return {
            downOption: {
            	auto: false //是否在初始化后,自动执行downCallback; 默认true
            },
            list:[],//商品数据
        }
    },
    onLoad() {
        
    },
    methods: {
        /*上拉加载的回调: 其中page.num:当前页 从1开始, page.size:每页数据条数,默认10 */
        upCallback(page) {
        	//联网加载数据
        	this.loadData(page.num)
        },
        // 特价商品
        loadData(pageNo) {
            let httpData = {
                shopId: '1275',
                pageNo: pageNo,
                row: 10,
            }
            uni.$u.http.get('/edcapi/Goods_goodsList', {params: httpData, custom: {load:false}}).then(res => {
                uni.stopPullDownRefresh();
                //方法一(推荐): 后台接口有返回列表的总页数 totalPage
                this.mescroll.endByPage(res.length, res.pages); //必传参数(当前页的数据个数, 总页数)
                if (pageNo == 1) {
                	this.list = res
                } else {
                	this.list = this.list.concat(res);
                }
            }).catch(() => {
                //联网失败, 结束加载
                this.mescroll.endErr();
            });
        },
    },
}
</script>

<style scoped lang="scss">
.page{
    padding-top:2rpx;
}
.goodsBox{
    padding: 50rpx 32rpx;
    border-bottom: 2rpx solid #f5f5f5;
    background:#fff;
    .goodsImg{
        width: 80rpx;
        height: 80rpx;
        border-radius: 40rpx;
    }
    .info{
        height: 80rpx;
        flex-direction: column;
        justify-content: space-between;
        align-items: flex-start;
        padding-left: 20rpx;
        .title{
            font-size: 30rpx;
            color: #333;
        }
        .desc{
            width: 100%;
            font-size: 26rpx;
            color: #666;
        }
    }
}
</style>
