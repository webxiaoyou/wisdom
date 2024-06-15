import Vue from 'vue';
// #ifdef APP-PLUS
import {judgePermission} from './permission'
// #endif
//金额过滤
Vue.filter('money', function(val) {
	if (val) {
		let value = Math.round(parseFloat(val) * 100) / 100;
		let valMoney = value.toString().split(".");
		if (valMoney.length == 1) {
			value = value.toString() + ".00";
			return value;
		}
		if (valMoney.length > 1) {
			if (valMoney[1].length < 2) {
				value = value.toString() + "0";
			}
			return value;
		}
		return value;
	} else {
		return "0.00";
	}
});
//手机号中间4位为*
Vue.filter('phone', function(val) {
    var tel = val;
    tel = "" + tel;
    var telShort = tel.replace(tel.substring(3, 7), "****")
    return telShort
})
//获取系统信息、判断ipX安全距离
export const getTabbarHeight = function() {
    var systemInfo = uni.getSystemInfoSync()
    var data = {
        ...systemInfo,
        tabbarH: 50,//tabbar高度--单位px
        tabbarPaddingB: 0,//tabbar底部安全距离高度--单位px
        device: systemInfo.system.indexOf('iOS') != -1?'iOS':'Android', //苹果或者安卓设备
    }
    let modelArr = ['10,3','10,6','X', 'XR', 'XS', '11', '12', '13', '14', '15', '16'];
    let model = systemInfo.model;
    model && modelArr.forEach(item => {
        //适配iphoneX以上的底部，给tabbar一定高度的padding-bottom
        if(model.indexOf(item) != -1 && (model.indexOf('iPhone') != -1 || model.indexOf('iphone') != -1)) {
            data.tabbarH = 70
            data.tabbarPaddingB = 20
        }
    })
    return data;
}

//计算两点距离
export const  commonDistance = function(lat1, lng1, lat2, lng2) {
    var f = ((lat1 + lat2) / 2) * Math.PI / 180.0;
    var g = ((lat1 - lat2) / 2) * Math.PI / 180.0;
    var l = ((lng1 - lng2) / 2) * Math.PI / 180.0;
    var sg = Math.sin(g);
    var sl = Math.sin(l);
    var sf = Math.sin(f); 
    var s, c, w, r, d, h1, h2;
    var a = 6378137.0; //地球的直径
    var fl = 1 / 298.257;
    sg = sg * sg;  
    sl = sl * sl;  
    sf = sf * sf;  
    s = sg * (1 - sl) + (1 - sf) * sl;  
    c = (1 - sg) * (1 - sl) + sf * sl;  
    w = Math.atan(Math.sqrt(s / c));  
    r = Math.sqrt(s * c) / w;  
    d = 2 * w * a;  
    h1 = (3 * r - 1) / 2 / c;  
    h2 = (3 * r + 1) / 2 / s;  
    var num = d * (1 + fl * (h1 * sf * (1 - sg) - h2 * (1 - sf) * sg))  
    // 换算单位
    if (num == undefined) {
        num = "0.0 m"
    };
    if (num < 1000) {
        num = (Math.round(num)).toFixed(1) + "m"
    } else if (num > 1000) {
        num = (Math.round(num / 100) / 10).toFixed(1) + "km"
    }
    return num
}
// px转upx
export const px2upx = function(n){
    return n/(uni.upx2px(n)/n);
}

