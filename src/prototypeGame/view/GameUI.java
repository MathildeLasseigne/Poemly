package prototypeGame.view;


import controller.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import model.ProjectDataManager;
import prototypeGame.model.Game;
import prototypeGame.widgets.Karaoke.Karaoke;

import java.net.URL;

public class GameUI extends Pane {

    private Game game;

    public GameFXMLController gameUINodes;

    public Karaoke karaoke;

    public GameUI(Game game){
        this.game = game;
    }

    /**
     * Return the path of the fxml to load depending on theme of the game
     * @param theme
     * @return
     */
    public URL chooseFXML(ProjectDataManager.Theme theme){
        URL url;
        String path = ""; //Modify if path change. For now in the same package
        if(theme == ProjectDataManager.Theme.Beach){
            url = getClass().getResource(path+"Beach.fxml");
        } else if(theme == ProjectDataManager.Theme.City){
            url = getClass().getResource(path+"City.fxml");
        } else {
            url = getClass().getResource(path+"School.fxml");
        }
        return url;
    }

    /**
     * Contain all the nodes of the UI
     */
    class GameFXMLController extends FXMLController {



        public GameFXMLController(String fxmlPath){

        }

        @FXML
        public void initialize(){

        }
    }

}
