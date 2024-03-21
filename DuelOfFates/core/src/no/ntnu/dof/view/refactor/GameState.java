package no.ntnu.dof.view.refactor;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class GameState {
    /**
     This class is the base class for all the states in the game. It is an abstract class, which means that it cannot be instantiated.
     It contains the methods that are commun in all the states.
     **/
    protected Vector2 mouse; //This is the mouse position
    protected GameStateManager gsm; //This is the GameStateManager object to handle states

    /**
     * Constructor for State, it just initialized variables
     */
    protected GameState(){
        this.gsm = GameStateManager.getInstance();
        mouse = new Vector2();
    }

    /**
     * This method handles the input of the user
     */
    protected abstract void handleInput();

    /**
     * This method updates the state
     * @param dt This is the time between frames
     */
    public abstract void update(float dt);

    /**
     * This method renders the image
     * @param spriteBatch This is the spriteBatch object to render
     */
    public abstract void render(SpriteBatch spriteBatch);

    /**
     * This method disposes object of the state
     */
    public abstract void dispose();

}
