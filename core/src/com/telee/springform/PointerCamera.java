package com.telee.springform;

import com.badlogic.gdx.Input;

public class PointerCamera extends Camera {
	GameObject target;
	float border;
	
	public PointerCamera(GameObject _target) {
		super(0,0);
		
		zoom=0.03125f;
		
		target = _target;
		border = 0.06f;
		
		x = target.x;
		y = -target.y;
	}
	
	public void update() {
		super.update();
		float tx = target.x;
		float ty = -target.y;
		
		//Render.text(Float.toString(zoom), 40, 50);
		
		if (tx > x + Render.hvasX*zoom - border*Render.hvasX*zoom) {
			vx += tx - (x + Render.hvasX*zoom - border*Render.hvasX*zoom);
		}
		if (tx < x - Render.hvasX*zoom + border*Render.hvasX*zoom) {
			vx -= (x - Render.hvasX*zoom + border*Render.hvasX*zoom) - tx;
		}
		
		if (ty > y + Render.hvasY*zoom - border*Render.hvasY*zoom) {
			vy += ty - (y + Render.hvasY*zoom - border*Render.hvasY*zoom);
		}
		if (ty < y - Render.hvasY*zoom + border*Render.hvasY*zoom) {
			vy -= (y - Render.hvasY*zoom + border*Render.hvasY*zoom) - ty;
		}
		
		if (Key.Down(Input.Keys.E)) { // e - zoom in
			this.zoom -= zoom/10;
		}
		if (Key.Down(Input.Keys.Q)) { // q - zoom out
			this.zoom += zoom/10;
		}
		
		vx = (float) Math.max(Math.min(vx*0.7, 2), -2);
		vy = (float) Math.max(Math.min(vy*0.7, 2), -2);;
		
		x += vx;
		y += vy;
	}
}
