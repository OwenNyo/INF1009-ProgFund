package com.mygdx.game.Engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Entity{
	
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
	
	public Entity(String t, float x, float y, boolean b) {
		this.Type = t;
		this.X = x;
		this.Y = y;
		this.AIControlled = b;
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
	
	// Class | Abstract Methods
	abstract void draw();
	
	// Check collision
	protected Rectangle getBoundingRectangle() {
		return new Rectangle(getX(), getY(), getHeight(), getWidth());
	}
	
}