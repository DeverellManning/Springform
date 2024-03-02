package com.telee.springform;

public class Objects {
	public static GameObject dirt;
	
	public static void load() {
		dirt = new GameObject() {{
			x = 0;
		}};
	}
	
}
