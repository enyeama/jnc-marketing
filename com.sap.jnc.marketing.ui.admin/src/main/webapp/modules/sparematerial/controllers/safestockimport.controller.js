(function() {
	// Controller
	angular.module('jnc-admin').controller('jnc-admin.sparematerial.safestockimport.controller', [
	// Dependencies
	'$scope', '$http', '$routeParams','$location',
	// Main
	function($scope, $http, $routeParams, $location) {
		// Initialize Example 1
		
		$scope.errorResults = [];
		$scope.errorResults.length=0;
		$("#importErrorCounts").hide();
		$("#importSuccessCounts").hide();

		
		var data = {};
		
		/**
		 * 导入安全库存数据
		 **/
		$scope.uploadAndSubmit = function() {
			$scope.importErrorFlag = true;
			$scope.importSuccessFlag = true;
			var form = document.forms[0];

			if (form["file"].files.length > 0) {
				var file = form["file"].files[0];
				var formdata = new FormData();
				formdata.append("file", file);
				$http.post("/api/admin/safestock/safestockupload", formdata, {
					transformRequest : angular.identity,
					headers : {
						'Content-Type' : undefined
					}
				}).success(function(data) {
					var errorFlag = data.errorFlag;
					if(errorFlag){
						$("#uploadingModal").modal("hide");
						alert('导入失败!');
						$scope.errorResults = data.detailErrorMsg;
						
						$scope.importSuccessCount = 0;
						$scope.importTotalCount = data.totalItems;
						if(0 == data.totalItems){$scope.importErrorCount = 0;}
						else{$scope.importErrorCount = data.checkFailureItems;}
//						$scope.importErrorCount = data.checkFailureItems;
						$("#importErrorCounts").show();
						$("#importSuccessCounts").hide();
						return;
					}
					$("#uploadingModal").modal("hide");
					alert('导入成功');
					$scope.importTotalCount = 0;
					$scope.importErrorCount = 0;
					$scope.importSuccessCount = data.totalItems;
					$("#importErrorCounts").hide();
					$("#importSuccessCounts").show();
					$scope.errorResults = [];
				}).error(function(data) {
					$("#uploadingModal").modal("hide");
					$("#importErrorCounts").hide();
					$("#importSuccessCounts").hide();
					alert('导入失败，请检查网络是否连通！');
					$scope.errorResults = [];
					return;
				});
			}
			else {
				alert("Please choose a file.");
			}
		}

		/**
		 * 返回备用物料安全库存页面
		 **/
		$scope.returnToSafeStock = function(){
			window.location.href = $location.protocol()+"://"+$location.host()+":"+$location.port()+"/admin/#/sparematerial/safestock";
		};		
		
		/**
		 * 安全库存导入出错信息导出功能
		 **/
		$scope.exportErrorSafeStock = function(){
			location.href = "../api/admin/safestock/exportsafestockerror";
		};
		
		
		/**
		 * 点击发货按钮，设置发货窗口中需补库存的值
		 **/
		//发货窗口专用form
		$scope.deliveryForm = {};
		$scope.setDeliveryForm = function(requiredStockQuantity,positionId,materialId){
			$scope.deliveryForm.requiredstock = requiredStockQuantity; 
			$scope.deliveryForm.positionId = positionId;
			$scope.deliveryForm.materialId = materialId;
			
		};
		
		/**
		 * 发货窗口-发货确认事件 
		 **/
		$scope.deliveryMaterial = function(positionId,materialId){
			deliveryQuantity = $scope.deliveryForm.deliveryQuantity;
			deliveryQuantity = +deliveryQuantity;
			requiredQuantity = $scope.deliveryForm.requiredstock;
			requiredQuantity = +requiredQuantity;
			
//			alert("deliveryQuantity::"+deliveryQuantity+"   requiredQuantity:::::"+requiredQuantity);
			if(deliveryQuantity > requiredQuantity){
				//窗口继续显示
				$("#deliveryModal").show();
				$("#deliveryQuNotLessThanRequireQu").show();
			}else{
				$("#deliveryQuNotLessThanRequireQu").hide();
				$("#deliveryModal").hide();
			}
		};
		
		
		
		
		
		
		
		
		/*
		 *分页功能起始区域 
		 */
		
		$scope.start=0;
		$scope.currentErrorPage = 0;
		$scope.data = [];
		
		$scope.changePageSize = function(){
			$scope.currentErrorPage = 0;
		};
		$scope.PageConfigProperties={pageSize:"5"};
		
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
		
		/*
		 *分页功能结束区域 
		 */
		
		 /*$('#banquetVerificationModal').on('show.bs.modal', function (event) {
					  var button = $(event.relatedTarget);
					  var recipient = button.data('whatever');
					  alert("************"+recipient);
					  $scope.verificationFormData={};
					  $scope.$apply();
		});
		*/
	}]).filter('startFrom', function() {
			return function(input, start) {
				start = +start; //parse to int
				return input.slice(start);
			}
		})
})()

