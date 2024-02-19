package com.mygdx.game.Engine;

import com.mygdx.game.GameMaster;
import com.mygdx.game.Scene.EndScene;
import com.mygdx.game.Scene.GameScene;
import com.mygdx.game.Scene.MenuScene;
import com.mygdx.game.Scene.OptionScene;

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
    
    public void setEndScreen(int finalScore) {
    	EndScene endScene = new EndScene(gameMaster, this, finalScore);
    	// Set the screen using the GameMaster
    	gameMaster.setScreen(endScene);
    }
}
