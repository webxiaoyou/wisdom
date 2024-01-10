import Http, { Axios } from '@/utils/axios';

/**
 * 登录
 */
// interface IResponseType<P = {}> {
//     code?: number;
//     status: number;
//     msg: string;
//     data: P;
// }
// 传递页数登录参数
interface menu {
    limit: [string, number];
    page: [string, number];
}
enum Api {
    Menu = '/getRouters', //左侧菜单
    AdminList = '/v1.Admin/list', //获取管理员用户列表
    AdminListDetele = '/v1.Admin/delete', //删除管理员用户
    AdminListAdd = '/v1.Admin/save', //新增管理员用户
    AdminListUpdate = '/v1.Admin/update', //编辑管理员用户
}
export const Menu = () => {
    return Axios.get(Api.Menu);
};
export const AdminList = (data: menu) => {
    return Axios.post(Api.AdminList, data);
};
export const AdminListDetele = (data: [String, Number]) => {
    return Axios.post(Api.AdminListDetele, data);
};
export const AdminListAdd = (data: any) => {
    return Axios.post(Api.AdminListAdd, data);
};
export const AdminListUpdate = (data: any) => {
    return Axios.post(Api.AdminListUpdate, data);
};