// 判断两时间段之间活动状态、判断活动还有多长时间开始、多长时间结束----添加定时器运行此方法可倒计时
// var startTime = new Date(item.startTime.replace(/-/g, '/')).getTime(); //转时间戳
// var closeTime = new Date(item.closeTime.replace(/-/g, '/')).getTime(); //转时间戳
// var djs = this.djsTime(startTime, closeTime);
export const djsTime = function(startTime, endTime) {
    var bbb = new Date().getTime(),
        leftTime = startTime - bbb,
        rightTime = endTime - bbb,
        djsTime = '',
        speed = 0,
        activityStatus = 0, //活动状态 1：未开始 2：进行中 3：已结束
        dd,hh,mm,ss;
    if (leftTime > 0) { //还未开始
        activityStatus = 1
        dd = Math.floor(leftTime / 1000 / 60 / 60 / 24);
        hh = Math.floor((leftTime / 1000 / 60 / 60) % 24) < 10 ? '0' + Math.floor((leftTime / 1000 / 60 / 60) % 24) : Math.floor((leftTime / 1000 / 60 / 60) % 24);
        mm = Math.floor((leftTime / 1000 / 60) % 60) < 10 ? '0' + Math.floor((leftTime / 1000 / 60) % 60) : Math.floor((leftTime / 1000 / 60) % 60);
        ss = Math.floor((leftTime / 1000) % 60) < 10 ? '0' + Math.floor((leftTime / 1000) % 60) : Math.floor((leftTime / 1000) % 60);
        if (dd != 0) {
            djsTime = dd + "天 " + hh + ":" + mm + ":" + ss;
        } else {
            djsTime = hh + ":" + mm + ":" + ss;
        }
    } else if (leftTime <= 0) {
        if (rightTime > 0) { //进行中
            activityStatus = 2;
            speed = (1 - rightTime / (endTime - startTime)) * 100;
            dd = Math.floor(rightTime / 1000 / 60 / 60 / 24);
            hh = Math.floor((rightTime / 1000 / 60 / 60) % 24) < 10 ? '0' + Math.floor((rightTime / 1000 / 60 / 60) % 24) : Math.floor((rightTime / 1000 / 60 / 60) % 24);
            mm = Math.floor((rightTime / 1000 / 60) % 60) < 10 ? '0' + Math.floor((rightTime / 1000 / 60) % 60) : Math.floor((rightTime / 1000 / 60) % 60);
            ss = Math.floor((rightTime / 1000) % 60) < 10 ? '0' + Math.floor((rightTime / 1000) % 60) : Math.floor((rightTime / 1000) % 60);
            if (dd != 0) {
                djsTime = dd + "天 " + hh + ":" + mm + ":" + ss;
            } else {
                djsTime = hh + ":" + mm + ":" + ss;
            }
        } else { //已结束
            speed = 100;
            djsTime = '已结束';
            activityStatus = 3;
        }
    }
    var item = {
        djsTime: djsTime, //距离当前时间差
        activityStatus: activityStatus, //活动状态 1：未开始 2：进行中 3：已结束
        speed: speed, //进度（单位%）
        dd: dd, //天
        hh: hh, //小时
        mm: mm, //分
        ss: ss, //秒
    }
    return item;
}

