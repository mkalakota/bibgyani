/**
 * 
 */
package com.mk.fl.bibgyani.model;

import java.io.Serializable;

/**
 * @author Mouli Kalakota
 */
public interface IModel extends Serializable {

	String UNASSIGNED_ID = "-1";
	long UNASSIGNED_ID_LONG = -1L;
	
	int getId();
	
	void setId(int id);

}
