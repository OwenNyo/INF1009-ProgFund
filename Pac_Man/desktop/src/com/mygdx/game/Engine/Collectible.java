package com.mygdx.game.Engine;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class Collectible extends Entity {
	
		// Fields
		private int NoSpawnRadius = 100;
		private float Radius;
		private float Speed;
		private Color Color;
		private ShapeRenderer shapeRenderer;
		private SpriteBatch batch;
		
		// Constructors
		public Collectible() {
			super();
		}
			 
		public Collectible(String type, float x, float y, Color color, float radius, float speed, boolean AIControlled) {
			super(type, x, y, AIControlled);
			this.Radius = radius;
			this.Color = color;
		}
	
		public Collectible(String type, String filename, float x, float y, float speed, float height, float width, boolean AIControlled) {
			super(type, filename, x, y, height, width, speed, AIControlled);
			this.Speed = speed;
		}
			 
		// Methods
		public void generateSpawnPoint(float playerX, float playerY) {
			Random rand = new Random();
	
			// Generate min and max positions to prevent spawning near player
			float minX = playerX - NoSpawnRadius;
			float maxX = playerX + NoSpawnRadius;
			float minY = playerY - NoSpawnRadius;
			float maxY = playerY + NoSpawnRadius;
	
			// Initialize spawn coordinates
			int x = 0;
			int y = 0;
	
			// Generate spawn location until it's outside player range
			do {
				x = rand.nextInt(1500);
				y = rand.nextInt(900);
			} while ((x > minX && x < maxX && y > minY && y < maxY));
	
			// Set spawn location
			super.setX(x);
			super.setY(y);
			rand = null;
		}
			 
		public void setShape(ShapeRenderer shape) {
			shapeRenderer = shape;
		}
			 
		// Getters and Setters
		public float getSpeed() {
			return Speed;
		}
	
		public void setSpeed(float speed) {
			Speed = speed;
		}
			 
		// Draw method
		public void draw() {
			// Initialize batch 
			batch = new SpriteBatch();
			batch.begin();
			
			// Clamp position to screen boundaries
			float clampedX = MathUtils.clamp(getX(), 0, Gdx.graphics.getWidth() - super.getWidth());
			float clampedY = MathUtils.clamp(getY(), 0, Gdx.graphics.getHeight() - super.getHeight());
					    
			// Draw with width and height of 50
			batch.draw(super.getTex(), clampedX, clampedY, super.getWidth(), super.getHeight());
			batch.end();
		}
}
