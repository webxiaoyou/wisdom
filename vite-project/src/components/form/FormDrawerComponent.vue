<template>
    <a-drawer height="80%" placement="top" :closable="false" :open="props.opens">
        <a-row>
            <a-col :xs="0" :sm="0" :md="0" :lg="0" :xl="6"></a-col>
            <a-col :xs="24" :sm="24" :md="24" :lg="24" :xl="12">
                <h1 style="margin-bottom: 60px;">{{ props.title }}</h1>
                <!-- <a-form ref="formRef" :model="formData">
                    <div style="display: flex;flex-wrap: wrap;justify-content: space-between;">
                        <a-col :xs="xs" :sm="sm" :md="md" :lg="lg" :xl="xl" v-for="(column, iColumn) in props.columns"
                            :key="column.name">
                            <a-form-item :label="column.title" :name="column.name" :labelCol="labelCol"
                                :rules="column.rules">
                                <template v-if="column.dataType === 'input'">
                                    <a-input :id="column.name" :disabled="column.disabled" :placeholder="`请输入${column.title}`" allowClear
                                        v-model:value="formData[column.name]" />
                                </template>
                                <template v-if="column.dataType === 'inputNumber'">
                                    <a-input-number v-model:value="formData[column.name]" :disabled="column.disabled"
                                        :placeholder="`请输入${column.title}`" :min="1" :max="100000" style="width: 100%;" />
                                </template>

                                <template v-if="column.dataType === 'textarea'">
                                    <a-textarea allow-clear v-model:value="formData[column.name]" :disabled="column.disabled"
                                        :placeholder="`请输入${column.title}`" :rows="4" />
                                </template>

                                <template v-if="column.dataType === 'select'">
                                    <a-select v-model:value="formData[column.name]" allowClear :disabled="column.disabled"
                                        :placeholder="`请选择${column.title}`">
                                        <a-select-option v-for="option in column.options" :key="option[column.keyField]"
                                            :value="option[column.keyField]">
                                            {{ option[column.labelField] }}
                                        </a-select-option>
                                    </a-select>
                                </template>
                                <template v-if="column.dataType === 'selectMenuTree'">
                                    <a-form-item-rest>
                                        <a-checkbox v-model:checked="unfoldT"
                                            @change="handleCheckNnfoldT">展开/折叠</a-checkbox>
                                        <a-checkbox v-model:checked="checkAllN"
                                            @change="handleCheckAllN">全选/全部选</a-checkbox>
                                        <a-checkbox v-model:checked="ganged">父子联动</a-checkbox>
                                    </a-form-item-rest>
                                    <div class="tree-border">
                                        <a-tree v-model:checkedKeys="checkedKeys" v-model:expandedKeys="expandedKeys" :disabled="column.disabled"
                                            :checkStrictly="ganged" checkable :tree-data="column.options.menus"
                                            :field-names="{ children: column.children, title: column.labelField, key: column.keyField }"
                                            @check="(checkedKeys) => handleTreeCheck(checkedKeys, iColumn)">
                                        </a-tree>
                                    </div>
                                </template>
                                <template v-if="column.dataType === 'rangePicker'">
                                    <a-range-picker v-model:value="formData[column.name]" allowClear :disabled="column.disabled"
                                        :show-time="{ format: 'HH:mm' }" format="YYYY-MM-DD HH:mm:ss" />
                                </template>
                            </a-form-item>
                        </a-col>
                    </div>
                    <a-col>
                        <a-form-item :labelCol="labelCol" style="text-align: right;margin-top: 1.25rem;">
                            <a-space>
                                <a-button type="primary" @click="onClose" v-if="!showCall">取消</a-button>
                                <a-button type="primary" style="background: rgb(57, 192, 91);" @click="handleSubmit" v-if="!showSubmit">{{
                                    subTitle }}</a-button>
                                <a-button @click="handleReset" v-if="!showReset">重置</a-button>
                            </a-space>
                        </a-form-item>  
                    </a-col>
                </a-form> -->
                <FormComponent :subTitle="props.subTitle" :opens="props.opens" :title="props.title" :data="props.data" :xs="props.xs" :sm="props.sm"
                    :md="props.md" :lg="props.lg" :xl="props.xl" :columns="props.columns" @submit="handleSubmit" @close="onClose" :showCall="props.showCall" :showReset="props.showReset" :showSubmit="props.showSubmit"></FormComponent>
            </a-col>
            <a-col :xs="0" :sm="0" :md="0" :lg="0" :xl="6"></a-col>
        </a-row>
    </a-drawer>
</template>
  

<script setup lang="ts">
/**
 * 组件属性表示组件使用的属性。
 *
 * @typedef {Object} ComponentProps
 * @property {boolean} opens -  是否弹出抽屉
 * @property {UnwrapRef<SysRole>} [data] - 详情、编辑数据
 * @property {Array<formColumns>} [columns] - 输入框显示数据。
 * @property {string} [subTitle] - 确定按键的名称。
 * @property {string} [title] - 组件的标题。 修改、新增
 * @property {number | string} [xs] - “xs”屏幕大小的响应参数。
 * @property {number | string} [sm] - “sm”屏幕大小的响应参数。
 * @property {number | string} [md] - “md”屏幕大小的响应参数。
 * @property {number | string} [lg] - “lg”屏幕大小的响应参数。
 * @property {number | string} [xl] - “xl”屏幕大小的响应参数。
 * @property {boolean} showCall -  是否显示取消按键 默认显示
 * @property {boolean} showReset -  是否显示重置按键 默认显示
 * @property {boolean} showSubmit -  是否显示确定按键 默认显示
 * 
 */

