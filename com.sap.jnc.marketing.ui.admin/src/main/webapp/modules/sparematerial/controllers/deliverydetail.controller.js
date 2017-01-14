(function () {
	// Controller
	angular.module('jnc-admin').controller(
		'jnc-admin.sparematerial.deliverydetail.controller', [
			// Dependencies
			'$scope', '$http',
			// Main
			function ($scope, $http) {
				$("#startDate").datetimepicker({
					format : 'YYYY-MM-DD'
				});
				$("#endDate").datetimepicker({
					format : 'YYYY-MM-DD'
				});

				$scope.oCriteria = {
					"paging": {
						"index": "0",
						"size": "10"
					},
					"keywords" : {
						"positionId" : null,
						"materialId" : null,
						"startDate" : null,
						"endDate" : null,
						"giftListStatus": null,
						"materialType": null
					}
				};

				$scope.oModel = {};
				$scope.iCurrentPage = 1;

				$scope.loadData = function () {
					$http.post('/api/admin/deliveries', $scope.oCriteria)
						.then(function (oResponse) {
							$scope.oModel = oResponse.data;
						}, function () {
							toastr.error("数据加载失败");
						});
				};

				$scope.toggleSearchPanel = function() {
					$("#searchPanel").collapse("toggle");
				};

				$scope.cancelDelivery = function(iId) {
					var bConfirm = confirm("确认退单？");
					if (bConfirm) {
						$http.put("/api/admin/deliveries/" + iId)
							.then(function(oResponse) {
								toastr.success("退单成功！");
								$scope.loadData();
							}, function() {
								toastr.error("退单失败");
							});
					} else {
						return ;
					}
				};

				$scope.clearQuery = function() {
					$scope.oCriteria.keywords = {};
				};

				$scope.goToPage = function (index, size) {
					$scope.oCriteria.paging.index = index === 0 ? "0" : (index || $scope.oCriteria.paging.index);
					$scope.oCriteria.paging.size = size || $scope.oCriteria.paging.size;
					$scope.loadData();
				};

				$scope.queryData = function() {
					if($scope.oCriteria.keywords.materialType === "") {
						delete $scope.oCriteria.keywords.materialType;
					}
					if($scope.oCriteria.keywords.giftListStatus === "") {
						delete $scope.oCriteria.keywords.giftListStatus;
					}
					$scope.oCriteria.keywords.startDate = $("#startDate").val();
					$scope.oCriteria.keywords.endDate = $("#endDate").val();
					$scope.loadData();
				};

				$scope.loadData();
			}]).filter("deliveryStatusFormatter", function() {
		return function(deliveryStatus) {
			if (deliveryStatus === "IN_TRANSIT") {
				return "在途";
			} else if(deliveryStatus === "RETURNED") {
				return "退单";
			} else if(deliveryStatus === "DELIVERED") {
				return "已收货";
			}
		};
	}).filter("giftListStatusFormatter", function() {
		return function(giftListStatus) {
			if(giftListStatus === "EXPORTED") {
				return "已导出";
			} else if(giftListStatus === "NOT_EXPORTED") {
				return "未导出";
			}
		}
	});
})()