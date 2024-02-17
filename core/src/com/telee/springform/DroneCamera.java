package com.telee.springform;

import com.badlogic.gdx.Input;

public class DroneCamera extends Camera {
	DroneCamera(float _x, float _y) {
		super(_x, _y);
		this.speed = 6f;
	}
	
	void update() {
		float spd = this.speed * this.zoom;
		
		if (Key.Down(Input.Keys.SHIFT_LEFT)) {
			spd = spd * 6;
		}
		if (Key.Down(Input.Keys.A)) {
			this.x -= spd;
		}
		if (Key.Down(Input.Keys.D)) {
			this.x += spd;
		}
		if (Key.Down(Input.Keys.W)) {
			this.y -= spd;
		}
		if (Key.Down(Input.Keys.S)) {
			this.y += spd;
		}
		if (Key.Down(Input.Keys.E)) { // e - zoom in
			this.zoom -= zoom/10;
		}
		if (Key.Down(Input.Keys.Q)) { // q - zoom out
			this.zoom += zoom/10;
		}
	
		super.update();
	}
}
