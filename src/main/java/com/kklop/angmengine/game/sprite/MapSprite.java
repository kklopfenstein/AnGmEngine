package com.kklop.angmengine.game.sprite;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

public class MapSprite extends StaticSprite {
	
	private static final String TAG = MapSprite.class.getSimpleName();
	
	private int screenHeight;
	private int screenWidth;
	
	public static enum MAP_STATE { MOVEMENT, FREEZE_X, FREEZE_Y, FREEZE_BOTH, STOPPED }
	
	private MAP_STATE state;
	
	public MapSprite(Context context, Bitmap bitmap, int x, int y, int fps, 
			int screenHeight, int screenWidth, String type) {
		super(context, bitmap, x, y, fps, type);
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		state = MAP_STATE.STOPPED;
	}

	public void update(Long gameTime, float targetX, float targetY, float speed) {
		
		double delta_x = (double) (this.dWidth-targetX);
		double delta_y = (double) (this.dHeight-targetY);
		
		double angle = Math.atan2(delta_y, delta_x);
		Log.i(TAG, "Angle is " + angle + " target is (" + targetX + ", " + targetY + ")");
		if (gameTime > frameTicker + framePeriod) {
			frameTicker = gameTime;
			float difX = speed*(float)Math.cos(angle);
			float difY = speed*(float)Math.sin(angle);
			
			x += difX;
			y += difY;
			
			// determine if map is now offscreen
			int bHeight = bitmap.getHeight();
			int bWidth = bitmap.getWidth();
			
			boolean freezeX = false;
			boolean freezeY = false;
			
			if((x+bWidth) < screenWidth) {
				x = screenWidth-bWidth;
				freezeX = true;
			}
			else if(x > 0) {
				x = 0;
				freezeX = true;
			}
			
			if((y+bHeight < screenHeight)) {
				y = screenHeight-bHeight;
				freezeY = true;
			}
			else if(y > 0) {
				y = 0;
				freezeY = true;
			}
			
			if(freezeY && !freezeX) {
				state = MAP_STATE.FREEZE_Y;
			}
			else if (!freezeY && freezeX) {
				state = MAP_STATE.FREEZE_X;
			}
			else if (freezeY && freezeX) {
				state = MAP_STATE.FREEZE_BOTH;
			}
			else {
				state = MAP_STATE.MOVEMENT;
			}
			
		}
	}


	
}
