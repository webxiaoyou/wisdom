// 动态文件路由
import { RouteRecordRaw } from 'vue-router';
import API from '@/api';
import router from '@/router';
import { useUserStore } from '@/store/modules/user';// 替换成你的 useUserStore 路径
import { useMenuLists } from '@/store/modules/menu'; //存储菜单信息
const MainRouter: RouteRecordRaw[] = [
  {
    path: '/',
    name: 'Index',
    meta: {
      title: '首页',
      keepAlive: true,
      requireAuth: true,
    },
    component: () => import('@/views/Index.vue'),
    redirect: 'Home',
    children: [
      {
        path: '/home',  // 空路径，作为默认子路由
        name: 'home',
        meta: {
          title: '控制台',
          keepAlive: true,
          requireAuth: true,
        },
        component: () => import('@/views/Home.vue'),
      },
      {
        path: '/user/profile',
        name: 'profile',
        meta: {
          title: '个人信息',
          keepAlive: false,
          requireAuth: true,
        },
        component: () => import('@/views/system/user/profile.vue'),
      },

      {
        path: '/system/dict-data/index/:dictType',
        name: 'dict-data',
        component: () => import('@/views/system/dict/data.vue'),
        meta: { title: '字典数据', activeMenu: '/system/dict' }
      },
      {
        path: '/tenement/floor-data/index/:buildingId/:buildingNumber',
        name: 'floor-data',
        component: () => import('@/views/tenement/pbuilding/data.vue'),
        meta: { title: '楼层信息', activeMenu: '/tenement/pbuilding' }
      },
      {
        path: '/system/oss-config',
        name: 'oss-config',
        component: () => import('@/views/system/oss/config.vue'),
        meta: {
          title: '配置管理', activeMenu: '/system/oss'
        }
      }
    ],
  },
];
// // 动态路由请求配置
const modules = import.meta.glob("/src/views/**/**.vue");
export const setupDynamicRoutes = async () => {
  const result: any = await API.Menu();
  if (result.code === 401) {
    useUserStore().updateSetAsyncRoutestMark(true);
  }
  if (result.code === 200) {
    useUserStore().updateSetAsyncRoutestMark(true);
    MenuArrData(result.data?.menus);
    useMenuLists().updateMenuLists(result);
  }

  return result;
};
function MenuArrData(Menu, parentPath = '') {

  Menu.forEach(itemRouter => {
    // debugger
    if (itemRouter.children && itemRouter.children.length > 0) {
      MenuArrData(itemRouter.children, itemRouter.path + '/');
    } else {
      const newRoute: RouteRecordRaw = {
        path: parentPath + itemRouter.path,
        name: itemRouter.path,
        component: modules[
            /* @vite-ignore */ `/src/views/${itemRouter.component}.vue`
        ],
        meta: {
          title: itemRouter.menuName,
          keepAlive: itemRouter?.isCache == 0 ? true : false,
        }

      };
      router.addRoute('Index', newRoute);
    }
  });
}



export default MainRouter;
