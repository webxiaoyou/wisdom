<template>
    <div style="border: 1px solid #ccc">
        <Toolbar style="border-bottom: 1px solid #ccc;" :editor="editorRef" :defaultConfig="toolbarConfig"/>
        <Editor style="height: 300px; overflow-y: hidden;text-align: left;" :defaultConfig="editorConfig"
            v-model="innerValue" @onCreated="handleCreated" />
    </div>
</template>
  
<script setup lang="ts">
import { ref, shallowRef, onMounted, watch } from "vue";
import '@wangeditor/editor/dist/css/style.css'; // 引入 css
import { Editor, Toolbar } from '@wangeditor/editor-for-vue';

import { useUserStore } from '@/store/modules/user';
import ipConfig from '@/utils/config';
import { message } from 'ant-design-vue';
const props = defineProps({
  modelValue: {
    type: String,
    required: true,
  },
});
const emit = defineEmits(['update:modelValue', 'submit', 'reset']);

// 工具栏配置
const toolbarConfig = {
//   toolbarKeys: [
//     // 一些常用的菜单 key
//     'bold', // 加粗
//     'italic', // 斜体
//     'through', // 删除线
//     'underline', // 下划线
//     'bulletedList', // 无序列表
//     'numberedList', // 有序列表
//     'color', // 文字颜色
//     'insertLink', // 插入链接
//     'fontSize', // 字体大小
//     'lineHeight', // 行高
//     'uploadImage', // 上传图片
//     'insertImage', // 插入网络图片
//     'editImage', // 插入网络图片
//     // 'uploadVideo',//上传视频
//     'delIndent', // 缩进
//     'indent', // 增进
//     'deleteImage',//删除图片
//     'divider', // 分割线
//     'insertTable', // 插入表格
//     'justifyCenter', // 居中对齐
//     'justifyJustify', // 两端对齐
//     'justifyLeft', // 左对齐
//     'justifyRight', // 右对齐
//     'undo', // 撤销
//     'redo', // 重做
//     'clearStyle', // 清除格式
//     'fullScreen' // 全屏
//   ]
excludeKeys : [
    'uploadVideo',
    // 'italic',
    // 'group-more-style' // 排除菜单组，写菜单组 key 的值即可
]
}
const innerValue = ref(props.modelValue);
// 监听父组件传递的 modelValue 变化
watch(() => props.modelValue, (newValue) => {
    innerValue.value = newValue;
});
// 监听内部值的变化，并通知父组件
watch(innerValue, (newValue) => {
  emit('update:modelValue', newValue);
});
// 编辑器实例，必须用 shallowRef
const editorRef = shallowRef();
// 内部使用的值
let imageList1 = []; // 用于记录插入或上传的图片
const handleCreated = (editor: any) => {
    editorRef.value = editor; // 记录 editor 实例，重要！
};
//执行双向数据绑定
// const handleChange = (editor) => {// emit('update:value', innerValue.value) }
const handleInsertedImage = (info) => {
  // 在 onInsertedImage 钩子中收集插入或上传的图片信息
  debugger
  imageList1.push(info);
};
const userStore = useUserStore();

