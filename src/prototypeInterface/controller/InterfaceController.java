package prototypeInterface.controller;

import controller.FXMLController;
import controller.Game;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.DataHolder;
import model.Difficulty;
import model.Poem;
import model.Song;
import prototypeInterface.model.InterfaceModel;

import java.awt.*;
import java.io.File;

public class InterfaceController  extends FXMLController {

    private InterfaceModel interfaceModel;

    /**The name indicating which kind of fxml file is loaded*/
    public final String name;

    private Node fxmlLoaded=null;
    private boolean isFrontInterface;

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
                /*try {
                    this.smallStage.close();
                    //stage = (Stage)((Node)e.getSource()).getScene().getWindow();
                    Scene homeScene = setHome();
                    stage.setScene(homeScene);
                    this.scene = homeScene;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                 */
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
                /*try {
                    openSmallStage(e, "prototypeInterface/view/Help.fxml");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                 */
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
               /* if(! interfaceModel.saveNode(name, fxmlLoaded)){
                    System.out.println("Save "+name+" did not work !!!");
                }
                try {
                    stage = (Stage)((Node)e.getSource()).getScene().getWindow();
                    stage.setScene(setScore());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                */
                if(! interfaceModel.saveNode(name, fxmlLoaded)){
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
                if(currentPoem !=null&& currentSong !=null&&difficultyLevel!=null) {
                    /*try {
                        Game newGame = new Game(poemType, currentSong, difficultyLevel);
                        goToGame(e, newGame);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                     */
                    Game newGame = new Game(currentPoem, currentSong, difficultyLevel);
                    //TODO set exit handler
                    interfaceModel.setNewGame(newGame);
                }
            });
            currentPoem =null;
            currentSong =null;
            difficultyLevel=null;
        }

        if (this.menuButton != null) {
            this.menuButton.setOnAction(e -> {
                /*try {
                    openSmallStage(e, "prototypeInterface/view/Menu.fxml");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
                 */

                Node menuNode = interfaceModel.retrieveSavedNode("Help");
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


        if(this.up!=null) {
            this.up.setOnAction(e -> {
            });
        }

        if(this.down!=null) {
            this.down.setOnAction(e -> {
            });
        }
    }


    private void setSongMenuItem(){
        if(songButton != null){
            for(int i = 0; i< DataHolder.projectDataManager.songList.size(); i++){
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
                Poem poem = DataHolder.projectDataManager.poemList.get(i);
                MenuItem miTmp = new MenuItem(poem.getName());
                miTmp.setOnAction(e -> currentPoem = DataHolder.projectDataManager.mapNameToPoem(miTmp.getText()));
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
                File fileSelection = filechooser.showOpenDialog(stage.getOwner());
                if(fileSelection != null){
                    newPoem = new Poem(fileSelection.getName(), fileSelection.getAbsolutePath());
                    //TODO select the newly created poem
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
                        MenuItem miTmp = new MenuItem(newPoem.getName());
                        miTmp.setOnAction(event -> currentPoem = DataHolder.projectDataManager.mapNameToPoem(miTmp.getText()));
                        this.poemButton.getItems().add(miTmp);
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

    /**
     * Save the fxml file this controller loaded
     * @param node
     */
    public void setFXMLLoaded(Node node){
        this.fxmlLoaded = node;
    }
}
