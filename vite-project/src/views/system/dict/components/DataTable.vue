<template>
  <a-table :row-selection="rowSelection" :columns="columns" :scroll="{ x: 1500 , y: height - 450}" class="custom-table"
    :data-source="data" rowKey="dictCode" :pagination="false">
    <template #bodyCell="{ column, text, record,index }">
      <template v-if="column.dataIndex === 'dictType'">
        <a @click="toDictData(record.dictType)">{{ text }}</a>
      </template>
      <template v-if="column.dataIndex === 'status'">
        <a-tag :color="text == 0 ? 'success' : 'error'">{{ text == 0 ? '正常' : '停用' }}</a-tag>
      </template>
      <!-- <template v-if="column.dataIndex === 'state'">
        <a-popconfirm :title="`你确定要${record.status === '0' ? '停用' : '启用'} ${record.dictName} 吗`" ok-text="确定"
          cancel-text="取消" @confirm="onConfirm(record.status, record.id)" @cancel="cancel(index)">
          <a-switch v-model:checked="record.status" :checkedValue="'0'" :unCheckedValue="'1'"
            checked-children="开" un-checked-children="关" />
        </a-popconfirm>
      </template> -->
      <template v-if="column.dataIndex === 'operation' && record.roleName != '超级管理员'">
        <a-button type="link" @click="onQuery(record.dictCode, 'up')" v-hasPermi="['system:dict:edit']">
          <template #icon>
            <Icon icon="uil:edit" style="vertical-align: middle;" />
          </template>
          编辑
        </a-button>
        <a-popconfirm title="你确定要删除该条数据吗" ok-text="确定" cancel-text="取消" @confirm="onQuery(record.dictCode, 'del')" >
          <a-button type="link" v-hasPermi="['system:dict:remove']">
            <template #icon>
              <Icon icon="ic:outline-delete" style="vertical-align: middle;" />
            </template>
            删除
          </a-button>
        </a-popconfirm>
      </template>
    </template>
  </a-table>
</template>
  
<script setup lang="ts">
import { h } from 'vue';
import * as Icons from '@ant-design/icons-vue';
import { Icon } from '@iconify/vue';
import { ref, computed, unref } from 'vue'
import type { TableProps, TableColumnType } from 'ant-design-vue';
import { useWindowSize } from '@vueuse/core'
const {  height } = useWindowSize()
const props = defineProps({
  distList: {
    type: Object,
    default: () => { },
  },
  data: {
    type: Array<any>,
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

});

const emit = defineEmits(['click', 'close', 'update:modelValue', 'query', 'state']);

// 编辑状态确认回调
const onConfirm = (status: string, id: number) => {
  emit('state', status, id);
}

// 取消回调
const cancel = (i) => {
  props.data[i].status = props.data[i].status==0?'1':'0';
};

//跳转字典数据页
const toDictData = (type: string) => {
  
}

const onQuery = (id: number, status: string) => {
  emit('query', id, status);
}
//所选内容
const onSelectChange = (changableRowKeys: Number[]) => {
  emit('update:modelValue', changableRowKeys);
};
const rowSelection = <TableProps['rowSelection']> computed(() => {
  return {
    selectedRowKeys: unref(props.modelValue),
    onChange: onSelectChange,
    getCheckboxProps: (record) => ({
      disabled: record.name === '超级管理员', // Column configuration not to be checked
      name: record.name,
    }),
  };
});



</script>
  
<style lang="less" scoped>
</style>