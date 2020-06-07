package Game.Scenery;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class MenuScene {

    private Stage primaryStage;
    private GameScene nextScene;
    private Scene scene;
    private BorderPane mainPane;
    private Button buttonHost;
    private Button buttonJoin;

    /**
     * MenuScene (Constructor)
     * Creates, initializes and puts together Scene
     */
    public MenuScene(){
        this.mainPane = new BorderPane();

        //Used to be a host as red bird
        this.buttonHost = new Button("Host");
        this.buttonHost.setOnAction(event -> {
            this.nextScene.init(true);
            this.primaryStage.setScene(this.nextScene.getScene());
        });

        //Used to join a host as a blue bird
        this.buttonJoin = new Button("Join");
        this.buttonJoin.setOnAction(event -> {
            this.nextScene.init(false);
            this.primaryStage.setScene(this.nextScene.getScene());
        });

        this.mainPane.setLeft(this.buttonHost);
        this.mainPane.setRight(this.buttonJoin);

        this.scene = new Scene(this.mainPane);
    }

    /**
     * getScene
     * Returns scene of itself.
     * @return Scene
     */
    public Scene getScene() { return this.scene; }

    /**
     * setPrimary
     * Sets primaryStage which nextScene will be set in.
     * @param primaryStage
     */
    public void setPrimary(Stage primaryStage){ this.primaryStage = primaryStage; }

    /**
     * setNextScene
     * Sets nextScene, GameScene.
     * @param nextScene
     */
    public void setNextScene(GameScene nextScene){ this.nextScene = nextScene; }
}
