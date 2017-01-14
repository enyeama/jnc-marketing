/**
 * @author: Zero Yu
 */
(function() {
	// Controller
	angular.module('jnc-admin').controller('jnc-admin.contract.contractquery.controller', [
	// Dependencies
	'$scope', '$http', '$routeParams', '$constant',
	// Main
	function($scope, $http, $routeParams, $constant) {
		/**
		 * @function
		 * @desc anonymous function to init
		 */
		(function() {
			$scope.request = {
				"paging" : {
					"index" : 0,
					"size" : "10"
				},
				"keywords" : {
					"contractId" : null,
					"dealerId" : null,
					"postionId" : null,
					"productDmsCategoryId" : null,
					"status" : null
				},
				"sort" : {
					"contract.externalId" : "ASC"
				} 

			};
			$scope.model = {
				"content" : [],
				"last" : true,
				"totalPages" : 0,
				"totalElements" : 0,
				"number" : 0,
				"size" : 10,
				"sort" : [],
				"first" : true,
				"numberOfElements" : 1
			};
		})();

		$scope.search = function() {
			$scope.model = {
				"content" : [],
				"last" : true,
				"totalPages" : 0,
				"totalElements" : 0,
				"number" : 0,
				"size" : 10,
				"sort" : [],
				"first" : true,
				"numberOfElements" : 1
			};
			if ($scope.request.keywords.status == "") {
				$scope.request.keywords.status = null;
			}
			$scope.request.paging.index = 0;
			$http.post($constant.URL_API + 'dealercontracts/pages', $scope.request).success(function(data) {
				if (data.numberOfElements) {
					$scope.model = data;
				}
				else {
					$scope.page(data.totalPages - 1, data.size.toString());
				}
			});
		};

		$scope.page = function(index, size) {
			$scope.request.paging.index = index === 0 ? "0" : (index || $scope.request.paging.index);
			$scope.request.paging.size = size || $scope.request.paging.size;
			$scope.search();
		};

		$scope.export = function() {
			location.href = "/api/admin/contract/export";
		}
		$scope.toggleDisplay = function() {
			$("#searchContractPanel").collapse("toggle");
		};
		$scope.search();
	} ]);
})()