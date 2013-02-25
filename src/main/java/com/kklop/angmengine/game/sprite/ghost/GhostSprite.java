package com.kklop.angmengine.game.sprite.ghost;
	
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.kklop.angmengine.game.sprite.AnimatedSprite;

public class GhostSprite extends AnimatedSprite {

	private static final String TAG = GhostSprite.class.getSimpleName();
	
	private int maxHover;
	private int hoverSpeed;
	private int hoverProgress = 0;
	
	private long hoverTicker;
	private int hoverPeriod;
	
	
	private enum HOVER_STATE { ASCENDING, DESCENDING }
	
	//private HOVER_STATE state;
	
	public GhostSprite(Context context, Bitmap bitmap, float x, float y, 
			int width, int height,
			int fps, int frameCount, int maxHover, int hoverSpeed, int moveFps, String type) {
		super(context, bitmap, x, y, width, height, fps, frameCount, moveFps, type);
		this.maxHover = maxHover;
		this.hoverSpeed = hoverSpeed;
		this.hoverTicker = 0l;
		this.hoverPeriod = 1000/hoverSpeed;
		//state = HOVER_STATE.DESCENDING;
	}
	
	public void update(long gameTime, float targetX, float targetY,
			int speed, boolean center) {
		super.update(gameTime);
		if (gameTime > hoverTicker + hoverPeriod) { 
			hoverTicker = gameTime;
			// ghost needs to hover, so increment hover algorithm
			//updateHover();
		}
		
		/*switch(state) {
			case FREEZE_X:
				this.moveX(gameTime, targetX, targetY, speed);
				break;
			case FREEZE_Y:
				this.moveY(gameTime, targetX, targetY, speed);
				break;
			case FREEZE_BOTH:
				this.move(gameTime, targetX, targetY, speed);
				break;
			default:
				this.moveCenter();
				break;
		}*/
		if(targetX != -1 && targetY != -1) {
			this.move(gameTime, targetX, targetY, speed, center);
		}
		
	}
	
	/*private void updateHover() {
		switch(state) {
		case ASCENDING:
			hoverAscend();
			break;
		case DESCENDING:
			hoverDescend();
			break;
		}
		
	}*/

	/*private void hoverAscend() {
		if(hoverProgress <= 0) {
			state = HOVER_STATE.DESCENDING;
			Log.i(TAG, "Setting hover state to DESCENDING.");
		}
		else {
			y--;
			hoverProgress--;
		}
        
	}
	
	private void hoverDescend() {
		if(hoverProgress >= maxHover) {
			state = HOVER_STATE.ASCENDING;
			Log.i(TAG, "Setting hover state to ASCENDING.");
		}
		else {
			y++;
			hoverProgress++;
		}
	}*/

}
