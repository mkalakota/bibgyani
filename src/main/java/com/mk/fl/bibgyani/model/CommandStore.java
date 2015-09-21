/**
 *
 */
package com.mk.fl.bibgyani.model;

/**
 * @author Mouli kalakota
 */
public enum CommandStore {

	NEXT_QUESTION("next_question"), //
	USE_LIFELINE("use_lifeline"), //
	USE_LIFELINE_DUAL("use_lifeline_dual"), //
	FINISH_GAME("finish_game"), //
	NEW_GAME("new_game"), //
	SHOW_ANSWER("show_answer");

	private String name;

	private CommandStore(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
