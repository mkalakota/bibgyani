/**
 *
 */
package com.mk.fl.bibgyani.to;

/**
 * @author Mouli Kalakota
 */
public class CommandTO implements Comparable<CommandTO> {

	private String name;
	private String value;
	private long time;
	private GameTO game;

	public CommandTO() {
	}

	public CommandTO(String name, String value, long time, GameTO game) {
		this.name = name;
		this.value = value;
		this.time = time;
		this.game = game;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int compareTo(CommandTO o) {
		return Long.valueOf(time).compareTo(Long.valueOf(o.time));
	}

	public GameTO getGame() {
		return game;
	}

	public void setGame(GameTO game) {
		this.game = game;
	}

}
