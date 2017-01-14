function DataModel() {
    var self = this;
    var bLoading = false;

    function loadData() {
        $.ajax({
            url: "/api/wechat/deliveries",
            type: "POST",
            data: JSON.stringify(self.oCriteria),
            dataType: "json",
            contentType: "application/json",
            success: function (oResponse) {
            	if(oResponse === null) {
            		
            	} else {
            		self.iTotalPages = oResponse.totalPages;
                    
                    if (oResponse.number === 0) {
                        self.aDeliveryRecords(oResponse.content);
                    } else {
                        for (var i = 0; i < oResponse.content.length; i++) {
                            self.aDeliveryRecords.push(oResponse.content[i]);
                        }
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

    this.aDeliveryRecords = ko.observableArray(),
    this.oCriteria = ko.observable(),
    this.iTotalPages = ko.observable(),
    this.navTap = function (data, event) {
        var target = event.target;
        var nNavType = parseInt(target.dataset["type"]);

        $(".weui_navbar > div").removeClass("weui_bar_item_on");
        $(target).addClass("weui_bar_item_on");

        $(document.body).infinite()
        $.showLoading("正在加载...");
        this.oCriteria.keywords.deliveryStatus = nNavType;
        this.oCriteria.paging.index = 0;
            
        if (nNavType === 2) {
            if (this.oCriteria.sort.deliveryDate) {
            	delete this.oCriteria.sort.deliveryDate;
            }
            this.oCriteria.sort.acknowledgementDate = "DESC";
        }
            
        loadData();
    },
    this.confirmReceipt = function (oDeliveryRecord) {
        $.confirm("是否确认收货？", function () {
            $.showLoading("正在加载...");
            $.ajax({
                url: "/api/wechat/deliveries/5280/receipts/" + oDeliveryRecord.id,
                type: "PUT",
                success: function (oDeliveryRecord) {
                    if (oDeliveryRecord) {
                        $.hideLoading();
                        $.toast("确认收货成功！", function () {
                        	$("#deliveredTab").click();
                        });
                    } else {
                        $.hideLoading();
                        $.alert("确认收货失败！");
                    }
                },
                error: function () {
                    $.hideLoading();
                    $.toast("数据加载失败", "forbidden");
                }
            });
        }, function () {
            return;
        });
    },
    this.init = function () {
        $(document.body).infinite();
        this.oCriteria = {
            "paging": {
                "index": 0,
                "size": 10
            },
            "keywords": {
                "positionId": 5280,
                "deliveryStatus": 0,
            },
            "sort": {
                "deliveryDate": "DESC"
            }
        };
        this.aDeliveryRecords([]);
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