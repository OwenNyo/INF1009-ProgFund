package com.mygdx.game.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.GameMaster;
import com.mygdx.game.Engine.HUD;
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
    
    private HUD hud;

    public EndScene(GameMaster gameMaster, SceneManager sceneManager, int finalScore) {
    	
        this.gameMaster = gameMaster;
        this.sceneManager = sceneManager;
        this.finalScore = finalScore;
        
        // Create a new SpriteBatch instance
        batch = new SpriteBatch();
        hud = new HUD();

        // Select the appropriate background image based on the final score
        if (finalScore < 200) {
            backgroundTexture = new Texture("ZeroPointsBg.png");
        } else if (finalScore < 400) {
            backgroundTexture = new Texture("NoviceAstronautBg.png");
        } else if (finalScore < 700) {
            backgroundTexture = new Texture("AstronautVeteranBg.png");
        } else {
            backgroundTexture = new Texture("MasterAstronomerBg.png");
        }
        
        this.scoreFont = new BitmapFont();
        this.scoreFont.getData().setScale(3);
        

        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Load atlas file to create skin for UI elements
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("freezing-ui.atlas"));
        Skin skin = new Skin(Gdx.files.internal("freezing-ui.json"), atlas);

        // Get the default button style from the skin
        TextButton.TextButtonStyle buttonStyle = skin.get("default", TextButton.TextButtonStyle.class);

        // Initialize play again button
        TextButton playAgainButton = new TextButton("Play Again", buttonStyle);
        playAgainButton.setPosition(Gdx.graphics.getWidth() / 2 - playAgainButton.getWidth() / 2,
                                    Gdx.graphics.getHeight() / 2 - playAgainButton.getHeight() / 2);
        playAgainButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sceneManager.setGameScreen(); // Reload game scene
            }
        });
        
        // Initialize menu button
        TextButton mainMenuButton = new TextButton("Menu", buttonStyle);
        mainMenuButton.setPosition(Gdx.graphics.getWidth() / 2 - mainMenuButton.getWidth() / 2,
                                   Gdx.graphics.getHeight() / 4 - mainMenuButton.getHeight() / 2);
        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sceneManager.setMenuScreen(); // Load menu screen
            }
        });

        // Calculate the button positions to be side by side at the bottom
        float buttonWidth = playAgainButton.getWidth();
        float buttonSpacing = 20; // Space between buttons
        float buttonX = (Gdx.graphics.getWidth() - (2 * buttonWidth + buttonSpacing)) / 2;
        playAgainButton.setPosition(buttonX, 50);
        mainMenuButton.setPosition(buttonX + buttonWidth + buttonSpacing, 50);

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
        hud.drawBackground(backgroundTexture);
        
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        
     // Draw player score and game over text side by side
        drawPlayerScore();
    }
    
    private void drawPlayerScore() {
        // Calculate width of game over and score texts
        GlyphLayout gameOverLayout = new GlyphLayout(scoreFont, "Game Over!");
        GlyphLayout scoreLayout = new GlyphLayout(scoreFont, "Score: " + finalScore);

        float totalWidth = gameOverLayout.width + scoreLayout.width + 20; // 20 for padding
        float startingX = (Gdx.graphics.getWidth() - totalWidth) / 2;

        batch.begin();
        scoreFont.draw(batch, "Game Over!", startingX, Gdx.graphics.getHeight() - 50);
        scoreFont.draw(batch, "Score: " + finalScore, startingX + gameOverLayout.width + 20, Gdx.graphics.getHeight() - 50);
        batch.end();
    }
    
    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
        scoreFont.dispose();
        backgroundTexture.dispose();
    }

}