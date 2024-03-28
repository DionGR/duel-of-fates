package no.ntnu.dof.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import no.ntnu.dof.controller.DuelOfFates;
import no.ntnu.dof.controller.network.AuthCallback;
import no.ntnu.dof.controller.network.ServiceLocator;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class LoginScreen implements Screen {
    private Stage stage;
    private DuelOfFates game;
    private Label feedbackLabel;
    private Texture backgroundTexture;
    private SpriteBatch spriteBatch;


    public LoginScreen(DuelOfFates game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        backgroundTexture = new Texture(Gdx.files.internal("menuBackground.png"));
        spriteBatch = new SpriteBatch();

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Initialize feedback label
        feedbackLabel = new Label("", skin);
        table.add(feedbackLabel).padBottom(20).row();

        final TextField emailField = new TextField("", skin);
        final TextField passwordField = new TextField("", skin);
        passwordField.setPasswordCharacter('*');
        passwordField.setPasswordMode(true);

        TextButton loginButton = new TextButton("Login", skin);
        loginButton.addListener(event -> {
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();
            if (!email.isEmpty() && !password.isEmpty()) {
                ServiceLocator.getAuthService().signIn(email, password, new AuthCallback() {
                    @Override
                    public void onSuccess() {
                        Gdx.app.log("LoginSuccess", "User has successfully logged in.");
                        // Transition to MenuScreen upon successful login
                        game.transitionToMenu();
                    }

                    @Override
                    public void onError(String message) {
                        Gdx.app.log("LoginError", message);
                        Gdx.app.postRunnable(() -> {
                            feedbackLabel.setText("Login failed: " + message);
                        });
                    }
                });
            }
            return false;
        });

        table.add(emailField).fillX().uniformX().padBottom(10).row();
        table.add(passwordField).fillX().uniformX().padBottom(10).row();
        table.add(loginButton).fillX().uniformX();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        spriteBatch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        spriteBatch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    // Implement other necessary Screen methods: resize, pause, resume, hide, dispose
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
        backgroundTexture.dispose();
        spriteBatch.dispose();
    }

}
