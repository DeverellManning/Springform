package com.telee.springform;

import com.badlogic.gdx.graphics.Texture;

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
	
	void draw() {
		Render.reset();
		if (background != null) {
			Render.sprite.draw(background, -1, -1, 2, 2);
		}
		
		Desktop.cam.transform();
		
		for (int i = objs.size()-1; i >= 0; i--) {
			GameObject c = objs.get(i);
			//if ((c.x - camX)*zoom > Render.vasX+64 || (c.x - camX)*zoom < -64) {continue;}
			//if ((c.y - camY)*zoom > Render.vasY+64 || (c.y - camY)*zoom < -64) {continue;}
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
