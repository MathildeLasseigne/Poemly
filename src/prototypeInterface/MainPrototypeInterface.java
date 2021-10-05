package prototypeInterface;

import com.sun.tools.javac.Main;
import controller.Game;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Difficulty;
import model.Poem;
import model.Song;
import prototypeInterface.controller.Controller;
import view.GameUI;


public class MainPrototypeInterface extends Application{

    public MainPrototypeInterface(){

    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader= new FXMLLoader(getClass().getClassLoader().getResource("prototypeInterface/view/Menu.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }



    public static void main(String[] args){
        launch(args);
    }
}
