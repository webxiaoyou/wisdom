<template>
    <div>
        <a-drawer width="60%" placement="right" :closable="false" :open="opens" @close="onClose">
            <a-descriptions bordered :title="formData.name" :column="{ xxl: 2, xl: 2, lg: 2, md: 1, sm: 1, xs: 1 }">
                <a-descriptions-item label="账号">{{ formData.account }}</a-descriptions-item>
                <a-descriptions-item label="头像">
                    <a-image :width="80"
                        :src="formData.avatar ? formData.avatar : getAssetsImages('avatar.jpg')" />
                </a-descriptions-item>
                <a-descriptions-item label="职位">
                    <a-select placeholder="岗位" v-model:value="formData.position" :open="false" :showArrow="false"
                        :bordered="false">
                        <a-select-option :value="item.postCode" v-for="item in distList?.sys_post_list" :key="item.id">{{
                            item.postName }}</a-select-option>
                    </a-select>
                </a-descriptions-item>
                <a-descriptions-item label="角色职位">
                    <a-select placeholder="角色职位" :open="false" :bordered="false" disabled v-model:value="formData.roleIds"
                        mode="multiple">
                        <a-select-option :value="item.dictValue" v-for="item in distList?.sys_role_lists" :key="item.id">{{
                            item.roleName }}</a-select-option>
                    </a-select>
                </a-descriptions-item>
                <a-descriptions-item label="年龄">{{ formData.age }}</a-descriptions-item>
                <a-descriptions-item label="性别">{{ getDictLabel(distList, 'sys_user_sex', formData.sex)
                }}</a-descriptions-item>
                <a-descriptions-item label="入职日期"> {{ formData.hireDate }}</a-descriptions-item>
                <a-descriptions-item label="离职日期" v-if="formData.terminationDate"> {{ formData.terminationDate }}</a-descriptions-item>
                <a-descriptions-item label="帐号状态">{{ getDictLabel(distList, 'sys_normal_disable', formData.status)
                }}</a-descriptions-item>
                <a-descriptions-item label="工资">{{ formData.salary }}</a-descriptions-item>
                <a-descriptions-item label="手机号">{{ formData.phone }}</a-descriptions-item>
                <a-descriptions-item label="邮箱">{{ formData.email }}</a-descriptions-item>
                <a-descriptions-item label="创建时间">{{ formData.createTime }}</a-descriptions-item>
                <a-descriptions-item label="备注">
                    {{ formData.remark }}
                </a-descriptions-item>
            </a-descriptions>
        </a-drawer>
    </div>
</template>
<script lang="ts" setup>
import { getAssetsImages, getDictLabel } from '@/hooks/publicFunction'; //图片
import { useUserDictData } from '@/hooks/dicts'
import { ref } from 'vue';
import dayjs from 'dayjs';
import type { UnwrapRef } from 'vue';
const props = defineProps<{
    opens: boolean; // 直接声明为 Boolean 类型
    data?: UnwrapRef<search>;
    distList?: UnwrapRef<any>;
}>()
let formData = ref<UnwrapRef<search>>(props.data)
watch(() => props.opens, (newData) => {
    formData.value = props.data;
});
const emit = defineEmits(['close', 'click']);
const pStyle = {
    fontSize: '16px',
    color: 'rgba(0,0,0,0.85)',
    lineHeight: '24px',
    display: 'block',
    marginBottom: '16px',
};
const pStyle2 = {
    marginBottom: '24px',
};

const onClose = () => {
    emit('close');
};
</script>
  
  