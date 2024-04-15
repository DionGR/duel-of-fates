package no.ntnu.dof.view.entity.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.player.Player;
import no.ntnu.dof.view.Image;

public class HostPlayerView extends PlayerView {
    private final Group hostInterface;
    private final Group handView;
    private final Image deckView;
    private final Image discardView;
    private final Player player;

    private static ClickListener playListener;

    public HostPlayerView(Player player) {
        super(player);
        this.player = player;
        this.setPosition((float) Gdx.graphics.getWidth() / 4 - this.getGraphics().getWidth() / 2, Gdx.graphics.getHeight() * 0.6f);

        this.getManaPool().setPosition(-this.getManaGraphics().getWidth() - 10, this.getGraphics().getHeight() / 2 - this.getManaGraphics().getHeight() / 2);


        hostInterface = new Group();

        deckView = new Image("cardBackside.png", 0.25f);
        deckView.setPosition(0, 0);
        hostInterface.addActor(deckView);

        discardView = new Image("cardBackside.png", 0.25f);
        discardView.setPosition((float) Gdx.graphics.getWidth() - discardView.getWidth(), 0);
        hostInterface.addActor(discardView);

        handView = new Group();
        updateHandView();
        hostInterface.addActor(handView);

        Skin skin = new Skin(Gdx.files.internal("UISkin.json"));
        TextButton endTurnButton = new TextButton("End Turn", skin, "default");
        endTurnButton.addListener(playListener);
        endTurnButton.setPosition(discardView.getX() * 0.9f, (discardView.getY() + discardView.getHeight()) * 1.5f);
        hostInterface.addActor(endTurnButton);

        this.addActor(hostInterface);
        hostInterface.setPosition(-this.getX(), -this.getY());
    }

    public void draw(Batch batch, float parentAlpha) {
        updateHandView();
        updateManaView();
        super.draw(batch, parentAlpha);
    }

    public void updateManaView() {
        this.getManaText().setText(Integer.toString(player.getMana().getValue()));
    }

    public void updateHandView() {
        handView.clear();
        List<Card> temporaryList = new ArrayList<>(player.getHand().getCards());
        //has to use iterator to avoid ConcurrentModificationException
        for (Iterator<Card> iterator = temporaryList.iterator(); iterator.hasNext(); ) {
            Card card = iterator.next();
            ClickListener cardListener = player.canPlay(card) ? playListener : null;
            handView.addActor(new CardView(0.3f, card, handView.getChildren().size, cardListener));
        }
        if(handView.getChildren().size > 0){
            handView.setPosition(Gdx.graphics.getWidth()/2f - (handView.getChild(handView.getChildren().size-1).getX()+handView.getChild(handView.getChildren().size-1).getWidth())/2f, 5);
        }
    }

    public static void provideClickListener(ClickListener playListener) {
        HostPlayerView.playListener = playListener;
    }
}
