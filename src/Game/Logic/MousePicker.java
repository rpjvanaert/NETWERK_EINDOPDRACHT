package Game.Logic;

import javafx.event.EventHandler;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;




public class MousePicker {

    private Point2D mousePos = null;
    private boolean requestedPos = true;

    public MousePicker(Node node){
        EventHandler<? super MouseEvent> oldMouseClicked = node.getOnMouseClicked();

        node.setOnMouseClicked(e -> {
            if (oldMouseClicked != null){
                oldMouseClicked.handle(e);
            }
            if (e.getButton() == MouseButton.PRIMARY){
                this.mousePos = new Point2D(e.getX(), e.getY());
                this.requestedPos = false;
            }
        });
    }

    public boolean isNewPosition(){
        return !this.requestedPos;
    }

    public Point2D getMousePos(){
        return this.mousePos;
    }
}
