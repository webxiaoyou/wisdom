// 传递用户登录参数
interface LoginParams {
  account: string;
  password: number | string;
}

/**
 * SysUserVo
 */
interface search {
  /**
   * 账号
   */
  account?: string;
  /**
   * 年龄
   */
  age?: number;
  /**
   * 创建者
   */
  createBy?: string;
  /**
   * 手机号
   */
  phone?: number | string;
  /**
   * 角色权限
   */
  roleIds?: number[];
  /**
   * 用户邮箱
   */
  email?: string;
  /**
   * 用户ID
   */
  id?: number;
  /**
   * 最后登录时间
   */
  loginDate?: Date;
  /**
   * 最后登录IP
   */
  loginIp?: string;
  /**
   * 姓名
   */
  name?: string;
  /**
   * 页数
   */
  pageNum?: number;
  /**
   * 每页大小
   */
  pageSize?: number;
  /**
   * 电话
   */
  phone?: string;
  /**
   * 职位
   */
  position?: string;
  /**
   * 备注
   */
  remark?: string;
  /**
   * 角色 0超级管理员 ， 1管理员， 2 普通账号
   */
  roleId?: number;
  /**
   * 工资
   */
  salary?: number;
  /**
   * 性别(0男 1女 2未知)
   */
  sex?: string;
  /**
   * 帐号状态（0正常 1停用）
   */
  status?: string;
  /**
   * 上级员工ID
   */
  supervisorId?: number;

  /**
   * 更新者
   */
  updateBy?: string;

  /**
   * 工作地点
   */
  workLocation?: string;
  [property: string]: any;
}

/**
 * SysMenu对象，菜单权限表
 */
interface SysMenu {
  children?: SysMenu[];
  /**
   * 组件路径
   */
  component?: string;
  /**
   * 创建者
   */
  createBy?: string;
  /**
   * 创建时间
   */
  createTime?: Date;
  delFlag?: string;
  /**
   * 菜单图标
   */
  icon?: string;
  /**
   * 菜单ID
   */
  id?: number;
  /**
   * 是否为外链（0是 1否）
   */
  isFrame?: number;
  /**
  * 是否缓存（0是 1否）
  */
  isCache?: number;
  /**
   * 菜单名称
   */
  menuName?: string;
  /**
   * 菜单类型（M目录 C菜单 F按钮）
   */
  menuType?: string;
  /**
   * 显示顺序
   */
  orderNum?: number;
  /**
   * 父菜单ID
   */
  parentId?: number;
  /**
   * 路由地址
   */
  path?: string;
  /**
   * 权限标识
   */
  perms?: string;
  /**
   * 备注
   */
  remark?: string;
  /**
   * 菜单状态（0正常 1停用）
   */
  status?: string;
  /**
   * 更新者
   */
  updateBy?: string;
  /**
   * 更新时间
   */
  updateTime?: Date;
  /**
   * 菜单状态（0显示 1隐藏）
   */
  visible?: number;
}

interface SysRole {
  /**
* 页数
*/
  pageNum?: number;
  /**
   * 每页大小
   */
  pageSize?: number;
  /**
 * 创建时间
 */
  createTime?: null;
  /**
* 更新时间
*/
  updateTime?: null;
  delFlag?: string;
  id?: string;
  remark?: string;
  roleKey?: string;
  roleName?: string;
  roleSort?: number;
  status?: string;
  menuIds?: number[] | string[];
  params?:any
}


/**
* DictTypeDto
*/
interface DIctType {
  dictId?: string;
  /**
   * 创建时间
   */
  createTime?: null;
  /**
   * 字典名称
   */
  dictName?: string;
  /**
   * 字典类型
   */
  dictType?: string;
  /**
   * 页数
   */
  pageNum?: number;
  /**
   * 每页大小
   */
  pageSize?: number;
  /**
   * 状态（0正常 1停用）
   */
  status?: string;
  /**
   * 更新时间
   */
  updateTime?: null;
    /**
   * 备注
   */
  remark?: string;
  params?:any;
}

interface  DictData {
    /**
* 页数
*/
pageNum?: number;
/**
 * 每页大小
 */
pageSize?: number;
  /**
   * 创建者
   */
  createBy?: string;
  /**
   * 创建时间
   */
  createTime?: null;
  /**
   * 样式属性（其他样式扩展）
   */
  cssClass?: string;
  /**
   * 字典编码
   */
  dictCode?: number;
  /**
   * 字典标签
   */
  dictLabel?: string;
  /**
   * 字典排序
   */
  dictSort?: number;
  /**
   * 字典类型
   */
  dictType?: string;
  /**
   * 字典键值
   */
  dictValue?: string;
  /**
   * 是否默认（Y是 N否）
   */
  isDefault?: string;
  /**
   * 表格回显样式
   */
  listClass?: string;
  /**
   * 备注
   */
  remark?: string;
  /**
   * 状态（0正常 1停用）
   */
  status?: string;
  /**
   * 更新者
   */
  updateBy?: string;
  /**
   * 更新时间
   */
  updateTime?: null;
}

