package prototypeGame.model;

import controller.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;

/**
 * with position, letter, punctuation, etc
 */
public class Tile extends Pane {

    private char contentChar;

    private TileController tileController;

    /**
     * Deplacement of the tile every frame
     */
    private int jump = 10;

    public Tile(int x, int y, char contentChar){
        this.relocate(x,y);

        this.contentChar = contentChar;
        this.tileController = new TileController();
        Parent tileUI = null;
        try {
            tileUI = tileController.loadFXMLWithController(getClass().getResource(""));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.getChildren().add(tileUI);
        this.tileController.character.setText(String.valueOf(this.contentChar));

    }

    public char getContentChar() {
        return contentChar;
    }

    /**
     * Move down the tile by the amount of points in the jump var
     */
    public void moveDown(){
        this.setTranslateY(this.getTranslateY() + this.jump);
    }

    private class TileController extends FXMLController {

        @FXML
        private Text character;

        public TileController(){

        }

        @FXML
        public void initialize(){

        }


    }
}
