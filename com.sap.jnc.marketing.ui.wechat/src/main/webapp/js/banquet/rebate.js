/**
 * @author Emily Yu
 * @time 2016-06-28
 * @description 宴会报备：兑付
 */
(function($) {

	var init = function(config) {
		var self = this;
		this.$btnCancel = $(config.btnCancel);
		this.$btnSave = $(config.btnSave);
		this.$btnCash = $(config.btnCash);
		this.$listContainer = $(config.listContainer);
		this.$cashContainer = $(config.cashContainer);
		this.$count = $(config.count);
		this.type = config.type;

		this.banquetId = banquet.utility.getParam("banquetId");
		this.id = "cash" + "_" + this.type + "_" + this.banquetId;
		this.local = banquet.local;
		this.scanCodes = this.local.getItem(this.id);
		this.scanCode = null;
		this.oldValue = null;

		if (this.type == "qr") {
			this.$btnScan = $(config.btnScan);
			this.$btnScan.click(function() {
				self.scan();
			});
		} else if (this.type == "logistics") {
			this.$btnSelect = $(config.btnSelect);
			this.$input = $(config.input);
			this.$input.select({
				title : "选择物流码",
				items : [],
				multi : true,
				onChange : function(d) {
					self.change();
				},
			});
			this.$btnSelect.click(function() {
				self.$input.click();
			});
			this.oldValue = this.initValue();
			banquet.utility.ajax(PATH.BANQUET.REBATE_SELECTION + "/" + self.banquetId, "GET", function(data) {
				if (self.oldValue) {
					self.$input.val(self.oldValue);
					self.$input.select("update", {
						init : self.oldValue,
						items : data
					});
				} else {
					self.$input.select("update", {
						items : data
					});
				}

			});
		}
		this.$btnSave.click(function() {
			if(self.scanCodes.length){
				self.local.setItem(self.id, self.scanCodes);
			} else {
				banquet.utility.showMessage(MESSAGE.SCAN_FIRST, "forbidden");
			}
		});
		this.$btnCash.click(function() {
			if(self.scanCodes.length){
				self.submitForm();
			} else {
				banquet.utility.showMessage(MESSAGE.SCAN_FIRST, "forbidden");
			}
		});
		this.$btnCancel.click(function() {
			self.clear();
		});

		this.showCodes();
	};

	var change = function() {
		var result = this.find();
		if (!this.scanCode) {// remove item
			for (var i = 0; i < this.scanCodes.length; i++) {
				if (result === this.scanCodes[i].scanCode) {
					this.scanCodes.splice(i, 1);
					this.oldValue = this.$input.val();
					break;
				}
			}
			this.showCodes();
		} else {// add item
			this.getCash();
		}
	};

	var clear = function() {
		this.local.removeItem(self.id);
		this.scanCode = null;
		this.oldValue = null;
		this.scanCodes = [];
		this.showCodes();
		banquet.utility.go(-1);
	};

	var find = function() {
		var result = "", oldArr = !this.oldValue ? [] : this.oldValue.split(","), newArr = !this.$input.val() ? [] : this.$input.val().split(",");
		if (oldArr.length > newArr.length) {// remove item
			for (var i = 0; i < oldArr.length; i++) {
				if (newArr.indexOf(oldArr[i]) < 0) {
					this.scanCode = null;
					result = oldArr[i];
					return result;
				}
			}
		} else {// add item
			for (var i = 0; i < newArr.length; i++) {
				if (oldArr.indexOf(newArr[i]) < 0) {
					this.scanCode = result = newArr[i];
					return result;
				}
			}
		}
	};

	var getCash = function() {
		var self = this
		call_back = null;
		data = {
			banquetId : self.banquetId,
			scanCode : self.scanCode,
		};
		if (self.isRepeat()) {
			banquet.utility.showMessage(MESSAGE.REPEAT, "cancel");
		} else {
			call_back = function(data) {
				if (data.banquetRebateSingleInfo.scanResult == "SUCCESS") {
					// forward
					banquet.utility.showMessage(MESSAGE.SUCCESS);
					self.scanCodes = self.scanCodes.concat([data.banquetRebateSingleInfo]);
					self.showCodes();
					if(self.type === "logistics"){
						self.oldValue = self.$input.val();
					}
				} else {
					// rollback
					banquet.utility.showMessage(MESSAGE[data.banquetRebateSingleInfo.scanResult], "cancel");
					if(self.type === "logistics"){
						self.$input.val(self.oldValue);
						self.$input.select("update", {
							init : self.oldValue
						});
					}
				}
			};
			banquet.utility.ajax(PATH.BANQUET.REBATE_SCAN, "POST", call_back, data);
		}

	};

	var initValue = function() {
		var arr = [];
		for (var i = 0; i < this.scanCodes.length; i++) {
			arr.push(this.scanCodes[i].scanCode);
		}
		return arr.length ? arr.join(",") : "";
	};

	var isRepeat = function() {
		var self = this;
		for (var i = 0; i < self.scanCodes.length; i++) {
			if (self.scanCode == self.scanCodes[i].scanCode) {
				return true;
			}
		}
		return false;
	};

	var scan = function() {
		var self = this;
		var call_back = function(data) {
			self.scanCode = data.resultStr.substr(-18);
			self.getCash();
		};
		scanQRCode(call_back);
	};

	var select = function() {
		var self = this;
		var call_back = function(data) {
			self.$input.select("update", {
				items : data
			});
			self.$input.click();
		};
		banquet.utility.ajax(PATH.BANQUET.REBATE_SELECTION + "/" + self.id, "GET", call_back);
	};

	var showCodes = function() {
		var listHtml = cashHtml = "", codes = this.scanCodes, counter = {};
		for (var i = 0; i < codes.length; i++) {
			listHtml = listHtml + "<div class=\"weui_cell\">\
				<div class=\"weui_cell_bd weui_cell_primary\">\
					<p>" + codes[i].scanCode + "</p>\
				</div>\
				<div class=\"weui_cell_ft\"></div>\
				</div>";
			for (var j = 0; j < codes[i].banquetRebateConfigInfoList.length; j++) {
				var type = codes[i].banquetRebateConfigInfoList[j].rebateTargetType;
				var value = Number(codes[i].banquetRebateConfigInfoList[j].amount.value);
				if (counter.hasOwnProperty(type)) {
					counter[type] += value;
				} else {
					counter[type] = value;
				}
			}
		}
		for (var k in counter) {
			cashHtml = cashHtml + "<div class=\"weui_cell\">\
			<div class=\"weui_cell_hd\">\
				<label for=\"\" class=\"weui_label\">" + banquet.utility.getEnumerationText("rebateTargetType", k) + "</label>\
			</div>\
			<div class=\"weui_cell_bd weui_cell_primary\">\
				<input class=\"weui_input\" type=\"number\" value=\"" + counter[k] + "\" readonly=\"true\" /></div>\
			</div>";
		}
		this.$cashContainer.html(cashHtml);
		this.$listContainer.html(listHtml);
		this.$count.html(codes.length);
	};

	var submitForm = function() {
		var self = this, data = {
			banquetId : self.banquetId,
			banquetRebateSingleInfoList : self.scanCodes,
		};
		console.log(data);
		var call_back = function(data) {
			if (data.resultCode == "SUCCESS") {
				banquet.utility.showMessage(MESSAGE.SUCCESS);
				self.clear();
			} else {
				banquet.utility.showMessage(MESSAGE[data.resultCode], "cancel");
			}
		}
		banquet.utility.ajax(PATH.BANQUET.REBATE, "POST", call_back, data);
	};

	banquet.cash = {
		init : init,
		change : change,
		clear : clear,
		find : find,
		getCash : getCash,
		initValue : initValue,
		isRepeat : isRepeat,
		showCodes : showCodes,
		scan : scan,
		select : select,
		submitForm : submitForm,
	}
})($);


