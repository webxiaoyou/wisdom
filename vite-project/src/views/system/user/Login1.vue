<template>
  <div class="login-page">
    <a-row>
      <a-col :xs="0" :sm="0" :md="12" :lg="12" :xl="15">
        <div class="left-side flex-column flex-justify-center">
          <div class="bbg w h">
            <img :src="getAssetsImages('login/bg1.svg')" alt="Background Image 2" class="svg-image"
              style=" z-index: 1;" />
            <img :src="getAssetsImages('login/bg.svg')" alt="Background Image 1" class="svg-image" style="" />
            <img :src="getAssetsImages('login/holographic-screen.gif')" class="gif-image" />
          </div>
        </div>
      </a-col>
      <a-col :xs="24" :sm="24" :md="10" :lg="10" :xl="9">
        <div class="right-side h p-l3 p-r3 p-t14 m-t7">
          <div class="rigth-box w h">
            <div class="title ">
              <h1>智慧管家</h1>
            </div>
            <div class="content rigth-box1 w m-t5">
              <a-col :xs="24" :sm="24" :md="24" :lg="24" :xl="14">
                <a-form ref="formRef" :model="formState" name="basic" autocomplete="off" @finish="onFinish" class="w">
                  <a-form-item name="account" :rules="rules.account">
                    <a-input v-model:value="formState.account" placeholder="请输入账号">
                      <template #prefix>
                        <Icon icon="fe:user" />
                      </template>
                    </a-input>
                  </a-form-item>
                  <a-form-item name="password" :rules="rules.password">
                    <a-input-password v-model:value="formState.password" placeholder="请输入密码">
                      <template #prefix>
                        <Icon icon="teenyicons:password-solid" />
                      </template>
                    </a-input-password>
                  </a-form-item>
                  <a-form-item :wrapper-col="{ offset: 0, span: 24 }">
                    <a-button type="primary" block html-type="submit">登录</a-button>
                  </a-form-item>
                </a-form>
              </a-col>
            </div>
          </div>
        </div>
      </a-col>

    </a-row>

  </div>
</template>

<script setup lang="ts" name="sys_login">
import { Icon } from '@iconify/vue';
import { reactive } from 'vue';
import { getAssetsImages, getDictLabel } from '@/hooks/publicFunction'; //图片
import API from '@/api';
import { useUserStore } from '@/store/modules/user';

const userStore = useUserStore();

interface FormState {
  account: string;
  password: string;
}

const formState = reactive<FormState>({
  account: '13197665421',
  password: '1234',
});

const rules = {
  account: [{ required: true, message: '请输入用户名！' }],
  password: [{ required: true, message: '请输入密码！' }],
}
const formRef = ref();
const onFinish = (values: FormState) => {
  formRef.value
    .validate()
    .then(() => {
      userStore.login(formState).then(
        (res) => {
          // Handle login success
        }
      );
    })
    .catch(error => {
      console.log('error', error);
    });

};

</script>

<style scoped lang="less">
.login-page {
  margin: 0 auto;
  width: 100vw;
  height: 100vh;
  overflow: hidden;
}

.bbg {
  position: relative;

  .svg-image {
    width: 150%;
    // height: 100%;
    position: absolute;
    bottom: 0;
    left: 0;
  }

  .svg-image:nth-child(1) {
    width: 130%;
  }

  .gif-image {
    width: 100%;
    position: absolute;
    left: 30%;
    bottom: 20%;
    z-index: 1;
  }
}

.left-side {
  width: 60%;
  height: 100vh;
}

.left-side img {
  width: 100%;
  height: auto;
}

.right-side {
  position: relative;
  width: 100%;
  box-sizing: border-box;

  .rigth-box {


    .title {
      color: @color-text;
    }
  }

  .rigth-box1 {}
}

.content {
  // width: 25rem;
  // height: 25rem;
  // margin-top: 120px;
  // background-color: rgba(255, 255, 255, 0.25);
  // backdrop-filter: blur(30px);
  // -webkit-backdrop-filter: blur(30px);
  // border: 1px solid rgba(255, 255, 255, 0.18);
  // box-shadow: rgba(142, 142, 142, 0.19) 0px 6px 15px 0px;
  // -webkit-box-shadow: rgba(142, 142, 142, 0.19) 0px 6px 15px 0px;
  // border-radius: 12px;
  // -webkit-border-radius: 12px;
  // color: rgba(255, 255, 255, 0.75);
}
</style>
