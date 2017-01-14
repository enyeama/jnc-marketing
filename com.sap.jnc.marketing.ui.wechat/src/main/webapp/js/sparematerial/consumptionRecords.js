function DataModel() {
    var self = this;
    var bLoading = false;

    function loadData() {
        $.ajax({
            url: "/api/wechat/consumptions",
            type: "POST",
            data: JSON.stringify(self.oCriteria),
            dataType: "json",
            contentType: "application/json",
            success: function (oResponse) {
                self.iTotalPages = oResponse.totalPages;

                if (oResponse.number === 0) {
                    self.aConsumptionRecords(oResponse.content);
                } else {
                    for (var i = 0; i < oResponse.content.length; i++) {
                        self.aConsumptionRecords.push(oResponse.content[i]);
                    }
                }
                $.hideLoading();
                bLoading = false;
                $("#scrollDiv").hide();
            },
            error: function () {
                $.hideLoading();
                bLoading = false;
                $("#scrollDiv").hide();
                $.toast("数据加载失败", "forbidden");
            }
        });
    }

    function activateEvent() {
        $(document.body).on("pull-to-refresh", function () {
            $.showLoading("正在加载...");
            loadData();
        });

        $(document.body).infinite().on("infinite", function () {
            if (bLoading) {
                return;
            }

            $("#scrollDiv").show();
            bLoading = true;
            self.oCriteria.paging.index++;
            
            if (self.oCriteria.paging.index + 1 > self.iTotalPages) {
                $("#scrollDiv").hide();
                $(document.body).destroyInfinite();
                return;
            }

            loadData();
        });
    }

    this.oCriteria = ko.observable(),
    this.iCurrentPage = ko.observable(),
    this.aConsumptionRecords = ko.observableArray([]),
    this.init = function () {
        $(document.body).pullToRefresh();
        $(document.body).infinite();
        this.oCriteria = {
            "paging": {
                "index": 0,
                "size": 10
            },
            "keywords": {
                "positionId": 5280,
            },
            "sort": {
                "paymentDate": "DESC"
            }
        };
        $.showLoading("正在加载...");
        loadData();
        activateEvent();
    }
}

$(function () {
    var oDataModel = new DataModel();

    ko.applyBindings(oDataModel);
    oDataModel.init();
});