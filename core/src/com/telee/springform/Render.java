package com.telee.springform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
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

		FileHandleResolver resolver = new InternalFileHandleResolver();
		assets.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
		assets.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

		FreeTypeFontLoaderParameter arial10 = new FreeTypeFontLoaderParameter();

		// This is the file that needs to exist in the assets directory.
		arial10.fontFileName = "./assets/fonts/PatrickHand-Regular.ttf";
		arial10.fontParameters.size = 40;

		// There is no file named arial10.ttf. This is just an identifier for the asset manager.
		// The .ttf extension is important, because it tells the asset manager which loader to use.
		assets.load("patrick10.ttf", BitmapFont.class, arial10);
		assets.finishLoading();


		bfont = assets.get("patrick10.ttf", BitmapFont.class);

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

	public static void text(String t, int x, int y) {
		bfont.draw(sprite, t, y, x);
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
