/**
 * @author Emily Yu
 * @time 2016-06-28
 * @description 宴会首页
 */
var viewModel = {
	formUrl : ko.observable(""),
	listUrl : ko.observable(""),
	id : ko.observable(119), //getParam("id"),
	getUrl : function() {
		this.formUrl("form.html?id=" + this.id());
		this.listUrl("list.html?id=" + this.id());
	},
	init : function() {
		this.getUrl(119);
	}
}
viewModel.init();
ko.applyBindings(viewModel);