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
import com.mygdx.game.Engine.HUD;
import com.mygdx.game.Engine.TimerClass;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameScene extends ScreenAdapter {

    // Entities
    private Player player;
    private Ghost ghost;
    private Collectible collectibles[];

    // Score
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

        // Load background texture
        backgroundTexture = new Texture("PacMan_Background.jpg");

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
    }

    @Override
    public void render(float delta) {
        // Clear screen with dark blue background
        ScreenUtils.clear(0 , 0, 0.2f, 1); 

        // Clear OpenGL color buffer
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        

        if (gameState == GameState.RUNNING) // Check if game state is running before updating entities
        {
	        // Draw background texture
	        hud.drawBackground(backgroundTexture);
	
	        // Draw timer and entities
	        timer.draw();
	        entityManager.drawEntities();
	        entityManager.moveEntities();
	
	        // Get Entities
	        player = entityManager.getPlayer();
	        ghost = entityManager.getGhost();
	        collectibles = entityManager.getCollectiblesArray();
	        
	        // Update and draw score
	        hud.drawScore(player.getPoints());
	
	        // Check for player collision with collectibles and update score
	        if (cManager.checkCollectibleCollision(player, collectibles)) {
	        	player.PlayerScorePoints(10);
	        }
	
	        // Check for collision with ghost
	        if (player != null && ghost != null) {
	            int remainingHealth = player.getHealth();
	            if (remainingHealth > 0 ) {
	                cManager.checkGhostCollision(player, ghost);
	                hud.drawRemainingHealth(player.getHealth());
	            }
	            else {
	            	gameState = GameState.GAME_OVER;
	            }
	        }
	        
	        // Check for timer
	        if(timer.getTime() == 0) {
	        	gameState = GameState.GAME_OVER;
	        }
	
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
    }

<<<<<<< Updated upstream
=======
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
        
        // Handle player logic to display different avatars
        if(player.getPoints() > 100 && player.getPoints() < 200) {
        	player.setTex(astronautVeteran);
        }
        else if (player.getPoints() > 200){
        	player.setTex(astronautCommander);
        }

        // Check for player collision with asteroids
        if (cManager.checkCollectibleCollision(player, asteroids)) {
            // Handle collectible collision
        	// Handle asteroid collision
        	if (firstAsteroidCollision) {
                // Handle asteroid collision
        		// Set true explicitly
                showingAsteroidPopup.set(true);
                
                // Schedule tasks to hide the popups after 2 seconds
                timer.timerCountdown(2, showingAsteroidPopup);
                // Set to false after the first collision
                firstAsteroidCollision = false; 
            }
        }
        
        // Check for player collision with planets
        if (cManager.checkCollectibleCollision(player, collectibles)) {
        	player.PlayerScorePoints(10);
        	
            // Pause game when player collides with a planet
            gameState = GameState.PAUSED;
            
            // Stop BG music
            ioManager.stopBG();
            
            // Set screen to planet fun fact scene
            System.out.println("Planet: " +cManager.getLastCollidedPlanetName());
            sceneManager.setPlanetScreen(cManager.getLastCollidedPlanetName());
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
        
        // Timer Over change to scene 2
		if(timer.getTime() == 0) {
			timer.setTime(30);
	        entityManager.disposeEntities();
	        entityManager.initEntities(2);
	        camera.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);
		}
        

        // Play background music
		if (gameState != GameState.PAUSED) {
			ioManager.playBG();
		}
    }
    
    // Render game elements when game state is GAME_OVER
    private void renderGameOverState() {
        // Dispose resources and switch to end screen
        backgroundTexture.dispose();
        entityManager.disposeEntities();
        ioManager.dispose();
        hud.dispose();
        sceneManager.setTriviaScreen(player);
//        sceneManager.setEndScreen(player.getPoints());
    }
    
    // Method to update game state
    public void updateGameState(GameState newState) {
        this.gameState = newState;
    }
        
>>>>>>> Stashed changes
    @Override
    public void dispose() {
        // Properly dispose of textures
        backgroundTexture.dispose();
        entityManager.disposeEntities();
        ioManager.dispose();
        hud.dispose();
        overlayStage.dispose();
    }
    
}
