(function() {
	// Controller
	angular
			.module('jnc-admin')
			.controller(
					'jnc-admin.sparematerial.safestock.controller',
					[
							// Dependencies
							'$scope',
							'$http',
							'$routeParams',
							'$location',
							'$modal',
							// Main
							function($scope, $http, $routeParams, $location, $modal) {
								$scope.queryForm = {};
								$scope.materials = [];
								$scope.materials.length = 0;
								/**
								 * 搜索备用物料
								 */
								$scope.searchSpareMaterial = function(event) {
									if (typeof $scope.queryForm.positionId === 'undefined') {
										$scope.queryForm.positionId = '';
									}
									if (typeof $scope.queryForm.materialId === 'undefined') {
										$scope.queryForm.materialId = '';
									}
									var url = "../api/admin/safestock/querysafestock?positionId=" + $scope.queryForm.positionId + "&materialId="
											+ $scope.queryForm.materialId;
									var data = {};
									// 弹出正在加载窗口

									$http.get(url).success(function(data) {
										$scope.materials = [];
										// alert("success..."+data);
										$scope.materials = data;
									}).error(function(data) {
										$scope.materials = [];
										alert("没有找到符合条件的数据");
									});
								};

								$scope.searchSpareMaterial();

								$scope.importSafeStock = function() {
									window.location.href = $location.protocol() + "://" + $location.host() + ":" + $location.port()
											+ "/admin/#/sparematerial/safestockimport";
								};

								/**
								 * 导出功能
								 */
								$scope.exportSafeStock = function() {
									location.href = "../api/admin/safestock/exportsafestock?positionId=" + $scope.queryForm.positionId
											+ "&materialId=" + $scope.queryForm.materialId;
								};
								

								/**
								 * @function 弹出发货窗口
								 * @desc open the dialog to delivery safety stock
								 */
								$scope.openDialog = function() {
									var oSelectedItem = this.item;
									var modalInstance = $modal.open({
										animation : 'am-flip-x',
										templateUrl : 'modules/sparematerial/views/deliverysafetystockDialog.html',
										controller : 'jnc-admin.sparematerial.controllers.deliverysafetystockDialog',
										size : 'lg',
										resolve : {
											item : function() {
												return oSelectedItem;
											}
										}
									});
									modalInstance.result.then(function() {
										$scope.selected = selectedItem;
										}, function() {
										});
								};


								$scope.start = 0;
								$scope.currentPage = 0;
								$scope.data = [];

								$scope.changePageSize = function() {
									$scope.currentPage = 0;
								};
								$scope.PageConfigProperties = {
									pageSize : "10"
								};

								$scope.pageNoKeyDown = function(event) {
									if (event.keyCode === 13) {
										var pageNo = this.goToPage();
										$scope.currentPage = pageNo;
									}
								};

								$scope.goToPage = function() {
									if ($scope.pagenum) {
										if ($scope.pagenum > $scope.numberOfPages()) {
											return $scope.numberOfPages() - 1
										}
										else if ($scope.pagenum < -1) {
											return 0;
										}
										else {
											return $scope.pagenum - 1;
										}
									}
									else {
										return $scope.currentPage;
									}
								}

								$scope.numberOfPages = function() {
									return Math.ceil($scope.materials.length / $scope.PageConfigProperties.pageSize);
								}

								$scope.verificationReport = function(isPassed) {
									if (isPassed === 1) {
										alert("合格");
									}
									else {
										alert("不合格");
									}
								}

							} ]).filter('startFrom', function() {
				return function(input, start) {
					start = +start; // parse to int
					return input.slice(start);
				}
			})
})()
