var viewModel = {
	list: ko.observableArray(),
	listToArray: function() {
		var materials = this.list();
		var arr = [];
		$.each(materials, function(idx, d) {
			arr.push(d.materia);
		});
		return arr;
	}
}

var scanCode = function(callback) {
	wx.scanQRCode({
		needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
	    scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
	    success: function (res) {
		    /*var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
		    var list = viewModel.materialList();
		    list.push({
		    	'materia': result,
		    	'desc': ''
		    });
		    viewModel.materialList(list);*/
	    	callback(res.resultStr);
		}
	});
}

var checkCode = function(data) {
	$.showLoading("正在处理...");
	$.ajax({
		type : "POST",
		url : PATH.PORTAL.LEADER_LOGISTIC_CHECK,
		contentType : "application/json; charset=utf-8",
		data : JSON.stringify(data),
		dataType : 'json',
		success: function(data) {
			$.hideLoading();
			if (data.errCode != 0) {
				$.toast(data.errDescription, "forbidden");
			} else {
				var list = viewModel.materialList();
				list.push(code);
				viewModel.materialList(list);
			}
		},
		error: function(response) {
			$.hideLoading();
			$.toast("处理失败", "forbidden");
		} 
	});
}

var postLogistic = function(data) {
	$.showLoading("正在处理...");
	$.ajax({
		type : "POST",
		url : PATH.PORTAL.LEADER_IN_OUT_CASE,
		contentType : "application/json; charset=utf-8",
		data : JSON.stringify(data),
		dataType : 'json',
		success : function(data) {
			$.hideLoading();
			if (data.failedRecords.length > 0) {
				$.toast("数据错误", "forbidden");
			}
			else {
				$.toast("出库完成");
			}
		},
		error : function(response) {
			$.hideLoading();
			$.toast("处理失败", "forbidden");
		}
	});
}