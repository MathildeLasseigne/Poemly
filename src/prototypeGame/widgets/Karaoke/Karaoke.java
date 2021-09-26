package prototypeGame.widgets.Karaoke;

import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.Difficulty;
import model.Poem;

public class Karaoke extends AnchorPane {


    Poem poem;
    Difficulty.DifficultyLevel difficulty;

    KaraokeController karaokeController;

    public Karaoke(Poem poem, Difficulty.DifficultyLevel difficulty){
        this.poem = poem;
        this.difficulty = difficulty;

        this.karaokeController = new KaraokeController(this);
    }


    /**
     * Select the new character depending on the difficulty
     */
    public void next(){
        this.karaokeController.nextSeparatorChar();
    }

    /**
     * Check if the key is the same as the current character depending on difficulty
     * @param keyEvent
     */
    public void checkKey(KeyEvent keyEvent){
        //TODO
    }

    /**
     * Return the Karaoke controller.
     * @return
     */
    public KaraokeController getKaraokeController() {
        return karaokeController;
    }

}
