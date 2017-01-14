/**
 * @author Emily Yu
 * @time 2016-06-28
 * @description 宴会报备：创建/修改
 */
(function() {

	var init = function(config) {
		var self = this;
		this.id = banquet.utility.getParam("id");
		this.banquetId = banquet.utility.getParam("banquetId");
		this.config = config;
		this.selection = {
			product : {},
			terminal : {},
			dealerId : [],
			cityManagerId : [],
			channel : {},
			banquetType : [],
			applicationType : [],
			scanType : [],
			terminalType : [],
			timePoint : []
		};
		this.data = null;

		$("#gift").hide();
		$("#hotel").hide();
		$("#applicationType").select({
			title : "选择报备类型",
			items : [],
			onChange : function(d) {
				self.onChangeApplicationType(d.values);
			}
		});
		$("#terminal").select({
			title : "选择网点",
			items : [],
		});
		$("#dealerId").select({
			title : "选择经销商",
			items : [],
		});
		$("#cityManagerId").select({
			title : "选择城市经理",
			items : [],
		});
		$("#banquetType").select({
			title : "选择宴会类型",
			items : [],
		});
		$("#channel").select({
			title : "选择版本",
			items : [],
			onChange : function(d) {
				self.onChangeChannel(d.titles);
			}
		});
		$("#product").select({
			title : "选择商品名称",
			items : [],
		});
		$("#timePoint").select({
			title : "选择使用时间",
			items : [],
		});
		$("#scanType").select({
			title : "选择扫码类型",
			items : [],
		});
		$("#banquetTime").val(formatDate(new Date())).calendar({
			minDate : formatDate(new Date())
		});
		$("#btnSubmit").click(function(e) {
			$("#createForm").submit();
		});
		$("#createForm").submit(function() {
			return false;
		});
		$("#createForm").validator(config.validateOrigin);
		
		if(self.banquetId){
			$.ajax({
				url: PATH.BANQUET.HOME + "/" + this.banquetId,
				method: "GET",
				async: false,
			}).success(function(data) {
				if(!data){
					$.alert(MESSAGE.NO_DATA, function(){
						window.history.go(-1);
					})
				}
				self.bindValue(data);
				ajax(PATH.BANQUET.RELATED_INFO + "/" + self.id, "GET", function(data) {
					self.rebuildSelection(data);
					self.updateSelection(self.selection);
				});
				ajax(PATH.BANQUET.CHANNEL, "POST", function(data, status, xhr) {
					self.rebuildSelection(data, "NORMAL");
					self.updateSelection(self.selection);
				}, "NORMAL");
				ajax(PATH.BANQUET.CHANNEL, "POST", function(data, status, xhr) {
					self.rebuildSelection(data, "HOTEL");
					self.updateSelection(self.selection);
				}, "HOTEL");
				ajax(PATH.BANQUET.CHANNEL, "POST", function(data, status, xhr) {
					self.rebuildSelection(data, "JJN");
					self.updateSelection(self.selection);
				}, "JJN");
			})
		} else {
			ajax(PATH.BANQUET.RELATED_INFO + "/" + self.id, "GET", function(data) {
				self.rebuildSelection(data);
				self.updateSelection(self.selection);
			});
			ajax(PATH.BANQUET.CHANNEL, "POST", function(data, status, xhr) {
				self.rebuildSelection(data, "NORMAL");
				self.updateSelection(self.selection);
			}, "NORMAL");
			ajax(PATH.BANQUET.CHANNEL, "POST", function(data, status, xhr) {
				self.rebuildSelection(data, "HOTEL");
				self.updateSelection(self.selection);
			}, "HOTEL");
			ajax(PATH.BANQUET.CHANNEL, "POST", function(data, status, xhr) {
				self.rebuildSelection(data, "JJN");
				self.updateSelection(self.selection);
			}, "JJN");
		}
	};
	
	var onChangeChannel = function(cName){
		var self = this;
		if(!(self.selection.product[cName] && self.selection.product[cName].length)){
			$.alert("没有匹配版本的物料ID",function(){
				window.history.go(-1);
			})
		}
		$("#product").val("").attr("data-values","").select("update", {
			items : self.clearSelection(self.selection.product[cName]),
		});
	};
	
	var onChangeApplicationType = function(aType){
		var self = this;
		if(aType === "NORMAL"){
			$("#terminalType").val(getEnumerationText("terminalType", "SHOP")).attr("data-values","SHOP");
			$("#terminal").val("").attr("data-values","").select("update", {
				items : self.clearSelection(self.selection.terminal[getEnumerationText("terminalType", "SHOP")]),
			});
			$("#channel").val("").attr("data-values","").select("update", {
				items : self.clearSelection(self.selection.channel[aType]),
			});
			$("#product").val("").attr("data-values","");
			$("#gift,#hotel").hide().find("input").each(function() {
				$(this).val("");
			});
			$("#createForm").validator("update", self.config.validateNormal);
		} else if (aType === "HOTEL"){
			$("#terminalType").val(getEnumerationText("terminalType", "HOTEL")).attr("data-values","HOTEL");;
			$("#terminal").val("").attr("data-values","").select("update", {
				items : self.clearSelection(self.selection.terminal[getEnumerationText("terminalType", "HOTEL")]),
			});
			$("#channel").val("").attr("data-values","").select("update", {
				items : self.clearSelection(self.selection.channel[aType]),
			});
			$("#product").val("").attr("data-values","");
			$("#gift").show();
			$("#hotel").hide().find("input").each(function() {
				$(this).val("");
			});
			$("#createForm").validator("update", self.config.validateHotel);
		} else if (aType === "JJN"){
			$("#terminalType").val(getEnumerationText("terminalType", "SHOP")).attr("data-values","SHOP");;
			$("#terminal").val("").attr("data-values","").select("update", {
				items : self.clearSelection(self.selection.terminal[getEnumerationText("terminalType", "SHOP")]),
			});
			$("#channel").val("").attr("data-values","").select("update", {
				items : self.clearSelection(self.selection.channel[aType]),
			});
			$("#product").val("").attr("data-values","");
			$("#createForm").validator("update", self.config.validateJJN);
			$("#gift").hide().find("input").each(function() {
				$(this).val("");
			});
			$("#hotel").show();
		}
	}
	
	var bindValue = function(data){
		var self = this;
		$(".weui_panel_hd").html("修改宴会报备表单");
		for ( var k in data) {
			if(data[k]){
				if ([ "dealer", "cityManager", "applicationType", "terminal", "channel", "product", "scanType", "banquetTime", "banquetType", "planQuantity", "timePoint" ].indexOf(k) < 0) {
					$("#" + k).val(data[k]);
				}
			} 
//			else {
//				$.alert(k + "数据不正确", function(){
//					window.history.go(-1);
//				})
//			}
		}
		if(data["applicationType"] && data["applicationType"] === "NORMAL"){
			$("#createForm").validator("update", self.config.validateNormal);
			$("#gift,#hotel").hide().find("input").each(function() {
				$(this).val("");
			});
		} else if (data["applicationType"] && data["applicationType"] === "HOTEL"){
			$("#createForm").validator("update", self.config.validateHotel);
			$("#gift").show();
			$("#hotel").hide().find("input").each(function() {
				$(this).val("");
			});
		} else if (data["applicationType"] && data["applicationType"] === "JJN"){
			$("#createForm").validator("update", self.config.validateJJN);
			$("#gift").hide().find("input").each(function() {
				$(this).val("");
			});
			$("#hotel").show();
		}
		$("#applicationType").val(getEnumerationText("applicationType",data["applicationType"])).attr("data-values", data["applicationType"]);
		$("#cityManagerId").val(data["cityManager"].name).attr("data-values", data["cityManager"].id);
		$("#terminalType").val(data["terminal"].terminalType);
		$("#terminal").val(data["terminal"].title).attr("data-values", data["terminal"].id);
		$("#channel").val(data["channel"].channelName).attr("data-values", data["channel"].id);
		$("#product").val(data["product"].name).attr("data-values", data["product"].id);
		$("#planQuantity").val(data["planQuantity"].value);
		$("#scanType").val(getEnumerationText("scanType", data["scanType"])).attr("data-values", data["scanType"]);
		$("#dealerId").val(data["dealer"].name).attr("data-values", data["dealer"].id);
		$("#banquetTime").val(formatDate(data["banquetTime"])).attr("data-values", data["banquetTime"]);
		$("#timePoint").val(getEnumerationText("timePoint", data["timePoint"])).attr("data-values", data["timePoint"]);
		$("#banquetType").val(data["banquetType"].name).attr("data-values", data["banquetType"].id);
	};

	var clearSelection = function(data){
		data = data || [];
		for(var i = 0; i < data.length; i++){
			if(data[i].hasOwnProperty("checked")){
				data[i].checked = false;
			}
		}
		return data;
	};
	
	var rebuildSelection = function(data, aType) {
		var i = 0, self = this;
		if(data.dealerInfoResponse){
			self.selection.dealerId = [{
				value : data.dealerInfoResponse.id,
				title : data.dealerInfoResponse.name
			}];
		}
		if(data.cityManagerInfoResponse){
			for (i = 0; i < data.cityManagerInfoResponse.length; i++) {
				self.selection.cityManagerId.push({
					title : data.cityManagerInfoResponse[i].name,
					value : data.cityManagerInfoResponse[i].id
				});
			}
		}
		if(data.banquetTypes){
			for (i = 0; i < data.banquetTypes.length; i++) {
				self.selection.banquetType.push({
					title : data.banquetTypes[i].name,
					value : data.banquetTypes[i].id
				});
			}
		}
		if(data.timePointTypes){
			for (i = 0; i < data.timePointTypes.length; i++) {
				self.selection.timePoint.push({
					title : banquet.utility.getEnumerationText("timePoint", data.timePointTypes[i]),
					value : data.timePointTypes[i]
				});
			}
		}
		if(data.banquetApplyTypes){
			for (i = 0; i < data.banquetApplyTypes.length; i++) {
				self.selection.applicationType.push({
					title : banquet.utility.getEnumerationText("applicationType",data.banquetApplyTypes[i]),
					value : data.banquetApplyTypes[i]
				});
			}
		}
		if(data.banquetScanTypes){
			for (i = 0; i < data.banquetScanTypes.length; i++) {
				self.selection.scanType.push({
					title : banquet.utility.getEnumerationText("scanType",data.banquetScanTypes[i]),
					value : data.banquetScanTypes[i]
				});
			}	
		}
		if(data.terminalTypes){
			for (i = 0; i < data.terminalTypes.length; i++) {
				self.selection.terminalType.push({
					title : banquet.utility.getEnumerationText("terminalType",data.terminalTypes[i]),
					value : data.terminalTypes[i]
				});
			}
		}
		if(data.channel && data.channel.length){
			for (i = 0; i < data.channel.length; i++) {
				if(self.selection.channel.hasOwnProperty(aType)){
					if (!self.isIncluded(self.selection.channel[aType], data.channel[i].id)) {
						self.selection.channel[aType].push({
							title : data.channel[i].channelName,
							value : data.channel[i].id
						});
					}
				} else {
					self.selection.channel[aType] = [];
					self.selection.channel[aType].push({
						title : data.channel[i].channelName,
						value : data.channel[i].id
					});
				}
			}
		}
		
		if(data.productInfoResponse){
			for (i = 0; i < data.productInfoResponse.length; i++) {
				if(self.selection.product.hasOwnProperty(data.productInfoResponse[i].channelName)){
					if (!self.isIncluded(self.selection.product[data.productInfoResponse[i].channelName], data.productInfoResponse[i].id)) {
						self.selection.product[data.productInfoResponse[i].channelName].push({
							title : data.productInfoResponse[i].name,
							value : data.productInfoResponse[i].id
						});
					}
				}
				 else {
					self.selection.product[data.productInfoResponse[i].channelName] = [];
					self.selection.product[data.productInfoResponse[i].channelName].push({
						title : data.productInfoResponse[i].name,
						value : data.productInfoResponse[i].id
					});
				}
			}
		}
		if(data.terminalInfoResponse){
			for (i = 0; i < data.terminalInfoResponse.length; i++) {
				if (self.selection.terminal.hasOwnProperty(data.terminalInfoResponse[i].terminalType)) {
					if(!self.isIncluded(self.selection.terminal[data.terminalInfoResponse[i].terminalType], data.terminalInfoResponse[i].id)){
						self.selection.terminal[data.terminalInfoResponse[i].terminalType].push({
							title : data.terminalInfoResponse[i].title,
							value : data.terminalInfoResponse[i].id
						});
					}
				} else {
					self.selection.terminal[data.terminalInfoResponse[i].terminalType] = [];
					self.selection.terminal[data.terminalInfoResponse[i].terminalType].push({
						title : data.terminalInfoResponse[i].title,
						value : data.terminalInfoResponse[i].id
					});
				}
			}
		}
	};
	
	var isIncluded = function(obj, value){
		for(o in obj){
			return o.value === value;
		}
		return false;
	}

	var updateSelection = function() {
		var self = this;
		$("#dealerId").select("update", {
			items : self.selection.dealerId
		});
		$("#cityManagerId").select("update", {
			items : self.selection.cityManagerId
		});
		$("#timePoint").select("update", {
			items : self.selection.timePoint
		});
		$("#banquetType").select("update", {
			items : self.selection.banquetType
		});
		$("#applicationType").select("update", {
			items : self.selection.applicationType
		});
		$("#scanType").select("update", {
			items : self.selection.scanType
		});
		if(self.selection.channel[$("#applicationType").attr("data-values")]){
			$("#channel").select("update", {
				items : self.selection.channel[$("#applicationType").attr("data-values")]
			});
		}
		if(self.selection.terminal[$("#terminalType").val()]){
			$("#terminal").select("update", {
				items : self.selection.terminal[$("#terminalType").val()]
			});
		}
		if(self.selection.product[$("#channel").val()]){
			$("#product").select("update", {
				items : self.selection.product[$("#channel").val()]
			});
		}
	};

	// 数据提交
	var submitForm = function() {
		var self = this;
		var data = {}, $el = $("#createForm"), call_back;
		data = {
			"creator" : {
				"id" : self.id
			},
			"customerName" : $("#customerName").val(),
			"customerPhone" : $("#customerPhone").val(),
			"reservationistName" : $("#reservationistName").val(),
			"reservationistPhone" : $("#reservationistPhone").val(),
			"hostAddress" : $("#hostAddress").val(),
			"hostPhone" : $("#hostPhone").val(),
			"banquetTime" : (new Date($("#banquetTime").val())).getTime(),
			"tableCount" : $("#tableCount").val(),
			"applicationType" : $("#applicationType").attr("data-values"),
			"scanType" : $("#scanType").attr("data-values"),
			"banquetComment" : $("#banquetComment").val(),
			"planQuantity" : {
				"value" : $("#planQuantity").val(),
				"unit" : "瓶"
			},
			"banquetType" : {
				"id" : $("#banquetType").attr("data-values"),
			},
			"dealer" : {
				"id" : $("#dealerId").attr("data-values"),
			},
			"cityManager" : {
				"id" : $("#cityManagerId").attr("data-values"),
			},
			"terminal" : {
				"id" : $("#terminal").attr("data-values"),
			},
			"product" : {
				"id" : $("#product").attr("data-values"),
			},
			"channel" : {
				"id" : $("#channel").attr("data-values"),
			},
			"timePoint" : $("#timePoint").attr("data-values"),
		}
		call_back = function(data) {
			var msg = "";
			if (self.banquetId) {
				msg = "宴会报备修改成功！";
			} else {
				msg = "宴会报备创建成功！您的报备单号是" + data.id;
			}
			$.alert(msg, MESSAGE.SUCCESS, function() {
				banquet.utility.go(-1);
			});
		};
		if (self.banquetId) {
			data.id = self.banquetId;
			banquet.utility.ajax(PATH.BANQUET.UPDATE + "/" + self.banquetId, "POST", call_back, data);
		} else {
			banquet.utility.ajax(PATH.BANQUET.UPDATE, "POST", call_back, data);
		}
	};

	banquet.form = {
		isIncluded : isIncluded,
		bindValue : bindValue,
		onChangeApplicationType : onChangeApplicationType,
		onChangeChannel : onChangeChannel,
		init : init,
		bindValue : bindValue,
		submitForm : submitForm,
		rebuildSelection : rebuildSelection,
		updateSelection : updateSelection,
		clearSelection : clearSelection,
	};

})($);
