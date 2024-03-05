package com.mygdx.game.Scene;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.GameMaster;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.mygdx.game.Engine.Collectible;
import com.mygdx.game.Engine.CollisionManager;
import com.mygdx.game.Engine.Entity;
import com.mygdx.game.Engine.EntityManager;
import com.mygdx.game.Engine.Enemy;
import com.mygdx.game.Engine.IOManager;
import com.mygdx.game.Engine.Player;
import com.mygdx.game.Engine.SceneManager;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Engine.HUD;
import com.mygdx.game.Engine.TimerClass;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameScene extends ScreenAdapter {

    // Entities
    private Player player;
    private Enemy enemy;
    private Collectible collectibles[], asteroids[];
    private List<Entity> entityList;

    // Initialize Managers and classes
    private EntityManager entityManager;
    private IOManager ioManager;
    private CollisionManager cManager;
    private SceneManager sceneManager;
    private GameMaster gameMaster;
    
    // Static Tables
    private Label BlackholeLabel, AsteroidLabel;
    private Table BHTable, AsteroidTable;
    private boolean showingBlackholePopup;
    private boolean showingAsteroidPopup;

    // Texture and Drawings
    private Texture backgroundTexture;
    private SpriteBatch batch;
    private HUD hud;
    private TimerClass timer;
    private Stage overlayStage;
    private OrthographicCamera camera;
    private float backgroundSpeed = 6;
    
    // Variable to track if the player collided with an asteroid before
    private boolean firstAsteroidCollision = true;
    
    // Game State management
    private enum GameState {
        RUNNING,
        GAME_OVER;
    }
    
    // Initialize current game state to running
    private GameState gameState = GameState.RUNNING;

    public GameScene(GameMaster gameMaster, SceneManager sceneManager) {
        this.gameMaster = gameMaster;
        this.sceneManager = sceneManager;
        
        // Create Instances
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        overlayStage = new Stage(new ScreenViewport());
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        
        // Class & Manager Initialization
        hud = new HUD();
        timer = new TimerClass();
        cManager = new CollisionManager();
        ioManager = new IOManager();
        entityManager = new EntityManager();
        entityManager.initEntities();
        entityList = entityManager.getEntityList();
        
        // Load background texture
        backgroundTexture = new Texture("space.jpg");     

        // Initialize Labels & Tables
        Skin skin = new Skin(Gdx.files.internal("freezing-ui.json")); 
        
        BHTable = new Table();
        AsteroidTable = new Table();
        
        BlackholeLabel = new Label("Beware of the hole!!!", skin);
        BlackholeLabel.setAlignment(Align.center);
        BlackholeLabel.setFontScale(2.5f);
        
        AsteroidLabel = new Label("You collided with an asteroid!!!", skin);
        AsteroidLabel.setAlignment(Align.center);
        AsteroidLabel.setFontScale(2.5f);
        
        BHTable = new Table();
        BHTable.setFillParent(true);
        BHTable.add(BlackholeLabel).expand().center();
        
        AsteroidTable = new Table();
        AsteroidTable.setFillParent(true);
        AsteroidTable.add(AsteroidLabel).expand().center();
        
        // Initialize popup visibility
        showingBlackholePopup = true;

        // Schedule tasks to hide the popups after 5 seconds
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
            	showingBlackholePopup = false;
            }
        }, 2);      
        
    }

    @Override
    public void render(float delta) {
        // Clear screen with dark blue background
        ScreenUtils.clear(0 , 0, 0.2f, 1); 

        // Clear OpenGL color buffer
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // Update camera position based on background scrolling speed
        camera.position.x += backgroundSpeed * Gdx.graphics.getDeltaTime();
        

        // Render game elements based on game state
        if (gameState == GameState.RUNNING) {
            renderRunningState();
        } else if (gameState == GameState.GAME_OVER) {
            renderGameOverState();
        }
        
        camera.update();
        
        // Draw Pop Up Messages
        if (showingBlackholePopup) {
            overlayStage.addActor(BHTable);
        } else {
            BHTable.remove(); // Remove the table if the popup is not showing
        }
        
        if (showingAsteroidPopup) {
            overlayStage.addActor(AsteroidTable);
        } else {
            AsteroidTable.remove(); // Remove the table if the popup is not showing
        }
        
        overlayStage.act(delta);
        overlayStage.draw();
        
    }
    
 // Render game elements when game state is RUNNING
    private void renderRunningState() {
        // Draw background texture
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth() * 2, Gdx.graphics.getHeight());
        batch.end();

        // Draw timer and entities
        timer.draw();
        entityManager.drawEntities();
        entityManager.moveEntities();
        
        // Get Entities Instances
        player = entityManager.getPlayer();
        enemy = entityManager.getEnemy();
        collectibles = entityManager.getCollectiblesArray();
        asteroids = entityManager.getAsteroidArray();
        


        // Update and draw score
        hud.drawScore(player.getPoints());

        // Check for player collision with collectibles and update score
        if (cManager.checkCollectibleCollision(player, asteroids)) {
            // Handle collectible collision
        	// Handle asteroid collision
        	if (firstAsteroidCollision) {
                // Handle asteroid collision
                showingAsteroidPopup = true;
                
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        showingAsteroidPopup = false;
                    }
                }, 2);
                // Set to false after the first collision
                firstAsteroidCollision = false; 
            }
        }

        // Check for collision with enemy
        if (player != null && enemy != null) {
            int remainingHealth = player.getHealth();
            if (remainingHealth > 0) {
                cManager.checkEnemyCollision(player, enemy);
                hud.drawRemainingHealth(player.getHealth());
            } else {
                gameState = GameState.GAME_OVER;
            }
        }
        
        // Timer Over
		//if(timer.getTime() == 0) {
		//  gameState = GameState.GAME_OVER;
		//}
        

        // Play background music
        ioManager.playBG();
    }
    
    // Render game elements when game state is GAME_OVER
    private void renderGameOverState() {
        // Dispose resources and switch to end screen
        backgroundTexture.dispose();
        entityManager.disposeEntities();
        ioManager.dispose();
        hud.dispose();
        sceneManager.setEndScreen(player.getPoints());
    }
    
    @Override
    public void dispose() {
        // Properly dispose of textures
        backgroundTexture.dispose();
        entityManager.disposeEntities();
        ioManager.dispose();
        hud.dispose();
        overlayStage.dispose();
        AsteroidLabel.clear();
        
    }
    
}
