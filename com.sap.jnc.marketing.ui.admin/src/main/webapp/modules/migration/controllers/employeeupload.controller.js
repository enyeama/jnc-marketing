(function() {
	// Controller
	angular.module('jnc-admin').controller('jnc-admin.migration.employeeupload.controller',
			function($scope, $http, $routeParams, $location, $constant) {
				// Upload
				$scope.submitForm = function() {
					var fd = new FormData();
					var file = document.querySelector('input[type=file]').files[0];
					fd.append('file', file);
					fd.append('type', $scope.type);
					$http({
						method : 'POST',
						url : $constant.URL_API + "employeemigrate/files",
						data : fd,
						headers : {
							'Content-Type' : undefined
						},
						transformRequest : angular.identity
					}).success(function(data) {
						$scope.model = data.data;
					});
				};
			});
})()