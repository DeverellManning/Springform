package com.telee.springform.pointer;

import com.telee.springform.Desktop;
import com.telee.springform.T;
import com.telee.springform.Util;
import com.telee.springform.components.Sprite;
import com.telee.springform.objects.Icon;

public class PointerTool extends Tool {

	public PointerTool() {
		super("");
		sprite = new Sprite("./assets/textures/pointers/arrow.png", 1);
		
		this.facing = T.D_RIGHT;
		
		setup();
		
	}

	@Override
	public void update() {
		Desktop.pworld.QueryAABB(Util.getFirstCallback, parent.getX(), parent.getY(), parent.getX(), parent.getY());
		
		if (Util.first != null) {
			floatText = Util.first.getName();
		}
	}
	
	public void press() {
		Desktop.pworld.QueryAABB(Util.getFirstCallback, parent.getX(), parent.getY(), parent.getX(), parent.getY());
		
		if (Util.first != null && Util.first.getClass() == Icon.class) {
			Util.log(((Icon) Util.first).exec);
			((Icon) Util.first).execute();
		}
		
	}

}
