/**
 * @author Zero Yu
 */
(function() {
	// Controller
	angular.module('jnc-admin').controller('jnc-admin.audit.auditQuery.controller', [
	// Dependencies
	'$scope', '$http', '$constant', '$routeParams', '$filter',
	// Main
	function($scope, $http, $constant, $routeParams, $filter) {
		$('#createDateFrom').datetimepicker({
			format : 'YYYY-MM-DD'
		});

		$('#createDateTo').datetimepicker({
			format : 'YYYY-MM-DD'
		});

		$('#auditDateFrom').datetimepicker({
			format : 'YYYY-MM-DD'
		});

		$('#auditDateTo').datetimepicker({
			format : 'YYYY-MM-DD'
		});

		$('#assignDateFrom').datetimepicker({
			format : 'YYYY-MM-DD'
		});

		$('#assignDateTo').datetimepicker({
			format : 'YYYY-MM-DD'
		});
		// $scope.serverURL = "http://localhost:13080/admin";
		// ''
		$scope.pagenum = '';
		$scope.currentRole = $routeParams.role;
		$scope.currentAuditTypeName = '';
		$scope.auditorModel = {};
		$scope.aduitQueryEnum = {
			auditType : [ {
				name : "宴会稽核",
				value : "BANQUET"
			}, {
				name : "陈列稽核",
				value : "EXHIBITION"
			}, {
				name : "终端稽核",
				value : "TERMINAL"
			}, {
				name : "访销稽核",
				value : "PROMOTION"
			} ],
			auditRegion : [ {
				value : 1,
				name : "成都"
			}, {
				value : 2,
				name : "绵竹"
			} ],
			approvalStatus : [ {
				name : "稽核通过",
				value : "CORRECT"
			}, {
				name : "稽核不通过",
				value : "FAILD"
			} ],
			auditStatus : [ {
				name : "全部",
				value : ""
			}, {
				name : "待办稽核",
				value : "PENDING"
			}, {
				name : "已分配",
				value : "ASSIGNED"
			}, {
				name : "稽核员退回",
				value : "RETURNED"
			}, {
				name : "稽核结果已确认",
				value : "CONFIRMED"
			}, {
				name : "稽核失效",
				value : "EXPIRED"
			} ]

		};
		$scope.criteria = {
			"paging" : {
				"index" : 0,
				"size" : 10
			},
			"keywords" : {
				"queryType" : 0,
				"provinceRegion" : null,
				"cityRegion" : null,
				"countyRegion" : null,
				"auditorId" : null,
				"auditorPositionId" : null,
				"createDateFrom" : null,
				"createDateTo" : null,
				"status" : null,
				"result" : null,
				"auditDateFrom" : null,
				"auditDateTo" : null,
				"assignDateFrom" : null,
				"assignDateTo" : null,
				"type" : "TERMINAL"
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
			} ],
			"role" : "",
			"operate" : ""
		};
		// 600 省区经理
		if ($routeParams.role == 600) {
			$scope.criteria.keywords.queryType = 1;
		}
		// 12 市管部
		if ($routeParams.role == 12) {
			$scope.criteria.keywords.queryType = 2;
		}
		$scope.criteria.role = $routeParams.role;
		$scope.criteria.operate = $routeParams.operate;
		$("#createDateFrom").on("dp.change", function() {
			$scope.criteria.keywords.createDateFrom = $("#createDateFrom").val();
		});

		$("#createDateTo").on("dp.change", function() {
			$scope.criteria.keywords.createDateTo = $("#createDateTo").val();
		});

		$("#auditDateFrom").on("dp.change", function() {
			$scope.criteria.keywords.auditDateFrom = $("#auditDateFrom").val();
		});

		$("#auditDateTo").on("dp.change", function() {
			$scope.criteria.keywords.auditDateTo = $("#auditDateTo").val();
		});

		$("#assignDateFrom").on("dp.change", function() {
			$scope.criteria.keywords.assignDateFrom = $("#assignDateFrom").val();
		});

		$("#assignDateTo").on("dp.change", function() {
			$scope.criteria.keywords.assignDateTo = $("#assignDateTo").val();
		});

		$scope.clearAduitQuery = function() {
			for ( var prop in $scope.criteria.keywords) {
				if ($scope.criteria.keywords.hasOwnProperty(prop)) {
					if (prop == "auditType") {
						$scope.criteria.keywords[prop] = "TERMINAL";
					}
					else {
						delete $scope.criteria.keywords[prop];
					}

				}
			}
		};

		$scope.selectedAuditItem = [];
		$scope.assignAuditor = function() {
			if ($scope.selectedAuditItem == "") {
				alert("请至少选择一条稽核条目!");
			}
			else {

				var auditsArray = [];
				var auditor = JSON.parse($scope.auditorModel.auditor);
				// var selectedAuditArray = $scope.selectedAuditItem.split(",");
				for (var i = 0; i < $scope.selectedAuditItem.length; i++) {
					auditsArray.push({
						"id" : $scope.selectedAuditItem[i],
						"auditorId" : auditor.id,
						"auditorPositionId" : auditor.positionId
					})
				}

				$http.post($constant.URL_API + "audits/assignments", {
					"audits" : auditsArray
				}).success(function(data) {
					alert("保存完成！");
				});
			}

		};
		$scope.processStatus = function(val) {
			var auditObjArray = [];
			var selectedAuditArray = $scope.selectedAuditItem;
			for (var i = 0; i < selectedAuditArray.length; i++) {
				auditObjArray.push({
					"id" : selectedAuditArray[i],
					"auditStatus" : val
				})
			}
			$http.post($constant.URL_API + "audits/audit", {
				"audits" : auditObjArray
			}).success(function(tableData) {
				$scope.queryByConditions($scope.criteria.keywords.type);
			}).error(function() {
			});
		};

		$scope.pageNoKeyDown = function(event) {
			if (event.keyCode === 13) {
				this.switchPage();
			}
		};

		$scope.queryCityByProvinceId = function(provinceId) {
			$http.get($constant.URL_API + "/audits/region/city/" + provinceId, {

			}).success(function(data) {
				$scope.aduitQueryEnum.cityEnum = data;
			});
		}

		$scope.queryCountyByCityId = function(provinceId, cityId) {
			$http.get($constant.URL_API + "/audits/region/county/" + provinceId + "/" + cityId, {

			}).success(function(data) {
				$scope.aduitQueryEnum.countyEnum = data;
			});
		}

		$scope.updateSelected = function(id) {
			if ($("#" + id)[0].checked == true) {
				$scope.selectedAuditItem.push(id);
			}
			else {
				var idx = $scope.selectedAuditItem.indexOf(id);
				$scope.selectedAuditItem.splice(idx, 1);
			}
		};
		$scope.switchPage = function() {
			var pageNo = this.goToPage();
			$scope.criteria.paging.index = pageNo <= 0 ? 0 : pageNo - 1;
			this.queryByConditions($scope.criteria.keywords.type);
		}

		$scope.searchKeyDown = function(event) {
			if (event.keyCode === 13) {
				this.queryByConditions($scope.criteria.keywords.type);
			}
		};
		$scope.searchAuditItem = function(type) {
			$scope.criteria.paging.index = 0;
			var createDateFrom = $("#createDateFrom").val();
			var createDateTo = $("#createDateTo").val();
			var auditDateFrom = $("#auditDateFrom").val();
			var auditDateTo = $("#auditDateTo").val();
			var auditAssignDateFrom = $("#assignDateFrom").val();
			var auditAssignDateTo = $("#assignDateTo").val();
			if (createDateFrom !== "") {
				$scope.criteria.keywords.createDateFrom = this.processDateTime(createDateFrom, 'start');
			}
			else {
				$scope.criteria.keywords.createDateFrom = "";
			}
			if (createDateTo !== "") {
				$scope.criteria.keywords.createDateTo = this.processDateTime(createDateTo, 'end');
			}
			else {
				$scope.criteria.keywords.createDateTo = "";
			}
			if (auditDateFrom !== "") {
				$scope.criteria.keywords.auditDateFrom = this.processDateTime(auditDateFrom, 'start');
			}
			else {
				$scope.criteria.keywords.auditDateFrom = "";
			}
			if (auditDateTo !== "") {
				$scope.criteria.keywords.auditDateTo = this.processDateTime(auditDateTo, 'end');
			}
			else {
				$scope.criteria.keywords.auditDateTo = "";
			}
			if (auditAssignDateFrom !== "") {
				$scope.criteria.keywords.assignDateFrom = this.processDateTime(auditAssignDateFrom, 'start');
			}
			else {
				$scope.criteria.keywords.assignDateFrom = "";
			}
			if (auditAssignDateTo !== "") {
				$scope.criteria.keywords.assignDateTo = this.processDateTime(auditAssignDateTo, 'end');
			}
			else {
				$scope.criteria.keywords.assignDateTo = "";
			}
			if ($scope.criteria.keywords.createDateFrom !== "" && $scope.criteria.keywords.createDateTo !== "") {
				if (createDateTo < createDateFrom) {
					alert("结束日期不能小于开始日期!");
					return false;
				}
			}

			if ($scope.criteria.keywords.auditDateFrom !== "" && $scope.criteria.keywords.auditDateTo !== "") {
				if (auditDateTo < auditDateFrom) {
					alert("结束日期不能小于开始日期!");
					return false;
				}
			}

			if ($scope.criteria.keywords.auditAssignDateFrom !== "" && $scope.criteria.keywords.auditAssignDateTo !== "") {
				if (auditAssignDateTo < auditAssignDateFrom) {
					alert("结束日期不能小于开始日期!");
					return false;
				}
			}

			this.queryByConditions(type);
		};
		$scope.processDateTime = function(dateStr, type) {
			var tmpDate = dateStr.split("-");
			var processedDate = "";
			if (type == 'start') {
				processedDate = tmpDate[1] + "," + tmpDate[2] + "," + tmpDate[0];
			}
			else if (type == 'end') {
				processedDate = tmpDate[1] + "," + (parseInt(tmpDate[2]) + 1) + "," + tmpDate[0];
			}
			else {
				return "";
			}
			return new Date(processedDate).getTime();
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
			this.queryByConditions($scope.criteria.keywords.type);
		};
		$scope.goToPage = function() {

			if ($scope.pagenum > $scope.numberOfPages) {
				return $scope.numberOfPages;
			}
			else if ($scope.pagenum <= 0) {
				return 0;
			}
			else {
				return $scope.pagenum;
			}
		}
		$scope.changeAuditName = function(type) {
			for ( var i in $scope.aduitQueryEnum.auditType) {
				if ($scope.aduitQueryEnum.auditType[i].value == type) {
					$scope.currentAuditTypeName = $scope.aduitQueryEnum.auditType[i].name;
				}
			}
		};
		$scope.queryByConditions = function(type) {
			this.changeAuditName(type)
			if (type == "BANQUET") {
				$http.post($constant.URL_API + "audits/banquet/tasks", {
					"paging" : $scope.criteria.paging,
					"keywords" : $scope.criteria.keywords
				}).success(function(tableData) {
					$scope.tableData = tableData.content;
					// $scope.currentPage = pageNum;
					$scope.numberOfPages = tableData.totalPages;
				}).error(function() {
				});
			}
			else if (type === "TERMINAL") {
				$http.post($constant.URL_API + "audits/terminal/tasks", {
					"paging" : $scope.criteria.paging,
					"keywords" : $scope.criteria.keywords
				}).success(function(tableData) {
					$scope.tableData = tableData.content;
					$scope.numberOfPages = tableData.totalPages;
				}).error(function() {
				});
			}
			else {
				alert("暂不支持");
				return false;
			}

		};
		$scope.toggleDisplay = function() {
			$("#searchAuditQueryPanel").collapse("toggle");
		};
		$scope.initialiazePage = function() {

			$scope.queryByConditions($scope.criteria.keywords.type);
			$http.get($constant.URL_API + "/audits/auditors/26", {

			}).success(function(data) {
				$scope.aduitQueryEnum.auditorsEnum = data;
			});

			$http.get($constant.URL_API + "/audits/region/province", {

			}).success(function(data) {
				$scope.aduitQueryEnum.provinceEnum = data;
			});
		}
		$scope.initialiazePage();

	} ]).filter('getAuditStatus', function() {
		var auditStatusText = function(status, statusEnum) {
			for ( var idx in statusEnum) {
				if (statusEnum[idx].value === status) {
					return statusEnum[idx].name
				}
			}
			return "";
		};
		return auditStatusText;
	}).filter('getTerminalType', function() {
		var terminalTypeText = function(status, statusEnum) {
			for ( var idx in statusEnum) {
				if (statusEnum[idx].value === status) {
					return statusEnum[idx].name
				}
			}
			return "";
		};
		return terminalTypeText;
	});
})()