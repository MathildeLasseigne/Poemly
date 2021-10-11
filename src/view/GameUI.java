package view;

import controller.Game;
import javafx.scene.layout.Pane;

import javax.swing.*;

public class GameUI extends Pane {

    private Game game;

    public GameUI(Game game){
        this.game = game;

        //load fxml files + add it as children
    }

}
