/**
 * @author Mouli Kalakota
 */
app.controller('NebibController', [ '$scope', 'questions', 'commands', function($scope, questions, commands) {
	$scope.action = null;
	/**
	 * Questions
	 */
	$scope.loadQuestion = function(id, fnSuccess) {
		$scope.custAnim = false;
		setTimeout(function() {
			questions.get(id).success(function(question) {
				question.amount = nebib.core.amounts[question.sequence - 1];
				question.amountExtra = question.amount * .03;
				$scope.currentQuestion = question;
				$scope.custAnim = true;
				if (fnSuccess) {
					fnSuccess();
				}
			});
		}, 10);
	};

	/**
	 * Operator actions
	 */
	$scope.sendCommand = function(name, value) {
		commands.post({
			"name" : name,
			"value" : value,
		}).success(function() {
			console.log("Sent command successfully: " + name);
		});
	};

	/**
	 * Amounts
	 */
	$scope.amountsGained = [];
	$scope.startGame = function() {
		var i;
		$scope.amountsGained.length = 0;
		for (i = 14; i >= 0; i--) {
			$scope.amountsGained.push({
				amount : 0,
				answered : false,
				sequence : i + 1
			});
		}
		$scope.lifelines = {
			grace : false,
			challenge : false,
			dual : false,
			tbd : false
		}
	};
	$scope.getTotalAmount = function() {
		var i, sum = 0;
		for (i = 0; i < $scope.amountsGained.length; i++) {
			if ($scope.amountsGained[i].answered) {
				sum += $scope.amountsGained[i].amount;
			}
		}
		return sum;
	};

} ]);