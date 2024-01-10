<template>
  <a-table :row-selection="rowSelection" :columns="columns" :scroll="{ x: 1500, y: height - 450 }" class="custom-table"
    :data-source="data" rowKey="id" :pagination="false">
    <template #bodyCell="{ column, text, record, index }">
      <template v-if="column.dataIndex === 'account'">
        <a @click="onQuery(record.id)">{{ text }}</a>
      </template>
      <template v-if="column.dataIndex === 'position'">
        <a-select placeholder="岗位" v-model:value="record.position" :open="false" :showArrow="false" :bordered="false">
          <a-select-option :value="item.postCode" v-for="item in distList?.sys_post_list" :key="item.id">{{
            item.postName }}</a-select-option>
        </a-select>
      </template>

      <template v-if="column.dataIndex === 'state' && record.name != '超级管理员'">
        <a-popconfirm :title="`你确定要${record.status === '0' ? '停用' : '启用'}  ${record.name} 用户吗`" ok-text="确定"
          cancel-text="取消" @confirm="onConfirm(record.status, record.id)" @cancel="cancel(index)" >
          <a-switch v-model:checked="record.status" :checkedValue="'0'" :unCheckedValue="'1'" checked-children="开"
            un-checked-children="关" v-hasPermi="['system:user:edit']"
           />
        </a-popconfirm>
        <a-tag  :color="record.status == 0 ? 'success' : 'error'" v-show="!$hasPermi(['system:user:edit'])">{{ record.status == 0 ? '正常' : '停用' }}</a-tag>
      </template>
      <template v-if="column.dataIndex === 'operation' && record.name != '超级管理员'" >
        <a-popover title="修改密码" trigger="click" v-hasPermi="['system:user:resetPwd']">
            <template #content>
              <a-form :model="passwordData" :rules="rules" ref="passwordForm">
                <a-form-item label="新密码" ref="newPassword" name="newPassword">
                  <a-input v-model:value="passwordData.newPassword" type="password" autocomplete="new-password" />
                </a-form-item>
                <a-form-item>
                  <a-button type="primary" @click="handleChangePassword(record.id)">确认密码</a-button>
                </a-form-item>
              </a-form>
            </template>
            <a-button type="link" v-hasPermi="['system:user:resetPwd']">修改密码</a-button>
          </a-popover>
        <a-button type="link" @click="onQuery(record.id, 'up')" v-hasPermi="['system:user:edit']">
          <template #icon>
            <Icon icon="uil:edit" style="vertical-align: middle;" />
          </template>
          编辑
        </a-button>
        <a-popconfirm title="你确定要删除该条数据吗" ok-text="确定" cancel-text="取消" @confirm="onQuery(record.id, 'del')">
          <a-button type="link" v-hasPermi="['system:user:remove']">
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
import type { Rule } from 'ant-design-vue/es/form';
import { ref, computed, unref } from 'vue'
import type { TableProps, TableColumnType } from 'ant-design-vue';
import { useWindowSize } from '@vueuse/core'
import { message } from 'ant-design-vue';
import API from '@/api/index';
// import { hasPermi } from '@/hooks/publicFunction'
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

const emit = defineEmits(['click', 'close', 'update:modelValue', 'query', 'state']);

// 编辑状态确认回调
const onConfirm = (status: string, id: number) => {
  emit('state', status, id);
}

// 取消回调
const cancel = (i) => {
  props.data[i].status = props.data[i].status == 0 ? '1' : '0';
};

const onQuery = (id: number, status: string) => {
  emit('query', id, status);
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
// 密码修改表单
const passwordForm = ref();
const passwordData = reactive({
  newPassword: '',
});
const rules: Record<string, Rule[]> = {
  newPassword: [
    { required: true, message: '请输入新密码', trigger: 'change' },
  ],
}
const handleChangePassword = (id) => {
  passwordForm.value
    .validate()
    .then(() => {
      passwordUp(id);
    })
    .catch(error => {
      console.log('error', error);
    });
};

const passwordUp = async (id) => {
  let datas = {
    id,
    password: passwordData.newPassword,
  }
  const data: any = await API.sys_user_password_reset(datas);
  const { code, msg } = data
  if (code == 200) {
    message.success(msg)
  } else {
    message.error(msg)
  }

}

</script>
  
<style lang="less" scoped></style>