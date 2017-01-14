(function() {
	// Controller
	angular.module('jnc-admin').controller('jnc-admin.error.controller', [
	// Dependencies
	'$scope', '$location', '$authentication',
	// Main
	function($scope, $location, $authentication) {
		$authentication.check();
		$scope.navToHome = function() {
			$location.path('/home');
		};
		$scope.navToLogin = function() {
			$location.path('/login');
		};
	} ]);
})();