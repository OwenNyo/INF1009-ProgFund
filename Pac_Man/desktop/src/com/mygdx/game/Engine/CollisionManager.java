package com.mygdx.game.Engine;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Engine.IOManager;

public class CollisionManager {
    
    // Manager Initialization
    private IOManager ioManager = new IOManager();
    
    // Fields
    private String lastCollidedPlanetName = "";

    // Logic to check for collision with Ghost
    public static boolean collidedWithEnemy(Player player, Enemy enemy) {
        Rectangle playerBounds = player.getBoundingRectangle();
        Rectangle ghostBounds = enemy.getBoundingRectangle();

        return playerBounds.overlaps(ghostBounds);
    }
    
    // Logic to handle collision with Ghost
    public void checkEnemyCollision(Player player, Enemy enemy) {
        if (collidedWithEnemy(player, enemy)) {
            System.out.println("Ghost Collision detected");
            player.PlayerDamageTaken(enemy.getDamage());
            enemy.GenerateSpawnPoint(player.getX(), player.getY());
            
            // Play sound effect
            ioManager.playSE();
        }
    }

    // Logic to check for collision with Pellet
    public static boolean collidedWithCollectible(Player player, Collectible c) {
        Rectangle playerBounds = player.getBoundingRectangle();
        Rectangle collectibleBounds = c.getBoundingRectangle();
        return playerBounds.overlaps(collectibleBounds);
    }

    // Logic to handle collision with Planet / Station / Asteroid
    public boolean checkCollectibleCollision(Player player, Collectible collectibles[]) {        
        for (Collectible c : collectibles) {
            if (collidedWithCollectible(player, c)) {
                
                // Handle collision based on collectible type
                if (c.getType().equals("asteroid")) {
                	
                	// Generate new spawn point for object
                    c.GenerateSpawnPoint(player.getX(), player.getY());
                    
                    // Player Buff / Debuff
                    player.PlayerSpeedReduced();
                    
                    return true;
                    
                } 
                else if (c.getType().equals("spaceStation")) {
                	
                	// Generate new spawn point for object
                    c.GenerateSpawnPoint(player.getX(), player.getY());
                    
                    // Player Buff / Debuff
                    player.PlayerHealthHealed(10);
                    
                    // Play collect sound effect
                    ioManager.playSECollect(); 
                    
                    return true;
                } 
                else {
                	
                	// Generate new spawn point for object
                    c.GenerateSpawnPoint(player.getX(), player.getY());
                    
                    // Play collect sound effect
                    ioManager.playSECollect(); 
                    
                    // Store collided planet's name
                    lastCollidedPlanetName = c.getType(); 
                    
                    return true;
                }
            }
        }
        return false;
    }
    
    // Getter for last collided planet's name
    public String getLastCollidedPlanetName() {
        return lastCollidedPlanetName;
    }
}