// 小程序获取定位权限判断
// isOpenSetting  默认false:不检验授权，true:检验授权后获取地址
function getMpLocation(successCallback, errCallback, isOpenSetting) {
	uni.getSetting({
		success: res => {
			if (res.authSetting['scope.userLocation'] || !isOpenSetting) {
				uni.getLocation({
                    // #ifndef MP-ALIPAY
                    type: 'gcj02',
                    // #endif
					success(res){
                        console.log('successCallback')
						successCallback(res);
					},
					fail(err){
						console.log("位置信息错误", err);
						errCallback("位置信息获取失败");
					}
				});
			} else {
				errCallback("“位置信息”未授权");
				isOpenSetting && uni.showModal({
					title: '提示',
					content: '请先在设置页面打开“位置信息”使用权限',
					confirmText: '去设置',
					cancelText: '再逛会',
					success: res => {
						if (res.confirm) {
							uni.openSetting();
						}
					}
				});
			}
		}
	});
}
// 获取地址信息
let locationAuthorize = true;
export const getAppLatLon = function(successCallback, errCallback, isOpenSetting) {
	// #ifdef MP
		// #ifndef MP-ALIPAY
		if (locationAuthorize && isOpenSetting) {
			uni.authorize({
				scope: 'scope.userLocation',
				success: () => {
					getMpLocation(successCallback, errCallback, isOpenSetting);
					locationAuthorize = false;
				},
				fail: (err) => {
					locationAuthorize = false;
				}
			});
		} else {
			getMpLocation(successCallback, errCallback, isOpenSetting);
		}
		// #endif
		// #ifdef MP-ALIPAY
		getMpLocation(successCallback, errCallback, false);
		// #endif
	// #endif
    // #ifdef H5
    uni.getLocation({
        type: 'gcj02',
    	success(res){
            console.log('successCallback')
    		successCallback(res);
    	},
    	fail(err){
    		console.log("位置信息错误", err);
    		errCallback("位置信息获取失败");
    	}
    });
    // #endif
	// #ifdef APP-PLUS
	judgePermission("location", function(result) {
		if (result == 1) {
			uni.getLocation({
				type: 'gcj02',
				success: res => {
					successCallback(res);
				},
				fail: (err) => {
					console.log("位置信息错误", err);
					errCallback("位置信息获取失败");
				}
			});
		}
	});
	// #endif
}
// 打开外链
export const openLink = function(href) {
	var that = this
	// #ifdef APP-PLUS
	plus.runtime.openURL(href)
	// #endif
	// #ifdef H5
	window.open(href)
	// #endif
	// #ifdef MP
	uni.setClipboardData({
		data: href,
		success: () => {
			uni.hideToast();
			that.$nextTick(() => {
				that.$u.toast('链接已复制，请在浏览器打开');
			})
		}
	});
	// #endif
}
// 保存图片
let settingWritePhotosAlbum = false;
let scopeAlbum = 'scope.writePhotosAlbum'
// #ifdef MP-TOUTIAO
scopeAlbum = 'scope.album'
// #endif
export const saveImg = function(url, callback) {
	if (url) {
		// #ifdef MP-WEIXIN || MP-BAIDU || MP-TOUTIAO || MP-MP-KUAISHOU
		if (settingWritePhotosAlbum) {
			uni.getSetting({
				success: res => {
					if (res.authSetting[scopeAlbum]) {
						uni.showLoading({
							title: '正在下载'
						});
						uni.downloadFile({
							url: url,
							success: data => {
								if (data.statusCode == 200) {
									uni.saveImageToPhotosAlbum({
										filePath: data.tempFilePath,
										success: () => {
											uni.hideLoading();
											callback && callback();
											uni.showToast({
												title: '保存成功'
											});
										},
										fail(e) {
											uni.hideLoading();
											tip({
												title: '下载失败，错误原因：' + e
													.errMsg,
												icon: "none"
											});
										}
									});
								} else {
									uni.hideLoading();
									uni.showToast({
										title: '下载失败',
										icon: "none"
									});
								}
							},
							fail(e) {
								uni.hideLoading();
								uni.showToast({
									title: '下载失败，错误原因：' + e.errMsg,
									icon: "none"
								});
							}
						});
					} else {
						uni.showModal({
							title: '提示',
							content: '请先在设置页面打开“保存相册”使用权限',
							confirmText: '去设置',
							cancelText: '算了',
							success: data => {
								if (data.confirm) {
									uni.openSetting();
								}
							}
						});
					}
				}
			});
		} else {
			settingWritePhotosAlbum = true;
			uni.authorize({
				scope: scopeAlbum,
				success: () => {
					uni.showLoading({
						title: '正在下载'
					});
					uni.downloadFile({
						url: url,
						success: data => {
							if (data.statusCode == 200) {
								uni.saveImageToPhotosAlbum({
									filePath: data.tempFilePath,
									success: () => {
										uni.hideLoading();
										callback && callback();
										uni.showToast({
											title: '保存成功'
										});
									},
									fail(e) {
										uni.hideLoading();
										tip({
											title: '下载失败，错误原因：' + e.errMsg,
											icon: "none"
										});
									}
								});
							} else {
								uni.hideLoading();
								uni.showToast({
									title: '下载失败',
									icon: "none"
								});
							}
						},
						fail(e) {
							uni.hideLoading();
							uni.showToast({
								title: '下载失败，错误原因：' + e.errMsg,
								icon: "none"
							});
						}
					});
				}
			});
		}
		// #endif
		// #ifdef H5
		uni.showLoading({
			title: '正在下载'
		});
		uni.downloadFile({
			url: url,
			success: data => {
				uni.hideLoading();
				if (data.statusCode == 200) {
					callback && callback();
					window.open(data.tempFilePath);
				} else {
					uni.showToast({
						title: '下载失败',
						icon: "none"
					});
				}
			},
			fail(e) {
				uni.hideLoading();
				uni.showToast({
					title: '下载失败，错误原因：' + e.errMsg,
					icon: "none"
				});
			}
		});
		// #endif
		// #ifdef APP-PLUS || MP-ALIPAY
		uni.showLoading({
			title: '正在下载'
		});
		uni.saveImageToPhotosAlbum({
			filePath: url,
			success: () => {
				uni.hideLoading();
				callback && callback();
				uni.showToast({
					title: '保存成功'
				});
			},
			fail(e) {
				uni.hideLoading();
				uni.showToast({
					title: '下载失败，错误原因：' + e.errMsg,
					icon: "none"
				});
			}
		});
		// #endif
	} else {
		uni.showToast({
			title: '未找到图片',
			icon: 'none'
		});
	}
};
function tip(data) {
	setTimeout(() => {
		uni.showToast(data);
	}, 500);
}

