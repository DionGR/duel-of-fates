package no.ntnu.dof.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import no.ntnu.dof.controller.ScreenManager;
import no.ntnu.dof.model.gameplay.card.AttackCard;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.deck.Deck;
import no.ntnu.dof.model.gameplay.playerclass.PlayerClass;
import no.ntnu.dof.model.gameplay.stats.armor.Armor;
import no.ntnu.dof.model.gameplay.stats.health.Health;
import no.ntnu.dof.model.gameplay.stats.mana.Mana;
import no.ntnu.dof.controller.DuelOfFates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChooseClassScreen implements Screen {

    private Stage stage;
    private SpriteBatch batch;
    private AssetManager assetManager;
    private Skin skin;
    private Sprite background;
    private Table contentTable;
    private List<PlayerClass> playerClasses;

    public ChooseClassScreen(DuelOfFates game, SpriteBatch batch, AssetManager assetManager) {
        this.batch = batch;
        this.assetManager = assetManager;
        this.stage = new Stage(new ScreenViewport());
        this.playerClasses = game.getPlayerClasses();
        Gdx.input.setInputProcessor(stage);

        this.skin = new Skin(Gdx.files.internal("uiskin.json"));
        this.background = new Sprite(assetManager.get("menuBackground.png", Texture.class));

        setupUI();
    }

    private void setupUI() {
        // Positioning and styling of the content table
        contentTable = new Table();
        contentTable.setFillParent(true);
        contentTable.top();

        // Title
        Label title = new Label("Choose a Class", skin);
        contentTable.add(title).padTop(50).padBottom(30).row();

        // Dynamically add class selection buttons
        for (PlayerClass playerClass : playerClasses) {
            TextButton classButton = new TextButton(playerClass.getName(), skin);
            classButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Handle class selection

                }
            });
            contentTable.add(classButton).padBottom(10).width(200).height(50).row();
        }

        stage.addActor(contentTable);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        background.draw(batch);
        batch.end();

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
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}