package no.ntnu.dof.view.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import java.util.Arrays;
import java.util.List;

import no.ntnu.dof.controller.application.ScreenController;
import no.ntnu.dof.view.screen.BaseScreen;

import no.ntnu.dof.controller.application.ScreenController;
import no.ntnu.dof.view.screen.BaseScreen;
public class MenuScreen extends BaseScreen {
    @Override
    public void show() {
        // Loading skin
        super.show();
        Skin skin = new Skin(Gdx.files.internal("UISkin.json"));

        // Making a centered table to store title and buttons
        Table contentTable = new Table();
        contentTable.setWidth(stage.getWidth());
        contentTable.align(Align.center | Align.top);
        contentTable.setPosition(0, Gdx.graphics.getHeight());

        Texture logoTexture = new Texture(Gdx.files.internal("logo.png"));
        TextureRegionDrawable logoDrawable = new TextureRegionDrawable(logoTexture);
        ImageButton logoButton = new ImageButton(logoDrawable);

        TextButton lobbiesBtn = new TextButton("Game Lobbies", skin, "default");
        TextButton chooseClassBtn = new TextButton("Choose Class", skin, "default");
        TextButton tutorialBtn = new TextButton("Tutorial", skin, "default");
        TextButton logoutBtn = new TextButton("Log Out", skin, "default");

        TextButton.TextButtonStyle textButtonStyle = skin.get(TextButton.TextButtonStyle.class);
        textButtonStyle.font.getData().setScale(getScreenHeight()*0.003f);

        // Adding content to table
        contentTable.padTop(getScreenHeight()*0.08f);
        contentTable.add(logoButton).padBottom(getScreenHeight()*0.04f).width(getScreenWidth()*0.6f).row();
        contentTable.add(lobbiesBtn).padBottom(getScreenHeight()*0.04f).width(getScreenWidth()*0.3f).height(getScreenHeight()*0.12f).row();
        contentTable.add(chooseClassBtn).padBottom(getScreenHeight()*0.04f).width(getScreenWidth()*0.3f).height(getScreenHeight()*0.12f).row();
        contentTable.add(tutorialBtn).padBottom(getScreenHeight()*0.04f).width(getScreenWidth()*0.3f).height(getScreenHeight()*0.12f).row();;
        contentTable.add(logoutBtn).width(getScreenWidth()*0.3f).height(getScreenHeight()*0.12f);

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
    public void dispose() {
        super.dispose();
        stage.dispose();
    }
}