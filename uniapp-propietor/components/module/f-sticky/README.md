## sticky吸顶组件，组件中用也不会失灵

下载下来后，将组件中f-sticky.vue复制到您的组件中

### 参数说明

| 参数名     | 说明						| 是否必填	| 数据类型	| 默认		|
| -------	| -------					| -------	| -------	| -------	|
| scrollTop	| 滚动条参数				    |  true		|  Number	|    0		|
| offsetTop	| 距离顶部多高吸顶（rpx）	    |  true		|  Number	|    0		|
| zIndex	| 图层						|  false	|  Number	|    10		|
| enable	| 是否开启吸顶				|  false	|  Boolean	|    true	|

### 使用方法

引用组件--注意您组件的路径
```
import fSticky from '@/components/module/f-sticky/f-sticky';
```

案例

```
<f-sticky :scrollTop="scrollTop" :offsetTop="200">
	<view class="sticky">请往上滑</view>
</f-sticky>
```

