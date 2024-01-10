import API from '@/api/index';
import { saveAs } from 'file-saver'
import { watch, computed, ref } from 'vue';
// 获取静态图片路径
export const getAssetsImages = (name) => {
    return new URL(`../assets/${name}`, import.meta.url).href;
};

//将字符串转换成数字字符
export const convertToNumber = (value: string | number): number => {
    return typeof value === 'string' ? Number(value) : value;
};


// 获取字典值对应的标签
/**
 * 
 * @param distList 数组数据
 * @param dictType 类型名称
 * @param dictValue 所输入的key
 * @returns 
 */
export const getDictLabel = (distList, dictType, dictValue) => {
    const dict = distList[dictType] || [];
    const item = dict.find(item => item.dictValue === dictValue);
    return item ? item.dictLabel : dictValue;
};

// 获取单表字典值对应的标签
/**
 * 
 * @param distList 数组数据
 * @param dictType 类型名称
 * @param dictValue 所输入的key
 * @returns 
 */
export const getSingleDictLabel = (distList, dictType, dictValue) => {
    const dict = distList[dictType] || [];
    const item = dict.find(item => item.value === dictValue);
    return item ? item.label : dictValue;
};

export const useModelValue = <T>(value: () => T | undefined, onChange: (val?: T) => void, defaultValue?: T) => {
    const _value = ref<T>();
    _value.value = value() ?? defaultValue;
    const sValue = computed({
        get() {
            return value() ?? _value.value;
        },
        set(val: T | undefined) {
            _value.value = val;
            onChange(val);
        },
    });
    watch(value, () => {
        _value.value = value();
    });
    return { value: sValue };
}

/**
 * 下载
 * @param ossId 
 */
export const download = async (ossId) => {
    const datas: any = await API.sys_file_downlad(ossId)
    if (blobValidate(datas.data)) {
        const blob = new Blob([datas.data], { type: 'application/octet-stream' })
        saveAs(blob, decodeURIComponent(datas.headers['download-filename']))
    }
}

// 验证是否为blob格式
export const blobValidate = (data) => {
    return data.type !== 'application/json'
}

/**
 * 计算总页数
 * @param totalCount 
 * @param pageSize 
 * @param currentPage 
 * @param count 
 * @returns 
 */
export const calculatePageCount = (totalCount, pageSize, currentPage, count) => {
    const totalPage = Math.ceil((totalCount - count) / pageSize) // 总页数
    const currentPages = currentPage > totalPage ? totalPage : currentPage
    return currentPages < 1 ? 1 : currentPages
}
// export default useModelValue;

//电话号码校验
export const phoneCheck = (rule, value) => {
    return new Promise<void>((resolve, reject) => {
        const reg = /^(\d{11})$|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$/
        if (!reg.test(value)) {
            reject('手机号码格式不正确');
        } else {
            resolve();
        }
    });
};