var _keyUserName, _keyUserPhone, licensePicture, picture;
var chooseImg = function(element, flg){
    chooseWxCamera(function(data){
        previewFile(data.localIds[0], $(element).parents('.weui_uploader_input_wrp'), flg);
    });
};

//地址获取回调
var position_back = function(res){
    //$('input[name=GPSDescription]').val('精确度:' + res.accuracy);

    var url='http://apis.map.qq.com/ws/geocoder/v1/';

   url += '?get_poi=1&key=2WYBZ-MC7WX-3B44H-TGWDS-KEXVO-2PF6Z&output=jsonp&location=' + res.latitude.toString() + ',' + res.longitude.toString();

    $('input[name=accuracy]').val(res.accuracy);
    $('input[name=longitude]').val(res.longitude);
    $('input[name=latitude]').val(res.latitude);

   $.ajax({
     url:url,
     dataType:'jsonp',
     processData: true,
     type:'get',
     success:function(data){
         if(data.status == 0 && data.result){
             var component = data.result.address_component;
             $('input[name=gpsDescription]').val(data.result.address);
             $('input[name=fullAddress]').val(component.province + ' ' + component.city + ' ' + component.district);
             $('input[name=province]').val(component.province);
             $('input[name=city]').val(component.city);
             $('input[name=country]').val(component.district);

             //获取当前城市的区域编码
             findCountyId(component.district, component.city, component.province);
             //初始化当前区县可选择的编码
             initCountyId(component.city, component.province);
         }
     },
     error:function(XMLHttpRequest, textStatus, errorThrown) {
        alert(XMLHttpRequest.readyState);
     }
   });
};

//表单校验
var checkForm = function(){
    var _title = $('input[name=title]');
    var _countyId = $('input[name=countyId]');
    var _address = $('input[name=address]');
    var _registrantName = $('input[name=registrantName]');
    var _purchaserType = $('input[name=purchaserType]');
    var _cashPersonName = $('input[name=cashPersonName]');
    var _cashPersonPhone = $('input[name=cashPersonPhone]');
    var _longitude = $('input[name=longitude]');
    var _organizationCode = $('input[name=organizationCode]');
    var _keyUserPosition = $('input[name=keyUserPosition]');

    var message = '';
    message += !validateNotNull(_title.val()) ? ' 请录入门头名称 ' : '';
    message += !validateNotNull(_countyId.val()) ? '请录入区县编码 ' : '';
    message += !validateNotNull(_address.val()) ? '请录入网点地址 ' : '';
    message += !validateNotNull(_registrantName.val()) ? '请录入注册人姓名 ' : '';
    message += !validateNotNull(_organizationCode.val()) ?'请录入组织机构代码 ': '';
    //message += (_longitude.length > 0 && !_longitude.val())? '请定位GPS地址 ': '';

    message += (_purchaserType.length > 0 && !validateNotNull(_purchaserType.val())) ? '请录入关键人职位 ' : '';
    message += (_cashPersonName.length > 0 && !validateNotNull(_cashPersonName.val())) ? '请录资源兑付人姓名 ' : '';
    message += (_cashPersonPhone.length > 0 && !validatePhonenumber(_cashPersonPhone.val())) ? '资源兑付人电话错误 ' : '';
    message += (_keyUserPosition.length> 0 && !validateNotNull(_keyUserPosition.val())) ?'请选择关键人职位 ' : '';
    if(_keyUserPosition.length > 0){
        message += !validateNotNull(_keyUserName.val())?'请录入所选关键人姓名 ':'';
        message += !validateNotNull(_keyUserPhone.val())?'请录入所选关键人电话 ':'';
    }

    if(message == ''){
        return true;
    }else{
        $.alert(message, '提示');
        return false;
    }
};

//表单提交
var submitForm = function(){
    if(checkForm()){
        var options = $('#bind_terminal_form').serialize();
        options += ('&licensePicture=' + licensePicture);
        options += ('&picture=' + picture);
        var id = $('input[name=id]').val();
        var _isBanquetHotel = $('.bind_isBanquetHotel');
        var _inWholeSaleMarket = $('.bind_inWholeSaleMarket');
        if(_isBanquetHotel.size() > 0){
            var isBanquetHotel = _isBanquetHotel.prop("checked");
            options += ('&isBanquetHotel=' + (isBanquetHotel? 1: 0));
        }

        if(_inWholeSaleMarket.size() > 0){
            var inWholeSaleMarket = _inWholeSaleMarket.prop("checked");
            options += ('&inWholeSaleMarket=' + (inWholeSaleMarket? 1: 0));
        }
        
        $.post(PATH.PORTAL.TERMINAL_ADD, options, function (data) {
            $.toast(id ? '终端更新成功!': '终端创建成功!');
            // 终端创建完成，请求稽核
            noticeBanquet(id);
            setTimeout(function(){
                window.location.href = PATH.PORTAL.TO_TERMINAL_LIST;
            }, 1000);
        }).error(function () {
            $.toast(id ? '终端更新失败!': '终端创建失败!');
        });
    }
};

