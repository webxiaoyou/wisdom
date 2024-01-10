import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router';
import staticRouter from './modules/staticRouter'; //静态页面
import mainRouter from './modules/MainRouter'; //动态页面路由
import { useUserStore } from '@/store/modules/user'; //获取是否缓存了路由
import { useMenuLists } from '@/store/modules/menu'; //存储菜单信息
import { setupDynamicRoutes } from '@/router/modules/MainRouter';
import API from '@/api';
const router = createRouter({
  routes: [...staticRouter, ...mainRouter],
  history: createWebHistory()
});



// 初始化动态路由
router.beforeEach(async (to, from, next) => {
  if (!useUserStore().setAsyncRoutestMark) {
    try {
      const data: any = await setupDynamicRoutes();
      if (data.code === 401) {
        next('/sys_login');
        return;
      }
      router.addRoute({
        path: `/:pathMatch(.*)*`,
        name: '404',
        component: () => import('@/views/other/404.vue'),
      });

      next({ ...to, replace: true });
    } catch (error) {
      console.error('Error setting up dynamic routes:', error);
      if (error == '网络错误') {
        useUserStore().setAsyncRoutestMark = true;
        // 保存当前路由信息
        // next('/error');
      } else {
        useUserStore().setAsyncRoutestMark = false;
        // useMenuLists().dellastRoute();
        next('/');
      }
    }
  } else {
    next();
  }
});





// export const initRouter =(app :App<Element> ) => {
//     app.use(router);
// } ;
export default router;