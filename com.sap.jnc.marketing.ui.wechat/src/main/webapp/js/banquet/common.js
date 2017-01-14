/**
 * @author Emily Yu
 * @time 2016-06-28
 * @description 宴会报备：通用/本地存储
 */

var banquet = banquet || {};

// 通用
(function($) {

	var getParam = function(name) {
		var result = location.search.match(new RegExp("[\?\&]" + name + "=([^\&]+)", "i"));
		if (result == null || result.length < 1) {
			return "";
		}
		return result[1];
	};

	var showLoading = function() {
		$.showLoading();
	};

	var hideLoading = function() {
		$.hideLoading();
	};

	var showMessage = function(text, style, callback) {
		$.toast(text, style, callback);
	};

	var formatDate = function(str) {
		var date = new Date(str);
		var month = (date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1);
		var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
		return date.getFullYear() + "-" + month + "-" + day;
	};

	var ajax = function(url, method, callback, data) {
		var self = this;
		$.ajax({
			url : url,
			method : method,
			processData : false,
			data : JSON.stringify(data),
			// dataType : "json",
			contentType : "application/json; charset=UTF-8",
			beforeSend : function() {
				self.showLoading();
			},
			success : function(data, status, xhr) {
				//console.log(data);
				self.hideLoading();
				callback(data, status, xhr);
			},
			error : function(xhr, status, error) {
				self.hideLoading();
				self.showMessage(MESSAGE.ERROR, "cancel");
			}
		})
	};

	var go = function(url) {
		if (typeof url == "string") {
			window.location.href = url;
		} else {
			window.history.go(-1);
		}
	};
	
	var getEnumerationText = function(type, key) {
		if(ENUMERATION[type] && ENUMERATION[type][key]){
			return ENUMERATION[type][key];
		}
	};

	// 弹出菜单数据获取
	var getSelection = function($el, url, call_back) {
		banquet.utility.ajax(url, "GET", function(data) {
			console.log(data);
			data = {
				items : data
			};
			$el.select("update", data);
			if (call_back) {
				call_back();
			}
		});
	};

	banquet.utility = {
		ajax : ajax,
		formatDate : formatDate,
		getEnumerationText : getEnumerationText,
		getParam : getParam,
		getSelection : getSelection,
		go : go,
		hideLoading : hideLoading,
		showLoading : showLoading,
		showMessage : showMessage,
	};

})($);




// 本地存储
(function($) {

	var setItem = function(id, data) {
		data = JSON.stringify(data);
		localStorage.setItem(id, data);
	};
	var removeItem = function(id) {
		if (localStorage.hasOwnProperty(id)) {
			localStorage.removeItem(id);
		}
	};
	var getItem = function(id) {
		if (localStorage.hasOwnProperty(id)) {
			return JSON.parse(localStorage.getItem(id));
		}
		return [];
	};

	banquet.local = {
		setItem : setItem,
		removeItem : removeItem,
		getItem : getItem,
	};

})($);

var getParam = function(name) {
	var result = location.search.match(new RegExp("[\?\&]" + name + "=([^\&]+)", "i"));
	if (result == null || result.length < 1) {
		return "";
	}
	return result[1];
};

var showLoading = function() {
	$.showLoading();
};

var hideLoading = function() {
	$.hideLoading();
};

var showMessage = function(text, style, callback) {
	$.toast(text, style, callback);
};

var formatDate = function(str) {
	var date = new Date(str);
	var month = (date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1);
	var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
	return date.getFullYear() + "-" + month + "-" + day;
};

var ajax = function(url, method, callback, data) {
	var self = this;
	$.ajax({
		url : url,
		method : method,
		processData : false,
		data : JSON.stringify(data),
		// dataType : "json",
		contentType : "application/json; charset=UTF-8",
		beforeSend : function() {
			showLoading();
		},
		success : function(data, status, xhr) {
			//console.log(data);
			hideLoading();
			callback(data, status, xhr);
		},
		error : function(xhr, status, error) {
			hideLoading();
			showMessage(MESSAGE.ERROR, "cancel");
		}
	})
};

var go = function(url) {
	if (typeof url == "string") {
		window.location.href = url;
	} else {
		window.history.go(-1);
	}
};

var getEnumerationText = function(type, key) {
	if(ENUMERATION[type] && ENUMERATION[type][key]){
		return ENUMERATION[type][key];
	}
};

$.ajaxSetup({
    statusCode: {
        403: function (xhr, status, error) {
            $.alert(xhr.responseJSON.error[0].message, "403错误", function(){
            	window.history.go(-1);
            })
        }
    }
});


