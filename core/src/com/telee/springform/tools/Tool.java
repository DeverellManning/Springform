package com.telee.springform.tools;

import com.telee.springform.*;

public abstract class Tool extends GameComponent{
	Sprite sprite;
	
	float w, h;
	
	
	public Tool(String image) {
		super();
	}

	public abstract void update();
	public void draw() {
		if (sprite != null) sprite.draw();
	}
	
	public void setup() {
		if (sprite != null) sprite.setParent(parent);
	}
	
	public void press() {}
	public void hold() {}
	public void drag() {}
	public void release() {}
}
