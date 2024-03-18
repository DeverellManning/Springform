package com.telee.springform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.SerializationException;

public class VistaLoader {

	protected ObjectList objs;
	FileHandle dataDir;
	
	public VistaLoader() {
		objs = new ObjectList();
		dataDir = Gdx.files.external("./.local/share/springform");
		
	}

	public void readObjectList(String path) {
		FileHandle objectJson = dataDir.child("/meta-fs" + path + "/objects.json");
		if (! objectJson.exists())
			return;
		
		objs.clear();
		
		Json json = new Json();
		try {
			objs = json.fromJson(ObjectList.class, objectJson);
		} catch (SerializationException ex) {
			Util.log("Failed to load object list: " + path);
			Util.log(ex.getMessage());
			ex.printStackTrace();
			objs.clear();
		}
		
	}
}
