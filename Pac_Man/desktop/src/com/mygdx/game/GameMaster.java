package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.Scene.GameScene;
import com.mygdx.game.Scene.MenuScene;
import com.mygdx.game.Scene.SceneManager;

public class GameMaster extends Game {
	
	// Initialize Scene Manager to handle all
	private SceneManager sceneManager;

    @Override
    public void create() {
    	// Initialize the SceneManager
        sceneManager = new SceneManager(this);
        // Set the initial screen to the menu scene
        sceneManager.setMenuScreen();
    }
}
