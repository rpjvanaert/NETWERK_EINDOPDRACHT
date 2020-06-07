package Game.Logic;

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

    private Body redBall, blueBall, ground;
    private ArrayList<Body> bodiesCrates;
    private ArrayList<GameObject> objectsCrates;

    public BoxDestroyer(World world, Label scoreLabel,
                        ArrayList<GameObject> objectsCrates, Body redBall, Body blueBall,
                        ArrayList<Body> bodiesCrates, Body ground){
        this.world = world;
        this.scoreLabel = scoreLabel;
        this.redBall = redBall;
        this.blueBall = blueBall;
        this.ground = ground;
        this.bodiesCrates = bodiesCrates;
        this.objectsCrates = objectsCrates;
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

    @Override
    public boolean begin(ContactPoint contactPoint) {
        return true;
    }

    @Override
    public void end(ContactPoint contactPoint) {
        if (contactPoint.getBody1().equals(this.redBall) || contactPoint.getBody2().equals(this.redBall)){
            if (this.bodiesCrates.contains(contactPoint.getBody2())){
                this.objectsCrates.remove(this.bodiesCrates.indexOf(contactPoint.getBody2()));
                this.bodiesCrates.remove(contactPoint.getBody2());
                this.world.removeBody(contactPoint.getBody2());
                ScoreSystem.getInstance().addRed();
                this.scoreLabel.setText(ScoreSystem.getInstance().toString());
            } else if (this.bodiesCrates.contains(contactPoint.getBody1())){
                this.objectsCrates.remove(this.bodiesCrates.indexOf(contactPoint.getBody1()));
                this.bodiesCrates.remove(contactPoint.getBody1());
                this.world.removeBody(contactPoint.getBody1());
                ScoreSystem.getInstance().addRed();
                this.scoreLabel.setText(ScoreSystem.getInstance().toString());
            } else if (this.ground.equals(contactPoint.getBody1()) || this.ground.equals(contactPoint.getBody2())){
                ScoreSystem.getInstance().turn();
                this.redBall = null;
                //@TODO Remove red and create blue
            }
        } else if (contactPoint.getBody1().equals(this.blueBall) || contactPoint.getBody2().equals(this.blueBall)){
            if (this.bodiesCrates.contains(contactPoint.getBody2())){
                this.objectsCrates.remove(this.bodiesCrates.indexOf(contactPoint.getBody2()));
                this.bodiesCrates.remove(contactPoint.getBody2());
                this.world.removeBody(contactPoint.getBody2());
                ScoreSystem.getInstance().addBlue();
                this.scoreLabel.setText(ScoreSystem.getInstance().toString());
            } else if (this.bodiesCrates.contains(contactPoint.getBody1())){
                this.objectsCrates.remove(this.bodiesCrates.indexOf(contactPoint.getBody1()));
                this.bodiesCrates.remove(contactPoint.getBody1());
                this.world.removeBody(contactPoint.getBody1());
                ScoreSystem.getInstance().addBlue();
                this.scoreLabel.setText(ScoreSystem.getInstance().toString());
            } else if (this.ground.equals(contactPoint.getBody1()) || this.ground.equals(contactPoint.getBody2())){
                ScoreSystem.getInstance().turn();
                this.blueBall = null;
                //@TODO Remove blue and create red
            }
        }
    }

    @Override
    public boolean persist(PersistedContactPoint persistedContactPoint) {
        return true;
    }

    @Override
    public boolean preSolve(ContactPoint contactPoint) {
        return true;
    }

    @Override
    public void postSolve(SolvedContactPoint solvedContactPoint) {

    }
}
