<template>
  <div class="clearfix">
    <template v-if="!drag">
      <a-upload v-model:file-list="fileList" :customRequest="onUpload" list-type="picture-card" @preview="handlePreview"
        :beforeUpload="handleBeforeUpload" :progress="progress" :multiple="multiple" :maxCount="limit"
        @change="changeValue">
        <div v-if="fileList.length < limit">
          <plus-outlined />
          <div style="margin-top: 8px">图片上传</div>
        </div>
      </a-upload>
      <!-- 上传提示 -->
      <div class="el-upload__tip" v-if="showTip">
        请上传
        <template v-if="fileSize"> 大小不超过 <b style="color: #f56c6c">{{ fileSize }}MB</b> </template>
        <template v-if="fileType"> 格式为 <b style="color: #f56c6c">{{ fileType.join("/") }}</b> </template>
        的文件
      </div>
    </template>
    <template v-else>
      <a-upload-dragger v-model:file-list="fileList" :customRequest="onUpload" list-type="picture-card"
        @preview="handlePreview" :beforeUpload="handleBeforeUpload" :progress="progress" :multiple="multiple"
        :maxCount="limit" @change="changeValue">
        <div v-if="fileList.length < limit">
          <p class="ant-upload-drag-icon">
            <inbox-outlined></inbox-outlined>
          </p>
          <p class="ant-upload-text">点击或将文件拖拽到这里上传</p>
          <p class="ant-upload-hint" v-if="showTip">
            <template v-if="fileSize"> 大小不超过 <b style="color: #f56c6c">{{ fileSize }}MB</b> </template>
            <template v-if="fileType"> 格式为 <b style="color: #f56c6c">{{ fileType.join("/") }}</b> </template>
          </p>
        </div>
      </a-upload-dragger>
    </template>
    <a-image :style="{ display: 'none' }" :preview="{
      visible: previewVisible,
      onVisibleChange: handleCancel,
      src: previewImage
    }" />

    <!-- <a-modal :open="previewVisible" :title="previewTitle" :footer="null" @cancel="handleCancel">
      <img alt="example" style="width: 100%" :src="previewImage" />
    </a-modal> -->
  </div>
</template>

<script setup lang="ts" name="imageupload">
import { reactive, toRefs, onMounted, getCurrentInstance } from 'vue'
import { useRouter } from 'vue-router';
import API from '@/api/index';
import { useUserStore } from '@/store/modules/user';
import { PlusOutlined, InboxOutlined } from '@ant-design/icons-vue';
import { ref } from 'vue';
import { message, UploadProps, Upload } from 'ant-design-vue';
const emit = defineEmits(['update:value', 'submit', 'reset']);
const props = defineProps({
  value: [String, Object, Array<any>],
  // 图片数量限制
  limit: {
    type: Number,
    default: 5,
  },
  // 大小限制(MB)
  fileSize: {
    type: Number,
    default: 5,
  },
  // 文件类型, 例如['png', 'jpg', 'jpeg']
  fileType: {
    type: Array,
    default: () => ["png", "jpg", "jpeg"],
  },
  // 是否多选
  multiple: {
    type: Boolean,
    default: false
  },
  // 是否运行拖拽
  drag: {
    type: Boolean,
    default: false,
  },
  // 是否显示提示
  isShowTip: {
    type: Boolean,
    default: true
  },
  //指定fileList返回的数据
  fileListData: {
    type: Object,
    default: () => {
      return {}
    }
  },
});
const previewVisible = ref(false);  //图片是否显示
const previewImage = ref('');  //图片路径
const previewTitle = ref('');  //图片标题
const issu=ref(false);  //是否上传成功按钮
const fileList = ref<UploadProps['fileList']>([]);
function getBase64(file: File) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result);
    reader.onerror = error => reject(error);
  });
}
// 是否显示提示
const showTip = computed(() => {
  return props.isShowTip && (props.fileType || props.fileSize);
})


