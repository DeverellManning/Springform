package com.telee.springform;

public class Block extends GameObject{
    int size;
    int xSpeed;
    int ySpeed;

    public Block(int x, int y) {
    	super(x, y, new Sprite("textures/ground.png", 1), new PhysicsBody(T.CT_STATIC, T.CS_AABB, true));
        this.size = size;
    }
}
