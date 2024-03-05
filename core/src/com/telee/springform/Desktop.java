package com.telee.springform;

import java.util.ArrayList;
import java.util.TreeMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.telee.springform.pointer.Pointer;


public class Desktop {
	//Main Object List
	private static ObjectList objs;
	public static TreeMap<LayerName,Layer> layers;
	public static ArrayList<Camera> cams;
	
	//Tools for vista interaction
	static VistaSaver vistaSaver;
	static VistaLoader vistaLoader;
	static VistaGenerator vistaGen;
	
	
	//Object References
	public static Pointer pointer;
	public static Camera cam;
	
	//Physics
	public static World pworld;
	static Box2DDebugRenderer drawpworld;
	
	//
	static String path;
	static float gravity;
	static Color skyColor;
	
	static float mx, my, angle;
	static boolean paused;
	static boolean breakLoop;

	
	static void init() {
		Box2D.init();
		
		//Set up physics world
		pworld = new World(new Vector2(0,-100), true);
		drawpworld = new Box2DDebugRenderer();
		
		//Initialize object lists
		objs = new ObjectList();
		cams = new ArrayList<Camera>();
		layers = new TreeMap<LayerName, Layer>();
		
		vistaSaver = new VistaSaver();
		vistaLoader = new VistaLoader();
		vistaGen = new VistaGenerator();
		
		//Add all layers. (This should go somewhere else)
		layers.put(LayerName.backdrop, new Layer(LayerName.backdrop));
		layers.put(LayerName.back, new Layer(LayerName.back));
		layers.put(LayerName.main, new Layer(LayerName.main));
		layers.put(LayerName.pointer, new Layer(LayerName.pointer));
		layers.put(LayerName.front, new Layer(LayerName.front));
		
		//Unimportant
		skyColor = Color.SKY;
		gravity = -40;
		paused = false;
		
		breakLoop = false;
	}
	
	
	static void update() {
		Vector3 mv = cam.cam.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(), 0));
		mx = mv.x;
		my = mv.y;
		
		float x = Gdx.input.getX() - Gdx.graphics.getWidth()/2;
		float y = Gdx.input.getY() - Gdx.graphics.getHeight()/2;;
		angle = (float) Math.atan2(y, x);
		
		if (! paused) {
			//pworld.step(Gdx.graphics.getDeltaTime(), 5, 5);
			pworld.step(1/60f, 4, 2);
		}
		
		if (! paused) {
			for (int i = objs.size()-1; i >= 0; i--) {
				GameObject o = objs.get(i);
				
				o.update();
				
				if (o.y < -10 && o != pointer)
					o.remove();
				
				if (breakLoop)
					break;
				
				if (o.doRemove()) {
					remove(o);
					continue;
				}
			}
		}
		
		for (int i = cams.size()-1; i >= 0; i--) {
			cams.get(i).update();
		}
		
		if (breakLoop) {
			breakLoop = false;
			//game.delta = 1;
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.S))
			save();
		
		if (Gdx.input.isKeyJustPressed(Keys.G))
			vistaGen.baseGen(path);
		
		if (Gdx.input.isKeyJustPressed(Keys.C))
			clear();
		
		if (Gdx.input.isKeyJustPressed(Keys.L))
			load(path);
	}
	
	@SuppressWarnings("unused")
	static void draw() {
		ScreenUtils.clear(skyColor);
		
		//stroke(C_RED);
		Render.shape.line(0, 0, 7, 0);
		//stroke(C_BLUE);
		Render.shape.line(0, 0, 0, 7);
		
		for (Layer l : layers.values()) {
			l.draw();
		}
		
		if (Key.Down(Input.Keys.V))
			drawpworld.render(pworld, cam.cam.combined);
	
		if (Key.Down(Keys.SHIFT_LEFT)) {
			Render.shape.setColor(Color.FIREBRICK.r, Color.FIREBRICK.g, Color.FIREBRICK.b, 0.5f);
			Render.shape.rect(mx-32,my-32, 64, 64);
		}
	}
	
	static void save() {
		vistaSaver.writeVistaData(path);
		vistaSaver.writeObjectList(objs, path);
	}
	
	static void load(String _path) {
		//Check path
		if (! Gdx.files.absolute(_path).isDirectory()) {
			Util.log("Loaded Directory does not exist or is not a directory: " + path);
			return;
		}
		
		Util.log("Loading into directory: " + _path);
		path = _path;
		
		clear();
		
		vistaLoader.readObjectList(path);
		
		for (GameObject o : vistaLoader.objs) {
			if (o.noSave) continue;
			add(o);
		}
		
        Desktop.pointer = new Pointer();
        Desktop.add(Desktop.pointer);
		
		pworld.setGravity(new Vector2(0, gravity));
	}
		
	static void clear() {
		objs.clear();
		cams.clear();
		
		for (Layer l : layers.values()) {
			l.clear();
		}
		
		//Ensure a drone camera
		cams.add(new DroneCamera(0f,0f));
		cam = cams.get(0);
		
		//Clear physics world
		Array<Body> pbodies = new Array<Body>();
		pworld.getBodies(pbodies);
		for (Body b : pbodies) {
			pworld.destroyBody(b);
		}
	}
	
	
	
	public static void add(GameObject o) {
		if (! objs.contains(o)) {
			objs.add(o);
			
			if (o.layer != null) {
				layers.get(o.layer).add(o);
			}
		}
	}
	
	static void remove(GameObject o) {
		if (objs.contains(o)) {
			layers.get(o.layer).remove(o);
			objs.remove(o);
			
		}
	}
}