const handleCancel = (value) => {
  previewVisible.value = value;
  previewTitle.value = '';
};
//查看图片
const handlePreview = async (file: UploadProps['fileList'][number]) => {
  if (!file.url && !file.preview) {
    file.preview = (await getBase64(file.originFileObj)) as string;
  }
  previewImage.value = file.url || file.preview;
  previewVisible.value = true;
  previewTitle.value = file.name || file.url.substring(file.url.lastIndexOf('/') + 1);
};
// 上传前loading加载
const handleBeforeUpload = (file) => {
  let isImg = false;
  if (props.fileType.length) {
    let fileExtension = "";
    if (file.name.lastIndexOf(".") > -1) {
      fileExtension = file.name.slice(file.name.lastIndexOf(".") + 1);
    }
    isImg = props.fileType.some((type: any) => {
      if (file.type.indexOf(type) > -1) return true;
      if (fileExtension && fileExtension.indexOf(type) > -1) return true;
      return false;
    });
  } else {
    isImg = file.type.indexOf("image") > -1;
  }

  if (!isImg) {
    message.error(`文件格式不正确, 请上传${props.fileType.join("/")}图片格式文件!`);
    return Upload.LIST_IGNORE;
  }
  if (props.fileSize) {
    const isLt = file.size / 1024 / 1024 < props.fileSize;
    if (!isLt) {
      message.error(`上传头像图片大小不能超过 ${props.fileSize} MB!`);
      return Upload.LIST_IGNORE;
    }
  }
}

//上传进度
const progress: UploadProps['progress'] = {
  strokeColor: {
    '0%': '#108ee9',
    '100%': '#87d068',
  },
  strokeWidth: 3,
  format: percent => `${parseFloat(percent.toFixed(2))}%`,
  class: 'test',
};

//上传操作
const onUpload = async (file) => {
  issu.value=false;
  // 创建一个 FormData 对象，将文件信息添加到其中
  const formData = new FormData();
  formData.append('file', file.file);
  const data: any = await API.sys_file_upload(formData)
  if (data.code === 200) {
    if (Object.keys(props.fileListData).length > 0) {
      for (const item in props.fileListData) {
        file.file[item] = data.data[item]
      }
    }
    file.onSuccess(data.data.url, file.file.name);
    emit('update:value', listToString(fileList.value))
  } else {
    file.onError(data.msg, "上传失败");
    message.error(data.msg)
  }
}

//监听数据变化 执行双向数据绑定
const changeValue = (value: string | object | Array<string | object>) => {

  
}
// 对象转成指定字符串分隔
const listToString = (list) => {
  let strs = [];
  if (Object.keys(props.fileListData).length > 0) {
    for (let i in list) {
      // 创建一个新的对象，使用das对象的属性名和list[i].response对象的属性值
      let data = Object.fromEntries(
        Object.keys(props.fileListData).map((key) => [key, list[i][key]])
      );
      strs.push(data);
    }
  } else {
    for (let i in list) {
      strs.push(list[i].response);
    }
  }
  return strs;
}

watchEffect(()=>{
  if (Array.isArray(props.value)) {
    // 将数组转为 UploadProps['fileList'] 结构
    fileList.value=[...props.value]
    // fileList.value = props.value.map((item: any, index: number) => ({
    //   uid: index.toString(),
    //   name: item.name || 'image.png',
    //   status: 'done',
    //   url: item.url,
    // }));
    return;
  } else if (typeof props.value === 'object' && props.value !== null) {
    // 处理对象，根据需要定义对应属性
    fileList.value = [{
      uid: '0',
      name: props.value.name || 'image.png',
      status: 'done',
      url: props.value.url,
      // 可根据需要添加其他属性
    }];
    return;
  } else if (typeof props.value === 'string'  ) {
    // 处理字符串，根据需要定义对应属性
    fileList.value = [{
      uid: '0',
      name: 'image.png',
      status: 'done',
      url: props.value,
      // 可根据需要添加其他属性
    }];
    return;
  }else {
    fileList.value =[];
  }
})


</script>
<style scoped>
/* you can make up upload button and sample style by using stylesheets */
.ant-upload-select-picture-card i {
  font-size: 32px;
  color: #999;
}

.ant-upload-select-picture-card .ant-upload-text {
  margin-top: 8px;
  color: #666;
}
</style>