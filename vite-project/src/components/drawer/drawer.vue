<template>
    <div>
      <a-drawer width="60%" placement="right" :closable="false" :open="opens" @close="onClose">
        <a-descriptions bordered title="详情" :column="{ xxl: 2, xl: 2, lg: 2, md: 1, sm: 1, xs: 1 }">
          <a-descriptions-item v-for="column in columns" :label="column.title" :key="column.dataIndex">
            <template v-if="column.dataIndex === 'avatar' || column.dataIndex === 'imageUrl' || column.dataIndex === 'coverUrl'">
            <a-image :width="80" :src="formData[column.dataIndex]"></a-image>
          </template>
          <template v-if="column.dataIndex === 'imageUrls'">
            <template v-if="formData[column.dataIndex]">
             <a-image :width="80" :src="item" v-for="(item, index) in formData[column.dataIndex].split(',')" :key="index"></a-image>
            </template>
          </template>
          <template v-else-if="column.dataIndex === 'authenticated'">
            <a-tag :color="formData[column.dataIndex] == 0 ? 'success' :formData[column.dataIndex] == 1? 'error': 'warning'"> {{ getDictLabel(props.distList, 'puser_authenticated', formData[column.dataIndex]) }}</a-tag>
          </template> 
          <template v-else-if="column.dataIndex === 'authenticationStatus'">
            <a-tag :color="formData[column.dataIndex] == 0 ? 'warning' :formData[column.dataIndex] == 1? 'success': 'error'"> {{ getDictLabel(props.distList, 'audit_status', formData[column.dataIndex]) }}</a-tag>
          </template> 
          <template v-else-if="column.dataIndex === 'content'">
           <!-- <div style="width: 100%; max-width: 800px;" v-html="formData[column.dataIndex]"></div> -->
           <editor v-model="formData[column.dataIndex]" 
      :disabled="true" :readonly="true"/>
          </template> 
          
          <template v-else>
            {{ getColumnValue(formData, column) }}
          </template>
          </a-descriptions-item>
        </a-descriptions>
      </a-drawer>
    </div>
  </template>
  
  <script setup lang="ts">
  import { ref, watch } from 'vue';
  import { getAssetsImages, getDictLabel,getSingleDictLabel } from '@/hooks/publicFunction'; // 导入必要的函数
  import { UnwrapRef ,h} from 'vue';
  interface TableColumn {
    title: string;
    dataIndex: string;
  }
  // 编辑器配置
const editorConfig = ref({

readOnly: true,  // 设置为只读模式
})
  const props = defineProps<{
    opens: boolean,
    data?: UnwrapRef<any>,
    distList?: UnwrapRef<any>,
    columns: TableColumn[],
  }>()
  
  const formData = ref<UnwrapRef<any>>(props.data);
  
  watch(() => props.opens, (newData) => {
    formData.value = props.data;
  });
  
  const emit = defineEmits(['close', 'click']);
  
  const onClose = () => {
    emit('close');
  };
  
  const getColumnValue = (data: any, column: TableColumn) => {
    const value = data[column.dataIndex];
    if (column.dataIndex === 'sex') {
      return getDictLabel(props.distList, 'sys_user_sex', value);
    } else if (column.dataIndex === 'terminationDate' && data.terminationDate) {
      return data.terminationDate;
    } else if (column.dataIndex === 'status') {
      return getDictLabel(props.distList, 'sys_normal_disable', value);
     } else if (column.dataIndex === 'propertyId') {
      return getSingleDictLabel(props.distList, 'pproperty_dict', value);
     }
     else if (column.dataIndex === 'buildingId') {
      return getSingleDictLabel(props.distList, 'pbuilding_dict', value);
     }
     else if (column.dataIndex === 'floorId') {
      return getSingleDictLabel(props.distList, 'pfloorId_dict', value);
     }
    else {
      return value;
    }
  };
  </script>
  