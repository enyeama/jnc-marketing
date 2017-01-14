'use strict';

angular.module('jnc-admin').filter('kaorderstatus', function() {
	return function(input) {
		var statusString = input;
		switch (input) {
			case "WAITFORDELIVERY":
				statusString = "待发货";
				break;
			case "INDELIVERY":
				statusString = "配送中";
				break;
			case "FINISH":
				statusString = "完成";
				break;
			case "CANCEL":
				statusString = "取消";
				break;
		}
		return statusString;
	};
});
