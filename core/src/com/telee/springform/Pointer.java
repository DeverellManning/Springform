package com.telee.springform;

import com.badlogic.gdx.*;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.QueryCallback;

public class Pointer extends GameObject {
	PointerCamera selfie;
	float speed;
	
	QueryCallback onclick;
	
	Pointer() {
		super(0, 15, new Sprite("textures/arrow.png", 1),
				new PhysicsBody(T.CT_DYNAMIC, T.CS_AABB));
		
		this.facing = T.D_RIGHT;
		this.layer = LayerName.pointer;
		
		w = sprite.tw/64;
		h = sprite.th/64;
		
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
			Desktop.pworld.QueryAABB(onclick, x, y, x, y);
		}
		
		
	}
	
	
	void draw() {
		super.draw();
	}
	
	void remove() {
		
	}
	
	void loadJSON() {
		Gdx.files.external("textures");
		
	}
}
