package com.kklop.angmengine.game.sprite.bound.rect;

import android.graphics.PointF;

import com.kklop.angmengine.game.sprite.bound.Bound;

public class RectBound extends Bound {

	PointF left;
	PointF right;
	
	public RectBound(PointF left, PointF right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public Float inBoundX(Float x, int spriteWidth) {
		Float result = x;
		if((x + spriteWidth) > right.x) {
			result = right.x - spriteWidth;
		} else if (x < left.x){
			result = left.x;
		}
		return result;
	}

	@Override
	public Float inBoundY(Float y, int spriteHeight) {
		Float result = y;
		if((y + spriteHeight) > right.y) {
			result = right.y - spriteHeight;
		} else if (y < left.y){
			result = left.y;
		}
		return result;
	}

	@Override
	public PointF getBoundPoint() {
		return right;
	}

}
