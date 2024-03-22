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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.GameMaster;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.mygdx.game.Engine.EntityManager;
import com.mygdx.game.Engine.IOManager;
import com.mygdx.game.Engine.Player;
import com.mygdx.game.Engine.SceneManager;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Engine.HUD;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class TriviaScene extends ScreenAdapter {
	private Stage stage;
    private GameMaster gameMaster;
    private SceneManager sceneManager;

    private IOManager ioManager;
    private Player player;
    private EntityManager entityManager;

    // Quiz Questions
    private String[][] planetsList;
    private Texture background;
    private String answer;
    private String answerText;
    
    private int finalScore; // Calculate final score
    private int currentBackgroundIndex = 0; // Index to track the current background
    
    //IDK stop 
//    private List<String[]> response = new ArrayList<>();
        
    // Batch texture
    private SpriteBatch batch;
    
    private BitmapFont scoreFont;
    
    private HUD hud;
    
    
    public TriviaScene(GameMaster gameMaster, SceneManager sceneManager, Player player) {
    	this.gameMaster = gameMaster;
    	this.sceneManager = sceneManager;
    	this.player = player; 
    	
    	// Create a new SpriteBatch instance
        batch = new SpriteBatch();
        hud = new HUD();
        // Class & Manager Initialization
        ioManager = new IOManager();
          
        // Questions list and answers 
        String[][] planetsListlocal = {
            {"quiz/earthquiz.png", "False", "Water covers 71% of Earth’s surface."},
            {"quiz/venusquiz.png", "False", "Venus is similar to Earth’s size"},
            {"quiz/mecuryquiz.png", "True", " "},
            {"quiz/uranusquiz.png", "True", " "},
            {"quiz/jupiterquiz.png", "False", "Jupiter is the largest planet in our Solar System."},
            {"quiz/marsquiz.png", "True", " "},
            {"quiz/neptunequiz.png", "False", "Neptune is the farthest planet to the Sun."},
            {"quiz/moonquiz.png", "True", " "},
            {"quiz/saturnquiz.png", "True", " "},
        };
        planetsList=planetsListlocal;
       
        this.scoreFont = new BitmapFont();
        this.scoreFont.getData().setScale(3);
        
        finalScore = player.getPoints();
        System.out.println(player.getPoints());
        
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Load atlas file to create skin for UI elements
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("freezing-ui.atlas"));
        Skin skin = new Skin(Gdx.files.internal("freezing-ui.json"), atlas);

        // Get the default button style from the skin
        TextButton.TextButtonStyle buttonStyle = skin.get("default", TextButton.TextButtonStyle.class);


        // Initialize button - "True" Option
        TextButton TrueButton = new TextButton("True", buttonStyle);
        TrueButton.setPosition(Gdx.graphics.getWidth() /4 - TrueButton.getWidth() /4 ,
                                    Gdx.graphics.getHeight() /4 - TrueButton.getHeight() /2);
        TrueButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	
            	// checks if the answer is correct
            	if (answer == "True") {
            		// play correct sound
            		ioManager.playSECollect();
            		//add score 
                	finalScore = finalScore + 10;
            	} 
            	else {
            		// play incorrect sound
            		ioManager.playSE();
            		// Draw quiz answer IDK HOW 
//                    drawAnswer(answerText);
            	}
			
            	// load next question, change background 
            	if (currentBackgroundIndex<8) {
            		currentBackgroundIndex = currentBackgroundIndex+1;
            	}
            	else {
            		// when quiz ends 
            		sceneManager.setEndScreen(finalScore);
            		}
            }
        });
        
        
        // Initialize button - "False" Option
        TextButton FalseButton = new TextButton("False", buttonStyle);
        FalseButton.setPosition(Gdx.graphics.getWidth() / 2 - FalseButton.getWidth() / 2,
                                   Gdx.graphics.getHeight() / 4 - FalseButton.getHeight() / 2);
        FalseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	
            	// checks if the answer is correct
            	if (answer == "False") {
            		// play correct sound
            		ioManager.playSECollect();
            		//add score 
                	finalScore = finalScore + 10;
            	} 
            	else {
            		// play incorrect sound
            		ioManager.playSE();
            		// Draw quiz answer IDK HOW 
//                    drawAnswer(answerText);
            	}
            	
            	// load next question 
            	if (currentBackgroundIndex<8) {
            	currentBackgroundIndex = currentBackgroundIndex+1;
            	}
            	
            	else {
            		// when quiz ends 
            		sceneManager.setEndScreen(finalScore);
            	}
            }
        });

        // Add buttons to the stage
        stage.addActor(TrueButton);
        stage.addActor(FalseButton);
        
    }
    @Override
    public void render(float delta) {
    	// Clear OpenGL color buffer
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // Draw background texture
        
        List<String[]> responses = askQuestion(planetsList);
        
        // Loop through current question 
        for (String[] response : responses) {
        	background = new Texture(response[0]);
        	answer = response[1];
        	answerText = response[2];
            System.out.println("Background: " + response[0]);
            System.out.println("Answer: " + answer);
            System.out.println("Answer Text: " + answerText);
            System.out.println();
        }
        
        
        hud.drawBackground(background);
        // timer here ??????????? stop 
        
//        hud.drawBackground(background[currentBackgroundIndex]);
        
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        
     // Draw player score
        drawPlayerScore();
        
     // Draw quiz answer IDK HOW 
        // should show only after clicking the wrong option
        // IS IT EVEN SUPPOSED TO BE HERE 
        
        drawAnswer(answerText);
        
    }
    
    private void drawPlayerScore() {
        batch.begin();
        scoreFont.draw(batch, String.valueOf(finalScore), 600, 1000);
        batch.end();
    }
    
    private void drawAnswer(String a) {
    	// getting but not enough time to show the shit on the screen 
    	// also idk if it would bc it's not inside render (probably not)
    	// also the formatting 
    	// no beta we die 
    	System.out.println("inside drawans : " + a);
        batch.begin(); 
        scoreFont.draw(batch, a, 1200, 300);
        batch.end();
    }
    
//  // QUIZ more like brain aneurysm 
    public List<String[]> askQuestion(String[][] planetsList) {
        List<String[]> questionResponses = new ArrayList<>();
        
        // Retrieve question details 
        if (currentBackgroundIndex < planetsList.length) {
        	String[] question = planetsList[currentBackgroundIndex];
            String backgroundpath = question[0];
            String answer = question[1];
            String answerText = question[2];
            
            // Store in new list to pass to main
            String[] response = {backgroundpath, answer, answerText};
            questionResponses.add(response);
        }
        return questionResponses;
    }
    

    
    @Override
    public void dispose() {
        batch.dispose();
        scoreFont.dispose();
        background.dispose();
    }

}
