<template>
    <view>
        <!-- 公共组件-每个页面必须引入 -->
        <public-module></public-module>
        <view class="u-wrap">
            <view class="u-search-box">
            	<view class="u-search-inner">
            		<text class="u-search-text">搜索</text>
            	</view>
            </view>
            <view class="u-menu-wrap">
                <scroll-view scroll-y scroll-with-animation class="u-tab-view menu-scroll-view" :scroll-top="scrollTop">
                    <view v-for="(item,index) in categoryBox" :key="index" class="u-tab-item" :class="[current==index ? 'u-tab-item-active' : '']" @tap.stop="swichMenu(index)">
                        <text class="u-line-2" :style="{color:current==index?PrimaryColor:''}">{{item.name}}</text>
                        <!-- 这里是对应购物车的数量角标 -->
                        <view class="cartNumber" v-if="item.cartNumber && item.cartNumber>0" :style="{backgroundColor: PrimaryColor}">{{item.cartNumber}}</view>
                    </view>
                </scroll-view>
                <!-- #ifdef MP-ALIPAY -->
                <scroll-view scroll-y class="right-box u-flex-1">
                    <view class="page-view">
                        <view class="class-item" style="width: 590rpx;">
                            <view class="item-title">
                                <text>{{categoryBox[current].name}}</text>
                            </view>
                            <good-one :list="categoryBox[current].goods_list" @onOpenPopup="onOpenPopup"></good-one>
                            <view style="text-align: center;padding-top: 50rpx;font-size: 24rpx;color: #999;" v-if="categoryBox[current].goods_list.length==0">
                                此分类暂无商品
                            </view>
                        </view>
                    </view>
                </scroll-view>
                <!-- #endif -->
                <!-- #ifndef MP-ALIPAY -->
                <scroll-view scroll-y scroll-with-animation class="right-box u-flex-1" :scroll-top="scrollRightTop" @scroll="rightScroll">
                    <view class="page-view">
                        <view class="class-item" :id="'item' + index" v-for="(item , index) in categoryBox" :key="index">
                            <view class="item-title">
                                <text>{{item.name}}</text>
                            </view>
                            <good-one :list="item.goods_list" @onOpenPopup="onOpenPopup"></good-one>
                            <view style="text-align: center;padding: 20rpx;font-size: 24rpx;color: #999;" v-if="item.goods_list==0">
                                暂无商品
                            </view>
                        </view>
                    </view>
                </scroll-view>
                <!-- #endif -->
            </view>
            <!-- tabbar占位高度 -->
            <view :style="{height: systemInfo.tabbarH+'px'}"></view>
        </view>
        <!-- 规格弹窗 -->
        <u-popup :show="isPopup" :round="10" mode="bottom" @close="isPopup = false">
            <specs-popup :specGoodsData="specGoodsData" :isPopup="isPopup" popupType="1" @change="onSpecChange"></specs-popup>
        </u-popup>
        <f-tabbar :isFillHeight="false"></f-tabbar>
    </view>
</template>

