package com.kklop.angmengine.game.event;

import android.graphics.Canvas;

/**
 * 
 * @author Kevin Klopfenstein
 *
 */
public interface GameEvent {
	/**
	 * 
	 * @param gameTime
	 */
	public void continueEvent(long gameTime);
	
	/**
	 * 
	 * @return
	 */
	public boolean isActive();
	
	/**
	 * 
	 */
	public void startEvent();
	
	/**
	 * 
	 * @param canvas
	 */
	public void drawEvent(Canvas canvas);
	
	/**
	 * 
	 */
	public void stopEvent();
}
