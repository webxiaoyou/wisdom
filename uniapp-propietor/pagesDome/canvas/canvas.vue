<template>
	<view class="content">
        <view class="titleBtn" @click="createRectangle">绘制矩形图fillRoundRect</view>
        <view class="canvasBox" v-if="rectangleImg">
            <view class="canvasItem">
                <image mode="widthFix" style="width:200rpx;height:100rpx" :src="rectangleImg"></image>
            </view>
        </view>
        
        <view class="titleBtn" @click="createRectangleBorder">绘制渐变边框矩形图fillRoundRect+roundRectBorder</view>
        <view class="canvasBox" v-if="rectangleBorderImg">
            <view class="canvasItem">
                <image mode="widthFix" style="width:200rpx;height:100rpx" :src="rectangleBorderImg"></image>
            </view>
        </view>
        
        <view class="titleBtn" @click="createCircular">绘制圆形头像drawCircular</view>
        <view class="canvasBox" v-if="circularImg">
            <view class="canvasItem">
                <image mode="widthFix" style="width:150rpx;height:150rpx" :src="circularImg"></image>
            </view>
        </view>
        
        <view class="titleBtn" @click="createCover">绘制图片cover不变形drawImgCover</view>
        <view class="canvasBox" v-if="coverImg">
            <view class="canvasItem">
                <image mode="widthFix" style="width:200rpx;height:200rpx" :src="coverImg"></image>
            </view>
        </view>
        
        <view class="titleBtn" @click="createText">绘制文本自定义换行超出省略dealWords</view>
        <view class="canvasBox" v-if="textImg">
            <view class="canvasItem">
                <image mode="widthFix" style="width:702rpx;height:200rpx" :src="textImg"></image>
            </view>
        </view>
        
		<view class="titleBtn" @click="createText2">绘制中英文文本自定义换行不截断，文本居中显示</view>
		<view class="canvasBox" v-if="textEnImg">
		    <view class="canvasItem">
		        <image mode="widthFix" style="width:702rpx;height:200rpx" :src="textEnImg"></image>
		    </view>
		</view>
		
        <view class="titleBtn" @click="createButton">绘制圆角按钮drawButton</view>
        <view class="canvasBox" v-if="buttonImg">
            <view class="canvasItem">
                <image mode="widthFix" style="width:260rpx;height:100rpx" :src="buttonImg"></image>
            </view>
        </view>
        
        <view class="titleBtn" @click="createGoodsPoster">绘制海报</view>
        <view class="canvasBox" v-if="canvasImg">
            <view class="canvasItem">
                <image mode="widthFix" style="width:500rpx;height:500rpx" :src="canvasImg"></image>
            </view>
            <view class="button" @click="onSaveImg">保存海报</view>
        </view>
        <!-- 矩形 -->
        <canvas class="f__canvas" style="width:200px;height:100px"  canvas-id="rectangleCanvas" id="rectangleCanvas"></canvas>
        <!-- 矩形+边框 -->
        <canvas class="f__canvas" style="width:200px;height:100px"  canvas-id="rectangleBorderCanvas" id="rectangleBorderCanvas"></canvas>
        <!-- 头像 -->
        <canvas class="f__canvas" style="width:100px;height:100px"  canvas-id="circularCanvas" id="circularCanvas"></canvas>
        <!-- 不变形图片-最短边显示完整 -->
        <canvas class="f__canvas" style="width:200px;height:200px"  canvas-id="coverCanvas" id="coverCanvas"></canvas>
        <!-- 文本自定义换行超出省略 -->
        <canvas class="f__canvas" style="width:1000px;height:300px"  canvas-id="textCanvas" id="textCanvas"></canvas>
		<!-- 英文换行不截断，居中显示 -->
		<canvas class="f__canvas" style="width:1000px;height:300px"  canvas-id="textCanvas2" id="textCanvas2"></canvas>
        <!-- 按钮 -->
        <canvas class="f__canvas" style="width:260px;height:100px"  canvas-id="buttonCanvas" id="buttonCanvas"></canvas>
        <!-- 海报 -->
        <canvas class="f__canvas" style="width:600px;height:730px"  canvas-id="firstCanvas" id="firstCanvas"></canvas>
        <!-- 二维码 -->
        <canvas class="f__canvas" style="width:300px;height:300px;" canvas-id="qrcode" id="qrcode"></canvas>
	</view>