<script>
var num = 0;
var dsq = null;
import { mapState, mapMutations } from 'vuex';
import base from '@/config/baseUrl.js';
import goodOne from '@/components/module/good';
import specsPopup from '@/components/module/specs-popup';
import fTabbar from '@/components/module/f-tabbar/f-tabbar';
export default {
    components: {
        goodOne,
        specsPopup,
        fTabbar
    },
	computed:{
	    ...mapState(['PrimaryColor']),
	},
    data() {
        return {
            systemInfo:base.systemInfo,
            isPopup:false,
            categoryBox:[],//分类商品
            current: 0,
            scrollTop:0,
            specGoodsData:{},//选择的规格数据
            
            // ***滑动联动所需参数***
            oldScrollTop: 0,
            current: 0, // 预设当前项的值
            menuHeight: 0, // 左边菜单的高度
            menuItemHeight: 0, // 左边菜单item的高度
            itemId: '', // 栏目右边scroll-view用于滚动的id
            menuItemPos: [],
            arr: [],
            scrollRightTop: 0, // 右边栏目scroll-view的滚动高度
            timer: null, // 定时器
            flag:false,//禁止current两次赋值
            flag2:true,//是否禁止右侧滑动
            c_id:'',//跳转的参数--定位分类id位置
        }
    },
    onLoad(e) {
        // 隐藏原生的tabbar
        uni.hideTabBar();
        // this.c_id = e.c_id
    	this.c_id = 9 //跳转的参数--定位分类id位置
        this.getgoodsList()
    },
    onReady() {
    	this.getMenuItemTop()
    },
    onShow(){
    	// #ifndef MP-ALIPAY
    	num = 0;
    	this.DSQ()
    	// #endif
    },
    methods: {
        px2upx(n){
            return n/(uni.upx2px(n)/n);
        },
        onOpenPopup(item){
            this.specGoodsData = item
            this.isPopup = true
        },
        //规格回调
        onSpecChange(e){
            console.log(e)
            if(e.type!=1 && e.type!=4 ){
                this.isPopup = false
            }
        },
        // 请求商品
        getgoodsList() {
            // uni.$u.http.post('您的接口', httpData).then((res) => {
                // 这里模拟加载的数据
                for(var i=0;i<20;i++){
                    var category = {
                        name:'分类名'+(i+1),
                        cartNumber:i+1,//分类已经加入购物车数量
                        id:i+1,
                        goods_list:[],
                    }
                    for(var f=0;f<5;f++){
                        var goods = {
                            id:f+1,
                            name:'商品名称',//商品名称
                            goodsImg:'https://img02.163.gg/img/96/66/75/966675-ieuskndmvo.jpg!YM0000',//商品图片
                            isPromote:1,//是否特价
                            totalStockCount:9999,//库存
                            goodsDescription:'商品描述',//商品描述
                            price:59.99,//商品价
                            memberPrice:35.99,//会员价
                            buyGoodsNumber:f+1,//当前加入购物车的数量
                            hasProduct:1,//是否多规格
                            monthlySales:999,//月售
                            unit:'份',//单位
                            attr_array:['不辣','微辣','特辣'],//规格
                        }
                        category.goods_list.push(goods)
                    }
                    this.categoryBox.push(category)
                }
                console.log(this.categoryBox,'this.categoryBox')
                // 如果有传分类c_id参数-滑动到对应的选项
                if(this.c_id){
                    this.categoryBox.forEach((item,index)=>{
                        if(item.id == this.c_id){
                            let time = 1500 //实际接口这里数字可以调小点
                            // 等待页面渲染完成
                            setTimeout(()=>{
                                if(index>=2){
                                    this.swichMenu(index-1)
                                }
                            },time) 
                            setTimeout(()=>{
                                this.swichMenu(index)
                            },time+200)
                        }
                    })
                }
            // })
        },
        // 点击左边的栏目切换
        async swichMenu(index) {
            console.log('swichMenu')
            // #ifdef MP-ALIPAY
            if(index == this.current) return ;
            this.current = index;
            // 如果为0，意味着尚未初始化
            if(this.menuHeight == 0 || this.menuItemHeight == 0) {
                await this.getElRect('menu-scroll-view', 'menuHeight');
                await this.getElRect('u-tab-item', 'menuItemHeight');
            }
            // 将菜单菜单活动item垂直居中
            this.scrollTop = index * this.menuItemHeight + this.menuItemHeight / 2 - this.menuHeight / 2;
            // #endif
            // #ifndef MP-ALIPAY
            if(this.arr.length == 0) {
                await this.getMenuItemTop();
            }
            if (index == this.current) return;
            this.scrollRightTop = this.oldScrollTop;
            this.$nextTick(function(){
                this.scrollRightTop = this.arr[index];
                this.current = index;
                this.flag = true
                this.flag2 = false //禁止右侧滑动
                this.leftMenuStatus(index);
                num = 0
            })
            // #endif
        },
        DSQ(isClear){
            // 解决点击分类，左侧多次滚动
            dsq && clearInterval(dsq)
            dsq = setInterval(()=>{
                num+=100
                if(num>500){
                    this.flag2 = true
                }
                // console.log(num)
            },100)
        },
        // 获取一个目标元素的高度
        getElRect(elClass, dataVal) {
            new Promise((resolve, reject) => {
                const query = uni.createSelectorQuery().in(this);
                query.select('.' + elClass).fields({size: true},res => {
                    // 如果节点尚未生成，res值为null，循环调用执行
                    if(!res) {
                        setTimeout(() => {
                            this.getElRect(elClass);
                        }, 10);
                        return ;
                    }
                    this[dataVal] = res.height;
                }).exec();
            })
        },
        // ***********************************************  微信小程序 S  *****************************************************************
        // 设置左边菜单的滚动状态
        async leftMenuStatus(index) {
            console.log('左侧滚动')
            // #ifndef MP-ALIPAY
            if(this.flag){
                this.current = index;
                this.flag = false
                console.log(index)
            }
            // 如果为0，意味着尚未初始化
            if (this.menuHeight == 0 || this.menuItemHeight == 0) {
                await this.getElRect('menu-scroll-view', 'menuHeight');
                await this.getElRect('u-tab-item', 'menuItemHeight');
            }
            console.log(this.menuHeight,'this.menuHeight')
            console.log(this.menuItemHeight,'this.menuItemHeight')
            // 将菜单活动item垂直居中
            this.scrollTop = index * this.menuItemHeight + this.menuItemHeight / 2 - this.menuHeight / 2;
            // #endif
        },
        // 获取右边菜单每个item到顶部的距离
        getMenuItemTop() {
            // #ifndef MP-ALIPAY
            new Promise(resolve => {
                let selectorQuery = uni.createSelectorQuery();
                selectorQuery.selectAll('.class-item').boundingClientRect((rects) => {
                    // 如果节点尚未生成，rects值为[](因为用selectAll，所以返回的是数组)，循环调用执行
                    if(!rects.length) {
                        setTimeout(() => {
                            this.getMenuItemTop();
                        }, 10);
                        return ;
                    }
                    rects.forEach((rect) => {
                        // 这里减去rects[0].top，是因为第一项顶部可能不是贴到导航栏(比如有个搜索框的情况)
                        this.arr.push(rect.top - rects[0].top);
                        resolve();
                    })
                }).exec()
            })
            // #endif
        },
        // 右边菜单滚动
        async rightScroll(e) {
            // #ifndef MP-ALIPAY
            if(this.flag2 && !this.flag){
                 console.log('右侧滚动')
                this.oldScrollTop = e.detail.scrollTop;
                if(this.arr.length == 0) {
                    await this.getMenuItemTop();
                }
                if(this.timer) return ;
                if(!this.menuHeight) {
                    await this.getElRect('menu-scroll-view', 'menuHeight');
                }
                setTimeout(() => { // 节流
                    this.timer = null;
                    // scrollHeight为右边菜单垂直中点位置
                    let scrollHeight = e.detail.scrollTop + this.menuHeight / 2;
                    for (let i = 0; i < this.arr.length; i++) {
                        let height1 = this.arr[i];
                        let height2 = this.arr[i + 1];
                        // 如果不存在height2，意味着数据循环已经到了最后一个，设置左边菜单为最后一项即可
                        if (!height2 || scrollHeight >= height1 && scrollHeight < height2) {
                            this.flag = true
                            this.leftMenuStatus(i);
                            return ;
                        }
                    }
                }, 10)
            }
            // #endif
        },
        // ***********************************************  微信小程序 E  *****************************************************************
    },

}
</script>

