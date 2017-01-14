/**
 * Vincent
 */
(function() {
	// Controller
	angular.module('jnc-admin').controller('jnc-admin.bonus.list.controller', [
	// Dependencies
	'$scope', '$http', '$routeParams', '$modal',
	// Main
	function($scope, $http, $routeParams, $modal) {
		$(function() {
			$('#validFrom').datetimepicker({
				format : 'YYYY-MM-DD'
			});
			$('#validTo').datetimepicker({
				format : 'YYYY-MM-DD'
			});

			/** 配置项 */
			$scope.url = {
				prefix : '/api/admin',
				toSave : '/admin/#/bonus/new',
				bonus : {
					add : '/bonus/config',
					pages : '/bonus/config/pages',
					product : {
						categories : '/bonus/config/product/categories'
					}
				}
			}

			$scope.toSave = $scope.url.toSave;
			$scope.addUrl = $scope.url.prefix + $scope.url.bonus.add;
			$scope.pagesUrl = $scope.url.prefix + $scope.url.bonus.pages;
			$scope.categoryUrl = $scope.url.prefix + $scope.url.bonus.product.categories;

			$scope.productCategories = null;
			$scope.categoryId = null;
			$scope.bonusConfigList = null;

			$scope.pageNo = 0;
			$scope.pageSize = 5;
			$scope.go = 0;

		});

		$scope.criteria = {
			"paging" : {
				"index" : $scope.pageNo,
				"size" : $scope.pageSize
			},
			"keywords" : {
				"validFrom" : null,
				"validTo" : null,
				"categoryId" : null
			},
			"sort" : {}
		}
		$scope.newModel = {
			averageAmount : 0,
			varianceAmount : 0,
			validFrom : null,
			erpRespose : {
				categoryName : null
			},
			list : []
		}
		$scope.model = {
			/** jpa */
			"content" : [],
			"last" : false,
			"totalPages" : 0,
			"totalElements" : 0,
			"number" : 0,
			"size" : 10,
			"sort" : [],
			"first" : false,
			"numberOfElements" : 1,
			/** 自定义 */
			"prevPage" : 0,
			"nextPage" : 0,
			"firstPage" : 0,
			"lastPage" : 0,
		};

		$scope.toggleDisplay = function() {
			$("#searchBonusListPanel").collapse("toggle");
		};

		$scope.pageSuccess = function(data) {
			/** 列表数据 */
			$scope.model.content = data.content;

			/** 页码相关 */
			$scope.model.last = data.last;
			$scope.model.totalPages = data.totalPages;
			$scope.model.totalElements = data.totalElements;
			$scope.model.number = data.number;
			$scope.model.size = data.size;
			$scope.model.sort = data.sort;
			$scope.model.first = data.first;
			$scope.model.numberOfElements = data.numberOfElements;

			/** 计算 */
			$scope.model.firstPage = 0;
			$scope.model.lastPage = $scope.model.totalPages - 1;

			if ($scope.model.first) {
				$scope.model.prevPage = 0;
			}
			else {
				$scope.model.prevPage = $scope.model.number - 1;
			}

			if ($scope.model.last) {
				$scope.model.nextPage = $scope.model.lastPage;
			}
			else {
				$scope.model.nextPage = $scope.model.number + 1;
			}
		}

		/** 初始化请求分页数据 */
		$http.post($scope.pagesUrl, $scope.criteria).success(function(data) {
			$scope.pageSuccess(data);
		});

		/** 转到页面 */
		$scope.skipToPage = function(index) {
			$scope.validFrom = $("#validFrom").val();
			$scope.validTo = $("#validTo").val();

			/** paging */
			$scope.criteria.paging.index = index == 0 ? 0 : index;
			if ($scope.criteria.paging.size == null || $scope.criteria.paging.size == 0) {
				$scope.criteria.paging.size = 5;
			}
			/** keywords */
			$scope.criteria.keywords.validFrom = $scope.validFrom;
			$scope.criteria.keywords.validTo = $scope.validTo;
			$scope.criteria.keywords.categoryId = ($scope.categoryName !== "") ? $scope.categoryId : null;

			/** 初始化请求分页数据 */
			$http.post($scope.pagesUrl, $scope.criteria).success(function(data) {
				$scope.pageSuccess(data);
			});

		}

		/** 选择产品类型 */
		$scope.fillProductInput = function(p) {
			$scope.categoryName = p.categoryName;
		}

		$scope.setSize = function() {
			$scope.skipToPage(0);
		};
		/** 加载ProductCategory */

		$scope.productCategoriesForSearch = [];
		$http.get($scope.categoryUrl).success(function(data) {
			if (data.length > 0) {
				$scope.productCategories = data;
				_.each(data, function(oItem) {
					$scope.productCategoriesForSearch.push(oItem.categoryName);
				});

				$("#categoryName").autocomplete({
					source : function(request, response) {
						var matcher = new RegExp($.ui.autocomplete.escapeRegex(request.term), "i");
						response($.grep($scope.productCategoriesForSearch, function(value) {
							value = value.label || value.value || value;
							return matcher.test(value);
						}));
					},
					select : function(event, ui) {
						$scope.categoryName = ui.item.value;
						var selectedItem = _.find(data, function(oItem) {
							return oItem.categoryName == ui.item.value;
						});
						$scope.categoryId = selectedItem.id;
					}
				});
			}
		});

		$scope.create = function() {
			var newModel = {
				averageAmount : null,
				varianceAmount : null,
				validFrom : null,
				erpRespose : {
					categoryName : null
				},
				list : []
			};
			var categories = $scope.productCategories;
			var categoriesSearch = $scope.productCategoriesForSearch;
			var modalInstance = $modal.open({
				animation : 'am-flip-x',
				templateUrl : 'modules/bonus/views/detail.html',
				controller : 'jnc-admin.bonus.detail.controller',
				size : 'lg',
				resolve : {
					item : function() {
						return newModel;
					},
					categories : function() {
						return categories;
					},
					categoriesSearch : function() {
						return categoriesSearch;
					},
					toastr : function() {
						return toastr;
					}
				}
			});
			modalInstance.result.then(function(selectedItem) {
				$scope.selected = selectedItem;
			}, function() {
			});
		}
	} ]);

})()
