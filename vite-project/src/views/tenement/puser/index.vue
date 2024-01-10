<template>
  <div class="animate__animated animate__fadeInDown">
    <!-- 动态搜索框 -->
    <SearchComponent subTitle="搜索" :search="formState" :xs="24" :sm="24" :md="12" :lg="9" :xl="5" :columns="searchColumns"
      @submit="handleChildSubmit" @reset="resetForm" />
  </div>

    <a-space class="animate__animated animate__fadeInDown">
      <a-button type="primary" @click="onAdd('add')" style="background-color: #40ce8c;"
        class="flex-row flex-align-center m-b3" v-hasPermi="['system:pconfig:add']">
        <template #icon>
          <Icon icon="ic:baseline-add" />
        </template>
        新增
      </a-button>
      <a-button type="primary" @click="onAdd('up')" style="background-color: #ffb700;" class="flex-row flex-align-center m-b3"
        :disabled="single" v-hasPermi="['system:pconfig:edit']">
        <template #icon>
          <Icon icon="uil:edit" />
        </template>
        编辑
      </a-button>
      <a-button type="primary" danger class="flex-row flex-align-center m-b3" :disabled="multiple"
        v-hasPermi="['system:pconfig:remove']" @click="onDel">
        <template #icon>
          <Icon icon="ic:outline-delete" />
        </template>
        删除
      </a-button>
    </a-space>


  <div class="content animate__animated animate__fadeIn">
    <index_table :distList="distList" :data="listData?.rows" :columns="columns" v-model="selectedRows" @query="onQuery"
      @state="userOnState" @update:model-value="selectDChange">
    </index_table>
    <div class="flex-row flex-justify-end m-t2">
      <a-pagination v-model:current="formState.pageNum" v-model:page-size="formState.pageSize"
        :total="convertToNumber(listData.total)" :pageSizeOptions="pageSizeOptions" :show-total="total => `共 ${total} 条`"
        @change="onJumperChange" show-size-changer :showQuickJumper="true" @showSizeChange="onShowSizeChange" />
    </div>
  </div>

  <FormDrawerComponent subTitle="确定" :opens="openAddUp" :title="AddUpTitle" :data="DetailData" :xs="24" :sm="24" :md="12"
    :lg="12" :xl="11" :columns="FormColumns" @submit="callSubmit" @close="AddUpClose" />
  <Drawer :opens="open" :columns="drawerColumns" @close="rowDrawerClose" :data="DetailData" :distList="distList" ></Drawer>
  <a-modal v-model:open="opendel" @cancel="AddUpClose" title="你确定要删除以下数据吗？" @ok="onDels(selectedRows)">
    <p v-for="id in selectedRows" :key="id">
      {{ getUserDataById(id).userId }}
    </p>
  </a-modal>
</template>

<script setup lang="ts" name="puser/index">
import { reactive, ref, toRefs, onMounted, watchEffect, nextTick, createApp, watch } from 'vue'
import { useRouter } from 'vue-router';
import { Icon } from '@iconify/vue';
import type { UnwrapRef } from 'vue';
import { useUserDictData } from '@/hooks/dicts';
import { Dayjs } from 'dayjs';
import index_table from './components/IndexTable.vue';
// import IndexRowDrawer from './components/IndexRowDrawer.vue';
// import IndexAddUp from './components/IndexAddUp.vue';
import API from '@/api/index';
import { message } from 'ant-design-vue';
import dayjs from 'dayjs';
import { convertToNumber, calculatePageCount } from '@/hooks/publicFunction';
import type { TableColumnType } from 'ant-design-vue';
let selectedRows = ref([]);  //所选id
let { distList } = useUserDictData(['sys_normal_disable','sys_user_sex','puser_authenticated']);  //字典数据
//表格内容
const columns: TableColumnType[] = [
  {
    title: '编号',
    dataIndex: 'userId',
  },
  // {
  //   title: 'openid',
  //   dataIndex: 'openid',
  // },
  {
    title: '账号',
    dataIndex: 'account',
  },
  {
    title: '手机',
    dataIndex: 'tel',
  },
  {
    title: '用户姓名',
    dataIndex: 'userName',
  },
  {
    title: '用户昵称',
    dataIndex: 'nickname',
  },
  {
    title: '性别',
    dataIndex: 'sex',
  },
  {
    title: '头像',
    dataIndex: 'avatar',
  },
  {
    title: '车牌号',
    dataIndex: 'licensePlate',
  },
  // {
  //   title: '头像ossid',
  //   dataIndex: 'avatarOssid',
  // },
  {
    title: '是否认证',
    dataIndex: 'authenticated',
  },
  {
    title: '最近登录',
    dataIndex: 'lastLoginTime',
  },
  {
    title: '操作',
    dataIndex: 'operation',
  },

];

