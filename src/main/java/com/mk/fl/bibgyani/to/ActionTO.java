/**
 *
 */
package com.mk.fl.bibgyani.to;

/**
 * @author Mouli Kalakota
 */
public class ActionTO {

	private boolean showAnswer = false;
	private boolean showActions = false;

	public boolean canShowAnswer() {
		return showAnswer;
	}

	public void setShowAnswer(boolean showAnswer) {
		this.showAnswer = showAnswer;
	}

	public boolean canShowActions() {
		return showActions;
	}

	public void setShowActions(boolean showActions) {
		this.showActions = showActions;
	}

}
