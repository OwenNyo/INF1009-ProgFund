package com.mygdx.game.Engine;

import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Collectible extends Entity {

	    private float radius;
	    private Color color;
	    private ShapeRenderer shapeRenderer;
	 
		 // Constructors
		 public Collectible() {
			 super();
		}
		 
		 public Collectible(String type, float x, float y, Color color, float radius, boolean AIControlled) {
			 super(type, x, y, AIControlled);
			 this.radius = radius;
		     this.color = color;
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
		 
		 public void draw() {
		    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		    shapeRenderer.setColor(color);
		    shapeRenderer.circle(getX(), getY(), radius);
		    shapeRenderer.end();
		 }
		  
}
