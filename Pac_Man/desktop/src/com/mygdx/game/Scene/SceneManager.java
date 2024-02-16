package com.mygdx.game.Scene;

import com.badlogic.gdx.Game;
import com.mygdx.game.GameMaster;

public class SceneManager {
	
    private Game game;

    public SceneManager(Game game) {
        this.game = game;
    }

    public void switchToMenu() {
    	// Dispose of the current screen if it exists
        if (game.getScreen() != null) {
            game.getScreen().dispose();
        }
        // Set current screen to menu screen
        game.setScreen(new MenuScene((GameMaster) game));
    }

    public void switchToGame() {
    	// Dispose of the current screen if it exists
        if (game.getScreen() != null) {
            game.getScreen().dispose();
        }
        // Set current screen to game screen
        game.setScreen(new GameScene());
    }
}
