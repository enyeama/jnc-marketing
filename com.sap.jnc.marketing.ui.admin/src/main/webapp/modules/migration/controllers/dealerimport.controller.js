(function () {
    // Controller
    angular.module('jnc-admin').controller('jnc-admin.migration.dealerimport.controller', [
        // Dependencies
        '$scope', '$http', '$routeParams', '$location',
        // Main
        function ($scope, $http, $routeParams, $location) {
            $scope.errorResults = [];
            $scope.errorResults.length = 0;
            $scope.importTotalCount = 0;
            $("#importErrorCounts").hide();
            $("#importSuccessCounts").hide();


            var data = {};

            /**
             * 导入安全库存数据
             **/
            $scope.uploadAndSubmit = function (fileUploader) {
                $scope.importErrorFlag = true;
                $scope.importSuccessFlag = true;

                var file = fileUploader.files[0];
                var formdata = new FormData();
                formdata.append("file", file);
                $http.post("/api/admin/dealer/dealerimport", formdata, {
                    transformRequest: angular.identity,
                    headers: {
                        'Content-Type': undefined
                    }
                }).success(function (data) {
                    if (data.errorCount > 0) {
                        if (data.totalCount >= 1000) {
                            toastr.error("超过1000条最大导入笔数！");
                        } else {
                            toastr.error('导入失败!');
                            $scope.errorResults = data.dealerMigrationImportResponse;

                            $scope.importSuccessCount = 0;
                            $scope.importTotalCount = data.totalCount;
                            if (0 === data.totalCount) {
                                $scope.importErrorCount = 0;
                            }
                            else {
                                $scope.importErrorCount = data.errorCount;
                            }
                            $("#importErrorCounts").show();
                            $("#importSuccessCounts").hide();
                            return;
                        }
                    } else {
                        toastr.success("导入成功");
                        $scope.importTotalCount = 0;
                        $scope.importErrorCount = 0;
                        $scope.importSuccessCount = data.totalCount;
                        $("#importErrorCounts").hide();
                        $("#importSuccessCounts").show();
                        $scope.errorResults = [];
                    }
                }).error(function () {
                    $("#importErrorCounts").hide();
                    $("#importSuccessCounts").hide();
                    toastr.error('导入失败，请检查网络是否连通！');
                    $scope.errorResults = [];
                    return;
                });
                var file = $("#importButton");
    			file.after(file.clone().val(""));
    			file.remove();
            };

            /*
             *分页功能起始区域
             */

            $scope.start = 0;
            $scope.currentErrorPage = 0;
            $scope.data = [];

            $scope.changePageSize = function () {
                $scope.currentErrorPage = 0;
            };
            $scope.PageConfigProperties = {pageSize: "10"};

            $scope.pageNoKeyDown = function (event) {
                if (event.keyCode === 13) {
                    var pageNo = this.goToErrorPage();
                    $scope.currentErrorPage = pageNo;
                }
            };

            $scope.goToErrorPage = function () {
                if ($scope.pagenum) {
                    if ($scope.pagenum > $scope.numberOfErrorPages()) {
                        return $scope.numberOfErrorPages() - 1
                    } else if ($scope.pagenum < -1) {
                        return 0;
                    } else {
                        return $scope.pagenum - 1;
                    }
                } else {
                    return $scope.currentErrorPage;
                }
            };

            $scope.numberOfErrorPages = function () {
                return Math.ceil($scope.errorResults.length / $scope.PageConfigProperties.pageSize);
            };

            /*
             *分页功能结束区域
             */

        }]).filter('startFrom', function () {
        return function (input, start) {
            start = +start; //parse to int
            return input.slice(start);
        }
    }).filter("statusFormatter", function() {
        return function(status) {
            if(status === "") {
                return "可用";
            } else if(status.toUpperCase() === "X"){
                return "停用";
            }
        }
    })
})()

