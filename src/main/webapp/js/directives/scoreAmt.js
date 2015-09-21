app.directive("scoreAmt", function() {
	return {
		restrict : 'E',
		scope : {
			question : '=',
			amount : '=',
			index : '=',
		},
		templateUrl : 'js/directives/scoreAmt.html',
		link : function(scope, element, attrs) {
			scope.getStyle = function() {
				var style = {
					"animation-delay" : ((16 - scope.index) * 100) + 'ms'
				}
				return style;
			};
		}
	}
});