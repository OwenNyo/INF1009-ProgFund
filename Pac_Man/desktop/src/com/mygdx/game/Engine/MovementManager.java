package com.mygdx.game.Engine;

public class MovementManager {
	
	interface Movable {
		public void UserMove();
		public void AIMove(float X, float Y);
	}
}
