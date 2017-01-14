/**
 * Vincent
 */
(function() {
	// Controller
	angular.module('jnc-admin').controller('jnc-admin.payment.detail.controller',
	// Main
	function($scope, $http, $modalInstance, item, toastr) {
		$scope.item = {
			defaultAccountId : null,
			defaultAccountOpenId : null,
			validFrom : null
		};

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
		$scope.item.validFrom = $scope.getTomorrowDate();

		$scope.ok = function() {
			dereg();
			$modalInstance.close($scope.item);
		};

		$scope.close = function() {
			$modalInstance.dismiss('cancel');
		}

		/** 配置项 */
		$scope.toList = "/admin/#/payment/list";
		$scope.url = {
			prefix : '/api/admin',
			payment : {
				add : '/payment/amount/config',
				pages : '/payment/amount/config/pages'
			}
		}

		$scope.addUrl = $scope.url.prefix + $scope.url.payment.add;
		$scope.pagesUrl = $scope.url.prefix + $scope.url.payment.pages;

		$scope.defaultAccountId = null;
		$scope.defaultAccountOpenId = null;
		$scope.validFrom = null;
		$scope.validTo = null;

		/** 时间差 */

		$scope.timeDiff = function(targetDate) {
			var targetTime = new Date(Date.parse(targetDate.replace(/-/g, "/"))).getTime();
			var cur_date = new Date();
			var cur_year = new Date().getFullYear();
			var cur_month = cur_date.getMonth() + 1;
			var real_date = cur_date.getDate();

			var currentTime = new Date(cur_year + "/" + cur_month + "/" + real_date).getTime();

			return (targetTime < currentTime || targetTime === currentTime) ? false : true;
		}

		/** 提交表单 */
		$scope.save = function() {

			$scope.errors = [];

			var reg = new RegExp("^[0-9]*$");
			if ($scope.item.defaultAccountId == null || $scope.item.defaultAccountId.length <= 0) {
				$scope.errors.push({
					text : "请输入支付账户商户号！"
				});
				return;
			}
			else if (!reg.test($scope.item.defaultAccountId)) {
				$scope.errors.push({
					text : "支付账户商户号格式不正确！"
				});
				return;
			}

			if ($scope.item.defaultAccountOpenId == null || $scope.item.defaultAccountOpenId.length <= 0) {
				$scope.errors.push({
					text : "请输入支付账户公众账号appid！"
				});
				return;
			}

			/** 时间 */
			$scope.item.validFrom = $("#startDate").val();
			if ($scope.item.validFrom == null || $scope.item.validFrom.length <= 0) {
				$scope.errors.push({
					text : "请选择生效日期！"
				});
				return;
			}

			var timediff = $scope.timeDiff($scope.item.validFrom);
			if (!timediff) {
				$scope.errors.push({
					text : "生效日期必须晚于当天！"
				});
				return;
			}

			$http.post($scope.addUrl, $scope.item).success(function(data) {
				if (data) {
					$scope.ok();
					toastr["success"]("保存成功");
					setTimeout(function() {
						window.location = $scope.toList;
					}, 1000);
				}
				else {
					toastr["error"]("保存失败");

				}
			});

		}

		var bInit = false;
		if (!bInit) {
			var dereg = $scope.$watch($scope.item, function() {
				$("#startDate").datetimepicker({
					format : 'YYYY-MM-DD'
				});
				$("#endDate").datetimepicker({
					format : 'YYYY-MM-DD'
				});

			});
		}

	});

})()
