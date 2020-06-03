package Game.Logic;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;
import org.jfree.fx.FXGraphics2D;

import java.util.ArrayList;

public class GameEngine {
    private World world;
    private ArrayList<GameObject> gameObjects;

    private Body bodySlingshot;
    private Body bodyGround;
    private Body bodyRedBird;
    private Body bodyBlueBird;
    private GameObject gameObjectRedBird;
    private GameObject gameObjectBlueBird;
    private ArrayList<Body> cratesBody;
    private ArrayList<GameObject> cratesGameObjects;

    public GameEngine(World world){
        this.world = world;
        this.world.setGravity(new Vector2(0, -9.8));

        gameObjects = new ArrayList<>();

        this.cratesGameObjects = new ArrayList<>();
        this.cratesBody = new ArrayList<>();

        this.bodyGround = new Body();
        this.bodyGround.addFixture((Geometry.createRectangle(999, 0.8)));
        this.bodyGround.getTransform().setTranslation(0, -2.6);
        this.bodyGround.setMass(MassType.INFINITE);
        this.bodyGround.getFixture(0).setRestitution(0.75);
        this.world.addBody(this.bodyGround);
        gameObjects.add(new GameObject("/ground.png", this.bodyGround, new Vector2(0, -90), 1));

        this.bodySlingshot = new Body();
        this.bodySlingshot.addFixture(Geometry.createRectangle(0.4, 1.4));
        this.bodySlingshot.getTransform().setTranslation(0, -1.3);
        this.bodySlingshot.setMass(MassType.INFINITE);
        this.bodySlingshot.getFixture(0).setRestitution(0.75);
        world.addBody(this.bodySlingshot);
        gameObjects.add(new GameObject("/Slingshot.png", this.bodySlingshot, new Vector2(0, -60), 0.45));

        this.setBall(true);

        this.createBoxRow(0,3);
        this.createBoxRow(1,2);
        this.createBoxRow(2,3);

    }

    private void setBall(boolean isRed){
        if (isRed){
            this.bodyRedBird = new Body();
            this.bodyRedBird.addFixture(Geometry.createCircle(0.29));
            this.bodyRedBird.getTransform().setTranslation(0, -0.4);
            this.bodyRedBird.setMass(MassType.NORMAL);
            this.bodyRedBird.getFixture(0).setRestitution(0.75);
            this.gameObjectRedBird = new GameObject("/redBall.png", bodyRedBird, new Vector2(0,0), 0.45);
        } else {
            this.bodyBlueBird = new Body();
            this.bodyBlueBird.addFixture(Geometry.createCircle(0.29));
            this.bodyBlueBird.getTransform().setTranslation(0, -0.4);
            this.bodyBlueBird.setMass(MassType.NORMAL);
            this.bodyBlueBird.getFixture(0).setRestitution(0.75);
            this.gameObjectBlueBird = new GameObject("/blueBall.png", bodyBlueBird, new Vector2(0,0), 0.45);
        }
    }

    private void setBall(Body bodyBall, GameObject gameObjectBall, String pathIMG){
        bodyBall = new Body();
        bodyBall.addFixture(Geometry.createCircle(0.29));
        bodyBall.getTransform().setTranslation(0, -0.4);
        bodyBall.setMass(MassType.NORMAL);
        bodyBall.getFixture(0).setRestitution(0.75);
        gameObjectBall = new GameObject(pathIMG, bodyBall, new Vector2(0,0), 0.45);
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
        box.getFixture(0).setRestitution(0.40);
        this.world.addBody(box);
        this.cratesBody.add(box);
        GameObject object = new GameObject("/box.jpg", box, new Vector2(0,0), 0.35);
        this.gameObjects.add(object);
        this.cratesGameObjects.add(object);
    }

    public void draw(FXGraphics2D g2d){
        gameObjects.forEach(gameObject -> gameObject.draw(g2d));
    }
}
