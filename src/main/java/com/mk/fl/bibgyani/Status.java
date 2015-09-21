/**
 *
 */
package com.mk.fl.bibgyani;

/**
 * @author Mouli Kalakota
 */
final class Status {

	private static boolean finishGame = false;
	private static long lastCommandAt = 0L;

	synchronized static void setLastCommandAt(long at) {
		lastCommandAt = at;
	}

	static long getLastCommandAt() {
		return lastCommandAt;
	}

	synchronized static void setFinishGame(boolean state) {
		finishGame = state;
	}

	static boolean isFinishGame() {
		return finishGame;
	}

	private Status() {
	}

}
