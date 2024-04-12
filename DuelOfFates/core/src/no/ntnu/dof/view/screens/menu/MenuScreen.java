package no.ntnu.dof.view.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import no.ntnu.dof.controller.ScreenController;
import no.ntnu.dof.view.screens.BaseScreen;


public class MenuScreen extends BaseScreen {

    private Stage stage;
    private Sprite background;
    private Sprite soundOn;

    public MenuScreen() {
        super();
    }

    @Override
    public void show() {
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
        super.dispose();
        stage.dispose();
    }
}