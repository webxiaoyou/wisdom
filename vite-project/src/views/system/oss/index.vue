<template>
  <div class="animate__animated animate__fadeInDown">
    <!-- 动态搜索框 -->
    <SearchComponent subTitle="搜索" :search="formState" :xs="24" :sm="24" :md="12" :lg="9" :xl="5" :columns="searchColumns"
      @submit="handleChildSubmit" @reset="resetForm" />
  </div>
  <div class="animate__animated animate__fadeInDown">
    <a-space>
      <a-button type="primary" @click="uploadModalOpen=true" class="flex-row flex-align-center b-s m-b3"
        v-hasPermi="['system:oss:add']"> 
        <template #icon>
          <Icon icon="ic:baseline-add" />
        </template>
        上传文件
      </a-button>
      <a-button type="primary" @click="onAdd('PvwImg')" class="flex-row flex-align-center b-w m-b3"
        v-hasPermi="['system:oss:list']">
        <template #icon>
          <Icon icon="uil:edit" />
        </template>
        预览:{{ PvwImg ? "关闭" : "开启" }}
      </a-button>
      <a-button type="primary" danger class="flex-row flex-align-center m-b3" :disabled="multiple"
        v-hasPermi="['system:oss:remove']" @click="onDel">
        <template #icon>
          <Icon icon="ic:outline-delete" />
        </template>
        删除
      </a-button>
      <a-button type="primary" class="flex-row flex-align-center m-b3" v-hasPermi="['system:oss:list']"
        @click="$router.push('/system/oss-config')">
        <template #icon>
          <Icon icon="ic:outline-delete" />
        </template>
        配置管理
      </a-button>
    </a-space>
  </div>

  <div class="content animate__animated animate__fadeIn m-t3">
    <index_table :distList="distList" :data="listData?.rows" :columns="columns" v-model="selectedRows" @query="onQuery"
      @state="userOnState" @update:model-value="selectDChange" :pvw-img="PvwImg">
    </index_table>
    <div class="flex-row flex-justify-end m-t2">
      <a-pagination v-model:current="formState.pageNum" v-model:page-size="formState.pageSize"
        :total="convertToNumber(listData.total)" :pageSizeOptions="pageSizeOptions" :show-total="total => `共 ${total} 条`"
        @change="onJumperChange" show-size-changer :showQuickJumper="true" @showSizeChange="onShowSizeChange" />
    </div>
  </div>

  <FormDrawerComponent subTitle="确定" :opens="openAddUp" :title="AddUpTitle" :data="DetailData" :xs="24" :sm="24" :md="12"
    :lg="12" :xl="11" :columns="FormColumns" @submit="callSubmit" @close="AddUpClose" />

  <a-modal v-model:open="opendel" @cancel="AddUpClose" title="你确定要删除以下数据吗？" @ok="onDels(selectedRows)">
    <p v-for="id in selectedRows" :key="id">
      {{ getUserDataById(id).fileName }}
    </p>
  </a-modal>
  <upload_modal :opens="uploadModalOpen" v-model:value="fileList" @submit="modalSubmit" @close="modalClose"></upload_modal>
</template>

