package com.telee.springform.pointer;

import com.telee.springform.Desktop;
import com.telee.springform.Icon;
import com.telee.springform.Util;
import com.telee.springform.components.Sprite;

public class PointerTool extends Tool {

	public PointerTool() {
		super("");
		sprite = new Sprite("./assets/textures/pointers/arrow.png", 1);
		
		scaledSpriteRatio(1);
		
		setup();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}
	
	public void press() {
		Desktop.pworld.QueryAABB(Util.getFirstCallback, parent.getX(), parent.getY(), parent.getX(), parent.getY());
		
		//Util.log(Util.first.getClass().getName());
		if (Util.first != null && Util.first.getClass() == Icon.class) {
			Util.log(((Icon) Util.first).exec);
			((Icon) Util.first).execute();
		}
		
	}

}
