package com.mygdx.game.Engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Engine.MovementManager.Movable;

public abstract class Entity implements Movable{
	
	// Class Attributes
	private String Type;
	private Texture Tex;
	private SpriteBatch batch;
	private float X;
	private float Y;
	private float width;
	private float height;
	private boolean AIControlled;
	
	
	// Constructors
	public Entity() {
		this.Type = null;
		this.Tex = null;
		this.X = 0;
		this.Y = 0;
		this.width = 0;
		this.height = 0;
		this.AIControlled = false;
	}
	
	
	public Entity(String t, String filename, float x, float y, float height, float width, boolean b) {
		this.Type = t;
		this.Tex = new Texture(Gdx.files.internal(filename));
		this.X = x;
		this.Y = y;
		this.height = height;
		this.width = width;
		this.AIControlled = b;
	}
	
	
	// Getters and Setters
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public Texture getTex() {
		return Tex;
	}
	public void setTex(Texture tex) {
		Tex = tex;
	}
	public SpriteBatch getBatch() {
		return batch;
	}
	public void setBatch(SpriteBatch batch) {
		this.batch = batch;
	}
	public float getX() {
		return X;
	}
	public void setX(float x) {
		X = x;
	}
	public float getY() {
		return Y;
	}
	public void setY(float y) {
		Y = y;
	}
	public float getWidth() {
		return width;
	}
	public void setWidth(float w) {
		width = w;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float h) {
		height = h;
	}
	
	public boolean getAIControlled() {
		return AIControlled;
	}

	public void setAIControlled(boolean b) {
		AIControlled = b;
	}
	
	public void UserMove() {
		
	}
	
	public void AIMove(float X, float Y) {
	}
	
	
	// Class | Abstract Methods
	public void draw() {
		// Initialize batch 
		batch = new SpriteBatch();
		batch.begin();
		
		// Ensure it doesn't spawn outside the boundary
		float clampedX = MathUtils.clamp(getX(), 0, Gdx.graphics.getWidth() - this.width);
	    float clampedY = MathUtils.clamp(getY(), 0, Gdx.graphics.getHeight() - this.height);

		// Draw with width and height of 50
		batch.draw(this.Tex, clampedX, clampedY, this.width, this.height);
		batch.end();
		
	}
	
	// Check collision
	public Rectangle getBoundingRectangle() {
		return new Rectangle(getX(), getY(), getHeight(), getWidth());
	}
	
}