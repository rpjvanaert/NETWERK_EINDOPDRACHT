package Game.Logic;

import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.Force;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.Mass;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;
import org.jfree.fx.FXGraphics2D;

import java.util.ArrayList;

public class GameEngine {
    private ArrayList<GameObject> objectCrates;
    private World world;

    private boolean isRedPlayer;

    private Label scoreLabel;

    private BoxDestroyer boxDestroyer;

    private Body bodySlingshot;
    private Body bodyGround;
    private Body bodyRedBird;
    private Body bodyBlueBird;
    private GameObject gameObjectRedBird;
    private GameObject gameObjectBlueBird;
    private GameObject gameObjectGround;
    private GameObject gameObjectSlingshot;
    private ArrayList<Body> cratesBody;
    private ArrayList<GameObject> cratesGameObjects;

    private static final double xSlingshot = -5.0;

    public GameEngine(World world, Label scoreLabel, boolean playerRed){
        this.world = world;
        this.world.setGravity(new Vector2(0, -9.8));

        this.isRedPlayer = playerRed;

        this.scoreLabel = scoreLabel;

        this.objectCrates = new ArrayList<>();

        this.cratesGameObjects = new ArrayList<>();
        this.cratesBody = new ArrayList<>();

        this.gameObjectRedBird = null;
        this.gameObjectBlueBird = null;

        this.createBoxRow(0,3);
        this.createBoxRow(1,3);
        this.createBoxRow(2,4);
        this.createBoxRow(3,5);

        this.bodyGround = new Body();
        this.bodyGround.addFixture((Geometry.createRectangle(999, 0.8)));
        this.bodyGround.getTransform().setTranslation(0, -2.6);
        this.bodyGround.setMass(MassType.INFINITE);
        this.bodyGround.getFixture(0).setRestitution(0.75);
        this.world.addBody(this.bodyGround);
        this.gameObjectGround = new GameObject("/ground.png", this.bodyGround, new Vector2(0, -90), 1);

        this.bodySlingshot = new Body();
        this.bodySlingshot.addFixture(Geometry.createRectangle(0.4, 1.4));
        this.bodySlingshot.getTransform().setTranslation(xSlingshot, -1.3);
        this.bodySlingshot.setMass(MassType.INFINITE);
        this.bodySlingshot.getFixture(0).setRestitution(0.75);
        world.addBody(this.bodySlingshot);
        this.gameObjectSlingshot = new GameObject("/Slingshot.png", this.bodySlingshot, new Vector2(0, -60), 0.45);

        this.setBall(true);
        //public BoxDestroyer(World world, Label scoreLabel,
        //                        ArrayList<GameObject> objectsCrates, Body redBall, Body blueBall,
        //                        ArrayList<Body> bodiesCrates){

        this.boxDestroyer = new BoxDestroyer(this.world, this.scoreLabel, this.objectCrates,
                this.bodyRedBird, this.bodyBlueBird, this.cratesBody, this.bodyGround);

        this.world.addListener(this.boxDestroyer);
    }

    private void setBallNull(boolean isRed){
        if (isRed){
            this.world.removeBody(this.bodyRedBird);
            this.bodyRedBird = null;
            this.gameObjectRedBird = null;
        } else {
            this.world.removeBody(this.bodyBlueBird);
            this.bodyBlueBird = null;
            this.gameObjectBlueBird = null;
        }
    }

    private void setBall(boolean isRed){
        if (isRed){
            this.bodyRedBird = new Body();
            this.bodyRedBird.addFixture(Geometry.createCircle(0.29));
            this.bodyRedBird.getTransform().setTranslation(xSlingshot, -0.4);
            this.bodyRedBird.setMass(MassType.NORMAL);
            this.bodyRedBird.getFixture(0).setRestitution(0.75);
            this.gameObjectRedBird = new GameObject("/redBall.png", bodyRedBird, new Vector2(0,0), 0.45);
            this.world.addBody(this.bodyRedBird);
        } else {
            this.bodyBlueBird = new Body();
            this.bodyBlueBird.addFixture(Geometry.createCircle(0.29));
            this.bodyBlueBird.getTransform().setTranslation(xSlingshot, -0.4);
            this.bodyBlueBird.setMass(MassType.NORMAL);
            this.bodyBlueBird.getFixture(0).setRestitution(0.75);
            this.gameObjectBlueBird = new GameObject("/blueBall.png", bodyBlueBird, new Vector2(0,0), 0.45);
            this.world.addBody(this.bodyBlueBird);
        }
    }

    private void createBoxRow(int row, int height){
        double yLowest = -2.2;
        double xLowest = 6;
        double boxWidth = 0.7;

        for (int i = 0; i < height; ++i){
            createBox(row * boxWidth + xLowest, yLowest + boxWidth * i);
        }
    }

    private void createBox(double x, double y){
        Body box = new Body();
        box.addFixture(Geometry.createRectangle(0.8, 0.8));
        box.getTransform().setTranslation(x, y + 0.4);
        box.setMass(MassType.NORMAL);
        box.getFixture(0).setRestitution(0.0);
        box.getFixture(0).setDensity(999);
        this.world.addBody(box);
        this.cratesBody.add(box);
        GameObject object = new GameObject("/box.jpg", box, new Vector2(0,0), 0.35);
        this.objectCrates.add(object);
        this.cratesGameObjects.add(object);
    }

    public void shoot(Point2D point){
//        this.bodyRedBird.setTransform();
//        if (point.getX() < 420 && point.getX() >= 0 && point.getY() < 1080 && point.getY() > 520 && !ScoreSystem.getInstance().isOver()){
//            Force force = new Force(-(point.getX() -420)/2,-(point.getY() - 520));
//
//            if (ScoreSystem.getInstance().isRedTurn()){
//                this.bodyRedBird.applyForce(force);
//            } else {
//                this.bodyBlueBird.applyForce(force);
//            }
//            // @TODO APPLIES FORCE
//        }
        if (point.getX() < 420 && point.getX() >= 0 && point.getY() < 1080 && point.getY() > 520){
            this.shoot(-(point.getX() - 420)/2, -(point.getY() - 520));
        }
    }

    public void shoot(){
        this.shoot(160.5, -134.0);
    }

    public void shoot(double x, double y){
        if (!ScoreSystem.getInstance().isOver()){
            if (ScoreSystem.getInstance().isRedTurn() && this.isRedPlayer){
                this.bodyRedBird.applyForce(new Force(x, y));
            } else if (!ScoreSystem.getInstance().isRedTurn() && !this.isRedPlayer){
                this.bodyBlueBird.applyForce(new Force(x, y));
            }
        }
    }

    public void update(double deltaTime){
        world.update(deltaTime);
    }

    public void draw(FXGraphics2D g2d){
//        this.gameObjects.forEach(gameObject -> gameObject.draw(g2d));
        this.objectCrates.forEach(gameObject -> gameObject.draw(g2d));
        this.gameObjectSlingshot.draw(g2d);
        this.gameObjectGround.draw(g2d);
        if (ScoreSystem.getInstance().isRedTurn()){
            this.setBallNull(false);
            if (this.gameObjectRedBird == null){
                this.setBall(true);
                this.boxDestroyer.setRed(this.bodyRedBird);
            }
            this.gameObjectRedBird.draw(g2d);
        } else {
            this.setBallNull(true);
            if (this.gameObjectBlueBird == null){
                this.setBall(false);
                this.boxDestroyer.setBlue(this.bodyBlueBird);
            }
            this.gameObjectBlueBird.draw(g2d);
        }
    }

}
