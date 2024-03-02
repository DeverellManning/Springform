package com.telee.springform;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.io.IOException;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;

@SuppressWarnings("unused")
public class Springform extends ApplicationAdapter {
	@Override
    public void create () {
		Config.load("./");
		
		Render.init();
        Gdx.graphics.setWindowedMode(Render.vasX, Render.vasY);
        Gdx.input.setCursorPosition(Render.hvasX, Render.hvasY);
        
        Gdx.input.setInputProcessor(new SpringformInputProccesor());
        
        Desktop.init();
        
        Desktop.load("/home/deverell-manning/Desktop");
    }

    @Override
    public void render () {
    	Render.assets.update(17);
    	
    	Desktop.update();

    	Render.begin();
    	
    	Desktop.draw();
    	
        Render.end();
    }
    
    public void resize(int x, int y) {
    	Render.resize(x, y);
    }
    
	@Override
	public void dispose () {
		Util.log("All Done.  Cleaning up...");
		Render.dispose();
	}
}
