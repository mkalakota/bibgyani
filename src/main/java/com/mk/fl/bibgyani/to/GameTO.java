/**
 *
 */
package com.mk.fl.bibgyani.to;

import java.io.Serializable;

import com.mk.fl.bibgyani.model.Game;
import com.mk.fl.bibgyani.model.Question;

/**
 * @author mkalakot
 */
public class GameTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	private boolean lifelineGraceUsed;
	private boolean lifelineChallengeUsed;
	private boolean lifelineDualUsed;
	private int challengedAt;

	private int currentQuestionId;
	private int currentQuestionSequence;
	private boolean currentQuestionAnswered;

	public GameTO() {
	}

	public GameTO(Game game) {
		id = game.getId();

		lifelineGraceUsed = game.isLifelineGrace();
		lifelineChallengeUsed = game.isLifelineChallenge();
		lifelineDualUsed = game.isLifelineDual();
		challengedAt = game.getChallengedAt();

		currentQuestionAnswered = game.isCurrentQuestionAnswered();
		currentQuestionSequence = game.getCurrentQuestionSequence();
		Question question = game.getCurrentQuestion();
		if (null != question) {
			currentQuestionId = question.getId();
		}
	}

	public int getCurrentQuestionId() {
		return currentQuestionId;
	}

	public void setCurrentQuestionId(int currentQuestionId) {
		this.currentQuestionId = currentQuestionId;
	}

	public int getCurrentQuestionSequence() {
		return currentQuestionSequence;
	}

	public void setCurrentQuestionSequence(int currentQuestionSequence) {
		this.currentQuestionSequence = currentQuestionSequence;
	}

	public boolean isCurrentQuestionAnswered() {
		return currentQuestionAnswered;
	}

	public void setCurrentQuestionAnswered(boolean currentQuestionAnswered) {
		this.currentQuestionAnswered = currentQuestionAnswered;
	}

	public boolean isLifelineGraceUsed() {
		return lifelineGraceUsed;
	}

	public void setLifelineGraceUsed(boolean lifelineGraceUsed) {
		this.lifelineGraceUsed = lifelineGraceUsed;
	}

	public boolean isLifelineChallengeUsed() {
		return lifelineChallengeUsed;
	}

	public void setLifelineChallengeUsed(boolean lifelineChallengeUsed) {
		this.lifelineChallengeUsed = lifelineChallengeUsed;
	}

	public boolean isLifelineDualUsed() {
		return lifelineDualUsed;
	}

	public void setLifelineDualUsed(boolean lifelineDualUsed) {
		this.lifelineDualUsed = lifelineDualUsed;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getChallengedAt() {
		return challengedAt;
	}

	public void setChallengedAt(int challengedAt) {
		this.challengedAt = challengedAt;
	}

}
