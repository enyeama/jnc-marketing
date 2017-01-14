/**
 * @author Emily Yu
 * @time 2016-06-27
 * @description 稽核首页
 */
var viewModel = {
	auditorId : ko.observable("191"),// COMMON.METHOD.GET_QUERY_STRING("id"),
	auditType : ["BANQUET", "EXHIBITION", "TERMINAL", "PROMOTION"],
	auditStatus : "ASSIGNED",
	banquetUrl : ko.observable(""),
	exhibitionUrl : ko.observable(""),
	terminalUrl : ko.observable(""),
	promotionUrl : ko.observable(""),
	banquetCount : ko.observable(0),
	exhibitionCount : ko.observable(0),
	terminalCount : ko.observable(0),
	promotionCount : ko.observable(0),
	getCount : function(auditorId, auditStatus, auditType){
		$.ajax({
			url: "/api/wechat/audits/count",
			method: "POST",
			data: JSON.stringify({
				"auditorId" : auditorId,
				"auditStatus" : auditStatus,
				"auditType" : auditType,				
			}),
			dataType: "json",
			contentType: "application/json; charset=UTF-8",
			beforeSend : function(){
				$.showLoading();
			},
			success: function(data){
				$.hideLoading();
				var method = auditType.toLowerCase() + "Count";
				viewModel[method](Number(data));
			},
			error : function(xhr, status, error){
				$.hideLoading();
				$.toast(status, "cancel");
			}
		})
	},
	getUrl : function(auditorId, auditStatus, auditType){
		var method = auditType.toLowerCase() + "Url";
		viewModel[method]("/wechat/audit/list.html?id=" + auditorId + "&type=" + auditType + "&status=" + auditStatus);
	},
	init : function(){
		var url = {};
		for(var i = 0; i<this.auditType.length; i++){
			this.getCount(this.auditorId(), this.auditStatus, this.auditType[i]);
			this.getUrl(this.auditorId(), this.auditStatus, this.auditType[i]);
		}
	}
}
viewModel.init();
ko.applyBindings(viewModel);