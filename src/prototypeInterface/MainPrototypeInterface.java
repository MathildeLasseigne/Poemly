package prototypeInterface;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.*;
import prototypeInterface.model.InterfaceModel;


public class MainPrototypeInterface extends Application{

    public MainPrototypeInterface(){

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        DataHolder.init();
        DataHolder.scoreManager.addScore(new Score(Song.createEmptySong(), Poem.createEmptyPoem(), Difficulty.DifficultyLevel.Easy, 100));
        DataHolder.scoreManager.addScore(new Score(Song.createEmptySong(), Poem.createEmptyPoem(), Difficulty.DifficultyLevel.Medium, 100));
        DataHolder.scoreManager.addScore(new Score(Song.createEmptySong(), Poem.createEmptyPoem(), Difficulty.DifficultyLevel.Easy, 80));
        DataHolder.scoreManager.addScore(new Score(Song.createEmptySong(), Poem.createEmptyPoem(), Difficulty.DifficultyLevel.Easy, 100));
        DataHolder.scoreManager.addScore(new Score(Song.createEmptySong(), Poem.createEmptyPoem(), Difficulty.DifficultyLevel.Hard, 50));

        InterfaceModel root = new InterfaceModel();
        root.createNewHome();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
