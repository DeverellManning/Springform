package com.telee.springform.pointer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.telee.springform.*;
import com.telee.springform.components.Sprite;

public class CreateTool extends Tool {
	
	private enum State {
		origin,
		size,
		create
	}
	State state;
	
	Vector2 origin, size;
	
	
	public CreateTool() {
		super("Creation");
		state = State.origin;
		
		size = new Vector2();
		origin = new Vector2();
		
		sprite = new Sprite("./assets/textures/tools/gimp-tool-rect-select.png", 1);
		//w = 
	}

	@Override
	public void update() {
		if (state == State.size) {
			size.x = parent.getX() - origin.x;
			size.y = parent.getY() - origin.y;
			
			size.clamp(1, 20);
			
			if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
				size.x = Math.round(size.x);
				size.y = Math.round(size.y);
			}
			
			
		}
		
	}

	public void draw() {
		super.draw();
		
		if (state == State.size) {
			Render.sprite.flush();
			Render.shape.setColor(0.9f, 1, 1, 0.6f);
			Render.shape.rect(origin.x, origin.y, size.x, size.y);
		}
	}
	
	public void press() {
		float x = parent.getX();
		float y = parent.getY();
		
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
			x = Math.round(x);
			y = Math.round(y);
			
			Util.log("x: " + x + " y: " + y);
		}
		
		switch (state) {
		case origin:
			origin = new Vector2(x, y);
			state = State.size;
			size = new Vector2(0, 0);
			break;
		case size:
			size.x /= 2;
			size.y /= 2;
			
			origin.add(size);
			
			size.x = Math.abs(size.x);
			size.y = Math.abs(size.y);
			
			if (size.x <= 0.005 || size.y <= 0.005) {
				size.x = 0.5f;
				size.y = 0.5f;
			}
			
			GameObject o = new GameObject(origin, size, LayerName.main, "./assets/textures/material/ground.png");
			o.sprite.stretch = false;
			
			Desktop.add(o);
			state = State.origin;
			break;
		default:
			break;
		}
	}
}
