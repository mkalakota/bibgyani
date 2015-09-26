/**
 * @author Mouli Kalakota
 */
nebib.Timer = function() {
};

nebib.Timer.prototype.setup = function() {
	var timer, pcw, timerWrapper, height, half;
	pcw = $("#pc-container").width();
	width = height = pcw;
	half = pcw / 2;
	timerWrapper = $(".timer-wrapper");
	timerWrapper.css({
		// "width" : width + "px",
		"height" : height + "px",
		"line-height" : height + "px"
	// "top" : "-" + height + "px"
	});
	timer = $("#svg-timer-group");
	timer.empty();

	timer.attr("transform", "translate(" + half + "," + half + ") scale(.9)");

	var timerBGOuter = document.createElementNS("http://www.w3.org/2000/svg", "circle");
	timerBGOuter.setAttribute("cx", 0);
	timerBGOuter.setAttribute("cy", 0);
	timerBGOuter.setAttribute("r", half);
	timerBGOuter.setAttribute("class", "timerBG timerBGOuter");
	$(timer).append(timerBGOuter);

	var timerBGInner = document.createElementNS("http://www.w3.org/2000/svg", "circle");
	timerBGInner.setAttribute("cx", 0);
	timerBGInner.setAttribute("cy", 0);
	timerBGInner.setAttribute("r", half - 24);
	timerBGInner.setAttribute("class", "timerBG timerBGInner");
	$(timer).append(timerBGInner);

	this._defaultPathValue = "M 0 -" + half + " A " + half + " " + half + " 1 0 1 0 -" + half;

	var timerPointer = document.createElementNS("http://www.w3.org/2000/svg", "path");
	timerPointer.setAttribute("d", this._defaultPathValue);
	timerPointer.setAttribute("class", "timerLoader timerPointer");
	$(timer).append(timerPointer);

	var extraIndicatorBG = document.createElementNS("http://www.w3.org/2000/svg", "path");
	extraIndicatorBG.setAttribute("d", this._defaultPathValue);
	extraIndicatorBG.setAttribute("class", "timerLoader extraIndicatorBG");
	$(timer).append(extraIndicatorBG);

	var extraIndicator = document.createElementNS("http://www.w3.org/2000/svg", "path");
	extraIndicator.setAttribute("d", this._defaultPathValue);
	extraIndicator.setAttribute("class", "timerLoader extraIndicator");
	$(timer).append(extraIndicator);

	$(window).on("resize", this.setup);
};

nebib.Timer.prototype.start = function(doNotReset) {
	doNotReset || this.reset();
	this.timerId = setInterval($.proxy(function() {
		var count, r, x, y, half, mid, timeLapse;
		var itAngle, itX, itY, itR, itMid;
		half = $("#pc-container").width() / 2;
		this.angle += 360 / this.divider;
		r = this.angle * Math.PI / 180; // radians
		x = Math.sin(r) * half;
		y = Math.cos(r) * -half;
		mid = this.angle > 180 ? 1 : 0;
		count = $(".countdown>span").text();
		count = parseInt(count, 10);
		$(".extraIndicator").attr("d", this._defaultPathValue);
		if (count === 0) {
			this.pause();
			$("#time-out-modal").modal();
			return;
		}
		$(".timerPointer").attr("d", "M 0 -" + half + " A " + half + " " + half + " 1 " + mid + " 1 " + x + " " + y);
		if (this.indicatedTime) {
			timeLapse = this.divider - count;
			if (timeLapse === 3) {
				$(".countdown").addClass("countdownDisappear");
				$(".extraAmount").addClass("extraAmountAppear");
			} else if (timeLapse === this.indicatedTime) {
				$(".countdown").removeClass("countdownDisappear");
				$(".extraAmount").removeClass("extraAmountAppear");

				$(".countdown").addClass("countdownAppear");
				$(".extraAmount").addClass("extraAmountDisppear");
			}
			itAngle = this.indicatedTime * 360 / this.divider;
			itR = itAngle * Math.PI / 180;
			itX = Math.sin(itR) * half;
			itY = Math.cos(itR) * -half;

			itMid = itAngle > 180 ? 1 : 0;

			if (timeLapse < this.indicatedTime) {
				$(".extraIndicator").attr("d", "M 0 -" + half + " A " + half + " " + half + " 1 " + mid + " 1 " + x + " " + y);
			} else {
				$(".extraIndicator").attr("d",
						"M 0 -" + half + " A " + half + " " + half + " 1 " + itMid + " 1 " + itX + " " + itY);
			}
			$(".extraIndicatorBG")
					.attr("d", "M 0 -" + half + " A " + half + " " + half + " 1 " + itMid + " 1 " + itX + " " + itY);
		}
		$(".countdown>span").text(count - 1);
	}, this), 1000);
};

nebib.Timer.prototype.pause = function() {
	if (typeof this.timerId === "number") {
		clearInterval(this.timerId);
		clearTimeout(this.timerId);
		this.timerId = null;
	}
};

nebib.Timer.prototype.resume = function() {
	this.start(true);
};

nebib.Timer.prototype.reset = function() {
	var half = $("#pc-container").width() / 2, strokedash;
	this.pause();
	this.angle = 0;
	this.divider = this.total || 60;
	$(".countdown>span").text(this.divider);
	$(".extraAmount").removeClass("extraAmountAppear extraAmountDisappear extraAmountAnswered");
	$(".countdown").removeClass("countdownAppear countdownDisappear");
	$(".timerPointer").attr("d", this._defaultPathValue);
	$(".extraIndicator").attr("d", this._defaultPathValue);
	$(".extraIndicatorBG").attr("d", this._defaultPathValue);

	strokedash = Math.PI * half / this.divider;

	$(".timerLoader").css("stroke-dasharray", strokedash + ", " + strokedash);
};

nebib.Timer.prototype.addTime = function(timeInSec) {
	var count = $(".countdown>span").text(), newTime;
	count = parseInt(count, 10);
	if (typeof timeInSec !== "number" && timeInSec < 1) {
		return;
	}
	this.reset(true);
	newTime = count + timeInSec;
	$(".countdown>span").text(newTime);
	this.divider = newTime;
	this.start(true);
};

nebib.Timer.prototype.getRemainingTime = function() {
	var count = $(".countdown>span").text();
	count = parseInt(count, 10);
	return count;
};

nebib.Timer.prototype.indicateAt = function(iAt) {
	this.indicatedTime = iAt > 0 ? iAt - 1 : 0;
	// this.reset();
	// $(".extraAmount").removeClass("extraAmountAppear extraAmountDisappear extraAmountAnswered");
	// $(".countdown").removeClass("countdownAppear countdownDisappear");
};

nebib.Timer.prototype.setTotal = function(newTotal) {
	this.total = newTotal;
};
