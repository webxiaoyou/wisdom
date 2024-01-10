import axios, { AxiosResponse } from 'axios';
import ipConfig from './config';
import router from '@/router';
import { useUserStore } from '@/store/modules/user';
import { notification } from 'ant-design-vue';
import { start, done } from '@/utils/nprogress';
import { message } from 'ant-design-vue';
// 在这里定义了一个接口，用于扩展 AxiosResponse，使其包含了额外的 code 和 msg 字段
// eslint-disable-next-line @typescript-eslint/ban-types

const toLogin = () => {
  router.replace('/sys_login');
};
// 处理错误的函数
const errorHandle = (data: any) => {
  const { msg, code } = data;
  switch (code) {
    case 401:
      notification.error({
        message: msg,
        style: {
          color: '#FF1238', // 设置字体颜色
        },
      });
      break;
    case 404:
      notification.error({
        message: msg,
        style: {
          color: '#FF1238', // 设置字体颜色
        },
      });
      break;
    // 这里可以添加其他状态码的处理逻辑
    default:
    // 其他错误处理逻辑
  }
};
interface RedirectedCdoe {
  code?: number;
  msg?: string;
}
// HTTP 请求类
class Http {
  private static isRedirectedCdoe: RedirectedCdoe = {}
  // 默认的配置，包含了一些默认的 headers
  private config: IHttpConfig = {
    headers: {
      Accept: 'application/json',
      Authorization: null,
    },
  };


  // 构造函数，接受一个配置对象作为参数，可以用来配置一些默认值
  constructor(config: IHttpConfig = {}) {
    // 合并默认配置和传入的配置
    this.config = { ...this.config, ...config };
  }



  // 私有方法，用于发送请求
  private request<T>(method: string, url: string, params = {}, headers = {}): Promise<AxiosResponse<T>> {
    start();
    const requestConfig: IHttpConfig = {
      ...this.config,
      headers: { ...this.config.headers, ...headers },
    };
    // 获取 token，假设你的 token 存储在某个地方，例如 localStorage
    // const token = localStorage.getItem('token');
    const userStore = useUserStore();
    // 设置 Authorization 头部
    if (userStore.token) {

      requestConfig.headers['Authorization'] = `Bearer ${userStore.token}`;
    }
    // 使用 axios.request 发送请求
    return axios.request({
      method,
      url,
      [method.toLowerCase() === 'get' ? 'params' : 'data']: params,
      ...requestConfig,
    });
  }

  // 发送 GET 请求
  get<T>(url: string, params = {}, headers = {}): Promise<AxiosResponse<T>> {
    return this.request<T>('GET', url, params, headers);
  }

  // 发送 POST 请求
  post<T>(url: string, params = {}, headers = {}): Promise<AxiosResponse<T>> {
    return this.request<T>('POST', url, params, headers);
  }

  // 发送 PUT 请求
  put<T>(url: string, params = {}, headers = {}): Promise<AxiosResponse<T>> {
    return this.request<T>('PUT', url, params, headers);
  }

  // 发送 DELETE 请求
  delete<T>(url: string, headers = {}): Promise<AxiosResponse<T>> {
    return this.request<T>('DELETE', url, {}, headers);
  }

  // 下载专用链接 GET 请求
  async  download<T>(url: string, params = {}, headers:any = {}) : Promise<AxiosResponse<Blob>>{
    // 创建新的 axios 实例，避免使用全局拦截器
    const axiosDownload = axios.create({
      baseURL: ipConfig.baseIP,
      timeout: 5000,
    });

    const userStore = useUserStore();
    const downloadHeaders = { 
      headers: {  ...headers },
      Authorization: `Bearer ${userStore.token}`,
    };
   // const token = localStorage.getItem('token');
   // 设置 Authorization 头部
  //  if (userStore.token) {
  //   downloadHeaders.headers['token'] = `${userStore.token}`;
  //  }
   try {
    const response: AxiosResponse<Blob> = await axiosDownload.get(url, {
      params,
      headers : downloadHeaders,
      responseType: 'blob',
    });

    return response;
  } catch (error) {
    // 处理下载过程中的错误
    message.error('下载失败，请重试');
    throw new Error(`Error during file download: ${error}`);
  }
  }
}

// 导出 HTTP 请求类
export default Http;

// 创建一个 Axios 实例，使用时可以直接导入该实例
export const Axios = new Http({
  baseURL: ipConfig.baseIP,
  timeout: 5000,
});

let errorHandlePromise: Promise<void> | null = null;
// // 响应拦截
axios.interceptors.response.use(
  async (response) => {
    const userStore = useUserStore();
    const { data, headers } = response;
    if (data.code == 401) {
      userStore.staleDated();
      // 如果errorHandlePromise为null，表示还没有处理过401，执行errorHandle
      if (!errorHandlePromise) {
        errorHandlePromise = new Promise<void>((resolve) => {
          errorHandle(data);
          resolve();  // 标记Promise为已完成
        })
      }
      // 跳转到登录页
      toLogin();
      return data;
    }
    done();
    errorHandlePromise = null;  // 处理完毕后重置为null，以便下一次处理401
    return data;
  },
  (error) => {
    done();
    errorHandle({ code: 404, msg: '后端网络异常' })
    return Promise.reject('网络错误');
  }
);

