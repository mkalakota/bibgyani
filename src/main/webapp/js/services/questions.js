app.factory('questions', [ '$http', function($http) {
	return {
		get : function(id) {
			return $http.get('questions/' + id).success(function(data) {
				return data;
			}).error(function(err) {
				return err;
			});
		}
	}
} ]);