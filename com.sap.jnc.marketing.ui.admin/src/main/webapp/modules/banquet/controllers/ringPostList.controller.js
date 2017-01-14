(function() {
	// Controller
	angular.module('jnc-admin').controller('jnc-admin.banquet.ringPostList.controller', [
	// Dependencies
	'$scope', '$http', '$routeParams', '$constant',
	// Main

	function($scope, $http, $routeParams, $constant) {
		// Initialize Example 1
		$('#postTime').datetimepicker({
			format : 'YYYY-MM-DD'
		});

		$('#datetimepicker3').datetimepicker({
			format : 'YYYY-MM-DD'
		});
		// $scope.start = 0;
		$('#datetimepicker4').datetimepicker({
			format : 'YYYY-MM-DD HH:mm'
		});

		$scope.criteria = {
			"paging" : {
				"index" : 1,
				"size" : 10
			},
			"keywords" : {
				"officeName" : null,
				"id" : null,
				"banquetTimeFrom" : null,
				"banquetTimeTo" : null,
				"banquetStatus" : null
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
		$scope.serverURL = $constant.URL_API_WECHAT;
		$scope.selectedReportItem = [];
		/* End define variable */

		$scope.pageNoKeyDown = function(event) {
			if (event.keyCode === 13) {
				this.switchPage();
			}
		};
		$scope.switchPage = function() {
			var pageNo = this.goToPage();
			$scope.criteria.paging.index = pageNo <= 1 ? 1 : pageNo;
			this.searchBanquetItem();
		}

		$scope.searchKeyDown = function(event) {
			if (event.keyCode === 13) {
				this.searchBanquetItem();
			}
		};
		$scope.changePageNo = function(action) {
			if (action == "first") {
				$scope.criteria.paging.index = 1;
			}
			else if (action == "previous") {
				$scope.criteria.paging.index = parseInt($scope.criteria.paging.index) - 1;
			}
			else if (action == "next") {
				$scope.criteria.paging.index = parseInt($scope.criteria.paging.index) + 1;
			}
			else if (action == "last") {
				$scope.criteria.paging.index = $scope.numberOfPages;
			}
			this.searchBanquetItem();
		};

		$scope.searchBanquetItem = function() {
			var banquetTimeFrom = $("#banquetTimeFrom").val();
			var banquetTimeTo = $("#banquetTimeTo").val();
			$http.post($scope.serverURL + 'banquets/pagnation?number=' + $scope.criteria.paging.index + '&pageSize=' + $scope.criteria.paging.size, {
				"officeName" : $scope.criteria.keywords.offficeName,
				"id" : $scope.criteria.keywords.id,
				"banquetTimeFrom" : banquetTimeFrom,
				"banquetTimeTo" : banquetTimeTo,
				"banquetStatus" : $scope.criteria.keywords.banquetStatus
			}).success(function(data) {
				$scope.banquetData = data;
				$scope.numberOfPages = data[0].totalPage
			});
		};

		$scope.hideMsgDialog = function(dialogId) {
			$('#' + dialogId).hide();
		}

		$scope.goToPage = function() {
			if ($scope.pagenum) {
				if ($scope.pagenum > $scope.numberOfPages) {
					return $scope.numberOfPages;
				}
				else if ($scope.pagenum <= 1) {
					return 1;
				}
				else {
					return $scope.pagenum;
				}
			}
		}

		$scope.saveBanquetItem = function() {
			$('#banquetDetailModal').hide();
			$http.put($scope.serverURL + 'banquets/' + this.formData.id, this.formData).success(function(data) {
				$scope.banquetData = data;
				$('#myModal').modal('show')
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
				$http.put($scope.serverURL + 'banquets/', {
					"banquetIds" : $scope.selectedReportItem,
					"status" : verifyStatus,
					"approveComment" : ""
				}).success(function(data) {
				});
			}
			else {
				var id = $scope.verificationFormData.id;
				var approveComment = $('#approvalComment').val();
				$http.put($scope.serverURL + 'banquets/', {
					"banquetIds" : [ id ],
					"status" : verifyStatus,
					"approveComment" : approveComment
				}).success(function(data) {
					$('#banquetVerificationModal').hide();
				});
			}
		}

		$('#banquetDetailModal').on('show.bs.modal', function(event) {
			var button = $(event.relatedTarget) // Button that triggered the modal
			var recipient = button.data('whatever') // Extract info from data-* attributes
			$http.get($scope.serverURL + 'wechat/banquets/' + recipient).success(function(data) {
				$scope.formData = data;
			});
		});

		$('#banquetVerificationModal').on('show.bs.modal', function(event) {
			var button = $(event.relatedTarget);
			var recipient = button.data('whatever');
			$scope.verificationFormData = {
				"id" : recipient
			};
			$scope.$apply();
		});
		$scope.initializePage = function() {
			$http.post($scope.serverURL + 'banquets/pagnation?number=1&pageSize=' + $scope.criteria.paging.size, {

			}).success(function(data) {
				$scope.banquetData = data;
				$scope.numberOfPages = data[0].totalPage
			});

			$http.get($scope.serverURL + 'banquetEnumType/BanquetStatus', {

			}).success(function(data) {
				$scope.banquetStatusEnum = data;
			});
		};
		$scope.toggleDisplay = function() {
			$("#searchRingPostPanel").collapse("toggle");
		};
		// $scope.initializePage();
	} ])
})()
