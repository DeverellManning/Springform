package com.telee.springform;

class GameComponent extends DefaultInterface {
	GameObject parent;
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
}
