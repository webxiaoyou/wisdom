// theme.ts
import { defineStore } from 'pinia';

export const useThemeStore = defineStore('theme', {
  state: () => ({
    themeColor: localStorage.getItem('themeColor') || '#00ff00',
  }),

  getters: {
    getThemeColor: (state) => state.themeColor,
  },

  actions: {
    setThemeColor(color: string) {
      this.themeColor = color;
      localStorage.setItem('themeColor', color);
    },
  },
});
