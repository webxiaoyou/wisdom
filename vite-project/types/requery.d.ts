// 在这里定义了一个接口，用于扩展 AxiosRequestConfig，使其包含了额外的 headers 字段
interface IHttpConfig extends AxiosRequestConfig {
  // headers 是一个对象，其中键值对表示请求头的名称和对应的值
  headers?: Record<string, string>;
  baseURL?: string;
  timeout?: number;
}

interface IResponseType<P = {}> {
  code?: number;
  msg?: string;
  data: P;
}

interface IconSelectOption {
  label?: string;
  component: string | Component;
  value: string | number;
}
interface IconSelectGroup {
  title: string;
  key: string | number;
  list: IconSelectOption[];
}

interface FormColumns {
  title?: string,   //默认参数
  dataType: string,  //显示输入框的类型 如：input、select、等输入框类型
  dataTypename?: string, //输入流的输入类型 如是 string还是 number 类型参数 【dataTypename 废弃】
  name?: string,  // value 名称 输入框接收输入参数名
  keyField?: string,  //选择器Key
  labelField?: string, //选择器名称
  defaultValue?: string | number,  //输入框默认参数
  options?: any,  //下来的数据
  children?:string,  //子内容
  disabled?:boolean, //禁止输入
  rules?: Record<Rule[]>,  //权限管理  用户输入的内容 如[{ required: true, message: '不能为空', trigger: 'change' },]
  mode?:any,  //设置 Select 的模式为多选或标签	'multiple' | 'tags' | 'combobox'
  max?:number, //设置输入框最大值
  min?:number, //设置输入框最小值
  rows?:number, //设置文本输入的长度
  upload?:UploadColumns,  //文件上传
  search?:boolean,  //是否显示搜索框

  xs?: number | string;
  sm?: number | string;
  md?: number | string;
  lg?: number | string;
  xl?: number | string;
  labelCol?: object; //label 标签布局，同 <Col> 组件，设置 span offset 值，如 {span: 3, offset: 12} 或 sm: {span: 3, offset: 12}
}

interface UploadColumns {
  limit?:number,   // 图片数量限制
  fileSize?:number, // 大小限制(MB)
  fileType?:Array<string> // 文件类型, 例如['png', 'jpg', 'jpeg']
  multiple?:boolean, // 是否多选
  drag?:boolean, // 是否运行拖拽
  isShowTip?:boolean,  // 是否显示提示
  fileListData?:object //指定fileList返回的数据 例如 {  url:'', ossId:'',}
}


