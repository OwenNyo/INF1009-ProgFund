package com.mygdx.game.Engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class FirstPlayerMovement implements PlayerMovement {
	 @Override
	 public void handleMovement(Player player) {
		// Check if UP key is pressed and within screen bounds
	        if (Gdx.input.isKeyPressed(Keys.UP) && player.getY() < Gdx.graphics.getHeight() - player.getHeight()) {
	            player.setY(player.getY() + player.getSpeed() * Gdx.graphics.getDeltaTime());
	        }
	        
	        // Check if DOWN key is pressed and within screen bounds
	        if (Gdx.input.isKeyPressed(Keys.DOWN) && player.getY() > 0) {
	            player.setY(player.getY() - player.getSpeed() * Gdx.graphics.getDeltaTime());
	        }
	        
	        // Check if LEFT key is pressed and within screen bounds
	        if (Gdx.input.isKeyPressed(Keys.LEFT) && player.getX() > 0) {
	            player.setX(player.getX() - player.getSpeed() * Gdx.graphics.getDeltaTime());
	        }
	        
	        // Check if RIGHT key is pressed and within screen bounds
	        if (Gdx.input.isKeyPressed(Keys.RIGHT) && player.getX() < Gdx.graphics.getWidth() - player.getWidth()) {
	            player.setX(player.getX() + player.getSpeed() * Gdx.graphics.getDeltaTime());
	        }
	 }
}
