import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import * as path from 'path';
import AutoImport from 'unplugin-auto-import/vite';
import Icons from 'unplugin-icons/vite';
import Components from 'unplugin-vue-components/vite';
import IconsResolver from 'unplugin-icons/resolver';
import { AntDesignVueResolver } from 'unplugin-vue-components/resolvers'
import vuesetupExtend from 'vite-plugin-vue-setup-extend'
import Inspect from 'vite-plugin-inspect';  //快速查看源码
// https://vitejs.dev/config/

export default defineConfig({
  
  plugins: [vue(),Inspect(), vuesetupExtend(),
  AutoImport({
    // 自动导入 Vue 相关函数，如：ref, reactive, toRef 等
    imports: ['vue'],
    dts: 'src/auto-import.d.ts',
    resolvers: [
      AntDesignVueResolver(),
      // 自动导入图标组件
      IconsResolver({
        prefix: 'Icon',
      }),
    ],
  }),
  Components({
    dirs: ['src/components'],
    extensions: ['vue', 'tsx'],
    // 配置文件生成位置
    dts: 'src/components.d.ts',
    resolvers: [
      AntDesignVueResolver({
        importStyle: 'less', // 根据你的需要选择导入样式的方式
      }),
      // 自动注册图标组件
      IconsResolver({
        enabledCollections: ['ant-design-vue'],
      }),
    ],
  }),
  Icons({
    autoInstall: true,
  }),
  ],
  resolve: {
    // 解决@路径的问题
    alias: {
      "@": path.join(__dirname, 'src'),
      "#": path.join(__dirname, 'types')
    }
  },
  server: {
    port: 8080, //启动端口
    open: true,
    hmr: {
      host: 'localhost',
      port: 8080,
    },
    // 设置 https 代理
    // proxy: {
    //   '/api': {
    //     target: 'your https address',
    //     changeOrigin: true,
    //     rewrite: (path: string) => path.replace(/^\/api/, '')
    //   }
    // }
  },
  css: {
    preprocessorOptions: {
      less: {
        /**
         * - 引用公共样式，使用vite搭建项目只安装sass即可，不需要安装node-sass,sass-loader
         * - 引入 common.scss (已经包括了 variables.scss及mixins.scss)
         * - 引入多个文件： `@import "xx1.scss";@import "@xx2.scss";`
         */
        javascriptEnabled: true,
        additionalData: `@import "${path.resolve(
          __dirname,
          'src/style/common.less',
        )}";@import "${path.resolve(
          __dirname,
          'src/style/mixins.less',
        )}";@import "${path.resolve(
          __dirname,
          'src/style/variables.less',
        )}";`,
      },
    },
  },
  optimizeDeps: {
    include: [
      '@ant-design/icons-vue',
      '@iconify/iconify',                 // 由 @purge-icons/generated 动态加载
      'ant-design-vue/es/locale/zh_CN',   // Ant Design Vue 的中文本地化
      'ant-design-vue/es/locale/en_US',   // 如果需要，请包含
    ],
  },
  build: {
    chunkSizeWarningLimit: 1600,
    rollupOptions: {
      output: {
        manualChunks(id) {
          if (id.includes('src/assets')) {
            return 'assets';
            
          }
        },
      },
    },
  },
})
