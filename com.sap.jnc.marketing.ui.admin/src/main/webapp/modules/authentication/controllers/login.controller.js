/**
 * @author I071053 Diouf Du
 */
(function() {
	// Controller
	angular.module('jnc-admin').controller('jnc-admin.authentication.login.controller', [
	// Dependencies
	'$scope', '$http', '$location', '$authentication',
	// Main
	function($scope, $http, $location, $authentication) {
		// Construct
		$("#header").hide();
		$("#menu").hide();
		$("#wrapper").addClass("fullScreen").removeClass("collapseScreen").addClass("loginPanel");
		function _constructor() {
			$scope.criteria = {
				username : '测试超级管理员01',
				password : '123456'
			};
		}
		_constructor();
		// Event handlers
		$scope.login = function() {
			$scope.errors = null;
			$http.post(
			// Login URL
			'/api/admin/login',
			// Request data
			$.param((function() {
				return {
					username : $scope.criteria.username,
					password : $.md5($scope.criteria.password)
				};
			})()),
			// Configuration
			{
				headers : {
					'Content-Type' : 'application/x-www-form-urlencoded;charset=UTF-8'
				}
			})
			// Success
			.success(function() {
				$authentication.check(function() {
					$location.path('/home');
					$("#header").show();
					$("#menu").show();
					$("#wrapper").removeClass("loginPanel").addClass("collapseScreen").removeClass("fullScreen");
				});
			})
			// Error
			.error(function() {
				$scope.errors = [ {
					text : '用户名或者密码错误'
				} ];
			});
		}
	} ]);
})()