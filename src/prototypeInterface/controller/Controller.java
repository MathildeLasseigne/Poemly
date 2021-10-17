package prototypeInterface.controller;

import controller.FXMLController;
import controller.Game;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.DataHolder;
import model.Difficulty;
import model.Poem;
import model.Song;
import view.GameUI;

import java.awt.*;
import java.io.File;
import java.io.IOException;


public class Controller extends FXMLController {

    private GameUI gameUI;

    private Stage stage;
    private Stage smallStage;
    private Scene scene;
    private Parent root;
    private Node container;

    @FXML
    private Button exitButton, homeButton, helpButton, scoreButton, menuButton, playButton, up, down;
    @FXML
    private MenuItem easy, medium, hard, customPoem;
    @FXML
    private Label song, poem, score, difficulty;

    @FXML
    private MenuButton songButton, poemButton;

    Difficulty.DifficultyLevel difficultyLevel = null;
    private Poem poemType = null;
    private Song currentSong = null;

    public Controller() throws IOException {

    }

    @FXML
    public void initialize(){

        setActions();
        setSongMenuItem();
        setCustomPoemButton();
        setPoemMenuItem();
    }


    public void setActions() {
        if (this.exitButton != null) {
            this.exitButton.setOnAction(e -> {
                this.stage = (Stage) this.exitButton.getScene().getWindow();
                this.stage.close();
                System.exit(0);
            });
        }

        if (this.homeButton != null) {
            this.homeButton.setOnAction(e -> {
                try {
                    this.smallStage.close();
                    //stage = (Stage)((Node)e.getSource()).getScene().getWindow();
                    Scene homeScene = setHome();
                    stage.setScene(homeScene);
                    this.scene = homeScene;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }

        if (this.helpButton != null) {
            this.helpButton.setOnAction(e -> {
                try {
                  openSmallStage(e, "prototypeInterface/view/Help.fxml");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }

        if (this.scoreButton != null) {
            this.scoreButton.setOnAction(e -> {
                try {
                    stage = (Stage)((Node)e.getSource()).getScene().getWindow();
                    stage.setScene(setScore());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }

        if (this.playButton != null) {
            this.playButton.setOnAction(e -> {
                if(poemType!=null&& currentSong !=null&&difficultyLevel!=null) {
                    try {
                        Game newGame = new Game(poemType, currentSong, difficultyLevel);
                        goToGame(e, newGame);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            poemType=null;
            currentSong =null;
            difficultyLevel=null;
        }

        if (this.menuButton != null) {
            this.menuButton.setOnAction(e -> {
                try {
                    openSmallStage(e, "prototypeInterface/view/Menu.fxml");
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


        if(this.up!=null) {
            this.up.setOnAction(e -> {
            });
        }

        if(this.down!=null) {
            this.down.setOnAction(e -> {
            });
        }
    }

    /**
     * Open the little stage (show) and change the currentScene {@link Controller#stage} to the one used in the new stage.
     * Old stage stay the same and is not closed
     * @param e
     * @param screen
     * @throws IOException
     */
    public void openSmallStage(ActionEvent e, String screen) throws IOException {
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

    public Scene setHome() throws IOException {
        Parent homeScreen = FXMLLoader.load(getClass().getClassLoader().getResource("prototypeInterface/view/Home.fxml"));
        Scene homeScene = new Scene(homeScreen);
        return homeScene;
    }

    public Scene setScore() throws IOException {
        Parent scoreScreen = FXMLLoader.load(getClass().getClassLoader().getResource("prototypeInterface/view/Score.fxml"));
        Scene scoreScene = new Scene(scoreScreen);
        return scoreScene;
    }

    private void setSongMenuItem(){
        if(songButton != null){
            for( int i = 0; i< DataHolder.projectDataManager.songList.size(); i++){
                Song tmp = DataHolder.projectDataManager.songList.get(i);
                MenuItem miTmp = new MenuItem(tmp.getName());
                miTmp.setOnAction(e -> currentSong = DataHolder.projectDataManager.mapNameToSong(miTmp.getText()));
                this.songButton.getItems().add(miTmp);
            }
        }

    }

    private void setPoemMenuItem(){
        if(this.poemButton != null){
            for( int i = 0; i< DataHolder.projectDataManager.poemList.size(); i++){
                this.poemButton.getItems().add(new MenuItem(DataHolder.projectDataManager.poemList.get(i).getName()));
            }
        }

    }

    private void setCustomPoemButton(){
        if(this.poemButton != null){
            MenuItem customPoem = new MenuItem("Add custom Poem");
            customPoem.setOnAction(e ->
            {
                Poem newPoem;
                FileChooser filechooser = new FileChooser();
                filechooser.setTitle("Choose a new poem");
                filechooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
                File fileSelection = filechooser.showOpenDialog(stage.getOwner());
                if(fileSelection != null){
                    newPoem = new Poem(fileSelection.getName(), fileSelection.getAbsolutePath());
                    //TODO select the newly created poem
                    boolean accepted = false;
                    try {
                        accepted = DataHolder.projectDataManager.addNewPoem(newPoem);

                    } catch (Exception ex) {
                        String errorMessage = ex.getMessage();
                        //TODO display error popup to user -> poem was not accepted
                    }
                    if(accepted){
                        poemType = newPoem;
                        //Set menu item for next time
                        MenuItem miTmp = new MenuItem(newPoem.getName());
                        miTmp.setOnAction(event -> poemType = DataHolder.projectDataManager.mapNameToPoem(miTmp.getText()));
                        this.poemButton.getItems().add(miTmp);
                    }


                } else {
                    //TODO error popup -> file was mot found
                }
            });
            this.poemButton.getItems().add(customPoem);
        }
    }

}

