package prototypeGame.view;


import javafx.scene.layout.Pane;
import prototypeGame.controller.Game;

public class GameUI extends Pane {

    private Game game;

    public GameUI(Game game){
        this.game = game;
    }

}
