package com.kklop.angmengine.game.map;

import android.graphics.Canvas;

import com.kklop.angmengine.game.sprite.bound.rect.RectBound;

/**
 * 
 * @author Kevin Klopfenstein
 *
 */
public interface Map {
	
	/**
	 * Draw map to canvas
	 * @param canvas
	 */
	public void draw(Canvas canvas, RectBound bound);
}
