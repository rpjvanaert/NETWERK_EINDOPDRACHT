package Game.Logic;

import Client.ClientCommunication;
import javafx.scene.control.Label;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.World;
import org.dyn4j.dynamics.contact.ContactListener;
import org.dyn4j.dynamics.contact.ContactPoint;
import org.dyn4j.dynamics.contact.PersistedContactPoint;
import org.dyn4j.dynamics.contact.SolvedContactPoint;

import java.util.ArrayList;

public class BoxDestroyer implements ContactListener {

    private World world;

    private Label scoreLabel;

    private ClientCommunication client;
    private boolean isRedPlayer;

    private Body redBall, blueBall, ground;
    private ArrayList<Body> bodiesCrates;
    private ArrayList<GameObject> objectsCrates;

    public BoxDestroyer(World world, Label scoreLabel,
                        ArrayList<GameObject> objectsCrates, Body redBall, Body blueBall,
                        ArrayList<Body> bodiesCrates, Body ground, ClientCommunication client, boolean isRedPlayer){
        this.world = world;
        this.scoreLabel = scoreLabel;
        this.redBall = redBall;
        this.blueBall = blueBall;
        this.ground = ground;
        this.bodiesCrates = bodiesCrates;
        this.objectsCrates = objectsCrates;
        this.client = client;
        this.isRedPlayer = isRedPlayer;
    }

    public void setRed(Body redBall){
        this.redBall = redBall;
    }

    public void setBlue(Body blueBall){
        this.blueBall = blueBall;
    }

    /**
     * @param contactPoint
     * @deprecated
     */
    @Override
    public void sensed(ContactPoint contactPoint) {

    }

    /**
     * begin
     * Returns true so collision handling continues in World.
     * @param contactPoint
     * @return
     */
    @Override
    public boolean begin(ContactPoint contactPoint) {
        return true;
    }

    @Override
    public void end(ContactPoint contactPoint) {
        //Checks for contact if it has red ball in it.
        if (contactPoint.getBody1().equals(this.redBall) || contactPoint.getBody2().equals(this.redBall)){

            //Handles and deletes crate if body2 is crate
            if (this.bodiesCrates.contains(contactPoint.getBody2())){
                this.objectsCrates.remove(this.bodiesCrates.indexOf(contactPoint.getBody2()));
                this.bodiesCrates.remove(contactPoint.getBody2());
                this.world.removeBody(contactPoint.getBody2());
                ScoreSystem.getInstance().addRed();
                this.scoreLabel.setText(ScoreSystem.getInstance().toString());

                //Handles and deletes crate if body1 is crate
            } else if (this.bodiesCrates.contains(contactPoint.getBody1())){
                this.objectsCrates.remove(this.bodiesCrates.indexOf(contactPoint.getBody1()));
                this.bodiesCrates.remove(contactPoint.getBody1());
                this.world.removeBody(contactPoint.getBody1());
                ScoreSystem.getInstance().addRed();
                this.scoreLabel.setText(ScoreSystem.getInstance().toString());

                //Handles if bird hits ground.
            } else if (this.ground.equals(contactPoint.getBody1()) || this.ground.equals(contactPoint.getBody2())){
                ScoreSystem.getInstance().turn();
                this.redBall = null;
            }

            //Checks for contact if it has blue ball in it.
        } else if (contactPoint.getBody1().equals(this.blueBall) || contactPoint.getBody2().equals(this.blueBall)){

            //Handles and deletes crate if body2 is crate
            if (this.bodiesCrates.contains(contactPoint.getBody2())){
                this.objectsCrates.remove(this.bodiesCrates.indexOf(contactPoint.getBody2()));
                this.bodiesCrates.remove(contactPoint.getBody2());
                this.world.removeBody(contactPoint.getBody2());
                ScoreSystem.getInstance().addBlue();
                this.scoreLabel.setText(ScoreSystem.getInstance().toString());

                //Handles and deletes crate if body1 is crate
            } else if (this.bodiesCrates.contains(contactPoint.getBody1())){
                this.objectsCrates.remove(this.bodiesCrates.indexOf(contactPoint.getBody1()));
                this.bodiesCrates.remove(contactPoint.getBody1());
                this.world.removeBody(contactPoint.getBody1());
                ScoreSystem.getInstance().addBlue();
                this.scoreLabel.setText(ScoreSystem.getInstance().toString());

                //Handles if bird hits ground.
            } else if (this.ground.equals(contactPoint.getBody1()) || this.ground.equals(contactPoint.getBody2())){
                ScoreSystem.getInstance().turn();
                this.blueBall = null;
            }
        }
    }

    /**
     * persist
     * Returns true so collision handling continues in World.
     * @param persistedContactPoint
     * @return
     */
    @Override
    public boolean persist(PersistedContactPoint persistedContactPoint) {
        return true;
    }

    /**
     * preSolve
     * Returns true so collision handling continues in World.
     * @param contactPoint
     * @return
     */
    @Override
    public boolean preSolve(ContactPoint contactPoint) {
        return true;
    }

    @Override
    public void postSolve(SolvedContactPoint solvedContactPoint) {

    }
}
