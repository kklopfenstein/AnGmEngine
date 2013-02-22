package com.kklop.angmengine.game.sprite.comparator;

import java.util.Comparator;

import android.util.Log;

import com.kklop.angmengine.game.sprite.Sprite;

public class SpriteComparator implements Comparator<Sprite> {

	public static final String TAG = SpriteComparator.class.getSimpleName();
	
	@Override
	public int compare(Sprite arg0, Sprite arg1) {
		int result = Float.valueOf(arg0.getCompY()).
				compareTo(Float.valueOf(arg1.getCompY()));
		if(result != 1) {
			Log.i(TAG, "Comparing " + arg0.getCompY() + " to " 
					+ arg1.getCompY() +  " = " + result);
		}
		return result;
		
	}

}
