/**
 * @author Emily Yu
 * @time 2016-06-28
 * @description 稽核明细
 */
var FIELDNAMES = {
	"assignTime" : "分配时间",
	"createOn" : "创建时间",
	"pictureURL" : "门头照片",
	"branchName" : "网点名称",
	"title" : "门头名称",
	"address" : "网点地址",
	"businessLicenseName" : "营业执照名称",
	"registrantName" : "注册人姓名",
	"phone" : "座机号码",
	"cashPersonName" : "资源兑付人姓名",
	"cashPersonMobile" : "资源兑付人手机号码",
	"cashPersonWechat" : "资源兑付人微信号",
	"goodsReceiverWechat" : "收货人微信号",
	"InWholeSaleMarket" : "批发市场",
	"licensePictureURL" : "营业执照照片",

	"isBanquetHotel" : "是否宴会酒店",
	"warehouseAddres" : "仓库地址",
	"keyUserContactPosition" : "关键人职位",
	"purchaseContactName" : "采购人姓名",
	"purchaseContactPhone" : "采购人手机号码",
	"purchaseContactWechat" : "采购人微信号",
	"supervisorContactName" : "吧台主管姓名",
	"supervisorContactPhone" : "吧台主管手机号码",
	"supervisorContactWechat" : "吧台主管微信号",
	"businessContactName" : "业务联系人",
	"businessContactPhone" : "业务联系人手机号码",
	"businessContactPosition" : "业务联系人职位",
	"businessContactWechat" : "业务联系人微信号",
	"promoterName" : "促销员",
	"promoterPhone" : "促销员电话",
	"promoterWechat" : "促销员微信",
}
var PICTURE_TYPES = {
	"HEAD" : "门头照片",
	"BUSINESS_LICEN" : "营业执照照片",
	"GRID_CLOTH" : "张贴网格布照片",
	"ENCLOSE_BOX" : "摆放盒围照片",
	"CARD" : "摆放盒围照片",
	"EXHIBITION" : "陈列照片",
	"BANQUET" : "宴会照片"
}
var viewModel = {
	itemList : ko.observableArray([]),
	pictureList : ko.observableArray([]),
	fieldNames : FIELDNAMES,
	pictureTypes : PICTURE_TYPES,
	auditorId : COMMON.METHOD.GET_QUERY_STRING("id"),
	auditStatus : COMMON.METHOD.GET_QUERY_STRING("status"),
	terminalId : COMMON.METHOD.GET_QUERY_STRING("tid"),
	terminalType : COMMON.METHOD.GET_QUERY_STRING("type"),
	getDetail : function(terminalType, terminalId) {
		var pictureList = [];
		if (terminalType === "TERMINAL") {
			pictureList.push({
				type : "HEAD",
				pictures : []
			});
			pictureList.push({
				type : "BUSINESS_LICEN",
				pictures : []
			});
		}
		else if (terminalType === "BANQUET") {
			pictureList.push({
				type : "BANQUET",
				pictures : []
			});
		}
		else if (terminalType === "EXHIBITION") {
			pictureList.push({
				type : "EXHIBITION",
				pictures : []
			});
		}
		else if (terminalType === "PROMOTION") {
			pictureList.push({
				type : "GRID_CLOTH",
				pictures : []
			});
			pictureList.push({
				type : "ENCLOSE_BOX",
				pictures : []
			});
			pictureList.push({
				type : "CARD",
				pictures : []
			});
		}
		$.ajax({
			url : "/api/wechat/audit/item/" + terminalId,
			method : "GET",
			// dataType: "json",
			contentType : "application/json; charset=UTF-8",
			beforeSend : function() {
				$.showLoading();
			},
			success : function(data) {
				$.hideLoading();
				viewModel.itemList(data);
			},
			error : function(xhr, status, error) {
				$.hideLoading();
				$.toast(status, "cancel");
			}
		})
		$.ajax({
			url : "/api/wechat/audit/picture/" + terminalId,
			method : "GET",
			// dataType: "json",
			contentType : "application/json; charset=UTF-8",
			beforeSend : function() {
				$.showLoading();
			},
			success : function(data) {
				$.hideLoading();
				var list = {};
				for (var i = 0; i < data.length; i++) {
					if (list[data[i].type]) {
						list[data[i].type].push(data[i]);
					}
					else {
						list[data[i].type] = [];
						list[data[i].type].push(data[i]);
					}
				}
				for (var j = 0; j < pictureList.length; j++) {
					for ( var k in list) {
						if (pictureList[j].type === k) {
							pictureList[j].pictures = list[k];
						}
					}
				}
				viewModel.pictureList(pictureList);
			},
			error : function(xhr, status, error) {
				$.hideLoading();
				$.toast(status, "cancel");
			}
		})
	},
	upload : function(file) {
		var filecontrol = $(":file")[0];
		if (!filecontrol.files[0]) {
			return;
		}
		var formdata = new FormData();
		formdata.append("file", filecontrol.files[0]);
		$.ajax({
			type : "POST",
			url : "/api/wechat/audits/files",
			data : formdata,
			processData : false,
			contentType : false,
			success : function(data) {
				alert(data);
			},
			error : function() {
				alert("failed");
			}
		});
	},
	preview : function(item) {
		var self = this;
		var call_back = function(address) {
			wx.chooseImage({
				count : 9,
				sizeType : [ 'original', 'compressed' ],
				sourceType : [ 'album', 'camera' ],
				success : function(res) {
					var localIds = res.localIds, pictureList = self.pictureList(), k = 0;
					for (var p = 0; p < pictureList.length; p++) {
						if (pictureList[p].type === item.type) {
							k = p;
						}
					}
					for (var i = 0; i < localIds.length; i++) {
						self.upload(localIds[i]);
						pictureList[k].pictures.push({
							address : address,
							processorId : item.processorId,
							type : item.type,
							url : localIds[i]
						})
					}
					self.pictureList(pictureList);
				}
			});
		}
		addressInfo(call_back);
	},
	submit : function() {
		this.upload();
		var itemList = self.itemList(), pictureList = [], p = self.pictureList();
		for (var i = 0; i < p.lenth; i++) {
			pictureList.concat(p[i].pictures);
		}
		$.ajax({
			url : "/api/wechat/terminal/auditdetails/" + terminalId,
			method : "POST",
			data : JSON.stringify({
				"id" : terminalId,
				"itemList" : itemList,
				"pictureList" : pictureList
			}),
			// dataType: "json",
			contentType : "application/json; charset=UTF-8",
			beforeSend : function() {
				$.showLoading();
			},
			success : function(data) {
				$.hideLoading();
				$.alert(MESSAGE.SUCCESS, function() {
					window.history.go(-1);
				})
			},
			error : function(xhr, status, error) {
				$.hideLoading();
				$.toast(status, "cancel");
			}
		})
	},
	cancel : function() {
		window.history.go(-1);
	},
	openPopup : function() {
		$("#popup").popup();
	},
	closePopup : function() {
		$.closePopup();
	},
	init : function() {
		this.getDetail(this.terminalType, this.terminalId);
	}
}
viewModel.init();
ko.applyBindings(viewModel);