package prototypeGame.widgets.Karaoke;

import javafx.beans.property.BooleanProperty;
import javafx.scene.Parent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.Difficulty;
import model.Poem;

import java.io.IOException;
import java.net.URL;

public class Karaoke extends AnchorPane {


    Poem poem;
    Difficulty.DifficultyLevel difficulty;

    KaraokeController karaokeController;

    public Karaoke(Poem poem, Difficulty.DifficultyLevel difficulty){
        this.poem = poem;
        this.difficulty = difficulty;

        this.karaokeController = new KaraokeController(this);

        Parent p = null;
        try {
            p = this.karaokeController.loadFXMLWithController(getClass().getResource("KaraokeUI.fxml"));
        } catch (IOException e){
            e.printStackTrace();
        }

        this.getChildren().add(p);
    }


    /**
     * Select the new character depending on the difficulty
     */
    public void next(){
        this.karaokeController.nextSeparatorChar();
    }

    /**
     * Check if the karaoke is finished
     * @return
     */
    public BooleanProperty isFinished(){
        return this.karaokeController.isFinished();
    }

    /**
     * Check if the key is the same as the current character depending on difficulty
     * @param keyEvent the event off the current key to check
     * @return If the current separator idx is -1 or the text is finished return false
     * @throws Exception  Using this methode when the text is finished will throws an error
     */
    public boolean checkKey(KeyEvent keyEvent) throws Exception {
        return this.karaokeController.checkKeyEvent(keyEvent);
    }

    /**
     * Return the Karaoke controller.
     * @return
     */
    public KaraokeController getKaraokeController() {
        return karaokeController;
    }

    public boolean isClippingNecessary(){
        return this.karaokeController.isClippingNecessary;
    }

}
