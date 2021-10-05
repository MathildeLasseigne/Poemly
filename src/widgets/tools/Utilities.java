package widgets.tools;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Utilities {



    /**
     * Clips the children of the specified {@link Region} to its current size.
     * This requires attaching a change listener to the region’s layout bounds,
     * as JavaFX does not currently provide any built-in way to clip children.
     * <br/> The region children still override its borders.
     * <br/><a href="https://news.kynosarges.org/2016/11/03/javafx-pane-clipping/">Source</a>
     * @param region the {@link Region} whose children to clip
     * @param arc the {@link Rectangle#getArcWidth()} and {@link Rectangle#getArcWidth()}
     *            of the clipping {@link Rectangle}
     * @throws NullPointerException if {@code region} is {@code null}
     */
    public static void clipChildren(Region region, double arc) {

        final Rectangle outputClip = new Rectangle();
        outputClip.setArcWidth(arc);
        outputClip.setArcHeight(arc);
        region.setClip(outputClip);

        region.layoutBoundsProperty().addListener((ov, oldValue, newValue) -> {
            outputClip.setWidth(newValue.getWidth());
            outputClip.setHeight(newValue.getHeight());
        });
    }


    /**
     * Create random int in range [rangeMin, rangeMax]
     * @param rangeMin Minimal range
     * @param rangeMax Maximal range (must be positive !)
     * @return the value of the random int
     */
    public static int rangedRandomInt(int rangeMin, int rangeMax) {
        int randomValue;
        if(rangeMin<0 ||rangeMax<=0||(rangeMax-rangeMin<=0)){
            System.out.println("Argument for random must be positive !!");
            if(rangeMin<0){
                System.out.println(rangeMin+" is negative");
            }
            return rangeMin;
        } else {
            Random r = new Random();
            randomValue = rangeMin + r.nextInt(rangeMax - rangeMin);

        }
        return randomValue;
    }

    /**
     * Calculate the bounds in parent of the node to screen
     * @param n the node from which the bounds are taken
     * @return
     */
    public static Bounds parentToScreen(Node n){
        Parent p = n.getParent();
        Bounds b = n.getBoundsInParent();
        return p.localToScreen(b);
    }
}
