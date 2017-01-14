(function() {
	// Controller
	angular.module('jnc-admin').controller('jnc-admin.header.controller', [
	// Dependencies
	'$scope', '$http', '$location', '$authentication', '$constant',
	// Main
	function($scope, $http, $location, $authentication, $constant) {
		// Initial
		var repeatLoginCheck = setInterval(function() {
			$scope.isLogin = !!$authentication.content;
			$scope.$apply();
		}, 1000);
		// Event
		$scope.confirmLogout = function() {
			$('#modal-logout-success').modal('show');
		};
		$scope.changeProfile = function() {
			$location.path('/profile');
		};
		$scope.logout = function() {
			$http.post($constant.URL_API + 'logout').success(function() {
				$('#modal-logout-success').modal('hide');
				$location.path('/login');
			})
		};
		$scope.changeScreenSize = function() {
			var state = $(this).data('state');
			if (typeof (state) === "undefined")
				state = true;
			state = !state;
			if (state) {
				$("#menu").addClass("show").removeClass("hide");
				$("#wrapper").addClass("collapseScreen").removeClass("fullScreen");
				$(".fixed-small-header .small-header").addClass("collapseScreen").removeClass("fullScreen");
			}
			else {
				$("#menu").removeClass("show").addClass("hide");
				$("#wrapper").addClass("fullScreen").removeClass("collapseScreen");
				$(".fixed-small-header .small-header").addClass("fullScreen").removeClass("collapseScreen");
			}
			$(this).data('state', state);
		};
	} ]);
})()
