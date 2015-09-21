/**
 *
 */
package com.mk.fl.bibgyani.to;

import com.mk.fl.bibgyani.model.Question;

/**
 * @author Mouli Kalakota
 */
public class QuestionTO {

	private int id;

	private String name;
	private String nameTelugu;

	private String optionA;
	private String optionATelugu;

	private String optionB;
	private String optionBTelugu;

	private String optionC;
	private String optionCTelugu;

	private String optionD;
	private String optionDTelugu;

	private int answer;

	private int level;

	private short amountExtraAt;

	private int sequence;

	public QuestionTO() {
	}

	public QuestionTO(Question question) {
		id = question.getId();

		name = question.getName();
		optionA = question.getOptionA();
		optionB = question.getOptionB();
		optionC = question.getOptionC();
		optionD = question.getOptionD();

		nameTelugu = question.getNameTelugu();
		optionATelugu = question.getOptionATelugu();
		optionBTelugu = question.getOptionBTelugu();
		optionCTelugu = question.getOptionCTelugu();
		optionDTelugu = question.getOptionDTelugu();

		answer = question.getAnswer();

		level = question.getLevel();

		amountExtraAt = question.getAmountExtraAt();
	}

	public QuestionTO(int id, String name, String optionA, String optionB, String optionC, String optionD, int answer, int level,
			long amount) {
		this(id, name, optionA, optionB, optionC, optionD, name, optionA, optionB, optionC, optionD, answer, level, (short) 0);
	}

	public QuestionTO(int id, String name, String optionA, String optionB, String optionC, String optionD, String nameTelugu,
			String optionATelugu, String optionBTelugu, String optionCTelugu, String optionDTelugu, int answer, int level,
			short amountExtraAt) {
		this.id = id;

		this.name = name;
		this.optionA = optionA;
		this.optionB = optionB;
		this.optionC = optionC;
		this.optionD = optionD;

		this.nameTelugu = nameTelugu;
		this.optionATelugu = optionATelugu;
		this.optionBTelugu = optionBTelugu;
		this.optionCTelugu = optionCTelugu;
		this.optionDTelugu = optionDTelugu;

		this.answer = answer;

		this.level = level;

		this.amountExtraAt = amountExtraAt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameTelugu() {
		return nameTelugu;
	}

	public void setNameTelugu(String nameTelugu) {
		this.nameTelugu = nameTelugu;
	}

	public String getOptionA() {
		return optionA;
	}

	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}

	public String getOptionATelugu() {
		return optionATelugu;
	}

	public void setOptionATelugu(String optionATelugu) {
		this.optionATelugu = optionATelugu;
	}

	public String getOptionB() {
		return optionB;
	}

	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}

	public String getOptionBTelugu() {
		return optionBTelugu;
	}

	public void setOptionBTelugu(String optionBTelugu) {
		this.optionBTelugu = optionBTelugu;
	}

	public String getOptionC() {
		return optionC;
	}

	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}

	public String getOptionCTelugu() {
		return optionCTelugu;
	}

	public void setOptionCTelugu(String optionCTelugu) {
		this.optionCTelugu = optionCTelugu;
	}

	public String getOptionD() {
		return optionD;
	}

	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}

	public String getOptionDTelugu() {
		return optionDTelugu;
	}

	public void setOptionDTelugu(String optionDTelugu) {
		this.optionDTelugu = optionDTelugu;
	}

	public int getAnswer() {
		return answer;
	}

	public void setAnswer(int answer) {
		this.answer = answer;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public short getAmountExtraAt() {
		return amountExtraAt;
	}

	public void setAmountExtraAt(short amountExtraAt) {
		this.amountExtraAt = amountExtraAt;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

}
