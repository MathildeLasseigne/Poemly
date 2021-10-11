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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Difficulty;
import model.Poem;
import model.Score;
import model.Song;
import prototypeGame.widgets.Karaoke.Karaoke;
import prototypeGame.widgets.Karaoke.KaraokeColorizer;
import view.GameUI;
import widgets.tools.Utilities;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;


public class Controller extends FXMLController {

    private GameUI gameUI;

    private Stage stage;
    private Stage smallStage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button exitButton, homeButton, helpButton, scoreButton, menuButton, playButton, up, down;
    @FXML
    private MenuItem easy, medium, hard, song1, song2, poem1, poem2, customPoem;
    @FXML
    private Label song, poem, score, difficulty;


    Difficulty.DifficultyLevel difficultyLevel = null;
    private Poem poemType = null;
    private Song songType = null;

    public Controller() throws IOException {

    }

    @FXML
    public void initialize(){
        setActions();
    }


    public void setActions() {
        if (this.exitButton != null) {
            this.exitButton.setOnAction(e -> {
                this.stage = (Stage) this.exitButton.getScene().getWindow();
                this.stage.close();
            });
        }

        if (this.homeButton != null) {
            this.homeButton.setOnAction(e -> {
                try {
                    stage = (Stage)((Node)e.getSource()).getScene().getWindow();
                    stage.setScene(gameUI.setHome());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }

        if (this.helpButton != null) {
            this.helpButton.setOnAction(e -> {
                try {
                  openStage(e, "prototypeInterface/view/Help.fxml");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }

        if (this.scoreButton != null) {
            this.scoreButton.setOnAction(e -> {
                try {
                    stage = (Stage)((Node)e.getSource()).getScene().getWindow();
                    stage.setScene(gameUI.setScore());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }

        if (this.playButton != null) {
            this.playButton.setOnAction(e -> {
                if(poemType!=null&&songType!=null&&difficultyLevel!=null) {
                    try {
                        Game newGame = new Game(poemType, songType, difficultyLevel);
                        goToGame(e, newGame);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            poemType=null;
            songType=null;
            difficultyLevel=null;
        }

        if (this.menuButton != null) {
            this.menuButton.setOnAction(e -> {
                try {
                    openStage(e, "prototypeInterface/view/Menu.fxml");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }

        if (this.easy != null) {
            this.easy.setOnAction(e -> {
                difficultyLevel = Difficulty.fromString("Easy");
            });
        }

        if (this.medium != null) {
            this.medium.setOnAction(e -> {
                difficultyLevel = Difficulty.fromString("Medium");
            });
        }

        if (this.hard != null) {
            this.hard.setOnAction(e -> {
                difficultyLevel = Difficulty.fromString("Hard");
            });
        }

        if (this.customPoem != null) {
            this.customPoem.setOnAction(e -> {
                Poem.createEmptyPoem();
            });
        }

        if(this.poem1 != null){
            this.poem1.setOnAction(e -> {
                poemType = new Poem("poem1", "assets/poems/poem1.txt");
            });
        }
        if(this.poem2 != null) {
            this.poem2.setOnAction(e -> {
                poemType = new Poem("poem2", "assets/poems/poem2.txt");
            });
        }

        if(this.song1 != null) {
            this.song1.setOnAction(e -> {
                songType = new Song("song1", "assets/poems/poem2.txt");
            });
        }

        if(this.song2 !=null) {
            this.song2.setOnAction(e -> {
                songType = new Song("song2", "assets/poems/poem2.txt");
            });
        }

        if(this.up!=null) {
            this.up.setOnAction(e -> {
            });
        }

        if(this.down!=null) {
            this.down.setOnAction(e -> {
            });
        }
    }

    public void openStage(ActionEvent e, String screen) throws IOException {
        root = FXMLLoader.load(getClass().getClassLoader().getResource(screen)); //Only home menu
        smallStage = new Stage();

        scene = new Scene(root) ;
        scene.setFill(Color.TRANSPARENT);

        smallStage.setScene(scene);
        smallStage.initStyle(StageStyle.TRANSPARENT);
        smallStage.show();
    }

    public void goToGame(ActionEvent e, Node screen) throws IOException {
        root = (Parent) screen;
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
    }
}

