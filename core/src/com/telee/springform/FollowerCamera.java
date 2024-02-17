package com.telee.springform;

import com.badlogic.gdx.Input;

public class FollowerCamera extends Camera {
		GameObject target;
		
		FollowerCamera(GameObject _target, int _maxSpeed) {
			super(0, 0);
			target = _target;
		}
		void update() {
			super.update();
			//x = Math.round(target.x - Render.hvasX);
			//y = Math.round(-target.y - Render.hvasY);
			
			x = target.x;
			y = -target.y;
			
			if (Key.Down(Input.Keys.E)) { // e - zoom in
				this.zoom -= zoom/10;
			}
			if (Key.Down(Input.Keys.Q)) { // q - zoom out
				this.zoom += zoom/10;
			}
		}
}
