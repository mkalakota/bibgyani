/**
 *
 */
package com.mk.fl.bibgyani.model;

import java.util.Date;

/**
 * @author Mouli Kalakota
 */
public class Game extends BaseModel {

	public static final String QUERY_RUNNING_GAMES = "select g from Game g where g.endedAt is null";
	// public static final String SQLQUERY_GET_UNUSED_QUESTION_WITH_LEVEL = "select r.qid from(select q.id as qid, gq.id as gqid
	// from bibgyani_question q left outer join bibgyani_game_questions gq on q.ID = gq.QUESTION_ID) r where r.gqid is null";

	private static final long serialVersionUID = 1L;

	private boolean lifelineGrace;
	private boolean lifelineChallenge;
	private boolean lifelineDual;
	private int challengedAt;

	private Date startedAt;
	private Date endedAt;

	private Question currentQuestion;
	private boolean currentQuestionAnswered;
	private int currentQuestionSequence;

	public boolean isLifelineGrace() {
		return lifelineGrace;
	}

	public void setLifelineGrace(boolean value) {
		lifelineGrace = value;
	}

	public boolean isLifelineDual() {
		return lifelineDual;
	}

	public void setLifelineDual(boolean value) {
		lifelineDual = value;
	}

	public boolean isLifelineChallenge() {
		return lifelineChallenge;
	}

	public void setLifelineChallenge(boolean value) {
		lifelineChallenge = value;
	}

	public Date getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(Date startedAt) {
		this.startedAt = startedAt;
	}

	public Date getEndedAt() {
		return endedAt;
	}

	public void setEndedAt(Date endedAt) {
		this.endedAt = endedAt;
	}

	public Question getCurrentQuestion() {
		return currentQuestion;
	}

	public void setCurrentQuestion(Question currentQuestion) {
		this.currentQuestion = currentQuestion;
	}

	public boolean isCurrentQuestionAnswered() {
		return currentQuestionAnswered;
	}

	public void setCurrentQuestionAnswered(boolean currentQuestionAnswered) {
		this.currentQuestionAnswered = currentQuestionAnswered;
	}

	public int getCurrentQuestionSequence() {
		return currentQuestionSequence;
	}

	public void setCurrentQuestionSequence(int currentQuestionSequence) {
		this.currentQuestionSequence = currentQuestionSequence;
	}

	public int getChallengedAt() {
		return challengedAt;
	}

	public void setChallengedAt(int challengedAt) {
		this.challengedAt = challengedAt;
	}

}
