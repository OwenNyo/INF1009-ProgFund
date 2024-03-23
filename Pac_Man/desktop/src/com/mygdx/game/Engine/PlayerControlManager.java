package com.mygdx.game.Engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class PlayerControlManager {
    
	// Reference to the controlled player
    private Player player; 
    
    // Constructor to set the player being controlled
    public PlayerControlManager(Player player) {
        this.player = player;
    }
    
    // Method to handle player movement based on player's type (first or second player)
    public void handleMovement() {
        if (player.isFirstPlayer()) {  	
        	// Handle movement for the first player
            handleFirstPlayerMovement(); 
        } 
        else {        	
        	// Handle movement for the second player
            handleSecondPlayerMovement(); 
        }
    }
    
    // Method to handle movement controls for the first player
    private void handleFirstPlayerMovement() {
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
    
    // Method to handle movement controls for the second player
    private void handleSecondPlayerMovement() {
        // Check if W key is pressed and within screen bounds
        if (Gdx.input.isKeyPressed(Keys.W) && player.getY() < Gdx.graphics.getHeight() - player.getHeight()) {
            player.setY(player.getY() + player.getSpeed() * Gdx.graphics.getDeltaTime());
        }
        
        // Check if S key is pressed and within screen bounds
        if (Gdx.input.isKeyPressed(Keys.S) && player.getY() > 0) {
            player.setY(player.getY() - player.getSpeed() * Gdx.graphics.getDeltaTime());
        }
        
        // Check if A key is pressed and within screen bounds
        if (Gdx.input.isKeyPressed(Keys.A) && player.getX() > 0) {
            player.setX(player.getX() - player.getSpeed() * Gdx.graphics.getDeltaTime());
        }
        
        // Check if D key is pressed and within screen bounds
        if (Gdx.input.isKeyPressed(Keys.D) && player.getX() < Gdx.graphics.getWidth() - player.getWidth()) {
            player.setX(player.getX() + player.getSpeed() * Gdx.graphics.getDeltaTime());
        }
    }
}
