package com.mygdx.game.Settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GameSettings {
	
	// Singleton instance
    private static GameSettings instance;
    
    // Speed Attribute
    private float speedMultiplier;
    
    // Preferences object to save/load settings
    private Preferences prefs;

    public GameSettings() {
    	// Initialize preferences with a unique name
        prefs = Gdx.app.getPreferences("GameSettings");
        
        // Load settings from preferences
        loadSettings();
    }

    // Static method to get the singleton instance of GameSettings
    public static GameSettings getInstance() {
        if (instance == null) {
            instance = new GameSettings();
        }
        return instance;
    }

    // Getter method for speed
    public float getSpeedMultiplier() {
        return speedMultiplier;
    }
    
    // Getter method for difficulty
    public String getDifficulty() {
        if (speedMultiplier == 2.5f) {
            return "Easy";
        } else if (speedMultiplier == 10.0f) {
            return "Medium";
        } else {
            return "Hard";
        }
    }

    // Setter method for difficulty
    public void setDifficulty(String difficulty) {
        switch (difficulty) {
            case "Easy":
                speedMultiplier = 5.0f;
                break;
            case "Medium":
                speedMultiplier = 10.0f;
                break;
            case "Hard":
                speedMultiplier = 30.0f;
                break;
            default:
                speedMultiplier = 10.0f; // Default to Medium
                break;
        }
        saveSettings();
    }

    // Method to save settings to preferences
    private void saveSettings() {
    	// Save speed multiplier
        prefs.putFloat("speedMultiplier", speedMultiplier);
        prefs.flush(); // Flush to save the changes to file
    }

    private void loadSettings() {
    	// Load speed multiplier from preferences, defaulting to 10.0f if not found
        speedMultiplier = prefs.getFloat("speedMultiplier", 10.0f);
    }
}
