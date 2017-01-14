/**
 * @author: Zero Yu
 */
(function() {
	// Controller
	angular.module('jnc-admin').controller('jnc-admin.exhibit.list.controller', [
	// Dependencies
	'$scope', '$http', '$routeParams',
	// Main
	function($scope, $http, $routeParams) {
		(function() {
			$scope.criteria = {
				"paging" : {
					"index" : "0",
					"size" : "10"
				},
				"keywords" : {}
			};
			$scope.model = {
				"content" : [ {
					salesman : "name"
				} ],
				"last" : false,
				"totalPages" : 0,
				"totalElements" : 0,
				"number" : 0,
				"size" : 10,
				"sort" : [],
				"first" : false,
				"numberOfElements" : 0
			};
			$scope.exhibitType = (function(){
				var hash = window.location.hash;
				var array = hash.split("/");
				switch(array[array.length-1]) {
					case "verification":
						return "核销";
						break;
					case "cash":
						return "兑付";
						break;
					case "audit":
						return "审核";
						break;
				}
			})();
		})();

		$scope.toggleDisplay = function() {
			$("#searchPanel").collapse("toggle");
		};

		$('#exhibitDetailModal').on('show.bs.modal', function(event) {
			$(".nav-tabs li").click(function(e) {
				e.preventDefault();
			});
		});

	} ]);

})()
