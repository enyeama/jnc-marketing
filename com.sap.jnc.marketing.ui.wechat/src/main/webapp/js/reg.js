/**
 * @author yikai.zhu
 * @time 2015-11-08
 */
	//注册按钮
$(document).on('click','.regBtn', function () {
	var Uname = $("#reg_form input[name='userName']").val();
	var Utel = $("#reg_form input[name='phoneNumber']").val();
	var Ucode = $("#reg_form input[name='code']").val();
	var _Jnumber = $("#reg_form input[name='jobNUmber']");
	var _Ucard = $("#reg_form input[name='IDcard']");
	//验证码
	if (!validateNotNull(Uname)) {
		$.toast("请输入姓名！","cancel");
		return;
	}
	//验证手机号
	if (!(validatePhonenumber(Utel))) {
		$.toast("请正确格式的手机号码！","cancel");
		return;
	}
	//验证码
	if (!validateNotNull(Ucode)) {
		$.toast("请输入验证码！","cancel");
		return;
	}

	//工号
	if (_Jnumber.size() > 0 && !validateNotNull(_Jnumber.val())) {
		$.toast("工号不能为空！","cancel");
		return;
	}
	//身份证
	if(_Ucard.size() > 0 && !validateIDcard(_Ucard.val())) {
			$.toast("请正确格式的身份证！","cancel");
		return;
	}

	var options = $('#reg_form').serialize();
	$.post(PATH.PORTAL.REGISTER_TERMINAL, options, function(data){
		$.showLoading();
		setTimeout(function () {
			$.hideLoading();
			//跳转到首页
			window.location.href="index.html";
			$(".yz_code").attr("title","stop");
		}, 2000);
	}).error(function(data){
		if(data && data.status == 200){
			$.showLoading();
			setTimeout(function () {
				$.hideLoading();
				//跳转到首页
				window.location.href="index.html";
				$(".yz_code").attr("title","stop");
			}, 2000);
		}else{
			$.toast("注册失败!请确认注册信息!")
		}
	});

});


function sendSuccess() {
	$.alert('验证码已发送，请注意查收。');
	var seconds = 45;
	$(obj).removeClass("jpform_yzCode").addClass("jp_disabled");
	$(obj).removeAttr("onclick");
	$(obj).attr("title","run");
	function time() {
		seconds--;
		$(obj).html(seconds + "秒后重发");
		var i = $(obj).attr("title");
		if (seconds == 0 || i == "stop") {
			window.clearInterval(inter);
			$(obj).removeClass("jp_disabled").addClass("jpform_yzCode");
			$(obj).html("获取验证码");
			$(obj).attr("onclick", "obtain_code(this)");
		}
	}
	var inter = setInterval(time, 1000);
}
// 获取验证码
function obtain_code(obj){
	var Utel = $("#reg_form input[name='phoneNumber']").val();
	//验证手机号
	if (!(validatePhonenumber(Utel))) {
		$.toast("请正确格式的手机号！","text");
		return;
	}
	var options = {};
	options.phone = Utel;
	options.type = 0;
	$.post(PATH.PORTAL.SEND_SMS_CODE, options, options, function () {
		sendSuccess();
	}, 'json').error(function(data){
		if(data && data.status == 200){
			sendSuccess();
		}else{
			$.alert('短信验证码发送失败!');
		}
	});
}