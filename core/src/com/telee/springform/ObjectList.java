package com.telee.springform;

import java.util.ArrayList;

//Is this right?
//Once used ArrayList, now TreeSet, nevermind ArrayList, for now...
public class ObjectList extends ArrayList<GameObject> {
	private static final long serialVersionUID = 3853790869391239213L;
	
	public boolean add(GameObject o) {
		if (! contains(o)) {
			super.add(o);
			return true;
		} else {
			return false;
		}
	}
}
