package com.kklop.angmengine.game.sprite;

import java.util.PriorityQueue;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import com.kklop.angmengine.game.sprite.bound.Bound;
import com.kklop.angmengine.game.sprite.bound.rect.RectBound;

public class AnimatedSprite extends Sprite {
	
	PriorityQueue<SpriteAnimation> anQueue;
	
	// height 60 width 30
	// so 5 frames will need to be 30*5=150px
	
	//protected Bitmap bitmap;		// the animation sequence
	protected Rect sourceRect;	// the rectangle to be drawn from the animation bitmap
	protected int frameNr;		// number of frames in animation
	protected int currentFrame;	// the current frame
	protected long anFrameTicker;	// the time of the last frame update
	protected int anFramePeriod;	// milliseconds between each frame (1000/fps)

	protected int spriteWidth;	// the width of the sprite to calculate the cut out rectangle
	protected int spriteHeight;	// the height of the sprite
	protected boolean loop;    // should the animation loop

	public AnimatedSprite(Bound bound, Bitmap bitmap, float x, float y, 
			int width, int height, int fps, int frameCount, int moveFps, 
			String type, boolean loop) {
		super(bound, bitmap, x, y, moveFps, type);
		currentFrame = 0;
		frameNr = frameCount;
		spriteWidth = bitmap.getWidth() / frameCount;
		spriteHeight = bitmap.getHeight();
		sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
		anFramePeriod = 1000 / fps;
		anFrameTicker = 0l;
		anQueue = new PriorityQueue<SpriteAnimation>();
	}
	
	@Override
	public void update(Long gameTime, float targetX, float targetY, int speed,
			boolean center) {
		super.update(gameTime, targetX, targetY, speed, center);
		if (gameTime > anFrameTicker + anFramePeriod) {
			anFrameTicker = gameTime;
			// increment the frame
			currentFrame++;
			if (currentFrame >= frameNr) {
				if(anQueue.peek() != null && (!loop)) {
					loadSpriteAnimation(anQueue.poll());
				}
				currentFrame = 0;
			}
		}
		// define the rectangle to cut out sprite
		this.sourceRect.left = currentFrame * spriteWidth;
		this.sourceRect.right = this.sourceRect.left + spriteWidth;
	}

	@Override
	public void draw(Canvas canvas, RectBound bound) {
		if(isInBound(bound)) {
			// where to draw the sprite
			RectF destRect = new RectF(
					getDrawX(), 
					getDrawY(), 
					getDrawX() + spriteWidth, 
					getDrawY() + spriteHeight
				);
			canvas.drawBitmap(bitmap, sourceRect, destRect, null);
		}
	}
	
	@Override
	public void draw(Canvas canvas) {
		// where to draw the sprite
		RectF destRect = new RectF(
				getDrawX(), 
				getDrawY(), 
				getDrawX() + spriteWidth, 
				getDrawY() + spriteHeight
			);
		canvas.drawBitmap(bitmap, sourceRect, destRect, null);
	}

	/**
	 * Add animation to the queue or interrupt
	 * current animation.
	 * @param bitmap
	 * @param width
	 * @param height
	 * @param fps
	 * @param frameCount
	 * @param moveFps
	 * @param type
	 * @param loop
	 * @param interrupt
	 */
	public void addAnimation(Bitmap _bitmap, int width, int height, 
			int fps, int frameCount, int moveFps, String type, boolean loop,
			boolean interrupt) {
		int currentFrame = 0;
		int frameNr = frameCount;
		int spriteWidth = _bitmap.getWidth() / frameCount;
		int spriteHeight = _bitmap.getHeight();
		Rect sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
		int anFramePeriod = 1000 / fps;
		long anFrameTicker = 0l;
		
		SpriteAnimation an = new SpriteAnimation(_bitmap, sourceRect, frameNr,
				currentFrame, anFrameTicker, anFramePeriod, spriteWidth,
				spriteHeight, loop);
		
		if(interrupt) {
			loadSpriteAnimation(an);
		} else {
			anQueue.add(an);
		}
	}
	
	private void loadSpriteAnimation(SpriteAnimation an) {
		this.bitmap = an.bitmap;
		this.normalBitmap = an.bitmap;
		this.sourceRect = an.sourceRect;
		this.frameNr = an.frameNr;
		this.currentFrame = an.currentFrame;
		this.anFrameTicker = an.anFrameTicker;
		this.anFramePeriod = an.anFramePeriod;
		this.spriteWidth = an.spriteWidth;
		this.spriteHeight = an.spriteHeight;
		this.loop = an.loop;
		createFlipBitmap();
		// handle direction so we don't revert to default
		if(direction == SPRITE_DIRECTION.EAST) {
			this.bitmap = normalBitmap;
		} else {
			this.bitmap = flipBitmap;
		}
	}

	private float getDrawX() {
		//return x-(spriteWidth);
		return x;
	}
	
	private float getDrawY() {
		//return y-(spriteHeight);
		return y;
	}
	
	@Override
	public float getCompY() {
		return getDrawY() + bitmap.getHeight();
	}

	@Override
	public int getWidth() {
		return spriteWidth;
	}

	@Override
	public int getHeight() {
		return spriteHeight;
	}

	@Override
	public PointF getTopLeftCrnr() {
		return new PointF(getDrawX(), getDrawY());
	}

	@Override
	public PointF getTopRightCrnr() {
		PointF p = null;
		p = new PointF(getDrawX() + spriteWidth, getDrawY());
		return p;
	}

	@Override
	public PointF getBotLeftCrnr() {
		PointF p = null;
		p = new PointF(getDrawX(), getDrawY()+ spriteHeight);
		return p;
	}

	@Override
	public PointF getBotRightCrnr() {
		PointF p = null;
		p = new PointF(getDrawX() + spriteWidth, 
				getDrawY() + spriteHeight);
		return p;
	}

	@Override
	public float getX() {
		return getDrawX();
	}

	@Override
	public float getMaxX() {
		return getX() + spriteWidth;
	}

	@Override
	public float getMaxY() {
		return getY() + spriteHeight;
	}

	@Override
	public float getCenterX() {
		return getDrawX() + spriteWidth/2;
	}

	@Override
	public float getCenterY() {
		return getDrawY() + spriteHeight/2;
	}

	@Override
	public float getY() {
		return getDrawY();
	}

	public int getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
	}
	

}
