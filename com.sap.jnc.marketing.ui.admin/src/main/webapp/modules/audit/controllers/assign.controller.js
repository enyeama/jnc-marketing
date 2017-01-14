/**
 * @author: Zero Yu
 */
(function() {
	// Controller
	angular.module('jnc-admin').controller('jnc-admin.audit.assign.controller', [
	// Dependencies
	'$scope', '$http', '$routeParams', '$constant',
	// Main
	function($scope, $http, $routeParams, $constant) {
		(function() {
			$http.get($constant.URL_API + 'audits/auditor/office/auditors').success(function(data) {
				$scope.auditors = data;
				$("#formAuditors").autocomplete({
					source : function(request, response) {
						var matcher = new RegExp($.ui.autocomplete.escapeRegex(request.term), "i");
						response($.grep($scope.auditors, function(value) {
							value = value.label || value.value || value;
							return matcher.test(value);
						}));
					},
					select : function(event, ui) {
						$scope.criteria.keywords.auditorId = ui.item.value;
					}
				});
			});
			$http.get($constant.URL_API + 'audits/auditor/office/offices').success(function(data) {
				$scope.offices = data;
				var array = [];
				for(var i = data.length; i--;) {
					array.push(data[i].officeName);
				}
				$("#formOffices").autocomplete({
					source : function(request, response) {
						var matcher = new RegExp($.ui.autocomplete.escapeRegex(request.term), "i");
						response($.grep(array, function(value) {
							value = value.officeName || value.officeId || value;
							return matcher.test(value);
						}));
					},
					select : function(event, ui) {
						var tmp = $scope.offices;
						for(var i = tmp.length; i--;){
							if(tmp[i].officeName === ui.item.value) {
								$scope.criteria.keywords.officeId = tmp[i].officeName;
							}
						}
						//$scope.criteria.keywords.officeId = ui.item.value;
					}
				});
			});
			
			$('#formDate').datetimepicker({
				format : 'YYYY-MM-DD'
			});
			$('#assignDialogDateFrom').datetimepicker({
				format : 'YYYY-MM-DD'
			});
			$('#assignDialogDateTo').datetimepicker({
				format : 'YYYY-MM-DD'
			});
			
			$scope.criteria = {
				"paging" : {
					"index" : "0",
					"size" : "10"
				},
				"keywords" : {
					assigned : false
				}
			};
			$scope.model = {
				"content" : [ {
					test : "test"
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
		})();

		$scope.toggleDisplay = function() {
			$("#searchPanel").collapse("toggle");
		};

		$scope.clearForm = function() {
			$scope.criteria = {
				"paging" : {
					"index" : "0",
					"size" : "10"
				},
				"keywords" : {
					assigned : false
				}
			};
		};

		$scope.search = function() {
			var tmp = $scope.offices;
			//var requestBody = $scope.criteria;
			var requestBody = $.extend(true, {}, $scope.criteria);
			for(var i = tmp.length; i--;){
				if(tmp[i].officeName === requestBody.keywords.officeId) {
					requestBody.keywords.officeId = tmp[i].officeId;
					break;
				}
			}
			if($scope.criteria.keywords.assigned === true) {
				$http.post($constant.URL_API + 'audits/auditor/office/assignments', requestBody).success(function(data) {
					$scope.model = data;
				});
			} else {
				$http.post($constant.URL_API + 'audits/auditor/office/unassignments', requestBody).success(function(data) {
					$scope.model = data;
				});
			}
		};
		
		$scope.page = function(index, size) {
			$scope.request.paging.index = index === 0 ? "0" : (index || $scope.request.paging.index);
			$scope.request.paging.size = size || $scope.request.paging.size;
			$scope.search();
		};
		
		$scope.openAssignDialog = function() {
			$scope.item = this.item;
			$('#assignModal').modal('show');
		};
		
		$scope.openTerminateDialog = function() {
			$scope.item = this.item;
			$('#terminateModal').modal('show');
		};
	} ]);

})()
