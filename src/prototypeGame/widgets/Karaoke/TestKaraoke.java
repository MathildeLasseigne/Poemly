package prototypeGame.widgets.Karaoke;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Difficulty;
import model.Poem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TestKaraoke extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = new AnchorPane();
        root.prefWidth(300);
        root.prefWidth(275);
        primaryStage.setTitle("Hello World");


        //testKeyEvent(scene);

        Karaoke karaoke = setKaraoke();
        //testParse();

        Timer timer = new Timer(300, new ActionListener() {
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




    public Karaoke setKaraoke(){

        //Poem poem = new Poem("Test Poem", "src/assets/tests/LoremIpsum.txt");
        Poem poem = new Poem("Test Poem", "C:\\Users\\mathilde\\Documents\\Cours\\M1HCI\\Adv programmation of ISO\\Project\\Poemly\\src\\assets\\tests\\testReader.txt");
        return new Karaoke(poem, Difficulty.DifficultyLevel.Hard);

    }

    static void testKeyEvent(Scene node){
        /*node.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println(event.getCharacter());

            }
        });

         */
        node.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println(event.getCharacter());

            }
        });
    }

    static void testParse(){
        String s = " Hello, my name is Blanc! I'm a student. " +
                "I am new here, so please help me adapt. Can you help me? Thanks." ;

        String[] out = KaraokeColorizer.parseInputToWordsArray(s);
        for(int i = 0; i< out.length; i++){
            System.out.print(out[i]+ ";");
        }

        System.out.println("\nSecond test");

        String[] words = s.split("\\s+");
        for(int i = 0; i< words.length; i++){
            System.out.print(words[i]+ ";");
        }

        System.out.println("\nThird test");

        String[] words2 = s.trim().split("[ ]+");
        for(int i = 0; i< words2.length; i++){
            System.out.print(words2[i]+ ";");
        }
    }
}
