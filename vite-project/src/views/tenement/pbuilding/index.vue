<template>
  <div class="animate__animated animate__fadeInDown">
    <!-- 动态搜索框 -->
    <SearchComponent subTitle="搜索" :search="formState" :xs="24" :sm="24" :md="12" :lg="9" :xl="5" :columns="searchColumns"
      @submit="handleChildSubmit" @reset="resetForm" />
  </div>

    <a-space  class="animate__animated animate__fadeInDown">
      <a-button type="primary" @click="onAdd('add')" style="background-color: #40ce8c;" v-hasPermi="['tenement:pbuilding:add']"
        class="flex-row flex-align-center m-b3">
        <template #icon>
          <Icon icon="ic:baseline-add" />
        </template>
        新增
      </a-button>
      <a-button type="primary" @click="onAdd('up')" style="background-color: #ffb700;" :disabled="single"
        v-hasPermi="['tenement:pbuilding:edit']" class="flex-row flex-align-center m-b3">
        <template #icon>
          <Icon icon="uil:edit" />
        </template>
        编辑
      </a-button>
      <a-button type="primary" danger class="flex-row flex-align-center m-b3" @click="onDel" :disabled="multiple"
        v-hasPermi="['tenement:pbuilding:remove']">
        <template #icon>
          <Icon icon="ic:outline-delete" />
        </template>
        删除
      </a-button>
    </a-space>


  <div class="content animate__animated animate__fadeIn">
    <index_table :distList="distList" :data="listData?.rows" :columns="columns" v-model="selectedRows"
      @update:model-value="selectDChange" @query="onQuery" @state="userOnState">
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
      {{ getUserDataById(id).dictName }}
    </p>
  </a-modal>
</template>

<script setup lang="ts" name="pubilding">
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
let { distList } = useUserDictData(['sys_normal_disable']);  //字典数据
//表格内容
const columns: TableColumnType[] = [
  {
    title: '编号',
    dataIndex: 'buildingId',
  },
  
  {
    title: '楼栋编号',
    dataIndex: 'buildingNumber',
  },
  {
    title: '小区名称',
    dataIndex: 'propertyName',
  },
  // {
  //   title: '状态',
  //   dataIndex: 'status',
  // },
  // {
  //   title: '备注',
  //   dataIndex: 'remark',
  // },
  // {
  //   title: '创建时间',
  //   dataIndex: 'createTime',
  // },

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
  dictList()
})

// 请求数据
let formState: UnwrapRef<PBuilding> = reactive({
  propertyId:1,
  pageNum: 1,
  pageSize: 20,
});

//搜索输入框内容
const searchColumns = ref([
  // {
  //   title: '名称',
  //   dataType: 'input',
  //   dataTypename: 'string',
  //   name: 'dictName',
  // },
  {
    title: '小区名称',
    dataType: 'select',
    keyField: 'value',
    labelField: 'label',
    name: 'propertyId',
    defaultValue: 1,
    options: [],
  },
  {
    title: '楼栋编号',
    dataType: 'input',
    dataTypename: 'string',
    name: 'buildingNumber',
  },
  // {
  //   title: '状态',
  //   dataType: 'select',
  //   keyField: 'dictValue',
  //   labelField: 'dictLabel',
  //   name: 'status',
  //   options: [],
  // },
  // {
  //   title: '时间',
  //   dataType: 'rangePicker',
  //   name: 'params',
  // },
])


