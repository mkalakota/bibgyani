/**
 * @author Mouli Kalakota
 */
nebib.commands = {
	last_at : null
};

nebib.commands.sorter = function(c1, c2) {
	return c1.time - c2.time;
};

nebib.commands.handle = function(aCommands) {
	var i, iStartFrom, receiver;
	if (!(aCommands instanceof Array) || aCommands.length === 0) {
		return;
	}
	// sort
	aCommands.sort(nebib.commands.sorter);
	// start from new game or finish game or from 0
	for (i = aCommands.length - 1; i >= 0; i--) {
		if ("new_game" === aCommands[i].name || "finish_game" === aCommands[i].name || i === 0) {
			break;
		}
	}

	i = nebib.commands.preprocess(aCommands, i);

	if ("next_question" !== aCommands[i].name) {
		nebib.commands.execute(aCommands, i);
	} else {
		nebib.commands.last_at = aCommands[i].time;

		// un-flip
		$("#qs-container").removeClass("q-flip");
		nebib.commands.next_question.receive(aCommands[i], function() {
			// show extra amount indicator
			var at = getScope().currentQuestion.amountExtraAt;
			nebib.timer.indicateAt(at);
			// option - style
			$(".option-wrapper").removeClass("no-transform");
			// amount
			$(".q-amount-content").css("transform", "");

			playAudio();

			setTimeout(function() {
				nebib.commands.execute(aCommands, ++i);
			}, 0);
		});
	}
};

nebib.commands.preprocess = function(aCommands, iStartIndex) {
	var i = iStartIndex || 0, iNextIndex = i, scope, sequence, value, score;
	if (aCommands.length === 0) {
		return i;
	}
	scope = getScope();
	for (; i < aCommands.length; i++) {
		sequence = 0;
		switch (aCommands[i].name) {
		case nebib.core.commands.TOGGLE_BG:
			$(".bg-img").toggleClass("bg-img-hide");
			break;
		case nebib.core.commands.NEXT_QUESTION:
			iNextIndex = i;
			sequence = nebib.commands._getSequence(aCommands[i]) - 1;
			if (!sequence) {// if sequence is 0
				break;
			}
		case nebib.core.commands.SHOW_ANSWER:
			value = aCommands[i].value;
			sequence = sequence || nebib.commands._getSequence(aCommands[i]);
			score = scope.amountsGained[15 - sequence];
			score.answered = true;
			score.amount = nebib.core.amounts[sequence - 1];
			if (sequence < 11 && (value === "true" || value === true)) {
				score.amount += score.amount * .03;
			}
			break;
		}
	}
	return iNextIndex;
};

nebib.commands.execute = function(aCommands, iStartIndex) {
	var i = iStartIndex || 0, receiver;
	for (; i < aCommands.length; i++) {
		nebib.commands.last_at = aCommands[i].time;

		receiver = null;
		switch (aCommands[i].name) {
		case nebib.core.commands.USE_LIFELINE_DUAL:
			receiver = nebib.commands.use_lifeline;
			break;
		case nebib.core.commands.START_TIMER:
			if (!nebib.timer.timerId) {
				nebib.timer.start();
			}
			break;
		case nebib.core.commands.PAUSE_TIMER:
			nebib.timer.pause();
			break;
		case nebib.core.commands.RESUME_TIMER:
			if (!nebib.timer.timerId) {
				nebib.timer.resume();
			}
			break;
		case nebib.core.commands.TOGGLE_BG:
			$(".bg-img").toggleClass("bg-img-hide");
			break;
		case nebib.core.commands.FINISH_GAME:
			if (getScope().currentQuestion) {
				$("#finish-game-modal").modal();
			}
			break;
		case nebib.core.commands.FLIP_QUESTION:
			$("#qs-container").toggleClass("q-flip");
			break;
		}
		receiver = receiver || nebib.commands[aCommands[i].name];
		receiver && receiver.receive(aCommands[i]);
	}
};

nebib.commands._isValidCommand = function(oCommand) {
	var oGame;
	if (!oCommand) {
		return false;
	}
	oGame = oCommand.game;
	if (!oGame) {
		return false;
	}
	return true;
};

nebib.commands._getSequence = function(oCommand) {
	if (!this._isValidCommand(oCommand)) {
		return 0;
	}
	return oCommand.game.currentQuestionSequence || 0;
};

nebib.commands.next_question = {
	receive : function(oCommand, fnSuccess) {
		var questionSequence, scope, game;
		if (!nebib.commands._isValidCommand(oCommand)) {
			return;
		}
		questionSequence = nebib.commands._getSequence(oCommand);
		scope = getScope();
		if (questionSequence < 6) {
			nebib.timer.setTotal(60);
		} else {
			nebib.timer.setTotal(180);
		}
		// reset timer
		nebib.timer.reset();
		game = oCommand.game;
		// load question
		scope.$apply(function() {
			var i, score, challengedAt;
			if (questionSequence && !isNaN(questionSequence)) {
				for (i = 0; i < questionSequence - 1; i++) {
					score = scope.amountsGained[14 - i];
					if (i < game.challengedAt) {
						score.amount = 0;
					}
					if (!score.answered) {
						score.amount = nebib.core.amounts[i];
					}
					score.answered = true;
				}
			}
			if (game.currentQuestionAnswered) {
				score = scope.amountsGained[15 - questionSequence];
				score.answered = true;
				score.amount = nebib.core.amounts[questionSequence - 1];
			}

			scope.lifelines.grace = game.lifelineGraceUsed;
			scope.lifelines.challenge = game.lifelineChallengeUsed;
			scope.lifelines.dual = game.lifelineDualUsed;
			scope.lifeline_used = false;

			scope.loadQuestion(game.currentQuestionId, fnSuccess);
		});

	}
};

