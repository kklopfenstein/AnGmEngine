package com.kklop.angmengine.game.sprite;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.util.Log;

import com.kklop.angmengine.game.sprite.bound.Bound;

public abstract class Sprite {
	
	public static final String TAG = Sprite.class.getSimpleName();
	
	protected Bitmap bitmap;
	protected Bitmap normalBitmap;
	protected Bitmap flipBitmap;
	
	String type; // holds the type of the sprite
	
	protected float x;
	protected float y;
	
	protected long frameTicker;	// the time of the last frame update
	protected int framePeriod;	// milliseconds between each frame (1000/fps)

	protected Bound bound; // bound of object on map
	
	public enum SPRITE_STATE { MOVING, STOPPED }
	public enum SPRITE_DIRECTION { EAST, WEST }
	protected SPRITE_STATE state;
	protected SPRITE_DIRECTION direction;
	
	protected double startAngle;
	protected float targetX;
	protected float targetY;
	
	/* used by grid for identification
	 * should not be manually set */
	protected Integer gridId = null;
	
	public void draw(Canvas canvas) {
		Log.i(TAG, "Draw not implemented for Sprite");
	}
	
	public Sprite(Bound bound, Bitmap bitmap, float x, float y, int fps, 
			String type) {
		this.bitmap = bitmap;
		this.normalBitmap = bitmap;
		this.x = x;
		this.y = y;
		this.bound = bound;
		if(bound != null) {
			x = (bound.inBoundX(getX(), getWidth()));
			y = (bound.inBoundY(getY(), getHeight()));
		}
		this.framePeriod = 1000/fps;
		this.frameTicker = 0l;
		// probably should get set by constructor I guess
		targetX = 0;
		targetY = 0;
		state = SPRITE_STATE.STOPPED;
		this.type = type;
		// create the flipped bitmap
		createFlipBitmap();
	}
	
	public void update(Long gameTime, float targetX, 
			float targetY, int speed, boolean center) {
		this.move(gameTime, targetX, targetY, speed, center);
	}
	
	/**
	 * Move the object to certain point over time
	 * at a speed.
	 * @param gameTime
	 * @param targetX
	 * @param targetY
	 * @param speed
	 * @param center
	 */
	protected void move(Long gameTime, float targetX, float targetY, float speed, 
			boolean center) {
		
		if(targetX != -1 && targetY != -1) {
			
			if(center) {
				targetX -= getWidth()/2;
				targetY -= getHeight()/2;
			}
			
			double delta_x = (double) (this.x-targetX);
			double delta_y = (double) (this.y-targetY);
			
			if(!(Math.abs(delta_x) < speed && Math.abs(delta_y) < speed)) {
				
				// flip the bitmap if it's moving in one direction or the other
				if(delta_x < 0) {
					direction = SPRITE_DIRECTION.EAST;
					bitmap = normalBitmap;
				} else {
					direction = SPRITE_DIRECTION.WEST;
					bitmap = flipBitmap;
				}
			
				state = SPRITE_STATE.MOVING;
				
				double angle = Math.atan2(delta_y, delta_x);
				if(targetX != this.targetX && targetY != this.targetY) {
					// target has changed so store the original angle
					this.startAngle = angle;
					this.targetX = targetX;
					this.targetY = targetY;
				}
				
				/* this means that the motion should increment
				 * every cetain number of frames, not every
				 * time the method is called. This is because
				 * this method could be called faster or
				 * slower than the requires FPS.
				 */
				if (gameTime > frameTicker + framePeriod) {
					frameTicker = gameTime;
					float difX = speed*(float)Math.cos(angle);
					float difY = speed*(float)Math.sin(angle);
					
					x += -difX;
					y += -difY;
					
					/* don't move past bounds. if we are
					 * passed the bound, then move axis to
					 * the edge of the bound
					 */
					if(bound != null) {
						x = (bound.inBoundX(getX(), getWidth()));
						y = (bound.inBoundY(getY(), getHeight()));
					}
				}
			}
			else {
				setX(targetX);
				setY(targetY);
			}
		} else {
			state = SPRITE_STATE.STOPPED;
		}
	}
	
	private void createFlipBitmap() {
		Matrix m = new Matrix();
		m.preScale(-1, 1);
		flipBitmap = Bitmap.createBitmap(bitmap, 0, 0, 
				bitmap.getWidth(), bitmap.getHeight(), 
				m, false);
		flipBitmap.setDensity(DisplayMetrics.DENSITY_DEFAULT);
	}
	
	/**
	 * Detect if two sprites are colliding.
	 * @param sprite
	 * @return
	 */
	public boolean collided(Sprite sprite) {
		if(this.getMaxX() < sprite.getX()) return false;
		if(this.getX() > sprite.getMaxX()) return false;
		if(this.getMaxY() < sprite.getY()) return false;
		if(this.getY() > sprite.getMaxY()) return false;
		return true;
	}

	public boolean onSprite(float x, float y) {
		boolean result = false;
		if((x>=this.x) && (x<(this.x+bitmap.getWidth())) &&
				((y>=this.y && (y<(this.y+bitmap.getHeight()))))) {
			result = true;
		}
		return result;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
	public int getWidth() {
		int result = 0;
		if(bitmap != null) {
			result = bitmap.getWidth();
		}
		return result;
	}
	
	public int getHeight() {
		int result = 0;
		if(bitmap != null) {
			result = bitmap.getHeight();
		}
		return result;
	}
	
	public PointF getTopLeftCrnr() {
		return new PointF(getX(), getY());
	}
	
	public PointF getTopRightCrnr() {
		PointF p = null;
		if(bitmap != null) {
			p = new PointF(getX(), getY() + bitmap.getWidth());
		}
		return p;
	}
	
	public PointF getBotLeftCrnr() {
		PointF p = null;
		if(bitmap != null) {
			p = new PointF(getX(), getY()+ bitmap.getHeight());
		}
		return p;
	}
	
	public PointF getBotRightCrnr() {
		PointF p = null;
		if(bitmap != null) {
			p = new PointF(getX() + bitmap.getWidth(), 
					getY() + bitmap.getHeight());
		}
		return p;
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
	
	public float getMaxX() {
		return x + bitmap.getWidth();
	}
	
	public float getMaxY() {
		return y + bitmap.getHeight();
	}
	
	public float getCompY() {
		return y + bitmap.getHeight();
	}

	public Integer getGridId() {
		return gridId;
	}

	public void setGridId(Integer gridId) {
		this.gridId = gridId;
	}

	public SPRITE_STATE getState() {
		return state;
	}

	public void setState(SPRITE_STATE state) {
		this.state = state;
	}
	
	public float getCenterX() {
		return x + bitmap.getWidth()/2;
	}
	
	public float getCenterY() {
		return y + bitmap.getHeight()/2;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
