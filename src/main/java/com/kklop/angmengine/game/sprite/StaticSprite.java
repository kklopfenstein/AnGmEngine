package com.kklop.angmengine.game.sprite;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class StaticSprite extends Sprite {
	
	private static final String TAG = StaticSprite.class.getSimpleName();
	
	protected Bitmap bitmap;
	
	protected long frameTicker;	// the time of the last frame update
	protected int framePeriod;	// milliseconds between each frame (1000/fps)
	
	public StaticSprite(Context context, Bitmap bitmap, int x, int y, int fps, String type) {
		super(context, bitmap, x, y, fps, type);
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
		this.framePeriod = 1000/fps;
	}
	
	public void update(Long gameTime, float targetX, float targetY, int speed, boolean center) {
		super.update(gameTime, targetX, targetY, speed, center);
		/*double delta_x = (double) (this.x-targetX);
		double delta_y = (double) (this.y-targetY);*/
		/*if(sign_x > 0 && sign_y > 0) {
			radian_angle += Math.PI;
		}
		else if (sign_x > 0 && sign_y < 0) {
			radian_angle += Math.PI/2;
		}
		else if (sign_x < 0 && sign_y > 0) {
			radian_angle += 3*Math.PI/2;
		}*/
		
		/*double angle = Math.atan2(delta_y, delta_x);
		
		if (gameTime > frameTicker + framePeriod) {
			frameTicker = gameTime;
			float difX = speed*(float)Math.cos(angle);
			float difY = speed*(float)Math.sin(angle);
			
			x += difX;
			y += difY;
			
		}*/
	}
	
	@Override
	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, getX(), getY(), null);
	}
}
