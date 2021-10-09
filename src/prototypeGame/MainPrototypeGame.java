package prototypeGame;

import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Difficulty;
import model.Poem;
import model.Song;
import prototypeGame.model.Game;
import prototypeGame.model.Tile;
import prototypeGame.widgets.Karaoke.Karaoke;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPrototypeGame  extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Parent root = new AnchorPane();
       // root.prefWidth(300);
        //root.prefWidth(275);
        primaryStage.setTitle("Test prototype");


        Node test;

        //test = testTiles();

        test = testGame();



        Scene scene = new Scene((Parent) test, 1000, 650);

        primaryStage.setScene(scene);
        primaryStage.show();

        Game g = (Game) test;
        g.setExitHandler( e -> System.exit(0));
        g.start();


    }



    Node testGame(){
        Poem poem = new Poem("Test Poem", "C:\\Users\\mathilde\\Documents\\Cours\\M1HCI\\Adv programmation of ISO\\Project\\Poemly\\src\\assets\\tests\\testReader.txt");
        Song song = Song.createEmptySong();

        Game game = new Game(poem, song, Difficulty.DifficultyLevel.Hard);


        return game;
    }


    Node testTiles(){

        Tile t = new Tile(100,100,'c');

        /*t.getTranslateTransition().setByY(200);
        t.getTranslateTransition().setDuration(Duration.seconds(5));
        t.getTranslateTransition().play();

         */

        /*Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                t.moveDown();
            }
        });
        timer.start();

         */

        /*AnimationTimer timer = new AnimationTimer() {

            long lastUpdate = 0;

            @Override
            public void handle(long arg0) {
                if(arg0 - lastUpdate >= 95000000) {
                    update();
                    lastUpdate = arg0;
                }
            }

            public void update() {
                t.moveDown();
            }

        };
        timer.start();

         */



        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300), event -> {

            t.moveDown();
            System.out.println("New timeline call");

        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        //timeline.setAutoReverse(false);
        timeline.setRate(1);
        timeline.play();




/*
        TranslateTransition tt = new TranslateTransition(Duration.seconds(5), t);
        tt.setByY(600);
        tt.play();

 */

        return t;
    }


    public static void main(String[] args){
        launch(args);
    }
}
