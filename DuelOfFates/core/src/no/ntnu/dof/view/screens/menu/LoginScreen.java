package no.ntnu.dof.view.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.Arrays;

import no.ntnu.dof.controller.ScreenController;

public class LoginScreen implements Screen {
    private Stage stage;
    private Label feedbackLabel;
    private SpriteBatch spriteBatch;
    private AssetManager assetManager;
    private LoginViewListener listener;
    private TextField emailField;
    private TextField passwordField;
    private Skin skin;

    public LoginScreen(SpriteBatch spriteBatch, AssetManager assetManager) {
        this.spriteBatch = spriteBatch;
        this.assetManager = assetManager;
        initializeUI();
    }

    public void initializeUI() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label headline = new Label("Log in / Sign up", skin);
        table.add(headline).padTop(10).padBottom(30).center().row();

        feedbackLabel = new Label("", skin);
        table.add(feedbackLabel).padBottom(20).colspan(2).center().row();

        float fieldButtonWidth = 250f;
        float buttonHeight = 50f;

        emailField = new TextField("", skin);
        passwordField = new TextField("", skin);
        passwordField.setPasswordCharacter('*');
        passwordField.setPasswordMode(true);

        TextButton loginButton = new TextButton("Login", skin);
        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String email = emailField.getText().trim();
                String password = passwordField.getText().trim();
                attemptLogin(email, password);
            }
        });

        table.add(emailField).width(fieldButtonWidth).padBottom(10).row();
        table.add(passwordField).width(fieldButtonWidth).padBottom(10).row();
        table.add(loginButton).width(fieldButtonWidth).height(buttonHeight).padBottom(10).row();

        Label signUpPlaceholder = new Label("", skin);
        table.add(signUpPlaceholder).width(fieldButtonWidth).height(buttonHeight).row();
    }

    public void showLoginFailed(String message) {
        Gdx.app.postRunnable(() -> {
            feedbackLabel.setText("Login failed, please sign up! " + message);
            TextButton signUpButton = new TextButton("Sign up", skin);
            signUpButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    listener.onSignUpAttempt(emailField.getText().trim(), passwordField.getText().trim());
                }
            });

            Table table = (Table) stage.getActors().first();
            table.getCells().peek().setActor(signUpButton);
            signUpButton.getLabel().setFontScale(1.0f);  // Adjust font scale if needed to match the login button
            table.invalidateHierarchy();  // Refresh layout to apply changes.
        });
    }

    public interface LoginViewListener {
        void onLoginAttempt(String email, String password);
        void onSignUpAttempt(String email, String password);
    }

    public void setLoginViewListener(LoginViewListener listener) {
        this.listener = listener;
    }

    private void attemptLogin(String email, String password) {
        if (listener != null) {
            listener.onLoginAttempt(email, password);
        }
    }

    @Override
    public void show() {
        Gdx.app.log("LoginScreen", "Showing Login Screen");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        Texture bgTexture = assetManager.get("menuBackground.png", Texture.class);
        spriteBatch.draw(bgTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        spriteBatch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
    }
}
