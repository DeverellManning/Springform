package com.telee.springform.components;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import com.telee.springform.Desktop;
import com.telee.springform.T;


public class PhysicsBody extends GameComponent{
	public T shape;
	public T type;
	boolean allLayers;
	
	float width, height;
	Boolean support, touching;
	float friction, restitution;
	
	public Body box;
	
	PhysicsBody() {
		friction = 0.6f;
		restitution = 0.3f;
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
	
	public PhysicsBody(T _type, T _shape) {
		this();
		this.shape = _shape;
		this.type = _type;
		width = 1;
		height = 1;
	}
	
	public PhysicsBody(T _type, T _shape, boolean _allLayers) {
		this(_type, _shape);
		allLayers = _allLayers;
	}
	
	//Setup - After setParent()
	public void setup() {
		if (box != null)
			Desktop.pworld.destroyBody(box);
		
		BodyDef def = new BodyDef();
		Shape shape;
		
		width = parent.getWidth();
		height = parent.getHeight();

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
		box.setTransform(parent.getX(), parent.getY(), parent.getAngle());
		
		Fixture f = box.createFixture(shape, 1);
		f.setFriction(friction);
		f.setRestitution(restitution);
		
		Filter filter = new Filter();
		filter.groupIndex = Desktop.layers.get(parent.getLayer()).collisionGroup;
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
	

	
	public void update() {
		support = false;
		touching = false;
	}
	
	public void remove() {
		box.getWorld().destroyBody(box);
	}
	
	public void setPosition(float x, float y) {
		box.setTransform(x, y, box.getAngle());
	}

	public void setAngle(float a) {
		box.setTransform(box.getPosition(), a);
	}
	
	@Override
	public void write(Json json) {
		json.writeValue("shape", shape);
		json.writeValue("type", type);
		json.writeValue("allLayers", allLayers);
		
	}
	@Override
	public void read(Json json, JsonValue jsonData) {
		shape = json.readValue(T.class, jsonData.get("shape"));
		type = json.readValue(T.class, jsonData.get("type"));
		allLayers = jsonData.getBoolean("allLayers", false);
	}
	
}
