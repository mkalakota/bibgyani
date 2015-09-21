app.directive("lifeLine", [ 'commands', function(commands) {
	return {
		restrict : 'E',
		scope : {
			name : '=',
			question : '=',
			action : "=",
			used : '='
		},
		templateUrl : 'js/directives/lifeLine.html',
		link : function(scope, element, attrs) {
			scope.sendCommand = function(value) {
				var jData;
				if (!value || !scope.action.showActions) {
					return;
				}
				if (scope.used) {
					return;
				}
				if (value === 'dual') {
					$("#dual-life-line-modal").modal();
					return;
				}
				commands.post({
					"name" : "use_lifeline",
					"value" : value,
				}).success(function() {
					console.log("Sent command successfully: use_lifeline - " + value);
				});
			};
		}
	}
} ]);