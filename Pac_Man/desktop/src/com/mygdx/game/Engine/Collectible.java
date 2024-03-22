package com.mygdx.game.Engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Collectible extends Entity {
	
		// Static Variables
		int NoSpawnRadius = 100;
	    private float radius;
	    private Color color;
	    private ShapeRenderer shapeRenderer;
		private SpriteBatch batch;
		private float Speed;
		
		 // Constructors
		 public Collectible() {
			 super();
		}
		 
		 public Collectible(String type, float x, float y, Color color, float radius, float speed, boolean AIControlled) {
			 super(type, x, y, AIControlled);
			 this.radius = radius;
		     this.color = color;
		}


//		public Collectible(String type, String filename, float x, float y, float height, float width, boolean AIControlled) {
//			super(type, filename, x, y, height, width, AIControlled);
//		}
		
		public Collectible(String type, String filename, float x, float y, float speed, float height, float width, boolean AIControlled) {
			super(type, filename, x, y, height, width, speed, AIControlled);
			this.Speed = speed;
		}
		 
		public void GenerateSpawnPoint(float playerX, float playerY) {
		    Random rand = new Random();
		    System.out.println("Generate the collectible spawning position to not spawn on top of the player or other collectibles");

		    // Generate a min and max pos, prevent collectibles from spawning in this range
		    float minX = playerX - NoSpawnRadius;
		    float maxX = playerX + NoSpawnRadius;
		    float minY = playerY - NoSpawnRadius;
		    float maxY = playerY + NoSpawnRadius;

		    // Initialize x and y coordinates to generate spawn location
		    int x = 0;
		    int y = 0;

		    // Adjust the range for x and y to be within the screen boundaries and not spawn on top of other collectibles
		    do {
		        x = rand.nextInt(1500);
		        y = rand.nextInt(900);
		    } while ((x > minX && x < maxX && y > minY && y < maxY));

		    // Set the spawn location after confirming not within range of player or other collectibles
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
		 
		public void draw() {
			// Initialize batch 
			batch = new SpriteBatch();
			batch.begin();
					
			// Ensure it doesn't spawn outside the boundary
			float clampedX = MathUtils.clamp(getX(), 0, Gdx.graphics.getWidth() - super.getWidth());
			float clampedY = MathUtils.clamp(getY(), 0, Gdx.graphics.getHeight() - super.getHeight());
				    
			// Draw with width and height of 50
			batch.draw(super.getTex(), clampedX, clampedY, super.getWidth(), super.getHeight());
			batch.end();
		}
		  
}
