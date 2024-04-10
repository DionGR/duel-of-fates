package no.ntnu.dof.controller.network;

import java.util.Optional;

import no.ntnu.dof.model.GameComms;
import no.ntnu.dof.model.gameplay.card.Card;

// TODO add callbacks if necessary
public interface GameService {
    GameComms createComms(String gameId);

    void addPlayListener(GameComms comms, PlayListener listener);

    void playCard(GameComms comms, Optional<Card> card);

    interface PlayListener {
        void onCardPlayed(Card card);

        void onTurnEnd(String player);
    }
}
