package com.kklop.angmengine.game.sprite;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

public class AnimatedSprite extends Sprite {
	// height 60 width 30
	// so 5 frames will need to be 30*5=150px
	
	private static final String TAG = AnimatedSprite.class.getSimpleName();
	
	//protected Bitmap bitmap;		// the animation sequence
	protected Rect sourceRect;	// the rectangle to be drawn from the animation bitmap
	protected int frameNr;		// number of frames in animation
	protected int currentFrame;	// the current frame
	protected long anFrameTicker;	// the time of the last frame update
	protected int anFramePeriod;	// milliseconds between each frame (1000/fps)

	protected int spriteWidth;	// the width of the sprite to calculate the cut out rectangle
	protected int spriteHeight;	// the height of the sprite

	//protected int x;				// the X coordinate of the object (top left of the image)
	//protected int y;				// the Y coordinate of the object (top left of the image)
	
	protected float targetX;
	protected float targetY;
	
	public AnimatedSprite(Context context, Bitmap bitmap, float x, float y, 
			int width, int height, int fps, int frameCount, int moveFps) {
		super(context, bitmap, x, y, moveFps);
		//this.bitmap = bitmap;
		//this.x = x;
		//this.y = y;
		currentFrame = 0;
		frameNr = frameCount;
		spriteWidth = bitmap.getWidth() / frameCount;
		spriteHeight = bitmap.getHeight();
		sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
		anFramePeriod = 1000 / fps;
		anFrameTicker = 0l;
	}
	
	public void update(long gameTime) {
		if (gameTime > anFrameTicker + anFramePeriod) {
			anFrameTicker = gameTime;
			// increment the frame
			currentFrame++;
			if (currentFrame >= frameNr) {
				currentFrame = 0;
			}
		}
		// define the rectangle to cut out sprite
		this.sourceRect.left = currentFrame * spriteWidth;
		this.sourceRect.right = this.sourceRect.left + spriteWidth;
	}

	@Override
	public void draw(Canvas canvas) {
		// where to draw the sprite
		RectF destRect = new RectF(getDrawX(), getDrawY(), getDrawX() + spriteWidth, getDrawY() + spriteHeight);
		canvas.drawBitmap(bitmap, sourceRect, destRect, null);
	}

	private float getDrawX() {
		return x-(spriteWidth);
	}
	
	private float getDrawY() {
		return y-(spriteHeight);
	}
	
	@Override
	public float getCompY() {
		return getDrawY() + bitmap.getHeight();
	}

}
