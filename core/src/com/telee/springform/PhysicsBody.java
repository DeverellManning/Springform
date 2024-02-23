package com.telee.springform;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

public class PhysicsBody extends GameComponent{
	T shape, type;
	float width, height;
	Boolean support, touching;
	float friction, restitution;
	boolean allLayers;
	
	Body box;
	
	PhysicsBody() {
		this.clss = "Physics_Body";
		friction = 0.4f;
		restitution = 0.2f;
		allLayers = false;
	}
	
	PhysicsBody(T _type, T _shape, float _width, float _height) {
		this();
		this.shape = _shape;
		this.type = _type;
		
		if (_shape == T.CS_CIRCLE) {
			width = _width;
			height = _width;
		} else if (_shape == T.CS_AABB) {
			width = _width;
			height = _height;
		}
		init();
	}
	
	PhysicsBody(T _type, T _shape) {
		this();
		this.shape = _shape;
		this.type = _type;
		width = 1;
		height = 1;
	}
	
	PhysicsBody(T _type, T _shape, boolean _allLayers) {
		this(_type, _shape);
		allLayers = _allLayers;
	}
	
	//Setup - After setParent()
	void setup() {
		if (box != null)
			Desktop.pworld.destroyBody(box);
		BodyDef def = new BodyDef();
		Shape shape;
		
		width = parent.w;
		height = parent.h;

		switch (type) {
		case CT_STATIC:
			def.type = BodyDef.BodyType.StaticBody;
			break;
		case CT_DYNAMIC:
			def.type = BodyDef.BodyType.DynamicBody;
			break;
		case CT_KINEMATIC:
			def.type = BodyDef.BodyType.KinematicBody;
			break;
		default:
			def.type = BodyDef.BodyType.DynamicBody;
		}

		
		if (type == T.CS_CIRCLE) {
			shape = new CircleShape();
			shape.setRadius(width);
		} else {
			PolygonShape tmp = new PolygonShape();
			tmp.setAsBox(width, height);
			shape = tmp;
		}
		
		
		
		box = Desktop.pworld.createBody(def);
		box.setTransform(parent.x, parent.y, parent.angle);
		
		Fixture f = box.createFixture(shape, 1);
		f.setFriction(friction);
		f.setRestitution(restitution);
		
		Filter filter = new Filter();
		filter.groupIndex = Desktop.layers.get(parent.layer).collisionGroup;
		filter.maskBits = 0x1000;
		filter.categoryBits = 0x0001;
		if (allLayers) {
			filter.maskBits = 0x1111;
			filter.categoryBits = 0x1001;
		}

		f.setFilterData(filter);
		box.setUserData(this);

		shape.dispose();
		
		
		
	}
	

	
	void update() {
		support = false;
		touching = false;
		
		if (box != null) {
			parent.x = box.getPosition().x;
			parent.y = box.getPosition().y;
			parent.angle = (float) Math.toDegrees(box.getAngle());
		}
		
	}
	
	void remove() {
		box.getWorld().destroyBody(box);
	}
	
	public void setPosition(float x, float y) {
		box.setTransform(x, y, box.getAngle());
	}

	public void setAngle(float a) {
		box.setTransform(box.getPosition(), a);
		
	}
	
}
