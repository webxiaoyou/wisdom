import { ref, reactive, onMounted, watchEffect, watch } from 'vue';
import API from '@/api/index';

export const useUserDictData = (array) => {
  const distList = ref<any>({});

  const fetchData = async () => {
    const resultMap = {};

    await Promise.all(array.map(async (dictType) => {
      const datas = { pageNum: 1, pageSize: 100, dictType }
      const res: any = await API.sys_dict_data_list(datas);
      if (res.code === 200) {
        resultMap[dictType] = res.data.rows;
      } else {
        resultMap[dictType] = null;
      }
    }));

    distList.value = resultMap;
  };
  watch(
    () => distList.value, // 监听 distList.value 的变化防止数据未加载
    (newValue, oldValue) => {
      // 在 distList 变化时执行的逻辑
      distList.value = newValue

    }
  );
  onMounted(() => {
    fetchData();
  });


  return {
    distList,
  };
};
