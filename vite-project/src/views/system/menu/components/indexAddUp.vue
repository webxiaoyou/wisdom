<template>
    <a-drawer height="80%" placement="top" :closable="false" :open="opens" @close="onClose">
        <a-row>
            <a-col :xs="0" :sm="0" :md="0" :lg="0" :xl="6"></a-col>
            <a-col :xs="24" :sm="24" :md="24" :lg="24" :xl="12">
                <h1>{{ title }}</h1>
                <a-form ref="formRefs" :model="formState" :rules="rules">
                    <a-form-item label="主类目" name="parentId">
                        <a-tree-select v-model:value="formState.parentId" v-model:searchValue="searchValue" show-search
                            style="width: 100%" :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
                            placeholder="请选择类目" :field-names="{
                                children: 'children',
                                label: 'menuName',
                                value: 'id',
                            }" allow-clear :tree-data="parentList" tree-node-filter-prop="menuName">
                            <template #title="{ menuName: label }">
                                <template v-for="(fragment, i) in label
                                    .toString()
                                    .split(new RegExp(`(?<=${searchValue})|(?=${searchValue})`, 'i'))">
                                    <span v-if="fragment.toLowerCase() === searchValue.toLowerCase()" :key="i"
                                        style="color: #08c">
                                        {{ fragment }}
                                    </span>
                                    <template v-else>{{ fragment }}</template>
                                </template>
                            </template>
                        </a-tree-select>
                    </a-form-item>
                    <a-form-item label="菜单类型" name="menuType">
                        <a-radio-group v-model:value="formState.menuType" :options="plainOptions" />
                    </a-form-item>
                    <a-row>
                        <a-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12">
                            <a-form-item label="菜单名称" name="menuName" :labelCol="labelCol">
                                <a-input v-model:value="formState.menuName" />
                            </a-form-item>
                            <a-form-item ref="orderNum" label="显示排序" name="orderNum" :labelCol="labelCol">
                                <a-input-number v-model:value="formState.orderNum" :min="1" :max="100000"
                                    style="width: 100%;" />
                            </a-form-item>

                            <a-form-item ref="component" label="组件地址" name="component" :labelCol="labelCol"
                                v-if="formState.menuType === 'C'">
                                <a-input v-model:value="formState.component" />
                            </a-form-item>
                            <!-- <a-form-item ref="perms" label="路由参数" name="perms" :labelCol="labelCol">
                                <a-input v-model:value="formState.perms" />
                            </a-form-item> -->

                            <a-form-item ref="isFrame" label="是否外链" name="isFrame" :labelCol="labelCol"
                                v-if="formState.menuType !== 'F'">
                                <a-radio-group v-model:value="formState.isFrame" name="isFrame">
                                    <a-radio :value="0">是</a-radio>
                                    <a-radio :value="1">否</a-radio>
                                </a-radio-group>
                            </a-form-item>
                            <a-form-item ref="status" label="菜单状态" name="status" :labelCol="labelCol">
                                <a-radio-group v-model:value="formState.status" name="radioGroup">
                                    <a-radio value="0">正常</a-radio>
                                    <a-radio value="1">停用</a-radio>
                                </a-radio-group>
                            </a-form-item>
                        </a-col>
                        <a-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12">
                            <a-form-item ref="icon" label="菜单图标" name="icon" :labelCol="labelCol"
                                v-if="formState.menuType !== 'F'">
                                <!-- <a-select placeholder="岗位" v-model:value="formState.icon">
                                    <template #dropdownRender>

                                    </template>
                                </a-select> -->
                                <IconSelector :column="6" :options="groupList" mode="single"
                                    v-model:value="formState.icon" />
                            </a-form-item>
                            <a-form-item ref="path" label="路由地址" name="path" :labelCol="labelCol"
                                v-if="formState.menuType !== 'F'">
                                <a-input v-model:value="formState.path" />
                            </a-form-item>
                            <!-- <template> -->

                            <a-form-item ref="perms" label="权限字符" name="perms" :labelCol="labelCol"
                                v-if="formState.menuType === 'C' || formState.menuType === 'F'">
                                <a-input v-model:value="formState.perms" />
                            </a-form-item>
                            <a-form-item ref="isCache" label="是否缓存" name="isCache" :labelCol="labelCol"
                                v-if="formState.menuType === 'C'">
                                <a-radio-group v-model:value="formState.isCache" name="radioGroup">
                                    <a-radio :value="0">缓存</a-radio>
                                    <a-radio :value="1">不缓存</a-radio>
                                </a-radio-group>
                            </a-form-item>

                            <!-- </template> -->
                            <a-form-item ref="visible" label="显示状态" name="visible" :labelCol="labelCol">
                                <a-radio-group v-model:value="formState.visible" name="radioGroup">
                                    <a-radio :value="0">显示</a-radio>
                                    <a-radio :value="1">隐藏</a-radio>
                                </a-radio-group>
                            </a-form-item>

                        </a-col>
                        <a-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" style="text-align: right">
                            <a-form-item ref="remark" label="备注" name="remark" :labelCol="{ span: 3 }">
                                <a-textarea allow-clear v-model:value="formState.remark" placeholder="备注" :rows="4" />
                            </a-form-item>
                        </a-col>

                        <a-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" style="text-align: right">
                            <a-form-item>
                                <a-space>
                                    <a-button type="primary" @click="onClose">取消</a-button>
                                    <a-button type="primary" style="background: rgb(57, 192, 91);"
                                        @click="onSubmit">确定</a-button>
                                    <a-button @click="resetForm">重置</a-button>
                                </a-space>
                            </a-form-item>
                        </a-col>
                    </a-row>
                </a-form>
            </a-col>
            <a-col :xs="0" :sm="0" :md="0" :lg="0" :xl="6"></a-col>
        </a-row>
    </a-drawer>
