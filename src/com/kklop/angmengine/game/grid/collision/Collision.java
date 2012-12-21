package com.kklop.angmengine.game.grid.collision;

import com.kklop.angmengine.game.sprite.Sprite;

public class Collision {
	Sprite sp1;
	Sprite sp2;
	
	public Collision(Sprite sp1, Sprite sp2) {
		this.sp1 = sp1;
		this.sp2 = sp2;
	}

	public Sprite getSp1() {
		return sp1;
	}

	public void setSp1(Sprite sp1) {
		this.sp1 = sp1;
	}

	public Sprite getSp2() {
		return sp2;
	}

	public void setSp2(Sprite sp2) {
		this.sp2 = sp2;
	}
	
	
}
