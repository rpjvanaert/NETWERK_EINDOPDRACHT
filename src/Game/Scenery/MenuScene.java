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
    private Button buttonJoin;

    public MenuScene(){
        this.mainPane = new BorderPane();

        this.buttonJoin = new Button("Join");
        this.buttonJoin.setOnAction(event -> {
            this.nextScene.init(true);
            this.primaryStage.setScene(this.nextScene.getScene());
        });

        this.mainPane.setCenter(this.buttonJoin);

        this.scene = new Scene(this.mainPane);
    }

    public Scene getScene() { return this.scene; }

    public void setPrimary(Stage primaryStage){ this.primaryStage = primaryStage; }

    public void setNextScene(GameScene nextScene){ this.nextScene = nextScene; }
}
