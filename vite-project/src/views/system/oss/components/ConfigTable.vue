<template>
  <a-table :row-selection="rowSelection" :columns="columns" :scroll="{x: 1500 ,y: height - 450}" class="custom-table"
    :data-source="data" rowKey="ossConfigId" :pagination="false">
    <template #bodyCell="{ column, text, record,index }">
      <template v-if="column.dataIndex === 'dictType'">
        <a @click="toDictData(record.dictType)">{{ text }}</a>
      </template>
      <template v-if="column.dataIndex === 'accessPolicy'">
        <a-tag :color="text == 0 ? 'orange': text == 1 ?'success' : 'cyan'">{{ text == 0 ? 'private': text == 1 ?'public' : 'custom' }}</a-tag>
      </template>
      <template v-if="column.dataIndex === 'status'">
        <a-popconfirm :title="`你确定要${record.status === '0' ? '停用' : '启用'} ${record.configKey} 吗`" ok-text="确定"
          cancel-text="取消" @confirm="onConfirm(record,index)" @cancel="cancel(index)">
          <a-switch v-model:checked="record.status" :checkedValue="'0'" :unCheckedValue="'1'"
            checked-children="开" un-checked-children="关" />
        </a-popconfirm>
      </template>
      <template v-if="column.dataIndex === 'operation' && record.roleName != '超级管理员'">
        <a-button type="link" @click="onQuery(record.ossConfigId, 'up')" v-hasPermi="['system:dict:edit']">
          <template #icon>
            <Icon icon="uil:edit" style="vertical-align: middle;" />
          </template>
          编辑
        </a-button>
        <a-popconfirm title="你确定要删除该条数据吗" ok-text="确定" cancel-text="取消" @confirm="onQuery(record.ossConfigId, 'del')" >
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
import { ref, computed, unref } from 'vue';
import type { TableProps, TableColumnType } from 'ant-design-vue';
import { useWindowSize } from '@vueuse/core';
import API from '@/api/index';
import { message } from 'ant-design-vue';

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
const onConfirm = async (record,i) => {
  const formData={
    ossConfigId: record.ossConfigId,
    status: record.status,
    configKey:record.configKey
  }
  const data:any =await API.sys_file_config_up_status(formData)
  // emit('state', status, id);
  const {code, msg } =data
  if (code === 200) {
    message.success(msg)
    emit('state');
  } else {
    props.data[i].status = props.data[i].status==0?'1':'0';
    message.error(msg)
  }
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