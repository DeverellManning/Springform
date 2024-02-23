package com.telee.springform;

import java.io.IOException;

public class Icon extends GameObject {
	String exec;
	
	public Icon(float _x, float _y, String command, String icon) {
		super(_x, _y, LayerName.main, new Sprite(icon, 1), new PhysicsBody(T.CT_DYNAMIC, T.CS_AABB));
		
		w = 1;
		h = 1;
		
		setup();
		
		exec = command;
	}
	
	public void execute() {
		if (exec == null || exec.isEmpty())
			return;
		
        try {
			Runtime.getRuntime().exec(exec);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
