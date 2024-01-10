<template>
  <div class="animate__animated animate__fadeInDown">
    <!-- 动态搜索框 -->
    <SearchComponent subTitle="搜索" :search="formState" :xs="24" :sm="24" :md="12" :lg="9" :xl="5" :columns="searchColumns"
      @submit="handleChildSubmit" @reset="resetForm" />
  </div>

  <a-space class="animate__animated animate__fadeInDown">
    <a-button type="primary" @click="onAdd('add')" style="background-color: #40ce8c;"
      class="flex-row flex-align-center m-b3" v-hasPermi="['system:post:add']">
      <template #icon>
        <Icon icon="ic:baseline-add" />
      </template>
      新增
    </a-button>
    <a-button type="primary" @click="onAdd('up')" style="background-color: #ffb700;"
      class="flex-row flex-align-center m-b3" :disabled="single" v-hasPermi="['system:post:edit']">
      <template #icon>
        <Icon icon="uil:edit" />
      </template>
      编辑
    </a-button>
    <a-button type="primary" danger class="flex-row flex-align-center m-b3" :disabled="multiple"
      v-hasPermi="['system:post:remove']" @click="onDel">
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
      {{ getUserDataById(id).postName }}
    </p>
  </a-modal>
</template>

<script setup lang="ts" name="pcommunity_activity">
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
import { useUserStore } from '@/store/modules/user';
import { convertToNumber, calculatePageCount } from '@/hooks/publicFunction';
import type { TableColumnType } from 'ant-design-vue';
let selectedRows = ref([]);  //所选id
const userStore: any = useUserStore();
let { distList } = useUserDictData(['sys_normal_disable']);  //字典数据
//表格内容
const columns: TableColumnType[] = [
  {
    title: '编号',
    dataIndex: 'activityId',
  },
  {
    title: '活动标题',
    dataIndex: 'title',
  },
  {
    title: '活动封面',
    dataIndex: 'coverUrl',
  },

  {
    title: '活动地点',
    dataIndex: 'location',
  },
  {
    title: '物业小区',
    dataIndex: 'propertyId',
  },
  {
    title: '开始时间',
    dataIndex: 'startTime',
  },
  {
    title: '结束时间',
    dataIndex: 'endTime',
  },

  {
    title: '操作',
    dataIndex: 'operation',
  },

];
// 非单个禁用
const single = ref(true)
// 非多个禁用
const multiple = ref(true)

onMounted(() => {
  list();
  dictList();
})

// 请求数据
let formState: UnwrapRef<Activity> = reactive({
  postName: '',   //名称
  postCode: '', // 权限字符
  status: null,
  createTime: null,
  updateTime: null,
  pageNum: 1,
  pageSize: 20,
});

//搜索输入框内容
const searchColumns = ref([
  {
    title: '物业小区',
    dataType: 'select',
    keyField: 'value',
    labelField: 'label',
    name: 'propertyId',
    defaultValue: userStore?.userInfo?.deptId,
    disabled: userStore?.userInfo?.deptId,
    options: [],
  },
  {
    title: '活动标题',
    dataType: 'input',
    dataTypename: 'string',
    name: 'title',
  },
  {
    title: '活动地点',
    dataType: 'input',
    dataTypename: 'string',
    name: 'location',
  },
  {
    title: '时间',
    dataType: 'rangePicker',
    name: 'params',
  },
])
// 详情展示
const drawerColumns: TableColumn[] = [
// {
//     title: '编号',
//     dataIndex: 'activityId',
//   },
  {
    title: '活动标题',
    dataIndex: 'title',
  },
  {
    title: '活动封面',
    dataIndex: 'coverUrl',
  },

  {
    title: '活动地点',
    dataIndex: 'location',
  },
  {
    title: '物业小区',
    dataIndex: 'propertyId',
  },
  {
    title: '开始时间',
    dataIndex: 'startTime',
  },
  {
    title: '结束时间',
    dataIndex: 'endTime',
  },
  {
    title: '活动内容',
    dataIndex: 'content',
  },
]
//添加表单输入框内容
const FormColumns = ref<Array<FormColumns>>([
  {
    title: '物业小区',
    dataType: 'select',
    keyField: 'value',
    labelField: 'label',
    name: 'propertyId',
    defaultValue: userStore?.userInfo?.deptId,
    disabled: userStore?.userInfo?.deptId,
    options: [],
    rules: [{ required: true, message: '请选择物业小区', trigger: 'change' },]
  },
  {
    title: '活动标题',
    dataType: 'input',
    name: 'title',
    rules: [{ required: true, message: '请输入活动标题', trigger: 'change' },]
  },
  {
    title: '活动描述',
    dataType: 'input',
    name: 'description',
    rules: [{ required: true, message: '请输入活动描述', trigger: 'change' },]
  },
  {
    title: '活动地点',
    dataType: 'input',
    name: 'location',
    rules: [{ required: true, message: '请输入活动地点', trigger: 'change' },]
  },
  {
    title: '活动封面',
    dataType: 'uploadImage',
    name: 'coverUrl',
    upload: {
      limit: 1,
      fileListData: {
         url: ''
      }
    },
    // rules: [{ required: true, message: '请输入上传头像', trigger: 'change' },]
  },
  {
    title: '活动时间',
    dataType: 'rangePicker',
    name: 'params',
    rules: [{ required: true, message: '请选择开始与结束时间', trigger: 'change' },]
  },
  // {
  //   title: '状态',
  //   dataType: 'select',
  //   keyField: 'dictValue',
  //   labelField: 'dictLabel',
  //   name: 'status',
  //   defaultValue: "0",
  //   options: [],
  // },
  {
    title: '活动内容',
    dataType: 'editor',
    name: 'content',
    xs: 36,
    sm: 24,
    md: 24,
    lg: 24,
    xl: 24,
    labelCol: { span: 2 }
  },
])

