/**
 * Madeleine
 */
(function() {
	// Controller
	angular.module('jnc-admin').controller('jnc-admin.ka.branchmanagement.controller', [
	// Dependencies
	'$scope', '$http', '$constant',
	// Main
	function($scope, $http, $constant) {

		$(function() {
			$scope.bootPrefix = $constant.URL_API;
			$scope.branchListAPI = $scope.bootPrefix + 'ka/pages';
			$scope.departListAPI = $scope.bootPrefix + 'kaoffices';
			$scope.importAPI = $scope.bootPrefix + 'ka/import';
			$scope.exportAPI = $scope.bootPrefix + 'ka/export';
			$scope.branchDetailAPI = $scope.bootPrefix + 'ka';
			$scope.cityManagerListAPI = $scope.bootPrefix + "kaorders/citymanagers";
			$scope.maintainerListAPI = $scope.bootPrefix + "kaorders/maintainers";
			$scope.branchNameListAPI = $scope.bootPrefix + "kadd/katerminalnames?externalId=";
			$scope.selectAll = false;

			$scope.criteria = {
				"paging" : {
					"index" : "0",
					"size" : "10"
				},
				"keywords" : {
					"id" : null,
					"branchName" : null,
					"kaSystemNumber" : null,
					"kaOfficeId" : null,
					"storeNumber" : null
				},
				"sort" : {
					"id" : "ASC"
				}
			};

			$scope.branchLevels = [ {
				name : 'A'
			}, {
				name : 'B'
			}, {
				name : 'C'
			} ];

			$scope.systemProperty = [ {
				id : "01",
				name : "国际性大卖场"
			}, {
				id : "02",
				name : "国内商超连锁店"
			}, {
				id : "03",
				name : "省内商超连锁店"
			}, {
				id : "04",
				name : "地方性超市(经销商自有门店)"
			}, {
				id : "05",
				name : "地方性超市(非经销商自有门店)"
			}, {
				id : "07",
				name : "本地商场(非经销商自有门店)"
			}, {
				id : "08",
				name : "本地国营连锁烟酒店(经销商自有门店)"
			}, {
				id : "09",
				name : "本地国营连锁烟酒店(非经销商自有门店)"
			}, {
				id : "10",
				name : "本地私营连锁烟酒店"
			}

			];

			$scope.promoteType = [ {
				id : "ANCU",
				name : "暗促"
			}, {
				id : "PROMOTE",
				name : "促销"
			} ];

			$http.get($scope.departListAPI).success(function(data) {
				if (data.length > 0) {
					$scope.departList = data;
				}
			});

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

			$scope.branchListData = [];
		});

		$scope.toggleDisplay = function() {
			$("#searchBranchmanagementPanel").collapse("toggle");
		};

		$scope.fetchBranchList = function(pageNo) {
			$scope.selectAll = false;
			$scope.criteria.paging.index = pageNo - 1;

			$http.post($scope.branchListAPI, $scope.criteria).success(function(data) {
				$scope.currentPage = data.currentPage
				$scope.beginPage = data.beginPage
				$scope.endPage = data.endPage
				$scope.firstPage = data.firstPage
				$scope.lastPage = data.lastPage
				$scope.totalPage = data.totalPage
				$scope.branchListData = data.pageList

				var pageArray = [];
				for (var p = $scope.beginPage, idx = 0; p <= $scope.endPage; p++, idx++) {
					var pg = $scope.beginPage + idx;
					pageArray[idx] = pg;
				}
				$scope.pageArray = pageArray;
			});
		};

		$scope.importBranchList = function(fileUploader) {
			var importedFile = fileUploader.files[0];
			var form = new FormData();
			form.append("csvFile", importedFile);

			$.ajax({
				url : $scope.importAPI,
				data : form,
				contentType : false,
				processData : false,
				type : 'POST',
				success : function(data) {
					if (data.length == 0) {
						$scope.fetchBranchList($scope.lastPage);
						toastr.success("导入成功");
					}
					else {
						var errorMsg = "";
						data.forEach(function(item) {
							errorMsg = errorMsg + "row " + item.rowNumber + ":" + item.errorDescription + "\r\n";
						});
						toastr.error(errorMsg, "导入失败");
					}
					$("#importButton").wrap('<form>').closest('form').get(0).reset();
					$("#importButton").unwrap();

					/*
					 * BootstrapDialog.show({ title : "导入结果", message : "导入成功", buttons : [ { label : "关闭", action : function(dialogItself) {
					 * dialogItself.close(); } } ] });
					 */
					/* toastr["success"]("导入结果", "导入成功"); */

				},
				error : function(data) {
					toastr.error(data.statusText, "导入失败");
					$("#importButton").wrap('<form>').closest('form').get(0).reset();
					$("#importButton").unwrap();
					/*
					 * BootstrapDialog.show({ title : "导入结果", message : "导入失败", buttons : [ { label : '关闭', action : function(dialogItself) {
					 * dialogItself.close(); } } ] });
					 */
				}
			});

		};

		$scope.exportAllBranchList = function() {
			$scope.exportBranchList({});
		};

		$scope.exportSelectedBranchList = function() {
			var selectedBranches = jQuery.grep($scope.branchListData, function(branch) {
				return branch.selected;
			});

			if (selectedBranches.length == 0) {
				return;
			}

			var requestBody = {
				"terminalIds" : _.pluck(selectedBranches, "id")
			};

			$scope.exportBranchList(requestBody);
		};

		$scope.exportBranchList = function(requestBody) {
			$http.post($scope.exportAPI, requestBody, {
				responseType : "arraybuffer"
			}).success(function(data) {
				var blob = new Blob([ data ], {
					type : "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
				});
				var objectUrl = URL.createObjectURL(blob);
				saveAs(blob, "网点信息" + moment().format("YYYY-MM-DD") + ".xlsx");
			});
		};

		$scope.updateBranch = function() {
			var branchId = $scope.branchDetail.id;
			$http.put($scope.branchDetailAPI + "/" + branchId, $scope.branchDetail).success(function(data) {
				$scope.fetchBranchList($scope.currentPage);
			}).error(function(data) {
				/*
				 * BootstrapDialog.show({ title : "编辑结果", message : "更新失败", buttons : [ { label : "关闭", action : function(dialogItself) {
				 * dialogItself.close(); } } ] });
				 */
				toastr.error("更新失败");
			});
		};

		$scope.disableBranch = function(branchId) {
			$http.post($scope.branchDetailAPI + "/" + branchId + "/disable").success(function(data) {
				$scope.fetchBranchList($scope.currentPage);
				/*
				 * BootstrapDialog.show({ title : "退店结果", message : "退店成功", buttons : [ { label : "关闭", action : function(dialogItself) {
				 * dialogItself.close(); } } ] });
				 */
				toastr.success("退店成功");
			}).error(function(data) {
				/*
				 * BootstrapDialog.show({ title : "退店结果", message : "退店失败", buttons : [ { label : "关闭", action : function(dialogItself) {
				 * dialogItself.close(); } } ] });
				 */
				toastr.error("退店失败");
			});
		};

		$('#branchDetailModal').on('show.bs.modal', function(event) {
			$(".nav-pills li").click(function(e) {
				e.preventDefault();
			});

			var button = $(event.relatedTarget)
			var branchId = button.data('key');

			$http.get($scope.branchDetailAPI + "/" + branchId).success(function(data) {
				$scope.branchDetail = data;

				if (($scope.branchDetail.kaOffice) && ($scope.branchDetail.kaOffice.id)) {
					$http.get($scope.cityManagerListAPI + "/" + $scope.branchDetail.kaOffice.externalId).success(function(data) {
						$scope.cityManagerList = data;
					});
				}

				if (($scope.branchDetail.cityManager) && ($scope.branchDetail.cityManager.positionExternalId)) {
					$http.get($scope.maintainerListAPI + "/" + $scope.branchDetail.cityManager.positionExternalId).success(function(data) {
						$scope.maintainerList = data;
					})
				}
			});

		});

		$('#disableBranchModal').on('show.bs.modal', function(event) {
			var button = $(event.relatedTarget)
			$scope.branchId = button.data('key');
		});

		$scope.changeCityManager = function(cityManagerName) {
			var selectedCityManager = jQuery.grep($scope.cityManagerList, function(cityManager) {
				return cityManager.positionName == cityManagerName;
			});

			$scope.branchDetail.cityManager.positionId = selectedCityManager[0].positionId;
			$scope.branchDetail.cityManager.positionExternalId = selectedCityManager[0].positionExternalId;
			$scope.branchDetail.cityManager.employeeId = selectedCityManager[0].employeeId;
			$scope.branchDetail.cityManager.employeeExternalId = selectedCityManager[0].employeeExternalId;
			$scope.branchDetail.cityManager.employeeName = selectedCityManager[0].employeeName;

			$http.get($scope.maintainerListAPI + "/" + $scope.branchDetail.cityManager.positionExternalId).success(function(data) {
				$scope.maintainerList = data;
			});
		};

		$scope.changeMaintainer = function(maintainerName) {
			var selectedMaintainer = jQuery.grep($scope.maintainerList, function(maintainer) {
				return maintainer.employeeName == maintainerName;
			});
			$scope.branchDetail.maintainer.employeeId = selectedMaintainer[0].employeeId;
			$scope.branchDetail.maintainer.employeeExternalId = selectedMaintainer[0].employeeExternalId;
			$scope.branchDetail.maintainer.positionId = selectedMaintainer[0].positionId;
			$scope.branchDetail.maintainer.positionExternalId = selectedMaintainer[0].positionExternalId;
			$scope.branchDetail.maintainer.positionName = selectedMaintainer[0].positionName;
		};

		$scope.changeSystemPropertyName = function(propertyName) {
			var selectedSystemProperty = jQuery.grep($scope.systemProperty, function(systemProperty) {
				return systemProperty.name == propertyName;
			});
			$scope.branchDetail.kaSystemPropertyNumber = selectedSystemProperty[0].id;
		};

		$scope.selectAllBranch = function() {
			$scope.branchListData.forEach(function(branch) {
				branch.selected = $scope.selectAll;
			});
		};

		$scope.clearQueryCriteria = function() {
			$scope.criteria = {
				"paging" : {
					"index" : "0",
					"size" : "10"
				},
				"keywords" : {
					"id" : null,
					"branchName" : null,
					"kaSystemNumber" : null,
					"kaOfficeId" : null,
					"storeNumber" : null
				},
				"sort" : {
					"id" : "ASC"
				}
			};
		};

		$scope.setSize = function() {
			$scope.fetchBranchList(1);
		};

	} ]);

})()
