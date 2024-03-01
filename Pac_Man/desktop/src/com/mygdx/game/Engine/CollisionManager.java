package com.mygdx.game.Engine;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Engine.IOManager;

public class CollisionManager {
	
	private IOManager ioManager = new IOManager();

	// Logic to check for collision with Ghost
    public static boolean collidedWithEnemy(Player player, Enemy enemy) {
        Rectangle playerBounds = player.getBoundingRectangle();
        Rectangle ghostBounds = enemy.getBoundingRectangle();

        return playerBounds.overlaps(ghostBounds);
    }
    
    // Logic to handle what happens if there is a collision with Ghost
    public void checkEnemyCollision(Player player, Enemy enemy) {
        if (collidedWithEnemy(player, enemy)) {
            System.out.println("Ghost Collision detected");
            player.PlayerDamageTaken(enemy.getDamage());
            enemy.GenerateSpawnPoint(player.getX(), player.getY());
            
            // Play Sound
            ioManager.playSE();
        }
    }

    // Logic to check for collision with Pellet
    public static boolean collidedWithCollectible(Player player, Collectible c) {
        Rectangle playerBounds = player.getBoundingRectangle();
        Rectangle collectibleBounds = c.getBoundingRectangle();
        return playerBounds.overlaps(collectibleBounds);
    }

    // Logic to handle what happens if there is a collision with Pellet
    public boolean checkCollectibleCollision(Player player, Collectible collectibles[]) {
        for (Collectible c : collectibles) {
            if (collidedWithCollectible(player, c)) {
//                System.out.println("Pellet Collision detected");
                c.resetPosition(player.getX(), player.getY());
                
                if(c.getType().equals(""))

                //play collect sound effect
                ioManager.playSECollect(); 
		    
                return true;
            }
        }
        return false;
    }
    
    public boolean checkasteroidCollision(Player player, Collectible collectibles[]) {
    	for (Collectible c : collectibles) {
            if (collidedWithCollectible(player, c)) {
            	if(c.getType().equals("asteroid") || c.getType() == "asteroid"){
            		System.out.println("Asteroid Collision detected");
    				return true;
            	}
		    
            }
        }
        return false;
		}
}

