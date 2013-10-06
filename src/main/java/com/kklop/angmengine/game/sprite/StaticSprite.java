package com.kklop.angmengine.game.sprite;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.kklop.angmengine.game.sprite.bound.Bound;
import com.kklop.angmengine.game.sprite.bound.rect.RectBound;

public class StaticSprite extends Sprite {
	
	@SuppressWarnings("unused")
	private static final String TAG = StaticSprite.class.getSimpleName();
	
	public StaticSprite(Bound bound, Bitmap bitmap, float x, float y, int fps, 
			String type) {
		super(bound, bitmap, x, y, fps, type);
	}
	
	public void update(Long gameTime, float targetX, float targetY, int speed, 
			boolean center) {
		super.update(gameTime, targetX, targetY, speed, center);
	}
	
	@Override
	public void draw(Canvas canvas, RectBound bound) {
		if(isInBound(bound)) {
			canvas.drawBitmap(bitmap, getX(), getY(), null);
		}
	}
	
	@Override
	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, getX(), getY(), null);
	}
}
