<template>
    <a-drawer height="80%" placement="top" :closable="false" :open="opens" @close="onClose">

        <a-row>
            <a-col :xs="0" :sm="0" :md="0" :lg="0" :xl="6"></a-col>
            <a-col :xs="24" :sm="24" :md="24" :lg="24" :xl="12">
                <h1>{{ title }}</h1>
                <a-form ref="formRefs" :model="formState" :rules="rules">
                    <a-row>
                        <a-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12">
                            <a-form-item ref="propertyId" label="物业小区" name="propertyId" :labelCol="labelCol">
                                <a-select placeholder="请选择物业小区" v-model:value="formState.propertyId" @change="propertyHandleChange" :disabled="userStore?.userInfo?.deptId">
                                    <a-select-option :value="item.value" v-for="item in distList?.pproperty_dict"
                                        :key="item.value">{{
                                            item.label }}</a-select-option>
                                </a-select>
                            </a-form-item>
                          
                          
                            <!-- <a-form-item ref="notificationType" label="公告类型" name="notificationType" :labelCol="labelCol">
                                <a-select placeholder="请选择公告类型" v-model:value="formState.notificationType"  >
                                    <a-select-option :value="item.dictValue" v-for="item in distList?.sys_notice_type"  
                                        :key="item.dictValue">{{
                                            item.dictLabel }}</a-select-option>
                                </a-select>
                            </a-form-item> -->
                            <a-form-item ref="userId" label="用户选择" name="userId" :labelCol="labelCol">
                                <a-select placeholder="请选择用户"  show-search v-model:value="formState.userId">
                                    <a-select-option :value="item.value" v-for="item in unitDictList"
                                        :key="item.value">{{
                                            item.label }}</a-select-option>
                                </a-select>
                            </a-form-item>
                           
                            <a-form-item ref="floorId" label="报修房间" name="floorId"  :labelCol="labelCol">
                                <a-select placeholder="请选择报修房间"  show-search v-model:value="formState.floorId" disabled>
                                    <a-select-option :value="item.value" v-for="item in buildings"
                                        :key="item.value">{{
                                            item.label }}</a-select-option>
                                </a-select>
                            </a-form-item>
                          
                            <!-- <a-form-item ref="propertyId" label="物业小区" name="propertyId" :labelCol="labelCol">
                                <a-select placeholder="请选择物业小区" v-model:value="formState.propertyId" @change="handleChange" :disabled="userStore?.userInfo?.deptId">
                                    <a-select-option :value="item.value" v-for="item in distList?.pproperty_dict"
                                        :key="item.value">{{
                                            item.label }}</a-select-option>
                                </a-select>
                            </a-form-item> -->
                        </a-col>
                        <a-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12">
                        
                            <a-form-item label="手机号" name="tel" :labelCol="labelCol">
                                <a-input v-model:value="formState.tel" placeholder="请输入通知标题" />
                            </a-form-item>
                            <a-form-item ref="buildingId" label="楼栋选择" name="buildingId"  :labelCol="labelCol">
                                <a-select placeholder="请选择楼栋"  show-search v-model:value="formState.buildingId" disabled>
                                    <a-select-option :value="item.value" v-for="item in buildings"
                                        :key="item.value">{{
                                            item.label }}</a-select-option>
                                </a-select>
                            </a-form-item>
                            <a-form-item ref="buildingId" label="报修类型" name="buildingId"  :labelCol="labelCol">
                                <a-select placeholder="请选择报修类型"  show-search v-model:value="formState.category" disabled>
                                    <a-select-option :value="item.value" v-for="item in buildings"
                                        :key="item.value">{{
                                            item.label }}</a-select-option>
                                </a-select>
                            </a-form-item>
                        </a-col>
                        <a-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" style="text-align: right">
                            <!-- <a-form-item ref="content" label="通知内容" name="content" :labelCol="{ span: 3 }">
                                <Editor v-model="formState.content" />
                            </a-form-item> -->
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
import dayjs from 'dayjs';
import { useUserStore } from '@/store/modules/user';
import { Dayjs } from 'dayjs';
import { message } from 'ant-design-vue';
import API from '@/api/index';
const props = defineProps<{
    opens: boolean; // 直接声明为 Boolean 类型
    data?: UnwrapRef<PRepair>;
    title: string;
    distList?: UnwrapRef<any>;
}>()
const userStore:any = useUserStore();
const dateFormat = 'YYYY-MM-DD HH:mm:ss';
let value4 = ref<Dayjs>();  //时间
let buildings= ref<Array<any>>();  //栋数内容
let unitDictList= ref<Array<any>>();  //栋数内容
const formState = toRef(props, 'data');
watch(() => props.opens, (newData) => {
    formState.value = props.data;
       // 更新 formState
       if(userStore?.userInfo?.deptId){
        formState.value.propertyId =userStore?.userInfo?.deptId;
    }
    // if (formState.value.hireDate) {
    //     formState.value.hireDate = dayjs(props.data.hireDate, dateFormat);
    // }
    // if (formState.value.terminationDate) {
    //     formState.value.terminationDate = dayjs(props.data.terminationDate, dateFormat);
    // }

});

const emit = defineEmits(['close', 'submit']);
const labelCol = { span: 6 };
const wrapperCol = { span: 13 };

const rules: Record<string, Rule[]> = {
    title: [
        { required: true, message: '请输入标题', trigger: 'change' },
    ],
    targetType: [
        { required: true, message: '请选择接收对象', trigger: 'change' },
    ],

    notificationType: [
        { required: true, message: '请选择公告类型', trigger: 'change' },
    ],
    targetId: [
        { required: true, message: '请选择用户对象', trigger: 'change' },
    ],
    propertyId: [
        { required: true, message: '请选择物业小区', trigger: 'change' },
    ],
    content: [
        { required: true, message: '请输入内容', trigger: 'change' },
    ],
};
onMounted(() => {
});


// 查询物业小区
const propertyHandleChange = (value: string) => {

        // formState.value.targetId = null;
        // formState.value.targetType = null;
        GetUnitDictlist(formState.value.propertyId);


};
//楼栋选择
const handleChange = (value: string) => {
    if(formState.value.propertyId && value === '0'){
        GetPbuildingDictlist(formState.value.propertyId); //查询所在栋数

    }else if(formState.value.propertyId && value === '1'){
    GetUnitDictlist(formState.value.propertyId);
    }
    
    
    else{
        message.error('请先选择物业小区');
        return
    }

};
const onRangeChange = (value: Dayjs, dateString: string) => {
    // formState.value.hireDate = dayjs(dateString, dateFormat);
};
const onRangeChange1 = (value: Dayjs, dateString: string) => {
    // if (dateString) {
    //     formState.value.terminationDate = dayjs(dateString, dateFormat);
    // } else {
    //     formState.value.terminationDate = null;

    // }

};
//事件存放 查询楼栋信息
const GetPbuildingDictlist = async (propertyId) => {
  // 创建一个新的对象，排除 dateRange 字段
  const data: any = await API.tmt_pbuilding_dict_id({propertyId})
  buildings.value = data.data

}
// 查询业主信息
const GetUnitDictlist = async (propertyId) => {
  // 创建一个新的对象，排除 dateRange 字段
  const data: any = await API.tmt_pproperty_unit_dict_list({
    isBuilding: false,
    propertyId:propertyId
  })
  unitDictList.value = data.data
}

const formRefs = ref(null);
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
  
  