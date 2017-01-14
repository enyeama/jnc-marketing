angular.module('jnc-admin').controller('jnc-admin.bonus.logisticsRebateDetail.controller', function($scope, $modalInstance, item) {
	$scope.selected = {
		item : item
	};
	$scope.ok = function() {
		$modalInstance.close($scope.selected.item);
	};
	$scope.close = function() {
		$modalInstance.dismiss('cancel');

	}
})