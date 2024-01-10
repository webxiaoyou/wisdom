<template>
  <div class="content ">
    <a-row>
      <a-col :xs="0" :sm="5" :md="5" :lg="4" :xl="3">
        <div class="left ">
          <div class="characteristic">
            智慧管家
          </div>
          <a-menu :theme="menuTheme" v-model:openKeys="state.openKeys" v-model:selectedKeys="state.selected"
            :mode="menuMode">
            <recursive-menu :menuData="result.data"></recursive-menu>
          </a-menu>
        </div>
      </a-col>

      <a-col :xs="24" :sm="19" :md="19" :lg="20" :xl="21">
        <div class="right ">
          <div class="top f-e ">
            <template v-if="isMobile">
              <a-affix :offset-top="0" class="w">
                <div class="top f-e top-p">
                  <a-col :xs="1" :sm="0" :md="0" :lg="0" :xl="0">
                    <div class="" style="">
                      <a-button @click="showLeftDrawer">
                        <template #icon>
                          <Icon icon="quill:hamburger" />
                        </template>
                      </a-button>
                    </div>
                  </a-col>
                  <a-col :xs="23" :sm="0" :md="0" :lg="0" :xl="0">
                    <div class="" style="text-align: center;">
                      <h3 class="m0 logo-title"> 智慧管家</h3>
                    </div>
                  </a-col>
                  <a-col :xs="1" :sm="0" :md="0" :lg="0" :xl="0">
                    <div class="">
                      <a-popover placement="bottomRight">
                        <template #content>
                          <div class="">
                            <p class="cursor-pointer" @click="toProfile">个人信息</p>
                            <p class="cursor-pointer" @click="onLogout">退出登录</p>
                          </div>
                        </template>
                        <a-button>
                          <template #icon>
                            <Icon icon="pepicons-pencil:hamburger" />
                          </template>
                        </a-button>
                      </a-popover>

                    </div>
                  </a-col>
                </div>

              </a-affix>
            </template>
            <a-col :xs="0" :sm="10" :md="10" :lg="5" :xl="5" v-else>
              <div class="top-right flex-row flex-justify-end p-r4">
                <div class="user-info  cursor-pointer">
                  <a-popover placement="bottomRight">
                    <template #content>
                      <div class="">
                        <p class="cursor-pointer" @click="toProfile">个人信息</p>
                        <p class="cursor-pointer" @click="onLogout">退出登录</p>
                      </div>
                    </template>
                    <a-avatar :src="userInfo?.avatar ? userInfo.avatar : getAssetsImages('avatar.jpg')" />
                    <span class="p-l1">{{ userInfo?.name }}</span>
                  </a-popover>
                </div>
              </div>
            </a-col>

          </div>
          <div class="contents p2 ">
            <router-view v-slot="{ Component }">
              <keep-alive :include="keepAliveInclude">
                <component :is="Component" />
              </keep-alive>
            </router-view>

          </div>
        </div>
      </a-col>
    </a-row>
    <!--iconify 使用  -->
    <!-- <Icon :icon="iconName" />   -->
  </div>
  <!-- leftDrawerVisible -->
  <!-- 左侧抽屉 -->
  <a-drawer v-model:open="leftDrawerVisible" :bodyStyle="{ padding: '0' }" style="padding: 0;" width="50%"
    :closable="false" placement="left">
    <a-menu :theme="menuTheme" v-model:openKeys="state.openKeys" v-model:selectedKeys="state.selected" :mode="menuMode">
      <recursive-menu :menuData="result.data"></recursive-menu>
    </a-menu>
  </a-drawer>
</template>

<script setup lang="ts">
import { Icon } from '@iconify/vue';
import API from '@/api/index'
import { reactive, ref, toRefs, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router';
import { useUserStore } from '@/store/modules/user'
import recursiveMenu from './components/recursiveMenu.vue';
import { useMenuLists } from '@/store/modules/menu';
import { Grid } from 'ant-design-vue';
import { MenuProps } from 'ant-design-vue';
import { getAssetsImages } from '@/hooks/publicFunction'; //图片
// import router from '@/router';
const useBreakpoint = Grid.useBreakpoint;
const screens = useBreakpoint();
const isMobile = ref(false);
//用户信息
const userInfo: search = useUserStore().userInfo;
// 菜单内容配置
const menuTheme = ref<MenuProps['theme']>('light');
const menuMode = ref<MenuProps['mode']>('inline');
const top = ref<number>(50);
let state = useUserStore().openKeys;
const leftDrawerVisible = ref(false);
const showLeftDrawer = () => {
  leftDrawerVisible.value = true;
};

onMounted(() => {
  requert();
  watchEffect(() => {
    isMobile.value = screens.value.xs;
  });
})
// 接收菜单数据内容
let result = reactive({
  data: []
});
const route = useRoute();
const cachedViews = useMenuLists().cachedViews;
const keepAliveInclude = computed(() => cachedViews);
// 方法事件
const closeLeftDrawer = () => {
  leftDrawerVisible.value = false;
};

// 退出登录
const onLogout = () => {
  let data = useUserStore().logout();
}

//获取菜单信息
const requert = async () => {
  const data: any = useMenuLists().menuLists
  if (data.code === 200) {
    result.data = data.data.menus
  }
}
watch(() =>useMenuLists().menuLists,()=>{
  requert();
})
const router = useRouter();
// 去个人信息页
const toProfile = () => {
  state.selected = [''];
  state.openKeys = [''];
  useUserStore().openKeys = {
    selected: [''],
    openKeys: ['']
  };
  router.push('/user/profile')
}


</script>
<style>
body {
  background: #EFF2FA;
}
</style>
<style scoped lang="less"> .left-hamburger {
   font-size: 20px;
   margin-right: 16px;
   /* 调整左侧汉堡按钮与标题之间的间距 */
 }

 .logo-title {
   color: @color-text;
 }

 .tooltip-div {
   width: 100%;
   color: black;
 }

 .content {

   .left {
     width: 100%;

     .characteristic {
       text-align: center;
       color: #fff;
       font-size: 24px;
       background: @color-background;
       width: 100%;
       height: 3.75rem;
       line-height: 3.75rem;
     }
   }

   .right {
     .top {
       width: 100%;
       background: #fff;
       height: 3.75rem;
       line-height: 3.75rem;

     }

     .top-p {
       padding: 0 2rem;
       box-sizing: border-box;
     }

     .contents {
       width: calc(100% - 40px);
       min-height: calc(100vh - 110px);
       /* 最小高度为 100vh 减去 .boxp2 的高度 */
       overflow-y: auto;
       /* 允许内容超出时显示垂直滚动条 */
       margin: 1.25rem;
       box-sizing: border-box;
       background: #fdfdfd;
       border-radius: .625rem;
       overflow-x: hidden;
       /* 不允许内容横向滚动 */
     }
   }
 }
</style>