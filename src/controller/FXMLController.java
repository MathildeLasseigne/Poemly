package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;

/**
 * Abstract class containing methods for FXML controllers
 */
public abstract class FXMLController implements Serializable {

    /**
     * The FXMLLoader used if Controller linked to fxml file with <i>loadFXMLWithController</i>
     * @see FXMLController#loadFXMLWithController(URL)
     */
    protected FXMLLoader fxmlLoader;

    /**
     * Load the fxml with this controller instanciated.
     * <br/>Save the FXMLLoader
     * @param fxmlPath path to fxml file
     * @return the fxml file content as Parent
     * @throws IOException
     * @see FXMLController#loadFXMLWithController(URL, FXMLController)
     * @see FXMLController#fxmlLoader
     */
    public Parent loadFXMLWithController(URL fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(fxmlPath);
        System.out.println("fxml path : " + fxmlPath);
        loader.setController(this);
        Parent fxmlContent = null;
        fxmlContent = loader.load();
        this.fxmlLoader = loader;
        return fxmlContent;
    }

    /**
     * Load the fxml with an instanciated Controller
     * @param fxmlPath path to fxml file
     * @param FXMLController the instanciated Controller
     * @return the fxml file content as Parent
     * @throws IOException
     * @see FXMLController#loadFXMLWithController(URL)
     */
    public static Parent loadFXMLWithController(URL fxmlPath, FXMLController FXMLController) throws IOException {
        FXMLLoader loader = new FXMLLoader(fxmlPath);
        loader.setController(FXMLController);
        Parent fxmlContent = null;
        fxmlContent = loader.load();
        return fxmlContent;
    }
}
