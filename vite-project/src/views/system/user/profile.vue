<template>
  <a-row>
    <a-col :xs="24" :sm="24" :md="24" :lg="24" :xl="7">
      <div>
        <div class="top w ">
          <a-card class="h flex-row flex-justify-center flex-align-center" :bordered="false"
            style="background: transparent;">
            <a-card-meta :title="userInfo.name" :description="userInfo.position" class="flex-row flex-align-center color">
              <template #avatar>
                <div class="card">
                  <a-avatar :size="84" :src="userInfo?.avatar ? userInfo.avatar : getAssetsImages('avatar.jpg')"
                    class="avatar" />
                  <a-upload-dragger class="icons" :showUploadList="false" :customRequest="onUpload"
                    list-type="picture-card" :beforeUpload="handleBeforeUpload" :progress="progress" :maxCount="1">

                    <a-tooltip placement="bottom">
                      <template #title>
                        <span>修改头像？</span>
                      </template>
                      <Icon icon="solar:camera-bold-duotone" width="20" />
                    </a-tooltip>

                  </a-upload-dragger>
                </div>
              </template>
            </a-card-meta>
          </a-card>
        </div>
        <a-col :xs="0" :sm="0" :md="0" :lg="0" :xl="24">
          <div class="buttom flex-row flex-justify-center flex-align-center m-t10 h">
            <img :src="getAssetsImages('user/yg8i.svg')" alt="" style="width: 90%;">
          </div>
        </a-col>
      </div>
    </a-col>
    <a-col :xs="0" :sm="0" :md="0" :lg="0" :xl="1"></a-col>
    <a-col :xs="24" :sm="24" :md="24" :lg="24" :xl="16">
      <a-card title="个人资料" :bordered="false" style="" class="w">
        <FormComponent subTitle="更新" :data="userInfo" :xs="24" :sm="24" :md="12" :lg="12" :xl="11" :columns="FormColumns"
          @submit="callSubmit" showCall :showReset="false"></FormComponent>
      </a-card>
      <a-card title="安全" :bordered="false" class="w m-t3">
        <p>账号：{{ userInfo.account }}</p>
        <p>密码： <a-popover title="修改密码" trigger="click">
            <template #content>
              <a-form :model="passwordData" :rules="rules" ref="passwordForm">
                <a-form-item label="旧密码" ref="oldPassword" name="oldPassword">
                  <a-input v-model:value="passwordData.oldPassword" type="password" autocomplete="current-password" />
                </a-form-item>
                <a-form-item label="新密码" ref="newPassword" name="newPassword">
                  <a-input v-model:value="passwordData.newPassword" type="password" autocomplete="new-password" />
                </a-form-item>
                <a-form-item label="确认密码" ref="confirmPassword" name="confirmPassword">
                  <a-input v-model:value="passwordData.confirmPassword" type="password" autocomplete="new-password" />
                </a-form-item>
                <a-form-item>
                  <a-button type="primary" @click="handleChangePassword">修改密码</a-button>
                </a-form-item>
              </a-form>
            </template>
            <a-button type="primary" ghost>点击修改密码</a-button>
          </a-popover></p>
      </a-card>
    </a-col>
  </a-row>
</template>
  
