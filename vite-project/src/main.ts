import { createApp } from 'vue'
import App from './App.vue'
// 引入封装的nprogress.ts
import 'normalize.css';
import 'animate.css'; //动画样式
import store from './store'; //vuex状态管理器
import router from './router'  //路由
import { start, done } from './utils/nprogress';
import directive from './directive' // directive 自定义组件指令 权限校验
// 加载进度条
router.beforeEach((to, from, next) => {
    start();
    next();
});

// 路由加载结算后
router.afterEach(() => {
    done();
});

const app = createApp(App)
app.use(store)
    .use(directive)
    .use(router);
app.mount('#app');
