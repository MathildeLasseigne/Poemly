package Demo;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import prototypeGame.widgets.Karaoke.Karaoke;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DemoMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = new AnchorPane();
        root.prefWidth(300);
        root.prefWidth(275);
        primaryStage.setTitle("Karaoke Demo");


        //Création de la cuisine
        javafx.scene.control.Dialog<String> dialog = new Dialog<String>();
        SelectionPoemDemoController dialogCtrl = new SelectionPoemDemoController(primaryStage);
        Node selectionPane = dialogCtrl.loadFXMLWithController(getClass().getResource("SelectionPoemDemo.fxml"));
        //Setting the title
        dialog.setTitle("Choose Poem");
        ButtonType type = new ButtonType("Apply", ButtonBar.ButtonData.OK_DONE);
        //Setting the content of the dialog
        dialog.setDialogPane((DialogPane) selectionPane);
        //Adding buttons to the dialog pane
        dialog.getDialogPane().getButtonTypes().add(type);

        //testKeyEvent(scene);


        dialog.showAndWait();
        Karaoke karaoke = dialogCtrl.loadSession();
        //testParse();

        Timer timer = new Timer(dialogCtrl.getDelay(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*if(! karaoke.isFinished().get()){
                    karaoke.next();
                } else {
                    System.out.println("Finished");
                }

                 */
                karaoke.next();
            }
        });
        timer.start();

        Scene scene = new Scene(karaoke, 1000, 650);

        primaryStage.setScene(scene);

        primaryStage.show();

    }

    public static void main(String[] args){

        launch(args);


    }
}
