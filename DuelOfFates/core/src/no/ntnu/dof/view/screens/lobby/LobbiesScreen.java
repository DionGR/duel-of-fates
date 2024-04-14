package no.ntnu.dof.view.screens.lobby;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import lombok.Setter;
import no.ntnu.dof.controller.lobby.LobbiesViewListener;
import no.ntnu.dof.model.GameLobbies;
import no.ntnu.dof.model.GameLobby;
import no.ntnu.dof.view.screens.ReturnableScreen;

public class LobbiesScreen extends ReturnableScreen {

    private Stage stage;
    private Skin skin;
    private Table contentTable;
    private TextButton createLobbyBtn;
    private Label lobbiesTitle;
    @Setter
    private LobbiesViewListener listener;
    @Setter
    private GameLobbies gameLobbies;

    public LobbiesScreen() {
        super();
    }

    @Override
    public void show() {
        super.show();
        this.skin = new Skin(Gdx.files.internal("uiskin.json"));
        this.stage = new Stage(new ScreenViewport(), this.batch);

        // Making a centered table to store title and buttons
        contentTable = new Table();
        contentTable.setWidth(stage.getWidth());
        contentTable.align(Align.center|Align.top);
        contentTable.setPosition(0, Gdx.graphics.getHeight());

        lobbiesTitle = new Label("Lobbies", skin, "big");
        contentTable.add(lobbiesTitle).expandX().padTop(20).row();

        TextButton lobbyBtn = new TextButton("<Lobby Title>\n<Host Name>", skin, "default");

        // Adding content to table
        contentTable.padTop(30);
        for (GameLobby lobby : gameLobbies.getLobbies()) {
            TextButton lobbyButton = new TextButton(lobby.getTitle() + "\n" + lobby.getCreator().getEmail(), skin, "default");
            lobbyButton.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    listener.transitionToLobby(lobby);
                    return true;
                }
            });
            contentTable.add(lobbyButton).padBottom(10).width(300).height(50).row();
        }

        // Making scrollable table
        final ScrollPane scroller = new ScrollPane(contentTable);

        final Table table = new Table();
        table.setFillParent(true);
        table.add(scroller).fill().expand();

        stage.addActor(table);

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

        Gdx.input.setInputProcessor(stage);
    }

    private void showCreateLobbyDialog() {
        Dialog dialog = new Dialog("Create Lobby", skin) {
            @Override
            protected void result(Object object) {
                boolean isConfirmed = (Boolean) object;
                if (isConfirmed) {
                    String lobbyTitle = ((TextField) findActor("lobbyTitleField")).getText();
                    listener.createNewLobby(lobbyTitle);
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

    public void updateLobbiesList(GameLobbies gameLobbies) {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                LobbiesScreen.this.gameLobbies = gameLobbies;
                if (contentTable != null) {
                    // Clear the existing content but preserve the title
                    contentTable.clearChildren();
                    contentTable.add(lobbiesTitle).expandX().padTop(20).row();

                    // Re-add each lobby as a button
                    for (GameLobby lobby : gameLobbies.getLobbies()) {
                        TextButton lobbyButton = new TextButton(lobby.getTitle() + "\n" + lobby.getCreator().getEmail(), skin, "default");
                        lobbyButton.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                listener.transitionToLobby(lobby);
                            }
                        });
                        contentTable.add(lobbyButton).padBottom(10).width(300).height(50).row();
                    }
                } else {
                    Gdx.app.log("LobbiesScreen", "Attempted to update lobbies list when contentTable is null.");
                }
            }
        });
    }


    @Override
    public void render(float delta) {
        super.render(delta); // This will render the background and the back button
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
