package no.ntnu.dof.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import no.ntnu.dof.DuelOfFates;
import no.ntnu.dof.AuthInterface;
import no.ntnu.dof.AuthCallback;

public class LoginScreen implements Screen {
    private Stage stage;
    private DuelOfFates game;
    private AuthInterface auth;

    public LoginScreen(DuelOfFates game, AuthInterface auth) {
        this.game = game;
        this.auth = auth;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        final TextField emailField = new TextField("", skin);
        final TextField passwordField = new TextField("", skin);
        passwordField.setPasswordCharacter('*');
        passwordField.setPasswordMode(true);

        TextButton loginButton = new TextButton("Login", skin);
        loginButton.addListener(event -> {
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();
            if (!email.isEmpty() && !password.isEmpty()) {
                auth.signIn(email, password, new AuthCallback() {
                    @Override
                    public void onSuccess() {
                        Gdx.app.log("LoginSuccess", "User has successfully logged in.");
                    }

                    @Override
                    public void onError(String message) {
                        Gdx.app.log("LoginError", message);
                        // Show error message to the user
                    }
                });
            }
            return false;
        });

        table.add("Email").padBottom(10);
        table.row();
        table.add(emailField).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add("Password").padBottom(10);
        table.row();
        table.add(passwordField).fillX().uniformX();
        table.row();
        table.add(loginButton).fillX().uniformX().padTop(10);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        // Implement this method if there's a need to pause specific activities
    }

    @Override
    public void resume() {
        // Implement this method if there's a need to resume specific activities
    }

    @Override
    public void hide() {
        // Optionally clear or dispose of resources when this screen is no longer visible
    }

    @Override
    public void dispose() {
        stage.dispose();
        // Dispose other resources if necessary
    }
}