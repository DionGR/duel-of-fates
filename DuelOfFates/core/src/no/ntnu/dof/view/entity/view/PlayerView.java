package no.ntnu.dof.view.entity.view;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import lombok.Getter;
import no.ntnu.dof.model.gameplay.player.Player;
import no.ntnu.dof.view.Image;
import no.ntnu.dof.view.entity.label.TextLabel;

@Getter
public class PlayerView extends Group {
    private final Player player;
    private final Image graphics;
    private final Group manaPool;
    private final Image manaGraphics;
    private final Label manaText;

    public PlayerView(Player player) {
        this.player = player;
        graphics = new Image("./assets/"+ player.getPlayerClass().getName() +".png", 0.30f);
        this.addActor(graphics);

        manaPool = new Group();
        manaGraphics = new Image("./assets/Mana.png", 0.10f);
        manaPool.addActor(manaGraphics);

        manaText = (new TextLabel(Integer.toString(player.getMana().getValue()), manaGraphics.getWidth()*0.28f,manaGraphics.getHeight()*0.3f,manaGraphics.getWidth()*0.4f,manaGraphics.getHeight()*0.4f,manaGraphics.getHeight()*0.03f, Color.GREEN)).getText();
        manaPool.addActor(manaText);

        this.addActor(manaPool);
    }
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        //Draw the healthbar
        batch.end();

        float percentage =  Math.max(0,((float) player.getHealth().getValue()/(float) player.getPlayerClass().getMaxHealth().getValue()));
        ShapeRenderer ShapeDrawer = new ShapeRenderer();
        ShapeDrawer.begin(ShapeRenderer.ShapeType.Filled);
        ShapeDrawer.setColor(Color.BLACK);
        ShapeDrawer.rect(getX(), getY()-10, graphics.getWidth(), (float) (Gdx.graphics.getHeight()*0.05));
        ShapeDrawer.setColor(Color.GRAY);
        ShapeDrawer.rect(getX()+2, getY()-8, graphics.getWidth()-4, (float) (Gdx.graphics.getHeight()*0.05)-4);
        ShapeDrawer.setColor(Color.RED);
        ShapeDrawer.rect(getX()+2, getY()-8, (graphics.getWidth()-4)*percentage, (float) (Gdx.graphics.getHeight()*0.05)-4);
        ShapeDrawer.end();

        batch.begin();
    }

}
