package no.ntnu.dof.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.List;

import no.ntnu.dof.controller.DuelOfFates;
import no.ntnu.dof.model.User;
import no.ntnu.dof.model.gameplay.playerclass.PlayerClass;

public class ChooseClassScreen extends BaseScreen {
    private Stage stage;
    private Skin skin;
    private Table contentTable;
    private User user;
    private List<PlayerClass> playerClasses;
    private TextButton selectedButton = null; // To keep track of the currently selected button
    private TextButton.TextButtonStyle defaultStyle;
    private TextButton.TextButtonStyle selectedStyle;

    public ChooseClassScreen(DuelOfFates game) {
        super();
        this.user = game.getCurrentUser();
        this.playerClasses = game.getPlayerClasses();
        this.skin = new Skin(Gdx.files.internal("uiskin.json"));
        this.stage = new Stage(new ScreenViewport(), this.batch);

        setupUI();
        Gdx.input.setInputProcessor(stage);
    }

    private void setupUI() {
        // Initialize styles
        initializeStyles();

        contentTable = new Table();
        contentTable.setFillParent(true);
        contentTable.top();

        Label title = new Label("Choose a Class", skin);
        contentTable.add(title).padTop(50).padBottom(30).row();

        for (PlayerClass playerClass : playerClasses) {
            TextButton classButton = new TextButton(playerClass.getName(), skin);

            if (playerClass.equals(user.getPlayerClass())) {
                classButton.setStyle(selectedStyle);
                selectedButton = classButton; // Set this button as selected if it matches the user's class
            } else {
                classButton.setStyle(defaultStyle);
            }

            classButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (selectedButton != null) {
                        selectedButton.setStyle(defaultStyle);
                    }
                    classButton.setStyle(selectedStyle);
                    selectedButton = classButton;
                    user.setPlayerClass(playerClass);
                }
            });
            contentTable.add(classButton).padBottom(10).width(200).height(50).row();
        }

        stage.addActor(contentTable);
    }

    private void initializeStyles() {
        defaultStyle = new TextButton.TextButtonStyle(skin.get(TextButton.TextButtonStyle.class));
        selectedStyle = new TextButton.TextButtonStyle(defaultStyle);
        selectedStyle.up = skin.newDrawable("default-round", Color.GREEN);
        selectedStyle.down = skin.newDrawable("default-round-down", Color.GREEN);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        background.setSize(width, height);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        super.dispose();
    }
}
