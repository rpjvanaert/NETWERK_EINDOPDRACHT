package Game.Logic;

import javafx.event.EventHandler;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;




public class MousePicker {

    // Mouse Point2D object and boolean if newest mouseposition has been read.
    private Point2D mousePos = null;
    private boolean readPos = true;

    public MousePicker(Node node){
        /*
        On mouse clicked
        If canvas gets a mouse click with primary button;
        save mouse position and set to read position false.
         */
        node.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY){
                this.mousePos = new Point2D(e.getX(), e.getY());
                this.readPos = false;
            }
        });
    }

    /**
     * readPos
     * Returns true if position is read.
     * @return boolean
     */
    public boolean readPos(){
        return this.readPos;
    }

    /**
     * getMousePos
     * @return Point2D
     */
    public Point2D getMousePos(){
        this.readPos = true;
        return this.mousePos;
    }
}
