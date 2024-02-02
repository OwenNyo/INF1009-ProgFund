package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Entity {
	
	// Class Attributes
	private String Type;
	private Texture Tex;
	private SpriteBatch batch;
	private float X;
	private float Y;
	
	
	// Constructors
	public Entity() {
		this.Type = null;
		this.Tex = null;
		this.X = 0;
		this.Y = 0;
	}
	
	
	public Entity(String t, String filename, float x, float y) {
		this.Type = t;
		this.Tex = new Texture(Gdx.files.internal(filename));
		this.X = x;
		this.Y = y;
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
	
	
	// Class | Abstract Methods
	public void draw() {
		
		// Initialize batch 
		batch = new SpriteBatch();
		batch.begin();
		// Draw with width and height of 50
		batch.draw(this.Tex, this.X, this.Y, 50, 50);
		batch.end();
	}
	
	
}