</template>
<script lang="ts" setup>

import { ref, watch, toRef, onMounted } from 'vue';
import type { UnwrapRef } from 'vue';
import type { Rule } from 'ant-design-vue/es/form';
import { RadioGroupProps, TreeProps,TreeSelectProps } from 'ant-design-vue';
import API from '@/api/index';
// @ts-ignore
import * as icons from '@ant-design/icons-vue/es/icons';
const props = defineProps<{
    opens: boolean; // 直接声明为 Boolean 类型
    data?: UnwrapRef<SysMenu>;  //对象数据
    parentList?: TreeSelectProps['treeData'];
    title: string;  //标题
    distList?: object;  //字典
}>()
const iconList: IconSelectOption[] = [];

const outlined: IconSelectGroup = {
    title: '线框风',
    key: 'outlined',
    list: [],
};
const filled: IconSelectGroup = {
    title: '实底风',
    key: 'filled',
    list: [],
};
const twoTone: IconSelectGroup = {
    title: '双色风',
    key: 'twoTone',
    list: [],
};

const groupList: IconSelectGroup[] = [outlined, filled, twoTone];

for (const key in icons) {
    const icon = {
        label: key,
        value: key,
        component: icons[key],
    };

    iconList.push(icon);
    if (key.endsWith('Outlined')) {
        outlined.list.push(icon);
    } else if (key.endsWith('Filled')) {
        filled.list.push(icon);
    } else if (key.endsWith('TwoTone')) {
        twoTone.list.push(icon);
    }
}
//类型选择 
const plainOptions: RadioGroupProps['options'] = [
    { label: '目录', value: 'M' },
    { label: '菜单', value: 'C' },
    { label: '按键', value: 'F' },
];;

const searchValue = ref('');  //主类目搜索框
let formState = ref<UnwrapRef<SysMenu>>({})
watch(() => props.data, (newData) => {
    if (props.title === '新增') {
        formState.value = {
            menuType: 'M',
            parentId: newData.parentId || 0,
            isFrame: 1,
            isCache: 1,
            status: "0",
            visible: 0,
        }
    } else {
        formState.value = newData;
    }
});


const emit = defineEmits(['close', 'submit']);
const labelCol = { span: 6 };
const wrapperCol = { span: 13 };
//验证
const rules: Record<string, Rule[]> = {
    parentId: [
        { required: true, message: '请选择上级菜单', trigger: 'change' },
    ],
    menuName: [
        { required: true, message: '请输入菜单名称', trigger: 'change' },
    ],
    orderNum: [
        { required: true, message: '请输入排序', trigger: 'change' },
    ],
    path: [
        { required: true, message: '请选择路由地址', trigger: 'change' },
    ],
    component: [
        { required: true, message: '请输入组件', trigger: 'change' },
    ],
};
onMounted(() => {
});

const formRefs = ref();
const onSubmit = () => {
    formRefs.value
        .validate()
        .then(() => {
            emit('submit', formState.value, props.title);
        })
        .catch(error => {
            console.log('error', error);
        });
};
const resetForm = () => {
    formRefs.value.resetFields();
};

const onClose = () => {
    emit('close');
};
</script>
  
  