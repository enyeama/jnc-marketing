/**
 * @author yikai.zhu
 * @time 2015-11-08
 * @description 通用表单字段验证封装
 * @final modification 2015-11-17
 */
// 字符不为空
function validateNotNull(txt) {
	if (txt !== null && txt != undefined && txt.length > 0) {
		return true;
	}
}

// 密码
function validatePwd(txt) {
	if (txt !== null && txt != undefined && txt.length > 6) {
		return true;
	}
}

// 重复密码验证
function validateRepwd(txt, retext) {
	if (txt === retext) {
		return true;
	}
}

// 性别
function validateSelect(select) {
	if (select != null) {
		return true;
	}
}
// 身份证v
function validateIDcard(card) {
	var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
	return reg.test(card);
}
// 手机号
function validatePhonenumber(number) {
	if (!number) {
		return false;
	}
	else {
		if (number.match(/^1(3|4|5|7|8){1}\d{9}$/)) {
			return true;
		}
		else {
			return false;
		}
	}
}
// 验证码
function validateVerificationCode(vCode) {
	var reg = /^[0-9]{4}$/;
	return reg.test(vCode);
}

//校验
var Val = {
	notNull: function (a) {
		return validateNotNull(a)
	},

	pwd: function (a) {
		return validatePwd(a);
	},

	rePwd: function (a, b) {
		return validateRepwd(a, b);
	}
};