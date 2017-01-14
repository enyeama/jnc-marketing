//初始化微信配置
var initWechat = function(){
    $.get(PATH.PORTAL.WX_JS_CONFIG, {
    	'url': location.href
    }, function(data){
        wx.config({
            debug: false,
            appId: data.appId,
            timestamp: data.timestamp,
            nonceStr: data.nonceStr,
            signature: data.signature,
            jsApiList: ['onMenuShareTimeline', 'onMenuShareAppMessage', 'onMenuShareQQ', 'onMenuShareWeibo', 'onMenuShareQZone', 'chooseImage', 'getLocation', 'scanQRCode']
        });
    });
};

//初始化分享
var initShareJson = function(wx_json){
    wx.ready(function(){
        wx.onMenuShareTimeline(wx_json);
        wx.onMenuShareAppMessage(wx_json);
        wx.onMenuShareQQ(wx_json);
        wx.onMenuShareWeibo(wx_json);
        wx.onMenuShareQZone(wx_json);
    });
};

//选择图片与照相
var chooseWxImg = function () {
    wx.chooseImage({
        count: 1, // 默认9
        sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
        sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
        success: function (res) {
            var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
            return localIds;
        }
    });
};

//选择照相
var chooseWxCamera = function(call_back){
    wx.chooseImage({
        count: 1, // 默认9
        sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
        sourceType: ['camera'], // 可以指定来源是相册还是相机，默认二者都有
        success: call_back
    });
};

//扫描二维码
var scanQRCode = function(call_back){
    wx.scanQRCode({
        needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
        scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
        success: call_back
    });
};


//获取地址信息
var addressInfo = function (call_back) {
    wx.ready(function(){
        wx.getLocation({
            type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
            success: call_back
        });
    });
};

$(function () {
    //初始化微信配置
    initWechat();
});