</template>

<script>
import _canvas from '@/uni_modules/fan-canvas/canvasUtils';// 加载万能绘制方法
import { saveTempFilePathImg } from '@/uni_modules/fan-canvas/plugins/utils';// 需要保存图片可以引用此文件
import uQRCode from '@/uni_modules/Sansnn-uQRCode/js_sdk/u-qrcode'
export default {
    data() {
        return {
            // #ifndef H5
            goodsImg:'https://img02.163.gg/img/96/66/75/966675-ieuskndmvo.jpg!YM0000',
            // goodsImg:'https://api.yichengshi.cn/minImage/sx2/sx2IndexHeadBg.jpg',
            // #endif
            // #ifdef H5
            goodsImg:'/static/goods.jpg',
            // #endif
            rectangleImg:'', //矩形
            rectangleBorderImg:'', //矩形+边框
            circularImg:'',  //圆形头像
            coverImg:'',     //不变形图片-最短边显示完整
            buttonImg:'',    //按钮
            canvasImg:'',    //生成的海报图片
            qrcodeImg:'',    //二维码本地图片
            content:'canvas万能制作方法，新手简单入手，易学，一天掌握canvas制作。绘制矩形方法、加载图片方法、绘制圆形头像方法、绘制图片cover不变形、文本自定义换行超出省略、绘制圆角按钮等方法。组合起来用，基本海报都能绘制。',   //内容
			textImg:'',      //文本超出省略
			content2:"The universal canvas making method is easy for beginners to learn and master canvas making in one day.中英文一起绘制文字也不影响换行，英文换行不会截断单词",
			textEnImg:'',    //英文
		}
    },
    onLoad() {
        
    },
    onReady() {
        this.createQrcode()
    },
    methods: {
        //绘制矩形图
        createRectangle(){
            if(this.rectangleImg){
            	return
            }
            var that = this;
            uni.showLoading({
            	title: '生成中'
            });
            let ctx = uni.createCanvasContext('rectangleCanvas');
            const cvsW = 200
            const cvsH = 100
            // 绘制矩形
            //参数：cxt、x坐标、y坐标、宽度、高度、圆角、颜色
            _canvas.fillRoundRect(ctx,0,0,cvsW,cvsH,20,"red");
            // 绘制图片
            ctx.draw(false, function() {
            	uni.canvasToTempFilePath({
            		width: cvsW,
            		height: cvsH,
            		canvasId: 'rectangleCanvas',
            		success(res) {
            			uni.hideLoading();
            			that.rectangleImg = res.tempFilePath
            		}
            	},that)
            }, 500)
        },
        //绘制渐变边框矩形图
        createRectangleBorder(){
            if(this.rectangleBorderImg){
            	return
            }
            var that = this;
            uni.showLoading({
            	title: '生成中'
            });
            let ctx = uni.createCanvasContext('rectangleBorderCanvas');
            const cvsW = 200
            const cvsH = 100
            //绘制矩形
            //参数：cxt、x坐标、y坐标、宽度、高度、圆角、颜色
            _canvas.fillRoundRect(ctx, 0, 0, cvsW, cvsH, 20,'yellow')
            ctx.clip() //超出剪切
            //绘制边框
            //参数：cxt、x坐标、y坐标、宽度、高度、圆角、边框宽度
            _canvas.roundRectBorder(ctx, 0, 0, cvsW, cvsH, 20, 10)
            // 渐变填充边框
            const grd = ctx.createLinearGradient(0, 0, 200, 200)
            grd.addColorStop(0, 'red')
            grd.addColorStop(1, 'blue')
            ctx.strokeStyle = grd //填充颜色--非渐变设置为颜色即可
            ctx.stroke() //绘制边框
            // 绘制图片
            ctx.draw(false, function() {
            	uni.canvasToTempFilePath({
            		width: cvsW,
            		height: cvsH,
            		canvasId: 'rectangleBorderCanvas',
            		success(res) {
            			uni.hideLoading();
            			that.rectangleBorderImg = res.tempFilePath
            		}
            	},that)
            }, 500)
        },
        // 绘制头像
        async createCircular(){
            if(this.circularImg){
            	return
            }
            var that = this;
            uni.showLoading({
            	title: '生成中'
            });
            let ctx = uni.createCanvasContext('circularCanvas',this);
            const cvsW = 100
            const cvsH = 100
            // 同步加载图片--这里可以同步多张图
            var logo = await _canvas.getImageInfo(this.goodsImg);
            if(logo.path){
                // 绘制圆形头像
                // 参数：cxt、图片路径、x坐标、y坐标、宽度、高度
                _canvas.drawCircular(ctx, logo.path, 0, 0, cvsW, cvsH);
                // 绘制图片
                ctx.draw(false, function() {
                	uni.canvasToTempFilePath({
                		width: cvsW,
                		height: cvsH,
                		canvasId: 'circularCanvas',
                		success(res) {
                			uni.hideLoading();
                			that.circularImg = res.tempFilePath
                		}
                	},that)
                }, 500)
            }else{
        		uni.hideLoading();
        		uni.showToast({
        			title: '制作失败',
        			icon: 'none'
        		});
        	}
        },
        // 绘制不变形图片-最短边显示完整
        async createCover(){
            if(this.coverImg){
            	return
            }
            var that = this;
            uni.showLoading({
            	title: '生成中'
            });
            let ctx = uni.createCanvasContext('coverCanvas',this);
            const cvsW = 200
            const cvsH = 200
            // 同步加载图片--这里可以同步多张图
            var logo = await _canvas.getImageInfo(this.goodsImg);
            if(logo.path){
                // 绘制不变形图片-最短边显示完整
                // 参数：cxt、图片属性、x坐标、y坐标、宽度、高度、圆角
                _canvas.drawImgCover(ctx, logo, 0, 0, cvsW, cvsH, 10);
                // 绘制图片
                ctx.draw(false, function() {
                	uni.canvasToTempFilePath({
                		width: cvsW,
                		height: cvsH,
                		canvasId: 'coverCanvas',
                		success(res) {
                			uni.hideLoading();
                			that.coverImg = res.tempFilePath
                		}
                	},that)
                }, 500)
            }else{
        		uni.hideLoading();
        		uni.showToast({
        			title: '制作失败',
        			icon: 'none'
        		});
        	}
        },
        //绘制文本自动换行超出省略
        createText(){
            if(this.textImg){
            	return
            }
            var that = this;
            uni.showLoading({
            	title: '生成中'
            });
            let family = " 'PingFang SC',tahoma,arial,'helvetica neue','hiragino sans gb','microsoft yahei',sans-serif";
            let ctx = uni.createCanvasContext('textCanvas');
            const cvsW = 1000
            const cvsH = 300
            // 绘制矩形
            _canvas.fillRoundRect(ctx,0,0,cvsW,cvsH,20,"red");
            // 文本参数
            var options = {
            	font:"32px"+family,     //设置字体
            	ctx:ctx,
            	word:that.content,      //文本
            	maxWidth:920,           //最大宽度
            	maxLine:3,              //最大行数--超出省略
            	x:40,                   //x坐标
            	y:40,                  //y坐标
            	l_h:60                  //行高
            }
            // 文字颜色
            ctx.setFillStyle('#ffffff')
            // #ifdef MP-TOUTIAO
            ctx.setFontSize(32)
            // #endif
            // 绘文本自定义换行
            _canvas.dealWords(options)
            // 绘制图片
            ctx.draw(false, function() {
            	uni.canvasToTempFilePath({
            		width: cvsW,
            		height: cvsH,
            		canvasId: 'textCanvas',
            		success(res) {
            			uni.hideLoading();
            			that.textImg = res.tempFilePath
            		}
            	},that)
            }, 500)
        },
		// 绘制英文换行不截断，居中显示
		createText2(){
		    if(this.textEnImg){
		    	return
		    }
		    var that = this;
		    uni.showLoading({
		    	title: '生成中'
		    });
		    let family = " 'PingFang SC',tahoma,arial,'helvetica neue','hiragino sans gb','microsoft yahei',sans-serif";
		    let ctx = uni.createCanvasContext('textCanvas');
		    const cvsW = 1000
		    const cvsH = 300
		    // 绘制矩形
		    _canvas.fillRoundRect(ctx,0,0,cvsW,cvsH,20,"red");
		    // 文本参数
		    var options = {
		    	font:"32px"+family,     //设置字体
		    	ctx:ctx,
		    	word:that.content2,      //文本
		    	maxWidth:920,           //最大宽度
		    	maxLine:3,              //最大行数--超出省略
		    	x:40,                   //x坐标
		    	y:40,                   //y坐标
		    	l_h:60,                  //行高
				textCenter:true,        //是否居中
				cvsW:cvsW,				//总宽度
		    }
		    // 文字颜色
		    ctx.setFillStyle('#ffffff')
		    // #ifdef MP-TOUTIAO
		    ctx.setFontSize(32)
		    // #endif
		    // 绘文本自定义换行
		    _canvas.dealWords(options)
		    // 绘制图片
		    ctx.draw(false, function() {
		    	uni.canvasToTempFilePath({
		    		width: cvsW,
		    		height: cvsH,
		    		canvasId: 'textCanvas',
		    		success(res) {
		    			uni.hideLoading();
		    			that.textEnImg = res.tempFilePath
		    		}
		    	},that)
		    }, 500)
		},
        // 绘制圆角按钮
        createButton(){
            if(this.buttonImg){
            	return
            }
            var that = this;
            uni.showLoading({
            	title: '生成中'
            });
            const family = " 'PingFang SC',tahoma,arial,'helvetica neue','hiragino sans gb','microsoft yahei',sans-serif";
            const ctx = uni.createCanvasContext('buttonCanvas');
            const cvsW = 260
            const cvsH = 100
            // 文字
            ctx.font = 'bold 28px' + family
            // 绘制圆角按钮
            // 参数：cxt、背景颜色、x坐标、y坐标、宽度、高度、圆角、文字、文字颜色、文字位置
            _canvas.drawButton(ctx, 'red', 0,0, cvsW, cvsH, cvsH/2, '会员价￥2.88', '#ffffff', 'center')
            // 绘制图片
            ctx.draw(false, function() {
            	uni.canvasToTempFilePath({
            		width: cvsW,
            		height: cvsH,
            		canvasId: 'buttonCanvas',
            		success(res) {
            			uni.hideLoading();
            			that.buttonImg = res.tempFilePath
            		}
            	},that)
            }, 500)
        },
        async createGoodsPoster() {
            if(this.canvasImg){
            	return
            }
        	var that = this;
        	uni.showLoading({
        		title: '海报生成中'
        	});
        	const cvsW = 600;
        	const cvsH = 730;
            const goodsH = 400;
        	const logo_w = 100;
            const margin = 30;
        	let family = " 'PingFang SC',tahoma,arial,'helvetica neue','hiragino sans gb','microsoft yahei',sans-serif";
        	let ctx = uni.createCanvasContext('firstCanvas');
            // 绘制矩形
        	_canvas.fillRoundRect(ctx,0,0,cvsW,cvsH,20,"#ffffff");
            // 同步加载图片
        	var logo = await _canvas.getImageInfo(this.goodsImg);
        	if(logo.path){
                // save+clip+restore:防止图片超出边框显示--相当于overflow: hidden;
                ctx.save();
                ctx.clip(); //剪切--不允许超出矩形框
                // 绘制商品图片
                _canvas.drawImgCover(ctx, logo, 0, 0, cvsW, goodsH);
                ctx.restore();
                
                // 价格符号
                ctx.setFillStyle('#ff0036')
                ctx.font = "24px" + family
                ctx.fillText('￥', margin-5, 450)
                // 价格
                ctx.font = "40px" + family
                ctx.fillText('39.99', 50, 450)
                
                // 绘制圆角按钮
                // 参数：cxt、背景颜色、x坐标、y坐标、宽度、高度、圆角、文字、文字颜色、文字位置
                ctx.font = '22px' + family
                _canvas.drawButton(ctx, 'red', 165, 418, 170, 32, 16, '会员价￥29.99', '#ffffff', 'center')
                // 简介-多行文字
        		ctx.setFillStyle('#666666')
                ctx.textAlign = 'left';
        		var options = {
        			font:"normal 26px"+family,     //设置字体
        			ctx:ctx,
        			word:that.content,      //文本
        			maxWidth:540,           //最大宽度
        			maxLine:2,              //最大行数--超出省略
        			x:margin,               //x坐标
        			y:450,                  //y坐标
        			l_h:40                  //行高
        		}
                // 文本自定义换行
        		_canvas.dealWords(options)
                // 绘制圆形头像
                _canvas.drawCircular(ctx, logo.path, margin, goodsH+margin+160, logo_w, logo_w);
                // 名称
                ctx.setFillStyle('#000000')
                ctx.font = "bold 26px" + family
                ctx.fillText('创作者：Aa.小凡', logo_w+margin+10, goodsH+margin+195)
                // 简介
                ctx.setFillStyle('#999999')
                ctx.font = "normal 22px" + family
                ctx.fillText('所有方法融合一起实战', logo_w+margin+10, goodsH+margin+235)
                // 绘制二维码
                ctx.drawImage(that.qrcodeImg, cvsW-margin-130, goodsH+margin+80+60, 130, 130);
                // 绘制图片
        		ctx.draw(false, function() {
        			uni.canvasToTempFilePath({
        				width: cvsW,
        				height: cvsH,
        				canvasId: 'firstCanvas',
        				success(res) {
        					uni.hideLoading();
        					that.canvasImg = res.tempFilePath
        				}
        			},that)
        		}, 500)
        	}else{
        		uni.hideLoading();
        		uni.showToast({
        			title: '海报制作失败',
        			icon: 'none'
        		});
        	}
        },
        // 保存图片
        async onSaveImg() {
        	let imgUrl = "";
        	if(this.canvasImg){
        		imgUrl = await this.canvasImg;
                saveTempFilePathImg(imgUrl)
        	}
        },
		// 创建二维码
		createQrcode(){
		    var that = this
			const ctx = uni.createCanvasContext('qrcode',this);
			const uqrcode = new uQRCode(
			  {
				text: 'https://www.baidu.com',//二维码参数
				size: 300
			  },
			  ctx
			);
			uqrcode.make();
			uqrcode.draw().then(()=>{
				uni.canvasToTempFilePath({
				    width: 600,
				    height: 600,
				    canvasId: 'qrcode',
				    success(res) {
				        // 二维码本地图
				         that.qrcodeImg = res.tempFilePath
				        console.log(res.tempFilePath,'createQrcode')
				    }
				},that)
			})
		}
    },
}
</script>

<style lang="scss" scoped>
.content{
    padding-bottom: 200rpx;
}
.titleBtn{
    margin: 24rpx;
    width: 702rpx;
    padding: 20rpx 24rpx;
    background-color: #007AFF;
    color: #fff;
    font-size: 24rpx;
    text-align: center;
}
.f__canvas {
	width: 300rpx;
	height: 300rpx;
	position: fixed;
	top: -10000rpx;
	left: 0rpx;
}
.canvasBox{
    .canvasItem {
        /* #ifndef APP-NVUE */
        display: flex;
        /* #endif */
        justify-content: center;
        .img {
            width: 100%;
        }
    }
    .button {
        width: 300rpx;
        height: 80rpx;
        background: #fff;
        line-height: 80rpx;
        text-align: center;
        font-size: 28rpx;
        border-radius: 40rpx;
        margin: 0 auto;
        margin-top: 20rpx;
    }
}
</style>
