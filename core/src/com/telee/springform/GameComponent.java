package com.telee.springform;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;

class GameComponent extends DefaultInterface implements Serializable{
	protected GameObject parent;
	float ox, oy;
	
	GameComponent() {
		super();
		clss = this.getClass().toString();
		ox = 0;
		oy = 0;
	}
	GameComponent(String _clss) {
		this();
		clss = _clss;
	}
	void setParent(GameObject _parent) {
		this.parent = _parent;
	}
	
	@Override
	public void write(Json json) {
		json.writeValue(ox);
		json.writeValue(oy);
		
	}
	@Override
	public void read(Json json, JsonValue jsonData) {
		ox = jsonData.getFloat("ox");
		oy = jsonData.getFloat("oy");
		
	}
}
