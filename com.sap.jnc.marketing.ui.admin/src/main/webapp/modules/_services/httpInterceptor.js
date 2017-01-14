var httpInterceptor = function($provide, $httpProvider) {
	$provide.factory('httpInterceptor', function($q, toastr) {
		toastr.options = {
			"closeButton" : true,
			"debug" : false,
			"newestOnTop" : false,
			"progressBar" : false,
			"positionClass" : "toast-top-right",
			"preventDuplicates" : true,
			"onclick" : null,
			"showDuration" : "300",
			"hideDuration" : "1000",
			"timeOut" : "8000",
			"extendedTimeOut" : "1000",
			"showEasing" : "swing",
			"hideEasing" : "linear",
			"showMethod" : "fadeIn",
			"hideMethod" : "fadeOut"
		};
		return {
			response : function(response) {
				return response || $q.when(response);
			},
			responseError : function(err) {
				if (err.status === 401) {
					toastr["error"]("你尚未登录系统", "身份验证失败");
				}
				else if (404 === err.status) {
					toastr["error"]("找不到资源", "失败");
				}
				else {
					toastr["error"]("发生错误，代码：" + err.status, "失败");
				}
				return $q.reject(err);
			}
		};
	});
	$httpProvider.interceptors.push('httpInterceptor');
};
angular.module("jnc-admin").config(httpInterceptor);
