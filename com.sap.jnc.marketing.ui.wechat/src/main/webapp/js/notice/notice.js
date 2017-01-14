var viewModel = {
    infos : ko.observableArray([]),
    getMaterials: function() {
    	var materials = this.infos();
		var list = [];
		$.each(materials, function(idx, d) {
			list.push(d.materia);
		});
		return list;
    },
    initInfo: function(str){
    	var data = {
			'qrCodes' : [str],
			'operatorId' : CONSTANT.YDFX.leaderId, // hard code TODO
			'operatorType' : 'LEADER',
			'movementType' : 'LEADER_IN'
		}
        // go to check
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
					var list = viewModel.infos();
					list.push({
						'materia': str
					});
					viewModel.infos(list);
				}
			},
			error: function(response) {
				$.hideLoading();
				$.toast("处理失败", "forbidden");
			}
		});
    }
};
ko.applyBindings(viewModel);

var code_back = function(res){
    viewModel.initInfo(res.resultStr);
};

//触发扫描二维码
var scanNotice = function(){
    scanQRCode(code_back);
	//viewModel.infos([{'materia':'123456789A12345678901234'}]);
};

var inCase = function(){
	var data = {
		'qrCodes' : viewModel.getMaterials(),
		'operatorId' : CONSTANT.YDFX.leaderId, // hard code TODO
		'operatorType' : 'LEADER',
		'movementType' : 'LEADER_IN'
	}
    // go to check
	$.showLoading("正在处理...");
	$.ajax({
		type : "POST",
		url : PATH.PORTAL.LEADER_IN_OUT_CASE,
		contentType : "application/json; charset=utf-8",
		data : JSON.stringify(data),
		dataType : 'json',
		success: function(data) {
			$.hideLoading();
			if (data.failedRecords.length > 0) {
				$.toast("数据错误", "forbidden");
			}
			else {
				viewModel.infos([]);
				$.toast("入库完成");
			}
		},
		error: function(response) {
			$.hideLoading();
			$.toast("处理失败", "forbidden");
		} 
	});

};