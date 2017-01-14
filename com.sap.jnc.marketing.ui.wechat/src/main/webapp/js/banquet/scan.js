/**
 * @author Emily Yu
 * @time 2016-06-28
 * @description 宴会报备：扫码
 */
(function($) {
	(function($) {

		var init = function(config) {
			var self = this;
			this.$btnScan = $(config.btnScan);
			this.$btnSave = $(config.btnSave);
			this.$btnCancel = $(config.btnCancel);
			this.$container = $(config.container);
			this.$count = $(config.count);
			this.type = config.type;
			this.url = config.url;

			this.materialId = banquet.utility.getParam("materialId");
			this.banquetId = banquet.utility.getParam("banquetId");
			this.id = "scan" + this.type + "_" + this.banquetId + "_" + this.materialId;
			this.local = banquet.local;
			this.scanCodes = this.local.getItem(this.id);
			this.scanCode = null;

			this.$btnScan.click(function() {
				var codeType = ""
				if (self.type == "qr") {
					codeType = "codes";
				} else {
					codeType = "barcodes";
				}
				self.scan(codeType);
			});
			this.$btnSave.click(function() {
				if(self.scanCodes.length){
					self.submitForm();
				} else {
					banquet.utility.showMessage(MESSAGE.SCAN_FIRST, "forbidden");
				}
			});
			this.$btnCancel.click(function() {
				self.local.removeItem(self.id);
				self.scanCode = null;
				self.scanCodes = [];
				self.showCodes();
				banquet.utility.go(-1);
			});
			if (this.type == "logistics") {
				this.$input = $(config.input);
				this.$btnInput = $(config.btnInput);
				this.$btnInput.click(function() {
					self.scanCode = self.$input.val();
					if (self.isValid(self.scanCode)) {
						self.scan("plaincodes");
					} else {
						banquet.utility.showMessage(MESSAGE.NOT_LEAGAL, "cancel");
					}
				})
			}

			this.showCodes();
		};

		var scan = function(codeType) {
			var self = this;
			var call_back = function(data) {
				var url = "";
				if (data) {
					if(codeType === "barcodes"){
						self.scanCode = data.resultStr.split(",")[1];
					} else {
						self.scanCode = data.resultStr;
					}
				}
				if (self.isRepeat()) {
					banquet.utility.showMessage(MESSAGE.REPEAT, "cancel");
				} else {
					url = self.url + "/" + codeType + "/" + self.scanCode + "/materials/" + self.materialId;
					banquet.utility.ajax(url, "GET", function(data) {
						if (codeType == "plaincodes") {
							self.$input.val("");
						}
						self.processData(data);
					});
				}
			};
			if (this.type == "logistics" && codeType == "plaincodes") {
				call_back();
			} else {
				scanQRCode(call_back);
			}
		};

		var processData = function(data) {
			var self = this;
			if (data.status == "ACCEPTED") {
				self.scanCode = null;
				self.scanCodes = self.scanCodes.concat(data.boxCasePairs);
				self.local.setItem(self.id, self.scanCodes);
				self.showCodes();
			} else {
				$.toast(MESSAGE[data.status], "cancel");
			}
		};

		var isValid = function(value) {
			return /^[0-9]{24}$/.test(value);
		};

		var isRepeat = function() {
			var codes = this.scanCodes, code = this.scanCode;
			if (typeof code == "string") {
				for (var q = 0; q < codes.length; q++) {
					if (code == codes[q].boxId || code == codes[q].caseId) {
						return true;
					}
				}
			} else if ($.isArray(code)) {
				for (var i = 0; i < code.length; i++) {
					for (var j = 0; j < codes.length; j++) {
						if (code[i].boxId == codes[j].boxId || code[i].caseId == codes[j].boxId) {
							return true;
						}
					}
				}
			}
			return false;
		};

		var submitForm = function() {
			var self = this;
			var data = {
				banquetId : self.banquetId,
				materialId : self.materialId,
				boxCasePairs : self.scanCodes,
			};
			var call_back = function(res) {
				if (res.status == "CREATED") {
					self.local.removeItem(self.id);
					$.toast(MESSAGE.CREATED, "function", function() {
						window.location.href = PATH.BANQUET.HTML_DETAIL + "?banquetId=" + self.banquetId + "&materialId=" + self.materialId;
					});
				} else {
					$.toast(MESSAGE[res.status], "cancel");
				}
			};
			banquet.utility.ajax(self.url, "POST", call_back, data);
		};

		var showCodes = function() {
			var codes = this.scanCodes, html = "";
			for (var i = 0; i < codes.length; i++) {
				html = html + "<div class=\"weui_cell\"><div class=\"weui_cell_bd weui_cell_primary\"><p>" + codes[i]["boxId"] + "</p></div><div class=\"weui_cell_ft\"></div></div>";
			}
			this.$container.html(html);
			this.$count.html(codes.length);
		};

		banquet.scan = {
			init : init,
			scan : scan,
			isRepeat : isRepeat,
			isValid : isValid,
			submitForm : submitForm,
			showCodes : showCodes,
			processData : processData
		};

	})($);
})($);