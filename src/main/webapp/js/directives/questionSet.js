app.directive("questionSet", [ 'commands', function(commands) {
	return {
		restrict : 'E',
		scope : {
			q : '=',
			a : '=',
			custanim : '=',
			tel : '=',
			status : '='
		},
		templateUrl : 'js/directives/questionSet.html',
		link : function(scope, element, attrs) {
			scope.sendCommand = function(value) {
				if (!scope.a.showActions || scope.q.dual) {
					return;
				}
				commands.post({
					"name" : "select_option",
					"value" : value,
				}).success(function() {
					console.log("Sent command successfully: select_option - " + value);
				});
			};

			scope.getOptionStatus = function(option) {
				var question = scope.q;
				var showAnswer = scope.a.showAnswer || question.showAnswer;
				var selectedOption = question.selectedOption === option;
				selectedOption = selectedOption || (question.dual && question.dual.indexOf(option) !== -1);
				if (showAnswer) {
					if ((question.answer === 0 && option === 'a')//
							|| (question.answer === 1 && option === 'b')//
							|| (question.answer === 2 && option === 'c')//
							|| (question.answer === 3 && option === 'd')) {
						// if (selectedOption) {
						// return nebib.core.option_status.RIGHT_SELECTED;
						// } else if (question.selectedOption) {
						// return nebib.core.option_status.RIGHT_NOT_SELECTED
						// }
						if (scope.a.showAnswer && !question.showAnswer && !scope.a.showActions) {
							return nebib.core.option_status.RIGHT_HOST;
						}
						return nebib.core.option_status.RIGHT;
					} else if (selectedOption) {
						return nebib.core.option_status.WRONG_SELECTED;
						// } else if (question.selectedOption) {
						// return nebib.core.option_status.WRONG_NOT_SELECTED;
					}
					return nebib.core.option_status.WRONG;
				} else if (selectedOption) {
					return nebib.core.option_status.SELECTED;
					// } else if (question.selectedOption) {
					// return nebib.core.option_status.NOT_SELECTED;
				}
				return nebib.core.option_status.NONE;
			};

			scope.getOptionClasses = function(option) {
				// {'option-correct':((a.showAnswer||q.showAnswer) && 0 == q.answer), 'option-wrong':((a.showAnswer||q.showAnswer)
				// && 0 !=
				// q.answer), 'option-selected': q.selectedOption == 'a', 'option-not-selected': (q.selectedOption != undefined &&
				// q.selectedOption != 'a')}
				var question = scope.q;
				var showAnswer = scope.a.showAnswer || question.showAnswer;
				var selectedOption = question.selectedOption === option;
				var aClasses = [];

				if (showAnswer) {
					if ((question.answer === 0 && option === 'a')//
							|| (question.answer === 1 && option === 'b')//
							|| (question.answer === 2 && option === 'c')//
							|| (question.answer === 3 && option === 'd')) {
						aClasses.push("option-correct");
					} else {
						aClasses.push("option-wrong");
					}
				}
				if (selectedOption) {
					aClasses.push("option-selected");
				} else if (question.selectedOption !== undefined) {
					aClasses.push("option-not-selected");
				}
				if (question.dual) {
					if (question.dual.indexOf(option) !== -1) {
						aClasses.push("dual-option-selected");
					} else {
						aClasses.push("dual-option-not-selected");
					}
				}
				return aClasses.join(" ");
			};
		}
	}
} ]);