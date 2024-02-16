package com.mygdx.game.Scene;
import com.mygdx.game.GameMaster;

public class SceneManager {

    private GameMaster gameMaster;

    public SceneManager(GameMaster gameMaster) {
        this.gameMaster = gameMaster;
    }

    public void setMenuScreen() {
        MenuScene menuScene = new MenuScene(gameMaster, this);
        gameMaster.setScreen(menuScene);
    }

    public void setGameScreen() {
        GameScene gameScene = new GameScene(gameMaster, this);
        gameMaster.setScreen(gameScene);
    }
}
