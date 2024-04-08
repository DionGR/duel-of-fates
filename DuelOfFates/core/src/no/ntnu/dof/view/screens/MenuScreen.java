package no.ntnu.dof.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import no.ntnu.dof.controller.DuelOfFates;
import no.ntnu.dof.controller.ScreenManager;


public class MenuScreen implements Screen {

    private Stage stage;
    private SpriteBatch batch;
    private Sprite background;
    private Skin skin;
    private Table contentTable;
    private TextButton lobbiesBtn;
    private TextButton tutorialBtn;
    private TextButton logoutBtn;
    private TextButton chooseClassBtn;
    private Label gameTitle;
    private Sprite soundOn;
    private Sprite soundOff;
    private AssetManager assetManager;
    private DuelOfFates game;

    public MenuScreen(DuelOfFates game, SpriteBatch batch, AssetManager assetManager) {
        this.batch = batch;
        this.assetManager = assetManager;
        this.game = game;
    }

    @Override
    public void show() {
        Gdx.app.log("MenuScreen", "show method called");
        // Loading skin
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage(new ScreenViewport());

        // Making a centered table to store title and buttons
        contentTable = new Table();
        contentTable.setWidth(stage.getWidth());
        contentTable.align(Align.center|Align.top);
        contentTable.setPosition(0, Gdx.graphics.getHeight());

        gameTitle = new Label("Duel of Fates", skin, "default");
        lobbiesBtn = new TextButton("Game Lobbies", skin, "default");
        chooseClassBtn = new TextButton("Choose Class", skin, "default");
        tutorialBtn = new TextButton("Tutorial", skin, "default");
        logoutBtn = new TextButton("Log out", skin, "default");

//        for (Actor btn:contentTable.getChildren()) {
//            btn.setHeight(50);
//            btn.setWidth(150);
//        }

        // Adding content to table
        contentTable.padTop(30);
        contentTable.add(gameTitle).padBottom(30).row();
        contentTable.add(lobbiesBtn).padBottom(30).width(150).height(50).row();
        contentTable.add(chooseClassBtn).padBottom(30).width(150).height(50).row();
        contentTable.add(tutorialBtn).padBottom(30).width(150).height(50).row();;
        contentTable.add(logoutBtn).width(150).height(50);

        stage.addActor(contentTable);


        lobbiesBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.transitionToLobbies();
            }
        });

        chooseClassBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.transitionToChooseClass();
            }
        });

        logoutBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.transitionToLogin();
            }
        });

        // Loading background
        background = new Sprite(assetManager.get("menuBackground.png", Texture.class));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Setting sound buttons
        soundOn = new Sprite(new Texture(Gdx.files.internal("soundOn.png")));
        soundOn.setSize(80, 80);
        soundOff = new Sprite(new Texture(Gdx.files.internal("soundOff.png")));
        soundOff.setSize(80,80);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        soundOn.draw(batch);
        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }
    @Override
    public void resume() {
    }
    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}