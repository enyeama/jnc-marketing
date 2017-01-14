(function() {
	// Controller
	angular.module('jnc-admin').controller('jnc-admin.dealers.dealerSMPTOutSearch.controller', [
	// Dependencies
	'$scope', '$http', '$routeParams', '$constant',
	// Main
	function($scope, $http, $routeParams, $constant) {
		// Initial
		$scope.criteria = {
			"paging" : {
				"index" : "0",
				"size" : "10"
			},
			"keywords" : {
				"leaderId" : 'abcdefg'
			},
			"sort" : {
				//"externalId" : "ASC"
			}
		};
		$scope.model = {
			"content" : []
		};
		// filter Search
		$scope.search = function() {
			// filter data
			var leader = $('#filter').find(':selected').text();
			$('#logisticList').trigger('footable_filter', {filter: leader});
		};
		(function() {
			$('#logisticList').footable();
			
			// load out case history
			// hard code dealerId
			$http.get($constant.URL_API + '/dealer/339/leader/logistic').success(function(response) {
				if (response) {
					$scope.model.content = response;
				}
			}).error(function(response) {
				console.log(response);
			});
			
			// load leader list
			// hard code dealerId
			$http.get($constant.URL_API + '/dealer/leaders/339').success(function(response) {
				$scope.leaders = response;
			}).error(function(response) {
				console.log(response);
			});
		})();
		
	} ]);
})()