//添加表单输入框内容
const FormColumns = ref<Array<FormColumns>>([
  {
    title: '小区选择',
    dataType: 'select',
    keyField: 'value',
    labelField: 'label',
    name: 'propertyId',
    defaultValue: 1,
    options: [],
    rules: [{ required: true, message: '请选择小区名称', trigger: 'change' },]
  },
  {
    title: '楼栋编号',
    dataType: 'input',
    dataTypename: 'text',
    name: 'buildingNumber',
    rules: [{ required: true, message: '请输入角色名称', trigger: 'change' },]
  },
  // {
  //   title: '类型',
  //   dataType: 'input',
  //   dataTypename: 'text',
  //   name: 'dictType',
  //   rules: [{ required: true, message: '请输入字典类型', trigger: 'change' },]
  // },
  // {
  //   title: '状态',
  //   dataType: 'select',
  //   keyField: 'dictValue',
  //   labelField: 'dictLabel',
  //   name: 'status',
  //   defaultValue: "0",
  //   options: [],
  // },
  // {
  //   title: '备注',
  //   dataType: 'textarea',
  //   dataTypename: 'string',
  //   name: 'remark',
  // },
])

let open = ref<boolean>(false); //详情抽屉
let opendel = ref<boolean>(false); //详情抽屉
let DetailData = ref<PBuilding>({}); //详情数据
let openAddUp = ref<boolean>(false); //新增、修改抽屉
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
  // formState.createTime = null
  // formState.updateTime = null
  // debugger
  list()
  searchColumns.value[0].defaultValue = searchColumns.value[0].options[0].value
};

watch(
  () => distList.value, // 监听 distList.value 的变化防止数据未加载
  (newValue, oldValue) => {
    // 在 distList 变化时执行的逻辑
    // searchColumns.value[2].options = distList.value.sys_normal_disable
    // FormColumns.value[2].options = distList.value.sys_normal_disable
  }
);


//事件存放
const list = async () => {
  // 创建一个新的对象，排除 dateRange 字段
  const formStates = { ...formState }
  // if (formStates.params != null && formStates.params.length > 1) { formStates.params = formStates.params.join(','); }

  const { data } = await API.tmt_pbuilding_list(formStates)
  listData.value = data
}

// 删除操作
const onDels = async (id: Number[]) => {
  if (id.length >= 1) {
    const datas: any = await API.tmt_pbuilding_del(
      id
    )
    const { code, msg } = datas
    if (code === 200) {
      message.success(msg)
      formState.pageNum = calculatePageCount(listData.value.total, formState.pageSize, formState.pageNum, id.length);
      await list()
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
  const datas: any = await API.tmt_pbuilding_id(id);
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

// 查询小区详情
const dictList = async () => {
  const datas: any = await API.sys_pproperty_dict_id();
  const { code, data, msg } = datas;
  if (code === 200) {
    searchColumns.value[0].defaultValue = data[0].value
    searchColumns.value[0].options = data

    FormColumns.value[0].defaultValue = data[0].value
    FormColumns.value[0].options = data
    formState.propertyId=data[0].value
    list()
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

//表单提交的回调
const callSubmit = async (formState: PBuilding, name: string) => {
  console.log(name)
  if(!formState.propertyName){
    formState.propertyName=FormColumns.value[0].options.find(item=> item.value === formState.propertyId).label
  }
  if (name === '新增') {
    onAddUp(formState)
  } else {
    onUpdate(formState)
  }
}

//编辑
const onUpdate = async (formStates: PBuilding) => {
  const datas: any = await API.tmt_pbuilding_up(formStates);
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
const onAddUp = async (callformState: PBuilding) => {
  const datas: any = await API.tmt_pbuilding_sa(callformState)
  const { code, msg } = datas
  if (code === 200) {
    message.success(msg)
    list()
    AddUpClose();
  } else {
    message.error(msg)
  }
}

//检测表格所需内容状态
const selectDChange = (item: number[]) => {
  single.value = item.length != 1;
  multiple.value = !item.length;
}

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


const onJumperChange = (pageNumber: number) => {
  formState.pageNum = pageNumber
  list()
};

const onShowSizeChange = (current: number, pageSize: number) => {
  formState.pageSize = pageSize;
  list()
};

const getUserDataById = (id) => {
  return listData.value.rows.find(user => user.dictId === id) || null;
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