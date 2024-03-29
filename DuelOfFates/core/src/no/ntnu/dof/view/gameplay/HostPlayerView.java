package no.ntnu.dof.view.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import no.ntnu.dof.model.gameplay.player.Player;
import no.ntnu.dof.view.Image;

public class HostPlayerView extends PlayerView{

    private Group hostInterface;
    private Group handView;
    private Image deckView;
    private Image discardView;

    public HostPlayerView(Player player) {
        super(player);
        this.setPosition((float) Gdx.graphics.getWidth()/4 - (float) this.getGraphics().getWidth()/2, Gdx.graphics.getHeight()*0.6f);

        this.getManaPool().setPosition(-this.getManaPool().getWidth()-10, this.getGraphics().getHeight()/2-this.getManaPool().getHeight()/2);

        hostInterface = new Group();

        handView = new Group();
        player.getHand().getCards().forEach(c -> handView.addActor(new CardView(0.2f, c, handView.getChildren().size)));
        handView.setPosition(Gdx.graphics.getWidth()/2f - (handView.getChild(handView.getChildren().size-1).getX()+handView.getChild(handView.getChildren().size-1).getWidth())/2f, 5);
        handView.addListener( new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("down");
                return true;
            }
        } );
        hostInterface.addActor(handView);

        deckView = new Image("./assets/Card_back.png", 0.25f);
        deckView.setPosition(0,0);
        hostInterface.addActor(deckView);

        discardView = new Image("./assets/Card_back.png", 0.25f);
        discardView.setPosition((float)Gdx.graphics.getWidth()-discardView.getWidth(),0);
        hostInterface.addActor(discardView);

        this.addActor(hostInterface);
        hostInterface.setPosition(-this.getX(),-this.getY());
    }

    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
