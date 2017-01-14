(function() {
	// Controller
	angular.module('jnc-admin').controller('jnc-admin.banquet.banquetWriteoffList.controller', [
	// Dependencies
	'$scope', '$http', '$constant',
	// Main

	function($scope, $http, $constant) {
		// Initialize Example 1
		$('#banquetTimeFrom').datetimepicker({
			format : 'YYYY-MM-DD'
		});
		$('#banquetTimeTo').datetimepicker({
			format : 'YYYY-MM-DD'
		});
		
		$scope.loginUserName = "测试超级管理员01";

		$scope.banquetQueryEnum = {
			"banquetStatusEnum" : [ {
				name : "已报备待审核",
				value : "APPLIED"
			}, {
				name : "已审核待兑付",
				value : "APPROVED"
			}, {
				name : "审核未通过",
				value : "NOT_APPROVED"
			}, {
				name : "已取消",
				value : "CANCELLED"
			}],
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
			"office": []
		}
		$scope.criteria = {
			"paging" : {
				"index" : 0,
				"size" : 10
			},
			"keywords" : {
				"officeName" : null,
				"id" : null,
				"banquetTimeFrom" : null,
				"banquetTimeTo" : null,
				"status" : 'APPLIED',
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
		$scope.banquetVerificationData={
				"id":"",
				"expressNO":"",
				"expressTime":"",
				"planArriveTime":"",
				"applyTime":"",
				"expressCompany":"",
				"status":"",
				"verifier":$scope.loginUserName,
				"verifiedFinishTime":"",
				"bottleQuantityValue":"",
				"bottleQuantityUnit":"个",
				"verifiedBottleQuantity":"",
				"creatorId":"",
				"officeId":"",
				"officeName":"",
				"salesChannel":"宴会",
				"banquetId":"",
				"boxQuantity":"",
				"scanType": "",
				"enableSave": false
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
		$scope.cancelBanquetItem = function() {
			$http.get($scope.apiAdminURL + 'banquets/cancellation/' + this.formData.id).success(function(data) {
				toastr.error('宴会已经取消!');
				$("#cancelBtn").trigger("click");
			});
		};
		$scope.searchBanquetItem = function() {
			var banquetTimeFrom = $("#banquetTimeFrom").val();
			var banquetTimeTo = $("#banquetTimeTo").val();
			var banquetOffice = $("#banquetOffice").val();
			if (banquetTimeFrom !== "") {
				$scope.criteria.keywords.banquetTimeFrom = this.processDateTime(banquetTimeFrom);
			}
			else {
				$scope.criteria.keywords.banquetTimeFrom = "";
			}
			if (banquetTimeTo !== "") {
				$scope.criteria.keywords.banquetTimeTo = this.processDateTime(banquetTimeTo);
			}
			else {
				$scope.criteria.keywords.banquetTimeTo = "";
			}

			$scope.criteria.keywords.officeName = banquetOffice;
			$http.post($scope.apiAdminURL + 'banquets/pages', {
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

		$scope.saveBanquetItem = function() {
			var submitFormData = jQuery.extend(true, {}, this.formData);
			var banquetTimeText = $("#banquetFormTime").val();
			submitFormData.banquetTime = new Date(banquetTimeText).getTime()
			delete submitFormData['officeDirector'];
			$http.post($scope.apiAdminURL + 'banquets/banquet/' + this.formData.id, submitFormData).success(function(data) {
			
				$("#cancelBtn").trigger("click");
				//$('#myModal').modal('show')
			});
		};
		$scope.processDateTime = function(dateStr){
			var tmpDate=dateStr.split("-");
			var processedDate="";
		    processedDate=tmpDate[1]+","+tmpDate[2]+","+tmpDate[0];		
			return new Date(processedDate).getTime();
		};
		$scope.getBanquetBySheetNo = function(evt){
			$http.get($scope.apiAdminURL + 'banquets/box/number/' + evt.banquetVerificationData.id).success(function(data) {
				if(data.cityManager.id=="6"){
					$scope.banquetVerificationData.enableSave=true;
					$scope.banquetVerificationData.scanType=data.scanType
					if(data.office){
						$scope.banquetVerificationData.officeName= data.office.name;
						$scope.banquetVerificationData.officeId= data.office.id;
					}
				}else{				
					$scope.banquetVerificationData.enableSave=false;
					alert("报备人跟登录人信息不符！");
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

		$scope.verificationReport = function(verifyStatus, isMultiple) {
			if (isMultiple) {
				$http.post($scope.apiAdminURL + 'banquets/', {
					"banquetIds" : $scope.selectedReportItem,
					"status" : verifyStatus,
					"approveComment" : ""
				}).success(function(data) {
					 toastr.success('已正确处理','批量审核完成!');	
				});
			}
			else {
				var id = $scope.formData.id;
				var approveComment = $('#approvalComment').val();
				$http.post($scope.apiAdminURL + 'banquets/', {
					"banquetIds" : [ id ],
					"status" : verifyStatus,
					"approveComment" : approveComment
				}).success(function(data) {
					 toastr.success('已正确处理','审核完成!');	
				});
			}
		}

		$('#banquetDetailModal').on('show.bs.modal', function(event) {
			var button = $(event.relatedTarget) // Button that triggered the modal
			var recipient = button.data('whatever') // Extract info from data-* attributes
			var action = button.data('action')
			var parentCmp = $scope;
			$http.get($scope.apiAdminURL + 'banquets/' + recipient).success(function(data) {
				$scope.formData = data;
				$scope.formData.action = action;
				/*
				 * $http.post($scope.apiAdminURL + '/api/banquets/channel',"NORMAL" ).success(function(data) { });
				 */
				$.ajax({
					type : "POST",
					url : $scope.apiAdminURL + 'banquets/channel',
					data : JSON.stringify("NORMAL"),
					contentType : "application/json",
					success : function(data) {
						$scope.banquetVersionData = data.channel;
					}
				});

				$scope.$watch('formData.banquetTime', function(newValue) {
					var newDate = new Date(newValue);
					$scope.formData.banquetTime = newDate.getFullYear() + "-" + (newDate.getMonth() + 1) + "-" + newDate.getDate();
				});
			});
		});

		$scope.toggleDisplay = function() {
			$("#searchBanquetReportPanel").collapse("toggle");
		};
		$scope.initializePage = function() {
			$http.post($scope.apiAdminURL + 'banquets/pages', {
				"paging" : $scope.criteria.paging,
				"keywords" : $scope.criteria.keywords
			}).success(function(data) {
				$scope.banquetData = data.content;
				$scope.numberOfPages = data.totalPages
			}).error(function(oEv) {
				$scope.loadList = null;
			});
			$http.get($scope.apiAdminURL + 'banquets/office', {

			}).success(function(data) {
				$("#banquetOffice").autocomplete({
					source : data
				});
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
	})
})()
