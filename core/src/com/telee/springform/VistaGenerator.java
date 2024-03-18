package com.telee.springform;

import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.telee.springform.components.PhysicsBody;
import com.telee.springform.components.Sprite;
import com.telee.springform.objects.Icon;

public class VistaGenerator {

	public VistaGenerator() {
		// TODO Auto-generated constructor stub
	}
	
	public void baseGen(String path) {
		FileHandle dir = Gdx.files.absolute(path);
		
		for (FileHandle f : dir.list()) {
			if (f.isDirectory())
				continue;
			
			String ext = f.extension();
			
			if (! ext.contentEquals("desktop")) {
				String icon="./assets/textures/objects/filetype/file.png";
				String exec = "";
				
				if (ext.matches("txt|c|css|h|cpp|java|hpp|js")) {
					icon="./assets/textures/objects/filetype/text.png";
				} else if (ext.matches("sh")) {
					icon="./assets/textures/objects/filetype/script.png";
					exec=f.path();
				} else if (ext.matches("png|jpg")) {
					icon="./assets/textures/objects/filetype/image.png";
				} else if (ext.matches("mp3|wav")) {
					icon="./assets/textures/objects/filetype/audio.png";
				}
				Util.log("Name: " + f.name() + " Icon: " + icon);
				
				Desktop.add(new Icon((float) Math.random()*50-25, (float) Math.random()*10+2, exec, icon));
				continue;
			}
					
			CharBuffer content = CharBuffer.allocate(1000);
			try {
				f.reader(Charset.defaultCharset().name()).read(content);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Util.log(f.name());
			//Util.log(content.toString());
			
			String icon = "system-run";
			String exec = "";
			
			String cont = String.valueOf(content.array());
			
			for (String l : cont.split("\n")) {
				if (l.startsWith("Exec=")) {
					exec=l.replace("Exec=", "");
					continue;
				}
				if (l.startsWith("Icon=")) {
					icon=l.replace("Icon=", "");
					continue;
				}
			}
			
			icon = Util.locateIcon(icon, "/usr/share/icons/gnome/32x32/categories/applications-other.png");
			Util.log("Exec: " + exec + ", Icon: " + icon);
			
			Desktop.add(new Icon((float) Math.random()*50-25, (float) Math.random()*10+2, exec, icon));
		}
		
		Desktop.add(new GameObject(-10, 20, LayerName.front, new Sprite("textures/Enemy.png", 0.9f), new PhysicsBody(T.CT_DYNAMIC, T.CS_AABB)));
		
		GameObject ground = new GameObject(new Vector2(0, -60), new Vector2(1000, 60), LayerName.main, "textures/ground.png");
		PhysicsBody groundBody = new PhysicsBody(T.CT_STATIC, T.CS_CIRCLE, true);
		ground.body = groundBody;
		ground.setup();
		
		Desktop.add(ground);
        for (int i = -100; i<100;i++) {
        	/*for (int d = -2; d>-13; d=d-2) {
        		Desktop.add(new Block(i*2, d));
        	}*/
        	
        	//Desktop.add(new GameObject())
        	
        	if (Math.random() > 0.8) {
        		Desktop.add(new GameObject(i*2, 0, LayerName.back, new Sprite("textures/bush.png", 0.9f), null));
        	}
        }
	}

}
