package com.telee.springform;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

public class Layer extends DefaultInterface{
	
	ObjectList objs;
	short collisionGroup;
	LayerName name;
	
	Texture background;
	
	Layer(LayerName n) {
		super();
		name = n;
		collisionGroup = (short) (name.ordinal()+1);
		
		objs = new ObjectList();
		
		if (name == LayerName.backdrop) {
			background = new Texture("textures/backdrops/lxde_blue.jpg");
		}
	}
	
	public void draw() {
		Render.reset();
		if (background != null) {
			Render.sprite.draw(background, -1, -1, 2, 2);
		}
		
		Render.transform();
		
		for (int i = objs.size()-1; i >= 0; i--) {
			GameObject c = objs.get(i);
			Vector3 screenPos = Desktop.cam.cam.project(new Vector3(c.x, c.y, 0f));
			if (screenPos.x > Render.vasX+c.w*32 || screenPos.x < -c.h*32) {continue;}
			if (screenPos.y > Render.vasY+c.h*32 || screenPos.y < -c.h*32) {continue;}
			if (c.hidden) {continue;}
			
			c.draw();
		}
		
		Render.sprite.flush();
	}
	
	void clear() {
		objs.clear();
	}
	
	boolean add(GameObject o) {
		return objs.add(o);
	}

	public boolean remove(GameObject o) {
		return objs.remove(o);
	}
}
