package com.mygdx.game.Scene;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.GameMaster;
import com.mygdx.game.Engine.SceneManager;

public class PlanetScene extends ScreenAdapter {
	
	// Class and Managers
    private Stage stage;
    private GameMaster gameMaster;
    private SceneManager sceneManager;
    
    // Class Attributes
    private String planetName;
    private Texture planetTexture;
    
    public PlanetScene(GameMaster gameMaster, SceneManager sceneManager, String planetName) {
    	// Initialize variables and setup stage
    	this.gameMaster = gameMaster;
        this.planetName = planetName;
        this.sceneManager = sceneManager;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        // Determine the image path based on the planet name
    	String planetFactImagePath = "";
    	switch (planetName) {
    	case "Earth":
    		planetFactImagePath = "earth_funfact.png";
    		break;
    	case "Jupiter":
    		planetFactImagePath = "jupiter_funfact.png";
    		break;
    	case "Mars":
    		planetFactImagePath = "mars_funfact.png";
    		break;
    	case "Mercury":
    		planetFactImagePath = "mercury_funfact.png";
    		break;
    	case "Neptune":
    		planetFactImagePath = "neptune_funfact.png";
    		break;
    	case "Saturn":
    		planetFactImagePath = "saturn_funfact.png";
    		break;
    	case "Uranus":
    		planetFactImagePath = "uranus_funfact.png";
    		break;
    	case "Venus":
    		planetFactImagePath = "venus_funfact.png";
    		break;
    	case "Moon":
    		planetFactImagePath = "moon_funfact.png";
    		break;
    	}
        
        // Load planet fact image
        planetTexture = new Texture(Gdx.files.internal(planetFactImagePath));
        Image planetImage = new Image(planetTexture);
        
        // Set image size
        planetImage.setSize(1080, 1080); 
        
        // Set image position
        planetImage.setPosition(0, 0);
        
        // Create button to close fact screen
        TextButton closeButton = new TextButton("X", new Skin(Gdx.files.internal("freezing-ui.json")));
        closeButton.setPosition(Gdx.graphics.getWidth() - closeButton.getWidth(), Gdx.graphics.getHeight() - closeButton.getHeight());
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sceneManager.resumeGameScreen();
            }
        });
        
        // Create a table to organize UI elements
        Table table = new Table();
        table.setFillParent(true);
        table.add(planetImage).center();
        
        // Add table and close button to stage
        stage.addActor(table);
        stage.addActor(closeButton);
    }

    @Override
    public void render(float delta) {
    	// Clear OpenGL color buffer
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // Render stage
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
    	// Dispose of resources
        planetTexture.dispose();
        stage.dispose();
    }
}
