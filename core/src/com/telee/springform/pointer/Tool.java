package com.telee.springform.pointer;

import com.telee.springform.*;
import com.telee.springform.components.GameComponent;
import com.telee.springform.components.Sprite;

public abstract class Tool extends GameObject {
	//Sprite sprite;

	protected GameObject parent;
	
	public String floatText;
	
	public Tool(String image) {
		super();
		
	}

	public abstract void update();
	public void draw() {
		if (sprite != null) sprite.draw();
	}
	
	public void setup() {
		if (sprite != null) {
			scaledSpriteRatio(sprite.th/64);
		}
		
		super.setup();
	}
	
	public void press() {}
	public void hold() {}
	public void drag() {}
	public void release() {}
	
	//public void equip() {}
	
	public void setParent(GameObject _parent) {
		parent = _parent;
	}
}
