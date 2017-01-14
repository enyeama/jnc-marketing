function DataModel() {
    var self = this;

    function loadMaterialData() {
        $.get("/api/wechat/consumptions/5280/materials", function (aMaterials) {
            self.aMaterialCategories(aMaterials);
            $("#materialCategories").select({
                title: "选择物料",
                items: aMaterials
            });
            $.hideLoading();
        }).fail(function() {
            $.hideLoading();
            $.toast("物料信息加载失败", "forbidden");
        });
    }

    this.aMaterialCategories = ko.observableArray([]),
    this.sMaterialId = ko.observable(),
    this.iPaidQuantityValue = ko.observable(),
    this.sPaymentComment = ko.observable(),
    this.fnConfirmInput = function () {
        var consumptionRecord = {};

        if ((typeof this.sMaterialId()) === "undefined") {
            $.alert("请选择物料类型！");
        } else if ((typeof this.iPaidQuantityValue()) === "undefined" || this.iPaidQuantityValue() === "") {
            $.alert("输入兑付数量！");
        } else if(isNaN(this.iPaidQuantityValue())) {
            $.alert("输入合法的兑付数量！");
        } else {
            $.showLoading("兑付录入中...");
            consumptionRecord.materialId = $("#materialCategories").data("values");
            consumptionRecord.paidQuantityValue = this.iPaidQuantityValue();
            consumptionRecord.paymentComment = this.sPaymentComment();
            console.log(consumptionRecord);
            $.ajax({
                url: "/api/wechat/consumptions/5280/consumption/" + consumptionRecord.materialId,
                type: "PUT",
                data: JSON.stringify(consumptionRecord),
                dataType: "text",
                contentType: "application/json",
                success: function(oConsumptionRecord) {
                    $.hideLoading();
                    if (oConsumptionRecord) {
                        $.toast("兑付录入成功！请等待页面跳转");
                        setTimeout(function() {
                            window.location.href = "consumptionRecords.html";
                        }, 2000);
                    } else {
                        $.toast("兑付录入失败", "forbidden");
                    }
                },
                error: function() {
                    $.hideLoading();
                    $.toast("兑付录入失败", "forbidden");
                }
            });
        }
    },
    this.init = function() {
        $(document.body).infinite();
        $.showLoading("正在加载物料数据...");
        loadMaterialData();
    }
};

$(function () {
    var oDataModel = new DataModel();

    ko.applyBindings(oDataModel);
    oDataModel.init();
});