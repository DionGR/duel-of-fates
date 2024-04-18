package no.ntnu.dof.view.gameplay.entity;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;

import lombok.Getter;
import no.ntnu.dof.model.gameplay.player.Player;
import no.ntnu.dof.view.gameplay.texture.Image;

@Getter
public class PlayerView extends Group {
    private final Player player;
    private final ShapeRenderer shapeDrawer;
    private final Image graphics;
    private final PoolView manaPool;
    private final PoolView armorPool;
    private final HealthBarView healthBarView;

    public PlayerView(Player player) {
        this.player = player;
        this.shapeDrawer = new ShapeRenderer();

        graphics = new Image(player.getPlayerClass().getName() + ".png", 0.30f);
        this.addActor(graphics);

        healthBarView = new HealthBarView(player, graphics.getWidth());
        this.addActor(healthBarView);

        manaPool = new PoolView("mana.png", Color.GREEN, 0.10f);
        this.addActor(manaPool);

        armorPool = new PoolView("armor.png", Color.LIGHT_GRAY, 0.10f);
        this.addActor(armorPool);
    }

    public void draw(Batch batch, float parentAlpha) {
        manaPool.update(player.getMana().getValue());
        armorPool.update(player.getArmor().getValue());
        super.draw(batch, parentAlpha);
    }

    public void dispose() {
        graphics.dispose();
        manaPool.dispose();
        armorPool.dispose();
        healthBarView.dispose();
        shapeDrawer.dispose();
    }

}
