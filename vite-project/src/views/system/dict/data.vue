<template>
  <div class="animate__animated animate__fadeInDown">
    <!-- 动态搜索框 -->
    <SearchComponent subTitle="搜索" :search="formState" :xs="24" :sm="24" :md="12" :lg="9" :xl="5" :columns="searchColumns"
      @submit="handleChildSubmit" @reset="resetForm" />
  </div>

  <a-space class="animate__animated animate__fadeInDown">
    <a-button type="primary" @click="onAdd('add')" style="background-color: #40ce8c;" class="flex-row flex-align-center m-b3"
      v-hasPermi="['system:dict:add']">
      <template #icon>
        <Icon icon="ic:baseline-add" />
      </template>
      新增
    </a-button>
    <a-button type="primary" @click="onAdd('up')" :disabled="single" style="background-color: #ffb700;"
      class="flex-row flex-align-center m-b3" v-hasPermi="['system:dict:edit']">
      <template #icon>
        <Icon icon="uil:edit" />
      </template>
      编辑
    </a-button>
    <a-button type="primary" danger class="flex-row flex-align-center m-b3" :disabled="multiple" @click="onDel"
      v-hasPermi="['system:dict:remove']">
      <template #icon>
        <Icon icon="ic:outline-delete" />
      </template>
      删除
    </a-button>
    <a-button type="primary" danger class="flex-row flex-align-center m-b3" @click=" { router.go(-1); }"
      style="background-color: #3ca7d1;">
      <template #icon>
        <Icon icon="icon-park-outline:return" />
      </template>
      关闭
    </a-button>
  </a-space>


  <div class="content animate__animated animate__fadeIn">
    <data_table :distList="distList" :data="listData?.rows" :columns="columns" v-model="selectedRows" @query="onQuery"
      @state="userOnState"  @update:model-value="selectDChange">
    </data_table>
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
      {{ getUserDataById(id).dictLabel }}
    </p>
  </a-modal>
</template>