// 编辑器配置
const editorConfig = ref({
    placeholder: '请输入内容...',
    readOnly: false,  // 只读
    MENU_CONF: {
        // 配置上传图片
        uploadImage: {
            server: ipConfig.baseIP + '/system/oss/upload', // 配置图片上传地址
            maxFileSize: 10 * 1024 * 1024, // 10M  图片大小限制
            fieldName: 'file', // 上传名字
            allowedFileTypes: ['image/*'], // 选择文件时的类型限制，默认为 ['image/*'] 。如不想限制，则设置为 []
            // 自定义上传参数，传递图片时需要带一些参数过去写在这。参数会被添加到 formData 中，一起上传到服务端。
            // meta: {
            //   image_class_id: "2",
            //   file_type: "1",
            // },
            // 自定义设置请求头，比如添加token之类的

            headers: {
                Authorization: `Bearer ${userStore.token}`,
            },
            // 上传进度的回调函数，可以用来显示进度条
            onProgress(progress: any) {
                // progress 是 0-100 的数字
                console.log('progress', progress)
            },
            // // 单个文件上传成功之后
            // onSuccess(file, res) {
            //     console.log(`${file.name} 上传成功`, res)
            // },

            // 单个文件上传失败
            onFailed(file: any, res: any) {
                console.log(`${file.name} 上传失败`, res)
            },

            // 上传错误，或者触发 timeout 超时
            onError(file: any, err: any, res: any) {
                message.error(err.message) // 显示错误信息
                console.log(`${file.name} 上传出错`, res)
            },

            // 插入图片到富文本编辑器回显
            customInsert(res: any, insertFn: any) {
                // console.log(res)
                // res 即服务端的返回结果
                // getPhotoUrl(res.data[0]).then((res) => {
                //   const url = res.data
                //   const alt = ''
                //   const href = res.data
                //   // 从 res 中找到 url alt href ，然后插入图片
                insertFn(res.data.url, res.data.fileName, res.data.url)
                // })
            },

        },
        // insertImage: {
        //         onInsertedImage(imageNode) {  // TS 语法
        //             debugger
        //             if (imageNode == null) return

        //             const { src, alt, url, href } = imageNode
        //             console.log('inserted image', src, alt, url, href)
        //         },
        //     },
        // // 配置上传视频
        // uploadVideo: {
        //   server: 'http://111.198.10.15:21409/minio/file/upload', // 配置视频上传地址
        //   maxFileSize: 5 * 1024 * 1024, // 5M  视频大小限制
        //   fieldName: 'multipartFile', // 上传名字
        //   // 最多可上传几个文件，默认为 5
        //   // maxNumberOfFiles: 1,
        //   allowedFileTypes: [], // 选择文件时的类型限制，默认为 ['video/*'] 。如不想限制，则设置为 []
        //   // 自定义上传参数，传递图片时需要带一些参数过去写在这。参数会被添加到 formData 中，一起上传到服务端。
        //   // meta: {
        //   //   type: 1,
        //   // },
        //   // 自定义设置请求头，比如添加token之类的
        //   // headers: {
        //   //     Accept: 'text/x-json',
        //   //     otherKey: 'xxx'
        //   // },
        //   // metaWithUrl: false, // 将 meta 拼接到 url 参数中，默认 false
        //   // withCredentials: true, // 跨域是否传递 cookie ，默认为 false
        //   // 上传之前触发
        //   onBeforeUpload(file: any) {
        //     // file 选中的文件，格式如 { key: file }
        //     return file
        //     // 可以 return
        //     // 1. return file 或者 new 一个 file ，接下来将上传
        //     // 2. return false ，不上传这个 file
        //   },

        //   // 上传进度的回调函数，可以用来显示进度条
        //   onProgress(progress: any) {
        //     // progress 是 0-100 的数字
        //     console.log('progress', progress)
        //   },

        //   // // 单个文件上传成功之后
        //   onSuccess(file: any, res: any) {
        //     console.log(`${file.name} 上传成功`, res)
        //   },

        //   // 单个文件上传失败
        //   onFailed(file: any, res: any) {
        //     console.log(`${file.name} 上传失败`, res)
        //   },

        //   // 上传错误，或者触发 timeout 超时
        //   onError(file: any, err: any, res: any) {
        //     console.log(`${file.name} 上传出错`, err, res)
        //   },

        //   // 插入图片到富文本编辑器回显
        //   customInsert(res: any, insertFn: any) {
        //     console.log(res, '视频插入')
        //     // res 即服务端的返回结果
        //     // let url = res.data.url;
        //     // let poster = res.data.poster;
        //     // 从 res 中找到 url poster ，然后插入
        //     // 参数url是视频地址，poster是视频封面图片，后端如果不返回，可以考虑写死一个固定的封面图
        //     getPhotoUrl(res.data[0]).then((res) => {
        //       const url = res.data
        //       // 从 res 中找到 url alt href ，然后插入图片
        //       insertFn(url, '')
        //     })
        //   },
        // },
    },
})
</script>
  