package Game.Scenery;

import Game.Background;
import Game.GUI;
import Game.Logic.Camera;
import Game.Logic.GameEngine;
import Game.Logic.MousePicker;
import Game.Logic.ScoreSystem;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.dyn4j.dynamics.World;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class GameScene {

    private Stage primaryStage;
    private MenuScene nextScene;
    private Scene scene;

    private Background background;

    private Camera camera;
    private BorderPane mainPane;
    private ResizableCanvas canvas;

    private HBox hBox;
    private Button shootBlue;
    private Label scoreLabel;
    private javafx.scene.control.Button backButton;

    private GameEngine gameEngine;
    private MousePicker mousePicker;

    public GameScene(){
        this.mainPane = new BorderPane();
        this.hBox = new HBox();

        this.scoreLabel = new Label();
        this.scoreLabel.setText(ScoreSystem.getInstance().toString());

        this.background = new Background();

        this.canvas = new ResizableCanvas(g -> draw(g), mainPane);
        this.mainPane.setBottom(this.canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());

        camera = new Camera(canvas, g -> draw(g), g2d);

//        this.init();

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

        this.backButton = new Button("Go back");
        this.backButton.setOnAction(event -> {
            this.goToMenu();
        });
        this.shootBlue = new Button("Shoot");
        this.shootBlue.setOnAction(event -> {
            this.gameEngine.shoot();
        });
        this.hBox.getChildren().addAll(this.backButton, this.shootBlue, this.scoreLabel);
        this.mainPane.setTop(this.hBox);
    }

    private void goToMenu(){
        GameScene gameScene = new GameScene();
        gameScene.setNextScene(this.nextScene);
        gameScene.setPrimary(this.primaryStage);
        this.nextScene.setNextScene(gameScene);
        this.primaryStage.setScene(this.nextScene.getScene());
        ScoreSystem.getInstance().reset();
        //@TODO reset to menu
    }

    public void init(boolean playerRed){
        this.gameEngine = new GameEngine(new World(), this.scoreLabel, playerRed);
        this.mousePicker = new MousePicker(this.canvas);
    }

    private void update(double deltaTime){
        if (mousePicker != null){
            if (!mousePicker.readPos()){
                Point2D mousePos = mousePicker.getMousePos();
                this.gameEngine.shoot(mousePos);
            }
            this.gameEngine.update(deltaTime);
        }
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
        if (this.gameEngine != null){
            this.gameEngine.draw(graphics);
        }

        if (ScoreSystem.getInstance().isOver()){
            Font font = new Font("Arial", Font.PLAIN, 60);
            Shape shape = font.createGlyphVector(graphics.getFontRenderContext(), ScoreSystem.getInstance().toString()).getOutline();
            AffineTransform at = new AffineTransform();
            at.setToScale(1, -1);
            at.translate(-220,0);
            graphics.setPaint(Color.WHITE);
            graphics.fill(at.createTransformedShape(shape));
            graphics.setPaint(Color.BLACK);
            graphics.draw(at.createTransformedShape(shape));
        }
    }

    public Scene getScene(){ return this.scene; }

    public void setPrimary(Stage primaryStage){ this.primaryStage = primaryStage; }

//    public void setMenuScene(MenuScene menuScene){
//        this.menuScene = menuScene;
//    }

    public void setNextScene(MenuScene nextScene){ this.nextScene = nextScene; }
}
