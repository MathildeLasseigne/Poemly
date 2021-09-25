package prototypeGame.widgets.Karaoke;

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


}
