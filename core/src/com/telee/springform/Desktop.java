package com.telee.springform;

import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.TreeMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;


public class Desktop {
	private static ObjectList objs;
	static ArrayList<Camera> cams;
	static TreeMap<LayerName,Layer> layers;
	
	//Object References
	static Pointer pointer;
	static Camera cam;
	
	//Physics
	static World pworld;
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
		pworld = new World(new Vector2(0,-100), true);
		drawpworld = new Box2DDebugRenderer();
		
		
		objs = new ObjectList();
		cams = new ArrayList<Camera>();
		
		layers = new TreeMap<LayerName, Layer>();
		
		layers.put(LayerName.backdrop, new Layer(LayerName.backdrop));
		layers.put(LayerName.back, new Layer(LayerName.back));
		layers.put(LayerName.main, new Layer(LayerName.main));
		layers.put(LayerName.pointer, new Layer(LayerName.pointer));
		layers.put(LayerName.front, new Layer(LayerName.front));
		
		skyColor = Color.SKY;
		gravity = 850;
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
			for (int i = objs.size()-1; i >= 0; i--) {
				GameObject o = objs.get(i);
				
				o.update();
				
				if (o.y < -10)
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
		
		if (! paused) {
			//pworld.step(Gdx.graphics.getDeltaTime(), 5, 5);
			pworld.step(1/60f, 4, 2);
		}
		
	}
	
	@SuppressWarnings("unused")
	static void draw() {
		ScreenUtils.clear(skyColor);
	
		float zoom = cam.zoom;
		float camX = cam.x;
		float camY = cam.y;
		
		//stroke(C_RED);
		//Render.shape.line(0, 0, 100, 0);
		//stroke(C_BLUE);
		//Render.shape.line(0, 0, 0, 100);
		
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
	
	static void load(String _path) {
		path = _path;
		
		clear();
		
		FileHandle dir = Gdx.files.external(path);
		
		for (FileHandle f : dir.list()) {
			if (f.isDirectory())
				continue;
			
			String ext = f.extension();
			if (! ext.contentEquals("desktop"))
				continue;
			
			CharBuffer content = CharBuffer.allocate(1000);
			try {
				f.reader(Charset.defaultCharset().name()).read(content);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Util.log(f.name());
			//Util.log(content.toString());
			
			String icon = "system-run";
			String exec = "";
			
			String cont = String.valueOf(content.array());
			//Util.log(cont);
			
			for (String l : cont.split("\n")) {
				
				if (l.startsWith("Exec=")) {
					exec=l.replace("Exec=", "");
					continue;
				}
				if (l.startsWith("Icon=")) {
					icon=l.replace("Icon=", "");
					continue;
				}
			}
			
			//Util.log("Exec: " + exec + ", Icon: " + icon);
			
			if (! Gdx.files.absolute(icon).exists()) {
				for (FileHandle l1 : Gdx.files.absolute("/usr/share/icons/gnome/").list()) {
					if (! l1.isDirectory())
						continue;
					for (FileHandle l2 : Gdx.files.absolute("/usr/share/icons/gnome/" + l1.name()).list()) {
						if (! l2.isDirectory())
							continue;
						for (FileHandle l3 : Gdx.files.absolute("/usr/share/icons/gnome/" + l1.name() + "/" + l2.name()).list()) {
							//Util.log(l3.nameWithoutExtension());
							if (l3.nameWithoutExtension().contentEquals(icon)) {
								//Util.log(l3.path());
								icon = l3.path();
								break;
							}
						}
					}
				}
			}
			
			if (Gdx.files.absolute(icon).extension().contentEquals("xpm")) {
				icon = "/usr/share/icons/gnome/16x16/categories/applications-other.png";
			}
			
			if (! Gdx.files.absolute(icon).exists()) {
				icon = "/usr/share/icons/gnome/16x16/categories/applications-other.png";
			}
			
			Util.log("Exec: " + exec + ", Icon: " + icon);
			
			//return;
			Desktop.add(new Icon((float) Math.random()*10-5, (float) Math.random()*5+2, exec, icon));
			
		}
		
        for (int i = -100; i<100;i++) {
        	for (int d = -2; d>-13; d=d-2) {
        		Desktop.add(new Block(i*2, d));
        	}
        	
        	if (Math.random() > 0.8) {
        		Desktop.add(new GameObject(i*2, 0, new Sprite("textures/bush.png", 0.9f), new PhysicsBody(T.CT_STATIC, T.CS_AABB)));
        	}
        }
        
        Desktop.pointer = new Pointer();
        Desktop.add(Desktop.pointer);
		
		pworld.setGravity(new Vector2(0, gravity));
		
	}
		
	static void clear() {
		objs.clear();
		cams.clear();
		
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
	
	
	
	static void add(GameObject o) {
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
