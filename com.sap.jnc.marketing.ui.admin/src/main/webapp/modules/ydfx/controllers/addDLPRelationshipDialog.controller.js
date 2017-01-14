/**
 * @author Zero Yu
 */
angular.module('jnc-admin').controller('jnc-admin.ydfx.controller.addDLPRelationshipDialog', function($scope, $http, $modalInstance, $constant) {
	(function(){
		$scope.cityManagerId = "5431";
		$http.get($constant.URL_API + 'maintenance/dealers?id=' + $scope.cityManagerId).success(function(data) {
			$scope.dealer = {
				content : data
			};
		});
		$http.get($constant.URL_API + 'maintenance/leaders?id=' + $scope.cityManagerId).success(function(data) {
			$scope.leader = {
				content : data
			};
		});
		$http.get($constant.URL_API + 'maintenance/products?id=' + $scope.cityManagerId).success(function(data) {
			$scope.dmsl3 = {
				content : data
			};
		});
	})();
	
	/**
	 * @function saveData
	 * @desc use dialog the add one relationship
	 */
	$scope.saveData = function() {
		if ($scope.addRequest.dealerId && $scope.addRequest.leaderId && $scope.addRequest.productId) {
			$http.post($constant.URL_API + 'maintenance/relationships', $scope.addRequest).success(function(data) {
				$scope.close();
			}).error(function() {
				$scope.close();
			});
		}
	};
	
	/**
	 * @function close
	 * @desc close the dialog
	 */
	$scope.close = function() {
		$modalInstance.close();
	}
})