/**
 * @author Yanyan Yu
 * @description 微信表单验证
 */

+(function($) {

	var defaults;

	var Validator = function(form, config) {
		var self = this;
		this.$form = $(form);
		this.$elements = this.$form.find("input");
		this.config = $.extend({}, defaults, config);
		this.invalid = [];
		this.init();
	}

	Validator.prototype = {
		init : function() {
			var self = this;
			this.$elements.change(function() {
				var name = $(this).attr("name");
				if (self.config.rules[name]) {
					self.valid($(this), self.config.rules[name]);
				}
			});
			this.$form.submit(function() {
				self.$elements.each(function() {
					var name = $(this).attr("name");
					if (self.config.rules[name]) {
						self.valid($(this), self.config.rules[name]);
					}
				});
				if (self.invalid.length) {
					return false;
				}
				else {
					self.config.onSubmit();
					return false;
				}
			});
		},
		initConfig : function() {
			this.config = $.extend({}, defaults, this.config);
		},
		valid : function($el, types) {
			var value = $el.val(), name = $el.attr("name"), errorMessage = [];
			for ( var t in types) {
				if (typeof types[t] === "boolean") {
					if (types[t]) {
						if (!this[t](value)) {
							errorMessage.push(this.config.messages[t]);
						}
					}
				}
				else {
					if (!this[t](value, types[t])) {
						errorMessage.push(this.config.messages[t]);
					}
				}
			}
			if (errorMessage.length) {
				this.showErrors($el, errorMessage[0]);
				if(this.invalid.indexOf(name) < 0){
					this.invalid.push(name);
				}
			}
			else {
				this.removeErrors($el);
				if(this.invalid.indexOf(name) >= 0){
					this.invalid.splice(this.invalid.indexOf(name), 1);
				}
			}
		},
		required : function(value) {
			return value.length > 0;
		},
		number : function(value) {
			return /^(?:-?\d+|-?\d{1,3}(?:,\d{3})+)?(?:\.\d+)?$/.test(value);
		},
		date : function(value) {
			return !/Invalid|NaN/.test(new Date(value).toString());
		},
		min : function(value, param) {
			return value >= param;
		},
		max : function(value, param) {
			return value <= param;
		},
		showErrors : function($el, errorMessage) {
			if ($el.parents(".weui_cell_warn").length)
				return;
			$el.parents(".weui_cell").addClass("weui_cell_warn");
			$el.parents(".weui_cell_bd").after("<div class=\"weui_cell_ft\"><i class=\"weui_icon_warn\"></i></div>");
			$el.attr("placeholder", errorMessage);
		},
		removeErrors : function($el) {
			$el.parents(".weui_cell").removeClass("weui_cell_warn");
			$el.parents(".weui_cell_bd").next(".weui_cell_ft").remove();
			$el.attr("placeholder", "");
		},
		update : function(config) {
			this.config.rules = $.extend({}, this.config.rules, config.rules);
		}
	}

	$.fn.validator = function(params, args) {

		return this.each(function() {

			var $this = $(this);

			if (!$this.data("weui-validator"))
				$this.data("weui-validator", new Validator(this, params));

			var validator = $this.data("weui-validator");

			if (typeof params === typeof "a")
				validator[params].call(validator, args);

			return validator;
		});
	}

	defaults = $.fn.validator.prototype.defaults = {
		messages : {
			required : "此项为必填项目",
			number : "请输入数字",
			date : "请输入日期",
			min : "请输入合法数据",
			max : "请输入合法数据",
		},
		rules : null,
		onSubmit : null
	};
})($);