package com.telee.springform.pointer;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;

import com.telee.springform.Desktop;
import com.telee.springform.GameObject;
import com.telee.springform.Key;
import com.telee.springform.LayerName;
import com.telee.springform.ObjectList;
import com.telee.springform.Render;
import com.telee.springform.T;
import com.telee.springform.Util;
import com.telee.springform.components.PhysicsBody;
import com.telee.springform.components.Sprite;
import com.telee.springform.objects.Icon;
import com.telee.springform.objects.PointerCamera;

public class Pointer extends GameObject {
	PointerCamera selfie;
	float speed;
	
	QueryCallback onclick;
	
	ArrayList<Tool> tools;
	Tool tool;
	int toolIndex;
	
	public Pointer() {
		super(0, 15, new Sprite("./assets/textures/pointers/arrow.png", 1),
				new PhysicsBody(T.CT_STATIC, T.CS_AABB));
		
		this.facing = T.D_RIGHT;
		this.layer = LayerName.pointer;
		
		//w = sprite.tw/64;
		//h = sprite.th/64;
		
		scaledSpriteRatio(0.5f);
		
		tools = new ArrayList<Tool>();
		tools.add(new CreateTool());
		tools.add(new GrabTool(""));
		tools.add(new DestroyTool());
		tools.add(new PointerTool());
		tools.add(new RocketTool());
		toolIndex = 0;
		
		setup();
		
		speed = 0.24f;
		
		Gdx.input.setCursorCatched(true);
		Gdx.input.setCursorPosition(Render.hvasX, Render.hvasY);
        
		body.box.setGravityScale(0);
		body.box.setBullet(true);
		body.box.setFixedRotation(true);
		MassData m = new MassData();
		m.mass = 0.2f;
		body.box.setMassData(m);
		body.box.setAngularDamping(0.4f);
		body.box.setLinearDamping(95f);
		
		selfie = new PointerCamera(this);
		Desktop.cams.add(selfie);
		Desktop.cam = Desktop.cams.get(Desktop.cams.size()-1);
		
	}
	
	public void setup() {
		super.setup();
		
		if (tool != null) {
			tool.setParent(this);
			tool.setup();
		}
		
		if (tools == null) return;
		
		for (Tool t : tools) {
			t.setParent(this);
			t.setup();
		}

	}
	
	public void update() {
		vx += Gdx.input.getDeltaX()*speed;
		vy += -Gdx.input.getDeltaY()*speed;
		
		vx *= 0.4;
		vy *= 0.4;
		
		setPosition(x + vx, y + vy);
		
		//Vector2 boxPos = body.box.getPosition();
		//body.box.applyLinearImpulse(100*(x - boxPos.x), 100*(y - boxPos.y), x, y, true);
		//body.box.applyLinearImpulse(	Gdx.input.getDeltaX()*speed, -Gdx.input.getDeltaY()*speed);
		
		Gdx.input.setCursorPosition(Render.hvasX, Render.hvasY);
		
		//if (body != null) {body.update();}
		if (sprite != null) {sprite.update();}

		//body.box.applyLinearImpulse(-Gdx.input.getDeltaX()*speed, Gdx.input.getDeltaY()*speed, 0, 0, true);
		//body.box.applyForceToCenter(-Gdx.input.getDeltaX()*speed, Gdx.input.getDeltaY()*speed, true);
		
		tools.get(toolIndex).update();
		
		
	}
	
	public void mouseScrolled(float amt) {
		//Util.log("Scrolled: " + amt);
		toolIndex = (int) (toolIndex + amt);
		if (toolIndex > tools.size()-1) toolIndex = 0;
		if (toolIndex < 0) toolIndex = tools.size()-1;
		
	}
	
	public void mouseDown(int button) {
		if (button == Buttons.LEFT) {
			tools.get(toolIndex).press();
		}
	}
	
	public void mouseUp(int button) {
		if (button == Buttons.LEFT) {
			tools.get(toolIndex).release();
		}
	}
	
	public void draw() {
		Matrix4 transform = new Matrix4();
		transform.translate(x, y, 0);
		transform.rotate(new Quaternion().set(new Vector3(0, 0, 1), angle));
		Render.sprite.setTransformMatrix(transform);
		
		if (tools.get(toolIndex) != null) {
			tools.get(toolIndex).draw();
		} else {
			if (sprite != null) {sprite.draw();}
		}
		if(Key.Down(Keys.SHIFT_LEFT) && Key.Down(Keys.NUM_1)) {
			Render.shape.circle(0, 0, 4);
		}
		
		transform.scale(1/32f, 1/32f, 1f);
		Render.sprite.setTransformMatrix(transform);
		if (tools.get(toolIndex) != null && tools.get(toolIndex).floatText != null) {
			Render.text(tools.get(toolIndex).floatText, 10, 10);
			
		}
		

	}
	
	public void remove() {
		
	}
	
}
