package com.telee.springform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Camera extends GameObject {
	float zoom, speed;
	
	OrthographicCamera cam;
		
	Camera(float _x, float _y) {
		super();
		x=_x;
		y=_y;
		this.zoom = 0.02f;
		this.speed = 6;
		
		cam = new OrthographicCamera();
		cam.viewportHeight = Gdx.graphics.getHeight();
		cam.viewportWidth = Gdx.graphics.getWidth();
	}
	void transform() {
		cam.update();
		Render.sprite.setProjectionMatrix(cam.combined);
		Render.shape.setProjectionMatrix(cam.combined);
	}
	public void update() {
		cam.translate(-cam.position.x, -cam.position.y);
		cam.translate(this.x, -this.y);
		cam.zoom = zoom;
	}
}
