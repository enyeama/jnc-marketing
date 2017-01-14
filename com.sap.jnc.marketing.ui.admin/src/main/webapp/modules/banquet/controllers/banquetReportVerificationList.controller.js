(function() {
	// Controller
	angular.module('jnc-admin').controller(
			'jnc-admin.banquet.banquetReportVerificationList.controller',
			[
					// Dependencies
					'$scope',
					'$http',
					'$routeParams',
					'$constant',
					// Main

					function($scope, $http, $routeParams, $constant) {
						// Initialize Example 1
						$('#expressTimeFrom').datetimepicker({
							format : 'YYYY-MM-DD'
						});
						$('#expressTimeTo').datetimepicker({
							format : 'YYYY-MM-DD'
						});
						$("#searchDate").datetimepicker({
							format : 'YYYY-MM-DD'
						});
						$('#banquetTime').datetimepicker({
							format : 'YYYY-MM-DD'
						});
						$('#deliveryTime').datetimepicker({
							format : 'YYYY-MM-DD'
						});
						$('#banquetFormTime').datetimepicker({
							format : 'YYYY-MM-DD'
						});
						$('#planArriveTime').datetimepicker({
							format : 'YYYY-MM-DD'
						});
						$('#expressTime').datetimepicker({
							format : 'YYYY-MM-DD'
						});
						$('#planArriveTime').datetimepicker({
							format : 'YYYY-MM-DD'
						});

						$scope.operate = $routeParams.operate;
						$scope.loginUserName = "测试超级管理员01";

						$scope.banquetQueryEnum = {
							"banquetStatusEnum" : [ {
								name : "待提报",
								value : "WAITFORAPPLY"
							}, {
								name : "已邮寄待签收",
								value : "DELIVERED"
							}, {
								name : "待核数",
								value : "WAITFORVERIFY"
							} ],
							"banquetPeriodEnum" : [ {
								name : "中午",
								value : "NOON"
							}, {
								name : "晚上",
								value : "NIGHT"
							}, {
								name : "中午和晚上",
								value : "NOON_AND_NIGHT"
							} ],
							"office" : []
						}
						$scope.criteria = {
							"paging" : {
								"index" : 0,
								"size" : 10
							},
							"keywords" : {
								"expressTimeFrom" : null,
								"expressTimeTo" : null,
								"banquetId" : null,
								"status" : "WAITFORAPPLY",
								"dateSelectOption" : "between"
							},
							"sort" : {
							// "externalId" : "ASC"
							},
							"pageConfig" : [ {
								"value" : 10,
								"label" : 10
							}, {
								"value" : 20,
								"label" : 20
							}, {
								"value" : 50,
								"label" : 50
							}, {
								"value" : 100,
								"label" : 100
							}, {
								"value" : 250,
								"label" : 250
							} ]
						};

						/* Start define variable */
						$scope.apiAdminURL = $constant.URL_API;
						$scope.selectedReportItem = [];

						$scope.banquetVerificationData = {
							"id" : "",
							"expressNO" : "",
							"expressTime" : new Date(),
							"planArriveTime" : "",
							"applyTime" : "",
							"expressCompany" : "",
							"status" : "WAITFORAPPLY",
							"bottleQuantityValue" : "",
							"bottleQuantityUnit" : "个",
							"creatorId" : "101",
							"officeId" : "",
							"officeName" : "",
							"salesChannel" : "宴会",
							"banquetId" : "",
							"boxQuantity" : "",
							"scanType" : "",
							"enableSave" : false
						};
						/* End define variable */

						$scope.dateSelectOptionIsBetween = true;

						$scope.pageNoKeyDown = function(event) {
							if (event.keyCode === 13) {
								this.switchPage();
							}
						};
						$scope.switchPage = function() {
							var pageNo = this.goToPage();
							$scope.criteria.paging.index = pageNo <= 0 ? 0 : pageNo;
							this.searchBanquetItem();
						}
						$scope.changePageSize = function(action) {
							$scope.criteria.paging.index = 0;
							this.searchBanquetItem();
						};
						$scope.changePageNo = function(action) {
							var currentPageIndex = $scope.criteria.paging.index;
							if (action == "first") {
								$scope.criteria.paging.index = 0;
							}
							else if (action == "previous") {
								$scope.criteria.paging.index = currentPageIndex - 1 < 0 ? 0 : currentPageIndex - 1;
							}
							else if (action == "next") {
								var nextPage = currentPageIndex >= $scope.numberOfPages - 1 ? $scope.numberOfPages - 1 : currentPageIndex + 1;
								$scope.criteria.paging.index = nextPage;
							}
							else if (action == "last") {
								$scope.criteria.paging.index = $scope.numberOfPages - 1;
							}
							this.searchBanquetItem();
						};

						$scope.searchBanquetItem = function() {
							var expressTimeFrom = $("#expressTimeFrom").val();
							var expressTimeTo = $("#expressTimeTo").val();
							var banquetOffice = $("#banquetOffice").val();
							if (expressTimeFrom !== "") {
								$scope.criteria.keywords.expressTimeFrom = this.processDateTime(expressTimeFrom);
							}
							else {
								$scope.criteria.keywords.expressTimeFrom = "";
							}
							if (expressTimeTo !== "") {
								$scope.criteria.keywords.expressTimeTo = this.processDateTime(expressTimeTo);
							}
							else {
								$scope.criteria.keywords.expressTimeTo = "";
							}

							$http.post($scope.apiAdminURL + 'banquets/verifications/applications/pages', {
								"paging" : $scope.criteria.paging,
								"keywords" : $scope.criteria.keywords
							}).success(function(data) {
								$scope.banquetData = data.content;
								$scope.numberOfPages = data.totalPages
							});
						};
						$scope.changeDateOption = function(sOption) {
							$scope.dateSelectOptionIsBetween = (sOption === "between") ? true : false;
						}
						$scope.hideMsgDialog = function(dialogId) {
							$('#' + dialogId).hide();
						}

						$scope.goToPage = function() {
							if ($scope.pagenum) {
								if ($scope.pagenum > $scope.numberOfPages) {
									return $scope.numberOfPages - 1;
								}
								else if ($scope.pagenum <= 1) {
									return 0;
								}
								else {
									return $scope.pagenum - 1;
								}
							}
						}
						$scope.processDateTime = function(dateStr) {
							var tmpDate = dateStr.split("-");
							var processedDate = "";
							processedDate = tmpDate[1] + "," + tmpDate[2] + "," + tmpDate[0];
							return new Date(processedDate).getTime();
						};
						$scope.getBanquetBySheetNo = function(evt) {
							$http.get($scope.apiAdminURL + 'banquets/' + evt.banquetVerificationData.banquetId).success(function(data) {
								if (data.cashTime && data.cashTime != null) {
									$scope.banquetVerificationData.scanType = data.scanType
									$scope.banquetVerificationData.enableSave = true;
								}
								else {
									$scope.banquetVerificationData.enableSave = false;
									toastr.error('宴会未兑付不能核销!');
								}

							});
							$http.get($scope.apiAdminURL + 'banquets/verifications/applications/boxes/' + evt.banquetVerificationData.banquetId)
									.success(function(data) {
										$scope.banquetVerificationData.maxboxnumber = data;
									});
						};
						$scope.saveVerificationItem = function() {
							var expressTime = $('#expressTime').val();
							var planArriveTime = $('#planArriveTime').val();
							$scope.banquetVerificationData.expressTime = this.processDateTime(expressTime);
							$scope.banquetVerificationData.planArriveTime = this.processDateTime(planArriveTime);
							if($scope.banquetVerificationData.scanType=='QRCODE'){
								if (parseInt($scope.banquetVerificationData.bottleQuantityValue) < 0) {
									alert("瓶盖数必须大于0.")
									return false;
								}
								if(parseInt($scope.banquetVerificationData.bottleQuantityValue) > parseInt($scope.banquetVerificationData.maxboxnumber)){
									alert("瓶盖数大于可兑付数.")
									return false;
								}
							}
							if (parseInt($scope.banquetVerificationData.boxQuantity) < 0) {
								alert("盒盖数必须大于0.")
								return false;
							}
							else if (parseInt($scope.banquetVerificationData.boxQuantity) > parseInt($scope.banquetVerificationData.maxboxnumber)) {
								alert("盒盖数大于可兑付数.")
								return false;
							}
							
							$http.post($scope.apiAdminURL + 'banquets/verifications/applications/application', $scope.banquetVerificationData)
									.success(function(data) {
										toastr.success('已正确处理', '核销申请已保存!');
										var bConfirm = confirm("核销申请单已经生成，是否增加下一条？");
										if (bConfirm) {
											$scope.banquetVerificationData.banquetId = "";
											$scope.banquetVerificationData.boxQuantity = "";
											$scope.banquetVerificationData.enableSave = false;
										}
										else {
											$("#cancelBtn").trigger("click");
										}
									});

							

						};
						$scope.updateSelected = function(id, name) {
							if (id == "selectAll") {
								$('input[name="banquetReportItem"]').each(function() {
									var checked = !!$("#selectAll")[0].checked;
									$(this).prop("checked", checked);
									if (checked) {
										$scope.selectedReportItem.push(parseInt($(this)[0].id));
									}
									else {
										$scope.selectedReportItem.length = 0;
									}

								});
							}
							else {
								if ($("#" + id)[0].checked == true) {
									$scope.selectedReportItem.push(id);
								}
								else {
									var idx = $scope.selectedReportItem.indexOf(id);
									$scope.selectedReportItem.splice(idx, 1);
									$("#selectAll").prop("checked", false);
								}
							}
						};

						$('#banquetVerificationModal').on(
								'show.bs.modal',
								function(event) {
									var button = $(event.relatedTarget) // Button that triggered the modal
									var recipient = button.data('whatever') // Extract info from data-* attributes
									var action = button.data('action')
									var parentCmp = $scope;
									if (recipient == "") {
										$scope.banquetVerificationData = {
												"id" : "",
												"expressNO" : "",
												"expressTime" : new Date(),
												"planArriveTime" : "",
												"applyTime" : "",
												"expressCompany" : "",
												"status" : "WAITFORAPPLY",
												"bottleQuantityValue" : "",
												"bottleQuantityUnit" : "个",
												"creatorId" : "101",
												"creatorName": $scope.loginUserName,
												"officeId" : "",
												"officeName" : "",
												"salesChannel" : "宴会",
												"banquetId" : "",
												"boxQuantity" : "",
												"scanType" : "",
												"enableSave" : false
										};
									}
									else {
										$http.get($scope.apiAdminURL + 'banquets/verifications/applications/' + recipient).success(
												function(data) {
													$scope.banquetVerificationData = data;
													$scope.banquetVerificationData['enableSave'] = true;
													$scope.banquetVerificationData.salesChannel = "宴会";
													$http.get($scope.apiAdminURL + 'banquets/' + data.banquet.id).success(function(data) {
														$scope.banquetVerificationData.scanType = data.scanType

													});
													$http.get($scope.apiAdminURL + 'banquets/verifications/applications/boxes/' + data.banquet.id)
															.success(function(data) {
																$scope.banquetVerificationData.maxboxnumber = data;
															});

												});
										$scope.$watch('banquetVerificationData.expressTime', function(newValue) {
											var newDate = new Date(newValue);
											$("#expressTime").val(newDate.getFullYear() + "-" + (newDate.getMonth() + 1) + "-" + newDate.getDate());
										});
										$scope.$watch('banquetVerificationData.planArriveTime', function(newValue) {
											var newDate = new Date(newValue);
											$("#planArriveTime")
													.val(newDate.getFullYear() + "-" + (newDate.getMonth() + 1) + "-" + newDate.getDate());
										});
									}
								});

						$('#banquetVerificationModal').on('show.bs.modal', function(event) {
							var button = $(event.relatedTarget);
							var recipient = button.data('whatever');
							$scope.verificationFormData = {
								"id" : recipient
							};
							$scope.$apply();
						});
						$scope.toggleDisplay = function() {
							$("#searchBanquetReportPanel").collapse("toggle");
						};

						$scope.submitforVerification = function() {
							if ($scope.selectedReportItem.length == 0) {
								alert("请至少选择一项!");
							}
							else {
								$http.post($scope.apiAdminURL + '/banquets/verifications/applications', $scope.selectedReportItem).success(
										function(data) {

										});
							}
						};

						$scope.initializePage = function() {
							$http.post($scope.apiAdminURL + 'banquets/verifications/applications/pages', {
								"paging" : $scope.criteria.paging,
								"keywords" : $scope.criteria.keywords
							}).success(function(data) {
								$scope.banquetData = data.content;
								$scope.numberOfPages = data.totalPages
							});

							$http.get($scope.apiAdminURL + 'banquets/verifications/applications/offices/' + 101, {}).success(function(data) {
								$scope.currentOffice = data.name;
							});
						}
						$scope.initializePage();
					} ]).filter('getBanquetStatus', function() {
		var banquetStatusText = function(status, statusEnum) {
			for ( var idx in statusEnum) {
				if (statusEnum[idx].value === status) {
					return statusEnum[idx].name
				}
			}
			return "";
		};
		return banquetStatusText;
	}).filter('getBanquetPeriod', function() {
		var banquetPeriodText = function(period, periodEnum) {
			for ( var idx in periodEnum) {
				if (periodEnum[idx].value === period) {
					return periodEnum[idx].name
				}
			}
			return "";
		};
		return banquetPeriodText;
	}).filter('getBanquetVerificationStatus', function() {
		var banquetVerificationStatusText = function(status, statusEnum) {
			for ( var idx in statusEnum) {
				if (statusEnum[idx].value === status) {
					return statusEnum[idx].name
				}
			}
			return "";
		};
		return banquetVerificationStatusText;
	})
})()
