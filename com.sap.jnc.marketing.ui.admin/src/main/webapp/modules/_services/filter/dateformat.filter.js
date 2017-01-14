'use strict';

angular.module('jnc-admin').filter('dateformat', function() {
	return function(input) {
		return moment(input).format("YYYY-MM-DD");
	};
});
