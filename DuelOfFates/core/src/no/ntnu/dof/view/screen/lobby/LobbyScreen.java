package no.ntnu.dof.view.screen.lobby;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import lombok.Setter;
import no.ntnu.dof.controller.lobby.GameLobbyController;
import no.ntnu.dof.controller.lobby.LobbyViewListener;
import no.ntnu.dof.model.communication.GameLobby;
import no.ntnu.dof.model.communication.User;
import no.ntnu.dof.view.screen.BaseScreen;

public class LobbyScreen extends BaseScreen {
    private final GameLobby gameLobby;
    private Skin skin;
    private Table contentTable;
    @Setter
    private LobbyViewListener listener;

    @Setter
    private boolean isCreator;
    private TextButton guestButton;
    private Label errorLabel;

    public LobbyScreen(User currentUser, GameLobby gameLobby) {
        this.gameLobby = gameLobby;
        this.isCreator = gameLobby.getCreator().equals(currentUser);
    }

    @Override
    public void show() {
        super.show();
        // Setting up the UI components specific to LobbyScreen
        this.skin = new Skin(Gdx.files.internal("UISkin.json"));
        TextButton.TextButtonStyle textButtonStyle = skin.get(TextButton.TextButtonStyle.class);
        textButtonStyle.font.getData().setScale(getScreenHeight() * 0.002f);

        contentTable = new Table();
        contentTable.setFillParent(true);
        stage.addActor(contentTable);

        Label lobbyTitle = new Label(gameLobby.getTitle(), skin, "big");
        lobbyTitle.setFontScale(getScreenHeight() * 0.003f);
        contentTable.add(lobbyTitle).expandX().row();

        // Making a centered table to store title and buttons
        contentTable = new Table();
        contentTable.setWidth(stage.getWidth());
        contentTable.align(Align.center | Align.top);
        contentTable.setPosition(0, Gdx.graphics.getHeight());

        // Adding content to table
        contentTable.padTop(getScreenHeight() * 0.1f);
        contentTable.add(lobbyTitle).colspan(2).row();
        contentTable.add(new TextButton(gameLobby.getCreator().getEmail(), skin, "default")).padTop(getScreenHeight() * 0.1f).padBottom(getScreenHeight() * 0.1f).padRight(getScreenWidth() * 0.05f).width(getScreenWidth() * 0.2f).height(getScreenHeight() * 0.12f);

        // If gameLobby.getGuest is not null, display guest.getEmail instead of "<Available>"
        String guestDisplay = gameLobby.getGuest() != null ? gameLobby.getGuest().getEmail() : "<Available>";
        guestButton = new TextButton(guestDisplay, skin, "default");
        contentTable.add(guestButton).padTop(getScreenHeight() * 0.1f).padBottom(getScreenHeight() * 0.1f).width(getScreenWidth() * 0.2f).height(getScreenHeight() * 0.12f).row();

        // If user is the creator, show "start game" button, else if user is a guest --> show join lobby button
        if (isCreator) {
            // Initialize creator exclusive buttons
            TextButton.TextButtonStyle startGameStyle = new TextButton.TextButtonStyle(skin.get(TextButton.TextButtonStyle.class));
            startGameStyle.up = skin.newDrawable("default-round", skin.getColor("green"));
            startGameStyle.down = skin.newDrawable("default-round-down", skin.getColor("green"));
            TextButton startGameButton = new TextButton("Start Game", startGameStyle);

            // Listener for starting the game
            startGameButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    listener.startGame();
                }
            });

            TextButton.TextButtonStyle deleteLobbyStyle = new TextButton.TextButtonStyle(skin.get(TextButton.TextButtonStyle.class));
            deleteLobbyStyle.up = skin.newDrawable("default-round", skin.getColor("red"));
            deleteLobbyStyle.down = skin.newDrawable("default-round-down", skin.getColor("red")); // Assuming you have a down state drawable
            TextButton deleteLobbyButton = new TextButton("Delete Lobby", deleteLobbyStyle);
            deleteLobbyButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    listener.deleteLobby(true);
                }
            });

            contentTable.add(startGameButton).colspan(2).padBottom(getScreenHeight() * 0.04f).width(getScreenWidth() * 0.2f).height(getScreenHeight() * 0.12f).row();
            contentTable.add(deleteLobbyButton).colspan(2).padBottom(10).width(getScreenWidth() * 0.2f).height(getScreenHeight() * 0.12f).row();
        } else {
            TextButton joinGameButton = new TextButton("Join Lobby", skin, "default");
            joinGameButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    listener.joinLobby();
                }
            });

            TextButton exitLobbyButton = new TextButton("Exit Lobby", skin, "default");
            exitLobbyButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    listener.exitLobby();
                }
            });

            contentTable.add(joinGameButton).colspan(2).padBottom(getScreenHeight() * 0.04f).width(getScreenWidth() * 0.2f).height(getScreenHeight() * 0.12f).row();
            contentTable.add(exitLobbyButton).colspan(3).width(getScreenWidth() * 0.2f).height(getScreenHeight() * 0.12f);
        }
        errorLabel = new Label("", skin);
        errorLabel.setColor(1, 0, 0, 1);
        contentTable.row();
        contentTable.add(errorLabel).padTop(10);

        stage.addActor(contentTable);
        Gdx.input.setInputProcessor(stage);
    }

    public void updateGuestInfo(String guestEmail) {
        Gdx.app.postRunnable(() -> {
            guestButton.setText(guestEmail); // Update the guest button text with the new guest email
            contentTable.invalidateHierarchy(); // Refresh the UI layout
        });
    }

    public void showError(String message) {
        Gdx.app.postRunnable(() -> errorLabel.setText(message));
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
    public void hide() {
        super.hide();
        if (listener instanceof GameLobbyController) {
            ((GameLobbyController) listener).stopListeningForLobbyUpdates();
        }
    }

    @Override
    public void dispose() {
        if (listener instanceof GameLobbyController) {
            ((GameLobbyController) listener).stopListeningForLobbyUpdates();
        }
        stage.dispose();
        skin.dispose();
        contentTable.clear();
        guestButton.clear();
        errorLabel.clear();
        super.dispose();
    }
}
