package com.telee.springform.objects;

import com.badlogic.gdx.Input;
import com.telee.springform.GameObject;
import com.telee.springform.Key;

public class FollowerCamera extends Camera {
		GameObject target;
		
		FollowerCamera(GameObject _target, int _maxSpeed) {
			super(0, 0);
			target = _target;
		}
		public void update() {
			super.update();
			//x = Math.round(target.x - Render.hvasX);
			//y = Math.round(-target.y - Render.hvasY);
			
			x = target.getX();
			y = -target.getY();
			
			if (Key.Down(Input.Keys.E)) { // e - zoom in
				this.zoom -= zoom/10;
			}
			if (Key.Down(Input.Keys.Q)) { // q - zoom out
				this.zoom += zoom/10;
			}
		}
}
