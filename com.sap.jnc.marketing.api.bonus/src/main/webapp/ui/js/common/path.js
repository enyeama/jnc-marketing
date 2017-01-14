var PATH = {};
var ROOT = '/api/wechat';
var HTML_ROOT = '/wechat';
PATH.PORTAL = {

    //移动访销
    TERMINAL_ADD: ROOT + '/terminal/create',
    TERMINAL_LIST: ROOT + '/terminal/list',
    TERMINAL_FIND_BY_ID: ROOT + '/terminal/find',
    TO_TERMINAL_LIST: HTML_ROOT + '/ydfx/terminal/terminal.html',
    TO_TERMINAL_ADD_ONE: HTML_ROOT + '/ydfx/terminal/terminal_add_one.html',
    TO_TERMINAL_ADD_TWO: HTML_ROOT + '/ydfx/terminal/terminal_add_two.html',
    TERMINAL_ACCOUNT: ROOT + 'terminal/account',
    TO_TERMINAL_ACCOUNT_ADD: HTML_ROOT + '/ydfx/terminal/terminal_account_add.html',

    //微信
    WX_JS_CONFIG: ROOT + '/wechat/js_config',

    SCAN_CODE_SAVE: ROOT + 'order',
    //sales
    SALESMAN_ORDERS: ROOT + '/salesman/orders',
    TO_SALESMAN_ORDERS: HTML_ROOT + '/ydfx/sale/salesOrderSuccess.html',
    SALESMAN_TERMINAL: ROOT + '/salesman/terminals',
    //leaders
    LEADER_ORDERS: ROOT + '/leader/orders',
    LEADER_IN_OUT_CASE: ROOT + '/leader/logistic',
    LEADER_LOGISTIC_CHECK: ROOT + '/leader/logistic/validateQrCode',
    //small wine activity
    GAIN_WINE_CHECK: ROOT + '/activities/check5',
    GAIN_WINE_ADD: ROOT + '/activities',
    GAIN_WINE_LOCATION: ROOT + '/activities/location',
    
    SEND_SMS_CODE: ROOT + '/sms/send_code'
};
