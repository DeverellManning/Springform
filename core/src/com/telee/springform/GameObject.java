package com.telee.springform;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;

public class GameObject extends DefaultInterface implements Serializable{
	//Geometry
	protected float x,y;
	protected float w, h; //Half Width and Half Height
	protected LayerName layer;
	protected float angle;
	
	//Data
	String name;
	
	public float vx;
	public float vy;
	
	T facing;
	
	/** Remove Flag */
	private boolean remove;
	/** Do not save objects with this flag */
	boolean noSave;
	/** Object is invisible */
	boolean hidden;
	
	//Components
	Sprite sprite;
	public PhysicsBody body;
	/*Particle_Emitter aura;
	Eblock_Controller guide;*/
	
	GameObject() {
		super();
		x = 0;
		y = 0;
		w = 1;
		h = 1;
		vx = 0;
		vy = 0;
		layer = LayerName.main;
		facing = T.D_LEFT;
		
		remove = false;
		noSave = false;
		hidden = false;
		
		setup();
	}

	public GameObject(Vector2 pos, Vector2 size, LayerName _l, String texture) {
		this();
		sprite = new Sprite(texture, 1);
		body = new PhysicsBody(T.CS_AABB, T.CT_DYNAMIC);
		
		x=pos.x;
		y=pos.y;
		w=size.x;
		h=size.y;
		layer = _l;
		
		setup();
	}
	
	public GameObject(float _x, float _y, LayerName _l, Sprite _s, PhysicsBody _b) {
		this();
		sprite = _s;
		body = _b;
		
		x=_x;
		y=_y;
		layer = _l;
		
		setup();
	}
	
	public GameObject(float _x, float _y, Sprite _s, PhysicsBody _b) {
		this(_x, _y, LayerName.main, _s, _b);
	}
	
	//After Constructor setup
	public void setup() {
		Util.log("w: " + w + "h: " + h);
		if (sprite != null) {
			sprite.setParent(this);
			sprite.setup();
		}
		if (body != null) {
			body.setParent(this);
			body.setup();
		}
		/*if (guide != null) {
			guide.setParent(this);
		}
		if (aura != null) {
			aura.setParent(this);
		}*/
	}
	
	//Update method
	public void update() {
		if (body != null) {body.update();}
		if (sprite != null) {sprite.update();}
		/* if (aura != null) {aura.update();}
		if (guide != null) {guide.update();}
		*/
	}
	
	//Draw Method
	public void draw() {
		Matrix4 transform = new Matrix4();
		transform.translate(x, y, 0);
		transform.rotate(new Quaternion().set(new Vector3(0, 0, 1), angle));
		Render.sprite.setTransformMatrix(transform);
		
		if (sprite != null) {sprite.draw();}
		//if (aura != null) {aura.draw();}
		if(Key.Down(Keys.SHIFT_LEFT) && Key.Down(Keys.NUM_1)) {
			Render.shape.circle(0, 0, 4);
		}
	}

	public boolean doRemove() {return remove;}
	void remove() {
		if (body != null) body.remove();
		remove = true;
	}
	
	public float getX () {return x;}
	public float getY () {return y;}
	public float getRotation () {return angle;}

	void setPosition(float x, float y) {
		if (body != null) {
			body.setPosition(x, y);
		}
		
		this.x = x;
		this.y = y;
	}
	
	void setAngle(float a) {
		if (body != null) {
			body.setAngle(a);
		}
		
		this.angle = a;
	}
	
	public float getWidth () {return w;}
	public float getHeight () {return h;}
	
	void scaledSpriteRatio (float scale) {
		w = (sprite.tw/sprite.th)*scale;
		h = 1*scale;
		
	}
	
	public void snap() {
		Util.log("x: " + x + "Snapped: " + Util.toGrid(x));
		x = Util.toGrid(x);
		y = Util.toGrid(y);
	}
	
	public LayerName getLayer () {return layer;}
	
	
	/* Serializable Interface */
	@Override
	public void write(Json json) {
		json.writeValue("x", x);
		json.writeValue("y", y);
		json.writeValue("w", w);
		json.writeValue("h", h);
		json.writeValue("angle", angle);
		json.writeValue("layer", layer);
		
		json.writeValue("vx", vx);
		json.writeValue("vy", vy);
		json.writeValue("facing", facing);
		json.writeValue("hidden", hidden);
		json.writeValue("noSave", noSave);
		
		if (sprite != null)
			json.writeValue("sprite", sprite);
		if (body != null)
			json.writeValue("body", body);
		
	}

	@Override
	public void read(Json json, JsonValue jsonData) {
		x = jsonData.getFloat("x");
		y = jsonData.getFloat("y");
		w = jsonData.getFloat("w");
		h = jsonData.getFloat("h");
		angle = jsonData.getFloat("angle");
		layer = json.readValue(LayerName.class, jsonData.get("layer"));
		
		vx = jsonData.getFloat("vx");
		vy = jsonData.getFloat("vy");
		//jsonData.getFloat("facing", facing);
		hidden = jsonData.getBoolean("hidden");
		noSave = jsonData.getBoolean("noSave", false);
		
		sprite = json.readValue(Sprite.class, jsonData.get("sprite"));
		body = json.readValue(PhysicsBody.class, jsonData.get("body"));
		
		setup();
	}
	
}

/*
class GameObject extends Default_Interface {
	GameObject(JSONObject jc) {
	  //this.mass = jc.getFloat("mass");
	  layer = jc.getInt("layer");
	  facing = jc.getInt("facing");

	  //Load Body
	  JSONObject jBody = jc.getJSONObject("body");
	  if (jBody != null) {
	  switch(jBody.getString("class")) {
	case "Physics_Body":body = new Physics_Body(jBody);break;
	case "Fan_Physics": body = new Fan_Physics(jBody);break;
	case "Portal_Physics":  body = new Portal_Physics(jBody);break;
	case "Kabob_Physics":   body = new Kabob_Physics(jBody);break;
	  }
	  }

	  //Load Guide
	  JSONObject jGuide = jc.getJSONObject("guide");
	  if (jGuide != null) {
	  switch(jGuide.getString("class")) {
	  //case "Basic_Controller":  guide = new Basic_Controller(jGuide);break;
	  case "Eblock_Controller": guide = new Eblock_Controller(jGuide);break;
	  case "Enemy_AI": 									guide = new Enemy_AI(jGuide);break;
	  }
	  }

	}
*/
