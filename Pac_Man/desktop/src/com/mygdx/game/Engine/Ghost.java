package com.mygdx.game.Engine;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Ghost extends Entity{
	
		// Static Variables
		int NoSpawnRadius = 30;

		// Class Attributes
		private float Speed;
		private int Damage;	
		
		// Constructors
		public Ghost() {
			super();
			this.Speed = 0;
			this.Damage = 0;
		}
		public Ghost(String type, String filename, float x, float y, float speed, int damage, float height, float width, boolean AIControlled) {
			super(type, filename, x, y, height, width, AIControlled);
			this.Speed = speed;
			this.Damage = damage;
		}
		
		
		// Getters and Setters
		public float getSpeed() {
			return Speed;
		}
		public void setSpeed(float speed) {
			Speed = speed;
		}
		public int getDamage() {
			return Damage;
		}
		public void setDamage(int damage) {
			Damage = damage;
		}
		
		
		
		
		// Class Methods
		public void GenerateSpawnPoint(float playerX, float playerY) {
		    Random rand = new Random();
		    System.out.println("Generate the ghost spawning position to not spawn on top of the player");

		    // Generate a min and max pos, prevent ghosts from spawning in this range
		    float minX = playerX - NoSpawnRadius;
		    float maxX = playerX + NoSpawnRadius;
		    float minY = playerY - NoSpawnRadius;
		    float maxY = playerY + NoSpawnRadius;

		    // Ensure that ghosts do not spawn within a 50px radius of the player
		    // Can reuse this function to respawn ghost after death
		    
		    // Initialize x and y coordinates to generate spawn location
		    int x = 0;
		    int y = 0;
		    int screenWidth = 800;  // Assuming 800 is the screen width
		    int screenHeight = 600; // Assuming 600 is the screen height

		    // Adjust the range for x and y to be within the screen boundaries
		    do {
		        x = rand.nextInt(screenWidth);
		        y = rand.nextInt(screenHeight);
		    } while (x > minX && x < maxX && y > minY && y < maxY);

		    // Set the spawn location after confirming not within range of player
		    super.setX(x);
		    super.setY(y);
		}
		
		// Movement Logic
		public void AIMove(float X, float Y) {
		    // Calculate the direction vector towards the player
		    Vector2 playerPosition = new Vector2(X, Y);
		    Vector2 ghostPosition = new Vector2(super.getX(), super.getY());
		    Vector2 direction = playerPosition.sub(ghostPosition).nor();

		    // Move the ghost towards the player
		    float speedMultiplier = 10.0f; // Adjust speed if needed
		    Vector2 velocity = direction.scl(getSpeed() * speedMultiplier * Gdx.graphics.getDeltaTime());
		    ghostPosition.add(velocity);

		    // Update the ghost's position
		    super.setX(ghostPosition.x);
		    super.setY(ghostPosition.y);
		}
		
		public void UserMove() {
			
		}
}