/**
* 表单列配置表示一个表单字段的配置选项。
*
* @typedef {Object} FormColumn
* @property {string} [title] - 列的标题，默认参数。
* @property {string} dataType - 显示输入框的类型，例如：input、select、等输入框类型。
* @property {boolean} disabled - 是否为禁止输入
* @property {string} [dataTypename] - 输入字段的数据类型，例如字符串或数字。
* @property {string} [name] - 值的名称，输入框接收的输入参数的名称。
* @property {string} [keyField] - 选择器的 Key 字段。
* @property {string} [labelField] - 选择器的名称字段。
* @property {string|number} [defaultValue] - 输入框的默认参数。
* @property {object} [options] - 选项对象，用于选择型输入框。
* @property {Record<Rule[]>} [rules] - 权限管理，用户输入的内容，例如 [{ required: true, message: '不能为空', trigger: 'change' }]。
*/

import { ref } from 'vue';
import type { UnwrapRef } from 'vue';
const emit = defineEmits(['submit', 'close']);
const props = defineProps<{
    opens: boolean; //  是否弹出抽屉
    data?: any;  //详情、编辑数据
    columns?: Array<FormColumns>; //显示列
    subTitle?: string;  //按键名称
    title?: string;  //标题
    //响应式参数  详情查看 bootstrap-vue 响应式参数
    xs?: number | string;
    sm?: number | string;
    md?: number | string;
    lg?: number | string;
    xl?: number | string;
    showCall?: boolean;
    showReset?: boolean;
    showSubmit?: boolean;
}>()

// watch(() => props.opens, (newValue, oldValue) => {
//     debugger
//     openAddUp.value = newValue;
// })

// let formData = ref<any>(props.data); // 表单数据
// const unfoldT = ref(false);  //展开/折叠按键
// const expandedKeys = ref<string[]>();   //展开/折叠数组
// let checkedKeys = ref<string[]>();
// const ganged = ref(true);  //父子联动
// const formRef = ref(null);
// const checkAllN = ref(false); //全选/全部选


// const labelCol = { span: 4 };
// watch(() => props.opens, (newValue, oldValue) => {
//     if (props.title === '新增') {
//         props.columns.forEach(item => {
//             if (item.defaultValue !== undefined) {
//                 formData.value[item.name] = item.defaultValue;
//             }
//         });
//     } else if (props.title === '编辑') {
//         formData.value = props.data;
//         props.columns.forEach(item => {
//             if (item.options?.checkedKeys !== undefined) {
//                 checkedKeys.value = item.options.checkedKeys;
//                 formData.value[item.name] = item.options.checkedKeys;
//             }
//         });
//     } else {
//         closes();
//     }
// })

// // 展开或折叠
// const handleCheckNnfoldT = () => {
//     if (unfoldT.value) {
//         // 全选
//         props.columns.forEach((column) => {
//             if (column.dataType === 'selectMenuTree') {
//                 expandedKeys.value = flattenMenus(column.options.menus);
//             }
//         });
//     } else {
//         // 全不选
//         props.columns.forEach((column) => {
//             if (column.dataType === 'selectMenuTree') {
//                 expandedKeys.value = [];
//             }
//         });
//     }
// };


// // 全选或全不选
// const handleCheckAllN = () => {
//     if (checkAllN.value) {
//         // 全选
//         props.columns.forEach((column) => {
//             if (column.dataType === 'selectMenuTree') {
//                 checkedKeys.value = flattenMenus(column.options.menus);
//                 formData.value.menuIds = checkedKeys.value;

//             }
//         });
//     } else {
//         // 全不选
//         props.columns.forEach((column) => {
//             if (column.dataType === 'selectMenuTree') {
//                 checkedKeys.value = [];
//                 formData.value.menuIds = [];
//             }
//         });
//     }
// };
// // 所选内容
// const handleTreeCheck = (checkedKeys, i) => {

//     // 更新 formData[column.name]，这里假设 column.name 是对应的字段名称
//     formData.value[props.columns[i].name] = checkedKeys.checked;
// };

// const flattenMenus = (menus) => {
//     const flattened = [];
//     for (const menu of menus) {
//         flattened.push(menu.value);
//         if (menu.children && menu.children.length > 0) {
//             flattened.push(...flattenMenus(menu.children));
//         }
//     }
//     return flattened;
// };


const handleSubmit = (formData,title) => {
            emit('submit', formData, title);
};

// const handleReset = () => {
//     // 处理重置逻辑
//     formRef.value.resetFields();
// };
// const closes = () => {
//     checkedKeys.value = [];
//     expandedKeys.value = [];
//     formData.value = {};
//     formData.value.menuIds = [];
//     checkAllN.value = false;
//     unfoldT.value = false;
// }
const onClose = () => {
    // handleReset();
    emit('close');
};

</script>

<style lang="less" scoped>
.tree-border {
    margin-top: .625rem;
    border: 1px solid #e5e6e7;
    background: #fff none;
    border-radius: 4px;
    padding: .5rem;
    box-sizing: border-box;
}
</style>