/**
 * @author Emily Yu
 * @time 2016-06-28
 * @description 宴会报备：列表
 */
var viewModel = {
	id : getParam("id"),
	banquetId : getParam("banquetId"),
	index : 0,
	pageSize : 10,
	loading : false,
	keyword : ko.observable(""),
	list : ko.observableArray([]),
	getList : function(creatorId, index, pageSize) {
		var self = this;
		var data = {
			keywords : {
				creatorId : Number(creatorId),
			},
			paging : {
				index : index,
				size : pageSize
			}
		};
		var call_back = function(data) {
			data = data.content || [];
			if (data.length === 0) {
				$.alert(MESSAGE.NO_DATA, function() {
					window.history.go(-1);
				})
			}
			if (data.length < pageSize) {
				$(".weui-infinite-scroll").html("没有更多数据了");
				$(document.body).unbind("infinite");
			}
			self.list(self.list().concat(data));
			self.loading = false;
			self.index += 1;
		};
		this.loading = true;
		banquet.utility.ajax(PATH.BANQUET.LIST, "POST", call_back, data);
	},
	init : function() {
		var self = this;
		this.getList(this.id, this.index, this.pageSize);
		$(".weui-infinite-scroll").hide();
		$(document.body).infinite().on("infinite", function() {
			$(".weui-infinite-scroll").show();
			if (self.loading) return;
			self.getList(self.id, self.index, self.pageSize);
		});
	}
}

//过滤
var filterList = ko.computed(function() {
	var self = this;
	var list = this.list();
	var keyword = this.keyword().toLowerCase();
	if(!keyword){
		return list;
	}
	if(list.length){
		return list.filter(function(element){
			return String(element.id).indexOf(keyword) >= 0 || String(element.hostPhone).indexOf(keyword) >= 0 || String(element.handler.id).indexOf(keyword) >= 0 || String(element.handler.name).toLowerCase().indexOf(keyword) >= 0;
		});
	} else {
		return [];
	}
}, viewModel);

viewModel.init();
ko.applyBindings(viewModel);
