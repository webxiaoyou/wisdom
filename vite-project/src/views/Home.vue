<template>
  <div>
    <a-row v-if="!details || Object.keys(details)?.length !== 0">
      <a-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <div class="m-card">
          <a-card :loading="loading" style="width: 385px;height: 250px;" v-for="item in mCardList" :key="item.id"
            @click="toRouter(item.id)"
            v-hasPermi="item.id == 1 ? ['tenement:prepair:list'] : ['tenement:pproperty_unit:list']">
            <div class="card-style">
              <div class="title">
                <h3>{{ item.name || 0}}</h3>
                <h1>{{ item.num || 0}}个</h1>
              </div>
              <div class="right-img" :style="{ width: item.width, right: item.right }">
                <img :src="getAssetsImages(item.img)" alt="">
              </div>
            </div>
          </a-card>
        </div>
      </a-col>
      <a-col :xs="24" :sm="24" :md="19" :lg="24" :xl="11">
        <div class="suggestion" v-hasPermi="['tenement:pcomplaint_suggestion:list']">
          <a-card :loading="loading" title="投诉建议" style="height: calc(100vh - 440px);">
            
            <template v-if="details?.suggestion && details.suggestion.length !== 0">
              <template v-for="item in details.suggestion" :key="item.id">
                <div class="flex-row flex-spaceB details">
                  <div class="left">
                    <a-tag color="processing">{{ getSingleDictLabel(distList, 'pproperty_dict', item.propertyId)
                    }}</a-tag>
                    <a-tag :color="item.isSuggest == 0 ? 'error' : 'warning'">{{ item.isSuggest == 0 ? '投诉' : '建议' }}</a-tag>
                    <a-popover placement="topLeft">
                      <template #content>
                        {{ item.content }}
                      </template>
                      {{ item.content }}
                    </a-popover>
                  </div>
                  <div class="right flex-row">
                    <div style="margin-right: 10px;"> <a-button @click="onStatus(item.id)" size="small" type="dashed"
                        style="color: #3F7BFD;" block>已读</a-button></div>{{ dayjs(item.createTime).format('YYYY-MM-DD') }}
                  </div>
                </div>
                <a-divider style="margin-top: 10px;" />
              </template>
            </template>
            <a-empty :image="simpleImage" v-else />

          </a-card>
        </div>

      </a-col>

    </a-row>
    <div v-else class="flex-row flex-align-center flex-justify-center"
      style="width: 100%; min-height: calc(100vh - 200px);"><a-empty
        :image="getAssetsImages('login/holographic-screen.gif')" :image-style="{
          height: '400px', width: '60%'
        }">
        <template #description>
          <span class="cont">欢迎来到</span>
          <span class="cont" style="color: #3F7BFD; margin: 0 2px;font-weight: bold;">智慧管家</span>
          <span class="cont">物业系统</span>
        </template>
      </a-empty></div>
  </div>
</template>

<script setup lang="ts" name="home">
import { getAssetsImages, getDictLabel, getSingleDictLabel } from '@/hooks/publicFunction'; // 导入必要的函数
import { reactive, toRefs, onMounted, getCurrentInstance } from 'vue'
import { useRouter } from 'vue-router';
import dayjs from 'dayjs';
import { message } from 'ant-design-vue';
import API from '@/api/index';
import { useUserStore } from '@/store/modules/user'
import { useUserDictData } from '@/hooks/dicts';
import { Empty } from 'ant-design-vue';

let { distList } = useUserDictData([]);  //字典数据
// 获取 Vue Router 实例
const router = useRouter();
let state = useUserStore().openKeys;
const simpleImage = Empty.PRESENTED_IMAGE_SIMPLE;
const mCardList = reactive([
  {
    id: 1,
    name: '报修',
    num: 0,
    img: 'index/repairs.svg',
    width: '12.5rem',
    right: '0px'
  },
  {
    id: 2,
    name: '业主',
    num: 0,
    img: 'index/interflow.svg',
    width: '320px',
    right: '-35px'

  }
])
let details = ref<any>({});
const loading = ref(true);
//获取角色列表
const getIndex = async () => {
  let { data } = await API.Index();
  details.value = data;
  mCardList[0].num = data?.repair?.length;
  mCardList[1].num = data?.propertyUnit?.length;
  loading.value = false;

}
// 查询小区字典详情
const dictList = async () => {
  const datas: any = await API.sys_pproperty_dict_id();
  const { code, data, msg } = datas;
  if (code === 200) {
    distList.value.pproperty_dict = data
  } else {

  }
}
//编辑
const onUpdate = async (formStates: PComplaintSuggestion) => {
  const datas: any = await API.tmt_pcomplaint_up(formStates);
  const { code, msg } = datas
  if (code === 200) {
    getIndex();
    message.success('信息已阅读')
  } else {
    message.error(msg)
  }
}
//点击修改投诉建议内容
const onStatus = (id: number) => {
  let formStates = {
    id: id,
    status: '0'
  }
  onUpdate(formStates);
}
//点击去往对应页面
const toRouter = (id: number) => {

  if (id === 1) {
    state.selected = ["/tenement/prepair"];
    state.openKeys = ["", "system", "tenement"];
    router.push('/tenement/prepair')
  } else if (id === 2) {
    state.selected = ["/tenement/ppropertyUnit"];
    state.openKeys = ["", "system", "tenement"];
    router.push('/tenement/ppropertyUnit')
  }
}

onMounted(() => {
  getIndex();
  dictList()
})


</script>
<style scoped lang="less">
.cont {
  font-size: 20px;
}

.m-card {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;

  .card-style {
    position: relative;

    .title {
      position: absolute;

      h1 {
        color: #3F7BFD;
      }
    }

    .right-img {
      position: absolute;
      right: 0;


      // height: 100px;
      img {
        width: 100%;
        height: 100%;
      }
    }
  }
}

.suggestion {
  margin-top: 40px;

  .details {
    color: rgb(141, 141, 141);

    .left {
      width: 70%;
      overflow: hidden;
      white-space: nowrap;
      text-overflow: ellipsis;
    }

  }
}
</style>