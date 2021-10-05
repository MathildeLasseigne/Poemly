package prototypeGame.model;

import controller.FXMLController;
import javafx.animation.FillTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

import java.io.IOException;

/**
 * A tile is an object containing a char and a {@link Tile#validated} state.
 * Color of the UI will change with its validated property.
 * <br/>A tile can only {@link Tile#moveDown()}
 */
public class Tile extends Pane {

    /**The width and height of a tile*/
    public final static int WIDTH = 67, HEIGHT = 65;

    /**The content of the char. Is final*/
    private final char contentChar;

    private final TileController tileController;

    /**The color of the tile when validated*/
    private final Color validColor = Color.rgb(52, 201, 36);

    /**
     * Indicate if the tile was validated (by checking its {@link Tile#contentChar} against the key typed
     * during the game.) Changing its value will change the tile color.
     */
    public BooleanProperty validated = new SimpleBooleanProperty(false);

    /**Duplicate validated to handle color change*/
    private BooleanProperty colorValidated = new SimpleBooleanProperty();

    private FillTransition fillTransition = new FillTransition();

    /**The translate transition of the tile. Manage automatic translations**/
    private TranslateTransition translateTransition = new TranslateTransition();

    /**
     * Movement of the tile every frame
     */
    private int jump = 1;

    /**
     * A tile is an object containing a char and a {@link Tile#validated} state.
     * Color of the UI will change with its validated property.
     * <br/>A tile can only {@link Tile#moveDown()}
     * @param x the original position on the X axis (is not meant to be changed)
     * @param y the original position on the Y axis (change with {@link Tile#moveDown()}
     * @param contentChar the content of the current char
     */
    public Tile(int x, int y, char contentChar){
        this.relocate(x,y);
        colorValidated.bind(this.validated);

        this.contentChar = contentChar;
        this.tileController = new TileController();
        Parent tileUI = null;
        try {
            tileUI = tileController.loadFXMLWithController(getClass().getResource("../view/TileUI.fxml"));
            this.getChildren().add(tileUI);
        } catch (IOException e) {
            System.out.println("\n Tile UI not loaded");
            e.printStackTrace();
        }

        setAnimation();
        setHandlers();
    }

    /**
     * Get the character of the tile
     * @return the character
     */
    public char getContentChar() {
        return contentChar;
    }

    /**
     * Move down the tile by the amount of points in the {@link Tile#jump} var
     */
    public void moveDown(){
        this.setTranslateY(this.getTranslateY() + this.jump);
    }

    /**
     * Change the color of the file if validated
     */
    private void setHandlers(){
        this.colorValidated.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                //tileController.tileBackground.setFill(validColor);
                this.fillTransition.play();
            }
        });
    }


    /**
     * Define the animation for fill change
     */
    private void setAnimation(){
        this.fillTransition.setFromValue((Color) this.tileController.tileBackground.getFill());
        this.fillTransition.setToValue(this.validColor);
        this.fillTransition.setShape(this.tileController.tileBackground);
        this.fillTransition.autoReverseProperty().set(false);

        this.translateTransition.setNode(this);
        this.translateTransition.setAutoReverse(false);
        this.translateTransition.setCycleCount(1);
    }

    /**
     * Return the TranslateTransition animation of the node that move the node.
     * Must be initialized
     * @return
     */
    public TranslateTransition getTranslateTransition() {
        return translateTransition;
    }

    private class TileController extends FXMLController {

        /**The Text containing the current char on the tile UI*/
        @FXML
        private Text character;

        /**The shape of the tile UI*/
        @FXML
        private Shape tileBackground;

        public TileController(){

        }

        @FXML
        public void initialize(){
            character.setText(String.valueOf(contentChar));
        }


    }
}
