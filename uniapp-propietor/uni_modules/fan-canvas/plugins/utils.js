import qrcode from './qrcode';//用于绘制二维码

// 保存图片
let settingWritePhotosAlbum = false;
let scopeAlbum = 'scope.writePhotosAlbum'
// #ifdef MP-TOUTIAO
scopeAlbum = 'scope.album'
// #endif
// 保存图片
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
											if(e.errMsg.indexOf('fail cancel')>=0){
												tip({
													title: '已取消',
													icon: "none"
												});
											}else{
												tip({
													title: '下载失败，错误原因：' + e
														.errMsg,
													icon: "none"
												});
											}
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
// 保存临时图片
export const saveTempFilePathImg = function(url, callback) {
	if (url) {
		// #ifdef MP-WEIXIN || MP-BAIDU || MP-TOUTIAO || MP-MP-KUAISHOU
		if (settingWritePhotosAlbum) {
			uni.getSetting({
				success: res => {
					if (res.authSetting[scopeAlbum]) {
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
								e.errMsg!='saveImageToPhotosAlbum:fail cancel' && tip({
									title: '下载失败，错误原因1：' + e.errMsg,
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
							e.errMsg!='saveImageToPhotosAlbum:fail cancel' && tip({
								title: '下载失败，错误原因2：' + e.errMsg,
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

// 绘制二维码
export const qrcodeCanvas = function(id, code, width, height, that) {
    qrcode.api.draw(code, {
        ctx: uni.createCanvasContext(id, that),
        width: convert_length(width),
        height: convert_length(height)
    })
}
function convert_length(length) {
    return Math.round(uni.getSystemInfoSync().windowWidth * length / 750);
}