<script setup lang="ts" name="profile">
import { reactive, toRefs, onMounted, getCurrentInstance } from 'vue'
import { useRouter } from 'vue-router';
import { Icon } from '@iconify/vue';
import API from '@/api/index';
import type { Rule } from 'ant-design-vue/es/form';
// import FormComponent from '@/components/FormDrawerComponent.vue';
import { getAssetsImages } from '@/hooks/publicFunction'; //图片
import { useUserStore } from '@/store/modules/user';
import { useUserDictData } from '@/hooks/dicts';
import { message, UploadProps, Upload } from 'ant-design-vue';
let userInfo: search = useUserStore().userInfo;
let openAddUp = ref<boolean>(false); //新增、修改抽屉
let { distList } = useUserDictData(['sys_user_sex']);  //字典数据
//添加表单输入框内容
const FormColumns = ref<Array<FormColumns>>([
  {
    title: '名称',
    dataType: 'input',
    name: 'name',
    rules: [{ required: true, message: '请输入名称', trigger: 'change' },]
  },
  {
    title: '性别',
    dataType: 'select',
    keyField: 'dictValue',
    labelField: 'dictLabel',
    name: 'status',
    options: [],
  },
  {
    title: '年龄',
    dataType: 'inputNumber',
    name: 'age',
    defaultValue: 1,
    // rules: [{ required: true, message: '角色顺序不能为空', trigger: 'change' },]
  },
  {
    title: '电话',
    dataType: 'input',
    name: 'phone',
    rules: [{ required: true, message: '请输入电话', trigger: 'change' },]
  },
  {
    title: '邮箱',
    dataType: 'input',
    name: 'email',
    // rules: [{ required: true, message: '请输入电话', trigger: 'change' },]
  },
  {
    title: '岗位',
    dataType: 'select',
    keyField: 'postCode',
    labelField: 'postName',
    name: 'position',
    disabled: true,
    options: [],
  },
  {
    title: '所属角色',
    dataType: 'select',
    keyField: 'id',
    labelField: 'roleName',
    name: 'roleIds',
    disabled: true,
    mode: "multiple",
    options: [],
  },
])
const fileList = ref<UploadProps['fileList']>([]);
onMounted(() => {
  getRoleList();
})

watch(
  () => distList.value, // 监听 distList.value 的变化防止数据未加载
  (newValue, oldValue) => {
    // 在 distList 变化时执行的逻辑
    FormColumns.value[1].options = distList.value.sys_user_sex
  }
);

//获取角色列表
const getRoleList = async () => {
  const { data: roleData } = await API.sys_role_dict_list();
  const { data } = await API.sys_post_dict_list();
  // 然后将过滤后的数据赋值给 distList
  FormColumns.value[5].options = data;
  FormColumns.value[6].options = roleData;
}

//表单提交的回调
const callSubmit = async (formState: search, name: string) => {
  console.log(name, formState)
  onUpdate(formState);
}

//编辑
const onUpdate = async (formStates: search) => {
  const datas: any = await API.sys_user_up(formStates);
  const { code, msg } = datas
  if (code === 200) {
    message.success(msg)
    await useUserStore().getUserInfoUP();
  } else {
    message.error(msg)
  }
}

// 密码修改表单
const passwordForm = ref();
const passwordData = reactive({
  oldPassword: '',
  newPassword: '',
  confirmPassword: '',
});
// eslint-disable-next-line require-await
const validatePass = async (_rule, value) => {
  // 二次验证
  // const regex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[\da-zA-Z]{8,16}$/;
  const regex = /^(?=.*\d)(?=.*[a-zA-Z])[\da-zA-Z]{8,16}$/;

  const a = regex.test(value)
  if (value === '') {
    return Promise.reject('新密码不能为空');
  } else {
    if (!a) {
      // return Promise.reject('请输入字符大小写和数字组合，至少八位');
      return Promise.reject('请输入字英文和数字组合，至少八位');

    }
    return Promise.resolve();
  }
};

