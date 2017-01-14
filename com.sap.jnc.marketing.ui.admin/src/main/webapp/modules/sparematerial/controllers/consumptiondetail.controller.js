(function() {
	// Controller
	angular.module('jnc-admin').controller(
			'jnc-admin.sparematerial.consumptiondetail.controller', [
			// Dependencies
			'$scope', '$http', '$routeParams',
			// Main
			function($scope, $http, $routeParams) {
				// Initialize Example 1
				$('#datetimepicker1').datetimepicker({
					format : 'YYYY-MM-DD'
				});
				$('#datetimepicker2').datetimepicker({
					format : 'YYYY-MM-DD'
				});
				$('#datetimepicker3').datetimepicker({
					format : 'YYYY-MM-DD'
				});
				$('#datetimepicker4').datetimepicker({
					format : 'YYYY-MM-DD'
				});
				// Load data
				
				$scope.searchParameter = {
					"id" : "",
					"positionId" : ""
				};

				
				toastr.options = {
		                    "closeButton": false,
		                    "positionClass": "toast-top-center"
		        };
				
				$scope.search = function(event) {
					var url = "/api/admin/consumptiondetail/allconsumption";
					if ($scope.searchParameter.id != "" && $scope.searchParameter.positionId == ""){
						url += "?id=" + $scope.searchParameter.id;
					}
					if ($scope.searchParameter.id == "" && $scope.searchParameter.positionId != "" ){
						url += "?positionId=" + $scope.searchParameter.positionId;
					}
					if ($scope.searchParameter.id != "" && $scope.searchParameter.positionId != ""){
						url += "?id="+$scope.searchParameter.id + "&positionId="+$scope.searchParameter.positionId;
					}
					var data = {};
					$http.get(url).success(function (data) {
			                $scope.data = data;
			            }).error(function (data){
			            	toastr.error("网络请求错误");
			            });
				}
				
				//export
				$scope.export = function(event){
					location.href =  "/api/admin/consumptiondetail/exportation";
					
				}
				
				
				// Modal Form
				$scope.hxForm = [];
				$scope.hx = function(event, id, quantity, comment) {
					$scope.hxForm.verifiedQuantity = quantity;
					$scope.hxForm.id = id;
					$scope.hxForm.verificationComment = comment;
					
				}
				$scope.tx = function(event, id, quantity, comment) {
					$scope.hxForm.differency = quantity;
					$scope.hxForm.id = id;
					$scope.hxForm.manualAdjustmentComment = comment;
				}
				$scope.hxsubmit = function(id, verifiedQuantity, differency, comment, type) {
					var requestData={};
					//核销
					if (type == "hx"){
						if (verifiedQuantity == 0){
							 toastr.error("核销量不能为0");
							return;
						}
						for(var index in $scope.data){
							var item = $scope.data[index];
							if (item.id == id){
								if (verifiedQuantity > item.paidQuantity ){
									toastr.error("核销量不能大于兑付数量-差异数量");
									return;
								}
							}
						}
						if (verifiedQuantity != null && verifiedQuantity != ""){
							requestData["verifiedQuantity"] = verifiedQuantity;
							requestData["verificationComment"] = comment;
						}
					}else if (type == "tx"){ //调差
						if (differency < 0){
							//TODO error message 
							toastr.error("调差数量不能小于0");
							return;
						}
						if (differency != null && differency != ""){
							requestData["manualAdjustmentQuantity"] = differency;
							requestData["manualAdjustmentComment"] = comment;
						}
					}else{
						toastr.error("类型错误");
					}
					
					
					
					$http({
			                method: "POST", 
			                url: "/api/admin/consumptiondetail/allconsumption/" + id,
			                data:requestData
			            }).
			            success(function(data, status) {
			            	$scope.search();
			            }).
			            error(function(data, status) {
			            	toastr.error("网络请求错误");
			            });
				}
				// Page controll
				$scope.start=0;
				$scope.currentPage = 0;
				$scope.numberOfPages=function(){
					if ($scope.data != null && $scope.data.length > 0){
						return Math.ceil($scope.data.length/$scope.PageConfigProperties.pageSize); 
					}else{
						return 0;
					}
				}
				$scope.changePageSize = function() {
					$scope.currentPage = 0;
				};
				$scope.PageConfigProperties = {
					pageSize : "5"
				};

				$scope.pageNoKeyDown = function(event) {
					if (event.keyCode === 13) {
						var pageNo = this.goToPage();
						$scope.currentPage = pageNo;
					}
				};

				$scope.goToPage = function() {
					if ($scope.pagenum) {
						if ($scope.pagenum > $scope.numberOfPages()) {
							return $scope.numberOfPages() - 1
						} else if ($scope.pagenum < -1) {
							return 0;
						} else {
							return $scope.pagenum - 1;
						}
					} else {
						return $scope.currentPage;
					}
				}

				$scope.search();

			} ])
})()