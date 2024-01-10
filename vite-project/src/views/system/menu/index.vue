<template>
  <div class="animate__animated animate__fadeInDown">
    <!-- 动态搜索框 -->
    <SearchComponent subTitle="搜索" v-model:search="formState" :xs="24" :sm="24" :md="12" :lg="9" :xl="6"
      :columns="searchColumns" @submit="handleChildSubmit" @reset="resetForm" />
  </div>
  <a-space class="animate__animated animate__fadeInDown">
    <a-button type="primary" @click="onAdd('add')" style="background-color: #40ce8c;" class="flex-row flex-align-center m-b3"
      v-hasPermi="['system:menu:add']">
      <template #icon>
        <Icon icon="ic:baseline-add" />
      </template>
      新增
    </a-button>
    <a-button class="flex-row flex-align-center m-b3" @click="handleToggleAllExpandInChild">
      <template #icon>
        <Icon :icon="!toggleTitle ? 'octicon:unfold-24' : 'octicon:fold-24'" />
      </template>
      {{ !toggleTitle ? '展开' : '收起' }}
    </a-button>
  </a-space>
  <a-skeleton :loading="loading">
    <div class="content animate__animated animate__fadeIn">
      <index_table ref="userIndexTable" :distList="distList" :data="ListData" :columns="columns" v-model="selectedRows"
        @query="onQuery" @state="userOnState" @toggleAllExpand="handleToggleAllExpand" :row-keys="currentExpandeds">
      </index_table>
    </div>
  </a-skeleton>

  <IndexAddUp :opens="openAddUp" :parentList="[{ 'id': 0, 'menuName': '主类目', 'children': ListData }]" :title="AddUpTitle"
    :data="DetailData" :distList="distList" @close="AddUpClose" @submit="callSubmit">
  </IndexAddUp>
</template>

