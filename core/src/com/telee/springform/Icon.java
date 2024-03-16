package com.telee.springform;

import java.io.IOException;

import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.telee.springform.components.PhysicsBody;
import com.telee.springform.components.Sprite;

public class Icon extends GameObject {
	public String exec;
	Process proc;
	
	public Icon() {
		
	}
	
	public Icon(float _x, float _y, String command, String icon) {
		super(_x, _y, LayerName.main, new Sprite(icon, 1), new PhysicsBody(T.CT_DYNAMIC, T.CS_AABB));
		
		//w = 1;
		//h = 1;
		
		scaledSpriteRatio(1);
		
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
				int ci = proc.getInputStream().read();
				if (ci > -1) {
					String out = "";
					do {
						ci = proc.getInputStream().read();
						
						out = out.concat(new Character((char) ci).toString());
						Util.log(new Character((char) ci).toString());
					} while (ci != -1);
					Util.log(out);
				}
				
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
	
	public void write(Json json) {
		super.write(json);
		json.writeValue("exec", exec);
		
	}
	
	public void read(Json json, JsonValue jsonData) {
		super.read(json, jsonData);
		exec = jsonData.getString("exec", "");
	}

}
