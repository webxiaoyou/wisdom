// import hasRole from './permission/hasRole'
import hasPermiDirective from './permission/hasPermi';
const install = function(Vue) {
  // Vue.directive('hasRole', hasRole)
  Vue.directive('hasPermi', hasPermiDirective);
  Vue.config.globalProperties.$hasPermi = hasPermiDirective.$hasPermi
}

export default install
