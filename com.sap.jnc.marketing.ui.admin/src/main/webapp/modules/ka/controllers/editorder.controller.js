/**
 * Madeleine
 */
(function() {
	angular.module('jnc-admin').controller('jnc-admin.ka.editorder.controller',
			[ '$scope', '$http', '$modal', function($scope, $modalInstance, item) {
				$(function() {
					var officeNameList2 = [ "abc", "cde", "def", "fgh" ];
					$("#testAutoComp").autocomplete({
						source : officeNameList2,
						select : function(event, ui) {
							alert(ui.item.value);
						}
					});

					$scope.selected = {
						item : item
					};
					$scope.ok = function() {
						$modalInstance.close($scope.selected.item);
					};
					$scope.close = function() {
						$modalInstance.dismiss('cancel');
					}
				});
			} ])
})();