// 保存视频
export const saveVideo = function(url, callback) {
	if (url) {
		// #ifdef MP
		if (settingWritePhotosAlbum) {
			uni.getSetting({
				success: res => {
					if (res.authSetting[scopeAlbum]) {
						// #ifdef MP-WEIXIN
						let urlArr = url.split("/");
						let updateUrl = urlArr[urlArr.length - 1];
						let  filePath = wx.env.USER_DATA_PATH + '/' + (Math.random() * 100)+ updateUrl;
						// #endif
						uni.showLoading({
							title: '正在下载'
						});
						uni.downloadFile({
							url: url,
							// #ifdef MP-WEIXIN
							filePath: filePath,
							// #endif
							success: data => {
								if (data.statusCode == 200) {
									var tempFilePath = ''
									// #ifdef MP-WEIXIN
									tempFilePath = data.filePath
									// #endif
									// #ifndef MP-WEIXIN
									tempFilePath = data.tempFilePath
									// #endif
									uni.saveVideoToPhotosAlbum({
										filePath: tempFilePath,
										success: () => {
											uni.hideLoading();
											callback && callback();
											tip({
												title: '保存成功'
											});
										},
										fail(e) {
											uni.hideLoading();
											if(e.errMsg.indexOf('fail cancel')>=0){
												tip({
													title: '已取消',
													icon: "none"
												});
											}else{
												console.log('下载失败，错误原因1：' + e.errMsg)
												tip({
													title: '下载失败，错误原因：' + e.errMsg,
													icon: "none"
												});
											}
										}
									});
								} else {
									uni.hideLoading();
									tip({
										title: '下载失败',
										icon: "none"
									});
								}
							},
							fail(e) {
								uni.hideLoading();
								console.log('下载失败，错误原因2：' + e.errMsg)
								tip({
									title: '下载失败，错误原因：' + e.errMsg,
									icon: "none"
								});
							}
						});
					} else {
						uni.showModal({
							title: '提示',
							content: '请先在设置页面打开“保存相册”使用权限',
							confirmText: '去设置',
							cancelText: '算了',
							success: data => {
								if (data.confirm) {
									uni.openSetting();
								}
							}
						});
					}
				}
			});
		} else {
			settingWritePhotosAlbum = true;
			uni.authorize({
				scope: scopeAlbum,
				success: () => {
					// #ifdef MP-WEIXIN
					let urlArr = url.split("/");
					let updateUrl = urlArr[urlArr.length - 1];
					let  filePath = wx.env.USER_DATA_PATH + '/' + updateUrl;
					// #endif
					uni.showLoading({
						title: '正在下载'
					});
					uni.downloadFile({
						url: url,
						// #ifdef MP-WEIXIN
						filePath: filePath,
						// #endif
						success: data => {
							if (data.statusCode == 200) {
								var tempFilePath = ''
								// #ifdef MP-WEIXIN
								tempFilePath = data.filePath
								// #endif
								// #ifndef MP-WEIXIN
								tempFilePath = data.tempFilePath
								// #endif
								uni.saveVideoToPhotosAlbum({
									filePath: tempFilePath,
									success: () => {
										uni.hideLoading();
										callback && callback();
										tip({
											title: '保存成功'
										});
									},
									fail(e) {
										uni.hideLoading();
										if(e.errMsg.indexOf('fail cancel')>=0){
											tip({
												title: '已取消',
												icon: "none"
											});
										}else{
											tip({
												title: '下载失败，错误原因：' + e.errMsg,
												icon: "none"
											});
										}
									}
								});
							} else {
								uni.hideLoading();
								tip({
									title: '下载失败，错误原因：' + data.errMsg,
									icon: "none"
								});
							}
						},
						fail(e) {
							uni.hideLoading();
							tip({
								title: '下载失败，错误原因：' + e.errMsg,
								icon: "none"
							});
						}
					});
				}
			});
		}
		// #endif
		// #ifdef H5
		uni.showLoading({
			title: '正在下载'
		});
		uni.downloadFile({
			url: url,
			success: data => {
				uni.hideLoading();
				if (data.statusCode == 200) {
					callback && callback();
					window.open(data.tempFilePath);
				} else {
					tip({
						title: '下载失败',
						icon: "none"
					});
				}
			},
			fail(e) {
				uni.hideLoading();
				tip({
					title: '下载失败，错误原因：' + e.errMsg,
					icon: "none"
				});
			}
		});
		// #endif
		// #ifdef APP-PLUS
		uni.showLoading({
			title: '正在下载'
		});
		uni.saveVideoToPhotosAlbum({
			filePath: url,
			success: () => {
				uni.hideLoading();
				callback && callback();
				tip({
					title: '保存成功'
				});
			},
			fail(e) {
				uni.hideLoading();
				tip({
					title: '下载失败，错误原因：' + e.errMsg,
					icon: "none"
				});
			}
		});
		// #endif
	} else {
		tip({
			title: '未找到视频',
			icon: 'none'
		});
	}
};
