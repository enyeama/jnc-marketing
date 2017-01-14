(function() {
	// Controller
	angular.module('jnc-admin').controller('jnc-admin.bonus.list.controller', [
	// Dependencies
	'$scope', '$http', '$routeParams',
	// Main
	function($scope, $http, $routeParams) {
		// Initialize Example 1
		$('#datetimepicker1').datetimepicker({
			format : 'YYYY-MM-DD'
		});
		$('#datetimepicker2').datetimepicker({
			format : 'YYYY-MM-DD'
		});
		$('#datetimepicker3').datetimepicker({
			format : 'YYYY-MM-DD'
		});
		$('#datetimepicker4').datetimepicker({
			format : 'YYYY-MM-DD'
		});
		// Load data
		$http.get('api/bonus-config.json').success(function(data) {
			$scope.bonus = {
				config : data
			};
		});
	} ]);
})()