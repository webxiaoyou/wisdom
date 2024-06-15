<template>
  <div class="login-page">
      <a-row style="position: absolute;width: 100%;top: 50%; transform: translateY(-50%);">
        <a-col :xs="24" :sm="24" :md="10" :lg="10" :xl="6" style="margin: 0 auto;">
          <div class="right-side h p-l3 p-r3 p-t6 p-b6  ">
            <div class="rigth-box w h flex-column flex-align-center ">
              <div class="title ">
                <h1>智慧管家</h1> 
              </div>
              <div class="content rigth-box1 w m-t5 flex-row flex-justify-center">
                <a-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
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
  // account: 'admin',
  // password: 'admin123',
  account: '',
  password: '',
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
  background-size: 100% 100%;
  background: url('/src/assets/login/haikei.svg') no-repeat center / cover;
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
  border-radius: 20px;

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
