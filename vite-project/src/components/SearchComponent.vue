<template>
    <div>
        <a-form ref="formRef" :model="searchs">
            <a-row>
                <a-col :xs="xs" :sm="sm" :md="md" :lg="lg" :xl="xl" style="margin-right: 30px;" v-for="column in columns"
                    :key="column.name">
                    <a-form-item :label="column.title" :name="column.name">
                        <template v-if="column.dataType === 'input'">
                            <a-input :placeholder="`请输入${column.title}`" :disabled="column.disabled" allowClear
                                v-model:value="searchs[column.name]" />
                        </template>
                        <template v-else-if="column.dataType === 'select'">
                            <a-select v-model:value="searchs[column.name]" allowClear :disabled="column.disabled" @change="handleSubmit"
                                :placeholder="`请选择${column.title}`" :show-search="column.search">
                                <a-select-option v-for="option in column.options" :key="option[column.keyField]"
                                    :value="option[column.keyField]">
                                    {{ option[column.labelField] }}
                                </a-select-option>
                            </a-select>
                        </template>
                        <template v-if="column.dataType === 'inputNumber'">
                            <a-input-number v-model:value="searchs[column.name]" :disabled="column.disabled"
                                :placeholder="`请输入${column.title}`" style="width: 100%;" />
                        </template>
                        <template v-else-if="column.dataType === 'rangePicker'">
                            <a-range-picker v-model:value="searchs[column.name]" :disabled="column.disabled" allowClear
                                :value-format="'YYYY-MM-DD HH:mm:ss'" />
                        </template>
                    </a-form-item>
                </a-col>
                <a-col :xs="xs" :sm="sm" :md="md" :lg="lg" :xl="xl">
                    <a-form-item :labelCol="labelCol">
                        <a-space>
                            <a-button type="primary" @click="handleSubmit">{{ subTitle }}</a-button>
                            <a-button @click="handleReset">重置</a-button>
                        </a-space>
                    </a-form-item>
                </a-col>

            </a-row>

        </a-form>
    </div>
</template>
  
<script setup lang="ts">
import dayjs from 'dayjs';
/**
 * 组件属性表示组件使用的属性。
 *
 * @typedef {Object} ComponentProps
 * @property {UnwrapRef<any>} [search] - 详情、编辑数据
 * @property {Array<any>} [columns] - 输入框显示数据。
 * @property {string} [subTitle] - 确定按键的名称。
 * @property {number | string} [xs] - “xs”屏幕大小的响应参数。
 * @property {number | string} [sm] - “sm”屏幕大小的响应参数。
 * @property {number | string} [md] - “md”屏幕大小的响应参数。
 * @property {number | string} [lg] - “lg”屏幕大小的响应参数。
 * @property {number | string} [xl] - “xl”屏幕大小的响应参数。
 */

/**
* 表单列配置表示一个表单字段的配置选项。 注意：在配置多选时间的时候 名称必须是 name: 'dateRange', 同时需要在请求体中 添加 dateRange: [], // 时间范围选择 进行双向数据绑定
*
* @typedef {Object} searchColumn
* @property {string} [title] - 列的标题，默认参数。
* @property {string} dataType - 显示输入框的类型，例如：input、select、等输入框类型。
* @property {boolean} disabled - 是否为禁止输入
* @property {string} [name] - 值的名称，输入框接收的输入参数的名称。
* @property {string} [keyField] - 选择器的 Key 字段。
* @property {string} [labelField] - 选择器的名称字段。
* @property {string|number} [defaultValue] - 输入框的默认参数。
* @property {object} [options] - 选项对象，用于选择型输入框。
* @property {Record<Rule[]>} [rules] - 权限管理，用户输入的内容，例如 [{ required: true, message: '不能为空', trigger: 'change' }]。
* @property {boolean} [search] - 是否显示搜索框
*/



import { ref } from 'vue';
const { search, columns, subTitle, xs, sm, md, lg, xl } = defineProps(['search', 'columns', 'subTitle', "xs", "sm", "md", "lg", "xl"]);
const emit = defineEmits(['update:modelValue', 'submit', 'reset']);
const formRef = ref(null);
const searchs = ref(search); // Wrap columns in a ref
const labelCol = { span: 4 };
const dateRange = ref<any>([]);

const handleSubmit = () => {
    // 处理提交逻辑
    emit('submit', searchs.value);

};

watchEffect(() => {
    columns.forEach(item => {
        if (item.defaultValue !== undefined) {
            searchs.value[item.name] = item.defaultValue;
        }
    });
})

const handleReset = () => {
    // 处理重置逻辑
    formRef.value.resetFields();

    emit('reset');
};
</script>
