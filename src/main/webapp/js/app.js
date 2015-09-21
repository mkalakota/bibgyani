var app = angular.module("NeBibApp", [ 'flow' ]);
app.filter('range', function() {
	return function(input, total) {
		total = parseInt(total);
		for (var i = 0; i < total; i++) {
			input.push(i);
		}
		return input;
	};
});
/**
 * Custom functions
 */
var nebib = {};
nebib.poll = function() {
	$.ajax({
		url : "command",
		method : "GET",
		dataType : 'json',
		data : {
			receivedTime : nebib.commands.last_at
		},
		async : true
	}).done(nebib.commands.handle).fail(nebib.serverConnFailed);
};

nebib.serverConnFailed = function() {
	console.log("Unavailable to connect to server");
};

nebib.dualDialogDismiss = function() {
	var i, options, text;
	var checkedOptions = $("#dual-life-line-modal .checkbox input:checked");
	if (!checkedOptions || checkedOptions.length !== 2) {
		return;
	}
	options = [];
	for (i = 0; i < checkedOptions.length; i++) {
		text = $(checkedOptions[i]).parent().text().trim();
		switch (text) {
		case "Option A":
			options.push("a");
			break;
		case "Option B":
			options.push("b");
			break;
		case "Option C":
			options.push("c");
			break;
		case "Option D":
			options.push("d");
			break;
		}
		$(checkedOptions[i]).attr("checked", false);
	}

	getScope().sendCommand("use_lifeline_dual", options.join(","));
};

nebib.handleStartAnimations = function() {
	$(".score-wrapper.score-opaque").on("animationend", function() {
		$(this).removeClass("score-opaque");
	});
	$(".ll-container.ll-animate").on("animationend", function() {
		$(this).removeClass("ll-animate");
	});
	setTimeout(function() {
		$(".ll-container.invisible").removeClass("invisible");
	}, 3500);
};

nebib.start = function(oAction) {
	var scope = getScope();
	scope.$apply(function() {
		scope.action = oAction;
		scope.startGame();
	});
	setTimeout(function() {
		nebib.pollId = setInterval(nebib.poll, 1000);
	}, 1600);

	nebib.handleStartAnimations();

	/**
	 * Setup timer
	 */
	nebib.timer = new nebib.Timer();
	nebib.timer.setup();
};

function getScope() {
	return angular.element($("#main-container")).scope();
}

function playAudio() {
	var audio = $("audio")[0];
	if (audio.paused !== true) {
		audio.pause();
		audio.play();
	} else {
		audio.play();
	}
}

$(document).ready(function() {
	$.ajax({
		url : "actions",
		methid : "GET",
		dataType : "json",
		async : true
	}).done(nebib.start);
});
