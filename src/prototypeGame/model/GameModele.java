package prototypeGame.model;


import prototypeGame.controller.Game;

public class GameModele {

    private Game game;

    /**
     * Store the data necessary to run a game and allow manipulation of score
     */
    public GameModele(Game game){
        this.game = game;
    }

}