<style lang="scss" scoped>
.u-wrap {
    height: calc(100vh);
    /* #ifdef H5 */
    height: calc(100vh - var(--window-top));
    /* #endif */
    /* #ifndef APP-NVUE */
    display: flex;
    /* #endif */
    flex-direction: column;
}
.u-search-box {
    background-color: #fff;
    padding: 18rpx 30rpx;
    border-bottom: 2rpx solid #eeeeee;
	width: 750rpx;
}
.u-search-inner {
    background-color: #f5f5f5;
    border-radius: 100rpx;
    /* #ifndef APP-NVUE */
    display: flex;
    /* #endif */
    align-items: center;
    padding: 10rpx 16rpx;
}
.u-search-text {
    font-size: 26rpx;
    color: #999;
    margin-left: 10rpx;
}

.u-menu-wrap {
    flex: 1;
    /* #ifndef APP-NVUE */
    display: flex;
    /* #endif */
    overflow: hidden;
}
.u-tab-view {
    width: 160rpx;
    height: 100%;
    background: #fff;
    border-right: 2rpx solid #eeeeee;
}
.u-tab-item {
    width: 160rpx;
    // height: 110rpx;
    padding: 38rpx 24rpx;
    background: #fff;
    box-sizing: border-box;
    /* #ifndef APP-NVUE */
    display: flex;
    /* #endif */
    align-items: center;
    justify-content: center;
    font-size: 26rpx;
    color: #333;
    font-weight: 400;
    line-height: 1;
    position: relative;
	z-index: 10;
    .cartNumber{
        position: absolute;
		z-index: 10;
        border-radius: 34rpx;
        top: 8rpx;
        right: 8rpx;
        // width: 34rpx;
        padding: 0 10rpx;
        height: 34rpx;
        text-align: center;
        line-height: 34rpx;
        background: #fe461d;
        color: #fff;
        font-size: 24rpx;
    }
    text{
        line-height: 1.2;
        color: #333;
    }
}
.u-tab-item-active {
    position: relative;
	z-index: 10;
    color: #fe461d;
    font-weight: 600;
    background: #fff;
    text{
        color: #fe461d
    }
}
.u-tab-item-active::before {
    content: "";
    position: absolute;
	z-index: 10;
    border-left: 4rpx solid #fe461d;
    height: 32rpx;
    left: 0;
    top: 50%;
    transform: translateY(-50%);
}
.u-tab-view {
    height: 100%;
}
.right-box {
    /* #ifdef MP-ALIPAY */
    background-color: #fff;
    /* #endif */
    /* #ifndef MP-ALIPAY */
    background-color: #f5f5f5;
    /* #endif */
}
.page-view {
    // padding: 16rpx;
}
.class-item {
    // margin-bottom: 30rpx;
    background-color: #fff;
    padding: 24rpx 24rpx 0;
    border-radius: 8rpx;
    /* #ifndef MP-ALIPAY */
    margin-bottom: 20rpx;
    /* #endif */
}
.item-title {
    font-size: 26rpx;
    color: #333;
    font-weight: bold;
    text{
        color: #999;
        font-size: 24rpx;
    }
}
.item-menu-name {
    font-weight: normal;
    font-size: 24rpx;
    color: #333;
}
.item-container {
    /* #ifndef APP-NVUE */
    display: flex;
    /* #endif */
    flex-wrap: wrap;
}
</style>
