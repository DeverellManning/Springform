package com.telee.springform;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class GameObject extends DefaultInterface{
	protected float x,y;
	protected float w, h;
	protected LayerName layer;
	protected float angle;
	
	float vx, vy;
	
	T facing;
	
	/** Flags */
	private boolean remove;
	boolean noSave;
	boolean hidden;
	
	//Components
	Sprite sprite;
	PhysicsBody body;
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

	GameObject(float _x, float _y, LayerName _l, Sprite _s, PhysicsBody _b) {
		this();
		sprite = _s;
		body = _b;
		
		x=_x;
		y=_y;
		layer = _l;
		
		
		setup();
	}
	
	GameObject(float _x, float _y, Sprite _s, PhysicsBody _b) {
		this(_x, _y, LayerName.main, _s, _b);
	}
	
	//After Constructor setup
	void setup() {
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
	void update() {
		if (body != null) {body.update();}
		if (sprite != null) {sprite.update();}
		/* if (aura != null) {aura.update();}
		if (guide != null) {guide.update();}
		*/
	}
	
	//Draw Method
	void draw() {
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


	void remove() {
		if (body != null) body.remove();
		remove = true;
	}
	
	public boolean doRemove() {return remove;}

	float getX () {return x;}
	float getY () {return y;}
	float getRotation () {return angle;}

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
	
	float getWidth () {return w;}
	float getHeight () {return h;}
	
	LayerName getLayer () {return layer;}
	
	
}

/*
class GameObject extends Default_Interface {
	GameObject(float _x, float _y, int _layer, Sprite _sprite) {//NORM
	this();

	x = _x;
	y = _y;
	layer = _layer;

	sprite = _sprite;

	if (_sprite != null && wb.addWithPhys) { // Problem Line
	this.body = new Physics_Body(CT_STATIC, CS_AABB, _sprite.tw*_sprite.scale, _sprite.th*_sprite.scale);
	this.body.setParent(this);

	body.box.setPosition(_x+body.width/2, _y+body.height/2);
	}

	setup();

	}
	GameObject(float _x, float _y, int _layer, Sprite _sprite, Physics_Body _body) {
	this();

	this.x = _x;
	this.y = _y;
	this.layer = _layer;

	this.body = _body;
	this.aura = null;
	this.sprite = _sprite;
	this.guide = null;


	setup();

	}
	GameObject(JSONObject jc) {
	  this();

	  x = jc.getFloat("x");
	  y = jc.getFloat("y");
	  vx = jc.getFloat("vx");
	  vy = jc.getFloat("vy");
	  //this.mass = jc.getFloat("mass");
	  layer = jc.getInt("layer");
	  facing = jc.getInt("facing");


	  //Retreive, Create Sprite
	  JSONObject jSprite = jc.getJSONObject("sprite");
	  if (jSprite != null) {
	switch(jSprite.getString("class")) {
	case "Animated_Sprite": sprite = new Animated_Sprite(jSprite);break;
	case "Sprite": 									sprite = new Sprite(jSprite); break;
	}
	  }

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



	  //load Aura
	  JSONObject jAura = jc.getJSONObject("aura");
	  if (jAura != null) {
	  //aura = new Particle_Emitter(jAura);
	  }


	  setup();

	  }





	  JSONObject saveJSON() {
	JSONObject j = super.saveJSON();
	j.setString("class", "Game_Object");

	j.setFloat("x", x);
	j.setFloat("y", y);
	j.setFloat("vx", vx);
	j.setFloat("vy", vy);
	j.setFloat("w", x);
	j.setFloat("h", y);
	j.setFloat("mass", mass);
	j.setFloat("rotation", mass);
	j.setInt("layer", layer);
	j.setInt("facing", facing);
	j.setBoolean("hidden", hidden);

	if (sprite != null)
	j.setJSONObject("sprite", sprite.saveJSON());

	if (aura != null)
	j.setJSONObject("aura", aura.saveJSON());

	if (guide != null)
	j.setJSONObject("guide", guide.saveJSON());

	if (body != null)
	j.setJSONObject("body", body.saveJSON());

	return j;
	  }
	}
*/
