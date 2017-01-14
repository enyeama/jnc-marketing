angular.module('jnc-admin').filter('toPercentage', function() {
	return function(value) {
		if (angular.isNumber(value)) {
			return (value * 100).toFixed(3) + "%";
		}
		return value;
	};
});