<script setup lang="ts" name="dict-data">
import { reactive, ref, toRefs, onMounted, watchEffect, nextTick, createApp, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router';
import { Icon } from '@iconify/vue';
import type { UnwrapRef } from 'vue';
import { useUserDictData } from '@/hooks/dicts';
import data_table from './components/DataTable.vue';
import API from '@/api/index';
import { message } from 'ant-design-vue';
import dayjs from 'dayjs';
import { convertToNumber, calculatePageCount } from '@/hooks/publicFunction';
import type { TableColumnType } from 'ant-design-vue';
let selectedRows = ref([]);  //所选id
let { distList } = useUserDictData(['sys_normal_disable']);  //字典数据
const route = useRoute();
const router = useRouter();

//表格内容
const columns: TableColumnType[] = [
  {
    title: '编号',
    dataIndex: 'dictCode',
  },
  {
    title: '标签',
    dataIndex: 'dictLabel',
  },
  {
    title: '键值',
    dataIndex: 'dictValue',
  },
  {
    title: '排序',
    dataIndex: 'dictSort',
  },
  {
    title: '状态',
    dataIndex: 'states',
  },
  {
    title: '备注',
    dataIndex: 'remark',
  },
  {
    title: '创建时间',
    dataIndex: 'createTime',
  },

  {
    title: '操作',
    dataIndex: 'operation',
  },

];
onMounted(() => {
  list();
  Typelist();
})

// 请求数据
let formState: UnwrapRef<DictData> = reactive({
  dictLabel: '',   //名称
  dictType: route.params.dictType as string, // 字典类型
  status: null,
  createTime: null,
  updateTime: null,
  pageNum: 1,
  pageSize: 20,
  params: null
});

//搜索输入框内容
const searchColumns = ref([

  {
    title: '名称',
    dataType: 'select',
    keyField: 'dictType',
    labelField: 'dictName',
    name: 'dictType',
    options: [],
  },
  {
    title: '标签',
    dataType: 'input',
    dataTypename: 'string',
    name: 'dictLabel',
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


//添加表单输入框内容
const FormColumns = ref<Array<FormColumns>>([
  {
    title: '类型',
    dataType: 'input',
    dataTypename: 'text',
    name: 'dictType',
    disabled: true,
    defaultValue: route.params.dictType as string,
    rules: [{ required: true, message: '请输入数据类型', trigger: 'change' },]
  },
  {
    title: '标签',
    dataType: 'input',
    dataTypename: 'text',
    name: 'dictLabel',
    rules: [{ required: true, message: '请输入数据标签', trigger: 'change' },]
  },
  {
    title: '键值',
    dataType: 'input',
    dataTypename: 'text',
    name: 'dictValue',
    rules: [{ required: true, message: '请输入数据键值', trigger: 'change' },]
  },
  {
    title: '排序',
    dataType: 'inputNumber',
    dataTypename: 'text',
    name: 'dictSort',
    defaultValue: 1,
    rules: [{ required: true, message: '数据顺序不能为空', trigger: 'change' },]
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
    title: '备注',
    dataType: 'textarea',
    dataTypename: 'string',
    name: 'remark',
  },
])

let open = ref<boolean>(false); //详情抽屉
let opendel = ref<boolean>(false); //详情抽屉
let DetailData = ref<DictData>({}); //详情数据
let openAddUp = ref<boolean>(false); //详情抽屉
let AddUpTitle = ref('新增');
let listData = ref<any>({});  //表单内容
const pageSizeOptions = ref<string[]>(['10', '20', '40', '100', '200']); // 表格分页
// 非单个禁用
const single = ref(true)
// 非多个禁用
const multiple = ref(true)

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
    searchColumns.value[2].options = distList.value.sys_normal_disable
    FormColumns.value[4].options = distList.value.sys_normal_disable
  }
);
watch(() => formState.dictType, (newValue, oldValue) => {
  FormColumns.value[0].defaultValue = newValue
})
//事件存放
const Typelist = async () => {
  // 创建一个新的对象，排除 dateRange 字段
  const requestData = {
    pageNum: 1,
    pageSize: 999
  };
  const data: any = await API.sys_dict_type_list(requestData)
  searchColumns.value[0].options = data.data.rows
}
//事件存放
const list = async () => {
  // 创建一个新的对象，排除 dateRange 字段
  const requestData = { ...formState, dateRange: undefined };
  const { data } = await API.sys_dict_data_list(requestData)
  listData.value = data
}

// 删除操作
const onDels = async (id: Number[]) => {
  if (id.length >= 1) {
    const datas: any = await API.sys_dict_data_del(
      id
    )
    const { code, msg } = datas
    if (code === 200) {
      message.success(msg)
      formState.pageNum = calculatePageCount(listData.value.total, formState.pageSize, formState.pageNum, id.length);
      await list()
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
  const datas: any = await API.sys_dict_data_info(id);
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

const callSubmit = async (formState: DictData, name: string) => {
  console.log(name)
  if (name === '新增') {
    onAddUp(formState)
  } else {
    onUpdate(formState)
  }
}

//编辑
const onUpdate = async (formStates: DictData) => {
  const datas: any = await API.sys_dict_data_up(formStates);
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
const onAddUp = async (callformState: DictData) => {
  const datas: any = await API.sys_dict_data_sa(callformState)
  const { code, msg } = datas
  if (code === 200) {
    message.success(msg)
    list()
    AddUpClose();
  } else {
    message.error(msg)
  }
}


// 详情抽屉回调
const rowDrawerClose = () => {
  DetailData.value = {}
  open.value = false
  selectedRows.value = []
};

const AddUpClose = () => {
  openAddUp.value = false
  multiple.value=true
  single.value=true
  DetailData.value = {}
  selectedRows.value = []
  AddUpTitle.value = ''
}

const userOnState = (state: string, id: string) => {
  let data: any = ref({ status: state, id: id })
  onUpdate(data.value)
}
//检测表格所需内容状态
const selectDChange = (item: number[]) => {
  single.value = item.length != 1;
  multiple.value = !item.length;
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
  return listData.value.rows.find(user => user.dictCode === id) || null;
}

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