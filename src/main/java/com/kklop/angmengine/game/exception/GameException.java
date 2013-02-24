package com.kklop.angmengine.game.exception;

/**
 * 
 * @author Kevin Klopfenstein
 *
 */
public class GameException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6694980273686843216L;

	public GameException(Throwable e) {
		super(e);
	}
	
	public GameException(String msg) {
		super(msg);
	}
}
