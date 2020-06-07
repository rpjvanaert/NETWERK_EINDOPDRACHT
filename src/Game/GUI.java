package Game;

import Game.Scenery.GameScene;
import Game.Scenery.MenuScene;
import javafx.application.Application;
import javafx.stage.Stage;

public class GUI  extends Application {

    @Override
    public void start(Stage primaryStage){
        //Creates scenes.
        MenuScene menuScene = new MenuScene();
        GameScene gameScene = new GameScene();

        //Sets primaryStage for both scenes.
        menuScene.setPrimary(primaryStage);
        gameScene.setPrimary(primaryStage);

        //Sets nextScene for both scenes.
        menuScene.setNextScene(gameScene);
        gameScene.setNextScene(menuScene);

        //primaryStage setup and .show().
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
