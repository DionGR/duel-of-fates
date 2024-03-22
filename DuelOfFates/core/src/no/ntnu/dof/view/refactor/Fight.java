package no.ntnu.dof.view.refactor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import no.ntnu.dof.model.gameplay.player.Player;
import no.ntnu.dof.view.Card_View;
import no.ntnu.dof.view.Image;

import java.util.ArrayList;
import java.util.List;

public class Fight extends GameState {
    /**
     This class is the base class for all the states in the game. It is an abstract class, which means that it cannot be instantiated.
     It contains the methods that are commun in all the states.
     **/
    protected GameStateManager gsm; //This is the GameStateManager object to handle states
    private Vector2 TouchPos;

    private Player Player1_Model;
    private Player Player2_Model;

    private List<Image> hand;
    private Image Card_back = new Image("./assets/Card_back.png", 0.35f);
    private int  start_hand;
    private int end_hand;

    private Image Player1_View = new Image("./assets/Player.png", 0.30f);
    private Image Player2_View = new Image("./assets/Player.png", 0.30f);

    private Image Mana_pool = new Image("./assets/Mana.png", 0.10f);


    private int health_player1;
    private int health_player2;


    /**
     * Constructor for State, it just initialized variables
     */
    public Fight(){
        this.gsm = GameStateManager.getInstance();
        this.TouchPos = new Vector2();
        health_player1 = 90;

        hand = new ArrayList<Image>();
        for (int i = 0; i < 5; i++)
        {
            hand.add(new Card_View("./assets/Card.png", 0.35f, "Card"+i, "Description"+i, i));
        }
        if(Card_back==null)
        {
            System.out.println("Card_back is null");
        }
    }

    /**
     * This method handles the input of the user
     */
    public void handleInput()
    {
        //This method is used to handle the input of the user
        if (Gdx.input.justTouched()) {
            System.out.println(Gdx.input.getX());
            System.out.println(Gdx.graphics.getHeight() - Gdx.input.getY());
            TouchPos.set(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
            System.out.println("Start hand = "+start_hand);
            if (TouchPos.x > start_hand && TouchPos.x < end_hand && TouchPos.y < hand.get(0).getHeight()) {
                DisplayCard();
            }
        }

    }

    /**
     * This method updates the state
     *
     * @param dt This is the time between frames
     */
    public void update(float dt) {
        //This method is used to update the state

        start_hand = Gdx.graphics.getWidth()/2- hand.get(0).getWidth()/6 - 2*hand.get(0).getWidth()/3*hand.size()/2;
        end_hand = start_hand + hand.size()*(2*hand.get(0).getWidth()/3);

        handleInput();
    }

    /**
     * This method renders the image
     * @param spriteBatch This is the spriteBatch object to render
     */
    public void render(SpriteBatch spriteBatch)
    {
        //This method is used to render the image
        spriteBatch.begin();
        Gdx.gl.glClearColor(0, 0, 1, 1);

        for(int i = 0; i < hand.size(); i++)
        {
            spriteBatch.draw(hand.get(0).getImg(),  start_hand + ((float) 2*hand.get(0).getWidth()/3)*(i), 0, hand.get(0).getWidth(), hand.get(0).getHeight());
        }

        spriteBatch.draw(Card_back.getImg(), 0, 0, Card_back.getWidth(), Card_back.getHeight());
        spriteBatch.draw(Card_back.getImg(), Gdx.graphics.getWidth()-Card_back.getWidth(), 0, Card_back.getWidth(), Card_back.getHeight());

        spriteBatch.draw(Player1_View.getImg(), (float) (Gdx.graphics.getWidth()/4- Player1_View.getWidth()/2), (float) (Gdx.graphics.getHeight()*0.6), Player1_View.getWidth(), Player1_View.getHeight());
        spriteBatch.draw(Player2_View.getImg(), (float) (3*Gdx.graphics.getWidth()/4- Player2_View.getWidth()/2), (float) (Gdx.graphics.getHeight()*0.6), Player2_View.getWidth(), Player2_View.getHeight());

        float percentage =  ((float) health_player1/100);
        ShapeRenderer ShapeDrawer = new ShapeRenderer();
        ShapeDrawer.begin(ShapeRenderer.ShapeType.Filled);
        ShapeDrawer.setColor(Color.BLACK);
        ShapeDrawer.rect( (float) (Gdx.graphics.getWidth()/4- Player1_View.getWidth()/2), (float) (Gdx.graphics.getHeight()*0.6) - ((float) (Gdx.graphics.getHeight()*0.05)+15), (float) Player1_View.getWidth(), (float) (Gdx.graphics.getHeight()*0.05));
        ShapeDrawer.setColor(Color.GRAY);
        ShapeDrawer.rect( (float) (Gdx.graphics.getWidth()/4- Player1_View.getWidth()/2)+3, (float) (Gdx.graphics.getHeight()*0.6) - ((float) (Gdx.graphics.getHeight()*0.05)+12), Player1_View.getWidth()-6, (float) (Gdx.graphics.getHeight()*0.05)-6);
        ShapeDrawer.setColor(Color.RED);
        ShapeDrawer.rect( (float) (Gdx.graphics.getWidth()/4- Player1_View.getWidth()/2)+3, (float) (Gdx.graphics.getHeight()*0.6) - ((float) (Gdx.graphics.getHeight()*0.05)+12),  percentage*(Player1_View.getWidth()-6), (float) (Gdx.graphics.getHeight()*0.05)-6);
        ShapeDrawer.end();

        spriteBatch.draw(Mana_pool.getImg(), (float) (Gdx.graphics.getWidth()/4- Player1_View.getWidth()/2-Mana_pool.getWidth()*1.5), (float) (Gdx.graphics.getHeight()*0.6+ Player1_View.getHeight()/2-Mana_pool.getHeight()), Mana_pool.getWidth(), Mana_pool.getHeight());
        spriteBatch.draw(Mana_pool.getImg(), (float) (3*Gdx.graphics.getWidth()/4+Mana_pool.getWidth()*1.5), (float) (Gdx.graphics.getHeight()*0.6+ Player2_View.getHeight()/2-Mana_pool.getHeight()), Mana_pool.getWidth(), Mana_pool.getHeight());

        spriteBatch.end();
    }

    /**
     * This method disposes object of the state
     */
    public void dispose() {

    }

    public void DisplayCard()
    {
        System.out.println("Card displayed");
        int num_card = (int) ((TouchPos.x - start_hand) / ((float) 2*hand.get(0).getWidth()/3));
        System.out.println("Card number = "+num_card);
    }

}
