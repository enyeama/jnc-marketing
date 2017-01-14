(function() {
	// Router
	angular.module('jnc-admin').config([ '$routeProvider', '$locationProvider', function($routeProvider, $locationProvider) {
		$routeProvider
		/** ****************************************************** */
		/** ************************* Home *********************** */
		/** ****************************************************** */
		// Home
		.when('/home', {
			templateUrl : 'modules/_views/home.html',
			controller : 'jnc-admin.home.controller'
		})
		// About
		.when('/about', {
			templateUrl : 'modules/_views/about.html',
			controller : 'jnc-admin.about.controller'
		})
		/** ****************************************************** */
		/** ************************ Authentication ************** */
		/** ****************************************************** */
		// Authentication - Login
		.when('/login', {
			templateUrl : 'modules/authentication/views/login.html',
			controller : 'jnc-admin.authentication.login.controller'
		})
		// Authentication - Profile
		.when('/profile', {
			templateUrl : 'modules/authentication/views/profile.html',
			controller : 'jnc-admin.authentication.profile.controller'
		})
		/** ****************************************************** */
		/** ************************* Ring Office *********************** */
		/** ****************************************************** */
		.when('/banquet/ringPostList/:operate', {
			templateUrl : 'modules/banquet/views/ringPostList.html',
			controller : 'jnc-admin.banquet.ringPostList.controller'
		}).when('/banquet/banquets/writeoff', {
			templateUrl : 'modules/banquet/views/banquetWriteoffList.html',
			controller : 'jnc-admin.banquet.banquetWriteoffList.controller'
		}).when('/banquet/banquets/application', {
			templateUrl : 'modules/banquet/views/banquetReportVerificationList.html',
			controller : 'jnc-admin.banquet.banquetReportVerificationList.controller'
		}).when('/banquet/banquets/sign', {
			templateUrl : 'modules/banquet/views/banquetSignList.html',
			controller : 'jnc-admin.banquet.banquetSignList.controller'
		})
		/** ****************************************************** */
		/** ************************* Audit *********************** */
		/** ****************************************************** */
		// Audit - Query
		.when('/audit/auditQuery/:role&:operate', {
			templateUrl : 'modules/audit/views/auditQuery.html',
			controller : 'jnc-admin.audit.auditQuery.controller'
		})
		// Audit - Assign
		.when('/audit/assign', {
			templateUrl : 'modules/audit/views/assign.html',
			controller : 'jnc-admin.audit.assign.controller'
		})
		/** ****************************************************** */
		/** ************************* Migration *********************** */
		/** ****************************************************** */
		// Migration - Province Manager
		.when('/migration/provincemanager', {
			templateUrl : 'modules/migration/views/provincemanager.html',
			controller : 'jnc-admin.migration.provincemanager.controller'
		}).when('/migration/employee', {
			templateUrl : 'modules/migration/views/employee.html',
			controller : 'jnc-admin.migration.employee.controller'
		}).when('/migration/employeeupload', {
			templateUrl : 'modules/migration/views/employeeupload.html',
			controller : 'jnc-admin.migration.employeeupload.controller'
		}).when('/migration/dealer', {
			templateUrl : 'modules/migration/views/dealer.html',
			controller : 'jnc-admin.migration.dealer.controller'
		}).when('/migration/dealerimport', {
			templateUrl : 'modules/migration/views/dealerimport.html',
			controller : 'jnc-admin.migration.dealerimport.controller'
		}).when('/migration/product', {
			templateUrl : 'modules/migration/views/product.html',
			controller : 'jnc-admin.migration.product.controller'
		}).when('/migration/productimport', {
			templateUrl : 'modules/migration/views/productimport.html',
			controller : 'jnc-admin.migration.productimport.controller'
		})
		/** ****************************************************** */
		/** ************************* Bonus ********************** */
		/** ****************************************************** */
		.when('/bonus/list', {
			templateUrl : 'modules/bonus/views/list.html',
			controller : 'jnc-admin.bonus.list.controller'
		}).when('/bonus/new', {
			templateUrl : 'modules/bonus/views/detail.html',
			controller : 'jnc-admin.bonus.detail.controller'
		}).when('/ka/branchmanagement', {
			templateUrl : 'modules/ka/views/branchmanagement.html',
			controller : 'jnc-admin.ka.branchmanagement.controller'
		}).when('/ka/ordermanagement', {
			templateUrl : 'modules/ka/views/ordermanagement.html',
			controller : 'jnc-admin.ka.ordermanagement.controller'
		}).when('/bonus/logisticsRebateList', {
			templateUrl : 'modules/bonus/views/logisticsRebateList.html',
			controller : 'jnc-admin.bonus.logisticsRebateList.controller'
		})
		/** ****************************************************** */
		/** ************************* YDFX **************** */
		/** ****************************************************** */
		.when('/ydfx/dealerconfig', {
			templateUrl : 'modules/ydfx/views/dealerConfig.html',
			controller : 'jnc-admin.ydfx.dealerConfig.controller'
		}).when('/ydfx/prodectsalescategory', {
			templateUrl : 'modules/ydfx/views/prodectSalesCategory.html',
			controller : 'jnc-admin.ydfx.prodectSalesCategory.controller'
		})
		/** ****************************************************** */
		/** ************************* Contract **************** */
		/** ****************************************************** */
		.when('/migration/contract', {
			templateUrl : 'modules/contract/views/contractquery.html',
			controller : 'jnc-admin.contract.contractquery.controller'
		}).when('/migration/contractimport', {
			templateUrl : 'modules/contract/views/contractimport.html',
			controller : 'jnc-admin.contract.contractimport.controller'
		})
		/** ****************************************************** */
		/** ******************** Spare Material ****************** */
		/** ****************************************************** */
		// spare material
		.when('/sparematerial/safestock', {
			templateUrl : 'modules/sparematerial/views/safestock.html',
			controller : 'jnc-admin.sparematerial.safestock.controller'
		})
		// spare material
		.when('/sparematerial/deliverydetail', {
			templateUrl : 'modules/sparematerial/views/deliverydetail.html',
			controller : 'jnc-admin.sparematerial.deliverydetail.controller'
		})
		// spare material
		.when('/sparematerial/consumptiondetail', {
			templateUrl : 'modules/sparematerial/views/consumptiondetail.html',
			controller : 'jnc-admin.sparematerial.consumptiondetail.controller'
		})
		// spare material - safe stock upload
		.when('/sparematerial/safestockimport', {
			templateUrl : 'modules/sparematerial/views/safestockimport.html',
			controller : 'jnc-admin.sparematerial.safestockimport.controller'
		})
		/** ****************************************************** */
		/** ************************* Payment *********************** */
		/** ****************************************************** */
		.when('/payment/list', {
			templateUrl : 'modules/payment/views/list.html',
			controller : 'jnc-admin.payment.list.controller'
		}).when('/payment/new', {
			templateUrl : 'modules/payment/views/detail.html',
			controller : 'jnc-admin.payment.detail.controller'
		})
		// Payment Detail
		.when('/payment/detail/:id', {
			templateUrl : 'modules/payment/views/detail.html',
			controller : 'jnc-admin.payment.detail.controller'
		})
		/** ****************************************************** */
		/** ************************* Dealers *********************** */
		/** ****************************************************** */
		.when('/dealers/smIn', {
			templateUrl : 'modules/dealers/views/dealerSMIn.html',
			controller : 'jnc-admin.dealers.dealerSMIn.controller'
		}).when('/dealers/smPtOut', {
			templateUrl : 'modules/dealers/views/dealerSMPTOut.html',
			controller : 'jnc-admin.dealers.dealerSMPTOut.controller'
		}).when('/dealers/smPtOutSearch', {
			templateUrl : 'modules/dealers/views/dealerSMPTOutSearch.html',
			controller : 'jnc-admin.dealers.dealerSMPTOutSearch.controller'
		}).when('/dealers/smNPtOrders', {
			templateUrl : 'modules/dealers/views/dealerSMNPTOrders.html',
			controller : 'jnc-admin.dealers.dealerSMNPTOrders.controller'
		}).when('/dealers/smNPtOut', {
			templateUrl : 'modules/dealers/views/dealerSMNPTOut.html',
			controller : 'jnc-admin.dealers.dealerSMNPTOut.controller'
		})
		/** ****************************************************** */
		/** ************************* Error *********************** */
		/** ****************************************************** */
		.when('/error', {
			templateUrl : 'modules/_views/error.html',
			controller : 'jnc-admin.error.controller'
		}).otherwise({
			redirectTo : '/error'
		});
	} ]);
})()