nebib.commands.select_option = {
	receive : function(oCommand) {
		var scope, questionSequence;
		if (!nebib.commands._isValidCommand(oCommand) || !oCommand.value) {
			return;
		}
		questionSequence = nebib.commands._getSequence(oCommand);
		if (!questionSequence) {
			return;
		}
		scope = getScope();
		if (scope.currentQuestion && scope.currentQuestion.sequence === questionSequence) {
			// pause timer
			nebib.timer.pause();
			scope.$apply(function() {
				scope.currentQuestion.selectedOption = oCommand.value;
			});
		}
	}
};

nebib.commands.show_answer = {
	receive : execute = function(oCommand) {
		var scope, questionSequence, iPassedTime;
		if (!nebib.commands._isValidCommand(oCommand)) {
			return;
		}
		questionSequence = nebib.commands._getSequence(oCommand);
		if (!questionSequence) {
			return;
		}
		scope = getScope();
		if (scope.currentQuestion && scope.currentQuestion.sequence === questionSequence) {
			scope.$apply(function() {
				var q;
				scope.currentQuestion.showAnswer = true;
				q = scope.currentQuestion;
				if ((q.answer == 0 && (q.selectedOption == 'a' || (q.dual && q.dual.indexOf("a") !== -1)))//
						|| (q.answer == 1 && (q.selectedOption == 'b' || (q.dual && q.dual.indexOf("b") !== -1)))//
						|| (q.answer == 2 && (q.selectedOption == 'c' || (q.dual && q.dual.indexOf("c") !== -1)))//
						|| (q.answer == 3 && (q.selectedOption == 'd' || (q.dual && q.dual.indexOf("d") !== -1)))) {
					scope.amountsGained[15 - questionSequence].answered = true;
					scope.amountsGained[15 - questionSequence].amount = nebib.core.amounts[questionSequence - 1];
					iPassedTime = 60 - nebib.timer.getRemainingTime();
					if (iPassedTime < scope.currentQuestion.amountExtraAt && !scope.lifeline_used
							&& scope.currentQuestion.sequence < 11) {
						$(".extraAmount").addClass("extraAmountAnswered");
						scope.amountsGained[15 - questionSequence].amount += scope.currentQuestion.amountExtra;
					}
					// drop amount
					setTimeout(function() {
						var shOffset = $(".score-highlight").offset();
						var daOffset;
						if ($(".qs-container.q-flip").length) {
							daOffset = $(".q-back .drop-amount").offset();
						} else {
							daOffset = $(".q-front .drop-amount").offset();
						}
						$(".drop-amount").css(
								"transform",
								"translate(" + (shOffset.left - daOffset.left + 95) + "px, " + (shOffset.top - daOffset.top + 10)
										+ "px)")
					}, 50);
				} else {
					scope.amountsGained[15 - questionSequence].answered = true;
					scope.amountsGained[15 - questionSequence].amount = 0;
					$(".option-wrapper").addClass("no-transform");
				}
			});
		}
	}
};

/**
 * Life-lines
 */
nebib.commands.use_lifeline = {
	receive : function(oCommand) {
		var scope, challengedQuestionSequence, i, lifeLineType;
		if (!nebib.commands._isValidCommand(oCommand)) {
			return;
		}
		scope = getScope();
		if (oCommand.name === "use_lifeline_dual") {
			lifeLineType = "dual";
		} else {
			lifeLineType = oCommand.value;
		}
		nebib.timer.pause();
		nebib.timer.indicateAt(0);
		if (lifeLineType === "challenge") {
			challengedQuestionSequence = nebib.commands._getSequence(oCommand);
			scope.$apply(function() {
				if (challengedQuestionSequence) {
					for (i = 0; i < challengedQuestionSequence; i++) {
						scope.amountsGained[14 - i].amount = 0;
					}
					scope.amountsGained[15 - challengedQuestionSequence].answered = true;
				}
				scope.lifelines.challenge = true;
				scope.lifeline_used = true;
			});
		} else if (lifeLineType === "grace") {
			nebib.timer.addTime(30);
			scope.$apply(function() {
				scope.lifelines.grace = true;
				scope.lifeline_used = true;
			});
		} else if (lifeLineType === "dual") {
			nebib.timer.pause();
			scope.$apply(function() {
				scope.lifelines.dual = true;
				scope.lifeline_used = true;
				scope.currentQuestion.dual = oCommand.value.split(",");
			});
		}
	}
};

/**
 * New Game
 */
nebib.commands.new_game = {
	receive : function(oCommand) {
		var scope = getScope();

		// set current question to null and reset amounts to zero;
		scope.$apply(function() {
			scope.currentQuestion = null;
			scope.startGame(); // resets amounts to zero
		});

		// reset timer
		nebib.timer.reset();

		// remove highlights
		$(".pc-bar").removeClass("pc-bar-highlight");
		$(".pc-bar").removeClass("pc-bar-scale");
		$(".score-wrapper").removeClass("score-highlight");
		$(".option-selected").removeClass("option-selected");

		// animations
		$(".score-wrapper").addClass("score-opaque");
		$(".ll-container").addClass("ll-animate invisible");
		nebib.handleStartAnimations();
	}
};
