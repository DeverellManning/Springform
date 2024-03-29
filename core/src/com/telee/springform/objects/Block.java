package com.telee.springform.objects;

import com.telee.springform.GameObject;
import com.telee.springform.T;
import com.telee.springform.components.PhysicsBody;
import com.telee.springform.components.Sprite;

public class Block extends GameObject{

    public Block() {}
    
    public Block(float x, float y) {
    	super(x, y, new Sprite("textures/ground.png", 1), new PhysicsBody(T.CT_STATIC, T.CS_AABB, true));
    }
}
