package com.telee.springform.objects;

import com.badlogic.gdx.physics.box2d.Box2D;
import com.telee.springform.GameObject;
import com.telee.springform.T;
import com.telee.springform.Util;
import com.telee.springform.components.*;

public class Rocket extends GameObject {
	Rocket() {
		super();
		sprite = new Sprite("./assets/textures/Metal-vert.png", 1);
		body = new PhysicsBody(T.CT_DYNAMIC, T.CS_AABB);
		
		scaledSpriteRatio(2);
		
		setup();
	}
	
	public Rocket(float _x, float _y) {
		this();
		x = _x;
		y = _y;
	}
	
	public void update() {
		super.update();
		if (age < 200) {
			body.box.applyForceToCenter(0, age*age*10, true);
			Util.log(age + " - " + age*age*10 + " - " + body.box.getLinearVelocity().y);
		}
	}
}
