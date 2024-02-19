package com.telee.springform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

class Sprite extends GameComponent {
	Texture texture;
	String texturePath;
	
	float scale;
	float w, h;
	int frameCount, cframe;
	float tw;
	float th;
	
	/** Zero parameter constructor for loading from json */
	Sprite() {
		super();
		scale = 1;
		tw = 0;
		th = 0;
		texture = null;
	}
	
	Sprite(String _texture, float _scale) {
		super("Sprite");
		
		texturePath = _texture;
		if (Gdx.files.internal(_texture).exists())
			this.texture = new Texture((Gdx.files.internal(_texture)));
		
		if (Gdx.files.absolute(_texture).exists())
			this.texture = new Texture((Gdx.files.absolute(_texture)));
		
		this.scale = _scale;
		
		this.frameCount = 1;
		this.cframe = 0;
		
		
		this.w = 1;
		this.h = 1;
		
		this.tw=texture.getWidth();
		this.th=texture.getHeight();
	}
	
	void setup() {
		this.w = parent.w;
		this.h = parent.h;
	}
	
	void draw() {
		
		//if (parent.guide != null && parent.guide.redTimer >= 0) {
		//	Render.sprite.setColor(Color.RED);
		//	rect(0,0, this.w*this.scale, this.h*this.scale);
		//}
		
		if(Key.Down(49) && Key.Down(16)) { //Shift + 1
			//push();
			//fill(T.C_YELLOW);
			//rect(0,0,this.tw*this.scale, this.th*this.scale);
			//pop();
		} else {
			if(parent.facing == T.D_LEFT) {
				Render.sprite.draw(texture, w*-1f, h*-1f, w*2f, h*2f,
						0, 0, (int) tw, (int) th, false, false);
				/*Render.sprite.draw(texture, w*scale*-0.5f, h*scale*-0.5f, w*scale, h*this.scale,
						0, 0, (int) w, (int) h, false, false);*/
				/*image(texlib.get(texture), this.w*this.scale*-0.5, this.h*this.scale*-0.5, this.w*this.scale, this.h*this.scale,
				Math.floor(this.w), 0, 0, Math.floor(this.h));*/
			} else {
				Render.sprite.draw(texture, w*-1f, h*-1f, w*2f, h*2f,
						0, 0, (int) tw, (int) th, true, false);
				/*image(texlib.get(texture), this.w*this.scale*-0.5, this.h*this.scale*-0.5, this.w*this.scale, this.h*this.scale,
				0, 0, floor(this.w), floor(this.h));*/
			}
			
			
		}
	}
	
	@Override
	public void write(Json json) {
		json.writeValue("texturePath", texturePath);
	}
	@Override
	public void read(Json json, JsonValue jsonData) {
		texturePath = jsonData.getString("texturePath");
		if (Gdx.files.internal(texturePath).exists())
			this.texture = new Texture((Gdx.files.internal(texturePath)));
		
		if (Gdx.files.absolute(texturePath).exists())
			this.texture = new Texture((Gdx.files.absolute(texturePath)));
	}
}
