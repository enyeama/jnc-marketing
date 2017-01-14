/**
 * author: Zero Yu
 */
(function() {
	// Controller
	angular.module('jnc-admin').controller('jnc-admin.contract.contractimport.controller', [
	// Dependencies
	'$scope', '$http', '$routeParams', '$constant',
	// Main
	function($scope, $http, $routeParams, $constant) {
		(function() {
			$scope.model = {
				"totalCount": 0,
				"errorCount": 0,
				"visible":false,
				"content" : [],
				"currentPage" : 0,
				"pageSize" : "10"
			};
			$scope.errorElements = $scope.model.content.length;
			$scope.model.totalPages = parseInt($scope.model.content.length / $scope.model.pageSize)+1;
		})();
		
		$scope.importContractList = function(fileUploader) {
			$("#uploadingModal").modal("show");
			$scope.model = {
					"totalCount": 0,
					"errorCount": 0,
					"visible":false,
					"content" : [],
					"currentPage" : 0,
					"pageSize" : "10"
				};
			var importedFile = fileUploader.files[0];
			var formdata = new FormData();
			formdata.append("flie", importedFile);
			$http.post($constant.URL_API + "contract/import", formdata, {
				transformRequest : angular.identity,
				headers : {
					'Content-Type' : undefined
				}
			}).success(function(data) {
				$("#uploadingModal").modal("hide");
				if(!(data.errorCount == 0 || data.errorCount =="0")) {
					toastr.error('文件中有错误信息');
					$scope.model.content = data.contractImportResponse;
					$scope.errorResults.length = data.contractImportResponse.length;
				} else {
					toastr.success('已正确处理','文件上传成功');
					//toastr.success('成功导入'+data.totalCount+'笔');
				}
				$scope.model.totalCount = data.totalCount;
				$scope.model.errorCount = data.errorCount;
				$scope.model.visible = true;
			}).error(function(errors,status) {
				$("#uploadingModal").modal("hide");
				if (status == "400")
					toastr.error('文件上传失败，'+errors.error);
			});
			var file = $("#importButton");
			file.after(file.clone().val(""));
			file.remove();
		};
		
		
		$scope.errorResults = [];
		$scope.errorResults.length=0;

		$scope.start=0;
		$scope.currentErrorPage = 0;
		$scope.data = [];
		
		$scope.changePageSize = function(){
			$scope.currentErrorPage = 0;
		};
		$scope.PageConfigProperties={pageSize:"10"};
		
		$scope.pageNoKeyDown = function(event){
			if(event.keyCode ===13){
				var pageNo = this.goToErrorPage();
				$scope.currentErrorPage = pageNo;
			}
		};
	
		 $scope.goToErrorPage = function () {
			 if($scope.pagenum){
				if($scope.pagenum>$scope.numberOfErrorPages()){
					return $scope.numberOfErrorPages()-1
				}else if($scope.pagenum<-1){
					return 0;
				}else{
					return $scope.pagenum-1;
				}
			 }else{
				 return $scope.currentErrorPage;
			 }
		}
	
		$scope.numberOfErrorPages=function(){
			return Math.ceil($scope.errorResults.length/$scope.PageConfigProperties.pageSize);                
		}
		
	} ]).filter('startFrom', function() {
		return function(input, start) {
			start = +start; //parse to int
			return input.slice(start);
		}
	});
})()