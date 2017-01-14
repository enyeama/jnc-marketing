(function() {
	// Controller
	angular.module('jnc-admin').controller('jnc-admin.menu.controller', [
	// Dependencies
	'$scope', '$http', '$location', '$authentication',
	// Main
	function($scope, $http, $location, $authentication) {
		// Construct
		var urlMapping = new UrlMapping();
		if ($location.path() === '/login') {
			$scope.user = null;
		}
		else {
			refresh({
				newUrl : '/login'
			});
		}
		/**
		 * Load and check roles and privileges
		 */
		function load() {
			$authentication.check(
			// Success
			function(data) {
				if (!data || !data.roles) {
					$location.path('/login');
					return;
				}
				$scope.user = data;
				var sAuthenName = (data.roles[0].name) ? data.roles[0].name : "menu";
				var sFileName = matchMenuConfigFile(sAuthenName);
				$http.get('modules/_resources/menu/' + sFileName + '.json').success(function(urlMappings) {
					urlMapping.attachProperties(urlMappings);
				})
			},
			// Error
			function() {
				$scope.user = null;
			});
		}

		function matchMenuConfigFile(sRoleName) {
			switch (sRoleName) {
				case '超级管理员':
					return 'superadmin';
					break;
				case 'KA专员':
					return 'KAofficer';
					break;
				case 'KA事业部内勤':
					return 'KAstaff';
					break;
				case '产销协调办A':
					return 'productionandmarketingcoordinatorA';
					break;
				case '办事处主任':
					return 'chiefoffice';
					break;
				case '城市经理':
					return 'citymanager';
					break;
				case '大组长':
					return 'teamleader';
					break;
				case '省区经理':
					return 'provincemanager';
					break;
				case '财务部内勤':
					return 'financialstaff';
					break;
				case '平台经销商':
					return 'dealerinplatform';
					break;
				case '非平台经销商':
					return 'dealernotinplatform';
					break;
				case '人资部人力专员':
					return 'HRofficer';
					break;
				case '市管部内勤A':
					return 'cityofficestaffA';
					break;
				case '市管部内勤B':
					return 'cityofficestaffB';
					break;
				case '市管部内勤C':
					return 'cityofficestaffC';
					break;
				case '市管部内勤D':
					return 'cityofficestaffD';
					break;
				case '市管部内勤E':
					return 'cityofficestaffE';
					break;
				case '市管部内勤F':
					return 'cityofficestaffF';
					break;
				case '市管部内勤G':
					return 'cityofficestaffG';
					break;
				case '市管部内勤H':
					return 'cityofficestaffH';
					break;
				case '拉环室内勤A':
					return 'ringpullstaffA';
					break;
				case '拉环室内勤B':
					return 'ringpullstaffB';
					break;
				case '拉环室操作员A':
					return 'ringpulloperatorA';
					break;
				case '拉环室操作员B':
					return 'ringpulloperatorB';
					break;
				case '销管部物料处E':
					return 'salesofficestockE';
					break;
				case '销管部物料处F':
					return 'salesofficestockF';
					break;
				case '销管部物料处I':
					return 'salesofficestockI';
					break;
				case '销管部物料处K':
					return 'salesofficestockK';
					break;
				case '销管部信息处A':
					return 'salesinformationofficeA';
					break;
				case '销管部信息处C':
					return 'salesinformationofficeC';
					break;
				case '销管部客户处B':
					return 'salesofficecustomerB';
					break;
				case '销管部核销处D':
					return 'salesofficecavD';
					break;
				case '销管部核销处G':
					return 'salesofficecavG';
					break;
				case '销管部核销处H':
					return 'salesofficecavH';
					break;
				case '销管部核销处J':
					return 'salesofficecavJ';
					break;
				case '销管部核销处M':
					return 'salesofficecavM';
					break;
				case '销管部促品处L':
					return 'salesofficepromotionL';
					break;
				case '稽核部所有人':
					return 'auditofficestaff';
					break;
				default:
					return 'menu';
					break;
			}
		}
		/**
		 * Refresh menu from authentication context
		 */
		function refresh(evtArgs, newUrl, oldUrl) {
			if ((newUrl && newUrl.indexOf('login') !== -1)) {
				$scope.user = null;
			}
			else if ((oldUrl && oldUrl.indexOf('login') !== -1)) {
				load();
			}
			else if (!$scope.user) {
				load();
			}
		}
		// Events
		$scope.$on('$locationChangeSuccess', refresh);
		/**
		 * Name and URL mappings
		 */
		function UrlMapping() {
			/*
			 * Attach property from mappings
			 */
			function _attachProperty(target, mappings) {
				var mapping = _.find(mappings, function(mapping) {
					return mapping.name && mapping.name === target.name;
				});
				if (mapping) {
					target.href = mapping.href;
					target.icon = mapping.icon;
				}
			}
			/*
			 * Attach properties from mappings
			 */
			this.attachProperties = function(urlMappings) {
				if ($scope.user && $scope.user.roles) {
					// Role assignment
					_.each($scope.user.roles, function(role) {
						_attachProperty(role);
						if (role.privileges) {
							// 1st Privilege assignment
							_.each(role.privileges, function(privilegeParent) {
								_attachProperty(privilegeParent, urlMappings);
								if (privilegeParent.children) {
									// 2nd Privilege assignment
									_.each(privilegeParent.children, function(privilegeChild) {
										var matchUrlMappings = _.filter(urlMappings, function(urlMapping) {
											return urlMapping.name === privilegeParent.name;
										});
										if (matchUrlMappings.length) {
											_attachProperty(privilegeChild, _.reduce(matchUrlMappings, function(a, b) {
												return {
													items : _.union(a.items, b.items)
												};
											}).items);
										}
									});
								}
							});
						}
					});
				}
			};
		}
	} ]);
})();