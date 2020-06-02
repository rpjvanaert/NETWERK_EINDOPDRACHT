package Game;

import org.jfree.fx.FXGraphics2D;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Background {

    private BufferedImage backgroundImage;

    public Background(){
        try {
            this.backgroundImage = ImageIO.read(getClass().getResource("background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render(FXGraphics2D g){
        g.scale(1, -1);
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.translate(0, 0);
        g.setTransform(affineTransform);

        g.drawImage(this.backgroundImage, 0, 0, null);
        g.scale(1, -1);
    }
}
