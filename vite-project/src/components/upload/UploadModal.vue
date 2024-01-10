<template>
  <a-modal :open="opens" title="文件上传" @ok="handleOk" width="50%" @cancel="handleCancel">
    <a-form :model="formData" name="basic" autocomplete="off">
      <a-form-item label="图片数量" name="limit">
        <a-input-number v-model:value="formData.limit" :placeholder="`请输入图片数量限制`" :min="1" :max="9"
          style="width: 100%;" />
      </a-form-item>
      <a-form-item label="图片大小限制(MB)" name="fileSize">
        <a-input-number v-model:value="formData.fileSize" :placeholder="`请输入图片大小限制(MB)`" :min="1" :max="9"
          style="width: 100%;" />
      </a-form-item>
      <a-form-item label="图片大小限制(MB)" name="fileSize">
        <a-select v-model:value="parsedFileType"  allowClear placeholder="请选择文件类型" @change="handleSelectChange">
          <a-select-option v-for="option in distList.upload_file" :key="option.dictCode" :value="option.dictValue">
            {{ option.dictLabel }}
          </a-select-option>
        </a-select>
      </a-form-item>


      <a-form-item label="是否多选" name="fileSize">
        <a-radio-group v-model:value="formData.multiple" button-style="solid">
          <a-radio-button :value="true">是</a-radio-button>
          <a-radio-button :value="false">否</a-radio-button>
        </a-radio-group>
      </a-form-item>
      <a-form-item label="是否拖拽" name="fileSize">
        <a-radio-group v-model:value="formData.drag" button-style="solid">
          <a-radio-button :value="true">是</a-radio-button>
          <a-radio-button :value="false">否</a-radio-button>
        </a-radio-group>
      </a-form-item>
      <a-form-item label="是否显示提示" name="fileSize">
        <a-radio-group v-model:value="formData.isShowTip" button-style="solid">
          <a-radio-button :value="true">是</a-radio-button>
          <a-radio-button :value="false">否</a-radio-button>
        </a-radio-group>
      </a-form-item>
    </a-form>
    <upload_file @update:value="fileLists" :limit="formData.limit" :file-size="formData.fileSize" :file-type="formData.fileType"
      :multiple="formData.multiple" :drag="formData.drag" :is-show-tip="formData.isShowTip" :fileListData="{ossId:'',url:''}"></upload_file>
  </a-modal>
</template>

<script setup lang="ts" name="uploadmodal">
import { ref } from 'vue';
import upload_file from '@/components/upload/UploadFile.vue';
import { useUserDictData } from '@/hooks/dicts';
const emit = defineEmits(['click', 'close', 'update:value', 'submit']);
let { distList } = useUserDictData(['upload_file']);  //字典数据
const props = defineProps({
  // opens: boolean; //  是否弹出抽屉
  value: {
    trype: Array<any>,
    default: []
  },
  opens: {
    type: Boolean,
    default: false
  }
});
const fileLists=(value)=>{
 emit('update:value', value);
}
const fileList = ref<Array<any>>([]);

//输入框数据
const formData = ref({
  limit: 1, //图片数量限制
  fileSize: 5, //大小限制(MB)
  fileType: ["png", "jpg", "jpeg"] ,  // 文件类型, 例如['png', 'jpg', 'jpeg']
  multiple: false,   // 是否多选
  drag: false, // 是否运行拖拽
  isShowTip: true // 是否显示提示
})
const parsedFileType = ref();
watch(()=> distList.value, (newVal, oldVal) =>{
  parsedFileType.value=distList.value.upload_file[0].dictValue

})
const handleOk = (e: MouseEvent) => {
  emit('submit');
};

const handleCancel=(e)=>{
  emit('close');
};

const handleSelectChange = (value) => {
  // Convert the selected value back to a string before setting it to the model
  formData.value.fileType = JSON.parse(value);
}
</script>
<style scoped></style>