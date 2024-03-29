package no.ntnu.dof.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

import no.ntnu.dof.model.gameplay.Game;
import no.ntnu.dof.view.gameplay.GameView;


public class FightScreen implements Screen {
    private Stage stage;

    private final GameView gameView;

    public FightScreen(Game game) {
        this.gameView = new GameView(game);
    }

    @Override
    public void show() {

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        stage.addActor(gameView);

        /*Group Hand = new Group();
        for (int i = 0; i < Players[0].getHand().getCards().size(); i++) {
            System.out.println(i);

            Hand.addActor(card);
        }
        Hand.setPosition(Gdx.graphics.getWidth()/2f - (Hand.getChild(Hand.getChildren().size-1).getX()+Hand.getChild(Hand.getChildren().size-1).getWidth())/2f, 5);

        stage.addActor(Hand);

        Hand.addListener( new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("down");
                return true;
            }
        } );*/

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }


}
