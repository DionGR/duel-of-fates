package no.ntnu.dof.view.screens.lobby;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import lombok.Setter;
import no.ntnu.dof.controller.lobby.LobbyViewListener;
import no.ntnu.dof.controller.lobby.GameLobbyController;
import no.ntnu.dof.model.GameLobby;
import no.ntnu.dof.model.User;
import no.ntnu.dof.view.screens.BaseScreen;

public class LobbyScreen extends BaseScreen {

    private Skin skin;
    private Table contentTable;
    private Label lobbyTitle;
    private GameLobby gameLobby;

    @Setter
    private LobbyViewListener listener;

    @Setter
    private boolean isCreator;
    private TextButton guestButton;
    private Label errorLabel;

    public LobbyScreen(User currentUser, GameLobby gameLobby) {
        super();
        this.gameLobby = gameLobby;
        this.isCreator = gameLobby.getCreator().equals(currentUser);
    }

    @Override
    public void show() {
        super.show();
        // Setting up the UI components specific to LobbyScreen
        this.skin = new Skin(Gdx.files.internal("UISkin.json"));

        contentTable = new Table();
        contentTable.setFillParent(true);
        stage.addActor(contentTable);

        lobbyTitle = new Label(gameLobby.getTitle(), skin, "big");
        contentTable.add(lobbyTitle).expandX().padTop(20).row();

        // Making a centered table to store title and buttons
        contentTable = new Table();
        contentTable.setWidth(stage.getWidth());
        contentTable.align(Align.center|Align.top);
        contentTable.setPosition(0, Gdx.graphics.getHeight());

        // Adding content to table
        contentTable.padTop(30);
        contentTable.add(lobbyTitle).colspan(2).padBottom(50).row();
        contentTable.add(new TextButton(gameLobby.getCreator().getEmail(), skin, "default")).padRight(30).padBottom(100).width(150).height(50);

        // If gameLobby.getGuest is not null, display guest.getEmail instead of "<Available>"
        String guestDisplay = gameLobby.getGuest() != null ? gameLobby.getGuest().getEmail() : "<Available>";
        guestButton = new TextButton(guestDisplay, skin, "default");
        contentTable.add(guestButton).padBottom(100).width(150).height(50).row();

        // If user is the creator, show "start game" button, else if user is a guest --> show join lobby button
        if (isCreator) {
            // Initalize creator exclusive buttons
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

            contentTable.add(startGameButton).colspan(2).padBottom(10).width(150).height(50).row();
            contentTable.add(deleteLobbyButton).colspan(2).padBottom(10).width(150).height(50).row();
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

            contentTable.add(joinGameButton).colspan(2).padBottom(10).width(150).height(50);
            contentTable.add(exitLobbyButton).colspan(3).padBottom(10).width(150).height(50);
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
        Gdx.app.postRunnable(() -> {
            errorLabel.setText(message);
        });
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
