package com.mygdx.game.Engine;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Settings.GameSettings;

public class AIManager {
    
    // Fields
    private Enemy ghost;
    private float playerX;
    private float playerY;
    private float asteroidX;
    private float asteroidY;
    private float asteroidSpeed;
    private Collectible asteroid;
    
    // Constructors
    public AIManager(Enemy ghost, float playerX, float playerY) {
        this.ghost = ghost;
        this.playerX = playerX;
        this.playerY = playerY;
    }
    
    public AIManager(Collectible asteroid, float asteroidX, float asteroidY, float asteroidSpeed) {
        this.asteroid = asteroid;
        this.asteroidX = asteroidX;
        this.asteroidY = asteroidY;
        this.asteroidSpeed = asteroidSpeed;
    }

    // Methods

    // Handle movement for the ghost enemy
    public void handleMovement() {
        // Calculate the direction vector towards the player
        Vector2 playerPosition = new Vector2(playerX, playerY);
        Vector2 ghostPosition = new Vector2(ghost.getX(), ghost.getY());
        Vector2 direction = playerPosition.sub(ghostPosition).nor();
        
        // Move the ghost towards the player
        float speedMultiplier = GameSettings.getInstance().getSpeedMultiplier(); // Get speed from GameSettings
        Vector2 velocity = direction.scl(ghost.getSpeed() * speedMultiplier * Gdx.graphics.getDeltaTime());
        ghostPosition.add(velocity);
        
        // Update the ghost's position
        ghost.setX(ghostPosition.x);
        ghost.setY(ghostPosition.y);
    }
    
    // Handle movement for the asteroid collectible
    public void handleMovement(float speed) {
        Random random = new Random();

        // Move the asteroid
        asteroidX -= speed;
        asteroidY -= speed;
        asteroid.setX(asteroid.getX() - speed);
        asteroid.setY(asteroid.getY() - speed);
        
        // Reset position if asteroid reaches edge
        if(asteroid.getX() == 0) {
            float randomX = random.nextInt(Gdx.graphics.getWidth());
            asteroid.setX(randomX);
        } else if (asteroid.getY() == 0) {
            asteroid.setY(750); // Reset Y position if asteroid reaches top
        }
    }
}