// 详情展示
const drawerColumns: TableColumn[] = [
  {
    title: '编号',
    dataIndex: 'userId',
  },
  {
    title: 'openid',
    dataIndex: 'openid',
  },
  {
    title: '账号',
    dataIndex: 'account',
  },
  {
    title: '手机',
    dataIndex: 'tel',
  },
  {
    title: '用户姓名',
    dataIndex: 'userName',
  },
  {
    title: '用户昵称',
    dataIndex: 'nickname',
  },
  {
    title: '身份证号',
    dataIndex: 'identity',
  },
  {
    title: '性别',
    dataIndex: 'sex',
  },
  {
    title: '头像',
    dataIndex: 'avatar',
  },
  {
    title: '车牌号',
    dataIndex: 'licensePlate',
  },
  {
    title: '头像ossid',
    dataIndex: 'avatarOssid',
  },
  {
    title: '是否认证',
    dataIndex: 'authenticated',
  },
  {
    title: '最近登录',
    dataIndex: 'lastLoginTime',
  },

];
// 非单个禁用
const single = ref(true)
// 非多个禁用
const multiple = ref(true)

onMounted(() => {
  list()
})

// 请求数据
let formState: UnwrapRef<PUser> = reactive({
  status: null,
  createTime: null,
  updateTime: null,
  pageNum: 1,
  pageSize: 20,
});

//搜索输入框内容
const searchColumns = ref([
  {
    title: '账号',
    dataType: 'inputNumber',
    name: 'account',
  },
  {
    title: '手机号',
    dataType: 'input',
    name: 'tel',
  },
  {
    title: '车牌号',
    dataType: 'input',
    name: 'licensePlate',
  },
  {
    title: '是否认证',
    dataType: 'select',
    keyField: 'dictValue',
    labelField: 'dictLabel',
    name: 'authenticated',
    options: [],
  },
])


//添加表单输入框内容
const FormColumns = ref<Array<FormColumns>>([
  {
    title: 'openid',
    dataType: 'input',
    name: 'openid',
    rules: [{ required: true, message: '请输入openid', trigger: 'change' },]
  },
  {
    title: '账号',
    dataType: 'input',
    name: 'account',
    // rules: [{ required: true, message: '请输入类型（如：小程序、公众号等）', trigger: 'change' },]
  },
  {
    title: '手机号',
    dataType: 'input',
    name: 'tel',
    // rules: [{ required: true, message: '请输入身份标识', trigger: 'change' },]
  },
  {
    title: '用户姓名',
    dataType: 'input',
    name: 'userName',
    // rules: [{ required: true, message: '请输入身份秘钥', trigger: 'change' },]
  },
  {
    title: '用户昵称',
    dataType: 'input',
    name: 'userName',
    rules: [{ required: true, message: '请输入身份秘钥', trigger: 'change' },]
  },
  
  {
    title: '性别',
    dataType: 'select',
    keyField: 'dictValue',
    labelField: 'dictLabel',
    name: 'status',
    defaultValue: "0",
    options: [],
  },
  {
    title: '头像上传',
    dataType: 'uploadImage',
    name: 'avatar',
    upload:{
      limit:1,
      fileListData:{
        ossId:'',url:''
      }
    },
    rules: [{ required: true, message: '请输入上传头像', trigger: 'change' },]
  },

  {
    title: '城市',
    dataType: 'input',
    name: 'city',
    // rules: [{ required: true, message: '请输入身份秘钥', trigger: 'change' },]
  },
  {
    title: '地址',
    dataType: 'input',
    name: 'address',
    // rules: [{ required: true, message: '请输入身份秘钥', trigger: 'change' },]
  },
  {
    title: '车牌号',
    dataType: 'input',
    name: 'licensePlate',
    // rules: [{ required: true, message: '请输入身份秘钥', trigger: 'change' },]
  },
  {
    title: '身份证',
    dataType: 'input',
    name: 'identity',
    // rules: [{ required: true, message: '请输入身份秘钥', trigger: 'change' },]
  },
  {
    title: '是否认证',
    dataType: 'select',
    keyField: 'dictValue',
    labelField: 'dictLabel',
    name: 'authenticated',
    defaultValue: "1",
    options: [],
  },
  
])

let open = ref<boolean>(false); //详情抽屉
let opendel = ref<boolean>(false); //详情抽屉
let DetailData = ref<PUser>({}); //详情数据
let openAddUp = ref<boolean>(false); //详情抽屉
let AddUpTitle = ref('新增')
let listData = ref<any>({})  //表单内容
const pageSizeOptions = ref<string[]>(['10', '20', '40', '100', '200']); // 表格分页

//搜索表单内容
const handleChildSubmit = (submittedData) => {
  formState.pageNum = 1;
  list()
};

watch(()=>formState?.avatar, (newValue:any)=>{
  debugger
  formState.avatar = newValue?.url;
  formState.avatarOssid = newValue?.ossId;
})

//删除按键操作
const onDel = () => {
  if (selectedRows.value.length >= 1) {
    opendel.value = true
  } else {
    message.warning('请选择一条数据')
  }
}

