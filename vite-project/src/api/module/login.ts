import Http, { Axios } from '@/utils/axios';

enum Api {
    Login = '/login',
    SYS_USER_INFO = '/getInfo',
    SYS_USER_LOGOUT = '/logout'
    
    // yam = '/bwmes-boot/sys/randomImage/'
}
// 可自定义头部
// const headers={
//     'Content-Type': 'application/json;charset=UTF-8',
// }
// export const login = (data, params) => {
//     return Axios.get(Api.yam + data.times, params);
// }
// export const login = (data: LoginParams) => {
//     return Axios.post(Api.Login, data),headers;
// };
export const sys_login = (data: LoginParams) => {
    return Axios.post(Api.Login, data);
};

export const sys_userInfo = () => {
    return Axios.get(Api.SYS_USER_INFO);
};

export const sys_user_logout = () => {
    return Axios.post(Api.SYS_USER_LOGOUT);
};