// 静态文件路由
import { RouteRecordRaw } from 'vue-router';
const staticRouter: RouteRecordRaw[] = [
   
    {
        path: '/sys_login',
        name: 'SysLogin',
        meta: {
            title: '登录',
            keepAlive: false,
            requireAuth: false,
        },
        component: () => import('@/views/system/user/Login.vue'),
    },
    {
        path: '/error',
        name: 'error',
        meta: {
            title: '网络错误',
        },
        component: () => import('@/views/other/error.vue'),
    },
    // {
    //     path: '/:pathMatch(.*)*',
    //     name: '404',
    //     component: () => import('@/views/other/404.vue'),
    // },
];
export default staticRouter;
