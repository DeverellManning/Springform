package com.telee.springform;

import java.io.IOException;

import com.badlogic.gdx.physics.box2d.MassData;
import com.telee.springform.components.PhysicsBody;
import com.telee.springform.components.Sprite;

public class Icon extends GameObject {
	String exec;
	Process proc;
	
	public Icon() {
		
	}
	
	public Icon(float _x, float _y, String command, String icon) {
		super(_x, _y, LayerName.main, new Sprite(icon, 1), new PhysicsBody(T.CT_DYNAMIC, T.CS_AABB));
		
		w = 1;
		h = 1;
		
		//MassData m = new MassData();
		//m.mass = 10000;
		//body.box.setMassData(m);
		setup();
		
		exec = command;
	}
	
	public void update() {
		super.update();
		
		if (proc != null) {
			try {
				proc.getInputStream().read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void execute() {
		if (exec == null || exec.isEmpty())
			return;
		
        try {
			proc = Runtime.getRuntime().exec(exec);
			//proc.getInputStream().
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
