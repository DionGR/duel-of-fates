package no.ntnu.dof.view.screens.lobby;

import androidx.annotation.NonNull;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import java.util.List;
import no.ntnu.dof.model.GameSummary;
import no.ntnu.dof.model.User;
import no.ntnu.dof.view.screens.ReturnableScreen;

public class HistoryScreen extends ReturnableScreen {
    private Skin skin;
    private Stage stage;
    private User user;
    private Label title;

    public HistoryScreen() {
        super();
        this.skin = new Skin(Gdx.files.internal("uiskin.json"));
        this.stage = new Stage(new ScreenViewport(), this.batch);
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
    public void dispose() {
        super.dispose();
        stage.dispose();
    }

    public void showMockGameSummaries(@NonNull List<GameSummary> gameSummaries) {
        Table contentTable = new Table();
        contentTable.setWidth(stage.getWidth());
        contentTable.align(Align.center | Align.top);
        contentTable.setPosition(0, Gdx.graphics.getHeight());

        title = new Label("Match History", skin, "default");
        contentTable.add(title).padTop(20).row();

        for (GameSummary summary : gameSummaries) {
            Table summaryTable = new Table(skin);
            summaryTable.setBackground("default-pane");
            summaryTable.pad(10);

            Label hostLabel = new Label("Host: " + summary.getUserHost().getName(), skin);
            Label guestLabel = new Label("Guest: " + summary.getUserGuest().getName(), skin);
            Label winLabel = new Label("Won!", skin);
            winLabel.setColor(Color.GREEN);
            Label loseLabel = new Label("Lost!", skin);
            loseLabel.setColor(Color.RED);

            Label hostResult = summary.getHostWin() ? winLabel : loseLabel;
            Label guestResult = summary.getGuestWin() ? winLabel : loseLabel;

            summaryTable.add(hostLabel).padBottom(5).row();
            summaryTable.add(hostResult).padBottom(5).row();
            summaryTable.add(guestLabel).padBottom(5).row();
            summaryTable.add(guestResult).padBottom(5).row();

            contentTable.add(summaryTable).padTop(10).row();
        }
        stage.addActor(contentTable);
    }
}
