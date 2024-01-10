import { defineStore } from 'pinia';
import API from '@/api';
import router from '@/router';
import { message } from 'ant-design-vue';
export const useUserStore = defineStore({
    id: 'user', //id必填，且需要唯一
    state: () => {
        return {
            openKeys: {
                selected: ['/home'],
                openKeys: ['']
            },  //菜单显示位置状态
            token: '',  //token
            loginModule: 0,  //登录界面类型状态 
            userInfo: {} ,  // 用户信息
            roles: [], //用户身份
            permissions:[],
            setAsyncRoutestMark: false,  //路由是否加载状态
            isRedirected: false, //验证用户信息是否过期
        };
    },
    actions: {
        // 用户登录
        async login(account) {
            this.setAsyncRoutestMark = false;
            const data:any = await API.sys_login(account);
            if (data.data?.token) {
                this.token = data.data.token;
                await this.getUserInfo();
            } else {
                message.error(data?.msg);
            }
            return data;
        },
        // 用户信息
        async getUserInfo() {
            this.setAsyncRoutestMark = false;
            const data:any = await API.sys_userInfo()
            if (data?.code == 200) {
                this.userInfo = data.data?.user;
                this.roles = data.data?.roles;
                this.permissions = data.data?.permissions;
                router.replace('/');
            }
            return data;
        },
        // 用户信息
        async getUserInfoUP() {
            this.setAsyncRoutestMark = false;
            const data:any = await API.sys_userInfo()
            if (data?.code == 200) {
                this.userInfo = data.data?.user;
                this.roles = data.data?.roles;
                this.permissions = data.data?.permissions;
            }
            return data;
        },
        //  退出登录
        async logout() {
            const data = await API.sys_user_logout();
            this.token = '';
            this.userInfo = {};
            this.roles = [];
            this.openKeys = {
                selected: ['/home'],
                openKeys: ['']
            }  //菜单显示位置状态
            router.replace('/sys_login');
        },
        //  信息过期
        async staleDated() {
            this.token = '';
            this.userInfo = {};
            this.roles = [];
            this.openKeys = {
                selected: ['/home'],
                openKeys: ['']
            }  //菜单显示位置状态
            // this.isRedirected = false
            // message.error(item?.msg);
            // router.replace('/sys_login');
        },
        // 路由状态判断
        updateSetAsyncRoutestMark(state: boolean) {
            this.setAsyncRoutestMark = state;
        },
        updateName(state: string, payload: any) {
            this.name = state;
        },
        // 登录的主题界面
        updateLoginModule(state: number, payload: any) {
            this.loginModule = state;
        },
    },
    persist: {
        enabled: true,
        // 可以更改存放位置
        strategies: [
            {
                key: 'userInfo',
                // storage: 'localStorage',
                // 可以指定持久化的key
                paths: ['token', 'userInfo', 'roles', 'openKeys','permissions'],
            },
            {
                // key: 'loginModule',
                storage: localStorage,
                // 可以指定持久化的key
                paths: ['loginModule'],
            },
        ],
    },
});
