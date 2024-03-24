package no.ntnu.dof.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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


public class MenuScreen implements Screen {

    private Stage stage;
    private DuelOfFates game;
    private SpriteBatch batch;
    private Sprite background;
    private Skin skin;
    private Table contentTable;
    private TextButton lobbiesBtn;
    private TextButton tutorialBtn;
    private TextButton logoutBtn;
    private Label gameTitle;
    private Sprite soundOn;
    private Sprite soundOff;

    public MenuScreen(DuelOfFates game) {
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

        gameTitle = new Label("Duel of Fates", skin, "default");
        lobbiesBtn = new TextButton("Game Lobbies", skin, "default");
        tutorialBtn = new TextButton("Tutorial", skin, "default");
        logoutBtn = new TextButton("Log out", skin, "default");

        lobbiesBtn.setSize(150,100);

//        for (Actor btn:contentTable.getChildren()) {
//            btn.setHeight(50);
//            btn.setWidth(150);
//        }

        // Adding content to table
        contentTable.padTop(30);
        contentTable.add(gameTitle).padBottom(30).row();
        contentTable.add(lobbiesBtn).padBottom(30).width(150).height(50).row();
        contentTable.add(tutorialBtn).padBottom(30).width(150).height(50).row();;
        contentTable.add(logoutBtn).width(150).height(50);

        stage.addActor(contentTable);


        //
//        lobbiesBtn.addListener(new ClickListener() {
//            @Override
//            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//                System.out.println("test");
//                super.touchUp(event, x, y, pointer, button);
//            }
//        });

        batch = new SpriteBatch();

        // Loading background
        background = new Sprite(new Texture(Gdx.files.internal("menuBackground.png")));
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