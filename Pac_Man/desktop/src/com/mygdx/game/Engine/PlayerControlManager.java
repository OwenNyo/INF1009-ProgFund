package com.mygdx.game.Engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class PlayerControlManager {
    
	// Reference to the controlled player
    private Player player; 
    
    // Player movement strategy
    private PlayerMovement movement;
    
    // Constructor to set the player being controlled and the movement strategy
    public PlayerControlManager(Player player, PlayerMovement movement) {
        this.player = player;
        this.movement = movement;
    }
    
    // Method to handle player movement using the assigned strategy
    public void handleMovement() {
    	movement.handleMovement(player);
    }
}


