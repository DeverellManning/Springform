package com.telee.springform.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.telee.springform.Key;
import com.telee.springform.Render;
import com.telee.springform.T;

public class Sprite extends GameComponent {
	Texture texture;
	String texturePath;
	
	float scale;
	float w, h;
	int frameCount, cframe;
	public float tw = 0;
	public float th = 0;
	
	
	
	/** Zero parameter constructor for loading from json */
	Sprite() {
		super();
		scale = 1;
		texture = null;
	}
	
	public Sprite(String _texture, float _scale) {
		super("Sprite");
		texturePath = _texture;
		
		Render.assets.load(texturePath, Texture.class);
		
		Render.assets.finishLoading();
		
		texture = Render.assets.get(texturePath, Texture.class);
		
		this.scale = _scale;
		
		this.frameCount = 1;
		this.cframe = 0;
		
		
		this.w = 1;
		this.h = 1;
		
		this.tw = texture.getWidth();
		this.th = texture.getHeight();
	}
	
	public void setup() {
		w = parent.getWidth();
		h = parent.getHeight();
		
		texture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
	}
	
	public void draw() {
		
		//if (parent.guide != null && parent.guide.redTimer >= 0) {
		//	Render.sprite.setColor(Color.RED);
		//	rect(0,0, this.w*this.scale, this.h*this.scale);
		//}
		
		if(Key.Down(49) && Key.Down(16)) { //Shift + 1
		//fill(T.C_YELLOW);
		//rect(0,0,this.tw*this.scale, this.th*this.scale);
		} else {
			/*if(parent.facing == T.D_LEFT) {
				Render.sprite.draw(texture, -w, -h, w*2f, h*2f,
						0, 0, (int) tw, (int) th, false, false);
			} else {
				Render.sprite.draw(texture, -w, -h, w*2f, h*2f,
						0, 0, (int) tw, (int) th, true, false);
			}*/
			
			if(parent.facing == T.D_LEFT) {
				Render.sprite.draw(texture, -w, -h, w*2f, h*2f,
						0, 0, Math.round(w*64), Math.round(h*64), false, false);
			} else {
				Render.sprite.draw(texture, -w, -h, w*2f, h*2f,
						0, 0, Math.round(w*64), Math.round(h*64), true, false);
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
		
		Render.assets.load(texturePath, Texture.class);
		Render.assets.finishLoading();
		
		texture = Render.assets.get(texturePath, Texture.class);
		
		this.tw=texture.getWidth();
		this.th=texture.getHeight();
	}
	
	void setTint () {
		Render.sprite.setColor(null);
	}
}
