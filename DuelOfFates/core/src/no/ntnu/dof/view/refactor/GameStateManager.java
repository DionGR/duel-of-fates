package no.ntnu.dof.view.refactor;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public class GameStateManager {
    //This class is used to manage the states of the game. It is used to push and pop states from a stack.
    private Stack<Fight> states; //Stack is a data structure that follows the LIFO (Last In First Out) principle.

    static GameStateManager instance = null; //This is the unique instance of the GameStateManager

    /**
     * Constructor for GameStateManager
     */
    //This is a singleton class, which means that there can only be one instance of this class. so the constructor has to be private
    private GameStateManager(){
        states = new Stack<Fight>();
    }

    //This method is used to get the unique instance of the GameStateManager
    public static GameStateManager getInstance() {
        if (instance == null) { //If there is no instance of the GameStateManager, we create one
            instance = new GameStateManager();
            return instance;
        }
        else { //If there is already an instance of the GameStateManager, we return it
            return instance;
        }
    }

    /**
     * Pushes a new state ontop of the stack
     * @param state This is the state to be pushed
     */
    public void push(Fight state){
        states.push(state);
    }

    /**
     * Pops the state ontop of the stack
     */
    public void pop(){
        states.pop();
    }

    /**
     * Sets a new state ontop of the stack
     * @param state This is the state to be set
     */
    public void set(Fight state){
        states.pop();
        states.push(state);
    }

    /**
     * Updates the state ontop of the stack
     * @param dt This is the time between frames
     */
    public void update(float dt){
        states.peek().update(dt);
    }

    /**
     * Renders the state ontop of the stack
     * @param spriteBatch This is the spriteBatch object to render
     */
    public void render(SpriteBatch spriteBatch){
        states.peek().render(spriteBatch);
    }

}
