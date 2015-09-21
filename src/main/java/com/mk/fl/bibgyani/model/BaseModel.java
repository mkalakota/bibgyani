/**
 * 
 */
package com.mk.fl.bibgyani.model;

/**
 * @author Mouli Kalakota
 */
abstract class BaseModel implements IModel {
	
	private static final long serialVersionUID = 1L;
	protected int id;

	/* (non-Javadoc)
	 * @see com.mk.fl.nebib.model.IModel#getId()
	 */
	public int getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see com.mk.fl.nebib.model.IModel#setId(int)
	 */
	public void setId(int id) {
		this.id = id;
	}

}
