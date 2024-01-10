<template>
  <div @click="aaa">首页</div>
</template>

<script setup lang="ts" name="home">
import { reactive, toRefs, onMounted, getCurrentInstance } from 'vue'
import { useRouter } from 'vue-router';
import API from '@/api/index';

//获取角色列表
const getRoleList = async () => {
  let formdata = {
    pageNum: 1,
    pageSize: 100
  }
  let { data: roleData } = await API.sys_role_list(formdata);
  // 假设超级管理员对象的 roleName 属性为 '超级管理员'
}
onMounted(() => {
  getRoleList()
})
// 获取 Vue Router 实例
const router = useRouter();
const aaa = (() => {
  router.push('/user')
})

</script>
<style scoped></style>