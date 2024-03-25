package com.mygdx.game.Engine;

import java.util.Random;

public class EntityFactory {
	 // Method to create a player entity
    public static Player createPlayer(float x, float y, float speed, int health, int points) {
        return new Player("player", "spaceDog.png", x, y, speed, health, points, 100, 100, false, true);
    }

    // Method to create an enemy entity
    public static Enemy createEnemy(float x, float y, float speed, int damage) {
        return new Enemy("enemy", "blackhole.png", x, y, speed, damage, 80, 80, true);
    }

    // Method to create a collectible entity
    public static Collectible createCollectible(String name, String texturePath, float x, float y, int size) {
        return new Collectible(name, texturePath, x, y, 0, size, size, false);
    }

    // Method to create an asteroid entity
    public static Collectible createAsteroid(float x, float y) {
        return new Collectible("asteroid", "asteroid.png", x, y, 1, 80, 80, false);
    }

    // Method to create a random collectible entity
    public static Collectible createRandomCollectible(float x, float y, int index) {
    	    
    	    String[] planetsName = {"Earth", "Uranus", "Moon", "Mercury",
                    "Venus", "Mars", "Jupiter", "Saturn", "Neptune"};
            String[] planets = {"earth.png", "uranus.png", "moon.png",
                    "mercury.png", "venus.png", "mars.png", "jupiter.png", "saturn.png", "neptune.png"};
            int[] planetSize = {240, 280, 220, 190, 230, 210, 320, 300, 260};

    	    // Create the collectible for the current index
    	    Collectible collectible = new Collectible(planetsName[index], planets[index], x, y, 0, planetSize[index], planetSize[index], false);

    	    return collectible;
    }

    // Method to create a space station entity
    public static Collectible createSpaceStation(float x, float y) {
        return new Collectible("spaceStation", "space_station.png", x, y, 1, 80, 80, false);
    }
}
