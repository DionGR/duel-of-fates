package no.ntnu.dof.view.screens.menu;

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

import no.ntnu.dof.controller.DuelOfFates;
import no.ntnu.dof.controller.ScreenController;
import no.ntnu.dof.view.screens.BaseScreen;
import no.ntnu.dof.view.screens.ReturnableScreen;


public class MenuScreen extends BaseScreen {

    private Stage stage;
    private final SpriteBatch batch;
    private Sprite background;
    private final AssetManager assetManager;

    public MenuScreen(DuelOfFates game, SpriteBatch batch, AssetManager assetManager) {
        super(game);
        this.batch = batch;
        this.assetManager = assetManager;
        this.game = game;
    }

    @Override
    public void show() {
        Gdx.app.log("MenuScreen", "show method called");
        // Loading skin
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage(new ScreenViewport(), this.batch);

        // Making a centered table to store title and buttons
        Table contentTable = new Table();
        contentTable.setWidth(stage.getWidth());
        contentTable.align(Align.center|Align.top);
        contentTable.setPosition(0, Gdx.graphics.getHeight());

        Label gameTitle = new Label("Duel of Fates", skin, "default");
        TextButton lobbiesBtn = new TextButton("Game Lobbies", skin, "default");
        TextButton chooseClassBtn = new TextButton("Choose Class", skin, "default");
        TextButton tutorialBtn = new TextButton("Tutorial", skin, "default");
        TextButton logoutBtn = new TextButton("Log Out", skin, "default");


        // Adding content to table
        contentTable.padTop(30);
        contentTable.add(gameTitle).padBottom(30).row();
        contentTable.add(lobbiesBtn).padBottom(20).width(150).height(50).row();
        contentTable.add(chooseClassBtn).padBottom(20).width(150).height(50).row();
        contentTable.add(tutorialBtn).padBottom(20).width(150).height(50).row();;
        contentTable.add(logoutBtn).width(150).height(50);

        stage.addActor(contentTable);


        lobbiesBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenController.transitionToLobbies();
            }
        });

        chooseClassBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenController.transitionToChooseClass();
            }
        });

        logoutBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenController.transitionToLoginWhenLoggedIn();
            }
        });

        // Loading background
        background = new Sprite(assetManager.get("menuBackground.png", Texture.class));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        game.getSoundBtn().draw(batch);
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
        super.dispose();
    }
}