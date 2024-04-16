package no.ntnu.dof.view.screens.lobby;

import androidx.annotation.NonNull;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import java.util.List;
import no.ntnu.dof.model.GameSummary;
import no.ntnu.dof.model.User;
import no.ntnu.dof.view.screens.ReturnableScreen;

public class HistoryScreen extends ReturnableScreen {
    private Skin skin;
    private User user;
    private Label title;

    public HistoryScreen() {
        super();
        this.skin = new Skin(Gdx.files.internal("UISkin.json"));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

//    @Override
//    public void resize(int width, int height) {
//        stage.getViewport().update(width, height, true);
//    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }

    public void showGameSummaries(@NonNull List<GameSummary> gameSummaries) {
        Table contentTable = new Table();
        contentTable.top();

        if (gameSummaries.isEmpty()) {
            Label noHistoryLabel = new Label("No match history yet", skin, "default");
            noHistoryLabel.setColor(Color.GRAY);
            contentTable.add(noHistoryLabel).center().padTop(50);
        } else {
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

                // Adjust alignment and spacing for host and guest labels
                summaryTable.add(hostLabel).align(Align.left).padRight(60).padBottom(6);
                summaryTable.add(guestLabel).align(Align.left).padBottom(5).row();

                // Align host and guest result directly below the names with adjusted spacing
                summaryTable.add(hostResult).align(Align.left).padRight(60).padBottom(6);
                summaryTable.add(guestResult).align(Align.left).padBottom(5).row();

                contentTable.add(summaryTable).fill().expandX().padTop(10).row();
            }
        }

        ScrollPane scrollPane = new ScrollPane(contentTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.getStyle().background = null;

        Table outerTable = new Table();
        outerTable.setFillParent(true);
        outerTable.padTop(30).padBottom(50);

        Label titleLabel = new Label("Match History", skin, "default");
        titleLabel.setFontScale(1.5f);
        outerTable.add(titleLabel).center().padBottom(20).row();
        outerTable.add(scrollPane).width(400).expandY().fillY().center();

        stage.addActor(outerTable);
    }
}
