package com.kklop.angmengine.game.event;

import android.graphics.Canvas;
import android.view.MotionEvent;

public class TouchScreenEvent implements GameEvent {
	MotionEvent event;
	
	public TouchScreenEvent(MotionEvent event) {
		this.event = event;
	}

	public MotionEvent getEvent() {
		return event;
	}

	public void setEvent(MotionEvent event) {
		this.event = event;
	}

	@Override
	public void continueEvent(long gameTime) {
		
	}

	@Override
	public boolean isActive() {
		return false;
	}

	@Override
	public void startEvent() {
		
	}

	@Override
	public void drawEvent(Canvas canvas) {
		
	}

	@Override
	public void stopEvent() {
		
	}
	
}
