## 加入购物车抛物线动画

加入购物车抛物线动画，左右抛物线适配动画，可自定义设置掉落点坐标，曲线流畅，使用简单，适合多平台

引入组件
```
import cartAnimation from '@/components/module/f-cartAnimation/f-cartAnimation'
```

点击事件传参$event
```
<view class="btn" @click="addCart($event)">加入购物车</view>
```

调用组件
```
<cart-animation ref="cartAnimation"></cart-animation>
```

自定义掉落点坐标，需data中设置坐标参数
```
busPos:{
	x:uni.getSystemInfoSync().windowWidth - 190,
	y:uni.getSystemInfoSync().windowHeight + 100
}
```

默认抛物线事件
```
addCart(event){
	this.$refs.cartAnimation.touchOnGoods(event);
}
```

自定义掉落点抛物线事件
```
onCustomPlay(event){
	this.$refs.cartAnimation.touchOnGoods(event,this.busPos);
}
```
