/**
 * author: Zero Yu
 */
(function() {
	// Controller
	angular.module('jnc-admin').controller('jnc-admin.migration.provincemanager.controller', [
	// Dependencies
	'$scope', '$http', '$routeParams',
	// Main
	function($scope, $http, $routeParams) {
		$scope.uploadAndSubmit = function() {
			var form = document.forms[0];

			if (form["file"].files.length > 0) {
				var file = form["file"].files[0];
				var formdata = new FormData();
				formdata.append("file", file);

				$http.post("/jnc-marketing-api-admin/api/admin/provincemanageroffice", formdata, {
					transformRequest : angular.identity,
					headers : {
						'Content-Type' : undefined
					}
				}).success(function(data) {
					$scope.formdata = data;
				}).error(function() {
				});
			}
			else {
				alert("Please choose a file.");
			}
		}
	} ]);
})()