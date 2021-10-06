package prototypeGame.model;

import javafx.scene.layout.Pane;
import model.Difficulty;
import model.Poem;
import model.ProjectDataManager;
import model.Song;
import prototypeGame.controller.GameModele;
import prototypeGame.view.GameUI;
import widgets.tools.Utilities;

public class Game extends Pane {

    private GameModele gameModele;

    private GameUI gameUI;

    private ProjectDataManager.Theme theme;

    private Difficulty.DifficultyLevel difficulty;

    private Poem poem;

    private Song song;

    /**
     * A new game containing all data and controllers. Use as a widget
     * @param poem
     * @param song
     * @param difficultyLevel
     */
    public Game(Poem poem, Song song, Difficulty.DifficultyLevel difficultyLevel){
        this.poem = poem;
        this.song = song;
        this.theme = chooseRandomTheme();
        //this.theme = ProjectDataManager.Theme.School;
        this.difficulty = difficultyLevel;

        this.gameUI = new GameUI(this);
        this.gameModele = new GameModele(this); //Take panes from gameUI
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
     * Return the gameModel
     * @return
     */
    public GameModele getGameModel() {
        return gameModele;
    }

    public ProjectDataManager.Theme getTheme() {
        return theme;
    }

    public Poem getPoem() {
        return poem;
    }

    public Song getSong() {
        return song;
    }

    public Difficulty.DifficultyLevel getDifficulty() {
        return difficulty;
    }

    /**
     * Return a random theme
     * @return
     */
    private ProjectDataManager.Theme chooseRandomTheme(){
        int rand = Utilities.rangedRandomInt(0,2);
        if(rand == 0){
            return ProjectDataManager.Theme.Beach;
        } else if(rand == 1){
            return ProjectDataManager.Theme.City;
        } else {
            return ProjectDataManager.Theme.School;
        }
    }

}