<script setup lang="ts" name="menus">
import { reactive, ref, toRefs, onMounted, watchEffect, nextTick, createApp, watch } from 'vue'
import { Icon } from '@iconify/vue';
import type { UnwrapRef } from 'vue';
import { useUserDictData } from '@/hooks/dicts'
import index_table from './components/IndexTable.vue'
import IndexAddUp from './components/indexAddUp.vue'
import API from '@/api/index';
import { message } from 'ant-design-vue';
import type { TableColumnType } from 'ant-design-vue';
import { setupDynamicRoutes } from '@/router/modules/MainRouter'; //修改/新增 后重新加载路由信息
let selectedRows = ref([]);  //所选id
let { distList } = useUserDictData(['sys_normal_disable']);  //字典数据
//表格内容
const columns: TableColumnType[] = [
  {
    title: '菜单名称',
    dataIndex: 'menuName',
  },
  {
    title: '图标',
    dataIndex: 'icon',
    width: '6%',
  },
  {
    title: '显示顺序',
    dataIndex: 'orderNum',
    width: '6%',
  },

  {
    title: '权限标识',
    dataIndex: 'perms',
  },
  {
    title: '组件路径',
    dataIndex: 'component',
  },
  {
    title: '状态',
    dataIndex: 'status',
    width: '6%',

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
const loading = ref<boolean>(false);
onMounted(() => {
  List()
})

//展开组件
const userIndexTable = ref(null);
const toggleTitle = ref(false);
const handleToggleAllExpandInChild = () => {
  userIndexTable.value.toggleAllExpand()
}
// 展开的回调
const handleToggleAllExpand = (item) => {
  toggleTitle.value = item;
};
// 请求数据
let formState = reactive<UnwrapRef<SysMenu>>({
  menuName: null,
  status: null,
});

//输入框内容
const searchColumns = ref([
  {
    title: '菜单名称',
    dataType: 'input',
    dataTypename: 'string',
    name: 'menuName',
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
let open = ref<boolean>(false); //详情抽屉
let opendel = ref<boolean>(false); //详情抽屉
let DetailData = reactive<SysMenu>({}); //详情数据
let openAddUp = ref<boolean>(false); //详情抽屉
let AddUpTitle = ref<string>('新增')
let ListData = ref<Array<SysMenu>>([])  //表格内容
// 保存当前展开的节点
let currentExpanded = ref<Array<any>>([]);  //存储打开树形列表的下标
let currentExpandeds = ref<Array<any>>([]);  //传递打开树形列表的下标

//搜索表单内容
const handleChildSubmit = (submittedData) => {
  List()
};

//删除按键操作
const onDel = () => {
  opendel.value = true
}

//搜索框重置回调
const resetForm = () => {
  formState.createTime = null
  formState.updateTime = null
  List()
};


watch(
  () => distList.value, // 监听 distList.value 的变化防止数据未加载
  (newValue, oldValue) => {
    // 在 distList 变化时执行的逻辑
    searchColumns.value[1].options = distList.value.sys_normal_disable
  }
);


//事件存放
const List = async () => {
  loading.value = true;
  const data: any = await API.sys_menu_list(formState)
  ListData.value = data.data
  loading.value = false;
}
// 删除操作
const onDels = async (id: Number[]) => {
  console.log(selectedRows.value)
  if (id.length >= 1) {
    const datas: any = await API.sys_menu_del(
      id
    )
    const { code, msg } = datas;
    if (code === 200) {
      message.success(msg)

      List()
      await setupDynamicRoutes()
      // 在重新加载数据后，将之前展开的节点恢复
      currentExpandeds = currentExpanded;
      selectedRows.value = []
    } else {
      message.error(msg)
    }
  } else {
    message.warning('请选择一条数据')
  }

}
// 查询详情
const onDetail = async (id: number) => {
  const datas: any = await API.sys_menu_id(id)
  const { code, data, msg } = datas;
  if (code === 200) {
    DetailData = data
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
//查询详情
const onQuery = async (id: number, status: string, expandedRowKeys) => {
  currentExpanded.value = expandedRowKeys
  if (status === 'up') {
    await onDetail(id)
    openAddUp.value = true
    AddUpTitle.value = '编辑'
  } else if (status === 'add') {
    DetailData = {
      menuType: 'M',
      parentId: id,
      isFrame: 1,
      isCache: 0,
      status: "0",
      visible: 0,
    }
    AddUpTitle.value = '新增'
    openAddUp.value = true
  } else if (status === 'del') {
    onDels([id])
  } else {

  }

}

//新增或者编辑
const onAdd = async (item: string) => {
  DetailData = {}
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

const callSubmit = async (formState: SysMenu, name: string) => {
  console.log(name)
  if (name === '新增') {
    onAddUp(formState)
  } else {
    onUpdate(formState)
  }
}

//编辑
const onUpdate = async (formStates: any) => {
  const datas: any = await API.sys_menu_up(formStates)
  const { code, msg } = datas
  if (code === 200) {
    message.success(msg)

    List()
    await setupDynamicRoutes()
    // 在重新加载数据后，将之前展开的节点恢复
    currentExpandeds.value = currentExpanded.value;
    openAddUp.value = false
    selectedRows.value = []
  } else {
    message.error(msg)
  }
}

// 新增操作
const onAddUp = async (callformState: SysMenu) => {
  const datas: any = await API.sys_menu_sa(callformState)
  const { code, msg } = datas
  if (code === 200) {
    message.success(msg)
    List()
    await setupDynamicRoutes()
    // 在重新加载数据后，将之前展开的节点恢复
    currentExpandeds.value = currentExpanded.value;
    openAddUp.value = false
    selectedRows.value = []
  } else {
    message.error(msg)
  }
}
const AddUpClose = () => {
  openAddUp.value = false
  DetailData = {}
  selectedRows.value = []
}

const userOnState = (state: string, id: number) => {
  const data: any = ref({ status: state, id: id })
  onUpdate(data)
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