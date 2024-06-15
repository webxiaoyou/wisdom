import store from '@/store';
import base from "@/config/baseUrl";
import QQMapWX from '@/plugins/qqmap-wx-jssdk.js';
import { getAppLatLon} from '@/plugins/utils';

//檢查小程序更新
function mpUpData(callback){
    if (uni.getUpdateManager) {
        const updateManager = uni.getUpdateManager();
        updateManager.onCheckForUpdate(function(res) {
            // 请求完新版本信息的回调
            // console.log(res.hasUpdate);
            // if(!res.hasUpdate){
            //     uni.showToast({
            //         title: '当前没有新版发布~',
            //         duration: 2000
            //     });
            // }
            let info = {
                type: 1,
                data: res
            }
            callback && callback(info)
        });
        updateManager.onUpdateReady(function(res) {
            uni.showModal({
                title: "更新提示",
                content: "检测到新版本，是否下载新版本并重启小程序？",
                success(res) {
                    if (res.confirm) {
                        // 新的版本已经下载好，调用 applyUpdate 应用新版本并重启
                        updateManager.applyUpdate();
                    }
                }
            });
        });
        updateManager.onUpdateFailed(function(res) {
            // 新的版本下载失败
            uni.showModal({
                title: "已经有新版本了哟~",
                content: "新版本已经上线啦~，请您删除当前小程序，重新搜索打开哟~",
                showCancel: false
            });
        });
    }else {
        // 如果希望用户在最新版本的客户端上体验您的小程序，可以这样子提示
        // uni.showModal({
        //     title: '提示',
        //     content: '当前微信版本过低，无法使用该功能，请升级到最新微信版本后重试。'
        // })
        let info = {
            type: 2
        }
        callback && callback(info)
    }
}

// App计算缓存
function formatSize(callback){
    let fileSizeString = "0B";
    plus.cache.calculate(function(size) {
        let sizeCache = parseInt(size);
        if (sizeCache == 0) {
            fileSizeString = "0B";
        } else if (sizeCache < 1024) {
            fileSizeString = sizeCache + "B";
        } else if (sizeCache < 1048576) {
            fileSizeString = (sizeCache / 1024).toFixed(2) + "KB";
        } else if (sizeCache < 1073741824) {
            fileSizeString = (sizeCache / 1048576).toFixed(2) + "MB";
        } else {
            fileSizeString = (sizeCache / 1073741824).toFixed(2) + "GB";
        }
        callback && callback(fileSizeString)
    });
}
/*
 *  App清理缓存
 *  fileSizeString:当前缓存
 */
function clearCache(fileSizeString){
    if(fileSizeString == '0B'){
        uni.showToast({
            title: '暂无缓存~',
            duration: 2000,
            icon: 'none'
        });
        return
    }
    return new Promise((resolve, reject) => {
        let os = plus.os.name;
        if (os == 'Android') {
            let main = plus.android.runtimeMainActivity();
            let sdRoot = main.getCacheDir();
            let files = plus.android.invoke(sdRoot, "listFiles");
            let len = files.length;
            for (let i = 0; i < len; i++) {
                let filePath = '' + files[i]; // 没有找到合适的方法获取路径，这样写可以转成文件路径 
                plus.io.resolveLocalFileSystemURL(filePath, function(entry) {
                    if (entry.isDirectory) {
                        entry.removeRecursively(function(entry) { //递归删除其下的所有文件及子目录 
                            uni.showToast({
                                title: '缓存清理完成',
                                duration: 2000
                            });
                            // 回调
                            resolve()
                        }, function(e) {
                            console.log(e.message)
                        });
                    } else {
                        entry.remove();
                    }
                }, function(e) {
                    console.log('文件路径读取失败')
                });
            }
        } else { // ios 
            plus.cache.clear(function() {
                uni.showToast({
                    title: '缓存清理完成',
                    duration: 2000
                });
                // 回调
                resolve()
            });
        }
    });
}

