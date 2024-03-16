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
		//FileHandle dirdle = Gdx.files.absolute(path);
		
		FileHandle objectJson = dataDir.child("/meta-fs" + path + "/objects.json");
		if (! objectJson.exists())
			return;
		
		objs.clear();
		
		Json json = new Json();
		try {
			objs = json.fromJson(ObjectList.class, objectJson);
		} catch (SerializationException ex) {
			Util.log(ex.getMessage());
			Util.log(ex.getStackTrace().toString());
			objs.clear();
		}
		
	}
}
