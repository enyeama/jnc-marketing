/**
 * @author Emily Yu
 * @time 2016-06-28
 * @description 宴会报备：明细
 */
var viewModel = {
	id : getParam("id"),
	banquetId : getParam("banquetId"),
	data : ko.observable({}),
	rebateUrl : ko.observable(""),
	scanUrl : ko.observable(""),
	getData : function(banquetId) {
		var self = this;
		ajax(PATH.BANQUET.HOME + "/" + banquetId, "GET", function(data) {
			self.data(data);
			self.getUrl(data.scanType);
		});
	},
	getUrl : function(type) {
		this.scanUrl(((type === 'QRCODE') ? PATH.BANQUET.HTML_SCAN_QR : PATH.BANQUET.HTML_SCAN_LOGISTICS) + '?banquetId=' + this.data().id + '&materialId=' + this.data().product.id);
		this.rebateUrl(((type === 'QRCODE') ? PATH.BANQUET.HTML_REBATE_QR : PATH.BANQUET.HTML_REBATE_LOGISTICS) + '?banquetId=' + this.data().id);
	},
	init : function() {
		this.getData(this.banquetId);
	}
};

viewModel.init();
ko.applyBindings(viewModel);