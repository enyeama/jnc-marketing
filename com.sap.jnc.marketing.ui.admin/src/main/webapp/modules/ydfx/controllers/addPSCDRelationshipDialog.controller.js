/**
 * @author Zero Yu
 */
angular.module('jnc-admin').controller('jnc-admin.ydfx.controller.addPSCDRelationshipDialog', function($scope, $http, $modalInstance, $constant) {
	(function(){
		$scope.cityManagerId = "905";
		$http.get($constant.URL_API + 'salescategorydmsmaintenance/salescateory?id=' + $scope.cityManagerId).success(function(data) {
			$scope.prodectSales = {
				content : data
			};
		});
		$http.get($constant.URL_API + 'salescategorydmsmaintenance/thirddms?id=' + $scope.cityManagerId).success(function(data) {
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
		if ($scope.addRequest.salesExternalId && $scope.addRequest.dmsId) {
			$http.post($constant.URL_API + 'salescategorydmsmaintenance/relationships', $scope.addRequest).success(function(data) {
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