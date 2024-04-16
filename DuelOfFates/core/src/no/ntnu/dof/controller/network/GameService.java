package no.ntnu.dof.controller.network;

import java.util.Optional;

import no.ntnu.dof.model.GameComms;
import no.ntnu.dof.model.gameplay.card.Card;

// TODO add callbacks if necessary
public interface GameService {
    GameComms createComms(String startingPlayerName);

    void getComms(String gameId, GetCommsCallback callback);

    void addPlayListener(GameComms comms, PlayListener listener);

    void playCard(GameComms comms, Optional<Card> card);

    void abort(GameComms comms);

    interface PlayListener {
        void onCardPlayed(Card card);

        void onTurnEnd(String player);

        void onAbort();
    }

    interface GetCommsCallback {
        void onSuccess(GameComms comms);
        void onFailure(Throwable throwable);
    }
}
