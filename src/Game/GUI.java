package Game;

import Game.Scenery.MenuScene;
import Game.Scenery.Scenery;
import javafx.application.Application;
import javafx.stage.Stage;

public class GUI  extends Application {

    @Override
    public void start(Stage primaryStage){
        Scenery MenuScene = new MenuScene();


    }

    public static void main(String[] args) {
        launch(GUI.class);
    }
}
