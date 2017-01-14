(function() {
	// Controller
	angular.module('jnc-admin').controller('jnc-admin.dealers.dealerSMIn.controller', [
	// Dependencies
	'$scope', '$http', '$routeParams', '$constant',
	// Main
	function($scope, $http, $routeParams, $constant) {
		// sm in handler
		$scope.onSave = function() {
			var boxCodeList = $('#caseList').val().split('\n');
			// post data
			$http.post($constant.URL_API + '/dealer/logistic' , {
				'qrCodes': boxCodeList,
				'operatorId': '339',
				'operatorType': 'DEALER',
				'movementType': 'DEALER_IN'
			}).then(function(response) {
				if (response.data.failedRecords.length > 0) {
					var list = [];
					$.each(response.data.failedRecords, function(idx, d) {
						list.push(d.qrCode);
					});
					$('#caseList').val(list.join('\n'));
					alert('部分商品入库错误，请核对');
				} else {
					$('#caseList').val('');
					alert('入库完成');
				}
			}, function(response) {
				console.log(response);
			});
		}
	} ]);
})()