package Game.Scenery;

import javafx.scene.Scene;
import javafx.stage.Stage;

public interface Scenery {
    Scene getScene();
    void setPrimary(Stage primaryStage);
    void setNextScene(Scenery scene);
}
