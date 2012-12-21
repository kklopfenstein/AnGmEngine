package com.kklop.angmengine.game.grid.exception;

import com.kklop.angmengine.game.exception.GameException;

public class GridException extends GameException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8606541898162474547L;

	public GridException(Throwable e) {
		super(e);
	}
	
	public GridException(String msg) {
		super(msg);
	}
	
}
