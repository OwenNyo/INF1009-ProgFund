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
import com.mygdx.game.Engine.SceneManager;
import com.mygdx.game.GameMaster;

public class MenuScene extends ScreenAdapter {

    // Reference to GameMaster and SceneManager instances
    private final GameMaster gameMaster;
    private final SceneManager sceneManager;

    // SpriteBatch for rendering textures, Stage for UI elements, and Texture for menu background
    private final SpriteBatch batch;
    private final Stage stage;
    private final Texture menuTexture;

    public MenuScene(GameMaster gameMaster, SceneManager sceneManager) {
        this.gameMaster = gameMaster;
        this.sceneManager = sceneManager;

        // Initialize rendering components
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());

        // Set input processor to the stage to handle UI input
        Gdx.input.setInputProcessor(stage);

        // Load menu background texture
        menuTexture = new Texture("Icy_background.jpg");

        // Load atlas file to create skin for UI elements
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("freezing-ui.atlas"));
        Skin skin = new Skin(Gdx.files.internal("freezing-ui.json"), atlas);

        // Get the default button style from the skin
        TextButton.TextButtonStyle buttonStyle = skin.get("default", TextButton.TextButtonStyle.class);

        // Create and configure PLAY button
        TextButton playButton = createButton("PLAY", buttonStyle, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() * 3 / 4);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Switch to the game screen when PLAY button is clicked
                sceneManager.setGameScreen();
            }
        });
        stage.addActor(playButton);

        // Create and configure OPTIONS button
        TextButton optionsButton = createButton("OPTIONS", buttonStyle, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Add code to handle options button click
            }
        });
        stage.addActor(optionsButton);

        // Create and configure EXIT button
        TextButton exitButton = createButton("EXIT", buttonStyle, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 4);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Close the application when EXIT button is clicked
                Gdx.app.exit();
            }
        });
        stage.addActor(exitButton);
    }

    // Helper method to create a TextButton with specified text, style, and position
    private TextButton createButton(String text, TextButton.TextButtonStyle style, float x, float y) {
        TextButton button = new TextButton(text, style);
        button.setPosition(x - button.getWidth() / 2, y - button.getHeight() / 2);
        return button;
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render menu background
        batch.begin();
        batch.draw(menuTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        // Update and render the stage
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void dispose() {
        // Dispose of resources when the screen is disposed
        batch.dispose();
        menuTexture.dispose();
        stage.dispose();
    }
}