<script setup lang="ts" name="oss">
import { reactive, ref, toRefs, onMounted, watchEffect, nextTick, createApp, watch } from 'vue'
import { useRouter } from 'vue-router';
import { Icon } from '@iconify/vue';
import type { UnwrapRef } from 'vue';
import { useUserDictData } from '@/hooks/dicts';
import index_table from './components/IndexTable.vue';
import API from '@/api/index';
import { message } from 'ant-design-vue';
import { convertToNumber, calculatePageCount, download } from '@/hooks/publicFunction';
import type { TableColumnType } from 'ant-design-vue';
import upload_modal from '@/components/upload/UploadModal.vue';
let selectedRows = ref([]);  //所选id
let { distList } = useUserDictData(['sys_normal_disable']);  //字典数据
//表格内容
const columns: TableColumnType[] = [
  {
    title: '文件名',
    dataIndex: 'fileName',
    // width: 40,

  },
  {
    title: '原名',
    dataIndex: 'originalName',

  },
  {
    title: '文件后缀',
    dataIndex: 'fileSuffix',
    width: 100,
    // ellipsis: true,
  },
  {
    title: '文件展示',
    dataIndex: 'url',
  },
  {
    title: '创建时间',
    dataIndex: 'createTime',
  },
  {
    title: '上传人',
    dataIndex: 'createBy',
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
const uploadModalOpen = ref(false);
onMounted(() => {
  list()
})
const fileList = ref<Array<any>>([]);  //文件上传回调

// 请求数据
let formState: UnwrapRef<SysRole> = reactive({
  status: null,
  createTime: null,
  updateTime: null,
  pageNum: 1,
  pageSize: 20,
  params: null, // 时间范围选择
  menuIds: []
});

//搜索输入框内容
const searchColumns = ref([
  {
    title: '文件名',
    dataType: 'input',
    dataTypename: 'string',
    name: 'fileName',
  },
  {
    title: '原名',
    dataType: 'input',
    dataTypename: 'string',
    name: 'originalName',
  },
  {
    title: '文件后缀',
    dataType: 'input',
    dataTypename: 'string',
    name: 'fileSuffix',
  },
  {
    title: '时间',
    dataType: 'rangePicker',
    name: 'params',
  },
  {
    title: '上传人',
    dataType: 'input',
    dataTypename: 'string',
    name: 'createBy',
  },
])


//添加表单输入框内容
const FormColumns = ref<Array<FormColumns>>([
  {
    title: '文件名',
    dataType: 'input',
    dataTypename: 'text',
    name: 'roleName',
    rules: [{ required: true, message: '请输入角色名称', trigger: 'change' },]
  },
  {
    title: '权限符',
    dataType: 'input',
    dataTypename: 'text',
    name: 'roleKey',
    rules: [{ required: true, message: '请输入权限符', trigger: 'change' },]
  },
  {
    title: '角色排序',
    dataType: 'inputNumber',
    dataTypename: 'text',
    name: 'roleSort',
    defaultValue: 1,
    rules: [{ required: true, message: '角色顺序不能为空', trigger: 'change' },]
  },
  {
    title: '状态',
    dataType: 'select',
    keyField: 'dictValue',
    labelField: 'dictLabel',
    name: 'status',
    defaultValue: "0",
    options: [],
  },
  {
    title: '菜单权限',
    dataType: 'selectMenuTree',
    keyField: 'value',
    labelField: 'label',
    children: 'children',
    name: 'menuIds',
    options: [],
  },
  {
    title: '备注',
    dataType: 'textarea',
    dataTypename: 'string',
    name: 'remark',
  },
])

let open = ref<boolean>(false); //详情抽屉
let opendel = ref<boolean>(false); //详情抽屉
let DetailData = ref<SysRole>({}); //详情数据
let openAddUp = ref<boolean>(false); //详情抽屉
let AddUpTitle = ref('新增')
let listData = ref<any>({})  //表单内容
const pageSizeOptions = ref<string[]>(['10', '20', '40', '100', '200']); // 表格分页
const PvwImg = ref<boolean>(true);

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
    FormColumns.value[3].options = distList.value.sys_normal_disable
  }
);


//事件存放
const list = async () => {
  // 创建一个新的对象，排除 dateRange 字段
  const formStates = { ...formState }
  if (formStates.params != null && formStates.params.length > 1) { formStates.params = formStates.params.join(','); }

  const { data } = await API.sys_file_list(formStates)
  listData.value = data
}

// 删除操作
const onDels = async (id: Number[]) => {
  if (id.length >= 1) {
    const datas: any = await API.sys_file_del(
      id
    )
    const { code, msg } = datas
    if (code === 200) {
      message.success(msg)
      formState.pageNum = calculatePageCount(listData.value.total, formState.pageSize, formState.pageNum, id.length);
      list()
      opendel.value = false
      multiple.value = true;
      selectedRows.value = []
    }
  } else {
    message.warning('请选择一条数据')
  }

}

// 查询详情
const onDetail = async (id: number) => {
  const datas: any = await API.sys_role_id(id);
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

  if (status === 'dow') {
    onDownload(id);
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

  if (item === 'PvwImg') {
    PvwImg.value = !PvwImg.value
  }
}

const callSubmit = async (formState: SysRole, name: string) => {
  console.log(name)
  if (name === '新增') {
    onAddUp(formState)
  } else {
    onUpdate(formState)
  }
}

//编辑
const onUpdate = async (formStates: SysRole) => {
  const datas: any = await API.sys_role_up(formStates);
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
const onAddUp = async (callformState: SysRole) => {
  const datas: any = await API.sys_role_sa(callformState)
  const { code, msg } = datas
  if (code === 200) {
    message.success(msg)
    list()
    AddUpClose();
  } else {
    message.error(msg)
  }
}

// 下载操作
const onDownload = async (ossId: number) => {
  download(ossId);
}


//检测表格所需内容状态
const selectDChange = (item: number[]) => {
  single.value = item.length != 1;
  multiple.value = !item.length;
}

const AddUpClose = () => {
  openAddUp.value = false
  multiple.value = true
  single.value = true
  DetailData.value = {}
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

//删除提示确认
const getUserDataById = (id) => {
  return listData.value.rows.find(user => user.ossId === id) || null;
}

//上传文件弹弹窗确认
const modalSubmit = () => {
  uploadModalOpen.value = false
  list();
}
//上传文件弹弹窗确认
const modalClose = async () => {
  if(fileList.value.length>0){
    let strs = [];
    for (let i in fileList.value) {
      strs.push(fileList.value[i].ossId);
    }
    onDels(strs)
  }
 
  uploadModalOpen.value = false
  // list();
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

.content {
  // height: calc(100% - 120px);
  max-height: calc(100vh - 100px);
  /* 调整这里的高度，使其适应你的布局 */
  // overflow-y: auto;
}
</style>