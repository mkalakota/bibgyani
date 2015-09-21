/**
 *
 */
package com.mk.fl.bibgyani.model;

/**
 * @author Mouli Kalakota
 */
public enum Lifeline {

	GRACE("grace"), CHALLENGE("challenge"), DUAL("dual");

	private String name;

	private Lifeline(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
