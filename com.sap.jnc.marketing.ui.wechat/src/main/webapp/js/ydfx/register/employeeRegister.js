/**
 * @author I323560
 * @time 2016-07-05
 */
$(document).ready(function() {
	$.alert("Test openId");
	$.ajax({
		url : '/api/wechat/authentication/user/openid'
	}).done(function(result) {
		$.alert('Done:' + result);
		$("#reg_form input[name='idNumber']").val(result);
	}).fail(function(result) {
		$.alert('Fail:' + result);
	}).always(function(result) {
		$.alert('Always:' + JSON.stringify(result));
	});
});
// 注册按钮
$(document).on('click', '.regBtn', function() {
	var employeeName = $("#reg_form input[name='employeeName']").val();
	var employeePhone = $("#reg_form input[name='employeePhone']").val();
	var verificationCode = $("#reg_form input[name='verificationCode']").val();
	var employeeNumber = $("#reg_form input[name='employeeNumber']");
	var idNumber = $("#reg_form input[name='idNumber']");
	// 验证姓名
	if (!validateNotNull(employeeName)) {
		$.toast("请输入姓名！", "cancel");
		return;
	}
	// 验证手机号
	if (!(validatePhonenumber(employeePhone))) {
		$.toast("请正确格式的手机号码！", "cancel");
		return;
	}
	// 验证码
	if (!validateNotNull(verificationCode)) {
		$.toast("请输入验证码！", "cancel");
		return;
	}
	// 验证工号
	if (employeeNumber.size() > 0 && !validateNotNull(employeeNumber.val())) {
		$.toast("工号不能为空！", "cancel");
		return;
	}
	// 验证身份证
	if (idNumber.size() > 0 && !validateIDcard(idNumber.val())) {
		$.toast("请正确格式的身份证！", "cancel");
		return;
	}

	var options = $('#reg_form').serialize();
	$.post(PATH.PORTAL.REGISTER_EMPLOYEE, options, function(data) {
		$.showLoading();
		setTimeout(function() {
			$.hideLoading();
			// 跳转到首页
			window.location.href = "index.html";
			$(".yz_code").attr("title", "stop");
		}, 2000);
	}).fail(function(data) {
		if (data && data.status == 200) {
			$.showLoading();
			setTimeout(function() {
				$.hideLoading();
				// 跳转到首页
				window.location.href = "index.html";
				$(".yz_code").attr("title", "stop");
			}, 2000);
		}
		else {
			$.toast("注册失败!请确认注册信息!")
		}
	}).done(function(data) {
		window.location.href = "index.html";
	});

});

function sendSuccess() {
	$.alert('验证码已发送，请注意查收。');
	var seconds = 45;
	$(obj).removeClass("jpform_yzCode").addClass("jp_disabled");
	$(obj).removeAttr("onclick");
	$(obj).attr("title", "run");
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
function obtain_code(obj) {
	var employeePhone = $("#reg_form input[name='employeePhone']").val();
	// 验证手机号
	if (!(validatePhonenumber(employeePhone))) {
		$.toast("请正确格式的手机号！", "text");
		return;
	}
	var options = {};
	options.phone = employeePhone;
	options.type = 0;
	$.post(PATH.PORTAL.SEND_SMS_CODE, options, options, function() {
		sendSuccess();
	}, 'json').fail(function(data) {
		if (data && data.status == 200) {
			sendSuccess();
		}
		else {
			$.alert('短信验证码发送失败!');
		}
	});
}