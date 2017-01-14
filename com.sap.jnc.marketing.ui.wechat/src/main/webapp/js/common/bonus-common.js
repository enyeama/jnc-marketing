var COMMON = {};
COMMON.METHOD = {};
//获取url中参数
COMMON.METHOD.GET_QUERY_STRING = function(name){
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    var context = "";
    if (r != null)
        context = r[2];
    reg = null;
    r = null;
    return context == null || context == "" || context == "undefined" ? "" : context;
};

$.ajaxSetup({
    statusCode: {
        401: function () {
            var oauthUrl = "/oauth_login?api_callback_url=" + window.location.href;
            window.location.href = oauthUrl;
        }
    },
    beforeSend: function (xhr) {
        var userOpenId = $.cookie('user_open_id') ? $.cookie('user_open_id') : '';
        var token = $.cookie('token') ? $.cookie('token') : '';
        console.log("userOpenId", userOpenId);
        console.log("token", token);
        xhr.setRequestHeader("user_open_id", userOpenId);
        xhr.setRequestHeader('token', token);
    },
    complete: function (xhr) {
        if (xhr.status == 422) {
            var error = JSON.parse(xhr.responseText);
            alert(error.errorMessage);
            WeixinJSBridge.invoke('closeWindow',{},function(res){});
        }

        var userOpenId = xhr.getResponseHeader("user_open_id");
        var token = xhr.getResponseHeader("token");
        if (isNotNull(userOpenId)) {
            $.cookie("user_open_id", userOpenId, {path: '/'});
        }
        if (isNotNull(token)) {
            $.cookie("token", token, {path: '/'});
        }
        console.log("userOpenId from response header: ", userOpenId);
        console.log("token from response header: ", token);
    }
});

function isNotNull(value) {
    if (value == null || value == '' || value == undefined) {
        return false;
    }
    return true;
}