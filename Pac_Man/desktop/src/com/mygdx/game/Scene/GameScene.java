package com.mygdx.game.Scene;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameMaster;
import com.mygdx.game.Engine.Collectible;
import com.mygdx.game.Engine.CollisionManager;
import com.mygdx.game.Engine.Entity;
import com.mygdx.game.Engine.EntityManager;
import com.mygdx.game.Engine.Ghost;
import com.mygdx.game.Engine.IOManager;
import com.mygdx.game.Engine.Player;
import com.mygdx.game.Engine.SceneManager;
import com.mygdx.game.Engine.Score;

public class GameScene extends ScreenAdapter {

    // Entities
    private Player player;
    private Ghost ghost;
    private Collectible collectibles[];

    // Score
    private Score score;

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

        // Draw background texture
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        // Draw score and entities
        score.draw();
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
            cManager.checkGhostCollision(player, ghost);
            player.drawRemainingHealth();
        }

        // Play background music
        ioManager.playBG();
    }

    @Override
    public void dispose() {
        // Properly dispose of textures
        backgroundTexture.dispose();
        entityManager.disposeEntities();
        ioManager.dispose();
    }
}
