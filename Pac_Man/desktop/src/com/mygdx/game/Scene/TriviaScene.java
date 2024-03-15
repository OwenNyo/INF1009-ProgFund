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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
import java.util.concurrent.atomic.AtomicBoolean;

public class TriviaScene extends ScreenAdapter {
	private Stage stage;
    private GameMaster gameMaster;
    private SceneManager sceneManager;
    private int finalScore;
    
    private IOManager ioManager;
    private Player player;
    private EntityManager entityManager;

    // Background texture
//    private Texture backgroundTexture;
    private Texture[] backgrounds; // Array to hold different background textures
    private int currentBackgroundIndex = 0; // Index to track the current background
    
//    private int quizscore;
    // planetList=[]
    //planetList.append([backhround,answer(yes or no),answer text?])
    //for each x in planetList : Askquestion(x)
    //askQuestiom(list list){
    //background = list[0], answer=list[1],text=list[2]
    //onclicknextbtn??????????????????
    
    
    
    // Batch texture
    private SpriteBatch batch;
    
    private BitmapFont scoreFont;
    private String endScore;
    
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
        

        // Load different background textures (quiz qns)
        backgrounds = new Texture[3]; 
        backgrounds[0] = new Texture("quiz/quiz1.jpg");
        backgrounds[1] = new Texture("quiz/quiz2.jpg");
        backgrounds[2] = new Texture("quiz/quiz3.jpg");
       
       
        this.scoreFont = new BitmapFont();
        this.scoreFont.getData().setScale(3);
        this.endScore = "Game Over!\nScore : ";
        
        //player = entityManager.getPlayer();
        finalScore = player.getPoints();
        System.out.println(player.getPoints());
        
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Load atlas file to create skin for UI elements
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("freezing-ui.atlas"));
        Skin skin = new Skin(Gdx.files.internal("freezing-ui.json"), atlas);

        // Get the default button style from the skin
        TextButton.TextButtonStyle buttonStyle = skin.get("default", TextButton.TextButtonStyle.class);

        // Initialize button
        TextButton OptionButton1 = new TextButton("correct", buttonStyle);
        OptionButton1.setPosition(Gdx.graphics.getWidth() /4 - OptionButton1.getWidth() /4 ,
                                    Gdx.graphics.getHeight() /4 - OptionButton1.getHeight() /2);
        OptionButton1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	ioManager.playSECollect();
            	//add score ??????????????? 
            	finalScore = finalScore + 10;
            	
            	//load next question 
            	//can try to randomize or something idk kill me 
            	currentBackgroundIndex = 1;
            	
            }
        });
        
        
        // Initialize button
        TextButton OptionButton2 = new TextButton("wrong", buttonStyle);
        OptionButton2.setPosition(Gdx.graphics.getWidth() / 2 - OptionButton2.getWidth() / 2,
                                   Gdx.graphics.getHeight() / 4 - OptionButton2.getHeight() / 2);
        OptionButton2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	ioManager.playSE();
            	//set qn status as wrong or some shit 
            	//load next question 
            	currentBackgroundIndex = 2;
            }
        });

        // Add buttons to the stage
        stage.addActor(OptionButton1);
        stage.addActor(OptionButton2);
     // stage.addActor(playAgainButton2);
        
    }
    @Override
    public void render(float delta) {
    	// Clear OpenGL color buffer
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // Draw background texture
        hud.drawBackground(backgrounds[currentBackgroundIndex]);
        
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        
     // Draw player score
        drawPlayerScore();
        
    }
    
    private void drawPlayerScore() {
        batch.begin();
        scoreFont.draw(batch, String.valueOf(finalScore), 600, 1000);
        batch.end();
    }
    

    
    @Override
    public void dispose() {
 
        batch.dispose();
        scoreFont.dispose();
//        backgrounds.dispose();
        
     // Dispose all loaded textures when application exits
        for (Texture background : backgrounds) {
            background.dispose();
        }
    }

}
