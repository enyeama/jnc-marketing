/**
 * @author Marco Huang
 */
angular.module('jnc-admin').controller('jnc-admin.sparematerial.controllers.deliverysafetystockDialog', function($scope, $modalInstance, item) {
	$scope.selected = {
		item : item
	};
	$scope.ok = function() {
		$modalInstance.close($scope.selected.item);
	};
	$scope.close = function() {
//		dereg();
		$modalInstance.dismiss('cancel');

	};
	
	$scope.saveData = function() {
		var deliveryQuantity = $('deliveryQuNotLessThanRequireQu').val();
		
		$modalInstance.dismiss('cancel');

	};
})