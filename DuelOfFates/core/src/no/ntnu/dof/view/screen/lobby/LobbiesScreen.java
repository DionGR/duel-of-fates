package no.ntnu.dof.view.screen.lobby;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import lombok.Setter;
import no.ntnu.dof.controller.application.ScreenController;
import no.ntnu.dof.controller.lobby.LobbiesViewListener;
import no.ntnu.dof.model.communication.GameLobbies;
import no.ntnu.dof.model.communication.GameLobby;
import no.ntnu.dof.view.screen.ReturnableScreen;

public class LobbiesScreen extends ReturnableScreen {
    private Skin skin;
    private Table contentTable;
    private TextButton createLobbyBtn;
    private TextButton matchHistoryBtn;
    private Label lobbiesTitle;
    @Setter
    private LobbiesViewListener listener;
    @Setter
    private GameLobbies gameLobbies;

    @Override
    public void show() {
        super.show();
        this.skin = new Skin(Gdx.files.internal("UISkin.json"));
        TextButton.TextButtonStyle textButtonStyle = skin.get(TextButton.TextButtonStyle.class);
        textButtonStyle.font.getData().setScale(getScreenHeight() * 0.002f);

        // Making a centered table to store title and buttons
        contentTable = new Table();
        contentTable.setWidth(stage.getWidth());
        contentTable.align(Align.center | Align.top);
        contentTable.setPosition(0, Gdx.graphics.getHeight());

        lobbiesTitle = new Label("Lobbies", skin, "big");
        lobbiesTitle.setFontScale(getScreenHeight() * 0.003f);
        contentTable.add(lobbiesTitle).padTop(20).row();

        // Adding content to table
        contentTable.padTop(30);
        for (GameLobby lobby : gameLobbies.getLobbies()) {
            TextButton lobbyButton = new TextButton(lobby.getTitle() + "\n" + lobby.getCreator().getEmail(), skin, "default");

            lobbyButton.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    listener.transitionToLobby(lobby);
                    Gdx.app.log("LobbiesScreen", "Selected lobby: " + lobby.getTitle());
                    return true;
                }
            });
            contentTable.add(lobbyButton).padBottom(getScreenHeight() * 0.03f).width(getScreenWidth() * 0.22f).height(getScreenHeight() * 0.12f).row();
        }

        // Making scrollable table
        final ScrollPane scroller = new ScrollPane(contentTable);
        scroller.setTouchable(Touchable.childrenOnly);

        final Table table = new Table();
        table.setFillParent(true);
        table.add(scroller).fill().expand();

        stage.addActor(table);

        // Setting create lobby button
        createLobbyBtn = new TextButton("Create Lobby", skin, "default");
        createLobbyBtn.setSize(getScreenWidth() * 0.1f, getScreenHeight() * 0.1f);
        createLobbyBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showCreateLobbyDialog();
            }
        });

        matchHistoryBtn = new TextButton("Match History", skin, "default");
        matchHistoryBtn.setSize(getScreenWidth() * 0.1f, getScreenHeight() * 0.1f);
        matchHistoryBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenController.transitionToHistory();
            }
        });

        // Add the button directly to the stage
        stage.addActor(createLobbyBtn);
        stage.addActor(matchHistoryBtn);

        // Positioning the create lobby button at the top right with some margin
        float margin = getScreenWidth() * 0.02f; // Adjust the margin value as needed
        float buttonX = Gdx.graphics.getWidth() - createLobbyBtn.getWidth() - margin;
        float buttonY = Gdx.graphics.getHeight() - createLobbyBtn.getHeight() - margin;

        float historyBtnY = buttonY - createLobbyBtn.getHeight() - margin;
        createLobbyBtn.setPosition(buttonX, buttonY);
        matchHistoryBtn.setPosition(buttonX, historyBtnY);
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
        dialog.setSize(getScreenWidth() * 0.3f, getScreenHeight() * 0.5f);

        TextField lobbyTitleField = new TextField("", skin);
        lobbyTitleField.setMessageText("Enter Lobby Title");
        lobbyTitleField.setName("lobbyTitleField");
        dialog.getContentTable().add(lobbyTitleField).width(dialog.getWidth() * 0.9f);

        TextButton btnCancel = new TextButton("Cancel", skin);
        TextButton btnConfirm = new TextButton("Confirm", skin);

        Table buttonTable = new Table();
        buttonTable.add(btnCancel).width(dialog.getWidth() * 0.4f).height(dialog.getHeight() * 0.3f).padRight(dialog.getHeight() * 0.2f);
        buttonTable.add(btnConfirm).width(dialog.getWidth() * 0.4f).height(dialog.getHeight() * 0.3f);

        btnCancel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.hide();
            }
        });

        dialog.button(btnCancel, false); // false - do not create a new lobby
        dialog.button(btnConfirm, true); // true - confirm and create a new lobby

        dialog.show(stage);
    }

    public void updateLobbiesList(GameLobbies gameLobbies) {
        Gdx.app.postRunnable(() -> {
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
                    contentTable.add(lobbyButton).padBottom(getScreenHeight() * 0.03f).width(getScreenWidth() * 0.22f).height(getScreenHeight() * 0.12f).row();
                }
            } else {
                Gdx.app.log("LobbiesScreen", "Attempted to update lobbies list when contentTable is null.");
            }
        });
    }

    @Override
    public void render(float delta) {
        super.render(delta); // This will render the background and the back button
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
        skin.dispose();
        contentTable.clear();
        createLobbyBtn.clear();
        matchHistoryBtn.clear();
        lobbiesTitle.clear();
    }
}