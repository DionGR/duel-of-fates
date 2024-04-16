package no.ntnu.dof.controller.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.LifecycleListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import no.ntnu.dof.controller.gameplay.player.HostPlayerController;
import no.ntnu.dof.controller.gameplay.player.PlayerController;
import no.ntnu.dof.controller.gameplay.player.RemotePlayerController;
import no.ntnu.dof.model.GameComms;
import no.ntnu.dof.model.GameSummary;
import no.ntnu.dof.model.gameplay.Game;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.event.GameEndListener;
import no.ntnu.dof.model.gameplay.player.Player;
import no.ntnu.dof.view.entity.view.HostPlayerView;

public class GameController {
    @Getter
    private final Game game;
    private final Map<Player, PlayerController> playerControllers;
    private boolean quit = false;

    public static final long TURN_TIMEOUT = 10000; // milliseconds

    public GameController(Player host, Player opponent, GameComms comms) {
        this.game = new Game(host, opponent);
        if (game.getNextPlayer().getName().equals(comms.getPlayerLastTurn())) {
            game.finalizeTurn();
        }

        this.playerControllers = new HashMap<>();
        HostPlayerController hostController = new HostPlayerController(host, comms);
        this.playerControllers.put(host, hostController);
        HostPlayerView.provideClickListener(hostController);
        this.playerControllers.put(opponent, new RemotePlayerController(opponent, comms));
    }

    public void startGame(GameEndListener gameEndListener) {
        Thread gameThread = new Thread(() -> {
            try {
                GameSummary gameSummary = gameLoop();
                gameEndListener.onGameEnd(gameSummary);
            } catch (InterruptedException e) {
                Gdx.app.log("Game", "Game over: host aborted");
                gameEndListener.onGameAbort();
            }
        });
        Gdx.app.addLifecycleListener(new LifecycleListener() {
            @Override
            public void pause() {}

            @Override
            public void resume() {}

            @Override
            public void dispose() {
                Gdx.app.log("Application", "Application quit.");
                quit = true;
                gameThread.interrupt();
                try {
                    gameThread.join();
                } catch (InterruptedException e) {
                    Gdx.app.error("Game", "Game ended ungracefully.");
                }
            }
        });
        gameThread.start();
    }

    public GameSummary gameLoop() throws InterruptedException {
        while (!game.isOver()) {
            Gdx.app.log("Game", "Turn of " + game.getNextPlayer().getName() + " " + game.getNextPlayer());
            Player currentPlayer = game.getNextPlayer();
            PlayerController currentPlayerController = playerControllers.get(currentPlayer);

            if (quit) throw new InterruptedException();
            Optional<Card> turnCard = currentPlayerController.choosePlay(TURN_TIMEOUT);
            if (turnCard.isPresent()) {
                game.playCard(turnCard.get());
            } else {
                game.finalizeTurn();
                Gdx.app.log("Game", "Turn finalized.");
            }
        }

        Gdx.app.log("Game", "Game over: player " + (game.getHost().isDead() ? "lost" : "won"));
        return new GameSummary(
            game.getHost().getName(),
            game.getOpponent().getName(),
            !game.getHost().isDead(),
            !game.getOpponent().isDead()
        );
    }
}
