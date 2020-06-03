package Game;

import Game.Scenery.GameScene;
import Game.Scenery.MenuScene;
import Game.Scenery.Scenery;
import javafx.application.Application;
import javafx.stage.Stage;

public class GUI  extends Application {

    @Override
    public void start(Stage primaryStage){
        Scenery menuScene = new MenuScene();
        Scenery gameScene = new GameScene();

        menuScene.setPrimary(primaryStage);
        gameScene.setPrimary(primaryStage);

        menuScene.setNextScene(gameScene);
        gameScene.setNextScene(menuScene);

        primaryStage.setScene(menuScene.getScene());
        primaryStage.setTitle("Angry birds");
        primaryStage.setHeight(1080);
        primaryStage.setWidth(1920);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(GUI.class);
    }
}
