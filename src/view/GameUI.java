package view;

import controller.Game;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import prototypeInterface.controller.InterfaceController;

import javax.swing.*;
import java.io.IOException;

public class GameUI extends Pane {

    public InterfaceController interfaceController;


    private Game game;

    public GameUI(Game game){
        this.game = game;
        //this.interfaceController = new InterfaceController();
        //this.getChildren().add();
    }

}
