angular
		.module('jnc-admin')
		.controller(
				'jnc-admin.bonus.detail.controller',
				function($scope, $http, $modalInstance, item, categories, categoriesSearch, toastr) {
					$scope.selected = {
						item : item
					};
					$scope.categories = categories;
					$scope.categoriesSearch = categoriesSearch;
					$scope.selected.item.categoryName = null;
					$scope.addByTransDate = function(dateParameter, num) {
						var translateDate = "", dateString = "", monthString = "", dayString = "";
						translateDate = dateParameter.replace("-", "/").replace("-", "/");
						var newDate = new Date(translateDate);
						newDate = newDate.valueOf();
						newDate = newDate + num * 24 * 60 * 60 * 1000;
						newDate = new Date(newDate);
						// 如果月份长度少于2，则前加 0 补位
						if ((newDate.getMonth() + 1).toString().length == 1) {
							monthString = 0 + "" + (newDate.getMonth() + 1).toString();
						}
						else {
							monthString = (newDate.getMonth() + 1).toString();
						}
						// 如果天数长度少于2，则前加 0 补位
						if (newDate.getDate().toString().length == 1) {
							dayString = 0 + "" + newDate.getDate().toString();
						}
						else {
							dayString = newDate.getDate().toString();
						}
						dateString = newDate.getFullYear() + "-" + monthString + "-" + dayString;
						return dateString;
					}
					$scope.getTomorrowDate = function() {
						var trans_day = "";
						var cur_date = new Date();
						var cur_year = new Date().getFullYear();
						var cur_month = cur_date.getMonth() + 1;
						var real_date = cur_date.getDate();
						cur_month = cur_month > 9 ? cur_month : ("0" + cur_month);
						real_date = real_date > 9 ? real_date : ("0" + real_date);
						eT = cur_year + "-" + cur_month + "-" + real_date;
						return $scope.addByTransDate(eT, 1);
					}
					$scope.selected.item.startDate = $scope.getTomorrowDate();
					$scope.rs = {
						"sum" : 0,
						"max_decimal_len" : 0,
						"max_power" : 0,
						"total_cny" : 0
					}
					$scope.ok = function() {
						$modalInstance.close($scope.selected.item);
					};
					$scope.close = function() {
						dereg();
						$modalInstance.dismiss('cancel');
					}
					$scope.save = function() {
						$scope.errors = [];

						var list = $scope.selected.item.list;
						var percentageArray = [];
						for (var i = 0; i < list.length; i++) {
							if (list[i].percentage === null || list[i].percentage === "") {
								$scope.errors.push({
									text : "请输入百分比!"
								});
								return;
							}
							percentageArray[i] = list[i].percentage;
						}

						/** 计算百分比 */
						$scope.refined(percentageArray);
						var sum = $scope.rs["sum"];

						if ($scope.selected.item.categoryName === null || $scope.selected.item.categoryName === "") {
							$scope.errors.push({
								text : "请选择产品类型！"
							});
							return;
						}

						if ($scope.selected.item.averageAmount == null || $scope.selected.item.averageAmount <= 0) {
							$scope.errors.push({
								text : "请输入计划均值金额!"
							});
							return;
						}

						if ($scope.selected.item.varianceAmount == null || $scope.selected.item.varianceAmount.length <= 0) {
							$scope.errors.push({
								text : "请输入均值误差金额！"
							});
							return;
						}

						if ($scope.selected.item.averageAmount < $scope.selected.item.varianceAmount) {
							$scope.errors.push({
								text : "计划均值金额需大于均值误差金额！"
							});
							return;
						}

						if (sum !== 100) {
							$scope.errors.push({
								text : "红包配额百分比之和需等于100%!"
							});
							return;
						}

						if (!$scope.checkNumber(list)) {
							$scope.errors.push({
								text : "档次需大于计划均值金额与均值误差金额的差值，并小于计划均值金额"
							});
							return;
						}

						var validFrom = $("#startDate").val();
						var timediff = $scope.timeDiff(validFrom);

						if (validFrom == null || validFrom.length <= 0) {
							$scope.errors.push({
								text : "请选择生效日期！"
							});
							return;
						}
						if (!timediff) {
							$scope.errors.push({
								text : "生效日期必须晚于当天！"
							});
							return;
						}

						var requestData = {};
						requestData["productErpCategoryId"] = $scope.selected.item.categoryId;
						requestData["averageAmount"] = $scope.selected.item.averageAmount;
						requestData["varianceAmount"] = $scope.selected.item.varianceAmount;
						requestData["validFrom"] = $("#startDate").val();
						requestData["validTo"] = $("#endDate").val();
						requestData["calculatedBaseNumber"] = $scope.rs["total_cny"];
						requestData["bonusDispatcherItemRequests"] = $scope.removeListId($scope.selected.item.list);

						$http.post("/api/admin/bonus/config", requestData).success(function(data) {
							$modalInstance.close($scope.selected.item);
							toastr["success"]("保存成功");
						});
					}
					$scope.removeListId = function(list) {
						var newList = [];
						_.each(list, function(oItem) {
							newList.push({
								number : oItem.number,
								percentage : oItem.percentage
							})
						});
						return newList;
					}
					$scope.checkNumber = function(list) {
						if (!$scope.selected.item.averageAmount || !$scope.selected.item.varianceAmount)
							return false;
						var diff = $scope.selected.item.averageAmount - $scope.selected.item.varianceAmount;

						var size = list.length;
						if (size === 0)
							return false;
						var sum = 0;
						_.each(list, function(item) {
							if (!item.number || !item.percentage)
								return false;
							sum += item.number * item.percentage;
						});

						var averageNumber = sum / 100;

						return ((averageNumber > diff || averageNumber === diff) && (averageNumber < $scope.selected.item.averageAmount || averageNumber === $scope.selected.item.averageAmount)) ? true
								: false;
					}
					$scope.remove = function(delId) {
						var lst = $scope.selected.item.list;
						for (var it = 0; it < lst.length; it++) {
							if (delId == lst[it].id) {
								lst.splice(it, 1);
							}
						}
					};
					/** 数组精确求和 */
					$scope.refined = function(perArray) {
						var sum = 0, intSum = 0, splitArray = null;
						var max_decimal_len = 0, decimal_len = 0, max_power = 1;
						if (perArray instanceof Array && perArray.length >= 0) {
							try {
								for (var i = 0; i < perArray.length; i++) {
									var p = perArray[i];
									splitArray = p.toString().split(".");
									if (splitArray.length >= 2) {
										decimal_len = splitArray[1].length;
									}
									else {
										decimal_len = 0;
									}
									power = Math.pow(10, decimal_len);
									/** 最终通用 */
									max_decimal_len = Math.max(max_decimal_len, decimal_len);
									max_power = Math.pow(10, max_decimal_len);

								}

								for (var j = 0; j < perArray.length; j++) {
									var p = perArray[j];
									/** 最终通用 */
									intSum += max_power * Number(p);
								}

								var mod_sec = 0, int_sec = 0;
								var notRefine = intSum / max_power;
								var int_sec_str = "";

								sum = Number(notRefine);

								// -- 返回JSON串
								$scope.rs.sum = sum;
								$scope.rs.max_decimal_len = max_decimal_len;
								$scope.rs.max_power = max_power;
								$scope.rs.total_cny = max_power * 100;

								return $scope.rs;
							}
							catch (e) {
								console.log(e);
							}

						}

					}
					$scope.timeDiff = function(targetDate) {
						var targetTime = new Date(Date.parse(targetDate.replace(/-/g, "/"))).getTime();
						var cur_date = new Date();
						var cur_year = new Date().getFullYear();
						var cur_month = cur_date.getMonth() + 1;
						var real_date = cur_date.getDate();

						var currentTime = new Date(cur_year + "/" + cur_month + "/" + real_date).getTime();

						return (targetTime < currentTime || targetTime === currentTime) ? false : true;
					}
					var bInit = false;
					if (!bInit) {
						var dereg = $scope.$watch($scope.selected.item, function() {
							$("#test").autocomplete({
								source : function(request, response) {
									var matcher = new RegExp($.ui.autocomplete.escapeRegex(request.term), "i");
									response($.grep($scope.categoriesSearch, function(value) {
										value = value.label || value.value || value;
										return matcher.test(value);
									}));
								},
								select : function(event, ui) {
									$scope.selected.item.categoryName = ui.item.value;
									var selectedItem = _.find(categories, function(oItem) {
										return oItem.categoryName == ui.item.value;
									});
									$scope.selected.item.categoryId = selectedItem.id;
								}
							});

							$("#startDate").datetimepicker({
								format : 'YYYY-MM-DD'
							});
							$("#endDate").datetimepicker({
								format : 'YYYY-MM-DD'
							});

							$("#addNew").click(function() {
								var id = new Date().getTime() + Math.random(1, 10);
								$scope.selected.item.list.push({
									id : id,
									percentage : null,
									number : null
								})
							});
						});
					}

				})