package com.telee.springform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class Render {
	public static ShapeRenderer shape;
	public static SpriteBatch sprite;
	
	public static AssetManager assets;
	
	static Label l;
	static Label.LabelStyle lstyle;
	static BitmapFont bfont;
	
	public static int vasX, vasY;
	public static int hvasX, hvasY;
	
	static Matrix4 identity = new Matrix4();
	
	static void init() {
		shape = new ShapeRenderer();
		sprite = new SpriteBatch();
		
		assets = new AssetManager();
		
		
		
		bfont = new BitmapFont();
		
		lstyle = new Label.LabelStyle(bfont, new Color());
		l = new Label("", lstyle);
		
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	static void begin() {
    	shape.begin(ShapeRenderer.ShapeType.Filled);
    	sprite.begin();
	}
	
	static void end() {
    	shape.end();
    	sprite.end();
	}
	
	static void transform() {
		Desktop.cam.transform();
	}
	
	static void reset() {
		sprite.setTransformMatrix(identity);
		sprite.setProjectionMatrix(identity);
		shape.setTransformMatrix(identity);
		shape.setProjectionMatrix(identity);
	}
	
	static void text(String t, int x, int y) {
		l.setText(t);
		l.setPosition(x, y);
		//l.draw(sprite, 1.0f);
	}

	public static void resize(int x, int y) {
		vasX = x;
		vasY = y;
		
		hvasX = vasX/2;
		hvasY = vasY/2;
	}
	
	public static void dispose() {
		shape.dispose();
		sprite.dispose();
		assets.dispose();
	}
	
	
}
