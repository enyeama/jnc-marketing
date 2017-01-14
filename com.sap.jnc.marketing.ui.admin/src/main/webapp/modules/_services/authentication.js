angular.module('jnc-admin').factory('$authentication', [
// Dependencies
'$http', '$location', '$constant',
// Main
function($http, $location, $constant) {
	// Context definition
	function AuthenticationContext() {
		// Definitions
		var _this = this;
		_this.content = null;
		/**
		 * Load authentication
		 */
		this.check = function(success, error) {
			$http.get($constant.URL_API + 'authentication/user')
			// Authenticated
			.success(function(data) {
				_this.content = data;
				if (typeof (success) === 'function') {
					success(data);
				}
			})
			// Non-authenticated
			.error(function() {
				_this.content = null;
				if (typeof (error) === 'function') {
					error();
				}
				$location.path('/login');
			})
			return this;
		};
		// Return
		return this;
	}
	// Return
	return new AuthenticationContext();
} ]);