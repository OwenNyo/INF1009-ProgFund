package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.Scene.SceneManager;
import com.mygdx.game.Scene.MenuScene;

public class GameMaster extends Game {
	
    private SceneManager sceneManager;

    @Override
    public void create() {
    	// Initialize Scene Manager
        this.sceneManager = new SceneManager(this);
        // Set current screen to menu scene
        this.setScreen(new MenuScene(this));
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }
}
