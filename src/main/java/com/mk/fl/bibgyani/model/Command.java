/**
 *
 */
package com.mk.fl.bibgyani.model;

import java.util.Date;

/**
 * @author Mouli Kalakota
 */
public class Command extends BaseModel {

	private static final long serialVersionUID = 1L;

	public static final String QUERY_DELETE_COMMANDS = "delete from Command";

	private Date time;
	private String name;
	private String value;
	private Game game;

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

}