// eslint-disable-next-line require-await
const testPass = async (_rule, value) => {
  // 二次验证
  console.log(passwordData.newPassword)
  console.log(value)
  if (value !== passwordData.newPassword) {
    return Promise.reject('两次输入密码不一致');
  } else {
    if (passwordData.newPassword === '') {
      return Promise.reject('确认密码不能为空');
    }
  }
  return Promise.resolve();

};
const rules: Record<string, Rule[]> = {
  oldPassword: [
    { required: true, message: '请输入旧密码', trigger: 'change' },
  ],
  newPassword: [
    {
      validator: validatePass,
      required: true,
      trigger: 'change',
    },
  ],
  confirmPassword: [
    {
      validator: testPass,
      required: true,
      trigger: 'change',
    },
  ],
}
const fileType = ref<Array<any>>(['jpg', 'jpeg', 'png', 'gif', 'bmp']);
const fileSize = ref<number>(5);
const handleBeforeUpload = async (file) => {
  let isImg = false;
  if (fileType.value.length) {
    let fileExtension = "";
    if (file.name.lastIndexOf(".") > -1) {
      fileExtension = file.name.slice(file.name.lastIndexOf(".") + 1);
    }
    isImg = fileType.value.some((type: any) => {
      if (file.type.indexOf(type) > -1) return true;
      if (fileExtension && fileExtension.indexOf(type) > -1) return true;
      return false;
    });
  } else {
    isImg = file.type.indexOf("image") > -1;
  }

  if (!isImg) {
    message.error(`文件格式不正确, 请上传${fileType.value.join("/")}图片格式文件!`);
    return Upload.LIST_IGNORE;
  }
  if (fileSize.value) {
    const isLt = file.size / 1024 / 1024 < fileSize.value;
    if (!isLt) {
      message.error(`上传头像图片大小不能超过 ${fileSize.value} MB!`);
      return Upload.LIST_IGNORE;
    }
  }
}
//上传进度
const progress: UploadProps['progress'] = {
  strokeColor: {
    '0%': '#108ee9',
    '100%': '#87d068',
  },
  strokeWidth: 3,
  format: percent => `${parseFloat(percent.toFixed(2))}%`,
  class: 'test',
};

//上传操作
const onUpload = async (file) => {
  // 创建一个 FormData 对象，将文件信息添加到其中
  const formData = new FormData();
  formData.append('avatarfile', file.file);
  const data: any = await API.sys_user_avatar(formData)
  if (data.code === 200) {
    message.success(data.msg)
    userInfo.avatar = data.data.imgUrl;
    // file.onSuccess(data.data.imgUrl, file.file.name);
  } else {
    // file.onError(data.msg, "上传失败");
    message.error(data.msg)
  }
}

const handleChangePassword = () => {
  passwordForm.value
    .validate()
    .then(() => {
      passwordUp();
    })
    .catch(error => {
      console.log('error', error);
    });
};
const passwordUp = async () => {
  let datas = {
    account: userInfo.account,
    id: userInfo.id,
    password: passwordData.oldPassword,
    newPassword: passwordData.newPassword,
    confirmPassword: passwordData.confirmPassword
  }
  const data: any = await API.sys_user_password_up(datas);
  const { code, msg } = data
  if (code == 200) {
    message.success(msg)
    useUserStore().logout()

  } else {
    message.error(msg)
  }

}
</script>
<style scoped lang="less">
.top {
  background: @color-background;
  height: 200px;

  :deep(.ant-card .ant-card-meta-title),
  :deep(.ant-card-meta-description) {
    color: @color-text-night;
  }
}

.card {
  box-shadow: 0 10px 10px rgba(0, 0, 0, 0.212);
  background: #fff;
  display: flex;
  border-radius: 50px;
  justify-content: center;
  position: relative;
  transition: all .4s;

  // &::before {
  //   content: '---content---';
  //   letter-spacing: 0.2em;
  //   position: absolute;
  //   bottom: 0px;
  //   left: 20px;
  //   color: rgb(51, 51, 51);
  //   font-size: 0.8em;
  //   font-weight: 700;
  // }
  .icons {
    padding: 0;
    cursor: pointer;
    position: absolute;
    bottom: 0px;
  }

  .avatar {
    width: 100%;
    height: 100%;
    // border-radius: 20px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.212);
    // cursor: pointer;
    z-index: 10;
    transition: all .4s;
    background-color: #fff;
  }

  :deep(.ant-upload-wrapper .ant-upload-drag .ant-upload) {
    padding: 0;
  }

  &:hover .avatar {
    transform: translateY(-30px);
  }
}
</style>