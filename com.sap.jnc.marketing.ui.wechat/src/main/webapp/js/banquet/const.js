/**
 * @author Emily Yu
 * @time 2016-06-28
 * @description 宴会报备：常量
 */

// API地址常量
var PATH = PATH || {};
var ROOT_HTML = "/wechat/banquet";
var ROOT_API = "/api/wechat";
PATH.BANQUET = {
	// HTML
	HTML_HOME : ROOT_HTML,
	HTML_FORM : ROOT_HTML + "/form.html",
	HTML_DETAIL : ROOT_HTML + "/detail.html",
	HTML_SCAN_QR : ROOT_HTML + "/scanQR.html",
	HTML_SCAN_LOGISTICS : ROOT_HTML + "/scanLogistics.html",
	HTML_REBATE_QR : ROOT_HTML + "/rebateQR.html",
	HTML_REBATE_LOGISTICS : ROOT_HTML + "/rebateLogistics.html",
	HTML_ASSIGN : ROOT_HTML + "/assign.html",
	// API
	HOME : ROOT_API + "/banquets",
	UPDATE : ROOT_API + "/banquets/banquet",
	LIST : ROOT_API + "/banquets/pages",
	RELATED_INFO : ROOT_API + "/banquets/relatedinfo",
	CHANNEL : ROOT_API + "/banquets/channel",
	SCAN_QR : ROOT_API + "/liquors",
	SCAN_LOGISTICS : ROOT_API + "/liquors/legacy",
	REBATE : ROOT_API + "/banquet/rebate/commit/items",
	REBATE_SCAN : ROOT_API + "/banquet/rebate/scan/items",
	REBATE_SELECTION : ROOT_API + "/banquet/rebate/list/boxids",
	HANDLER : ROOT_API + "/handler",
	ASSIGN : ROOT_API + "/handler/assignment",
};

// 信息常量
var MESSAGE = {
	// 通用
	REPEAT : "此码已扫",
	NETWORK : "网络错误",
	SUCCESS : "操作成功",
	ERROR : "网络错误",
	SCAN_FIRST : "请先扫码",
	NO_DATA : "没有数据",
	// 扫码
	CONFLICT : "此码被占用",
	INVALID : "无效码",
	NOT_FOUND : "此码未找到",
	BAD_REQUEST : "请求失败，请重新请求",
	FORBIDDEN : "此码与报备物料不匹配",
	NOT_ACCEPTABLE : "格式不合法",
	ACCEPTED : "扫码成功",
	CREATED : "报备成功",
	NOT_MODIFIED : "报备不成功,请稍后重试",
	NOT_LEAGAL : "输入格式不符",
	PRECONDITION_FAILED : "找不到宴会ID",
	// 明细
	TOBECASH : "已审核",
	APPROVEFAILED : "已关闭",
	// 兑付
	MORE_THAN_ONE_RECORD : "重复数据",
	ALREADY_PAID : "已兑付",
	UNKOWN_STATUS : "未知状态",
};

// 枚举
var ENUMERATION = {
	banquetStatus : {
		"APPLIED" : "已报备待审核",
		"APPROVED" : "已审核待兑付",
		"NOT_APPROVED" : "审核未通过",
		"PAID" : "已兑付",
		"CANCELLED" : "已取消",
		"EXPIRED" : "已过期",
	},
	rebateTargetType : {
		"CONSUMER" : "消费者",
		"AGENT" : "拉单人",
		"AGENTSHOP" : "终端",
		"AGENTHOTEL" : "酒店关键人",
		"MERCHANDISER" : "跟单员",
	},
	applicationType : {
		"NORMAL" : "普通宴会",
		"HOTEL" : "酒店宴会",
		"JJN" : "金剑南报备制宴会"
	},
	scanType : {
		"QRCODE" : "二维码",
		"LOGISCODE" : "物流码",
	},
	terminalType : {
		"HOTEL" : "酒店",
		"SHOP" : "名烟名酒店",
		"KA" : ""
	},
	timePoint : {
		"NOON" : "中午",
		"NIGHT" : "晚上",
		"NOON_AND_NIGHT" : "中午和晚上"
	}
}