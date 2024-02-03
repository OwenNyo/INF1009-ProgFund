package com.mygdx.game;

import java.awt.Rectangle;

import com.badlogic.gdx.graphics.Texture;

public class Player extends Entity{

		/// Class Attributes
		private float Speed;
		private int Health;
		private int Points;
		private Texture texture; 
		
		
		
		//	Constructors
		public Player() {
			this.Speed = 0;
			this.Health = 0;
			this.Points = 0;
		}
		
		// Player to earn points via pellet eating -> win cond = survive longer than other player while stacking
		public Player(String type, String filename, float x, float y, float speed, int health, int points, float height, float width) {
			super(type, filename, x, y, width, height);
			this.Speed = speed;
			this.Health = health;
			this.Points = points;
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
		public int getPoints() {
			return Points;
		}
		public void setPoints(int points) {
			Points = points; 
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
			 Rectangle playerBounds = new Rectangle((int) super.getX() - 25, (int) super.getY() - 25, 50, 50);
			 Rectangle ghostBounds = new Rectangle((int) ghost.getX() - 25, (int) ghost.getY() - 25, 50, 50);

			 return playerBounds.intersects(ghostBounds);

		}
		
		public void checkGhostCollision (Ghost ghost) {
			if (collidedWithGhost(ghost)) {
				System.out.println("collision detected");
				PlayerDamageTaken(10);
				ghost.GenerateSpawnPoint(getX(), getY());
			}
		}
		
		
		
}
