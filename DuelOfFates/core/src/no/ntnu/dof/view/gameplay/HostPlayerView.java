package no.ntnu.dof.view.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import no.ntnu.dof.controller.gameplay.player.TestClickPlayerController;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.player.Player;
import no.ntnu.dof.view.Image;

@Getter
public class HostPlayerView extends PlayerView{

    private Group hostInterface;
    private Group handView;
    private Image deckView;
    private Image discardView;
    private Player player;
    private TextButton endTurnButton;

    public HostPlayerView(Player player) {
        super(player);
        this.player = player;
        this.setPosition((float) Gdx.graphics.getWidth()/4 - (float) this.getGraphics().getWidth()/2, Gdx.graphics.getHeight()*0.6f);

        this.getManaPool().setPosition(-this.getManaGraphics().getWidth()-10, this.getGraphics().getHeight()/2-this.getManaGraphics().getHeight()/2);
        this.getManaPool().setBounds(this.getManaPool().getX(), this.getManaPool().getY(), this.getManaGraphics().getWidth(), this.getManaGraphics().getHeight());

        hostInterface = new Group();

        handView = new Group();
        player.getHand().getCards().forEach(c -> handView.addActor(new CardView(0.3f, c, handView.getChildren().size)));
        handView.setPosition(Gdx.graphics.getWidth()/2f - (handView.getChild(handView.getChildren().size-1).getX()+handView.getChild(handView.getChildren().size-1).getWidth())/2f, 5);
        hostInterface.addActor(handView);

        deckView = new Image("./assets/Card_back.png", 0.25f);
        deckView.setPosition(0,0);
        hostInterface.addActor(deckView);

        discardView = new Image("./assets/Card_back.png", 0.25f);
        discardView.setPosition((float)Gdx.graphics.getWidth()-discardView.getWidth(),0);
        hostInterface.addActor(discardView);

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        endTurnButton = new TextButton("End Turn", skin, "default");
        endTurnButton.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                TestClickPlayerController.setPlay(Optional.empty());
                return super.touchDown(event, x, y, pointer, button);
            }
        } );
        endTurnButton.setPosition(discardView.getX()*0.9f, (discardView.getY()+discardView.getHeight())*1.5f);
        hostInterface.addActor(endTurnButton);

        this.addActor(hostInterface);
        hostInterface.setPosition(-this.getX(),-this.getY());
    }

    public void draw(Batch batch, float parentAlpha) {
        updateHandView();
        updateManaView();
        super.draw(batch, parentAlpha);
    }

    public void updateManaView(){
        this.getManaText().setText(Integer.toString(player.getMana().getValue()));
    }

    public void updateHandView(){
        handView.clear();
        List<Card> temporaryList = new ArrayList<>(player.getHand().getCards());
        //has to use iterator to avoid ConcurrentModificationException
        for(Iterator<Card> iterator = temporaryList.iterator(); iterator.hasNext();){
            Card c = iterator.next();
            handView.addActor(new CardView(0.3f, c, handView.getChildren().size));
        }
        handView.setPosition(Gdx.graphics.getWidth()/2f - (handView.getChild(handView.getChildren().size-1).getX()+handView.getChild(handView.getChildren().size-1).getWidth())/2f, 5);
        CardView FirstChild = (CardView) handView.getChild(0);
        CardView LastChild = (CardView) handView.getChild(handView.getChildren().size-1);
        handView.setBounds(handView.getX(), handView.getY(), LastChild.getX()-FirstChild.getX()+LastChild.getWidth(), LastChild.getY()-FirstChild.getY()+LastChild.getHeight());
    }


}
