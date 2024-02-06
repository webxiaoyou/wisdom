<template>
  <!--如需要多选请添加 :row-selection="rowSelection"  -->
  <a-table :columns="columns" :scroll="{ x: 1500, y: height - 450 }" class="custom-table" :data-source="data"
    rowKey="unitId" :pagination="false">
    <template #bodyCell="{ column, text, record }">
      <template v-if="column.dataIndex === 'floorNumber'">
        <a @click="onQuery(record.unitId)">{{ text }}</a>
      </template>
      <template v-if="column.dataIndex === 'imageUrl'">
        <a-image :width="60" :src="text" />
      </template>
      <template v-if="column.dataIndex === 'sex'">
        {{ getDictLabel(distList, 'sys_user_sex', text) }}
      </template>

      <template v-if="column.dataIndex === 'authenticationStatus'">
        <a-tag :color="text == 0 ? 'warning' : text == 1 ? 'success' : text == 3 ? 'warning': 'error'">{{ getDictLabel(distList, 'audit_status',
          text) }}</a-tag>
      </template>
      <!-- <template v-if="column.dataIndex === 'state'">
        <a-popconfirm :title="`你确定要${record.status === '0' ? '停用' : '启用'} ${record.roleName} 用户吗`" ok-text="确定"
          cancel-text="取消" @confirm="onConfirm(record.status, record.postId)" @cancel="cancel(index)">
          <a-switch v-model:checked="record.status" :checkedValue="'0'" :unCheckedValue="'1'"
            checked-children="开" un-checked-children="关" />
        </a-popconfirm>
      </template> -->
      <template v-if="column.dataIndex === 'operation' && record.roleName != '超级管理员'">
        <!-- <a-button type="link" @click="onQuery(record.unitId, 'up')" v-hasPermi="['system:post:edit']">
          <template #icon>
            <Icon icon="uil:edit" style="vertical-align: middle;" />
          </template>
          编辑
        </a-button> -->
        <a-button type="link" @click="onUpSt(record.unitId, 'upSu', 1)" v-hasPermi="['tenement:pproperty_unit:edit']" v-if="record.authenticationStatus!=1">
          <template #icon>
            <Icon icon="uil:edit" style="vertical-align: middle;" />
          </template>
          通过
        </a-button>
        <a-popover title="理由"  v-hasPermi="['tenement:pproperty_unit:edit']" trigger="click" v-if="record.authenticationStatus!=1">
          <template #content>
            <a-textarea v-model:value="remark" placeholder="请输入不通过的理由" :rows="4" />
            <a-button type="link" v-hasPermi="['tenement:pproperty_unit:edit']" @click="onUpSt(record.unitId, 'upEr', 2)">
            确定
          </a-button>
          </template>
          <a-button type="link" v-hasPermi="['tenement:pproperty_unit:edit']">
            <template #icon>
              <Icon icon="uil:edit" style="vertical-align: middle;" />
            </template>
            不通过
          </a-button>
        </a-popover>
        <a-popconfirm title="你确定要删除该条数据吗" ok-text="确定" cancel-text="取消" @confirm="onQuery(record.unitId, 'del')">
          <a-button type="link" v-hasPermi="['tenement:pproperty_unit:remove']">
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
import  { message } from 'ant-design-vue';
import { getDictLabel } from '@/hooks/publicFunction'; //图片
import { useWindowSize } from '@vueuse/core'
const { height } = useWindowSize()
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
const remark = ref<string>('');
const emit = defineEmits(['click', 'close', 'update:modelValue', 'query', 'state']);

// 编辑状态确认回调
const onConfirm = (status: string, id: number) => {
  emit('state', status, id);
}
// 取消回调
const cancel = (i) => {
  props.data[i].status = props.data[i].status == 0 ? '1' : '0';
};
const onUpSt=(id: number, status: string, statu: string | number)=>{
  if(status ==='upEr' && remark.value === '' ){
    message.error('请输入不通过的理由');
    return;
  }

  onQuery(id, 'upSt', statu);
}
const onQuery = (id: number, status: string, statu: string | number) => {
  emit('query', id, status, statu,remark.value);
  remark.value = '';
}
//所选内容
const onSelectChange = (changableRowKeys: Number[]) => {
  emit('update:modelValue', changableRowKeys);
};
const rowSelection = <TableProps['rowSelection']>computed(() => {
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
  
<style lang="less" scoped></style>