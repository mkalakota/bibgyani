app.directive("opActions", [ 'commands', function(commands) {
	return {
		restrict : 'E',
		scope : {
			action : '=',
			question : '='
		},
		templateUrl : 'js/directives/opActions.html',
		link : function(scope, element, attrs) {
			$("input#question-upload").on("change");
			scope.sendCommand = function(name, value) {
				commands.post({
					"name" : name,
					"value" : value,
				}).success(function(response) {
					if (response.error) {
						console.log("Failed to send command: " + response.message);
						alert(response.message);
					} else {
						console.log("Sent command successfully: " + name);
					}
				});
			};
			scope.showAnswer = function() {
				var question, answer;
				question = scope.question;
				if (question) {
					if (question.dual) {
						answer = String.fromCharCode(97 + question.answer);
						if (question.dual.indexOf(answer) === -1) {
							scope.sendCommand("finish_game");
							return;
						}
					}
					scope.sendCommand("show_answer", scope.isWithinAmountTime());
				}
			};

			scope.isWithinAmountTime = function() {
				var iPassedTime;
				if (scope.question && !scope.lifeline_used) {
					iPassedTime = 60 - nebib.timer.getRemainingTime();
					if (iPassedTime < scope.question.amountExtraAt) {
						return true;
					}
				}
				return false;
			};

			scope.fileUploadSuccess = function(message) {
				if (!message) {
					alert("Done!");
					return;
				}
				message = JSON.parse(message);
				if (message.error) {
					alert("Failed to upload: " + message.message);
				}
			};

			scope.fileUploadError = function(message) {
				if (console && console.log) {
					console.log(message);
				}
			};
		}
	}
} ]);