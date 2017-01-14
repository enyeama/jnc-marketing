/**
 * Madeleine
 */
(function() {
	// Controller
	angular.module('jnc-admin').controller(
			'jnc-admin.ka.ordermanagement.controller',
			[
					// Dependencies
					'$scope',
					'$http',
					'$modal',
					'$constant',
					'$authentication',
					// Main
					function($scope, $http, $modal, $constant, $authentication) {

						$(function() {
							$.datepicker.setDefaults($.datepicker.regional["zh-CN"]);

							$('#orderDate').datetimepicker({
								format : 'YYYY-MM-DD'
							});
							$('#orderStartDate').datetimepicker({
								format : 'YYYY-MM-DD'
							});
							$('#orderEndDate').datetimepicker({
								format : 'YYYY-MM-DD'
							});

							$("#orderDate").on("dp.change", function() {
								$scope.criteria.keywords.date = $("#orderDate").val();
							});

							$("#orderStartDate").on("dp.change", function() {
								$scope.criteria.keywords.dateFrom = $("#orderStartDate").val();
							});

							$("#orderEndDate").on("dp.change", function() {
								$scope.criteria.keywords.dateTo = $("#orderEndDate").val();
							});

							$scope.bootPrefix = $constant.URL_API;
							$scope.orderListAPI = $scope.bootPrefix + "kaorders/pages";
							$scope.orderDetailAPI = $scope.bootPrefix + "kaorders";
							$scope.branchNameListAPI = $scope.bootPrefix + "kadd/katerminalnames?externalId=";
							$scope.branchDetailByNameAPI = $scope.bootPrefix + "kadd/katerminaldetails";
							$scope.categoryListAPI = $scope.bootPrefix + "kadd/dmscategorys";
							$scope.productListByCategoryAPI = $scope.bootPrefix + "kadd/productnames?dmsName=";
							$scope.cityManagerListAPI = $scope.bootPrefix + "kaorders/citymanagers";
							$scope.deliveryEmployeeListAPI = $scope.bootPrefix + "kaorders/leaders";

							$scope.orderDateSelectOptionIsBetween = true;
							$scope.deliveryEmployeeType = "responsibleLeader";
							$scope.orderListData = [];

							$scope.orderStatus = [ {
								code : "WAITFORDELIVERY",
								name : "待发货"
							}, {
								code : "INDELIVERY",
								name : "配送中"
							}, {
								code : "FINISH",
								name : "完成"
							} ];

							$scope.criteria = {
								"paging" : {
									"index" : "0",
									"size" : "10"
								},
								"keywords" : {
									"kaSpecialistEmployeeId" : "901",
									"cityManagerEmployeeId" : null,
									"branchName" : null,
									"kaSystemNumber" : null,
									"terminalOrderStatus" : null,
									"validType" : null,
									"dateFrom" : null,
									"dateTo" : null,
									"date" : null
								},
								"sort" : {
									"id" : "ASC"
								}
							};

							$authentication.check(
							// Success
							function(data) {
								$scope.role = data.roles[0].name;
								$scope.employeeId = data.roles[0].id;
								$scope.employeeId = "326";
								$scope.positionId = "4459";
								$http.get($scope.branchNameListAPI + $scope.positionId).success(function(data) {
									$scope.branchNameList = _.pluck(data, "kaTerminalName");
									$("#branchName").autocomplete({
										source : $scope.branchNameList,
										select : function(event, ui) {
											$scope.criteria.keywords.branchName = ui.item.value;
										}
									})
								});
							},
							// Error
							function() {
								$scope.role = null;
								$scope.employeeId = null;
								$scope.positionId = null;
							});

							$http.get($scope.categoryListAPI).success(function(data) {
								/* $scope.dmsList = data; */

								$scope.dmsList = _.pluck(data, "dmsName");
							});
						});

						$scope.fetchOrderList = function(pageNo) {
							$scope.criteria.paging.index = pageNo - 1;
							$http.post($scope.orderListAPI, $scope.criteria).success(function(data) {
								$scope.currentPage = data.currentPage
								$scope.beginPage = data.beginPage
								$scope.endPage = data.endPage
								$scope.firstPage = data.firstPage
								$scope.lastPage = data.lastPage
								$scope.totalPage = data.totalPage
								$scope.orderListData = data.pageList

								var pageArray = [];
								for (var p = $scope.beginPage, idx = 0; p <= $scope.endPage; p++, idx++) {
									var pg = $scope.beginPage + idx;
									pageArray[idx] = pg;
								}
								$scope.pageArray = pageArray;
							});
						};

						$scope.createOrder = function() {
							var oSelectedItem = this.logistic;
							var modalInstance = $modal.open({
								animation : 'am-flip-x',
								templateUrl : 'modules/ka/views/editorder.html',
								controller : 'jnc-admin.ka.editorder.controller',
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

						$scope.cancelOrder = function(orderId) {
							$http.post($scope.orderDetailAPI + "/" + orderId + "/cancel").success(function(data) {
								$scope.fetchOrderList($scope.currentPage);
							});
						};

						$scope.changeOrderDateOption = function(sOption) {
							$scope.orderDateSelectOptionIsBetween = (sOption === "BETWEEN") ? true : false;
						}

						$('#assignOrderModal').on('show.bs.modal', function(event) {
							var button = $(event.relatedTarget)
							var orderId = button.data('key');

							$http.get($scope.orderDetailAPI + "/" + orderId).success(function(data) {
								$scope.orderDetail = data;

								if (!$scope.orderDetail.responsibleLeaderInfo) {
									$scope.orderDetail.responsibleLeaderInfo = {};
								}
								if (!$scope.orderDetail.responsibleLeaderPositionInfo) {
									$scope.orderDetail.responsibleLeaderPositionInfo = {};
								}

								var requestBody = {
									"cityManagerPositionExternalId" : $scope.orderDetail.cityManagerPositionInfo.externalId,
									"productId" : $scope.orderDetail.productInfo.id,
									"regionId" : null,
									"channelId" : null,
									"terminalId" : $scope.orderDetail.terminalInfo.id
								}

								$http.post($scope.deliveryEmployeeListAPI, requestBody).success(function(data) {
									if (data.length > 0) {
										if (data[0].dealerId) {
											$scope.deliveryEmployeeType = "dealer";
											$scope.dealerList = [];

											data.forEach(function(item) {
												var dealerInfo = {};
												dealerInfo.dealerId = item.dealerId;
												dealerInfo.dealerName = item.dealerName;
												$scope.dealerList.push(dealerInfo);
											});
										}
										else {
											$scope.deliveryEmployeeType = "responsibleLeader";
											$scope.respLeaderList = [];

											data.forEach(function(item) {
												var respLeaderInfo = {};
												respLeaderInfo.employeeId = item.employeeId;
												respLeaderInfo.employeeName = item.employeeName;
												respLeaderInfo.positionId = item.positionId;
												respLeaderInfo.positionName = item.positionName;
												$scope.respLeaderList.push(respLeaderInfo);
											});
										}
									}

								});
							});
						});

						$('#orderDetailModal').on('show.bs.modal', function(event) {
							var button = $(event.relatedTarget)
							$scope.mode = button.data('mode');
							var orderId = button.data('key');

							if ($scope.mode === "new") {

								$scope.modelTitle = "新建订单";
								$scope.orderDetail = {
									"orderTime" : moment(),
									"productDmsCategory" : null,
									"id" : null,
									"type" : null,
									"status" : "WAITFORDELIVERY",
									"createrType" : null,
									"createrId" : null,
									"complaint" : null,
									"comment" : null,
									"productInfo" : {
										"id" : null,
										"name" : null,
										"description" : null
									},
									"quantity" : 0,
									"createrEmployeeInfo" : {
										"id" : null,
										"externalId" : null,
										"name" : null,
										"phone" : null,
										"idCardNO" : null
									},
									"createrPositionInfo" : {
										"id" : null,
										"externalId" : null,
										"name" : null,
										"isHead" : false
									},
									"terminalInfo" : {
										"kaOffice" : {
											"id" : null,
											"externalId" : null,
											"name" : null,
											"validFrom" : null,
											"validTo" : null
										},
										"maintainerEmployee" : {
											"id" : null,
											"externalId" : null,
											"name" : null,
											"phone" : null,
											"idCardNO" : null
										},
										"maintainerPosition" : {
											"id" : null,
											"externalId" : null,
											"name" : null,
											"isHead" : false
										},
										"id" : null,
										"branchName" : null,
										"address" : null,
										"kaSystemName" : null,
										"kaSystemPropertyName" : null
									},
									"dealerInfo" : {
										"id" : null,
										"externalId" : null,
										"status" : null,
										"name" : null,
										"isPlatformDealer" : true,
										"address" : null
									},
									"responsibleLeaderInfo" : {
										"id" : null,
										"externalId" : null,
										"name" : null,
										"phone" : null,
										"idCardNO" : null
									},
									"responsibleLeaderPositionInfo" : {
										"id" : null,
										"externalId" : null,
										"name" : null,
										"isHead" : false
									},
									"cityManagerEmployeeInfo" : {
										"id" : null,
										"externalId" : null,
										"name" : null,
										"phone" : null,
										"idCardNO" : null
									},
									"cityManagerPositionInfo" : {
										"id" : null,
										"externalId" : null,
										"name" : null,
										"isHead" : true
									}
								};
								$scope.setBranchInfo();
								$scope.setOrderInfo();
							}
							else {
								$scope.modelTitle = "编辑订单";
								$http.get($scope.orderDetailAPI + "/" + orderId).success(function(data) {
									$scope.orderDetail = data;
									$scope.setBranchInfo();
									$scope.setOrderInfo();
								});
							}

						});

						$('#cancelOrderModal').on('show.bs.modal', function(event) {
							var button = $(event.relatedTarget)
							$scope.orderId = button.data('key');
						});

						$scope.setBranchInfo = function() {
							/*
							 * $http.get($scope.branchNameListAPI + $scope.positionId).success( function(data) { $scope.branchNameList = _.pluck(data,
							 * "kaTerminalName");
							 */
							$("#orderDetailBranchName").autocomplete(
									{
										source : $scope.branchNameList,
										select : function(event, ui) {
											$http.get($scope.branchDetailByNameAPI + "?externalId=" + $scope.positionId + "&kaName=" + ui.item.value)
													.success(function(data) {
														$scope.orderDetail.terminalInfo.id = data.kaId;

														$scope.orderDetail.terminalInfo.kaSystemName = data.kaSystemName;
														$scope.orderDetail.terminalInfo.address = data.kaAddress;

														$scope.orderDetail.terminalInfo.kaOffice.externalId = data.officeId;
														$scope.orderDetail.terminalInfo.kaOffice.name = data.officeName;

														$scope.orderDetail.terminalInfo.maintainerEmployee.id = data.maintainerEmployeeId;
														$scope.orderDetail.terminalInfo.maintainerEmployee.name = data.maintainerName;

														$scope.orderDetail.terminalInfo.maintainerPosition.id = data.maintainerPositionId;
														$scope.orderDetail.terminalInfo.maintainerPosition.name = data.maintainerPosition;

														$scope.setCityManagerInfo();
													});
										}
									});
							/* }); */
						};

						$scope.setOrderInfo = function() {
							/*
							 * $http.get($scope.categoryListAPI).success(function(data) { $scope.dmsList = data; $scope.dmsList = _.pluck(data,
							 * "dmsName");
							 */
							$("#orderDetailDmsCategory").autocomplete({
								source : $scope.dmsList,
								select : function(event, ui) {
									$scope.changeDmsCategory(ui.item.value);
								}
							});
							/* }); */

							if ($scope.orderDetail.productDmsCategory) {
								$http.get($scope.productListByCategoryAPI + $scope.orderDetail.productDmsCategory).success(function(data) {
									$scope.productList = data;
								});
							}

							if (($scope.orderDetail.terminalInfo) && ($scope.orderDetail.terminalInfo.kaOffice)
									&& ($scope.orderDetail.terminalInfo.kaOffice.externalId)) {
								$http.get($scope.cityManagerListAPI + "/" + $scope.orderDetail.terminalInfo.kaOffice.externalId).success(
										function(data) {
											$scope.cityManagerList = data;
										});
							}

						};

						$scope.setCityManagerInfo = function() {
							$http.get($scope.cityManagerListAPI + "/" + $scope.orderDetail.terminalInfo.kaOffice.externalId).success(function(data) {
								$scope.cityManagerList = data;
							});
						};

						$scope.changeDmsCategory = function(productDmsCategory) {
							$http.get($scope.productListByCategoryAPI + productDmsCategory).success(function(data) {
								$scope.productList = data;
							});
						};

						$scope.changeProduct = function(productName) {
							var selectedProduct = jQuery.grep($scope.productList, function(product) {
								return product.productName == productName;
							});
							$scope.orderDetail.productInfo.id = selectedProduct[0].productId;
						};

						$scope.changeCityManagerPosition = function(positionName) {
							var selectedCityManager = jQuery.grep($scope.cityManagerList, function(cityManager) {
								return cityManager.positionName == positionName;
							});
							$scope.orderDetail.cityManagerPositionInfo.id = selectedCityManager[0].positionId;
							$scope.orderDetail.cityManagerPositionInfo.externalId = selectedCityManager[0].positionExternalId;
							$scope.orderDetail.cityManagerEmployeeInfo.id = selectedCityManager[0].employeeId;
							$scope.orderDetail.cityManagerEmployeeInfo.externalId = selectedCityManager[0].employeeExternalId;
							$scope.orderDetail.cityManagerEmployeeInfo.name = selectedCityManager[0].employeeName;
						};

						$scope.changeRespLeaderPosition = function(positionName) {
							var selectedRespLeader = jQuery.grep($scope.respLeaderList, function(respLeader) {
								return respLeader.positionName == positionName;
							});
							$scope.orderDetail.responsibleLeaderPositionInfo.id = selectedRespLeader[0].positionId;
							$scope.orderDetail.responsibleLeaderInfo.id = selectedRespLeader[0].employeeId;
							$scope.orderDetail.responsibleLeaderInfo.name = selectedRespLeader[0].employeeName;
						};

						$scope.changeDealerInfo = function(dealerName) {
							var selectedDealer = jQuery.grep($scope.dealerList, function(dealer) {
								return dealer.dealerName == dealerName;
							});
							$scope.orderDetail.dealerInfo.id = selectedDealer[0].dealerId;
						}

						$scope.saveOrder = function() {
							switch ($scope.modelTitle) {
								case "编辑订单":
									$scope.updateOrder();
									break;
								case "新建订单":
									$scope.createOrder();
									break;
								default:
									break;
							}
						};

						$scope.createOrder = function() {
							$scope.orderDetail.createrId = $scope.employeeId;
							$http.post($scope.orderDetailAPI, $scope.orderDetail).success(function(data) {
								$scope.orderDetail.id = data.id;
								$scope.fetchOrderList($scope.lastPage);
							});

						};

						$scope.updateOrder = function() {
							$http.put($scope.orderDetailAPI, $scope.orderDetail).success(function(data) {
								$scope.fetchOrderList($scope.currentPage);
							});

						};

						$scope.setSize = function() {
							$scope.fetchOrderList(1);
						};

						$scope.toggleDisplay = function() {
							$("#searchOrdersPanel").collapse("toggle");
						};

						$scope.clearQueryCriteria = function() {
							$scope.criteria = {
								"paging" : {
									"index" : "0",
									"size" : "10"
								},
								"keywords" : {
									"kaSpecialistEmployeeId" : "901",
									"cityManagerEmployeeId" : null,
									"branchName" : null,
									"kaSystemNumber" : null,
									"terminalOrderStatus" : null,
									"validType" : null,
									"dateFrom" : null,
									"dateTo" : null,
									"date" : null
								},
								"sort" : {
									"id" : "ASC"
								}
							};
						}

					} ]);

})()
