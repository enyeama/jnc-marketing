(function() {
	// Controller
	angular.module('jnc-admin').controller('jnc-admin.bonus.logisticsRebateList.controller', [
	// Dependencies
	'$scope', '$http', '$routeParams', '$constant', '$modal',
	// Main
	function($scope, $http, $routeParams, $constant, $modal) {
		// Initial
		(function() {
			var key = CryptoJS.enc.Utf8.parse("jdbcuser2:Welcome2");
			var encode64pw = CryptoJS.enc.Base64.stringify(key);
			console.log(encode64pw);
			$http.get("http://120.76.31.239:8000/cdp/mmp/test/test.xsjs", {
				headers : {
					'Access-Control-Allow-Origin' : '*',
					'Access-Control-Allow-Methods' : 'GET, POST, PUT, DELETE, OPTIONS',
					'Access-Control-Allow-Headers' : 'Content-Type, X-Requested-With',
					"Content-Type" : "application/json",
					'Authorization' : 'Basic ' + encode64pw
				},
			}).success(function(data) {
				console.log(data);
			});

			$.datepicker.setDefaults($.datepicker.regional["zh-CN"]);
			$('#searchConsumerScanDate').datetimepicker({
				format : 'YYYY-MM-DD'
			});
			$('#searchConsumerScanStartDate').datetimepicker({
				format : 'YYYY-MM-DD'
			});
			$('#searchConsumerScanEndDate').datetimepicker({
				format : 'YYYY-MM-DD'
			});

			$('#searchTerminalInStartDate').datetimepicker({
				format : 'YYYY-MM-DD'
			});
			$('#searchTerminalInEndDate').datetimepicker({
				format : 'YYYY-MM-DD'
			});
			$('#searchTerminalInDate').datetimepicker({
				format : 'YYYY-MM-DD'
			});
			$('#searchLeaderOutStartDate').datetimepicker({
				format : 'YYYY-MM-DD'
			});
			$('#searchLeaderOutEndDate').datetimepicker({
				format : 'YYYY-MM-DD'
			});
			$('#searchLeaderOutDate').datetimepicker({
				format : 'YYYY-MM-DD'
			});
			$scope.loadList = null;
			$scope.message = '正在加载中...请稍后';
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

			$.cxSelect.defaults.url = "modules/_resources/cityData.min.json";

			$("#city").cxSelect({
				selects : [ "province", "city", "area" ],
				nodata : "none"
			});

			$scope.criteria = {
				"paging" : {
					"index" : "0",
					"size" : "10"
				},
				"keywords" : {
					"consumerScanDateSelectOption" : "between",
					"terminalInDateSelectOption" : "between",
					"leaderOutDateSelectOption" : "between",
					"consumerScanStartDate" : null,
					"consumerScanEndDate" : null,
					"consumerScanDate" : null,
					"terminalInDate" : null,
					"terminalInStartDate" : null,
					"terminalInEndDate" : null,
					"leaderOutDate" : null,
					"leaderOutStartDate" : null,
					"leaderOutEndDate" : null,
					"product" : null,
					"terminalName" : null,
					"terminalId" : null,
					"dealerName" : null,
					"dealderId" : null,
					"salesManId" : null,
					"salesManName" : null,
					"leaderName" : null,
					"leaderId" : null,
					"brandManagerId" : null,
					"brandManagerName" : null,
					"terminalProvince" : null,
					"terminalCity" : null,
					"terminalCountry" : null,
					"capInnerCode" : null
				},
				"sort" : {
					"product" : "ASC"
				}
			};
			$scope.model = {
				"content" : [],
				"last" : false,
				"totalPages" : 0,
				"totalElements" : 0,
				"number" : 0,
				"size" : 10,
				"sort" : [],
				"first" : false,
				"numberOfElements" : 1
			};
		})();
		$scope.toggleDisplay = function() {
			$("#logisticsRebateSearchPanel").collapse("toggle");
		};
		// Search
		$scope.search = function() {
			$http({
				method : 'post',
				// url : $scope.serverURL + "api/logisticsRebateReport.json",
				url : "api/logisticsRebateReport.json",
				data : $scope.criteria
			}).success(function(oData) {
				$scope.loadList = null;
				if (oData.length > 0) {
					$scope.model.content = oData;
					$scope.model.totalElements = oData.length;
					$scope.model.totalPages = oData.totalPages;
				}
				else {
					$scope.page(oData.totalPages - 1, oData.size.toString());
				}
			}).error(function(oEv) {
				$scope.loadList = null;
			});
			// $http.post('api/logisticsRebateReport.json', $scope.criteria).success(function(data) {
			// if (data.numberOfElements) {
			// $scope.model = data;
			// }
			// else {
			// $scope.page(data.totalPages - 1, data.size.toString());
			// }
			// });
		};
		$scope.page = function(index, size) {
			$scope.criteria.paging.index = index === 0 ? "0" : (index || $scope.criteria.paging.index);
			$scope.criteria.paging.size = size || $scope.criteria.paging.size;
			$scope.search();
		};
		$scope.goToPage = function() {
			$scope.page($scope.model.number, $scope.model.totalPages);
		}

		$scope.changeTerminalInDateOption = function(sOption) {
			$scope.terminalInDateOptionIsBetween = (sOption === "between") ? true : false;
		}
		$scope.changeConsumerScanDateOption = function(sOption) {
			$scope.cosumerScanDateSelectOptionIsBetween = (sOption === "between") ? true : false;
		}
		$scope.changeLeaderOutDateOption = function(sOption) {
			$scope.leaderOutDateOptionIsBetween = (sOption === "between") ? true : false;
		}
		$scope.navToDetail = function() {
			var oSelectedItem = this.logistic;
			var modalInstance = $modal.open({
				animation : 'am-flip-x',
				templateUrl : 'modules/bonus/views/logisticsRebateDetail.html',
				controller : 'jnc-admin.bonus.logisticsRebateDetail.controller',
				size : 'lg',
				resolve : {
					item : function() {
						return oSelectedItem;
					}
				}
			});
			modalInstance.result.then(function(selectedItem) {
				$scope.selected = selectedItem;
			}, function() {
			});
		};
		$scope.exportCSV = function() {

		}
	} ]);
})()