interface SysPost {
  /**
   * 创建者
   */
  createBy?: string;
  /**
   * 创建时间
   */
  createTime?: string;
  /**
   * 页数
   */
  pageNum?: number;
  /**
   * 每页大小
   */
  pageSize?: number;
  /**
   * 岗位编码
   */
  postCode?: string;
  /**
   * 岗位ID
   */
  postId?: number;
  /**
   * 岗位名称
   */
  postName?: string;
  /**
   * 显示顺序
   */
  postSort?: number;
  /**
   * 备注
   */
  remark?: string;
  /**
   * 状态（0正常 1停用）
   */
  status?: string;
  /**
   * 更新者
   */
  updateBy?: string;
  /**
   * 更新时间
   */
  updateTime?: string;
}

 interface PConfig {
  /**
   * aeskey
   */
  aeskey?: string;
  /**
   * 身份标识
   */
  appid?: string;
  /**
   * 创建人
   */
  createBy?: string;
  /**
   * 创建时间
   */
  createTime?: string;
  /**
   * 删除标志（0代表存在 2代表删除）
   */
  delFlag?: string;
  /**
   * id
   */
  id?: number;
  /**
   * 是否支付
   */
  isPay?: string;
  pageNum?: number;
  pageSize?: number;
  /**
   * 请求参数
   */
  params?: any[];
  /**
   * 身份秘钥
   */
  secret?: string;
  /**
   * 是否使用
   */
  status?: string;
  /**
   * 配置token
   */
  token?: string;
  /**
   * 类型
   */
  type?: string;
  /**
   * 修改人
   */
  updateBy?: string;
  /**
   * 更新时间
   */
  updateTime?: string;
  /**
   * 微信id
   */
  wid?: number;
}

 interface PUser {
  /**
   * 账号
   */
  account?: string;
  /**
   * 地址
   */
  address?: string;
  /**
   * 是否认证（0已认证 1未认证）
   */
  authenticated?: string;
  /**
   * 头像
   */
  avatar?: string;
  /**
   * 头像ossid
   */
  avatarOssid?: string;
  /**
   * 余额
   */
  balance?: number;
  /**
   * 积分
   */
  cedits?: number;
  /**
   * 城市
   */
  city?: string;
  /**
   * 创建人
   */
  createBy?: string;
  /**
   * 创建时间
   */
  createTime?: string;
  /**
   * 删除标志（0代表存在 1代表删除）
   */
  delFlag?: string;
  /**
   * 身份证
   */
  identity?: string;
  /**
   * 最近登录
   */
  lastLoginTime?: string;
  /**
   * 车牌号
   */
  licensePlate?: string;
  /**
   * 昵称
   */
  nickname?: string;
  /**
   * openid
   */
  openid?: string;
  pageNum?: number;
  pageSize?: number;
  /**
   * 请求参数
   */
  params?: any[];
  /**
   * 密码
   */
  password?: string;
  /**
   * 父id
   */
  pid?: number;
  /**
   * 备注
   */
  remark?: string;
  /**
   * 性别
   */
  sex?: string;
  /**
   * 手机
   */
  tel?: string;
  /**
   * 劵数
   */
  tickets?: number;
  /**
   * unionid
   */
  unionid?: string;
  /**
   * 更新人
   */
  updateBy?: string;
  /**
   * 更新时间
   */
  updateTime?: string;
  /**
   * id
   */
  userId?: number;
  /**
   * 用户姓名
   */
  userName?: string;
  /**
   * 用户类型
   */
  utype?: string;
}


interface PNotification {
  /**
   * 通知内容
   */
  content?: string;
  /**
   * 创建者
   */
  createBy?: string;
  /**
   * 创建时间
   */
  createTime?: string;
  /**
   * 通知ID
   */
  notificationId?: number;
  /**
   * 通知类型
   */
  notificationType?: string;
  pageNum?: number;
  pageSize?: number;
  /**
   * 请求参数
   */
  params?: any[];
  /**
   * 物业项目ID
   */
  propertyId?: number;
  /**
   * 接收对象ID（楼栋ID、楼层ID、用户ID等）
   */
  targetId?: number;
  /**
   * 接收对象类型
   */
  targetType?: string;
  /**
   * 通知标题
   */
  title?: string;
  /**
   * 更新者
   */
  updateBy?: string;
  /**
   * 更新时间
   */
  updateTime?: string;
}