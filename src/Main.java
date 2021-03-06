import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.*;
import prototypeInterface.model.InterfaceModel;

public class Main  extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        DataHolder.init();
        Audio.load();
        //manuallyAddAssets();



        InterfaceModel root = new InterfaceModel();
        root.createNewHome();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }

    /**
     * For test purpose
     */
    void manuallyAddAssets(){

        DataHolder.scoreManager.addScore(new Score(Song.createEmptySong(), Poem.createEmptyPoem(), Difficulty.DifficultyLevel.Easy, 100));
        DataHolder.scoreManager.addScore(new Score(Song.createEmptySong(), Poem.createEmptyPoem(), Difficulty.DifficultyLevel.Medium, 100));
        DataHolder.scoreManager.addScore(new Score(Song.createEmptySong(), Poem.createEmptyPoem(), Difficulty.DifficultyLevel.Easy, 80));
        DataHolder.scoreManager.addScore(new Score(Song.createEmptySong(), Poem.createEmptyPoem(), Difficulty.DifficultyLevel.Easy, 100));
        DataHolder.scoreManager.addScore(new Score(Song.createEmptySong(), Poem.createEmptyPoem(), Difficulty.DifficultyLevel.Hard, 50));

        DataHolder.projectDataManager.poemList.add(new Poem("The Fat Cat", "src/assets/poems/The_Fat_Cat.txt"));
        DataHolder.projectDataManager.poemList.add(new Poem("A Little Seed", "src/assets/poems/A_Little_Seed.txt"));
        DataHolder.projectDataManager.poemList.add(new Poem("I look in the mirror", "src/assets/poems/I_look_in_the_mirror.txt"));

    }

    public static void main(String[] args){
        launch(args);
    }
}
