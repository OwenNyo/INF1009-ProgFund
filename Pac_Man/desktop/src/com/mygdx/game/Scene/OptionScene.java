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
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Engine.SceneManager;
import com.mygdx.game.Engine.IOManager;
import com.mygdx.game.GameMaster;

public class OptionScene extends ScreenAdapter {

    private final GameMaster gameMaster;
    private final SceneManager sceneManager;
    private final IOManager ioManager;

    private final SpriteBatch batch;
    private final Stage stage;
    private final Texture menuTexture;

    public OptionScene(GameMaster gameMaster, SceneManager sceneManager, IOManager ioManager) {
        // Initialize game objects
        this.gameMaster = gameMaster;
        this.sceneManager = sceneManager;
        this.ioManager = ioManager;

        // Create sprite batch and stage
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Load menu background texture and UI skin
        menuTexture = new Texture("Icy_background.jpg");
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("freezing-ui.atlas"));
        Skin skin = new Skin(Gdx.files.internal("freezing-ui.json"), atlas);

        // Get button and label styles from the skin
        TextButton.TextButtonStyle buttonStyle = skin.get("default", TextButton.TextButtonStyle.class);
        Label.LabelStyle labelStyle = skin.get("dark", Label.LabelStyle.class);

        // Create back button
        TextButton backButton = createButton("BACK", buttonStyle, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 4);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sceneManager.setMenuScreen();
            }
        });
        stage.addActor(backButton);

        // Create background music volume label and slider
        Label bgLabel = new Label("Background Music Volume", labelStyle);
        bgLabel.setPosition(Gdx.graphics.getWidth() / 2 - bgLabel.getWidth() / 2, Gdx.graphics.getHeight() * 3 / 4 + 50);
        stage.addActor(bgLabel);
        Slider bgVolumeSlider = new Slider(0, 1, 0.1f, false, skin);
        bgVolumeSlider.setValue(ioManager.getBGVolume());
        bgVolumeSlider.setSize(400, 50);
        bgVolumeSlider.setPosition(Gdx.graphics.getWidth() / 2 - bgVolumeSlider.getWidth() / 2, Gdx.graphics.getHeight() * 2 / 3 + 50);
        bgVolumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Slider slider = (Slider) actor;
                float volume = slider.getValue();
                ioManager.setBGVolume(volume);
            }
        });
        stage.addActor(bgVolumeSlider);

        // Create sound effects volume label and slider
        Label seLabel = new Label("Sound Effects Volume", labelStyle);
        seLabel.setPosition(Gdx.graphics.getWidth() / 2 - seLabel.getWidth() / 2, Gdx.graphics.getHeight() / 2 + 50);
        stage.addActor(seLabel);
        Slider seVolumeSlider = new Slider(0, 1, 0.1f, false, skin);
        seVolumeSlider.setValue(ioManager.getSEVolume());
        seVolumeSlider.setSize(400, 50);
        seVolumeSlider.setPosition(Gdx.graphics.getWidth() / 2 - seVolumeSlider.getWidth() / 2, Gdx.graphics.getHeight() / 3 + 50);
        seVolumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Slider slider = (Slider) actor;
                float volume = slider.getValue();
                ioManager.setSEVolume(volume);
            }
        });
        stage.addActor(seVolumeSlider);

        // Create mute checkbox
        CheckBox.CheckBoxStyle checkBoxStyle = skin.get("default", CheckBox.CheckBoxStyle.class);
        CheckBox muteCheckbox = new CheckBox("", checkBoxStyle);
        // Adjust position to shift more towards the left
        muteCheckbox.setPosition(backButton.getX() - muteCheckbox.getWidth() - 40, backButton.getY() + backButton.getHeight());
        // Make the checkbox bigger
        muteCheckbox.setSize(150, 150);
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
        stage.addActor(muteCheckbox);

        // Add a label next to the checkbox
        Label muteLabel = new Label("Mute", labelStyle);
        muteLabel.setPosition(muteCheckbox.getX() + muteCheckbox.getWidth() + 10, muteCheckbox.getY() + (muteCheckbox.getHeight() - muteLabel.getHeight()) / 2);
        stage.addActor(muteLabel);
    }

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
        batch.begin();
        batch.draw(menuTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        // Render the stage
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void dispose() {
        // Dispose of resources
        batch.dispose();
        menuTexture.dispose();
        stage.dispose();
    }
}
