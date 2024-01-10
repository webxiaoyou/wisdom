<template>
    <div class="w h" style="display: flex;flex-direction: column; align-items: center;">
        <div  style="display: flex;flex-direction: column; align-items: center;margin-top: 2%; width: 50%;">
            <img :src="getAssetsImages('404.svg')" alt="" style="width: 100%;">
            <!-- <div class="text">服务器飞走啦</div> -->
            <a-button type="primary" ghost @click="refreshPage">返回上一层</a-button>
        </div>
    </div>
</template>

<script setup lang="ts" name="error">
import { getAssetsImages, getDictLabel } from '@/hooks/publicFunction'; //图片
import { reactive, toRefs, onBeforeMount, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
const router = useRouter(); //实例化路由
const refreshPage = () =>{
    router.go(-1);
  }
</script>
<style lang="less" scoped>

.text{
    font-size: 24px;
    color: @color-text;
}
</style>
