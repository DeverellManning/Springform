package com.telee.springform;

public class Layers {
	public static Layer backdrop, back, main, pointer, front;
	
	private static Layer [] values = {backdrop, back, main, pointer, front};
	
	static void init() {
		backdrop = new Layer(0);
		back = new Layer(1);
		main = new Layer(2);
		pointer = new Layer(3);
		front = new Layer(4);
	}
	
	static Layer[] values() {
		return values;
	}
	
	public static Layer before(Layer l) {
		return values[l.index - 1];
	}
	
	public static Layer at(int i) {
		return values[i];
	}
}
