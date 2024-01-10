<!-- ThemeSwitcher.vue -->
<template>
  <div>
    <label>选择主题色:</label>
    <input type="color" v-model="selectedColor" @input="changeThemeColor" />
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { useThemeStore } from '@/store/modules/theme';

const selectedColor = ref<string>('#00ff00'); // 初始主题色
const themeStore = useThemeStore();

const changeThemeColor = () => {
  themeStore.setThemeColor(selectedColor.value);
};
</script>
