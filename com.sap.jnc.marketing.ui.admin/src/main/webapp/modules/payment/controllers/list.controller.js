(function() {
	// Controller
	angular.module('jnc-admin').controller('jnc-admin.payment.list.controller', [
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
			$scope.newpayment = '/admin/#/payment/new';
			$scope.url = {
				prefix : '/api/admin',
				payment : {
					add : '/payment/amount/config',
					pages : '/payment/amount/config/pages'
				}
			}

			$scope.addUrl = $scope.url.prefix + $scope.url.payment.add;
			$scope.pagesUrl = $scope.url.prefix + $scope.url.payment.pages;
			

			$scope.productCategories = null;
			$scope.categoryId = null;
			$scope.pageList = null;

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
				"validTo" : null
			},
			"sort" : {}
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
			
			if($scope.model.first) {
				$scope.model.prevPage = 0;
			} else {
				$scope.model.prevPage = $scope.model.number - 1;
			}
			
			if($scope.model.last) {
				$scope.model.nextPage = $scope.model.lastPage;
			} else {
				$scope.model.nextPage = $scope.model.number + 1;
			}
		}

		/** 初始化请求分页数据 */
		$http.post($scope.pagesUrl,//
		    $scope.criteria//
		)//
		.success(function(data) {
			$scope.pageSuccess(data);
		});

		/** 转到页面 */
		$scope.skipToPage = function(index) {
			$scope.validFrom = $("#validFrom").val();
			$scope.validTo = $("#validTo").val();

			/** paging */
			$scope.criteria.paging.index = index == 0 ? 0 : index;
			if($scope.criteria.paging.size == null || $scope.criteria.paging.size == 0){
				$scope.criteria.paging.size = 5;
			}
			/** keywords */
			$scope.criteria.keywords.validFrom = $scope.validFrom;
			$scope.criteria.keywords.validTo = $scope.validTo;
			

			/** 初始化请求分页数据 */
			$http.post($scope.pagesUrl,//
					$scope.criteria)//
			.success(function(data) {
				$scope.pageSuccess(data);
			});

		}

		
		$scope.setSize = function() {
			$scope.skipToPage(0)
		}
		
		$scope.create = function() {
			var newModel = {
				defaultAccountId : null,
				defaultAccountOpenId : null,
				validFrom : null,
			};
			var modalInstance = $modal.open({
				animation : 'am-flip-x',
				templateUrl : 'modules/payment/views/detail.html',
				controller : 'jnc-admin.payment.detail.controller',
				size : 'lg',
				resolve : {
					item : function() {
						return newModel;
					},
					toastr : function() {
						return toastr;
					}
				}
			});
			modalInstance.result.then(function(item) {
				$scope.item = item;
			}, function() {
			});
		}
		
		
	} ]);
})()