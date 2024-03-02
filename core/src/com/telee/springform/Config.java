package com.telee.springform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

public class Config {
	static String DataDirectory;
	static String startDirectory;
	static String DesktopDirectory;
	static String TrashDirectory;
	
	static boolean debug;
	static boolean safeMode;
	
	public static void load(String path) {
		FileHandle config = Gdx.files.external(path);
		if (! config.exists() || config.isDirectory()) {
			Util.log("Error: Could not load config.");
			return;
		}
		
		Json json = new Json();
		json.fromJson(Config.class, config);
	}

}
