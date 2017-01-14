/**
 * @author Emily Yu
 * @time 2016-06-29
 * @description 宴会报备：指定跟单人
 */
var viewModel = {
	handlerId : getParam("handlerId"),
	banquetId : getParam("banquetId"),
	keyword: ko.observable(""),
	list : ko.observableArray([]),
	getList : function(handlerId) {
		var self = this;
		ajax(PATH.BANQUET.HANDLER + "/" + handlerId, "GET", function(data){
			if(!data.length){
				$.alert(MESSAGE.NO_DATA, function(){
					window.history.go(-1);
				})
			}
			self.list(data);
		});
	},
	assign : function(context, item){
		var data = {
			"handler" : {
				"id" : Number(item.id)
			},
			"id" : Number(context.banquetId)
		};
		ajax(PATH.BANQUET.ASSIGN, "POST", function(data){
			$.alert(MESSAGE.SUCCESS, function(){
				window.history.go(-1);
			})
		},data);
	},
	init : function() {
		this.getList(this.handlerId);
	}
};

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
			return String(element.id).indexOf(keyword) >= 0 || String(element.name).indexOf(keyword) >= 0;
		});
	} else {
		return [];
	}
}, viewModel);

viewModel.init();
ko.applyBindings(viewModel);