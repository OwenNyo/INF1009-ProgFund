package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;

public class Player extends Entity{

		/// Class Attributes
		private float Speed;
		private int Health;
		
		
		
		//	Constructors
		public Player() {
			this.Speed = 0;
			this.Health = 0;
		}
		
		// Player to earn points via pellet eating -> win cond = survive longer than other player while stacking
		public Player(String type, String filename, float x, float y, float speed, int health, float height, float width) {
			super(type, filename, x, y, width, height);
			this.Speed = speed;
			this.Health = health;
		}
		
		
		
		//	Getters and Setters
		public float getSpeed() {
			return Speed;
		}
		public void setSpeed(float speed) {
			Speed = speed;
		}
		public int getHealth() {
			return Health;
		}
		public void setHealth(int health) {
			Health = health;
		}
		
		
		
		// Class Methods
		public void PlayerPoints(int points) {
			System.out.println("Player <> earned points");
		}
		
		public void PlayerDamageTaken(int damage) {
			Health -= damage;
			
			if (Health != 0) {
				System.out.println("Damage Taken by Player, Health left:" + getHealth());
			}
			else {
				System.out.println("Player has lost all his health!");
			}
		}
		
		public boolean collidedWithGhost (Ghost ghost) {
			Rectangle playerBounds = getBoundingRectangle();
	        Rectangle ghostBounds = ghost.getBoundingRectangle();

	        return playerBounds.overlaps(ghostBounds);
		}
		
		public void checkGhostCollision (Ghost ghost) {
			if (collidedWithGhost(ghost)) {
				System.out.println("Ghost Collision detected");
				PlayerDamageTaken(10);
				ghost.GenerateSpawnPoint(getX(), getY());
			}
		}
		
		public boolean collidedWithCollectible(Collectible c) {
			Rectangle playerBounds = getBoundingRectangle();
	        Rectangle collectibleBounds = c.getBoundingRectangle();

	        return playerBounds.overlaps(collectibleBounds);
	    }
		
		public boolean checkCollectibleCollision(Collectible collectibles[]) {
			for (Collectible c : collectibles) {
				if(collidedWithCollectible(c)) {
					System.out.println("Pellet Collision detected");
					c.resetPosition(getX(), getY());
					return true;				
				}
			}
			return false;
	    }
		
		
		
		
}
