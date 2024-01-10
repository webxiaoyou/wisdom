<template>
  <a-table :columns="columns" :scroll="{ x: 1500 , y: height - 340 }" class="custom-table" :data-source="data"
    rowKey="id" :pagination="false" expandRowByClick :expanded-row-keys="expandedRowKeys" @expand="handleRowClick">
    <template #bodyCell="{ column, text, record }">
      <template v-if="column.dataIndex === 'status'">
        <a-tag :color="text == 0 ? 'success' : 'error'">{{ text == 0 ? '正常' : '停用' }}</a-tag>
      </template>
      <template v-if="column.dataIndex === 'icon'">
        <component :is="icons[record.icon]" />
      </template>
      <template v-if="column.dataIndex === 'operation'">
        <a-button type="link" @click.stop="onQuery(record.id, 'up')" v-hasPermi="['system:menu:edit']">
          <template #icon>
            <Icon icon="uil:edit" style="vertical-align: middle;" />
          </template>
          编辑
        </a-button>
        <a-popconfirm title="你确定要删除该条数据吗" ok-text="确定" cancel-text="取消" @confirm="onQuery(record.id, 'del')" >
          <a-button type="link" v-hasPermi="['system:menu:remove']">
            <template #icon>
              <Icon icon="ic:outline-delete" style="vertical-align: middle;" />
            </template>
            删除
          </a-button>
        </a-popconfirm>
        <a-button type="link" @click.stop="onQuery(record.id, 'add')" v-hasPermi="['system:menu:add']">
          <template #icon>
            <Icon icon="ic:baseline-add" style="vertical-align: middle;" />
          </template>
          新增
        </a-button>
      </template>
    </template>
  </a-table>
</template>
  
<script setup lang="ts">
  import * as icons from '@ant-design/icons-vue/es/icons';
import { Icon } from '@iconify/vue';
import { ref, computed } from 'vue'
import { useWindowSize } from '@vueuse/core'
const { height } = useWindowSize()
const props = defineProps({
  distList: {
    type: Object,
    default: () => { },
  },
  data: {
    type: Array,
    default: () => [],
  },
  columns: {
    type: Array,
    default: () => [],
  },
  modelValue: {
    type: Array,
    default: () => [],
  },
  rowKeys: {
    type: Array,
    default: () => [],
  },
});

const emit = defineEmits(['click', 'close', 'update:modelValue', 'query', 'state', 'toggleAllExpand']);

const onQuery = (id: number, status: string, event) => {
  emit('query', id, status,expandedRowKeys.value);
}




const expandedRowKeys = ref([]);  // 手动控制展开和折叠的节点
const isAllExpanded = ref(false);  // 标识是否全部展开

const toggleAllExpand = () => {
  isAllExpanded.value = !isAllExpanded.value;
  emit('toggleAllExpand', isAllExpanded.value); // 传递保存的值
  if (isAllExpanded.value) {
    // 设置展开的节点为所有节点的 rowKey
    expandedRowKeys.value = getAllNodeIds(props.data);
  } else {
    expandedRowKeys.value = [];
  }
};
defineExpose({ toggleAllExpand });

const handleRowClick = (expanded, record) => {
  const index = expandedRowKeys.value.indexOf(record.id);
  if (index === -1) {
    // 点击某一项时展开
    expandedRowKeys.value.push(record.id);
  } else {
    // 点击某一项时收缩
    expandedRowKeys.value.splice(index, 1);
    // 同时关闭子节点
    expandedRowKeys.value = expandedRowKeys.value.filter((id) => !id.startsWith(`${record.id}-`));
  }
  // 如果当前项在展开全部状态下被点击，取消展开全部状态
  isAllExpanded.value = false;
};

// 获取所有节点的 id，包括子节点
const getAllNodeIds = (data) => {
  const result = [];
  data.forEach((item) => {
    result.push(item.id);
    if (item.children && item.children.length > 0) {
      result.push(...getAllNodeIds(item.children).map((childId) => `${childId}`));
    }
  });
  return result;
};

watchEffect(
  ()  => {
    // 在 rowKeys 变化时执行的逻辑
  expandedRowKeys.value = props.rowKeys;
  }
);
</script>
  
<style lang="less" scoped></style>