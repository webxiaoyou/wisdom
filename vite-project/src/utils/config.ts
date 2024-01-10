const env = process.env.NODE_ENV;
interface IIP {
    baseIP: string;
    homeIP: string;
}
type IP={
    development:IIP,
    production:IIP,
    test:IIP
}
const IPS:IP ={
    
    development: {  //开发
        baseIP: 'http://localhost:8989/wisdom/admins',
        // baseIP: 'http://nmyzm.tmp622.shiningsoft.cn:33903',
        homeIP: '',
    },
    production:{  //生产 
        baseIP: 'http://www.webxiaoyou.top:8989/wisdom/admins',
        homeIP: ''
    },
    test:{  //测试
        baseIP: '',
        homeIP: '',
    },
}
export default IPS[env];