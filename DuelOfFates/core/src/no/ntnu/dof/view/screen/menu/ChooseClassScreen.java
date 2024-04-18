package no.ntnu.dof.view.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import no.ntnu.dof.model.communication.User;
import no.ntnu.dof.model.gameplay.playerclass.PlayerClass;
import no.ntnu.dof.view.di.ChooseClassScreenComponent;
import no.ntnu.dof.view.di.DaggerChooseClassScreenComponent;
import no.ntnu.dof.view.screen.ReturnableScreen;

public class ChooseClassScreen extends ReturnableScreen {
    private final Skin skin;
    private Table contentTable;
    private final User user;
    private final ChooseClassListener listener;
    private TextButton selectedButton = null; // To keep track of the currently selected button
    private TextButton.TextButtonStyle defaultStyle;
    private TextButton.TextButtonStyle selectedStyle;

    @Inject
    @Named("listPlayerClasses")
    protected List<PlayerClass> playerClasses;

    public ChooseClassScreen(User currentUser, ChooseClassListener listener) {
        ChooseClassScreenComponent chooseClassScreenComponent = DaggerChooseClassScreenComponent.create();
        chooseClassScreenComponent.inject(this);

        this.user = currentUser;
        this.listener = listener;
        this.skin = new Skin(Gdx.files.internal("UISkin.json"));
    }

    private void setupUI() {
        // Initialize styles
        initializeStyles();

        TextButton.TextButtonStyle textButtonStyle = skin.get(TextButton.TextButtonStyle.class);
        textButtonStyle.font.getData().setScale(getScreenHeight() * 0.002f);

        contentTable = new Table();
        contentTable.setFillParent(true);
        contentTable.top();

        Label title = new Label("Choose a Class", skin);
        title.setFontScale(getScreenHeight() * 0.004f);
        contentTable.add(title).padTop(getScreenHeight() * 0.05f).padBottom(getScreenHeight() * 0.1f).center().row();

        for (PlayerClass playerClass : playerClasses) {
            TextButton classButton = new TextButton(playerClass.getName(), skin);

            if (playerClass.getName().equals(user.getPlayerClassName())) {
                classButton.setStyle(selectedStyle);
                selectedButton = classButton;
            } else {
                classButton.setStyle(defaultStyle);
            }

            classButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (selectedButton != null) {
                        selectedButton.setStyle(defaultStyle);
                    }
                    classButton.setStyle(selectedStyle);
                    selectedButton = classButton;
                    listener.onClassChoice(playerClass);
                }
            });
            contentTable.add(classButton).padBottom(getScreenHeight() * 0.05f).width(getScreenWidth() * 0.22f).height(getScreenHeight() * 0.12f).row();
        }

        stage.addActor(contentTable);
    }

    private void initializeStyles() {
        defaultStyle = new TextButton.TextButtonStyle(skin.get(TextButton.TextButtonStyle.class));
        selectedStyle = new TextButton.TextButtonStyle(defaultStyle);
        selectedStyle.up = skin.newDrawable("default-round", Color.GREEN);
        selectedStyle.down = skin.newDrawable("default-round-down", Color.GREEN);
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
        background.setSize(width, height);
    }

    @Override
    public void show() {
        super.show();
        setupUI();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        contentTable.clear();
        super.dispose();
    }

    public interface ChooseClassListener {
        void onClassChoice(PlayerClass playerClass);
    }
}
