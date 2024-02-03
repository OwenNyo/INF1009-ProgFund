package com.mygdx.game;

import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;

public class collectible extends Entity {
	
	 private float radius;
	 private Color color;
	 private ShapeRenderer shapeRenderer;
	 private Texture texture;
	 
	 // Default Constructor
	 public collectible(String type, String fileName, float x, float y, float height, float width) {
		 super(type, fileName, x, y, height, width);
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
	 
}
