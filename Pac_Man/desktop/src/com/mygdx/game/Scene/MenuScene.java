package com.mygdx.game.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.GameMaster;

public class MenuScene extends ScreenAdapter {

    private GameMaster gameMaster;
    private SceneManager sceneManager;
    private SpriteBatch batch;
    private Texture menuTexture;
    private Stage stage;

    public MenuScene(GameMaster gameMaster, SceneManager sceneManager) {
        this.gameMaster = gameMaster;
        this.sceneManager = sceneManager;
        
        batch = new SpriteBatch();
        // Load menu screen texture
        menuTexture = new Texture("menu_screen.png"); 

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Setup button style and add it to the stage
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        // Set font style, default font is Arial
        textButtonStyle.font = new BitmapFont();
        // Set text colour
        textButtonStyle.fontColor = Color.RED;
        TextButton playButton = new TextButton("PLAY", textButtonStyle);
        // Set position of the play button
        playButton.setPosition(Gdx.graphics.getWidth() / 2 - playButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 - playButton.getHeight() / 2);
        // Add On Click Listener function for the Play Button
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	sceneManager.setGameScreen();
            }
        });

        // Add play button to stage
        stage.addActor(playButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(menuTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void dispose() {
        // Properly dispose of resources
        batch.dispose();
        menuTexture.dispose();
        stage.dispose();
    }
}
