package com.kklop.angmengine.game.sprite.bound;

import android.graphics.PointF;

/**
 * Bounds for an object
 * @author Kevin Klopfenstein
 *
 */
public abstract class Bound {
	public abstract Float inBoundX(Float x, int spriteWidth);
	
	public abstract Float inBoundY(Float y, int spriteHeight);
	
	public abstract PointF getBoundPoint();

}
