package com.telee.springform.objects;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.telee.springform.GameObject;
import com.telee.springform.LayerName;
import com.telee.springform.T;
import com.telee.springform.Util;
import com.telee.springform.components.PhysicsBody;
import com.telee.springform.components.Sprite;

public class Icon extends GameObject {
	public String exec;
	Process proc;
	
	BufferedReader output;
	
	boolean running;
	float glow;
	
	public Icon() {
		
	}
	
	public Icon(float _x, float _y, String command, String icon) {
		super(_x, _y, LayerName.main, new Sprite(icon, 1), new PhysicsBody(T.CT_DYNAMIC, T.CS_AABB));
		
		scaledSpriteRatio(1);
		
		//MassData m = new MassData();
		//m.mass = 10000;
		//body.box.setMassData(m);
		setup();
		
		exec = command;
		running = false;
		
		
	}
	
	public void update() {
		super.update();
		
		if (running == true && proc != null && ! proc.isAlive()) {
			Util.log("Proccess Ended: " + exec + ", code: " + proc.exitValue());
			running = false;
		}
		if (output != null) {
			try {
				//int ci = output.read();
				
				/*if (ci > -1) {
					String out = "";
					while (ci != -1) {
						ci = output.read();
						
						out = out.concat(new Character((char) ci).toString());
						Util.log(new Character((char) ci).toString());
					}
					Util.log(out);
				}*/
				if (output.ready())
					Util.log(output.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//sprite.
	}
	
	
	public void execute() {
		if (exec == null || exec.isEmpty())
			return;
		
        try {
			proc = Runtime.getRuntime().exec(exec);
			if (proc != null) {
				output = new BufferedReader(new InputStreamReader(proc.getInputStream()));
				Util.log("Started Proccess:" + exec);
				running = true;
			}
		} catch (IOException e) {
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
