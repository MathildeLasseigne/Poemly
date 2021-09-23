package controller;

import model.*;
import view.GameUI;

import javax.swing.*;
import java.awt.*;

public class Game extends JPanel {

    private GameModele gameModele;

    private GameUI gameUI;

    public Game(Poem poem, Song song, Difficulty.DifficultyLevel difficultyLevel){
        this.gameModele = new GameModele(this);
        this.gameUI = new GameUI(this);
        this.add(this.gameUI);
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
