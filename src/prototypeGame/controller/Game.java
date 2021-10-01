package prototypeGame.controller;

import javafx.scene.layout.Pane;
import model.Difficulty;
import model.Poem;
import model.Song;
import prototypeGame.model.GameModele;
import prototypeGame.view.GameUI;

public class Game extends Pane {

    private GameModele gameModele;

    private GameUI gameUI;

    public Game(Poem poem, Song song, Difficulty.DifficultyLevel difficultyLevel){
        this.gameModele = new GameModele(this);
        this.gameUI = new GameUI(this);
        this.getChildren().add(this.gameUI);
    }

    /**
     * Return the game UI. All graphic modifications must happen on the gameUI
     * @return
     */
    public GameUI getGameUI() {
        return gameUI;
    }

    /**
     * Return the gameModele
     * @return
     */
    public GameModele getGameModele() {
        return gameModele;
    }


}
