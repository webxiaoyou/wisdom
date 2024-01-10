import { defineStore } from 'pinia';
import { useRouter, useRoute } from 'vue-router';
export const useMenuLists = defineStore({
    id: 'menu', // id must be unique
    state: () => {
        return {
            menuLists: {}, // menu information
            cachedViews: [],
            // lastRoute: null,
        };
    },
    actions: {
        // Menu information
        async updateMenuLists(data) {
            if (data.code === 200) {
                data.data.menus.unshift({
                    "icon": "HomeOutlined",
                    "id": "0",
                    "isFrame": 1,
                    "isCache": 0,
                    "menuName": "控制台",
                    "menuType": "M",
                    "orderNum": 1,
                    "parentId": "0",
                    "path": "home",
                    "perms": "",
                    "status": "0",
                    "visible": "0"
                });
                data.data.menus = this.processMenuPaths(data.data.menus)
            }
            this.menuLists = data;
            // this.menuLists = {
            //     ...data,
            //     data: {
            //         ...data.data,
            //         menus: ,
            //     },
            // };
        },

        processMenuPaths(menuData, parentPath = '') {
            // 使用 for 循环以便能够正确地等待异步递归调用
            for (const menuItem of menuData) {
                // If there are children, recursively call the function
                if (menuItem.children && menuItem.children.length > 0) {
                    this.processMenuPaths(menuItem.children, menuItem.path + '/');
                } else {
                    if (menuItem?.isCache == 0) {
                        this.addCachedView(menuItem.path)
                    }
                    // If there are no children, don't modify the path
                    menuItem.path = '/' + parentPath + menuItem.path;
                }
            }
            // debugger
            return menuData;
        },
        addCachedView(view) {
            if (this.cachedViews.includes(view.name)) return
            this.cachedViews.push(view)
        },
        // arrlastRoute(roure) {
        //     this.lastRoute = roure
        // },
        // dellastRoute() {
        //     this.lastRoute = null
        // }

    },
    persist: {
        enabled: true,
        strategies: [
            {
                key: 'menu',
                paths: ['menuLists'],
            },
        ],
    },
});
