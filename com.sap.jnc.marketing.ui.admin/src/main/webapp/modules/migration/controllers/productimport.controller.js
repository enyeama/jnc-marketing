(function() {
	// Controller
	angular.module('jnc-admin').controller('jnc-admin.migration.productimport.controller', [
	// Dependencies
	'$scope', '$http', '$routeParams','$location',
	// Main
	function($scope, $http, $routeParams, $location) {
		$scope.renderLocalData = function(datatable, data){
			datatable.clear();
			if(data){
		    	datatable.rows.add(data);
		    	datatable.draw();
			}
		}
		/**
		 * 导入安全库存数据
		 */
		$scope.uploadAndSubmit = function() {
			console.log('上传物料主数据文件， 导入操作开始');
			$scope.importErrorFlag = true;
			$scope.importSuccessFlag = true;
			var form = document.forms[0];
			if (form["file"].files.length > 0) {
				var file = form["file"].files[0];
				var formdata = new FormData();
				formdata.append("file", file);
				$http.post("/api/admin/migration/product/imports", formdata, {
					transformRequest : angular.identity,
					headers : {
						'Content-Type' : undefined
					}
				}).success(function(data) {
					console.log('导入操作结束');
					$scope.renderLocalData($('#typeTable').DataTable(), data.productType);
					$scope.renderLocalData($('#groupTable').DataTable(), data.productGroup);
					$scope.renderLocalData($('#dmsCategoryTable').DataTable(), data.productDmsCategory);
					$scope.renderLocalData($('#erpCategoryTable').DataTable(), data.productErpCategory);
					$scope.renderLocalData($('#channelTable').DataTable(), data.channel);
					$scope.renderLocalData($('#productTable').DataTable(), data.product);
					$scope.renderLocalData($('#productDmsCateTable').DataTable(), data.productDmsCategoryMapping);
					$scope.renderLocalData($('#productErpCateTable').DataTable(), data.productErpCategoryMapping);
					$scope.renderLocalData($('#productChannelTable').DataTable(), data.productChannelMapping);
				}).error(function(data) {
					console.log(data);
				});
			}
			else {
				alert("请选择可导入的文件！");
			}
		}

		// 返回至经销商查询页面
		$scope.returnToDealer = function(){
			window.location.href = $location.protocol()+"://"+$location.host()+":"+$location.port()+"/admin/#/migration/product";
		};
		
		/**
		 * 安全库存导入出错信息导出功能
		 */
		$scope.exportErrorSafeStock = function(){
			location.href = "../api/admin/safestock/exportsafestockerror";
		};
		
		
		/*
		 * 分页功能起始区域
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
		
		$(function(){
			var tableconf={};
			tableconf.language = {
					info : "当前第_START_条 至 第_END_条 共_TOTAL_条",
					infoEmpty : "第0条 至 第0条 共0条",
					search : "查找:",
					zeroRecords : "没有找到任何匹配的记录",
					infoFiltered : "(在全部_MAX_条记录中查找)",
					emptyTable : "表格中没有可用的数据",
					paginate : {
						first : "首页",
						previous : "上一页",
						next : "下一页",
						last : "末页"
					}
				};
			
			$('#groupTable').DataTable({
				dom : 'Bfrtip',
				language : tableconf.language,
//				buttons : [{
//					text : '错误导出',
//					action : $scope.exports
//				} ],
				buttons :[],
				columns : [ {data : 'rowIndex' }, { data : 'operation' }, { data : 'id' }, { data : 'name' }, { data : 'errorMsg' } ]
			});
			
			$('#typeTable').DataTable({
				dom : 'Bfrtip',
				language : tableconf.language,
//				buttons : [{
//				text : '错误导出',
//				action : $scope.exports
//			} ],
			buttons :[],
				columns : [ {data : 'rowIndex' }, { data : 'operation' }, { data : 'externalId' }, { data : 'name' }, {data:'errorMsg'}]
			});
			
			$('#erpCategoryTable').DataTable({
				dom : 'Bfrtip',
				language : tableconf.language,
//				buttons : [{
//				text : '错误导出',
//				action : $scope.exports
//			} ],
			buttons :[],
				columns : [ {data : 'rowIndex' }, { data : 'operation' }, { data : 'levelNumber' }, { data : 'levelName' }, { data : 'categoryId' }, { data : 'categoryName'}, {data : 'errorMsg'} ]
			});
			
			$('#dmsCategoryTable').DataTable({
				dom : 'Bfrtip',
				language : tableconf.language,
//				buttons : [{
//				text : '错误导出',
//				action : $scope.exports
//			} ],
			buttons :[],
				columns : [ {data : 'rowIndex' }, { data : 'operation' }, { data : 'id' }, { data : 'level' }, { data : 'name' }, { data : 'parentId'}, {data : 'errorMsg'} ]
			});
			
			$('#channelTable').DataTable({
				dom : 'Bfrtip',
				language : tableconf.language,
//				buttons : [{
//				text : '错误导出',
//				action : $scope.exports
//			} ],
			buttons :[],
				columns : [ {data : 'rowIndex' }, { data : 'operation' }, { data : 'externalId' }, { data : 'name' }, { data : 'errorMsg' }]
			});

			$('#productTable').DataTable({
				dom : 'Bfrtip',
				language : tableconf.language,
//				buttons : [{
//				text : '错误导出',
//				action : $scope.exports
//			} ],
			buttons :[],
				columns : [ {data : 'rowIndex' }, { data : 'operation' }, { data : 'id' }, { data : 'name' }, { data : 'productGroup' }, { data : 'productType'}, {data : 'errorMsg'}]
			});

			$('#productErpCateTable').DataTable({
				dom : 'Bfrtip',
				language : tableconf.language,
//				buttons : [{
//				text : '错误导出',
//				action : $scope.exports
//			} ],
			buttons :[],
				columns : [ {data : 'rowIndex' }, { data : 'operation' }, { data : 'productId' }, { data : 'levelNumber' }, { data : 'categoryId' }, {	data : 'errorMsg'	} ]
			});
			
			$('#productDmsCateTable').DataTable({
				dom : 'Bfrtip',
				language : tableconf.language,
//				buttons : [{
//				text : '错误导出',
//				action : $scope.exports
//			} ],
			buttons :[],
				columns : [ {data : 'rowIndex' }, { data : 'operation' }, { data : 'productId' }, { data : 'categoryId' }, {	data : 'errorMsg'	} ]
			});
			
			$('#productChannelTable').DataTable({
				dom : 'Bfrtip',
				language : tableconf.language,
//				buttons : [{
//				text : '错误导出',
//				action : $scope.exports
//			} ],
			buttons :[],
				columns : [ {data : 'rowIndex' }, { data : 'operation' }, { data : 'productId' }, { data : 'channelId' }, {data : 'errorMsg'} ]
			});
		});
	}])
})()

