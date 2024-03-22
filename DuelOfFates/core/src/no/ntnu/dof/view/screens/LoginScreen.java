package no.ntnu.dof.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import no.ntnu.dof.controller.DuelOfFates;
import no.ntnu.dof.controller.network.AuthCallback;
import no.ntnu.dof.controller.network.LobbyService;
import no.ntnu.dof.controller.network.ServiceLocator;

public class LoginScreen implements Screen {
    private Stage stage;
    private DuelOfFates game;
    private Label feedbackLabel;

    public LoginScreen(DuelOfFates game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Initialize the feedback label
        Label.LabelStyle labelStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        feedbackLabel = new Label("", labelStyle);
        feedbackLabel.setPosition(Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 - 100); // Adjust position as needed

        Table table = new Table();
        table.setFillParent(true);
        table.add(feedbackLabel).padTop(20); // Display the feedback label at the top of the table
        stage.addActor(table);

        // Text Field and Button Styles
        TextFieldStyle textFieldStyle = new TextFieldStyle();
        textFieldStyle.font = new BitmapFont();
        textFieldStyle.fontColor = Color.WHITE;
        textFieldStyle.background = createDrawableFromColor(Color.DARK_GRAY, 1, 1);

        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = new BitmapFont();
        textButtonStyle.up = createDrawableFromColor(Color.LIGHT_GRAY, 1, 1);
        textButtonStyle.down = createDrawableFromColor(Color.GRAY, 1, 1);
        textButtonStyle.over = createDrawableFromColor(Color.DARK_GRAY, 1, 1);

        final TextField emailField = new TextField("admin@gmail.com", textFieldStyle);
        final TextField passwordField = new TextField("admin123", textFieldStyle);
        passwordField.setPasswordCharacter('*');
        passwordField.setPasswordMode(true);

        TextButton loginButton = new TextButton("Login", textButtonStyle);
        loginButton.addListener(event -> {
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();
            if (!email.isEmpty() && !password.isEmpty()) {
                ServiceLocator.getAuthService().signIn(email, password, new AuthCallback() {
                    @Override
                    public void onSuccess() {
                        Gdx.app.log("LoginSuccess", "User has successfully logged in.");


                        // Attempt to create a lobby after successful login
                        ServiceLocator.getLobbyService().createLobby(new LobbyService.LobbyCreationCallback() {
                            @Override
                            public void onSuccess(String lobbyId) {
                                Gdx.app.log("LobbyCreationSuccess", "Lobby created successfully with ID: " + lobbyId);
                                Gdx.app.postRunnable(() -> {
                                    feedbackLabel.setText("Lobby created successfully!");
                                });
                                // Navigate to the lobby screen or update the UI accordingly
                                // For example, you could pass the lobbyId to the next screen to join the created lobby
                            }

                            @Override
                            public void onFailure(Throwable throwable) {
                                Gdx.app.log("LobbyCreationError", "Failed to create lobby: " + throwable.getMessage());
                                Gdx.app.postRunnable(() -> {
                                    feedbackLabel.setText("Failed to create lobby. Try again.");
                                });
                            }
                        });

                    }


                    @Override
                    public void onError(String message) {
                        Gdx.app.log("LoginError", message);
                        // Show error message to the user
                        Gdx.app.postRunnable(() -> {
                            feedbackLabel.setText("Login failed: " + message);
                        });
                    }
                });
            }
            return false;
        });

        table.add(emailField).fillX().uniformX().padBottom(10);
        table.row();
        table.add(passwordField).fillX().uniformX().padBottom(10);
        table.row();
        table.add(loginButton).fillX().uniformX();
    }

    private Drawable createDrawableFromColor(Color color, int width, int height) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return new TextureRegionDrawable(texture);
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
        // Dispose of other resources if necessary
    }
}