let open = ref<boolean>(false); //详情抽屉
let opendel = ref<boolean>(false); //详情抽屉
let DetailData = ref<Activity>({}); //详情数据
let openAddUp = ref<boolean>(false); //详情抽屉
let AddUpTitle = ref('新增')
let listData = ref<any>({})  //表单内容
const pageSizeOptions = ref<string[]>(['10', '20', '40', '100', '200']); // 表格分页

//搜索表单内容
const handleChildSubmit = (submittedData) => {
  formState.pageNum = 1;
  list()
};

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
    // searchColumns.value[2].options = distList.value.sys_normal_disable
    // FormColumns.value[0].options = distList.value.sys_normal_disable
  }
);


//事件存放
const list = async () => {
  // 创建一个新的对象，排除 dateRange 字段
  const formStates = { ...formState }
  if (formStates.params != null && formStates.params.length > 1) { formStates.params = formStates.params.join(','); }
  const { data } = await API.cmy_activity_list(formStates)
  listData.value = data
}

// 查询小区字典详情
const dictList = async () => {
  const datas: any = await API.sys_pproperty_dict_id();
  const { code, data, msg } = datas;
  if (code === 200) {
    distList.value.pproperty_dict = data
    FormColumns.value[0].options = data
    searchColumns.value[0].options = data
  } else {

  }
}

// 删除操作
const onDels = async (id: Number[]) => {
  if (id.length >= 1) {
    const datas: any = await API.cmy_activity_del(
      id
    )
    const { code, msg } = datas
    if (code === 200) {
      message.success(msg)
      formState.pageNum = calculatePageCount(listData.value.total, formState.pageSize, formState.pageNum, id.length);
      list()
      opendel.value = false
      multiple.value = !multiple.value;
      selectedRows.value = []
    }
  } else {
    message.warning('请选择一条数据')
  }

}

// 查询详情
const onDetail = async (id: number) => {
  const datas: any = await API.cmy_activity_info(id);
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
  // DetailData.value = {}
  if (item === 'add') {
    DetailData.value = null
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

const callSubmit = async (formState: Activity, name: string) => {
  console.log(name)
  if(formState.coverUrl[0].url){
    formState.coverUrl = formState.coverUrl[0].url
  }
  if (name === '新增') {
    onAddUp(formState)
  } else {
    onUpdate(formState)
  }
}

//编辑
const onUpdate = async (formStates: Activity) => {
  // if (formStates.params != null && formStates.params.length > 1) { formStates.params = formStates.params.join(','); }
  const datas: any = await API.cmy_activity_up(formStates);
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
const onAddUp = async (callformStates: Activity) => {
  
  const callformState:Activity ={...callformStates}

  // if (callformState.params != null && callformState.params.length > 1) { callformState.params = callformState.params.join(','); }
  const datas: any = await API.cmy_activity_sa(callformState)
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
  multiple.value = true
  single.value = true
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
  return listData.value.rows.find(user => user.postId === id) || null;
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
  margin-top: 20px;
}

.content {
  // height: calc(100% - 7.5rem);
  max-height: calc(100vh - 6.25rem);
  /* 调整这里的高度，使其适应你的布局 */
  // overflow-y: auto;
}
</style>