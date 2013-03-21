package com.kklop.angmengine.game.sprite;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * 
 * @author Kevin Klopfenstein
 *
 */
public class SpriteAnimation {

	protected Bitmap bitmap;    // bitmap
	protected Rect sourceRect;	// the rectangle to be drawn from bitmap
	protected int frameNr;		// number of frames in animation
	protected int currentFrame;	// the current frame
	protected long anFrameTicker;	// the time of the last frame update
	protected int anFramePeriod;	// milliseconds btw each frame (1000/fps)
	protected int spriteWidth;	// the width of the sprite to calculate the cut out rectangle
	protected int spriteHeight;	// the height of the sprite
	protected boolean loop; // should the animation loop

	public SpriteAnimation(Bitmap bitmap, Rect sourceRect, int frameNr,
			int currentFrame, long anFrameTicker, int anFramePeriod,
			int spriteWidth, int spriteHeight, boolean loop) {
		super();
		this.bitmap = bitmap;
		this.sourceRect = sourceRect;
		this.frameNr = frameNr;
		this.currentFrame = currentFrame;
		this.anFrameTicker = anFrameTicker;
		this.anFramePeriod = anFramePeriod;
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
		this.loop = loop;
	}
	
}
