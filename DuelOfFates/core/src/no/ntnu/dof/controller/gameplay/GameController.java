package no.ntnu.dof.controller.gameplay;

import com.badlogic.gdx.Gdx;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import no.ntnu.dof.controller.gameplay.player.HostPlayerController;
import no.ntnu.dof.controller.gameplay.player.PlayerController;
import no.ntnu.dof.controller.gameplay.player.RemotePlayerController;
import no.ntnu.dof.controller.network.ServiceLocator;
import no.ntnu.dof.model.GameComms;
import no.ntnu.dof.model.GameSummary;
import no.ntnu.dof.model.User;
import no.ntnu.dof.model.gameplay.Game;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.controller.network.UserService;
import no.ntnu.dof.model.gameplay.player.Player;
import no.ntnu.dof.view.entity.view.HostPlayerView;

public class GameController {
    @Getter
    private final Game game;
    private final User currentUser;
    private final Map<Player, PlayerController> playerControllers;

    public GameController(Player host, Player opponent, GameComms comms, User currentUser) {
        this.game = new Game(host, opponent);
        if (game.getNextPlayer().getName().equals(comms.getPlayerLastTurn())) {
            game.finalizeTurn();
        }

        this.playerControllers = new HashMap<>();
        HostPlayerController hostController = new HostPlayerController(host, comms);
        this.playerControllers.put(host, hostController);
        HostPlayerView.provideClickListener(hostController);
        this.playerControllers.put(opponent, new RemotePlayerController(opponent, comms));

        this.currentUser = currentUser;
    }

    public void gameLoop() {
        while (!game.isOver()) {
            Gdx.app.log("Game", "Turn of " + game.getNextPlayer().getName() + " " + game.getNextPlayer());
            Player currentPlayer = game.getNextPlayer();
            PlayerController currentPlayerController = playerControllers.get(currentPlayer);

            Optional<Card> turnCard = currentPlayerController.choosePlay();
            if (turnCard.isPresent()) {
                game.playCard(turnCard.get());
            } else {
                game.finalizeTurn();
                Gdx.app.log("Game", "Turn finalized.");
            }
        }

        GameSummary gameSummary = new GameSummary(game.getHost().getName(), game.getOpponent().getName(), game.getHost().isDead(), game.getOpponent().isDead());
        ServiceLocator.getUserService().uploadGameSummary(currentUser.getId(), gameSummary, new UserService.GameSummaryCallback() {
            @Override
            public void onSuccess() {
                Gdx.app.log("Game", "Game summary uploaded successfully.");
            }

            @Override
            public void onFailure(Exception e) {
                Gdx.app.log("Game", "Failed to upload game summary: " + e.getMessage());
            }
        });

        // TODO: Callbacks and so on?
        if (this.game.getHost().isDead()) {
            Gdx.app.log("Game", "Game over: player lost");
        } else {
            Gdx.app.log("Game", "Game over: player won");
        }
    }
}
