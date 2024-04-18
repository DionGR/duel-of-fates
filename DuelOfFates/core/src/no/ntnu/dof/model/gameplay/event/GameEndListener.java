package no.ntnu.dof.model.gameplay.event;

import no.ntnu.dof.model.communication.GameSummary;

public interface GameEndListener {
    void onGameEnd(GameSummary gameSummary);
    void onGameAbort();
}
