package com.mygdx.game.Engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class PlayerControlManager {
    
    private Player player;
    
    public PlayerControlManager(Player player) {
        this.player = player;
    }
    
    public void handlePlayerMovement() {
        if (player.isFirstPlayer()) {
            handleFirstPlayerMovement();
        } else {
            handleSecondPlayerMovement();
        }
    }
    
    private void handleFirstPlayerMovement() {
        if (Gdx.input.isKeyPressed(Keys.UP) && player.getY() < Gdx.graphics.getHeight() - player.getHeight()) {
            player.setY(player.getY() + player.getSpeed() * Gdx.graphics.getDeltaTime());
        }
        
        if (Gdx.input.isKeyPressed(Keys.DOWN) && player.getY() > 0) {
            player.setY(player.getY() - player.getSpeed() * Gdx.graphics.getDeltaTime());
        }
        
        if (Gdx.input.isKeyPressed(Keys.LEFT) && player.getX() > 0) {
            player.setX(player.getX() - player.getSpeed() * Gdx.graphics.getDeltaTime());
        }
        
        if (Gdx.input.isKeyPressed(Keys.RIGHT) && player.getX() < Gdx.graphics.getWidth() - player.getWidth()) {
            player.setX(player.getX() + player.getSpeed() * Gdx.graphics.getDeltaTime());
        }
    }
    
    private void handleSecondPlayerMovement() {
        if (Gdx.input.isKeyPressed(Keys.W) && player.getY() < Gdx.graphics.getHeight() - player.getHeight()) {
            player.setY(player.getY() + player.getSpeed() * Gdx.graphics.getDeltaTime());
        }
        
        if (Gdx.input.isKeyPressed(Keys.S) && player.getY() > 0) {
            player.setY(player.getY() - player.getSpeed() * Gdx.graphics.getDeltaTime());
        }
        
        if (Gdx.input.isKeyPressed(Keys.A) && player.getX() > 0) {
            player.setX(player.getX() - player.getSpeed() * Gdx.graphics.getDeltaTime());
        }
        
        if (Gdx.input.isKeyPressed(Keys.D) && player.getX() < Gdx.graphics.getWidth() - player.getWidth()) {
            player.setX(player.getX() + player.getSpeed() * Gdx.graphics.getDeltaTime());
        }
    }
}
