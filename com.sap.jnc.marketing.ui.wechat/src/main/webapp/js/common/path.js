var PATH = {};
var ROOT = '/api/wechat';
var HTML_ROOT = '/wechat/ydfx';

PATH.PORTAL = {

	// 移动访销
	TERMINAL_ADD : ROOT + '/terminal/create',
	TERMINAL_LIST : ROOT + '/terminal/list',
	TERMINAL_FIND_BY_ID : ROOT + '/terminal/find',

	TERMINAL_ACCOUNT : ROOT + '/terminal/account',
	TERMINAL_ACCOUNT_DETAIL : ROOT + '/terminal/account_detail',
	TERMINAL_ACCOUNT_ADD : ROOT + '/terminal/account_add',

	TO_TERMINAL_ACCOUNT : HTML_ROOT + '/terminal/terminal_account.html',
	TO_TERMINAL_ACCOUNT_ADD : HTML_ROOT + '/terminal/terminal_account_add.html',
	TO_TERMINAL_LIST : HTML_ROOT + '/sale/terminal.html',
	TO_TERMINAL_ADD_ONE : HTML_ROOT + '/terminal/terminal_add_one.html',
	TO_TERMINAL_ADD_TWO : HTML_ROOT + '/terminal/terminal_add_two.html',

	// 微信
	WX_JS_CONFIG : ROOT + '/wechat/js_config',

	SCAN_CODE_SAVE : ROOT + 'order',
	// sales
	SALESMAN_ORDERS : ROOT + '/salesman/orders',
	SALESMAN_TERMINAL : ROOT + '/salesman/terminals',

    TO_SALESMAN_ORDERS : HTML_ROOT + '/sale/salesOrderSuccess.html',

	// leaders
	LEADER_ORDERS : ROOT + '/leader/orders',
	LEADER_IN_OUT_CASE : ROOT + '/leader/logistic',
	LEADER_LOGISTIC_CHECK : ROOT + '/leader/logistic/validateqrcode',
	// small wine activity
	GAIN_WINE_CHECK : ROOT + '/activities/check5',
	GAIN_WINE_ADD : ROOT + '/activities',
	GAIN_WINE_LOCATION : ROOT + '/activities/location',

	// send code
	SEND_SMS_CODE : ROOT + '/sms/send_code',

	// 注册
	REGISTER_TERMINAL : ROOT + '/register/terminal',
	REGISTER_EMPLOYEE : ROOT + '/register/employee',

	// KA
	KA_LIST : ROOT + '/ka/sales/terminals',
	KA_SAVE : ROOT + '/ka/sales/save',
	KA_INFO : ROOT + '/ka/info',

	TO_KA_LIST : HTML_ROOT + '/ydfx/ka/ka_store_list.html',

	// region
	REGION_LIST : ROOT + '/region/list',
	REGION_ONE : ROOT + '/region/one',

	// banquet
	BANQUET_AUDITS : '/api/admin/audits',
	
	// terminal
	TERMINAL_ORDERS : ROOT + '/terminal/orders',
	TERMINAL_ORDER_DETAIL : ROOT + '/terminal/orders/detail',
	TERMINAL_LOGISTIC : ROOT + '/terminal/logistic',
	TERMINAL_LOGISTIC_VALIDATEQRCODE : ROOT + '/terminal/logistic/validateqrcode',
};
// test data
CONSTANT = {
	YDFX : {
		dealerId : '',
		salesId : '96',
		salesExtId : '612',
		leaderId : '104',
		leaderExtId : '628',
		salesCategoryId : '8600',
		chanelId : '14',
		terminalId : '10000000'
	}
}

function toUrl(url) {
	location.href = HTML_ROOT + url;
};
