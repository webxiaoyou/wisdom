<template>
  <div class="animate__animated animate__fadeInDown">
    <!-- 动态搜索框 -->
    <SearchComponent subTitle="搜索" :search="formState" :xs="24" :sm="24" :md="12" :lg="9" :xl="5" :columns="searchColumns"
      @submit="handleChildSubmit" @reset="resetForm" />
  </div>

    <!-- <a-space class="animate__animated animate__fadeInDown">
      <a-button type="primary" @click="onAdd('add')" style="background-color: #40ce8c;"
        class="flex-row flex-align-center m-b3" v-hasPermi="['system:post:add']">
        <template #icon>
          <Icon icon="ic:baseline-add" />
        </template>
        新增
      </a-button>
      <a-button type="primary" @click="onAdd('up')" style="background-color: #ffb700;" class="flex-row flex-align-center m-b3"
        :disabled="single" v-hasPermi="['system:post:edit']">
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
    </a-space> -->


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
<!-- 
  <FormDrawerComponent subTitle="确定" :opens="openAddUp" :title="AddUpTitle" :data="DetailData" :xs="24" :sm="24" :md="12"
    :lg="12" :xl="11" :columns="FormColumns" @submit="callSubmit" @close="AddUpClose" /> -->
  
     <PIndexAddUp :opens="openAddUp" :title="AddUpTitle" :data="DetailData" :distList="distList" @close="AddUpClose"
    @submit="callSubmit">   </PIndexAddUp>
  <a-modal v-model:open="opendel" @cancel="AddUpClose" title="你确定要删除以下数据吗？" @ok="onDels(selectedRows)">
    <p v-for="id in selectedRows" :key="id">
      {{ getUserDataById(id).postName }}
    </p>
  </a-modal>
</template>

<script setup lang="ts" name="prepair">
import { reactive, ref, toRefs, onMounted, watchEffect, nextTick, createApp, watch } from 'vue'
import { useRouter } from 'vue-router';
import { Icon } from '@iconify/vue';
import type { UnwrapRef } from 'vue';
import { useUserDictData } from '@/hooks/dicts';
import { Dayjs } from 'dayjs';
import index_table from './components/IndexTable.vue';
import PIndexAddUp from './components/pindexAddUp.vue';
import { useUserStore } from '@/store/modules/user';
// import IndexRowDrawer from './components/IndexRowDrawer.vue';
// import IndexAddUp from './components/IndexAddUp.vue';
import API from '@/api/index';
import { message } from 'ant-design-vue';
import dayjs from 'dayjs';
import { convertToNumber, calculatePageCount } from '@/hooks/publicFunction';
import type { TableColumnType } from 'ant-design-vue';
let selectedRows = ref([]);  //所选id
let { distList } = useUserDictData(['sys_normal_disable','prepair_status']);  //字典数据
const userStore:any = useUserStore();
//表格内容
const columns: TableColumnType[] = [
  {
    title: '编号',
    dataIndex: 'repairId',
  },
  {
    title: '物业小区',
    dataIndex: 'propertyId',
  },
  
  {
    title: '栋数',
    dataIndex: 'buildingId',
  },

  {
    title: '楼层房间',
    dataIndex: 'floorId',
  },
  {
    title: '报修描述',
    dataIndex: 'description',
  },
  {
    title: '报修状态',
    dataIndex: 'status',
  },
  {
    title: '报修用户',
    dataIndex: 'userId',
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
  if(userStore?.userInfo?.deptId){
    GetUnitDictlist();
  }
})

// 请求数据
let formState: UnwrapRef<PRepair> = reactive({
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
    defaultValue:userStore?.userInfo?.deptId,
    disabled:userStore?.userInfo?.deptId,
    options: [],
  },
  {
    title: '报修用户',
    dataType: 'select',
    keyField: 'value',
    labelField: 'label',
    name: 'userId',
    search:true,
    // defaultValue:userStore?.userInfo?.deptId,
    // disabled:userStore?.userInfo?.deptId,
    options: [],
  },
  {
    title: '状态',
    dataType: 'select',
    keyField: 'dictValue',
    labelField: 'dictLabel',
    name: 'status',
    options: [],
  },
])

// //添加表单输入框内容
// const FormColumns = ref<Array<FormColumns>>([
//   {
//     title: '物业小区',
//     dataType: 'select',
//     keyField: 'value',
//     labelField: 'label',
//     name: 'propertyId',
//     options: [],
//     rules: [{ required: true, message: '请选择物业小区', trigger: 'change' },]
//   },
//   {
//     title: '报修用户',
//     dataType: 'select',
//     keyField: 'value',
//     labelField: 'label',
//     name: 'userId',
//     search:true,
//     options: [],
//     rules: [{ required: true, message: '请选择报修用户', trigger: 'change' },]
//   },
//   {
//     title: '手机号',
//     dataType: 'input',
//     name: 'tel',
//     rules: [{ required: true, message: '请输入用户手机号', trigger: 'change' },]
//   },

