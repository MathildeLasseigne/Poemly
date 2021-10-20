package prototypeGame.view;


import controller.FXMLController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import model.Audio;
import model.DataHolder;
import model.ProjectDataManager;
import prototypeGame.model.Game;
import prototypeGame.widgets.Karaoke.Karaoke;
import widgets.SoundPlayer;

import java.io.IOException;
import java.net.URL;

public class GameUI extends Pane {

    private Game game;

    public GameFXMLController gameUINodes;

    public ScoreUIController scoreUI;

    public Karaoke karaoke;

    public GameUI(Game game){
        this.game = game;

        gameUINodes = new GameFXMLController(chooseFXML(this.game.getTheme()));
        this.getChildren().add(this.gameUINodes.gamePanel);

        this.karaoke = new Karaoke(this.game.getPoem(), this.game.getDifficulty());

        this.gameUINodes.karaokeContainer.getChildren().add(karaoke);

        this.scoreUI = new ScoreUIController();

    }

    /**
     * Return the path of the fxml to load depending on theme of the game
     * @param theme
     * @return
     */
    private URL chooseFXML(ProjectDataManager.Theme theme){
        URL url;
        String path = ""; //Modify if path change. For now in the same package
        if(theme == ProjectDataManager.Theme.Beach){
            url = getClass().getResource(path+"Beach.fxml");
        } else if(theme == ProjectDataManager.Theme.City){
            url = getClass().getResource(path+"City.fxml");
        } else {
            url = getClass().getResource(path+"School.fxml");
        }
        if(url == null){
            System.out.println("URL gameUI null");
        }
        return url;
    }

    /**
     * Show the score obtained in the panel.
     * Blur the background pane
     * @param score
     */
    public void showScorePanel(double score){
        this.scoreUI.score.setText(String.valueOf(Math.round(score)));
        Pane gamePanel = (Pane) this.gameUINodes.gamePanel.getChildren().get(0);
        gamePanel.setEffect(new BoxBlur());
        this.gameUINodes.gamePanel.getChildren().add(this.scoreUI.container);
        DataHolder.scoreManager.addScore(this.game.score);

    }

    /**
     * Set the exit handlers on the buttons
     * @param e
     */
    public void setExitHandlers(EventHandler e){
        //Close the game before coming back
        EventHandler newEvent = event -> {
            game.getGameModel().closeGame();
            SoundPlayer song = this.game.getSong().getLoopingSoundPlayer();
            if(song != null) {
                song.stop();
            }
            Audio.buttonSound.play();

            e.handle(event);
        };

        this.scoreUI.home.setOnAction(newEvent);
        this.scoreUI.home.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue)  this.game.requestFocus();
        });

        this.gameUINodes.home.setOnAction(newEvent);
        this.gameUINodes.home.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue)  this.game.requestFocus();
        });
    }

    /**
     * Contain all the nodes of the UI
     */
    public class GameFXMLController extends FXMLController {

        /**The parent of all the nodes of the gameUI*/
        private StackPane gamePanel;

        @FXML
        private Pane karaokeContainer;

        @FXML
        private Pane board;

        @FXML
        private Pane bar;

        @FXML
        private ProgressBar scoreBar;

        @FXML
        private Button home;

        public GameFXMLController(URL fxmlPath){
            Parent gamePanel = null;
            try {
                gamePanel = this.loadFXMLWithController(fxmlPath);
                this.gamePanel = (StackPane) gamePanel;
            } catch (IOException e) {
                System.out.println("\n Game UI not loaded");
                e.printStackTrace();
            }
        }

        @FXML
        public void initialize(){

        }

        public StackPane getGamePanel() {
            return gamePanel;
        }

        public Pane getKaraokeContainer() {
            return karaokeContainer;
        }

        public Pane getBoard() {
            return board;
        }

        public Pane getBar() {
            return bar;
        }

        public ProgressBar getScoreBar() {
            return scoreBar;
        }
    }


    public class ScoreUIController extends FXMLController{

        public Node container;

        @FXML
        public Text score;

        @FXML
        public Button home;

        public ScoreUIController(){
            Parent gamePanel = null;
            try {
                gamePanel = this.loadFXMLWithController(getClass().getResource("StopGame.fxml"));
                this.container = gamePanel;
            } catch (IOException e) {
                System.out.println("\n Score UI not loaded");
                e.printStackTrace();
            }
        }

        @FXML
        public void initialize(){

        }

    }
}
