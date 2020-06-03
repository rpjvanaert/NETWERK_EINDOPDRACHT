package Game.Scenery;

import Game.Background;
import Game.Logic.Camera;
import Game.Logic.GameEngine;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.dyn4j.dynamics.World;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class GameScene implements Scenery{

    private Stage primaryStage;
    private Scenery nextScene;
    private Scene scene;

    private Background background;

    private Camera camera;
    private BorderPane mainPane;
    private ResizableCanvas canvas;

    private GameEngine gameEngine;

    public GameScene(){
        this.mainPane = new BorderPane();

        this.background = new Background();

        this.canvas = new ResizableCanvas(g -> draw(g), mainPane);
        this.mainPane.setBottom(this.canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());

        camera = new Camera(canvas, g -> draw(g), g2d);

        this.init();

        new AnimationTimer(){
            long last = 1;

            @Override
            public void handle(long now){
                if (last == -1){
                    last = now;
                }
                update((now-last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();

        this.scene = new Scene(this.mainPane);
    }

    private void init(){
        this.gameEngine = new GameEngine(new World());
    }

    private void update(double deltaTime){
        //@TODO mousepicker.update
        //@TODO world update
    }

    private void draw(FXGraphics2D graphics){
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        this.background.render(graphics);

        AffineTransform originalTransform = graphics.getTransform();

        graphics.setTransform(camera.getTransform((int) canvas.getWidth(), (int) canvas.getHeight()));
        graphics.scale(1, -1);

        //draw GameObjects
        this.gameEngine.draw(graphics);
    }

    public Scene getScene(){ return this.scene; }

    public void setPrimary(Stage primaryStage){ this.primaryStage = primaryStage; }

    public void setNextScene(Scenery nextScene){ this.nextScene = nextScene; }
}
