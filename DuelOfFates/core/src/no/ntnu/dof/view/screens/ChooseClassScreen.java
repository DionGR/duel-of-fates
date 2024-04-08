package no.ntnu.dof.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ChooseClassScreen implements Screen {

    private Stage stage;
    private SpriteBatch batch;
    private Skin skin;
    private Table contentTable;

    public ChooseClassScreen(SpriteBatch batch, AssetManager assetManager) {
        this.batch = batch;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Skin should ideally be managed globally or passed as a parameter
        this.skin = new Skin(Gdx.files.internal("uiskin.json"));

        setupUI();
    }

    private void setupUI() {
        contentTable = new Table();
        contentTable.setWidth(stage.getWidth());
        contentTable.align(Align.center | Align.top);
        contentTable.setPosition(0, Gdx.graphics.getHeight());

        Label title = new Label("Choose a Class", skin, "default");
        contentTable.add(title).padBottom(30).row();

        // Placeholder for dynamically adding class selection buttons
        // Similar to addClassSelectionButtons() method discussed previously

        stage.addActor(contentTable);
    }

    @Override
    public void show() {
        // This is called when the screen becomes the current screen for a Game.
        Gdx.input.setInputProcessor(stage); // Set input processor
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); // Clear the screen with black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        // Background drawing operations, if any
        batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // Adjusts the viewport of the stage to the new window size
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}

