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

    public Scene setHome() throws IOException {
        Parent homeScreen = FXMLLoader.load(getClass().getClassLoader().getResource("prototypeInterface/view/Home.fxml"));
        Scene homeScene = new Scene(homeScreen);
        return homeScene;
    }

    public Scene setHelp() throws IOException {
        Parent helpScreen = FXMLLoader.load(getClass().getClassLoader().getResource("prototypeInterface/view/Help.fxml"));
        Scene helpScene = new Scene(helpScreen);
        return helpScene;
    }

    public Scene setScore() throws IOException {
        Parent scoreScreen = FXMLLoader.load(getClass().getClassLoader().getResource("prototypeInterface/view/Score.fxml"));
        Scene scoreScene = new Scene(scoreScreen);
        return scoreScene;
    }

    public Scene setMenu() throws IOException {
        Parent scoreScreen = FXMLLoader.load(getClass().getClassLoader().getResource("prototypeInterface/view/Menu.fxml"));
        Scene scoreScene = new Scene(scoreScreen);
        return scoreScene;
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
