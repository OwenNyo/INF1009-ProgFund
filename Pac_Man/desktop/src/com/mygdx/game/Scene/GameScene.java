package com.mygdx.game.Scene;

import java.util.ArrayList;
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
    
    private Label popupLabel;
    private Table popupTable;
    private boolean showingPopup;

    // Hud
    private HUD hud;
    
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
    
    // Scene Manager
    private SceneManager sceneManager;
    
    private OrthographicCamera camera;
    
    private Texture collisionTexture;
    private boolean showCollisionTexture;
    private float collisionTextureTimer;
    
    private float backgroundSpeed = 6;
    
    // GameState Enum to manage game state
    private enum GameState {
        RUNNING,
        GAME_OVER;
    }
    
    // Initialize current game state to running
    private GameState gameState = GameState.RUNNING;

    public GameScene(GameMaster gameMaster, SceneManager sceneManager) {
        this.gameMaster = gameMaster;
        this.sceneManager = sceneManager;
        
        // Create a new SpriteBatch instance
        batch = new SpriteBatch();
        
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);

        // Load background texture
        backgroundTexture = new Texture("space.jpg");

        collisionTexture = new Texture("Space_Background.png");
        showCollisionTexture = false;
        collisionTextureTimer = 0f;

        // Initialize EntityManager and entities
        entityManager = new EntityManager();
        entityManager.initEntities();
        
        // Fetch Entity List
        entityList = entityManager.getEntityList();
        
        // Initialize Scoring System
        hud = new HUD();
        
        // Initialize Scoring System
        timer = new TimerClass();

        // Initialize Collision Manager
        cManager = new CollisionManager();

        // Initialize IO Manager
        ioManager = new IOManager();
        
        // Create the stage for UI elements
        overlayStage = new Stage(new ScreenViewport());

        // Initialize popup message
        Skin skin = new Skin(Gdx.files.internal("freezing-ui.json")); // Assuming you have a skin file
        popupLabel = new Label("Beware of the blackhole!!!", skin);
        popupLabel.setAlignment(Align.center);
        
        popupLabel.setFontScale(2.5f);

        popupTable = new Table();
        popupTable.setFillParent(true);
        popupTable.add(popupLabel).expand().center();
        
        showingPopup = true;

        // Schedule a task to hide the popup after 2 seconds
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                showingPopup = false;
            }
        }, 5);
        
    }

    @Override
    public void render(float delta) {
        // Clear screen with dark blue background
        ScreenUtils.clear(0 , 0, 0.2f, 1); 

        // Clear OpenGL color buffer
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        camera.position.x += backgroundSpeed * Gdx.graphics.getDeltaTime();
        

        if (gameState == GameState.RUNNING) // Check if game state is running before updating entities
        {
	        // Draw background texture
        	// Draw background using camera's combined matrix
            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth()*2, Gdx.graphics.getHeight());
            batch.end();
	
	        // Draw timer and entities
	        timer.draw();
	        entityManager.drawEntities();
	        entityManager.moveEntities();
	
	        // Get Entities
	        player = entityManager.getPlayer();
	        enemy = entityManager.getEnemy();
	        collectibles = entityManager.getCollectiblesArray();
	        asteroids = entityManager.getAsteroidArray();
	        
	        // Update and draw score
	        hud.drawScore(player.getPoints());
	
	        // Check for player collision with collectibles and update score
	        if (cManager.checkCollectibleCollision(player, collectibles)) {
	        	
	        }
	        
	        // Check for player collision with asteroids and update score
	        if (cManager.checkasteroidCollision(player, asteroids)) {

                
	        }
	
	        // Check for collision with enemy
	        if (player != null && enemy != null) {
	            int remainingHealth = player.getHealth();
	            if (remainingHealth > 0 ) {
	                cManager.checkEnemyCollision(player, enemy);
	                hud.drawRemainingHealth(player.getHealth());
	            }
	            else {
	            	gameState = GameState.GAME_OVER;
	            }
	        }
	        
	        // Check for timer
//	        if(timer.getTime() == 0) {
//	        	gameState = GameState.GAME_OVER;
//	        }
	
	        // Play background music
	        ioManager.playBG();
	    }
        else if (gameState == GameState.GAME_OVER) // If game state is changed to end
        {
        	backgroundTexture.dispose();
        	entityManager.disposeEntities();
        	ioManager.dispose();
        	hud.dispose();
            sceneManager.setEndScreen(player.getPoints());
        }
        
        camera.update();
        
        // Draw Pop Up Message
        if (showingPopup) {
            overlayStage.addActor(popupTable);
            overlayStage.act(delta);
            overlayStage.draw();
        }
        
    }

    @Override
    public void dispose() {
        // Properly dispose of textures
        backgroundTexture.dispose();
        entityManager.disposeEntities();
        ioManager.dispose();
        hud.dispose();
        overlayStage.dispose();
        popupLabel.clear();
        popupTable.clear();
    }
    
}
