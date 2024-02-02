package com.mygdx.game;

import java.awt.Rectangle;

public class Player extends Entity{

		/// Class Attributes
		private float Speed;
		private int Health;
		private int Points;
		
		
		
		
		//	Constructors
		public Player() {
			this.Speed = 0;
			this.Health = 0;
			this.Points = 0;
		}
		
		// Player to earn points via pellet eating -> win cond = survive longer than other player while stacking
		public Player(String type, String filename, float x, float y, float speed, int health, int points) {
			super(type, filename, x, y);
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
		public int PlayerPoints() {
			System.out.println("Player <> earned points");
			return 0;
		}
		
		public void PlayerDamageTaken(int damage) {
			System.out.println("Damage Taken by Player");
			Health -= damage;
			if (Health == 0) {
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
			}
		}
		
}
