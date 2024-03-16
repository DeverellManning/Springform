package com.telee.springform.pointer;

import com.telee.springform.Desktop;
import com.telee.springform.Util;
import com.telee.springform.components.Sprite;

public class DestroyTool extends Tool {

	public DestroyTool() {
		super("");
		sprite = new Sprite("./assets/textures/tools/destroy-tool.png", 1);
	}

	public void update() {}
	
	public void press() {
		Desktop.pworld.QueryAABB(Util.getFirstCallback, parent.getX(), parent.getY(), parent.getX(), parent.getY());
		if (Util.first == null) return;
		if (Util.first == null) return;
		
		Util.first.remove();
	}
}
