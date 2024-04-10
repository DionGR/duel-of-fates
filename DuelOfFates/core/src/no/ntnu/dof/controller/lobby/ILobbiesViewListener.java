package no.ntnu.dof.controller.lobby;

import java.util.List;

import no.ntnu.dof.model.GameLobby;

public interface ILobbiesViewListener {
    void updateLobbiesList(List<GameLobby> gameLobbies);
    void transitionToLobby(GameLobby lobby);
    void createNewLobby(String title);
}
