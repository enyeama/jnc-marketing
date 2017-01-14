(function() {
	// Controller
	angular.module('jnc-admin').controller('jnc-admin.migration.dealer.controller', [
	// Dependencies
	'$scope', '$http', '$routeParams', '$constant', '$modal', '$location',
	// Main
	function($scope, $http, $routeParams, $constant, $modal, $location) {
		// Initial
		(function() {
			var names = [ "JNH001 - 剑南红", "JNLJ001 - 剑南老酒", "JJN002 - 金剑南 K6", "JJN001 - 金剑南 K8" ];
			$("#searchFieldProduct").autocomplete({
				source : function(request, response) {
					var matcher = new RegExp($.ui.autocomplete.escapeRegex(request.term), "i");
					response($.grep(names, function(value) {
						value = value.label || value.value || value;
						return matcher.test(value);
					}));
				},
				select : function(event, ui) {
					$scope.criteria.keywords.product = ui.item.value;
				}
			});
			$scope.dateOption = [ {
				value : '介于',
				id : '0'
			}, {
				value : '大于',
				id : '1'
			}, {
				value : '小于',
				id : '2'
			}, {
				value : '等于',
				id : '3'
			} ];
			$scope.terminalInDateOptionIsBetween = true;
			$scope.cosumerScanDateSelectOptionIsBetween = true;
			$scope.leaderOutDateOptionIsBetween = true;

			$scope.serverURL = $constant.URL_API;

			$scope.criteria = {
				"paging" : {
					"index" : "0",
					"size" : "10"
				},
				"keywords" : {
					"dealerId" : null,
					"dealerName" : null,
					"dealerStatus" : null,
					"isPlatform": null,
					"isPrimaryDealer": null,
					"cityManagerPositionId": null
				},
				"sort" : {
					"externalId" : "ASC"
				}
			};
			$scope.model = {};
			$scope.iCurrentPage = 1;
		})();
		$scope.toggleDisplay = function() {
			$("#logisticsRebateSearchPanel").collapse("toggle");
		};
		// Search
		$scope.search = function() {
			$http({
				method : 'post',
				url : $scope.serverURL + "dealer/query",
				data : $scope.criteria
			}).success(function(oData) {
					$scope.model = oData;
			}).error(function(oData, oHeader, oConfig, sStatus) {
				toastr.error("数据加载失败");
			});
		};
		
		$scope.exportDealer = function() {
			var oRequestBody = $scope.criteria;
			oRequestBody.paging.index = 0;
			oRequestBody.paging.size = $scope.model.totalElements;
			$http({
				method: "POST",
				url: $constant.URL_API + "dealers/export",
				data: JSON.stringify(oRequestBody),
				responseType: "arraybuffer"
			}).success(function(data) {
				var blob = new Blob([ data ], {
					type : "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
				});
				var objectUrl = URL.createObjectURL(blob);
				saveAs(blob, "经销商基本信息" + moment().format("YYYY-MM-DD") + ".xlsx"); 
			}).error(function() {
				toastr.error("导出经销商数据失败！");
			});
		}
		
		$scope.goToPage = function(index, size) {
			$scope.criteria.paging.index = index === 0 ? "0" : (index || $scope.criteria.paging.index);
			$scope.criteria.paging.size = size || $scope.criteria.paging.size;
			$scope.search();
		};
		
		$scope.queryByConditions = function() {
			$scope.criteria.paging.index = 0;
			if($scope.criteria.keywords.dealerStatus === "") {
				delete $scope.criteria.keywords.dealerStatus;
			}
			$scope.search();
		}

		$scope.clearDealerQuery = function() {
			$scope.criteria.keywords = {};
		};

		$scope.search();
	} ]).filter("dealerStatusFormatter", function() {
		return function(dealerStatus) {
			if(dealerStatus === "ACTIVE") {
				return "可用";
			} else {
				return "停用";
			}
		}
	}).filter("platformFormatter", function() {
		return function(isPlatform) {
			if(isPlatform === true) {
				return "是";
			} else {
				return "否";
			}
		}
	}).filter("primaryDealerFormatter", function() {
		return function(isPrimary) {
			if(isPrimary === true) {
				return "是";
			} else {
				return "否";
			}
		}
	});
})()