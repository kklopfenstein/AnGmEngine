package com.kklop.angmengine.game.sprite;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

public abstract class Sprite {
	
	public static final String TAG = Sprite.class.getSimpleName();
	
	protected Context context;
	protected Bitmap bitmap;
	
	protected float x;
	protected float y;
	
	protected long frameTicker;	// the time of the last frame update
	protected int framePeriod;	// milliseconds between each frame (1000/fps)
	
	int dWidth;
	int dHeight;
	
	public enum SPRITE_STATE { MOVING, STOPPED }
	protected SPRITE_STATE state;
	
	protected double startAngle;
	protected float targetX;
	protected float targetY;
	
	/* used by grid for identification
	 * should not be manually set */
	protected Integer gridId = null;
	
	public void draw(Canvas canvas) {
		Log.i(TAG, "Draw not implemented for Sprite");
	}
	
	public Sprite(Context context, Bitmap bitmap, float x, float y, int fps) {
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
		this.framePeriod = 1000/fps;
		this.frameTicker = 0l;
		this.context = context;
		
		WindowManager wm = (WindowManager) context.
				getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		
		this.dHeight = display.getHeight();
		this.dWidth = display.getWidth();

		// probably should get set by constructor I guess
		targetX = 0;
		targetY = 0;
		state = SPRITE_STATE.STOPPED;
	}
	
	public void update(Context context, Long gameTime, float targetX, 
			float targetY, int speed) {
		this.move(gameTime, targetX, targetY, speed);
	}
	
	protected void move(Long gameTime, float targetX, float targetY, float speed) {
		
		double delta_x = (double) (this.x-targetX);
		double delta_y = (double) (this.y-targetY);
		
		if(!(Math.abs(delta_x) < 1 && Math.abs(delta_y) < 1)) {
		
			state = SPRITE_STATE.MOVING;
			
			double angle = Math.atan2(delta_y, delta_x);
			if(targetX != this.targetX && targetY != this.targetY) {
				// target has changed so store the original angle
				this.startAngle = angle;
				this.targetX = targetX;
				this.targetY = targetY;
			}
			
			if (gameTime > frameTicker + framePeriod) {
				frameTicker = gameTime;
				float difX = speed*(float)Math.cos(angle);
				float difY = speed*(float)Math.sin(angle);
				
				x += -difX;
				y += -difY;
				
			}
		}
		else {
			state = SPRITE_STATE.STOPPED;
		}
	}
	
	/*protected void moveX(Long gameTime, float targetX, float targetY, float speed) {
		double delta_x = (double) (this.x-targetX);
		double delta_y = (double) (this.y-targetY);
		
		double angle = Math.atan2(delta_y, delta_x);
		
		if (gameTime > frameTicker + framePeriod) {
			frameTicker = gameTime;
			float difX = speed*(float)Math.cos(angle);
			
			x -= difX;
			
		}
	}
	
	protected void moveY(Long gameTime, float targetX, float targetY, float speed) {
		double delta_x = (double) (this.x-targetX);
		double delta_y = (double) (this.y-targetY);
		
		double angle = Math.atan2(delta_y, delta_x);
		
		if (gameTime > frameTicker + framePeriod) {
			frameTicker = gameTime;
			float difY = speed*(float)Math.sin(angle);
			
			y -= difY;
		}
	}*/

	/**
	 * Move sprite to center
	 */
	public void moveCenter() {
		x = (dWidth/2 - (bitmap.getWidth()/2));
		y = (dHeight/2 - (bitmap.getHeight()/2));
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
	
	
}
