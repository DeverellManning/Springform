package com.telee.springform;

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
	static void log(String message) {
		log(message, Priority.debug);
	}
	
	
	int toGrid(float x) {
		return (int) Math.floor(x);
	}
	float toWorld(int x) {
		return x;
	}
}
