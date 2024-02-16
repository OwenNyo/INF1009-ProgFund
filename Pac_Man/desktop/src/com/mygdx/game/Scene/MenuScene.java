package com.mygdx.game.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
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
    	
    	// Initialize Managers
        this.gameMaster = gameMaster;
        this.sceneManager = sceneManager;
        
        // Initialize New Batch
        batch = new SpriteBatch();
        
        // Load menu screen texture
        menuTexture = new Texture("Icy_background.jpg"); 

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Load atlas file to create skin
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("freezing-ui.atlas"));
        Skin skin = new Skin(Gdx.files.internal("freezing-ui.json"), atlas);
        
        // Setup button style and add it to the stage
        TextButton.TextButtonStyle playButtonStyle = skin.get("default", TextButton.TextButtonStyle.class);
        TextButton playButton = new TextButton("PLAY", playButtonStyle);
        playButton.setPosition(Gdx.graphics.getWidth() / 2 - playButton.getWidth() / 2, Gdx.graphics.getHeight() * 3 / 4 - playButton.getHeight() / 2);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sceneManager.setGameScreen();
            }
        });
        stage.addActor(playButton);
        
        // Setup Options button
        TextButton.TextButtonStyle optionsButtonStyle = skin.get("default", TextButton.TextButtonStyle.class);
        TextButton optionsButton = new TextButton("OPTIONS", optionsButtonStyle);
        optionsButton.setPosition(Gdx.graphics.getWidth() / 2 - optionsButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 - optionsButton.getHeight() / 2);
        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Add code to handle options button click
            }
        });
        stage.addActor(optionsButton);
        
        // Setup Exit button
        TextButton.TextButtonStyle exitButtonStyle = skin.get("default", TextButton.TextButtonStyle.class);
        TextButton exitButton = new TextButton("EXIT", exitButtonStyle);
        exitButton.setPosition(Gdx.graphics.getWidth() / 2 - exitButton.getWidth() / 2, Gdx.graphics.getHeight() / 4 - exitButton.getHeight() / 2);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit(); // Close the application when exit button is clicked
            }
        });
        stage.addActor(exitButton);
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
