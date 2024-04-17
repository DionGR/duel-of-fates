package no.ntnu.dof.view.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import no.ntnu.dof.controller.ScreenController;
import no.ntnu.dof.view.screens.BaseScreen;


public class MenuScreen extends BaseScreen {

    private Sprite background;
    private Sprite soundOn;

    private Table contentTable;

    public MenuScreen() {
        super();
    }

    @Override
    public void show() {
        // Loading skin
        super.show();
        Skin skin = new Skin(Gdx.files.internal("UISkin.json"));

        // Making a centered table to store title and buttons
        contentTable = new Table();
        contentTable.setWidth(stage.getWidth());
        contentTable.align(Align.center|Align.top);
        contentTable.setPosition(0, Gdx.graphics.getHeight());

        Texture logoTexture = new Texture(Gdx.files.internal("logo.png"));
        TextureRegionDrawable logoDrawable = new TextureRegionDrawable(logoTexture);
        ImageButton logoButton = new ImageButton(logoDrawable);

        TextButton lobbiesBtn = new TextButton("Game Lobbies", skin, "default");
        TextButton chooseClassBtn = new TextButton("Choose Class", skin, "default");
        TextButton tutorialBtn = new TextButton("Tutorial", skin, "default");
        TextButton logoutBtn = new TextButton("Log Out", skin, "default");

        // Adding content to table
        contentTable.padTop(10);
        contentTable.add(logoButton).padBottom(10).row();
        contentTable.add(lobbiesBtn).padBottom(10).width(150).height(50).row();
        contentTable.add(chooseClassBtn).padBottom(10).width(150).height(50).row();
        contentTable.add(tutorialBtn).padBottom(10).width(150).height(50).row();;
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

        tutorialBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenController.transitionToTutorial();
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