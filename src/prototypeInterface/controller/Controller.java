package prototypeInterface.controller;

import controller.FXMLController;
import controller.Game;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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

    private Stage stage;
    private Scene scene;
    private Parent root;

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

    public Controller() throws IOException {
        setActions();

    }

    public void setActions(){
        this.homeButton.setOnAction(e-> {
            try {
                switchScene(e, "Windows/Home.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        this.exitButton.setOnAction(e-> {
            this.stage = (Stage) this.exitButton.getScene().getWindow();
            this.stage.close();
        });

        this.scoreButton.setOnAction(e-> {
            try {
                switchScene(e, "prototypeInterface/view/Score.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        this.playButton.setOnAction(e-> {
            try {
                switchScene(e, screens[random]);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        this.helpButton.setOnAction(e->{
            try {
                openStage(e, "prototypeInterface/view/Help.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        this.menuButton.setOnAction(e->{
            try {
                openStage(e, "prototypeInterface/view/Menu.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        this.easy.setOnAction(e -> {
            Difficulty.fromString("easy");
        });

        this.medium.setOnAction(e -> {
            Difficulty.fromString("medium");
        });

        this.hard.setOnAction(e -> {
            Difficulty.fromString("hard");
        });

        this.customPoem.setOnAction(e-> {
            Poem.createEmptyPoem();
        });

    }

    public void openStage(ActionEvent e, String screen) throws IOException {
        root = FXMLLoader.load(getClass().getClassLoader().getResource(screen)); //Only home menu
        stage = new Stage();

        Scene scene = new Scene(root) ;
        scene.setFill(Color.TRANSPARENT);

        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

    public void switchScene(ActionEvent e, String screen) throws IOException {
        root = FXMLLoader.load(getClass().getClassLoader().getResource(screen));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
    }
}

