(function() {
	// Controller
	angular.module('jnc-admin').controller('jnc-admin.dealers.dealerSMNPTOrders.controller', [
	// Dependencies
	'$scope', '$http', '$routeParams', '$constant',
	// Main
	function($scope, $http, $routeParams, $constant) {
		// Initial
		(function() {
			$scope.criteria = {
				"paging" : {
					"index" : "0",
					"size" : "10"
				},
				"keywords" : {
					"orderId" : '',
					'status' : 'WAITFORDELIVERY',
					'terminal' : ''
				},
				"sort" : {
					"id" : "DESC"
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
			$http.post($constant.URL_API + 'dealer/orders', $scope.criteria).success(function(data) {
				if (data) {
					$scope.model = data;
				}
			}).error(function(response) {
				console.log(response);
			});
		};
		$scope.page = function(index, size) {
			$scope.criteria.paging.index = index === 0 ? "0" : (index || $scope.criteria.paging.index);
			$scope.criteria.paging.size = size || $scope.criteria.paging.size;
			$scope.search();
		}
		$scope.cancel = function(orderId) {
			$http.post($constant.URL_API + 'dealer/order', {
				'orderId': orderId
			}).success(function(data) {
				$.each($scope.model.content, function(idx, d) {
					if (d.orderId === data.orderId) {
						$scope.model.content.splice(idx, 1);
					}
				});
			}).error(function(response) {
				console.log(response);
			});
		}
	} ]);
})()