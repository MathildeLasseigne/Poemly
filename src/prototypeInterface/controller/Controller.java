package prototypeInterface.controller;

import controller.FXMLController;
import controller.Game;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Difficulty;
import model.Poem;
import model.Score;
import prototypeGame.widgets.Karaoke.Karaoke;
import prototypeGame.widgets.Karaoke.KaraokeColorizer;
import view.GameUI;
import widgets.tools.Utilities;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Controller extends FXMLController {

    private GameUI gameUI;

    Stage stage = new Stage();

    private String[] screens = {"Windows/School.fxml", "Windows/City.fxml", "Windows/Beach.fxml"};
    int random = 	new Random().nextInt(3);

    @FXML
    private Button exitButton, homeButton, helpButton, scoreButton, menuButton, playButton, up, down;
    private MenuButton difficultyButton, songButton, poemButton;
    private MenuItem easy, medium, hard, song1, song2, poem1, poem2, customPoem;
    private Label song, poem, score, difficulty;
    private ProgressBar progressBar;
    private Poem poemSelected;
    private Panel textPanel;

    public Controller() {

    }

    public void goHome() throws IOException {
        try {
            Parent home = FXMLLoader.load(getClass().getClassLoader().getResource("prototypeInterface/view/Home.fxml"));
            this.homeButton.setOnAction(e -> stage.setScene(new Scene(home)));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void toScore() throws IOException {
        try {
            Parent score = FXMLLoader.load(getClass().getResource("prototypeInterface/view/Score.fxml"));
            this.scoreButton.setOnAction(e -> stage.setScene(new Scene(score)));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void toGame(){
        try {
            Scene theme = FXMLLoader.load(getClass().getClassLoader().getResource(screens[random]));
            this.playButton.setOnAction(e -> stage.setScene(theme));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void toHelp() throws IOException {
        Scene help = FXMLLoader.load(getClass().getClassLoader().getResource(screens[random]));
        stage = new Stage();
        this.helpButton.setOnAction(e -> stage.setScene(help));
        stage.getScene().setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);

    }

    public void setEasyDifficulty(){
        this.easy.setOnAction(e -> Difficulty.fromString("easy"));
        }
    public void setMediumDifficulty(){
        this.medium.setOnAction(e -> Difficulty.fromString("medium"));
    }
    public void setHardDifficulty() {
        this.hard.setOnAction(e -> Difficulty.fromString("hard"));
    }
    public void setSong(){

    }

    public void setPoem(){
        this.customPoem.setOnAction(e -> Poem.createEmptyPoem());

    }


    }

