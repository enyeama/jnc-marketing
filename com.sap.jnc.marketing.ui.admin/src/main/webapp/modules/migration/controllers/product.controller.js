(function() {
	// Controller
	angular.module('jnc-admin').controller('jnc-admin.migration.product.controller', [
	// Dependencies
	'$scope', '$http', '$routeParams', '$constant', '$modal', '$location',
	// Main
	function($scope, $http, $routeParams, $constant, $modal, $location) {
		
		// 查询参数
		$scope.criteria = {
			"pagination" : {
				"page" : "0",
				"size" : "10"
			},
			"filters" : {
				"id" : null,
				"name" : null,
				"dmsCategoryId" : null,
			},
			"sorts" : {
				"externalId" : "ASC"
			}
		};
		
		// 分页 信息
		$scope.model = {
			"content" : [],
			"last" : false,
			"totalPages" : 0,
			"totalElements" : 0,
			"number" : 0,
			"size" : 10,
			"sort" : [],
			"first" : true,
			"numberOfElements" : 0
		};
		

		
		/**
		 * 初始化
		 * 
		 * */
		(function() {
			$scope.serverURL = $constant.URL_API;

			$http.get($constant.URL_API + "migration/product/dmscategory", {}).success(function(data) {
				$scope.dmscategory = data;
				$("#dmsCategoryInput").autocomplete({
//					source : function(request, response) {
//						var matcher = new RegExp($.ui.autocomplete.escapeRegex(request.term), "i");
//						response($.grep($scope.dmscategory, function(value) {
//							value = value.label || value.value || value;
//							return matcher.test(value);
//						}));
//					},
						source : $scope.dmscategory,
					matchContains : true,
					minLength : 0,
					select : function(event, ui) {
						$("#dmsCategoryInput").val( ui.item.label );
						console.log(ui.item.value);
						$scope.criteria.filters.dmsCategoryId = ui.item.value;
						return false;
					}
				});
			});
			
		})();
		
		
		/**
		 * 页面收拢打开
		 */
		$scope.toggleDisplay = function() {
			$("#fitlerPanel").collapse("toggle");
		};
		
		/**
		 * 页面跳转
		 */
		$scope.forwardImportPage = function() {
			window.location.href = $location.protocol() + "://" + $location.host() + ":" + $location.port() + "/admin/#/migration/productimport";
		};
		
		/**
		 * 清空查询条件
		 */
		$scope.clearFitlers = function() {
			$scope.criteria = {
				"pagination" : {
					"page" : "0",
					"size" : "10"
				},
				"filters" : {
					"id" : null,
					"name" : null,
					"dmsCategoryId" : null,
				},
				"sorts" : {
					"externalId" : "ASC"
				}
			};
		}
		
		/**
		 * 查询
		 */
		$scope.search = function() {
			$scope.page(0);
		};
		
		/**
		 * 分页查询
		 */
		$scope.page = function(page) {
			$scope.criteria.pagination.page = page;
			
			var size = $scope.criteria.pagination.size;
			if(!size || size === 0){
				$scope.criteria.pagination.size = 10;
			}
			
			if($('#dmsCategoryInput').val() == ''){
				// 判断autocomplete是否有数据，如果没有则将该查询条件清空
				$scope.criteria.filters.dmsCategoryId = null;
			}
			
			$('#pageTxt').val($scope.model.number+1);
			
			$http({
				method : 'post',
				url : $scope.serverURL + "migration/product",
				data : $scope.criteria
			}).success(function(oData) {
				if (oData && oData.content) {
					$scope.model = oData;
				}
			}).error(function(oData, oHeader, oConfig, sStatus) {

			});
		};
		
		/**
		 * 分页跳转
		 */
		$scope.goToPage = function() {
			var page = $('#pageTxt').val();
			if(page > $scope.model.totalPages && page > 0){
				$scope.model.number = $scope.model.totalPages -1;
			} else if(page <= 0) {
				$scope.model.number = 0;
			} else {
				$scope.model.number = page - 1 ;
			}
			$scope.page($scope.model.number);
		}
		
		/**
		 * 数据导出
		 */
		$scope.exportCSV = function() {
		}
	} ]);
})()