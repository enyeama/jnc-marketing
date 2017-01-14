/**
 * @author Zero Yu
 */
(function() {
	// Controller
	angular.module('jnc-admin').controller('jnc-admin.ydfx.dealerConfig.controller', [
	// Dependencies
	'$scope', '$http', '$routeParams', 'filterFilter', '$modal', '$constant',
	// Main
	function($scope, $http, $routeParams, filterFilter, $modal, $constant) {
		/**
		 * @function
		 * @desc anonymous function to init
		 */
		(function() {
			$scope.request = {
				"paging" : {
					"index" : 0,
					"size" : "10"
				}
			};
			$scope.model = {
				content:[]
			};
			$scope.cityManagerId = "656";
		})();
		
		/**
		 * @function search
		 * @desc find all of the relationships
		 */
		$scope.search = function() {
			$http.post($constant.URL_API + 'maintenance/relationships/page', $scope.request).success(function(data) {
				$scope.model = data;
			});
		};
		
		/**
		 * @function page
		 * @desc go to specified page
		 * @param {number} index - the index of page number
		 * @param {number} size - item number per page
		 */
		$scope.page = function(index, size) {
			$scope.request.paging.index = index === 0 ? "0" : (index || $scope.request.paging.index);
			$scope.request.paging.size = size || $scope.request.paging.size;
			$scope.search();
		}

		/**
		 * @function openDialog
		 * @desc open the dialog to add a relationship
		 */
		$scope.openDialog = function() {
			var modalInstance = $modal.open({
				animation : 'am-flip-x',
				templateUrl : 'modules/ydfx/views/addDLPRelationshipDialog.html',
				controller : 'jnc-admin.ydfx.controller.addDLPRelationshipDialog',
				size : 'lg'
			});
			modalInstance.result.then(function() {
				$scope.search();
			}, function() {
				$scope.search();
			});
		};

		/**
		 * @function remove
		 * @desc remove one specific relationship
		 */
		$scope.remove = function() {
			$scope.model.content = filterFilter($scope.model.content, function(item) {
				if (item.selected) {
					var removeRequest = {
						dealerId : item.dealerId,
						leaderId : item.leaderId,
						productId : item.productId
					};
					$http.post($constant.URL_API + 'maintenance/relationships/config', removeRequest).success(function(data) {
						$scope.search();
					});
				}
				//return !item.selected;
			});
		};
		
		$scope.search();
	} ]);
})()