//搜索框重置回调
const resetForm = () => {
  formState.createTime = null
  formState.updateTime = null
  list()
};


watch(
  () => distList.value, // 监听 distList.value 的变化防止数据未加载
  (newValue, oldValue) => {
    // 在 distList 变化时执行的逻辑
    searchColumns.value[3].options = distList.value.puser_authenticated
    FormColumns.value[5].options = distList.value.sys_user_sex
    FormColumns.value[11].options = distList.value.puser_authenticated
  }
);


//事件存放
const list = async () => {
  // 创建一个新的对象，排除 dateRange 字段
  const requestData = { ...formState, dateRange: undefined };
  const { data } = await API.sys_puser_info_list(requestData)
  listData.value = data
}

// 删除操作
const onDels = async (id: Number[]) => {
  if (id.length >= 1) {
    const datas: any = await API.sys_puser_info_del(
      id
    )
    const { code, msg } = datas
    if (code === 200) {
      message.success(msg)
      formState.pageNum = calculatePageCount(listData.value.total, formState.pageSize, formState.pageNum, id.length);
      list()
      opendel.value = false
      multiple.value =  true;
      selectedRows.value = []
    }
  } else {
    message.warning('请选择一条数据')
  }

}

// 查询详情
const onDetail = async (id: number) => {
  const datas: any = await API.sys_puser_info_id(id);
  const { code, data, msg } = datas;
  if (code === 200) {
    DetailData.value = data;
  } else {
    message.error({
      content: () => msg,
      class: 'custom-class',
      style: {
        marginTop: '20vh',
      },
    });
  }
}

//查询详情  表格操作按键操作
const onQuery = async (id: number, status: string) => {

  if (status === 'up') {
    AddUpTitle.value = '编辑'
    await onDetail(id)
    openAddUp.value = true
  } else if (status === 'del') {
    onDels([id])
  } else {
    await onDetail(id)
    open.value = true
  }

}

//新增或者编辑
const onAdd = async (item: string) => {
  DetailData.value = {}
  if (item === 'add') {
    openAddUp.value = true
    AddUpTitle.value = '新增'
  } else {
    if (selectedRows.value.length >= 1) {
      await onDetail(selectedRows.value[0])
      openAddUp.value = true
      AddUpTitle.value = '编辑'
    } else {
      message.warning('请选择一条数据')
    }
  }
}

const callSubmit = async (formState: any, name: string) => {
  if(formState.avatar !=null ||  formState.avatar.length>0 ||  formState.avatar != '' ||  formState.avatar != undefined){
      formState.avatarOssid=formState?.avatar[0]?.ossId
      formState.avatar = formState?.avatar[0]?.url
    }
  if (name === '新增') {
    onAddUp(formState)
  } else {
    onUpdate(formState)
  }
}

//编辑
const onUpdate = async (formStates: PUser) => {
  const datas: any = await API.sys_puser_info_up(formStates);
  const { code, msg } = datas
  if (code === 200) {
    message.success(msg)
    list()
    AddUpClose();
  } else {
    message.error(msg)
  }
}

// 新增操作
const onAddUp = async (callformState: PUser) => {
  const datas: any = await API.sys_puser_info_sa(callformState)
  const { code, msg } = datas
  if (code === 200) {
    message.success(msg)
    list()
    AddUpClose();
  } else {
    message.error(msg)
  }
}

//获取角色列表
const getRoleList = async (roleId: number | string = '') => {
  const datas: any = await API.sys_menu_get_tree(roleId);
  const { data: roleData } = datas
  FormColumns.value[4].options = roleData;
}

// 详情抽屉回调
const rowDrawerClose = () => {
  DetailData.value = {}
  open.value = false
  selectedRows.value = []
};
//检测表格所需内容状态
const selectDChange = (item: number[]) => {
  single.value = item.length != 1;
  multiple.value = !item.length;
}

const AddUpClose = () => {
  openAddUp.value = false
  DetailData.value = {}
  multiple.value=true
  single.value=true
  selectedRows.value = []
  AddUpTitle.value = ''
}

const userOnState = (state: string, id: string) => {
  let data: any = ref({ status: state, id: id })
  onUpdate(data.value)
}


const onJumperChange = (pageNumber: number) => {
  formState.pageNum = pageNumber
  list()
};

const onShowSizeChange = (current: number, pageSize: number) => {
  formState.pageSize = pageSize;
  list()
};

const getUserDataById = (id) => {
  return listData.value.rows.find(user => user.userId === id) || null;
}


// 获取 Vue Router 实例
// const  router  = useRouter();
//     const aaa=(()=>{
//       router.push('/sys_login')
//     })

</script>
<style scoped lang="less">
body {
  background: #fff;
}

.up {
  margin-top: 1.25rem;
}

.content {
  // height: calc(100% - 120px);
  max-height: calc(100vh - 100px);
  /* 调整这里的高度，使其适应你的布局 */
  // overflow-y: auto;
}
</style>