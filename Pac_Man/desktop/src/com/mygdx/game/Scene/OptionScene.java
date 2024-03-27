package com.mygdx.game.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Engine.SceneManager;
import com.mygdx.game.Logic.HUD;
import com.mygdx.game.Settings.GameSettings;
import com.mygdx.game.Engine.IOManager;
import com.mygdx.game.GameMaster;

public class OptionScene extends ScreenAdapter {

	// Managers
    private final GameMaster gameMaster;
    private final SceneManager sceneManager;
    private final IOManager ioManager;
    
    // Classes
    private final SpriteBatch batch;
    private final Stage stage;
    private final Texture optionTexture;
    private HUD hud;

    // Constructors
    public OptionScene(GameMaster gameMaster, SceneManager sceneManager, IOManager ioManager) {
    	// Initialize game objects
        this.gameMaster = gameMaster;
        this.sceneManager = sceneManager;
        this.ioManager = ioManager;

        // Create sprite batch and stage
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        hud = new HUD();
        Gdx.input.setInputProcessor(stage);
        
        // Load menu background texture and UI skin
        optionTexture = new Texture("Icy_background.jpg");
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("freezing-ui.atlas"));
        Skin skin = new Skin(Gdx.files.internal("freezing-ui.json"), atlas);

        // Get button and label styles from the skin
        TextButton.TextButtonStyle buttonStyle = skin.get("default", TextButton.TextButtonStyle.class);
        Label.LabelStyle labelStyle = skin.get("dark", Label.LabelStyle.class);
        
        // Create labels
        Label bgLabel = new Label("Background Music Volume", labelStyle);
        bgLabel.setPosition(Gdx.graphics.getWidth() / 2 - bgLabel.getWidth() / 2, Gdx.graphics.getHeight() * 3 / 4);
        
        Label seLabel = new Label("Sound Effects Volume", labelStyle);
        seLabel.setPosition(Gdx.graphics.getWidth() / 2 - seLabel.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        
        Label difficultyLabel = new Label("Game Difficulty", labelStyle);
        difficultyLabel.setPosition(Gdx.graphics.getWidth() / 2 - difficultyLabel.getWidth() / 2, Gdx.graphics.getHeight() / 4);
        
        // Create sliders
        Slider bgVolumeSlider = new Slider(0, 1, 0.1f, false, skin);
        bgVolumeSlider.setValue(ioManager.getBGVolume());
        bgVolumeSlider.setSize(600, 50);
        bgVolumeSlider.setPosition(Gdx.graphics.getWidth() / 2 - bgVolumeSlider.getWidth() / 2, Gdx.graphics.getHeight() * 2 / 3);
        bgVolumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Slider slider = (Slider) actor;
                float volume = slider.getValue();
                ioManager.setBGVolume(volume);
            }
        });
       
        Slider seVolumeSlider = new Slider(0, 1, 0.1f, false, skin);
        seVolumeSlider.setValue(ioManager.getSEVolume());
        seVolumeSlider.setSize(600, 50);
        seVolumeSlider.setPosition(Gdx.graphics.getWidth() / 2 - seVolumeSlider.getWidth() / 2, Gdx.graphics.getHeight() * 5 / 12);
        seVolumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Slider slider = (Slider) actor;
                float volume = slider.getValue();
                ioManager.setSEVolume(volume);
            }
        });       

        // Create checkbox
        CheckBox.CheckBoxStyle checkBoxStyle = skin.get("default", CheckBox.CheckBoxStyle.class);
        CheckBox muteCheckbox = new CheckBox("", checkBoxStyle);
        float muteCheckboxX = Gdx.graphics.getWidth() / 2 - muteCheckbox.getWidth() / 2;
        muteCheckbox.setPosition(muteCheckboxX - 30, Gdx.graphics.getHeight() / 3);
        muteCheckbox.setChecked(ioManager.getMuteState());
        muteCheckbox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ioManager.setMuteState(!ioManager.getMuteState());
                if (ioManager.getMuteState()) {
                    ioManager.setBGVolume(0);
                    ioManager.setSEVolume(0);
                } else {
                    ioManager.setBGVolume(1);
                    ioManager.setSEVolume(1);
                }
                bgVolumeSlider.setValue(ioManager.getBGVolume());
                seVolumeSlider.setValue(ioManager.getSEVolume());
            }
        }); 
        
        // Create dropdown box
        SelectBox<String> difficultySelectBox = new SelectBox<>(skin);
        difficultySelectBox.setItems("Easy", "Medium", "Hard");
        difficultySelectBox.setSelected(GameSettings.getInstance().getDifficulty());
        difficultySelectBox.setSize(200, 50);
        difficultySelectBox.setPosition(Gdx.graphics.getWidth() / 2 - difficultySelectBox.getWidth() / 2, Gdx.graphics.getHeight() / 5);
        difficultySelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                @SuppressWarnings("unchecked")
                String selectedDifficulty = ((SelectBox<String>) actor).getSelected();
                GameSettings.getInstance().setDifficulty(selectedDifficulty);
            }
        });
        
        // Create button
        TextButton backButton = createButton("BACK", buttonStyle, Gdx.graphics.getWidth() / 2, 50);
        backButton.setPosition(Gdx.graphics.getWidth() / 2 - backButton.getWidth() / 2, 50);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sceneManager.setMenuScreen();
            }
        });
        
        
        
        // Create mute label based on checkbox location
        Label muteLabel = new Label("Mute", labelStyle);
        float muteLabelX = Gdx.graphics.getWidth() / 2 + muteCheckbox.getWidth() / 2 + 10;
        muteLabel.setPosition(muteLabelX - 20, Gdx.graphics.getHeight() / 3); // Adjusted position
        
        
        // Label Actors
        stage.addActor(bgLabel);
        stage.addActor(seLabel);
        stage.addActor(difficultyLabel);
        stage.addActor(muteLabel);
        
        // UI Interactive Element Actors
        stage.addActor(bgVolumeSlider);
        stage.addActor(seVolumeSlider);
        stage.addActor(muteCheckbox);
        stage.addActor(difficultySelectBox);     
        stage.addActor(backButton);
    }

    // Private function to ease the creation of buttons
    private TextButton createButton(String text, TextButton.TextButtonStyle style, float x, float y) {
        TextButton button = new TextButton(text, style);
        button.setPosition(x - button.getWidth() / 2, y - button.getHeight() / 2);
        return button;
    }

    @Override
    public void render(float delta) {
        // Clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render background texture
        hud.drawBackground(optionTexture);

        // Render the stage
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void dispose() {
        // Dispose of resources
        batch.dispose();
        optionTexture.dispose();
        stage.dispose();
    }
}