var noticeBanquet = function(terminalId) {
	$.post(PATH.PORTAL.BANQUET_AUDITS, {
		'type': 'TERMINAL',
		'targetId': terminalId
	}).error(function(error) {
		$.toast('稽核请求失败', 'forbidden');
	});
};

var viewModel = {
    info : ko.observable({}),
    initInfo: function(id){
        var _this = this;
        var options = {};
        options.id = id;
        $.get(PATH.PORTAL.TERMINAL_FIND_BY_ID, options, function(data){
            if(data){
                _this.info(data);
                var _isBanquetHotel = $('.bind_isBanquetHotel');
                var _inWholeSaleMarket = $('.bind_inWholeSaleMarket');
                if(_isBanquetHotel.size()  > 0 && data.banquetHotel){
                    _isBanquetHotel.prop("checked",true);
                }

                if(validateNotNull(data.pictureUrl)){
                    $('.bind_picture').css('background-image', 'url('+ data.pictureUrl +')');
                }

                if(validateNotNull(data.licensePictureURL)){
                    $('.bind_license_picture').css('background-image', 'url('+ data.licensePictureURL +')');
                }

                if(_inWholeSaleMarket.size() > 0 && data.inWholeSaleMarket){
                    _inWholeSaleMarket.prop("checked",true);
                }
            }
        });
    }
};
ko.applyBindings(viewModel);

$(function () {
    //变更title
    var id = COMMON.METHOD.GET_QUERY_STRING('id');
    var status = COMMON.METHOD.GET_QUERY_STRING('status');
    id ? viewModel.initInfo(id) : null;
    //status 0:查看 1:编辑
    if(status === '0'){
        $('input').attr('disabled', 'disabled');
        $('.bottomBtnBox').hide();
        $('.bind_gps_btn').hide();
        $('title').text('查看' + $('title').text());
    }else if(status === '1'){
        $('title').text('编辑' + $('title').text());
    }
    //隐藏关键人必录
    $('.bind_key_user_need').hide();
});

function changeKeyUser(element){
    var value = $(element).val();

    var _purchaserName = $('input[name=purchaserName]');
    var _purchaserPhone  =$('input[name=purchaserPhone]');
    var _supervisorName =$('input[name=supervisorName]');
    var _supervisorPhone =$('input[name=supervisorPhone]');
    var _businessName =$('input[name=businessName]');
    var _businessPhone =$('input[name=businessPhone]');
    var _promoterName =$('input[name=promoterName]');
    var _promoterPhone =$('input[name=promoterPhone]');
    if(value == '吧台主管'){
        _keyUserName =_supervisorName;
        _keyUserPhone =_supervisorPhone;
    }else if(value == '促销员'){
        _keyUserName =_promoterName;
        _keyUserPhone =_promoterPhone;
    }else if(value == '业务联系人'){
        _keyUserName =_businessName;
        _keyUserPhone =_businessPhone;
    }else if(value == '采购人'){
        _keyUserName =_purchaserName;
        _keyUserPhone =_purchaserPhone;
    }
    //隐藏keyuser*
    $('.bind_key_user_need').hide();
    //显示当情keyUser*
    $(_keyUserName.parents('.weui_cell')).find('.bind_key_user_need').show();
    $(_keyUserPhone.parents('.weui_cell')).find('.bind_key_user_need').show();

};

function initCountyId(cityName, provinceName){
    var options = {};
    options.cityName = cityName || '成都市';
    options.provinceName = provinceName || '四川省';
    $.get(PATH.PORTAL.REGION_LIST, options, function(data){
        var array = [];
        $.each(data, function (index, obj) {
           array[index] = obj.countyName + ' ' + obj.countyId;
        });

        $(".bind_county").select({
            title: "选择区县编码",
            items: array
        });
    });
};

function findCountyId(countyName, cityName, provinceName){
    var options = {};
    options.cityName = cityName || '成都市';
    options.provinceName = provinceName || '四川省';
    options.countyName = countyName || '武侯区';
    $.get(PATH.PORTAL.REGION_ONE, options, function (data) {
        $(".bind_county").val(data.countyId);
    });
};

function changeCounty(element) {
    $(element).val($(element).val().split(' ')[1]);
};

function previewFile(file, _obj, flg) {
    var reader = new FileReader();
    reader.onloadend = function () {
        _obj.css('background-image', 'url('+ reader.result +')');
        _obj.css('background-size', '100%');
        if(flg == 0){
            picture = reader.result;
        }else{
            licensePicture =reader.result;
        }
    }
    reader.readAsDataURL(file);
}

function changeFile(element) {
    var file = $(element).get(0).files[0];
    previewFile(file, $(element).parents('.weui_uploader_input_wrp'));
}

