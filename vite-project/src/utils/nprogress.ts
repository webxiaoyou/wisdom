// 引入nprogress
// 由于nprogress是第三方插件库，因此需要在src\types\index.d.ts中添加对应的声明
import NProgress from 'nprogress';
import 'nprogress/nprogress.css';
// 全局进度条配置
NProgress.configure({
    easing: 'ease', //动画方式
    speed: 500, //进度条速度
    showSpinner: false, //是否显示加载ico
    trickleSpeed: 200, //自动递增间隔
    minimum: 0.3, //最小百分比
});
// 打开进度条
export const start = () => {
    NProgress.start();
};
// 关闭进度条
export const done = () => {
    NProgress.done();
};
