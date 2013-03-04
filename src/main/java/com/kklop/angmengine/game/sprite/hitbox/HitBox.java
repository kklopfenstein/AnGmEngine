package com.kklop.angmengine.game.sprite.hitbox;

import com.kklop.angmengine.game.sprite.Sprite;

/**
 * Represents a hitbox for a sprite
 * @author Kevin Klopfenstein
 *
 */
public class HitBox {
	private float x;
	private float y;
	private float xMax;
	private float yMax;
	
	public HitBox(float x, float y, float xMax, float yMax) {
		super();
		this.x = x;
		this.y = y;
		this.xMax = xMax;
		this.yMax = yMax;
	}
	
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getxMax() {
		return xMax;
	}
	public void setxMax(float xMax) {
		this.xMax = xMax;
	}
	public float getyMax() {
		return yMax;
	}
	public void setyMax(float yMax) {
		this.yMax = yMax;
	}
	
	// getters based on sprite position
	public float getX(Sprite s) {
		return this.x + s.getX();
	}
	public float getY(Sprite s) {
		return this.y + s.getY();
	}
	public float getxMax(Sprite s) {
		return xMax + s.getX();
	}
	public float getyMax(Sprite s) {
		return yMax + s.getY();
	}
	
}
