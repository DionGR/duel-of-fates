package no.ntnu.dof.view.entity.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import lombok.Getter;
import no.ntnu.dof.model.gameplay.player.Player;
import no.ntnu.dof.view.Image;
import no.ntnu.dof.view.entity.label.TextLabel;
import no.ntnu.dof.view.gameplay.HealthBarView;

@Getter
public class PlayerView extends Group {
    private final Player player;
    private final Image graphics;
    private final Group manaPool;
    private final Image manaGraphics;
    private final Label manaText;
    private final HealthBarView healthBarView;

    public PlayerView(Player player) {
        this.player = player;
        graphics = new Image("./assets/Player.png", 0.30f);
        this.addActor(graphics);

        manaPool = new Group();
        manaGraphics = new Image("./assets/Mana.png", 0.10f);
        manaPool.addActor(manaGraphics);

        manaText = (new TextLabel(Integer.toString(player.getMana().getValue()), manaGraphics.getWidth()*0.28f,manaGraphics.getHeight()*0.3f,manaGraphics.getWidth()*0.4f,manaGraphics.getHeight()*0.4f,manaGraphics.getHeight()*0.03f, Color.GREEN)).getText();
        manaPool.addActor(manaText);
        this.addActor(manaPool);

        healthBarView = new HealthBarView(player, getX(), getY()-10, graphics.getWidth());
        this.addActor(healthBarView);
    }
    public void draw(Batch batch, float parentAlpha) {
        healthBarView.setPosition(getX(), getY()-10);
        super.draw(batch, parentAlpha);
    }

}
