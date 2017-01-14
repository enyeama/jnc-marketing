/**
 * @author I071053 Diouf Du
 */
(function() {
	// Controller
	angular.module('jnc-admin').controller('jnc-admin.authentication.profile.controller', [
	// Dependencies
	'$scope', '$http', '$location', '$authentication', '$constant',
	// Main
	function($scope, $http, $location, $authentication, $constant) {
		// Construct
		function _constructor() {
			$scope.notifications = null;
			$scope.errors = null;
			$scope.model = {
				userName : null,
				oldPassword : '',
				newPassword : '',
				confirmPassword : ''
			};
			var loadUserNameFunc = setInterval(function() {
				if ($authentication.content) {
					$scope.model.userName = $authentication.content.loginName;
					clearInterval(loadUserNameFunc);
				}
			}, 300);
		}
		_constructor();
		// Event handlers
		$scope.confirmSave = function() {
			$('#modal-confirm-save').modal('show');
		};
		$scope.save = function() {
			$http.put(
			// Change Profile URL
			$constant.URL_API + 'authentication/user',
			// Request data
			{
				userName : $authentication.content.loginName,
				oldPassword : $.md5($scope.model.oldPassword),
				newPassword : $.md5($scope.model.newPassword),
				confirmPassword : $.md5($scope.model.confirmPassword)
			})
			// Success
			.success(function() {
				$scope.notifications = [ {
					message : '个人信息修改成功'
				} ];
				_constructor();
				$('#modal-confirm-save').modal('hide');
			})
			// Error
			.error(function(response) {
				$scope.errors = response;
				$('#modal-confirm-save').modal('hide');
			});
		}
	} ]);
})()