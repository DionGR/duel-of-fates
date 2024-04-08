package no.ntnu.dof.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import no.ntnu.dof.controller.DuelOfFates;
import no.ntnu.dof.controller.ScreenManager;
import no.ntnu.dof.controller.network.LobbyService;
import no.ntnu.dof.controller.network.ServiceLocator;
import no.ntnu.dof.model.GameLobby;

public class LobbyScreen extends BaseScreen {

    private Stage stage;
    private DuelOfFates game;
    private Skin skin;
    private Table contentTable;
    private Label lobbyTitle;
    private GameLobby gameLobby;
    private boolean isCreator;
    private TextButton guestButton;

    public LobbyScreen(DuelOfFates game, GameLobby gameLobby) {
        super();
        this.game = game;
        this.gameLobby = gameLobby;
        this.isCreator = gameLobby.getCreator().equals(game.getCurrentUser());
    }

    @Override
    public void show() {
        this.skin = new Skin(Gdx.files.internal("uiskin.json"));
        this.stage = new Stage(new ScreenViewport(), this.batch); // Use the batch from BaseScreen
        // Setting up the UI components specific to LobbyScreen
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
            // Set up listener for starting the game...

            TextButton.TextButtonStyle deleteLobbyStyle = new TextButton.TextButtonStyle(skin.get(TextButton.TextButtonStyle.class));
            deleteLobbyStyle.up = skin.newDrawable("default-round", skin.getColor("red"));
            deleteLobbyStyle.down = skin.newDrawable("default-round-down", skin.getColor("red")); // Assuming you have a down state drawable
            TextButton deleteLobbyButton = new TextButton("Delete Lobby", deleteLobbyStyle);
            deleteLobbyButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    ServiceLocator.getLobbyService().deleteLobby(gameLobby.getLobbyId(), new LobbyService.LobbyDeletionCallback() {
                        @Override
                        public void onSuccess() {
                            Gdx.app.log("LobbyDeletion", "Lobby successfully deleted.");
                            Gdx.app.postRunnable(ScreenManager::popScreen);
                        }

                        @Override
                        public void onFailure(Throwable throwable) {
                            Gdx.app.error("LobbyDeletion", "Failed to delete the lobby.", throwable);
                            // Show error message or handle failure
                        }
                    });
                }
            });

            contentTable.add(startGameButton).colspan(2).padBottom(10).width(150).height(50).row();
            contentTable.add(deleteLobbyButton).colspan(2).padBottom(10).width(150).height(50).row();
        } else {
            TextButton joinGameButton = new TextButton("Join Lobby", skin, "default");
            joinGameButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    ServiceLocator.getLobbyService().joinLobby(new LobbyService.LobbyJoinCallback() {
                        @Override
                        public void onSuccess() {
                            Gdx.app.log("LobbyJoin", "Successfully joined the lobby.");
                            gameLobby.setGuest(game.getCurrentUser());
                            Gdx.app.postRunnable(() -> {
                                // Update the guestButton text
                                guestButton.setText(game.getCurrentUser().getEmail());
                                contentTable.invalidateHierarchy();
                            });
                        }

                        @Override
                        public void onFailure(Throwable throwable) {
                            Gdx.app.error("LobbyJoin", "Failed to join the lobby.", throwable);

                        }
                    }, gameLobby, game.getCurrentUser());
                }
            });

            contentTable.add(joinGameButton).colspan(2).padBottom(10).width(150).height(50);
        }

        stage.addActor(contentTable);
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
        stage.dispose();
        super.dispose();
    }

}
