<template>
    <a-drawer height="80%" placement="top" :closable="false" :open="opens" @close="onClose">
        <a-row>
            <a-col :xs="0" :sm="0" :md="0" :lg="0" :xl="6"></a-col>
            <a-col :xs="24" :sm="24" :md="24" :lg="24" :xl="12">
                <h1>{{ title }}</h1>
                <a-form ref="formRefs" :model="formState" :rules="rules">
                    <a-row>
                        <a-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12">
                            <a-form-item label="姓名" name="name" :labelCol="labelCol">
                                <a-input v-model:value="formState.name" />
                            </a-form-item>
                            <a-form-item ref="position" label="岗位" name="position" :labelCol="labelCol">
                                <a-select placeholder="岗位" v-model:value="formState.position">
                                    <a-select-option :value="item.postCode" v-for="item in distList?.sys_post_list"
                                        :key="item.id">{{
                                            item.postName }}</a-select-option>
                                </a-select>
                            </a-form-item>
                            <a-form-item ref="age" label="年龄" name="age" :labelCol="labelCol">
                                <a-input v-model:value="formState.age" />
                            </a-form-item>
                            <a-form-item ref="hireDate" label="入职日期" v-if="opens && title!=='修改'" name="hireDate" :labelCol="labelCol">
                                <a-date-picker v-model:value="formState.hireDate" :show-time="{ format: 'HH:mm' }"
                                    :format="dateFormat" @change="onRangeChange" style="width: 100%;" />
                            </a-form-item>
                            <a-form-item ref="terminationDate" label="离职职日期" v-if="title === '修改' && opens"
                                name="terminationDate" :labelCol="labelCol">
                                <a-date-picker v-model:value="formState.terminationDate" :show-time="{ format: 'HH:mm' }"
                                    :format="dateFormat" @change="onRangeChange1" style="width: 100%;" />
                            </a-form-item>
                            <a-form-item ref="deptId" label="所属部门" name="deptId" :labelCol="labelCol" tooltip="注册新员工请选择此项 如不选择则视为物业超级管理员" >
                                <a-select placeholder="请选择所属部门" allowClear  show-search v-model:value="formState.deptId">
                                    <a-select-option :value="item.value" v-for="item in distList?.pproperty_dict"
                                        :key="item.value">{{
                                            item.label }}</a-select-option>
                                </a-select>
                                
                            </a-form-item>
                            <a-form-item ref="salary" label="工资" name="salary" :labelCol="labelCol">
                                <a-input v-model:value="formState.salary" />
                            </a-form-item>
                            <a-form-item ref="email" label="邮箱" name="email" :labelCol="labelCol">
                                <a-input v-model:value="formState.email" />
                            </a-form-item>
                        </a-col>
                        <a-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12">
                            <a-form-item ref="account" label="账号" name="account" :labelCol="labelCol">
                                <a-input v-model:value="formState.account" />
                            </a-form-item>
                            <a-form-item ref="password" v-if="title === '新增'" label="密码" name="password"
                                :labelCol="labelCol">
                                <a-input-password v-model:value="formState.password" />
                            </a-form-item>
                            <a-form-item ref="phone" label="手机号" name="phone" :labelCol="labelCol">
                                <a-input v-model:value="formState.phone" />
                            </a-form-item>
                            <a-form-item ref="roleIds" label="角色职位" name="roleIds" :labelCol="labelCol">
                                <a-select placeholder="角色职位" v-model:value="formState.roleIds" mode="multiple">
                                    <a-select-option :value="item.dictValue" v-for="item in distList?.sys_role_list"
                                        :key="item.id">{{
                                            item.roleName }}</a-select-option>
                                </a-select>
                            </a-form-item>
                            <a-form-item ref="sex" label="性别" name="sex" :labelCol="labelCol">
                                <a-select placeholder="性别" v-model:value="formState.sex">
                                    <a-select-option :value="item.dictValue" v-for="item in distList?.sys_user_sex"
                                        :key="item.dictLabel">{{
                                            item.dictLabel }}</a-select-option>
                                </a-select>
                            </a-form-item>
                            <a-form-item ref="status" label="帐号状态" name="status" :labelCol="labelCol">
                                <a-select placeholder="帐号状态" v-model:value="formState.status">
                                    <a-select-option :value="item.dictValue" v-for="item in distList?.sys_normal_disable"
                                        :key="item.dictLabel">{{
                                            item.dictLabel }}</a-select-option>
                                </a-select>
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
                                    <a-button type="primary" style="background: rgb(57, 192, 91);" @click="onSubmit">确定</a-button>
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
import dayjs from 'dayjs';
import { Dayjs } from 'dayjs';
const props = defineProps<{
    opens: boolean; // 直接声明为 Boolean 类型
    data?: UnwrapRef<search>;
    title: string;
    distList?: UnwrapRef<any>;
}>()
const dateFormat = 'YYYY-MM-DD HH:mm:ss';
let value4 = ref<Dayjs>();  //时间
let formState = ref<UnwrapRef<search>>(props.data)
    // 监听父组件传递的 modelValue 变化
watch(() => props.data, (newValue) => {
    debugger
    formState.value = newValue;
});
watch(() => props.opens, (newData) => {
    // 更新 formState
    formState.value = props.data;
    if (formState.value.hireDate) {
        formState.value.hireDate = dayjs(props.data.hireDate, dateFormat);
    }
    if (formState.value.terminationDate) {
        formState.value.terminationDate = dayjs(props.data.terminationDate, dateFormat);
    }

});
const emit = defineEmits(['close', 'submit']);
const labelCol = { span: 6 };
const wrapperCol = { span: 13 };
const phoneCheck = (rule, value) => {
    return new Promise<void>((resolve, reject) => {
        const reg =
            /^(\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$/;
        if (!reg.test(value)) {
            reject('手机号码格式不正确');
        } else {
            resolve();
        }
    });
};
const rules: Record<string, Rule[]> = {
    name: [
        { required: true, message: '请输入姓名', trigger: 'change' },
    ],
    password: [
        { required: true, message: '请输入密码', trigger: 'change' },
    ],
    phone: [
        { required: true, message: '请输入正确的手机号', trigger: 'change' },
        {
            max: 11,
            validator: phoneCheck,
            trigger: 'blur',
        },
    ],
    position: [
        { required: true, message: '请选择岗位', trigger: 'change' },
    ],
    hireDate: [
        { required: true, message: '请选择入职日期', trigger: 'change' },
    ],
    account: [
        { required: true, message: '请输入账号', trigger: 'change' },
    ],
    roleIds: [
        { required: true, message: '请选择职位', trigger: 'change' },
    ],
};
onMounted(() => {
});

const onRangeChange = (value: Dayjs, dateString: string) => {
    formState.value.hireDate = dayjs(dateString, dateFormat);
};
const onRangeChange1 = (value: Dayjs, dateString: string) => {
    if (dateString) {
        formState.value.terminationDate = dayjs(dateString, dateFormat);
    }else{
        formState.value.terminationDate = null;

    }
   
};

const formRefs = ref();
const onSubmit = () => {
    formRefs.value
        .validate()
        .then(() => {
            emit('submit', formState, props.title);
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
  
  