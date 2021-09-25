package widgets.tools;

import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;

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
}