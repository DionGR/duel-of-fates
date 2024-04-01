package no.ntnu.dof.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

public class LoginScreen implements Screen {
    private Stage stage;
    private DuelOfFates game;
    private Label feedbackLabel;
    private SpriteBatch spriteBatch;
    private AssetManager assetManager;

    public LoginScreen(DuelOfFates game, SpriteBatch spriteBatch, AssetManager assetManager) {
        this.game = game;
        this.spriteBatch = spriteBatch;
        this.assetManager = assetManager;
        initializeUI();
    }

    private void initializeUI() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        feedbackLabel = new Label("", skin);
        table.add(feedbackLabel).padBottom(20).row();

        TextField emailField = new TextField("admin@gmail.com", skin);
        TextField passwordField = new TextField("admin123", skin);
        passwordField.setPasswordCharacter('*');
        passwordField.setPasswordMode(true);

        TextButton loginButton = new TextButton("Login", skin);
        loginButton.addListener(event -> {
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();
            attemptLogin(email, password);
            return false;
        });

        table.add(emailField).fillX().uniformX().padBottom(10).row();
        table.add(passwordField).fillX().uniformX().padBottom(10).row();
        table.add(loginButton).fillX().uniformX();
    }

    private void attemptLogin(String email, String password) {
        if (!email.isEmpty() && !password.isEmpty()) {
            ServiceLocator.getAuthService().signIn(email, password, new AuthCallback() {
                @Override
                public void onSuccess() {
                    Gdx.app.postRunnable(() -> game.transitionToMenu());
                }

                @Override
                public void onError(String message) {
                    Gdx.app.postRunnable(() -> feedbackLabel.setText("Login failed: " + message));
                }
            });
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
    public void hide() {
        // It's crucial to not dispose shared assets here
    }

    @Override
    public void dispose() {
        // Dispose only the Stage here, as it's exclusive to LoginScreen.
        stage.dispose();
        // Do NOT dispose spriteBatch or assets from assetManager here.
    }
}
