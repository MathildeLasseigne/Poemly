package view;

import controller.Game;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import javax.swing.*;
import java.io.IOException;

public class GameUI extends Pane {

    private Game game;

    public GameUI(Game game){
        this.game = game;
    }



    public Parent setMenu()throws IOException  {
        Parent menuPanel = null;
            menuPanel = FXMLLoader.load((getClass().getResource("prototypeInterface/view/Menu.fxml")));
            return menuPanel;
        }

    public Parent setHelp()throws IOException   {
        Parent helpPanel = null;
        helpPanel = FXMLLoader.load((getClass().getResource("prototypeInterface/view/Menu.fxml")));
        return helpPanel;
    }



    public Scene setSchool() throws IOException {
        Parent scoreScreen = FXMLLoader.load(getClass().getClassLoader().getResource("prototypeInterface/view/School.fxml"));
        Scene scoreScene = new Scene(scoreScreen);
        return scoreScene;
    }
    public Scene setCity() throws IOException {
        Parent scoreScreen = FXMLLoader.load(getClass().getClassLoader().getResource("prototypeInterface/view/City.fxml"));
        Scene scoreScene = new Scene(scoreScreen);
        return scoreScene;
    }
    public Scene setBeach() throws IOException {
        Parent scoreScreen = FXMLLoader.load(getClass().getClassLoader().getResource("prototypeInterface/view/Beach.fxml"));
        Scene scoreScene = new Scene(scoreScreen);
        return scoreScene;
    }
    public Scene setGameOver() throws IOException {
        Parent scoreScreen = FXMLLoader.load(getClass().getClassLoader().getResource("prototypeInterface/view/GameOver.fxml"));
        Scene scoreScene = new Scene(scoreScreen);
        return scoreScene;
    }
}
