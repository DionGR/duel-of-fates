package no.ntnu.dof.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import no.ntnu.dof.controller.DuelOfFates;

public class LobbyScreen implements Screen {

    private Stage stage;
    private DuelOfFates game;
    private SpriteBatch batch;
    private Sprite background;
    private Skin skin;
    private Table contentTable;
    private Label lobbyTitle;
    private Sprite soundOn;
    private Sprite soundOff;
    private Sprite backBtn;

    public LobbyScreen(DuelOfFates game) {
        this.game = game;
    }

    @Override
    public void show() {
        // Loading skin
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage(new ScreenViewport());

        // Making a centered table to store title and buttons
        contentTable = new Table();
        contentTable.setWidth(stage.getWidth());
        contentTable.align(Align.center|Align.top);
        contentTable.setPosition(0, Gdx.graphics.getHeight());

        lobbyTitle = new Label("<Lobby Title>", skin, "big");

        // Adding content to table
        contentTable.padTop(30);
        contentTable.add(lobbyTitle).colspan(2).padBottom(50).row();
        contentTable.add(new TextButton("<Host Name>", skin, "default")).padRight(30).padBottom(100).width(150).height(50);
        contentTable.add(new TextButton("<Guest Name>", skin, "default")).padBottom(100).width(150).height(50).row();
        contentTable.add(new TextButton("Start Game", skin, "default")).colspan(2).padBottom(10).width(150).height(50);


        stage.addActor(contentTable);

        batch = new SpriteBatch();

        // Loading background
        background = new Sprite(new Texture(Gdx.files.internal("menuBackground.png")));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Setting sound buttons
        soundOn = new Sprite(new Texture(Gdx.files.internal("soundOn.png")));
        soundOn.setSize(80, 80);
        soundOff = new Sprite(new Texture(Gdx.files.internal("soundOff.png")));
        soundOff.setSize(80,80);

        // Setting back button
        backBtn = new Sprite(new Texture(Gdx.files.internal("backBtn.png")));
        backBtn.setSize(50,50);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        soundOn.draw(batch);
        backBtn.setPosition(150, Gdx.graphics.getHeight()-backBtn.getHeight()-20);
        backBtn.draw(batch);
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