/**
 *
 */
package com.mk.fl.bibgyani.model;

/**
 * @author Mouli Kalakota
 */
public class Question extends BaseModel {

	private static final long serialVersionUID = 1L;

	public static final String QUERY_RANDOM_QUESTIONS_WITH_LEVEL = "select q from Question q where q.level = :level ORDER BY RAND()";
	public static final String QUERY_RANDOM_QUESTIONS_WITH_LEVEL_NOT_WITH_ID = "select q from Question q where q.level = :level AND q.id != :qId ORDER BY RAND()";
	public static final String QUERY_DELETE_QUESTION_WITH_ID = "delete from Question q where q.id = :qId";
	public static final String QUERY_SELECT_ALL_QUESTIONS = "select q from Question q";
	public static final String QUERY_DELETE_ALL_QUESTIONS = "delete from Question q";

	public static final int EASY = 0;
	public static final int MEDIUM = 1;
	public static final int DIFFICULT = 2;

	public static final int ANSWER_A = 0;
	public static final int ANSWER_B = 1;
	public static final int ANSWER_C = 2;
	public static final int ANSWER_D = 3;

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

	public Question() {
	}

	public Question(String name, String optionA, String optionB, String optionC, String optionD, int answer, int level,
			long amount) {
		this(name, optionA, optionB, optionC, optionD, name, optionA, optionB, optionC, optionD, answer, level, (short) 0);
	}

	public Question(String name, String optionA, String optionB, String optionC, String optionD, String nameTelugu,
			String optionATelugu, String optionBTelugu, String optionCTelugu, String optionDTelugu, int answer, int level,
			short amountExtraAt) {
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

}
