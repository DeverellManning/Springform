package com.telee.springform;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.telee.springform.tools.*;

public class Pointer extends GameObject {
	PointerCamera selfie;
	float speed;
	
	QueryCallback onclick;
	
	Tool tool;
	
	Pointer() {
		super(0, 15, new Sprite("./assets/textures/pointers/arrow.png", 1),
				new PhysicsBody(T.CT_STATIC, T.CS_AABB));
		
		this.facing = T.D_RIGHT;
		this.layer = LayerName.pointer;
		
		//w = sprite.tw/64;
		//h = sprite.th/64;
		
		scaledSpriteRatio(0.5f);
		
		tool = new CreateTool();

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
		
		onclick = new QueryCallback () {
			public boolean reportFixture(Fixture f) {
				GameObject o = ((PhysicsBody) f.getBody().getUserData()).parent;
				//((PhysicsBody) f.getBody().getUserData()).parent.remove();
				if (o != null && o.getClass() == Icon.class) {
					((Icon) o).execute();
				}
				return false;
			}
		};
		

	}
	
	public void setup() {
		super.setup();
		
		if (tool != null) {
			tool.setParent(this);
			tool.setup();
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
		
		
		//if (Gdx.input.getX() > Render.vasX-2) {
		Gdx.input.setCursorPosition(Render.hvasX, Render.hvasY);
		//}
		
		//if (body != null) {body.update();}
		if (sprite != null) {sprite.update();}

		//body.box.applyLinearImpulse(-Gdx.input.getDeltaX()*speed, Gdx.input.getDeltaY()*speed, 0, 0, true);
		//body.box.applyForceToCenter(-Gdx.input.getDeltaX()*speed, Gdx.input.getDeltaY()*speed, true);
		
		
		
		if (Gdx.input.justTouched()) {
			//Desktop.pworld.QueryAABB(onclick, x, y, x, y);
			//Desktop.add(new Block(x, y));
		}
		
		tool.update();
		
		
	}
	
	void mouseScrolled(float amt) {
		Util.log("Scrolled: " + amt);
		if (amt > 0) {tool = new GrabTool(""); setup();};
		if (amt < 0) {tool = new CreateTool(); setup();};
	}
	
	void mouseDown(int button) {
		if (button == Buttons.LEFT) {
			tool.press();
		}
	}
	
	void mouseUp(int button) {
		if (button == Buttons.LEFT) {
			tool.release();
		}
	}
	
	public void draw() {
		Matrix4 transform = new Matrix4();
		transform.translate(x, y, 0);
		transform.rotate(new Quaternion().set(new Vector3(0, 0, 1), angle));
		Render.sprite.setTransformMatrix(transform);
		
		if (tool != null) {
			tool.draw();
		} else {
			
		}
		if (sprite != null) {sprite.draw();}
		if(Key.Down(Keys.SHIFT_LEFT) && Key.Down(Keys.NUM_1)) {
			Render.shape.circle(0, 0, 4);
		}

	}
	
	void remove() {
		
	}
	
}
