package no.ntnu.dof.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import no.ntnu.dof.controller.DuelOfFates;
import no.ntnu.dof.model.User;

public class HistoryScreen extends BaseScreen {
    private DuelOfFates game;
    private Skin skin;
    private Stage stage;
    private User user;
    private Label title;

    public HistoryScreen(DuelOfFates game) {
        super();
        this.skin = new Skin(Gdx.files.internal("uiskin.json"));
        this.stage = new Stage(new ScreenViewport(), this.batch);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        Table contentTable = new Table();
        contentTable.setWidth(stage.getWidth());
        contentTable.align(Align.center|Align.top);
        contentTable.setPosition(0, Gdx.graphics.getHeight());

        title = new Label("Match History", skin, "default");
        contentTable.add(title).padTop(20).row();
        stage.addActor(contentTable);
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
    public void dispose() {
        super.dispose();
        stage.dispose();
    }
}
