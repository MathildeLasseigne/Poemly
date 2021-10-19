package prototypeInterface.controller;

import controller.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.*;
import prototypeGame.model.Game;
import prototypeInterface.model.InterfaceModel;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class InterfaceController  extends FXMLController {

    private InterfaceModel interfaceModel;

    /**The name indicating which kind of fxml file is loaded*/
    public final String name;

    private Node fxmlLoaded=null;
    private boolean isFrontInterface;

    private Stage stage;

    @FXML
    private Button exitButton, homeButton, helpButton, scoreButton, menuButton, playButton, scoreUp, scoreDown;
    @FXML
    private RadioMenuItem easy, medium, hard, customPoem;
    @FXML
    private MenuButton songButton, poemButton;
    @FXML
    private Label scoreSong1, scoreSong2, scoreSong3, scoreSong4;
    @FXML
    private Label scorePoem1, scorePoem2, scorePoem3, scorePoem4;
    @FXML
    private Label scoreScore1, scoreScore2, scoreScore3, scoreScore4;
    @FXML
    private Label scoreDifficulty1, scoreDifficulty2, scoreDifficulty3, scoreDifficulty4;

    private ToggleGroup songToggleGroup = new ToggleGroup(), poemToggleGroup = new ToggleGroup();

    /**The idx of the first score displayed in the score panel*/
    private int currentScoreDisplayIdx;


    Difficulty.DifficultyLevel difficultyLevel = null;
    private Poem currentPoem = null;
    private Song currentSong = null;

    public InterfaceController(InterfaceModel interfaceModel, String name, boolean isFrontInterface){
        this.name = name;
        this.interfaceModel = interfaceModel;
        this.isFrontInterface = isFrontInterface;
    }

    @FXML
    public void initialize(){
        setActions();
        setSongMenuItem();
        setCustomPoemButton();
        setPoemMenuItem();
        initScore();
    }


    public void setActions() {
        if (this.exitButton != null) {
            this.exitButton.setOnAction(e -> {
                this.stage = (Stage) this.exitButton.getScene().getWindow();
                this.stage.close();
                DataHolder.saveAllNewData();
                System.exit(0);
            });
        }

        if (this.homeButton != null) {
            this.homeButton.setOnAction(e -> {
                if(! interfaceModel.saveNode(name, fxmlLoaded)){
                    System.out.println("Save "+name+" did not work !!!");
                }
                if(isFrontInterface){
                    interfaceModel.setFrontInterface(null);
                } else {
                    Node homeNode = interfaceModel.retrieveSavedNode("Home");
                    if( homeNode != null){
                        interfaceModel.setBackInterface(homeNode);
                    } else {
                        interfaceModel.createNewHome();
                    }
                }
            });
        }

        if (this.helpButton != null) {
            this.helpButton.setOnAction(e -> {
                Node helpNode = interfaceModel.retrieveSavedNode("Help");
                if( helpNode != null){
                    interfaceModel.setFrontInterface(helpNode);
                } else {
                    interfaceModel.createNewHelp();
                }

            });
        }

        if (this.scoreButton != null) {
            this.scoreButton.setOnAction(e -> {
                if(!interfaceModel.saveNode(name, fxmlLoaded)){
                    System.out.println("Save "+name+" did not work !!!");
                }
                if(isFrontInterface){
                    interfaceModel.setFrontInterface(null);
                } else {
                    Node scoreNode = interfaceModel.retrieveSavedNode("Score");
                    if (scoreNode != null) {
                        interfaceModel.setBackInterface(scoreNode);
                    } else {
                        interfaceModel.createNewScore();
                    }
                }
            });
        }


        if (this.playButton != null) {
            this.playButton.setOnAction(e -> {
                System.out.println("play button selected");
                if(currentPoem !=null&& currentSong !=null&&difficultyLevel!=null) {
                    Game newGame = new Game(currentPoem, currentSong, difficultyLevel);
                    newGame.setExitHandler(event -> interfaceModel.createNewHome());
                    System.out.println("start game");
                    interfaceModel.setNewGame(newGame);
                } else {
                    Toolkit.getDefaultToolkit().beep();
                }
            });
            currentPoem =null;
            currentSong =null;
            difficultyLevel=null;
        }

        if (this.menuButton != null) {
            this.menuButton.setOnAction(e -> {

                Node menuNode = interfaceModel.retrieveSavedNode("Menu");
                if (menuNode != null) {
                    interfaceModel.setFrontInterface(menuNode);
                } else {
                    interfaceModel.createNewMenu();
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


        if(this.scoreUp!=null) {
            this.scoreUp.setOnAction(e -> {
                if(this.name == "Score"){
                    int currentDisplay = this.currentScoreDisplayIdx-4;
                    //Set every score
                    if(currentDisplay >= 0){
                        this.currentScoreDisplayIdx = currentDisplay;
                        setScoreFrom(currentDisplay);
                    }

                }
            });
        }

        if(this.scoreDown!=null) {
            this.scoreDown.setOnAction(e -> {
                if(this.name == "Score"){
                    int currentDisplay = this.currentScoreDisplayIdx+4;
                    if(currentDisplay < DataHolder.scoreManager.getScoreList().size()){ //TODO change to length in score manager
                        this.currentScoreDisplayIdx = currentDisplay;
                        setScoreFrom(currentDisplay);
                    }

                }
            });
        }
    }


    private void setSongMenuItem(){
        if(songButton != null){
            for(int i = 0; i< DataHolder.projectDataManager.songList.size(); i++){
                Song tmp = DataHolder.projectDataManager.songList.get(i);
                RadioMenuItem miTmp = new RadioMenuItem(tmp.getName());
                miTmp.setToggleGroup(this.songToggleGroup);
                miTmp.setOnAction(e ->
                        {
                            currentSong = DataHolder.projectDataManager.mapNameToSong(miTmp.getText());
                            if(currentPoem == null){
                                Toolkit.getDefaultToolkit().beep();
                            }
                        });

                this.songButton.getItems().add(miTmp);
            }
        }
    }

    private void setPoemMenuItem(){
        if(this.poemButton != null){
            for( int i = 0; i< DataHolder.projectDataManager.poemList.size(); i++){
                Poem poem = DataHolder.projectDataManager.poemList.get(i);
                RadioMenuItem miTmp = new RadioMenuItem(poem.getName());
                miTmp.setToggleGroup(this.poemToggleGroup);
                miTmp.setOnAction(e -> {
                    currentPoem = DataHolder.projectDataManager.mapNameToPoem(miTmp.getText());
                    if(currentPoem == null){
                        Toolkit.getDefaultToolkit().beep();
                    }
                        }
                );
                this.poemButton.getItems().add(miTmp);
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
                File fileSelection = filechooser.showOpenDialog(this.poemButton.getScene().getWindow());
                if(fileSelection != null){
                    newPoem = new Poem(fileSelection.getName(), fileSelection.getAbsolutePath());
                    boolean accepted = false;
                    try {
                        accepted = DataHolder.projectDataManager.addNewPoem(newPoem);

                    } catch (Exception ex) {
                        String errorMessage = ex.getMessage();
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText(errorMessage);
                        alert.showAndWait();
                    }
                    if(accepted){
                        currentPoem = newPoem;
                        //Set menu item for next time
                        RadioMenuItem miTmp = new RadioMenuItem(newPoem.getName());
                        miTmp.setToggleGroup(this.poemToggleGroup);
                        miTmp.setOnAction(event -> currentPoem = DataHolder.projectDataManager.mapNameToPoem(miTmp.getText()));

                        this.poemButton.getItems().add(miTmp);
                        miTmp.setSelected(true);
                    }


                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("File was not found");
                    alert.showAndWait();
                }
            });
            this.poemButton.getItems().add(customPoem);
        }
    }

    private void initScore(){
        if(this.name == "Score"){
            this.currentScoreDisplayIdx = 0; //Init
            int currentDisplay = this.currentScoreDisplayIdx;
            setScoreFrom(currentDisplay);
        }
    }

    /**
     * Save the fxml file this controller loaded
     * @param node
     */
    public void setFXMLLoaded(Node node){
        this.fxmlLoaded = node;
    }


    public void setScoreFrom(int scoreDisplayIdx){
        ArrayList<Score> scoreList = DataHolder.scoreManager.getScoreList();
        int currentDisplayIdX = scoreDisplayIdx;
        int currentScoreUIIdx = 0;
        //Set every score
        Score s = null;
        if(scoreList.size()> this.currentScoreDisplayIdx + currentScoreUIIdx){
            s = scoreList.get(currentDisplayIdX);
            this.scorePoem1.setText(s.getPoem());
            this.scoreSong1.setText(s.getSong());
            this.scoreDifficulty1.setText(Difficulty.toString(s.getDifficulty()));
            this.scoreScore1.setText("Score: " + Math.round(s.getScore())+"%");

            currentDisplayIdX++;
            currentScoreUIIdx++;
        } else {
            this.scorePoem1.setText("Poem Name");
            this.scoreSong1.setText("Song Name");
            this.scoreDifficulty1.setText("Difficulty");
            this.scoreScore1.setText("Score");
        }
        if(scoreList.size()> this.currentScoreDisplayIdx + currentScoreUIIdx){
            s = scoreList.get(currentDisplayIdX);
            this.scorePoem2.setText(s.getPoem());
            this.scoreSong2.setText(s.getSong());
            this.scoreDifficulty2.setText(Difficulty.toString(s.getDifficulty()));
            this.scoreScore2.setText("Score: " + Math.round(s.getScore())+"%");

            currentDisplayIdX++;
            currentScoreUIIdx++;
        } else {
            this.scorePoem2.setText("Poem Name");
            this.scoreSong2.setText("Song Name");
            this.scoreDifficulty2.setText("Difficulty");
            this.scoreScore2.setText("Score");
        }
        if(scoreList.size()> this.currentScoreDisplayIdx + currentScoreUIIdx){
            s = scoreList.get(currentDisplayIdX);
            this.scorePoem3.setText(s.getPoem());
            this.scoreSong3.setText(s.getSong());
            this.scoreDifficulty3.setText(Difficulty.toString(s.getDifficulty()));
            this.scoreScore3.setText("Score: " + Math.round(s.getScore())+"%");

            currentDisplayIdX++;
            currentScoreUIIdx++;
        } else {
            this.scorePoem3.setText("Poem Name");
            this.scoreSong3.setText("Song Name");
            this.scoreDifficulty3.setText("Difficulty");
            this.scoreScore3.setText("Score");
        }
        if(scoreList.size()> this.currentScoreDisplayIdx + currentScoreUIIdx){
            s = scoreList.get(currentDisplayIdX);
            this.scorePoem4.setText(s.getPoem());
            this.scoreSong4.setText(s.getSong());
            this.scoreDifficulty4.setText(Difficulty.toString(s.getDifficulty()));
            this.scoreScore4.setText("Score: " + Math.round(s.getScore())+"%");
            currentScoreUIIdx++;
        } else {
            this.scorePoem4.setText("Poem Name");
            this.scoreSong4.setText("Song Name");
            this.scoreDifficulty4.setText("Difficulty");
            this.scoreScore4.setText("Score");
        }
    }
}
