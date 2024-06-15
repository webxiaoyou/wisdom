<template>
  <a-table :columns="columns" :scroll="{ x: 1500 , y: height - 320}" class="custom-table"
    :data-source="data" rowKey="repairId" :pagination="false">
    <template #bodyCell="{ column, text, record,index }">

      <template v-if="column.dataIndex === 'status'">
        <a-tag :color="text == 0 ? 'processing':text == 1 ?'warning':'success'">{{ text == 0 ? '待处理' :text == 1 ?'处理中' :'已完成' }}</a-tag>
      </template>
      <template v-if="column.dataIndex === 'propertyId'">
       {{getSingleDictLabel(props.distList, 'pproperty_dict', text)}}
      </template>
      <template v-if="column.dataIndex === 'buildingId'">
        <a @click="onQuery(record.repairId)">{{getSingleDictLabel(props.distList, 'pbuilding_dict', text)}}</a>
      </template>
      <template v-if="column.dataIndex === 'floorId'">
       {{getSingleDictLabel(props.distList, 'pfloorId_dict', text)}}
      </template>
      <template v-if="column.dataIndex === 'userId'">
       {{getSingleDictLabel(props.distList, 'unit_dict', text)}}
      </template>
      
      <!-- <template v-if="column.dataIndex === 'state'">
        <a-popconfirm :title="`你确定要${record.status === '0' ? '停用' : '启用'} ${record.roleName} 用户吗`" ok-text="确定"
          cancel-text="取消" @confirm="onConfirm(record.status, record.postId)" @cancel="cancel(index)">
          <a-switch v-model:checked="record.status" :checkedValue="'0'" :unCheckedValue="'1'"
            checked-children="开" un-checked-children="关" />
        </a-popconfirm>
      </template> -->
      <template v-if="column.dataIndex === 'operation'">
        <!-- <a-button type="link" @click="onQuery(record.postId, 'up')" v-hasPermi="['tenement:prepair::edit']">
          <template #icon>
            <Icon icon="uil:edit" style="vertical-align: middle;" />
          </template>
          编辑
        </a-button> -->
        <a-button type="link" style="color: #e78708;" @click="onQuery(record.repairId, 'allot')" v-hasPermi="['tenement:prepair:edit']" v-if="record.status !== '2' && userStore?.userInfo?.position==='董事长' || record.status !== '2' && userStore?.userInfo?.position==='ceo' || record.status !== '2' &&  userStore?.userInfo?.position==='se' || record.status !== '2' &&  userStore?.userInfo?.position==='user'">
          <template #icon>
            <Icon icon="uil:edit" style="vertical-align: middle;" />
          </template>
          {{ record.status === '0' ?'分配':'修改分配' }}
        </a-button>
        <a-popover title="遇见的问题"   v-hasPermi="['tenement:prepair:edit']" trigger="click" v-if="record.status === '1'">
          <template #content>
            <a-textarea v-model:value="remark" placeholder="请输入遇见的问题" :rows="4" />
            <a-button type="link" v-hasPermi="['tenement:prepair:edit']" @click="onUpSt(record.repairId, 'upEr', 2)">
            确定
          </a-button> 
          </template>
          <a-button type="link" v-hasPermi="['tenement:prepair:edit']">
            <template #icon>
              <Icon icon="uil:edit" style="vertical-align: middle;" />
            </template>
            有问题？
          </a-button>
        </a-popover>
        <a-button type="link" style="color: #30be14;" @click="onQuery(record.repairId, 'succ')" v-hasPermi="['tenement:prepair:edit']" v-if="record.status === '1'">
          <template #icon>
            <Icon icon="uil:edit" style="vertical-align: middle;" />
          </template>
          完成
        </a-button>
        <a-popconfirm title="你确定要删除该条数据吗" ok-text="确定" cancel-text="取消" @confirm="onQuery(record.repairId, 'del')" v-if="userStore?.userInfo?.position==='董事长' || userStore?.userInfo?.position==='ceo' ||  userStore?.userInfo?.position==='se'">
          <a-button type="link" v-hasPermi="['tenement:prepair:remove']">
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
import { useUserStore } from '@/store/modules/user';
  import { getAssetsImages, getDictLabel,getSingleDictLabel } from '@/hooks/publicFunction'; // 导入必要的函数
import { h } from 'vue';
import * as Icons from '@ant-design/icons-vue';
import { Icon } from '@iconify/vue';
import { ref, computed, unref } from 'vue'
import  { message } from 'ant-design-vue';
import type { TableProps, TableColumnType } from 'ant-design-vue';
import { useWindowSize } from '@vueuse/core'
const {  height } = useWindowSize()
const userStore:any = useUserStore();
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
  props.data[i].status = props.data[i].status==0?'1':'0';
};
const onUpSt=(id: number, status: string, statu: string | number)=>{
  if(status ==='upEr' && remark.value === '' ){
    message.error('请输入不通过的理由');
    return;
  }
  onQuery(id, 'upSt');
}
const onQuery = (id: number, status: string) => {
  emit('query', id, status,remark.value);
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