package com.telee.springform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

public class VistaSaver {
	FileHandle dataDir;
	
	public VistaSaver() {
		dataDir = Gdx.files.external("./.local/share/springform");
		
	}
	
	public void writeObjectList(ObjectList objs, String path) {
		Json json = new Json();
		String jobjs = json.toJson(objs);
		//Util.log(json.prettyPrint(jobjs));
		
		//Check path
		if (! Gdx.files.absolute(path).isDirectory()) {
			Util.log("Loaded Directory does not exist or is not a directory: " + path);
			return;
		}
		
		//Write File
		dataDir.child("meta-fs/" + path + "/objects.json").writeString(jobjs, false);
	}
	
	public void writeVistaData(String Path) {
		
	}

}
