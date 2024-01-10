<template>
    <view>
		<f-signature @getImg="getImg"></f-signature>
		<!-- 此处预览图片，正式可删除 -->
		<image :src="img" mode="widthFix" style="position: absolute;background: red;top:0;width: 235rpx;z-index: 10000;" v-if="img"></image>
    </view>
</template>

<script>
import fSignature from '@/components/module/f-signature/f-signature';
export default {
    components: {
        fSignature
    },
    data() {
        return {
			img:''
        }
    },
    onLoad() {

    },
    methods: {
		getImg(e){
			console.log(e,'getImg')
			this.img = e
		}
    },

}
</script>

<style lang="scss" scoped>

</style>