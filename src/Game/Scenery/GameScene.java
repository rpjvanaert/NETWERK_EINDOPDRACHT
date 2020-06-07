package Game.Scenery;

import Game.Background;
import Game.Logic.GameEngine;
import Game.Logic.MousePicker;
import Game.Logic.ScoreSystem;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.dyn4j.dynamics.World;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class GameScene {
    //Creates scene and creates every component for gameScene
    private Stage primaryStage;
    private MenuScene nextScene;
    private Scene scene;

    private Background background;

    private BorderPane mainPane;
    private ResizableCanvas canvas;

    private HBox hBox;
    private Button shootBlue;
    private Label scoreLabel;
    private Button backButton;

    private GameEngine gameEngine;
    private MousePicker mousePicker;

    public GameScene(){
        //Initializes layout managers
        this.mainPane = new BorderPane();
        this.hBox = new HBox();

        //Initializes scoreLabel and sets text to score of ScoreSystem.
        this.scoreLabel = new Label();
        this.scoreLabel.setText(ScoreSystem.getInstance().toString());

        this.background = new Background();

        //Initializes canvas  and puts it at bottom mainPane. Also creates FXGraphics2D for it.
        this.canvas = new ResizableCanvas(g -> draw(g), mainPane);
        this.mainPane.setBottom(this.canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());

        //Normal Animation Timer.
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

        //Back button.
        this.backButton = new Button("Go back");
        this.backButton.setOnAction(event -> {
            this.goToMenu();
        });

        //Shoots bird whose turn is.
        this.shootBlue = new Button("Shoot");
        this.shootBlue.setOnAction(event -> {
            this.gameEngine.shoot();
        });

        //Puts every node together.
        this.hBox.getChildren().addAll(this.backButton, this.shootBlue, this.scoreLabel);
        this.mainPane.setTop(this.hBox);

        this.scene = new Scene(this.mainPane);
    }

    /**
     * goToMenu
     * Sets to menu.
     */
    private void goToMenu(){
        GameScene gameScene = new GameScene();
        gameScene.setNextScene(this.nextScene);
        gameScene.setPrimary(this.primaryStage);
        this.nextScene.setNextScene(gameScene);
        this.primaryStage.setScene(this.nextScene.getScene());
        ScoreSystem.getInstance().reset();
    }

    /**
     * init
     * Creates new GameEngine and MousePicker.
     * @param playerRed
     */
    public void init(boolean playerRed){
        this.gameEngine = new GameEngine(new World(), this.scoreLabel, playerRed);
        this.mousePicker = new MousePicker(this.canvas);
    }

    /**
     * update
     * Updates GameScene, handles MousePicker
     * @param deltaTime
     */
    private void update(double deltaTime){
        if (mousePicker != null){
            if (!mousePicker.readPos()){
                Point2D mousePos = mousePicker.getMousePos();
                this.gameEngine.shoot(mousePos);
            }
            this.gameEngine.update(deltaTime);
        }
    }

    /**
     * draw
     * Draws background, gameObjects.
     * @param graphics
     */
    private void draw(FXGraphics2D graphics){
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        this.background.render(graphics);

        AffineTransform originalTransform = graphics.getTransform();

        graphics.setTransform(AffineTransform.getTranslateInstance(this.canvas.getWidth()/2, this.canvas.getHeight()/2));
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

    /**
     * getScene
     * Returns scene.
     * @return
     */
    public Scene getScene(){ return this.scene; }

    /**
     * setPrimary
     * Sets primary stage, for nextScene set.
     * @param primaryStage
     */
    public void setPrimary(Stage primaryStage){ this.primaryStage = primaryStage; }

    /**
     * setNextScene
     * Sets next scene.
     * @param nextScene
     */
    public void setNextScene(MenuScene nextScene){ this.nextScene = nextScene; }
}
