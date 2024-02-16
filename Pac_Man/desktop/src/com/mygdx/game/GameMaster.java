package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.Engine.SceneManager;
import com.mygdx.game.Scene.GameScene;
import com.mygdx.game.Scene.MenuScene;

public class GameMaster extends Game {

    @Override
    public void create() {
    	
    	// Initialize the SceneManager
    	SceneManager sceneManager = SceneManager.getInstance(this);
    	
        // Set the initial screen to the menu scene
        sceneManager.setMenuScreen();
    }
}
