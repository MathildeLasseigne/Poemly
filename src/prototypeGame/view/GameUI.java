package prototypeGame.view;


import controller.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import model.ProjectDataManager;
import prototypeGame.model.Game;
import prototypeGame.widgets.Karaoke.Karaoke;

import java.io.IOException;
import java.net.URL;

public class GameUI extends Pane {

    private Game game;

    public GameFXMLController gameUINodes;

    public Karaoke karaoke;

    public GameUI(Game game){
        this.game = game;

        gameUINodes = new GameFXMLController(chooseFXML(this.game.getTheme()));
        this.getChildren().add(this.gameUINodes.gamePanel);

        this.karaoke = new Karaoke(this.game.getPoem(), this.game.getDifficulty());

        this.gameUINodes.karaokeContainer.getChildren().add(karaoke);

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
    }

}