//e 扫码参数
//flag uni.scanCode方法点击扫码的参数没有q，为true转换参数
function scene(e,callback,flag=false){
    store.commit("setChatScenesInfo", {});//先请空
    console.log(e,'scene')
    var qrCodeValue = ''
    // #ifndef MP-ALIPAY
    if(flag){
        e.query = {q:e.result}
        console.log(e,'scene-转换后')
    }
    if(e.query.q){
        let scene = decodeURIComponent(e.query.q).split("?")[1];
            scene = scene.split("&");
        let data = {
        	//场景值
        	scene: e.scene,
        };
        scene.forEach(item => {
        	let arr = item.split("=");
        	if (arr.length == 2) {
        		data[arr[0]] = arr[1];
        	}
        });
        store.commit("setChatScenesInfo", Object.assign(e.query, data));
        console.log(store.state.chatScenesInfo,'scene--解码参数')
    }else{
        store.commit("setChatScenesInfo", Object.assign(e.query, {path:e.path}));
    }
    // #endif
    // #ifdef MP-ALIPAY
    var AliqrCode = {}
    if(flag){
        e.query = {qrCode:e.result}
        console.log(e,'scene-转换后')
    }
    if (e.query && e.query.qrCode) {
        AliqrCode.q = e.query.qrCode;
    } else if (e.query && !e.query.qrCode) {
        AliqrCode = e.query;
    }
    if (AliqrCode.q) {
        var queryParam = AliqrCode.q.split("?")[1] //二维码清除域名
        var scene = queryParam.split("&");
        let data = {};
        scene.forEach(item => {
        	let arr = item.split("=");
            if (arr.length == 2) {
            	data[arr[0]] = arr[1];
            }
        });
        store.commit("setChatScenesInfo", Object.assign(AliqrCode, data));
        console.log(store.state.chatScenesInfo,'scene--支付宝小程序解码参数')
    }else{
        store.commit("setChatScenesInfo", Object.assign(AliqrCode, {path:e.path}));
    }
    // #endif
    callback && callback()
}

/*
 * 获取定位信息
 * successCallback:成功回调
 * errCallback:失败回调
 * isOpenSetting:是否检验授权 默认不检验
 */
function loGetLocation(successCallback, errCallback, isOpenSetting=false){
    var that = this
    getAppLatLon(item=>{
        if(base.mapData?.key){
            var latitude = item.latitude; // 纬度，浮点数，范围为90 ~ -90
            var longitude = item.longitude; // 经度，浮点数，范围为180 ~ -180
            var qqmapsdk = new QQMapWX({
                    key: base.mapData?.key, // 您的key---秘钥key值可通过https://lbs.qq.com/qqmap_wx_jssdk/index.html申请
                    // #ifdef H5
                    vm: that
                    // #endif
                });
            // 地址逆解析可获取省市区等信息
            qqmapsdk.reverseGeocoder({
                location: { latitude: latitude, longitude: longitude },
                sig: base.mapData?.sk,
                success: function(res) {
                    store.commit('setLocateInformation',res.result)
                    successCallback && successCallback(res.result)
                },fail: function(err) {
                    item.location = {
                        lat:latitude,
                        lng:longitude,
                    }
                    store.commit('setLocateInformation',item)
                    successCallback && successCallback(item)
                }
            });
        }else{
            item.location = {
                lat:item.latitude,
                lng:item.longitude,
            }
            store.commit('setLocateInformation',item)
            successCallback && successCallback(item)
        }
    },err=>{
        store.commit('setLocateInformation',{})
        errCallback && errCallback(err)
    },isOpenSetting)
}
// 选择地址
function choiseRegion(callback) {
    uni.chooseLocation({
        success: function(res) {
            callback && callback(res)
        },
        fail: function() {
			if(err.errMsg=='chooseLocation:fail cancel'){
				return
			}
            uni.getSetting({
                success: function(res) {
                    if (res.authSetting['scope.userLocation']) {
                        uni.showModal({
                            title: '是否授权当前位置',
                            content: '需要获取您的地理位置，请确认授权，否则地图功能将无法使用',
                            success: function(tip) {
                                if (tip.confirm) {
                                    uni.openSetting({
                                        success: function(data) {
                                            if (data.authSetting["scope.userLocation"] === true) {
                                                uni.$u.toast('授权成功~');
                                                //授权成功之后，再调用chooseLocation选择地方
                                                choiseRegion()
                                            } else {
                                                uni.$u.toast('授权失败~');
                                            }
                                        }
                                    })
                                }
                            }
                        })
                    }
                },
                fail: function(res) {
                    uni.$u.toast('调用授权窗口失败~');
                }
            })
        }
    })
}

export {
    mpUpData,         //小程序更新
    formatSize,       //App计算缓存
    clearCache,       //App清理缓存
    scene,            //扫码信息
    loGetLocation,    //获取定位信息
    choiseRegion,    //选择地址
}