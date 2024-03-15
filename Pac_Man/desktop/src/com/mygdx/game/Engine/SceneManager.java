package com.mygdx.game.Engine;

import com.mygdx.game.GameMaster;
import com.mygdx.game.Scene.EndScene;
import com.mygdx.game.Scene.GameScene;
import com.mygdx.game.Scene.MenuScene;
import com.mygdx.game.Scene.OptionScene;
<<<<<<< Updated upstream
=======
import com.mygdx.game.Scene.PlanetScene;
import com.mygdx.game.Scene.TriviaScene;
>>>>>>> Stashed changes

public class SceneManager {

    // Singleton instance
    private static SceneManager instance;

    // Reference to the GameMaster
    private GameMaster gameMaster;

    // Private constructor to prevent direct instantiation
    private SceneManager(GameMaster gameMaster) {
        this.gameMaster = gameMaster;
    }

    // Static method to get the singleton instance of SceneManager
    public static SceneManager getInstance(GameMaster gameMaster) {
        // Create a new instance if it doesn't exist
        if (instance == null) {
            instance = new SceneManager(gameMaster);
        }
        return instance;
    }

    // Method to set the menu screen
    public void setMenuScreen() {
        MenuScene menuScene = new MenuScene(gameMaster, this);
        // Set the screen using the GameMaster
        gameMaster.setScreen(menuScene);
    }
    
    public void setOptionScreen() {
    	// Initialize IOManager
        IOManager ioManager = new IOManager();
        
    	OptionScene optionScene = new OptionScene(gameMaster, this, ioManager);
    	// Set the screen using the GameMaster
    	gameMaster.setScreen(optionScene);
    }

    // Method to set the game screen
    public void setGameScreen() {
        GameScene gameScene = new GameScene(gameMaster, this);
        // Set the screen using the GameMaster
        gameMaster.setScreen(gameScene);
    }
    
<<<<<<< Updated upstream
=======
    // Method to set the planet fact screen
    public void setPlanetScreen(String planetName) {
    	// Store current scene
    	previousScene = gameMaster.getScreen();
    	// Initialize PlanetScene
        PlanetScene planetScene = new PlanetScene(gameMaster, this, planetName);
        // Set the screen using the GameMaster
        gameMaster.setScreen(planetScene);
        
        
    }
    
    // Method to set game screen to previous instance
    public void resumeGameScreen() {
        if (previousScene != null) {
            if (previousScene instanceof GameScene) {
                ((GameScene) previousScene).updateGameState(GameScene.GameState.RUNNING);
            }
            // Set the screen using the GameMaster
            gameMaster.setScreen(previousScene);
        }
        else {
        	System.out.println("Error, previous screen is null");
        }
    }
    
    //Method to to set the trivia scene (after game, before end) 
    public void setTriviaScreen(Player player) {
    	// Initialize EndScene
    	TriviaScene triviaScene = new TriviaScene(gameMaster, this, player);
    	System.out.println("ehre");
    	// Set the screen using the GameMaster
    	gameMaster.setScreen(triviaScene);
    }
    
    // Method to set the end screen
>>>>>>> Stashed changes
    public void setEndScreen(int finalScore) {
    	EndScene endScene = new EndScene(gameMaster, this, finalScore);
    	// Set the screen using the GameMaster
    	gameMaster.setScreen(endScene);
    }
}
