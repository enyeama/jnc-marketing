/**
 * @author Emily Yu
 * @time 2016-06-28
 * @description 稽核列表
 */
var viewModel = {
	list : ko.observableArray([]),
	auditorId : COMMON.METHOD.GET_QUERY_STRING("id"),
	auditType : COMMON.METHOD.GET_QUERY_STRING("type"),
	auditStatus : COMMON.METHOD.GET_QUERY_STRING("status"),
	getList : function(auditorId, auditStatus, auditType){
		$.ajax({
			url: "/api/wechat/"+auditType.toLowerCase()+"/audits/",
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
				if(data.length == 0){
					$.alert("没有数据", function(){
						window.history.go(-1);
					})
				}
				for(var i = 0; i<data.length; i++){
					data[i].url = "/wechat/audit/detail.html?id=" + auditorId + "&type=" + data[i].terminalType + "&tid=" + data[i].id + "&status=" + data[i].status;
				}
				viewModel.list(data);
			},
			error : function(xhr, status, error){
				$.hideLoading();
				$.toast(status, "cancel");
			}
		})
	},
	refuseAudit: function(model, item){
		$.confirm("确定要拒绝稽核吗？", function() {
			$.ajax({
				url: "/api/wechat/terminal/audits/auditstatus",
				method: "POST",
				data: JSON.stringify({
					"auditId" : item.auditId,
					"auditStatus" : "RETURNED",
				}),
//				dataType: "json",
				contentType: "application/json; charset=UTF-8",
				beforeSend : function(){
					$.showLoading();
				},
				success: function(data){
					$.hideLoading();
					var list = model.list();
					list.splice(list.indexOf(item), 1);
					model.list(list);
//					$.toast(MESSAGE.SUCCESS, "function", function(){
//						window.history.go(-1);
//					})
				},
				error : function(xhr, status, error){
					$.hideLoading();
					$.toast(status, "cancel");
				}
			})
		});
	},
	formatDate: function(timestamp){
		var date = timestamp ? new Date(timestamp) : new Date();
		var month = (date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1);
		var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
		return date.getFullYear() + "-" + month + "-" + day;
	},
	init : function(){
		this.getList(this.auditorId, this.auditStatus, this.auditType);
	}
}
viewModel.init();
ko.applyBindings(viewModel);