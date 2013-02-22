package com.kklop.angmengine.game.event;

import android.view.MotionEvent;

public class TouchScreenEvent extends GameEvent {
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
	
}
