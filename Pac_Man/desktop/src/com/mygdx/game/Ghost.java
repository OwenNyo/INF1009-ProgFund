package com.mygdx.game;

import java.util.Random;


public class Ghost extends Entity{
	
		// Static Variables
		int NoSpawnRadius = 100;

		// Class Attributes
		private float Speed;
		private int Damage;
		
		
		
		
		// Constructors
		public Ghost() {
			super();
			this.Speed = 0;
			this.Damage = 0;
		}
		public Ghost(String type, String filename, float x, float y, float speed, int damage) {
			super(type, filename, x, y);
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
			//System.out.println("Generate the ghost spawning position to not spawn on top of the player");
			// Generate a min and max pos, prevent ghosts from spawning in this range
			float minX = playerX - NoSpawnRadius;
			float maxX = playerX + NoSpawnRadius;
			float minY = playerY - NoSpawnRadius;
			float maxY = playerY + NoSpawnRadius;

			// Ensure that ghosts do not spawn within a 50px radius of the player
			// Can reuse this function to respawn ghost after death
			
			// Initialize x and y coordinates to generate spawn location
			int x = 0;
			int y = 0 ;
			do {
				x = rand.nextInt(800);
				y = rand.nextInt(600);
			}while ( x > minX && x < maxX && y > minY && y < maxY);
			
			// Set the spawn location after confirming not within range of player
			super.setX(x);
			super.setY(y);
		}
}
