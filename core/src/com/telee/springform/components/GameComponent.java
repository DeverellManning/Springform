package com.telee.springform.components;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.telee.springform.DefaultInterface;
import com.telee.springform.GameObject;
import com.badlogic.gdx.utils.JsonValue;

public class GameComponent extends DefaultInterface implements Serializable{
	/** The GameObject that owns this component*/
	public GameObject parent;
	/** Offset position */
	float ox, oy;
	
	protected GameComponent() {
		super();
		ox = 0;
		oy = 0;
	}
	
	protected GameComponent(String _clss) {
		this();
	}
	
	public void setParent(GameObject _parent) {
		parent = _parent;
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