//   {
//     title: '报修栋数',
//     dataType: 'select',
//     keyField: 'dictValue',
//     labelField: 'dictLabel',
//     name: 'buildingId',
//     options: [],
//     rules: [{ required: true, message: '请选择报修栋数', trigger: 'change' },]
//   },
//   {
//     title: '报修房间',
//     dataType: 'select',
//     keyField: 'dictValue',
//     labelField: 'dictLabel',
//     name: 'floorId',
//     options: [],
//     rules: [{ required: true, message: '请选择报修房间', trigger: 'change' },]
//   },
//   {
//     title: '报修类别',
//     dataType: 'select',
//     keyField: 'dictValue',
//     labelField: 'dictLabel',
//     name: 'category',
//     options: [],
//     rules: [{ required: true, message: '请选择报修类别', trigger: 'change' },]
//   },
//   {
//     title: '报修状态',
//     dataType: 'select',
//     keyField: 'dictValue',
//     labelField: 'dictLabel',
//     name: 'status',
//     defaultValue: "0",
//     options: [],
//   },
//   {
//     title: '报修描述',
//     dataType: 'textarea',
//     dataTypename: 'string',
//     name: 'description',
//     rules: [{ required: true, message: '请输入报修描述', trigger: 'change' },]
//   },
//   {
//     title: '文件上传',
//     dataType: 'uploadImage',
//     name: 'imageUrls',
//     upload:{
//       limit:1,
//       fileListData:{
//         ossId:'',url:''
//       }
//     },
//     rules: [{ required: true, message: '请输入上传头像', trigger: 'change' },]
//   },
// ])

let open = ref<boolean>(false); //详情抽屉
let opendel = ref<boolean>(false); //详情抽屉
let DetailData = ref<PRepair>({}); //详情数据
let openAddUp = ref<boolean>(false); //详情抽屉
let AddUpTitle = ref('新增')
let listData = ref<any>({})  //表单内容
const pageSizeOptions = ref<string[]>(['10', '20', '40', '100', '200']); // 表格分页

//搜索表单内容
const handleChildSubmit = (submittedData) => {
  formState.pageNum = 1;
  list()
  if(formState.propertyId){
    GetUnitDictlist();
   }else{
    formState.userId=null
    searchColumns.value[1].options  = []
   }
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
    // searchColumns.value[0].options = distList.value.pproperty_dict
    searchColumns.value[2].options = distList.value.prepair_status
    // FormColumns.value[3].options = distList.value.sys_normal_disable
  }
);


//事件存放
const list = async () => {
  // 创建一个新的对象，排除 dateRange 字段
  const requestData = { ...formState, dateRange: undefined };
  const { data } = await API.tmt_prepair_list(requestData)
  listData.value = data
}
// 查询业主信息
const GetUnitDictlist = async () => {
  // 创建一个新的对象，排除 dateRange 字段
  const data: any = await API.tmt_pproperty_unit_dict_list({
    isBuilding: true,
    propertyId:userStore?.userInfo?.deptId ?userStore?.userInfo?.deptId:formState.propertyId
  })
  searchColumns.value[1].options  = data.data
}

// 查询小区字典详情
const dictList = async () => {
  const datas: any = await API.sys_pproperty_dict_id();
  const { code, data, msg } = datas;
  if (code === 200) {
    distList.value.pproperty_dict = data
    searchColumns.value[0].options = data
  } else {
   
  }
}
// 删除操作
const onDels = async (id: Number[]) => {
  if (id.length >= 1) {
    const datas: any = await API.tmt_prepair_del(
      id
    )
    const { code, msg } = datas
    if (code === 200) {
      message.success(msg)
      formState.pageNum = calculatePageCount(listData.value.total, formState.pageSize, formState.pageNum, id.length);
      list()
      opendel.value = false
      multiple.value = ! multiple.value;
      selectedRows.value = []
    }
  } else {
    message.warning('请选择一条数据')
  }

}

// 查询详情
const onDetail = async (id: number) => {
  const datas: any = await API.tmt_prepair_id(id);
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

const callSubmit = async (formState: PRepair, name: string) => {
  console.log(name)
  if (name === '新增') {
    onAddUp(formState)
  } else {
    onUpdate(formState)
  }
}

//编辑
const onUpdate = async (formStates: PRepair) => {
  const datas: any = await API.tmt_prepair_up(formStates);
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
const onAddUp = async (callformState: PRepair) => {
  const datas: any = await API.tmt_prepair_sa(callformState)
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
// const getRoleList = async (roleId: number | string = '') => {
//   const datas: any = await API.sys_menu_get_tree(roleId);
//   const { data: roleData } = datas
//   FormColumns.value[4].options = roleData;
// }

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
  margin-top: 1.25rem;
}

.content {
  // height: calc(100% - 120px);
  max-height: calc(100vh - 100px);
  /* 调整这里的高度，使其适应你的布局 */
  // overflow-y: auto;
}
</style>