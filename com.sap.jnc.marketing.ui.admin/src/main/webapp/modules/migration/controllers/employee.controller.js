(function() {
	// Controller
	angular.module('jnc-admin').controller('jnc-admin.migration.employee.controller', [
	// Dependencies
	'$scope', '$http', '$routeParams', '$location', '$constant',
	// Main
	function($scope, $http, $routeParams, $location, $constant) {
		// Initial
		(function() {
			$scope.criteria = {
				"paging" : {
					"index" : "0",
					"size" : "10"
				},
				"keywords" : {
					"externalId" : null,
					"name" : null,
					"idCardNO" : null,
					"phone" : null,
					"positionExternalId" : null,
					"departmentExternalId" : null,
					"productSalesCategoryExternalId" : null
				},
				"sort" : {
					"externalId" : "ASC"
				}
			};
			$scope.model = {
				"content" : [],
				"last" : false,
				"totalPages" : 0,
				"totalElements" : 0,
				"number" : 0,
				"size" : 10,
				"sort" : [],
				"first" : false,
				"numberOfElements" : 0
			};
		})();
		// Search
		$scope.search = function() {
			$http.post($constant.URL_API + 'employeemigrate/pages', $scope.criteria).success(function(data) {
				if (data.numberOfElements) {
					$scope.model = data;
				}
				else {
					$scope.page(data.totalPages - 1, data.size.toString());
				}
			});
		};
		// Upload
		$scope.upload = function(path) {
			$location.path(path);
			var curUrl = $location.absUrl(); 
		};
		// Export
		$scope.export = function() {
			$http.post($constant.URL_API + 'employeemigrate/export', $scope.criteria);
		};
		$scope.page = function(index, size) {
			$scope.criteria.paging.index = index === 0 ? "0" : (index || $scope.criteria.paging.index);
			$scope.criteria.paging.size = size || $scope.criteria.paging.size;
			$scope.search();
		}
	} ]);
})()