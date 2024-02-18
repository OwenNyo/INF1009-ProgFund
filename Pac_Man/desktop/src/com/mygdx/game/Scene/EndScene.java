package com.mygdx.game.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.GameMaster;
import com.mygdx.game.Engine.SceneManager;

public class EndScene extends ScreenAdapter {
	
    private Stage stage;
    private GameMaster gameMaster;
    private SceneManager sceneManager;
    private int finalScore;
    
    // Background texture
    private Texture backgroundTexture;
    
    // Batch texture
    private SpriteBatch batch;
    
    private BitmapFont scoreFont;
    private String endScore;

    public EndScene(GameMaster gameMaster, SceneManager sceneManager, int finalScore) {
    	
        this.gameMaster = gameMaster;
        this.sceneManager = sceneManager;
        this.finalScore = finalScore;
        
        // Create a new SpriteBatch instance
        batch = new SpriteBatch();

        // Load background texture
        backgroundTexture = new Texture("PacMan_Background.jpg");
        
        this.scoreFont = new BitmapFont();
        this.scoreFont.getData().setScale(3);
        this.endScore = "Game Over!\nScore : " + finalScore;

        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal("freezing-ui.json"));

        // Initialize play again button
        TextButton playAgainButton = new TextButton("Play Again", skin);
        playAgainButton.setPosition(Gdx.graphics.getWidth() / 2 - playAgainButton.getWidth() / 2,
                                    Gdx.graphics.getHeight() / 2 - playAgainButton.getHeight() / 2);
        playAgainButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sceneManager.setGameScreen(); // Reload game scene
            }
        });
        
        // Initialize menu button
        TextButton mainMenuButton = new TextButton("Menu", skin);
        mainMenuButton.setPosition(Gdx.graphics.getWidth() / 2 - mainMenuButton.getWidth() / 2,
                                   Gdx.graphics.getHeight() / 4 - mainMenuButton.getHeight() / 2);
        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sceneManager.setMenuScreen(); // Load menu screen
            }
        });

        // Add buttons to the stage
        stage.addActor(playAgainButton);
        stage.addActor(mainMenuButton);

    }
    
    @Override
    public void render(float delta) {
    	// Clear OpenGL color buffer
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // Draw background texture
        drawBackground();
        
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        
        // Draw player score
        drawPlayerScore();
    }
    
    private void drawBackground() {
    	batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }
    
    private void drawPlayerScore() {
        batch.begin();
        scoreFont.draw(batch, endScore, 390, 660);
        batch.end();
    }
    
    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
        scoreFont.dispose();
    }

}