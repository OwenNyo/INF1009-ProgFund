package com.mygdx.game.Scene;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameMaster;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.mygdx.game.Engine.Collectible;
import com.mygdx.game.Engine.CollisionManager;
import com.mygdx.game.Engine.Entity;
import com.mygdx.game.Engine.EntityManager;
import com.mygdx.game.Engine.Ghost;
import com.mygdx.game.Engine.IOManager;
import com.mygdx.game.Engine.Player;
import com.mygdx.game.Engine.SceneManager;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Engine.Score;
import com.mygdx.game.Engine.TimerClass;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameScene extends ScreenAdapter {

    // Entities
    private Player player;
    private Ghost ghost;
    private Collectible collectibles[];

    // Score
    private Score score;
    
    //Timer
    private TimerClass timer;

    // Entity management
    private EntityManager entityManager;
    private List<Entity> entityList;

    // Managers
    private IOManager ioManager;
    private CollisionManager cManager;

    // GameMaster reference
    private GameMaster gameMaster;

    // Background texture
    private Texture backgroundTexture;
    
    // Batch texture
    private SpriteBatch batch;
    
    // Stage for UI elements
    private Stage overlayStage;

    // Button for play again
    private TextButton playAgainButton, MainMenu;

    public GameScene(GameMaster gameMaster, SceneManager sceneManager) {
        this.gameMaster = gameMaster;
        
        // Create a new SpriteBatch instance
        batch = new SpriteBatch();

        // Load background texture
        backgroundTexture = new Texture("PacMan_Background.jpg");

        // Initialize EntityManager and entities
        entityManager = new EntityManager();
        entityManager.initEntities();
        
        // Fetch Entity List
        entityList = entityManager.getEntityList();
        
        // Initialize Scoring System
        score = new Score();
        
        // Initialize Scoring System
        timer = new TimerClass();

        // Initialize Collision Manager
        cManager = new CollisionManager();

        // Initialize IO Manager
        ioManager = new IOManager();
        
        overlayStage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(overlayStage);
        
    	// Load atlas file to create skin for UI elements
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("freezing-ui.atlas"));
        Skin skin = new Skin(Gdx.files.internal("freezing-ui.json"), atlas);
        TextButton.TextButtonStyle buttonStyle = skin.get("default", TextButton.TextButtonStyle.class);

     // Initialize play again button
        playAgainButton = new TextButton("Play Again", skin);
        playAgainButton.setPosition(Gdx.graphics.getWidth() / 2 - playAgainButton.getWidth() / 2,
                                    Gdx.graphics.getHeight() / 2 - playAgainButton.getHeight() / 2); // Adjusted position
        playAgainButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Reload the game scene
                sceneManager.setGameScreen();
            }
        });

        // Add button to the overlay stage
        overlayStage.addActor(playAgainButton);

        // Initialize menu button
        MainMenu = new TextButton("Menu", skin);
        MainMenu.setPosition(Gdx.graphics.getWidth() / 2 - MainMenu.getWidth() / 2,
                             Gdx.graphics.getHeight() / 4 - MainMenu.getHeight() / 2); // Adjusted position
        MainMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Load the menu screen
                sceneManager.setMenuScreen();
            }
        });

        // Add button to the overlay stage
        overlayStage.addActor(MainMenu);
    }

    @Override
    public void render(float delta) {
        // Clear screen with dark blue background
        ScreenUtils.clear(0 , 0, 0.2f, 1); 

        // Clear OpenGL color buffer
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw background texture
        drawBackground();

        // Draw score and entities
        score.draw();
        timer.draw();
        entityManager.drawEntities();
        entityManager.moveEntities();

        // Create a list to hold the collectibles
        List<Collectible> collectibleList = new ArrayList<>();

        // Iterate through entity list to find collectibles, player, and ghost
        for (Entity entity : entityList) {
            if (entity instanceof Collectible) {
                collectibleList.add((Collectible) entity);
            }
            if (entity instanceof Player) {
                player = (Player) entity;
            } else if (entity instanceof Ghost) {
                ghost = (Ghost) entity;
            }
        }
        
        // Convert collectible list back to an array
        collectibles = collectibleList.toArray(new Collectible[collectibleList.size()]);

        // Check for player collision with collectibles and update score
        if (cManager.checkCollectibleCollision(player, collectibles)) {
            score.calculateScore();
        }

        // Check for collision with ghost
        if (player != null && ghost != null) {
            int remainingHealth = player.getHealth();
            if (remainingHealth > 0 ) {
                cManager.checkGhostCollision(player, ghost);
                player.drawRemainingHealth();
            }
            else {
            	entityManager.gameOverDispose();
            	drawBackground();
            	renderGameOverOverlay();
            }
        }
        
        // Check for timer
        if(timer.getTime() == 0) {
        	entityManager.gameOverDispose();
        	drawBackground();
        	renderGameOverOverlay();
        }

        // Play background music
        ioManager.playBG();
    }
    
    private void drawBackground() {
    	batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }
    
    private void renderGameOverOverlay() {
        // Clear the overlay stage
        overlayStage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        overlayStage.draw();
    	score.draw(score.getScore());
    }

    @Override
    public void dispose() {
        // Properly dispose of textures
        backgroundTexture.dispose();
        entityManager.disposeEntities();
        ioManager.dispose();
        overlayStage.dispose();
    }
    
}
