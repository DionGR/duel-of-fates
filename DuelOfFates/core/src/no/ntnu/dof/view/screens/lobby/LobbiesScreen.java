package no.ntnu.dof.view.screens.lobby;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
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
import no.ntnu.dof.controller.ScreenController;
import no.ntnu.dof.controller.lobby.LobbiesViewListener;
import no.ntnu.dof.model.GameLobbies;
import no.ntnu.dof.model.GameLobby;
import no.ntnu.dof.view.screens.ReturnableScreen;

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

    public LobbiesScreen() {
        super();
    }

    @Override
    public void show() {
        super.show();
        setupUI();
    }

    private void setupUI() {
        if (skin == null) {
            skin = new Skin(Gdx.files.internal("UISkin.json"));
        }

        stage.clear();  // Clear the stage to remove old actors
        Table mainTable = new Table();
        mainTable.setFillParent(true);
        stage.addActor(mainTable);

        lobbiesTitle = new Label("Lobbies", skin, "big");
        lobbiesTitle.setFontScale(1.5f);
        mainTable.add(lobbiesTitle).padTop(30).padBottom(20).center().row();

        contentTable = new Table(skin);
        ScrollPane scrollPane = new ScrollPane(contentTable, skin);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setFadeScrollBars(false);
        mainTable.add(scrollPane).width(350).expandY().fillY().pad(10);

        setupButtons();
        updateLobbiesList(gameLobbies);  // Refresh list using current lobbies
    }


    private void setupButtons() {
        createLobbyBtn = new TextButton("Create Lobby", skin);
        createLobbyBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showCreateLobbyDialog();
            }
        });

        matchHistoryBtn = new TextButton("Match History", skin);
        matchHistoryBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenController.transitionToHistory();
            }
        });

        stage.addActor(createLobbyBtn);
        stage.addActor(matchHistoryBtn);

        float margin = 20;
        float buttonX = Gdx.graphics.getWidth() - createLobbyBtn.getWidth() - margin;
        float buttonY = Gdx.graphics.getHeight() - createLobbyBtn.getHeight() - margin;

        float historyBtnY = buttonY - createLobbyBtn.getHeight() - margin;
        createLobbyBtn.setPosition(buttonX, buttonY);
        matchHistoryBtn.setPosition(buttonX, historyBtnY);

        Gdx.app.log("LobbiesScreen", "Setting up buttons" + gameLobbies.getLobbies().size());
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
        Gdx.app.postRunnable(() -> {
            this.gameLobbies = gameLobbies;
            contentTable.clearChildren();
            if (gameLobbies.getLobbies().isEmpty()) {
                contentTable.add(new Label("No lobbies available", skin)).padTop(30).center().row();
            } else {
                for (GameLobby lobby : gameLobbies.getLobbies()) {
                    TextButton lobbyButton = new TextButton(lobby.getTitle() + "\n" + lobby.getCreator().getEmail(), skin);
                    lobbyButton.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            listener.transitionToLobby(lobby);
                        }
                    });
                    contentTable.add(lobbyButton).padBottom(10).width(300).height(50).row();
                }
            }
            contentTable.invalidateHierarchy();
            stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f)); // Ensure updates are processed
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
