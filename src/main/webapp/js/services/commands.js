app.factory('commands', [ '$http', function($http) {
	return {
		get : function() {
			return $http.get('command').success(function(data) {
				return data;
			}).error(function(err) {
				return err;
			});
		},
		post : function(jData) {
			return $http.post('command', jData).success(function(data) {
				return data;
			}).error(function(err) {
				return err;
			});
		}
	};
} ]);