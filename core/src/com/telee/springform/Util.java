package com.telee.springform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.telee.springform.components.PhysicsBody;

/** Static Utility class */
public final class Util {
	enum Priority {
		debug,
		normal,
		warning,
		error
	}
	
	
	static void log(String message, Priority priority) {
		System.out.println(message);
	}
	public static void log(String message) {
		log(message, Priority.debug);
	}
	
	
	public static int toGrid(float x) {
		return (int) Math.floor(x);
	}
	
	float toWorld(int x) {
		return x;
	}
	
	
	static String locateIcon(String name, String def) {
		String path = "";
		if (! Gdx.files.absolute(name).exists()) {
			for (FileHandle l1 : Gdx.files.absolute("/usr/share/icons/hicolor/").list()) {
				if (! l1.isDirectory())
					continue;
				for (FileHandle l2 : Gdx.files.absolute("/usr/share/icons/hicolor/" + l1.name()).list()) {
					if (! l2.isDirectory())
						continue;
					for (FileHandle l3 : Gdx.files.absolute("/usr/share/icons/hicolor/" + l1.name() + "/" + l2.name()).list()) {
						if (l3.nameWithoutExtension().contentEquals(name) && ! l3.extension().contentEquals("svg")) {
							path = l3.path();
							break;
						}
					}
				}
			}
		} else {
			path = name;
		}
		
		if (Gdx.files.absolute(path).extension().contentEquals("xpm")) {
			path = def;
		}
		
		if (! Gdx.files.absolute(path).exists()) {
			log("Unable to locate icon '" + name + "'");
			path = def;
			
		}
		
		return path;
	}
	
	private static QueryCallback getFirstCallback = new QueryCallback () {
		public boolean reportFixture(Fixture f) {
			GameObject o = ((PhysicsBody) f.getBody().getUserData()).parent;
			if (o == Desktop.pointer) return true;
			//grabbed = o;
			return false;
		}
	};
	
	//GameObject getFirst()
}
