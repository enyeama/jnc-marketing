(function() {
	// Controller
	angular.module('jnc-admin').controller('jnc-admin.dealers.dealerSMPTOut.controller', [
	// Dependencies
	'$scope', '$http', '$routeParams', '$constant',
	// Main
	function($scope, $http, $routeParams, $constant) {
		
		$(function() {
			$scope.onSave = function() {
				var caseList = $('#caseList').val().split('\n');
				// post data
				$http.post($constant.URL_API + '/dealer/logistic' , {
					'qrCodes': caseList,
					'operatorId': '339',
					'operatorType': 'DEALER',
					'movementType': 'DEALER_OUT',
					'receiverId': $scope.leader
				}).then(function(response) {
					if (response.data.failedRecords.length > 0) {
						var list = [];
						$.each(response.data.failedRecords, function(idx, d) {
							list.push(d.qrCode);
						});
						$('#caseList').val(list.join('\n'));
						alert('部分商品出库错误，请核对');
					} else {
						$('#caseList').val('');
						alert('出库完成');
					}
				}, function(response) {
					console.log(response);
				});
			}
			
			// load leader list
			// hard code dealerId
			$http.get($constant.URL_API + '/dealer/leaders/339').success(function(response) {
				$scope.leaders = response;
			}).error(function(response) {
				console.log(response);
			});
		});
	} ]);
})()