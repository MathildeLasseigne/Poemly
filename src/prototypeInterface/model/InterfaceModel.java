package prototypeInterface.model;

import controller.Game;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import prototypeInterface.controller.InterfaceController;

import java.io.IOException;
import java.util.HashMap;

public class InterfaceModel extends StackPane{

    public StackPane mainWindows;

    public Pane frontInterface;

    public Pane backInterface;

    private HashMap<String, Node> savedNode = new HashMap<>();

    /**
     * This class is used to manage the display of the differents intefaces
     */
    public InterfaceModel(){
        this.mainWindows = this;
    }

    /**
     * Show the inteface given in front
     * @param smallInterface if null, remove the interface showed
     */
    public void setFrontInterface(Node smallInterface){
        if(smallInterface == null){
            //Remove the front interface
            if(this.frontInterface != null){
                this.mainWindows.getChildren().remove(this.frontInterface);
                frontInterface = null;
            }
        } else {
            //Add the interface given in front
            if(this.frontInterface != null){
                setFrontInterface(null);
            } else {
                this.frontInterface = (Pane) smallInterface;
                this.mainWindows.getChildren().add(this.frontInterface);
                this.frontInterface.toFront();

            }
        }
    }

    /**
     * Set the back interface. Can't be null. It is recommended to remove font interface before.
     * @param backInterface
     */
    public void setBackInterface(Node backInterface){
        if(backInterface != null){
            if(this.backInterface == null){// When initialising the project
                this.backInterface = (Pane) backInterface;
                this.mainWindows.getChildren().add(backInterface);
            } else {
                this.mainWindows.getChildren().remove(this.backInterface);
                this.backInterface = (Pane) backInterface;
                this.mainWindows.getChildren().add(backInterface);
                this.backInterface.toBack();
            }
        }
    }

    public void setNewGame(Game game){
        clearSavedNodes();
        setBackInterface(game);
        //TODO game.start();
    }

    /**
     * Create a new home and set it in place
     */
    public void createNewHome(){
        InterfaceController controller = new InterfaceController(this, "Home", false);

        Node homeContainer = null;
        try {
            homeContainer = controller.loadFXMLWithController(getClass().getClassLoader().getResource("prototypeInterface/view/Home.fxml"));
            setFrontInterface(null);
            setBackInterface(homeContainer);
            controller.setFXMLLoaded(homeContainer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void createNewMenu(){
        InterfaceController controller = new InterfaceController(this, "Menu", true);

        Node menuContainer = null;
        try {
            menuContainer = controller.loadFXMLWithController(getClass().getClassLoader().getResource("prototypeInterface/view/Menu.fxml"));
            setFrontInterface(menuContainer);
            controller.setFXMLLoaded(menuContainer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void createNewScore(){
        InterfaceController controller = new InterfaceController(this, "Score", false);

        Node scoreContainer = null;
        try {
            scoreContainer = controller.loadFXMLWithController(getClass().getClassLoader().getResource("prototypeInterface/view/Score.fxml"));
            setFrontInterface(null);
            setBackInterface(scoreContainer);
            controller.setFXMLLoaded(scoreContainer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Create a new home and set it in place
     */
    public void createNewHelp(){
        InterfaceController controller = new InterfaceController(this, "Help", true);

        Node helpContainer = null;
        try {
            helpContainer = controller.loadFXMLWithController(getClass().getClassLoader().getResource("prototypeInterface/view/Help.fxml"));
            setFrontInterface(helpContainer);
            controller.setFXMLLoaded(helpContainer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save a node to be used later
     * @param nodeName the name used to retrieve the node. For security issue
     * @param node the node to save
     * @return true if the node was added successfully, false if the name of the node is already in use
     */
    public boolean saveNode(String nodeName, Node node){
        if(this.savedNode.containsKey(nodeName)){
            return false;
        }
        this.savedNode.put(nodeName, node);
        return true;
    }

    /**
     * Retrieve a node saved if the name correspond
     * @param nodeName the nane the node was saved with
     * @return null if no node of this name was saved
     */
    public Node retrieveSavedNode(String nodeName){
        Node tmp = this.savedNode.get(nodeName);
        if(tmp != null){
            this.savedNode.remove(nodeName);
        }
        return tmp;
    }

    public void clearSavedNodes(){
        this.savedNode.clear();
    }
}
