package com.telee.springform.pointer;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.telee.springform.Desktop;
import com.telee.springform.GameObject;
import com.telee.springform.components.PhysicsBody;
import com.telee.springform.components.Sprite;

public class GrabTool extends Tool {
	GameObject grabbed;
	Joint joint;
	
	QueryCallback getFirst = new QueryCallback () {
		public boolean reportFixture(Fixture f) {
			GameObject o = ((PhysicsBody) f.getBody().getUserData()).parent;
			if (o == Desktop.pointer) return true;
			grabbed = o;
			return false;
		}
	};
	
	public GrabTool(String name) {
		super(name);
		grabbed = null;
		
		sprite = new Sprite("./assets/textures/tools/hand1.png", 0.5f);
	}

	@Override
	public void update() {
		
		if (grabbed != null) {
			if (grabbed.doRemove() == true || grabbed.body.box == null) {
				joint = null;
				return;
			}
			grabbed.body.box.setAwake(true);
		}

	}
	
	public void press() {
		grabbed = null;
		WeldJointDef jointDef = new WeldJointDef();
		
		Desktop.pworld.QueryAABB(getFirst, parent.getX(), parent.getY(), parent.getX(), parent.getY());
		if (grabbed == null) return;
		if (grabbed.body == null) return;
		//if (grabbed.body.type != T.CT_DYNAMIC) return;
		
		jointDef.bodyA = grabbed.body.box;
		jointDef.bodyB = parent.body.box;
		jointDef.dampingRatio = 0.0f;
		jointDef.frequencyHz = 0.25f;
		
		//jointDef.maxForce = 2 * Math.min(first.body.box.getMass(), 1f) * 10000;
		joint = Desktop.pworld.createJoint(jointDef);
	}
	
	public void release() {
		if (joint != null) Desktop.pworld.destroyJoint(joint);
		joint = null;
		if (grabbed != null) grabbed.body.box.applyForceToCenter(0, -50, true);
	}

}
