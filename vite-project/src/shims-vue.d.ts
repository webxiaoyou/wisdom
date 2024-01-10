export {}
 
/**
 * 防止注册全局方法的时候报错
 */
declare module 'vue' {
  interface ComponentCustomProperties {
    $hasPermi: any
  }
}