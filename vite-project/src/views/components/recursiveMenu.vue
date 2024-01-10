<template>
  <!-- 递归渲染菜单项 -->
  <template v-for="(menuItem, menuIndex) in menuData" :key="menuIndex">
    <template v-if="menuItem.children && menuItem.children.length > 0">
      <!-- 使用 v-if 条件来验证是否显示子菜单 -->
      <a-sub-menu :key="menuItem.path" v-if="menuItem.visible == 0">
        <template #title>
          <component class="icons" :is="Icons[menuItem.icon]"></component>
          <span>{{ menuItem.menuName }} </span>
        </template>
        <!-- 递归渲染子菜单项 -->
        <recursive-menu :menuData="menuItem.children" ></recursive-menu>
      </a-sub-menu>
    </template>
    <template v-else>
      <!-- 使用 v-if 条件来验证是否显示菜单项 -->
      <a-menu-item :key="menuItem.path" v-if="menuItem.visible == 0">
        <router-link :to="menuItem.path" v-if="menuItem.isFrame === 1">
          <component class="icons" :is="Icons[menuItem.icon]"></component>
          <span>{{ menuItem.menuName }}</span>
        </router-link>
        <a :href="menuItem.isFrame === 1 ? null : (menuItem.path.startsWith('http') ? menuItem.path : 'http://' + menuItem.path)"
          target="_blank" v-else>
          <component class="icons" :is="Icons[menuItem.icon]"></component>
          <span>{{ menuItem.menuName }}</span>
        </a>
      </a-menu-item>
    </template>
  </template>
</template>

<script setup lang="ts">
import * as Icons from '@ant-design/icons-vue';
import {  ref } from 'vue';

const props = defineProps({
  menuData: {
    type: Array<SysMenu>,
    default: () => [],
  },
});

</script>
