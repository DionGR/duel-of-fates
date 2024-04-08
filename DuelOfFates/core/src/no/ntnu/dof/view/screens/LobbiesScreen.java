package no.ntnu.dof.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.List;

import no.ntnu.dof.controller.DuelOfFates;
import no.ntnu.dof.controller.ScreenManager;
import no.ntnu.dof.controller.network.LobbyService;
import no.ntnu.dof.controller.network.ServiceLocator;
import no.ntnu.dof.model.GameLobbies;
import no.ntnu.dof.model.GameLobby;
import no.ntnu.dof.model.User;

public class LobbiesScreen implements Screen {

    private Stage stage;
    private SpriteBatch batch;
    private Sprite background;
    private Skin skin;
    private Table contentTable;
    private TextButton lobbyBtn;
    private TextButton createLobbyBtn;
    private Label lobbiesTitle;
    private Sprite soundOn;
    private Sprite soundOff;
    private Sprite backBtn;
    private AssetManager assetManager;
    private Rectangle backBtnBounds;
    private DuelOfFates game;

    public LobbiesScreen(DuelOfFates game, SpriteBatch batch, AssetManager assetManager) {
        this.batch = batch;
        this.assetManager = assetManager;
        this.game = game;
        backBtnBounds = new Rectangle();
    }

    @Override
    public void show() {
        // Loading skin
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage(new ScreenViewport());

        // Making a centered table to store title and buttons
        contentTable = new Table();
        contentTable.setWidth(stage.getWidth());
        contentTable.align(Align.center|Align.top);
        contentTable.setPosition(0, Gdx.graphics.getHeight());

        lobbiesTitle = new Label("Lobbies", skin, "big");
        lobbyBtn = new TextButton("<Lobby Title>\n<Host Name>", skin, "default");

        // Adding content to table
        contentTable.padTop(30);
        contentTable.add(lobbiesTitle).colspan(2).padBottom(30).row();
        GameLobbies gameLobbies = game.getGameLobbies();
        for (GameLobby lobby : gameLobbies.getLobbies()) {
            TextButton lobbyButton = new TextButton(lobby.getTitle() + "\n" + lobby.getCreator().getEmail(), skin, "default");
            lobbyButton.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    ScreenManager.transitionToLobby(lobby);
                    return true;
                }
            });
            contentTable.add(lobbyButton).padBottom(10).width(300).height(50).row();
        }
        stage.addActor(contentTable);

        batch = new SpriteBatch();

        // Loading background
        background = new Sprite(new Texture(Gdx.files.internal("menuBackground.png")));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Setting sound buttons
        soundOn = new Sprite(new Texture(Gdx.files.internal("soundOn.png")));
        soundOn.setSize(80, 80);
        soundOff = new Sprite(new Texture(Gdx.files.internal("soundOff.png")));
        soundOff.setSize(80,80);

        // Setting back button
        backBtn = new Sprite(new Texture(Gdx.files.internal("backBtn.png")));
        backBtn.setSize(50,50);
        // Setting back button bounds for touch detection
        backBtnBounds.set(150, Gdx.graphics.getHeight() - backBtn.getHeight() - 20, backBtn.getWidth(), backBtn.getHeight());

        // Add a general input listener to the stage for handling back button clicks
        stage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (backBtnBounds.contains(x, y)) {
                    // Perform the action associated with the back button
                    Gdx.app.postRunnable(ScreenManager::popScreen);
                    return true;
                }
                return false;
            }
        });

        // Setting create lobby button
        createLobbyBtn = new TextButton("Create Lobby", skin, "default");
        createLobbyBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showCreateLobbyDialog();
            }
        });

        // Add the button directly to the stage
        stage.addActor(createLobbyBtn);

        // Positioning the create lobby button at the top right with some margin
        float margin = 20; // Adjust the margin value as needed
        float buttonX = Gdx.graphics.getWidth() - createLobbyBtn.getWidth() - margin;
        float buttonY = Gdx.graphics.getHeight() - createLobbyBtn.getHeight() - margin;

        createLobbyBtn.setPosition(buttonX, buttonY);

        // Set up the Firebase listener
        ServiceLocator.getLobbyService().listenForLobbyChanges(lobbies -> Gdx.app.postRunnable(() -> {
            game.getGameLobbies().setLobbies(lobbies); // Assuming GameLobbies has a setter for the lobbies list
            updateLobbiesList();
        }));

        Gdx.input.setInputProcessor(stage);
    }

    private void showCreateLobbyDialog() {
        Dialog dialog = new Dialog("Create Lobby", skin) {
            @Override
            protected void result(Object object) {
                boolean isConfirmed = (Boolean) object;
                if (isConfirmed) {
                    String lobbyTitle = ((TextField) findActor("lobbyTitleField")).getText();
                    createNewLobby(lobbyTitle);
                }
                this.hide();
            }
        };
        dialog.padTop(30).padBottom(30);

        TextField lobbyTitleField = new TextField("", skin);
        lobbyTitleField.setMessageText("Enter Lobby Title");
        lobbyTitleField.setName("lobbyTitleField");
        dialog.getContentTable().add(lobbyTitleField).width(200);

        TextButton btnCancel = new TextButton("Cancel", skin);
        btnCancel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.hide();
            }
        });

        dialog.button(btnCancel, false); // false - do not create a new lobby

        TextButton btnConfirm = new TextButton("Confirm", skin);
        dialog.button(btnConfirm, true); // true - confirm and create a new lobby

        dialog.show(stage);
    }

    private void createNewLobby(String title) {
        User currentUser = game.getCurrentUser();
        GameLobby newLobby = new GameLobby(currentUser, title);
        ServiceLocator.getLobbyService().createLobby(new LobbyService.LobbyCreationCallback() {
            @Override
            public void onSuccess(GameLobby lobby) {

            }

            @Override
            public void onFailure(Throwable throwable) {
                // Handle failure, e.g., show an error message
            }
        }, newLobby);
        // Refresh the UI to show the new lobby
    }

    private void updateLobbiesList() {
        // Clear the existing content but preserve the title
        contentTable.clearChildren();
        contentTable.add(lobbiesTitle).colspan(2).padBottom(30).row();

        // Re-add each lobby as a button
        for (GameLobby lobby : game.getGameLobbies().getLobbies()) {
            TextButton lobbyButton = new TextButton(lobby.getTitle() + "\n" + lobby.getCreator().getEmail(), skin, "default");
            lobbyButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    ScreenManager.transitionToLobby(lobby);
                }
            });
            contentTable.add(lobbyButton).padBottom(10).width(300).height(50).row();
        }
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        soundOn.draw(batch);
        backBtn.setPosition(150, Gdx.graphics.getHeight()-backBtn.getHeight()-20);
        backBtn.draw(batch);
        createLobbyBtn.setPosition(Gdx.graphics.getWidth()-200, Gdx.graphics.getHeight()-60);
        createLobbyBtn.draw(batch, 1);
        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
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
    }

}
