package com.mygdx.game.Engine;

import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class Collectible extends Entity {

	    private float radius;
	    private Color color;
	    private ShapeRenderer shapeRenderer;
		private SpriteBatch batch;
		private float Speed;
	 
		 // Constructors
		 public Collectible() {
			 super();
		}
		 
		 public Collectible(String type, float x, float y, Color color, float radius, boolean AIControlled) {
			 super(type, x, y, AIControlled);
			 this.radius = radius;
		     this.color = color;
		}


		public Collectible(String type, String filename, float x, float y, float height, float width, boolean AIControlled) {
			super(type, filename, x, y, height, width, AIControlled);
		}
		
		public Collectible(String type, String filename, float x, float y, float speed, float height, float width, boolean AIControlled) {
			super(type, filename, x, y, height, width, AIControlled);
			this.Speed = speed;
		}
		 
		 public void resetPosition(float posX, float posY) {
			 Random random = new Random();
				
		     float randomX = random.nextInt(Gdx.graphics.getWidth());
		     float randomY = random.nextInt(Gdx.graphics.getHeight());
		     
		     if(randomX != posX && randomY != posY && isValidPosition(randomX, randomY, posX, posY))
		     {
			     super.setX(randomX);
			     super.setY(randomY);
		     }
		     
		 }
		 
		 private boolean isValidPosition(float newX, float newY, float posX, float posY) {
			    // You can adjust these margins if needed
			    float marginX = 50;
			    float marginY = 50;
		
			    return newX >= marginX && newX <= Gdx.graphics.getWidth() - marginX &&
			           newY >= marginY && newY <= Gdx.graphics.getHeight() - marginY &&
			           (newX != posX || newY != posY);
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
		 
//		 public void draw() {
//		    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//		    shapeRenderer.setColor(color);
//		    shapeRenderer.circle(getX(), getY(), radius);
//		    shapeRenderer.end();
//		 }
		 
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
