package no.ntnu.dof.view.entity.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import no.ntnu.dof.controller.gameplay.player.ClickHostPlayerController;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.player.Player;
import no.ntnu.dof.view.Image;

public class HostPlayerView extends PlayerView{

    private Group hostInterface;
    private Group handView;
    private Image deckView;
    private Image discardView;
    private Player player;

    public HostPlayerView(Player player) {
        super(player);
        this.player = player;
        this.setPosition((float) Gdx.graphics.getWidth()/4 - (float) this.getGraphics().getWidth()/2, Gdx.graphics.getHeight()*0.6f);

        this.getManaPool().setPosition(-this.getManaGraphics().getWidth()-10, this.getGraphics().getHeight()/2-this.getManaGraphics().getHeight()/2);


        hostInterface = new Group();

        handView = new Group();
        updateHandView();
        handView.addListener( new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("down");
                return true;
            }
        } );
        hostInterface.addActor(handView);

        deckView = new Image("./assets/Card_back.png", 0.30f);
        deckView.setPosition(0,0);
        hostInterface.addActor(deckView);

        discardView = new Image("./assets/Card_back.png", 0.30f);
        discardView.setPosition((float)Gdx.graphics.getWidth()-discardView.getWidth(),0);
        hostInterface.addActor(discardView);

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        TextButton endTurnButton = new TextButton("End Turn", skin, "default");
        endTurnButton.addListener(ClickHostPlayerController.get());
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
        for (int i = 0; i < handView.getChildren().size; i++) {
            ((CardView) handView.getChild(i)).dispose();
        }
        handView.clear();
        List<Card> temporaryList = new ArrayList<>(player.getHand().getCards());
        //has to use iterator to avoid ConcurrentModificationException
        for(Iterator<Card> iterator = temporaryList.iterator(); iterator.hasNext();){
            Card c = iterator.next();
            handView.addActor(new CardView(0.4f, c, handView.getChildren().size));
        }
        if(handView.getChildren().size > 0){
            handView.setPosition(Gdx.graphics.getWidth()/2f - (handView.getChild(handView.getChildren().size-1).getX()+handView.getChild(handView.getChildren().size-1).getWidth())/2f, 5);
        }
    }

    public void dispose() {
        super.dispose();
        deckView.dispose();
        discardView.dispose();
        for (int i = 0; i < handView.getChildren().size; i++) {
            ((CardView) handView.getChild(i)).dispose();
        }
    }
}
