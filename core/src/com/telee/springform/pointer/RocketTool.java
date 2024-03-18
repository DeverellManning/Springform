package com.telee.springform.pointer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.telee.springform.*;
import com.telee.springform.components.Sprite;
import com.telee.springform.objects.Rocket;

public class RocketTool extends Tool {
	public RocketTool() {
		super("");
		sprite = new Sprite("./assets/textures/tools/gimp-tool-rect-select.png", 1);
	}
	
	public void press() {
		float x = parent.getX();
		float y = parent.getY();
		
		Desktop.add(new Rocket(x, y));
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
}
