package Demo;

import controller.FXMLController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import model.Difficulty;
import model.Poem;
import prototypeGame.widgets.Karaoke.Karaoke;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class SelectionPoemDemoController extends FXMLController {

    private Window owner;


    @FXML
    private Label filePath;

    @FXML
    private Button searchFile;

    @FXML
    private TextField poemName;

    @FXML
    private TextField delay;

    @FXML
    private ChoiceBox<Difficulty.DifficultyLevel> difficultyChoice;


    private File fileSelection = null;


    private StringProperty filePathProperty = new SimpleStringProperty("Select/a/file");


    /**
     * Cree le controlleur de la fenetre de selection
     * @param owner souvent primaryStage
     */
    public SelectionPoemDemoController(Window owner){
        this.owner = owner;
    }

    @FXML
    public void initialize(){
        this.delay.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        this.filePath.textProperty().bind(this.filePathProperty);
        searchFile.setOnAction((actionEvent)->{
            try {
                searchFileAction();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        this.difficultyChoice.getItems().addAll(Difficulty.DifficultyLevel.Easy, Difficulty.DifficultyLevel.Medium, Difficulty.DifficultyLevel.Hard);
        this.difficultyChoice.getSelectionModel().select(0);
    }


    public String getPoemName(){
        return this.poemName.getText();
    }

    public int getDelay(){
        return Integer.valueOf(this.delay.getText()) * 1000;
    }

    public Difficulty.DifficultyLevel getDifficulty(){
        return this.difficultyChoice.getValue();
    }


    /**
     * Cree une session dans data en fonction de la maniere dont la cuisine a ete creee
     */
    public Karaoke loadSession(){
        Poem poem = Poem.createEmptyPoem();
        if(this.fileSelection == null){
           poem = new Poem("Test Poem", "src/assets/tests/LoremIpsum.txt");
            return new Karaoke(poem, getDifficulty());
        } else {
            System.out.println("Selectionner un fichier");
            poem = new Poem(getPoemName(), this.filePathProperty.get());
            return new Karaoke(poem, getDifficulty());
        }
    }

    /**
     * L action du button search File
     */
    public void searchFileAction() throws IOException {
        cancelFileAction();
        FileChooser filechooser = new FileChooser();
        filechooser.setTitle("Choose a poem");
        filechooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        this.fileSelection = filechooser.showOpenDialog(owner);
        if(this.fileSelection != null){
            this.filePath.setTextFill(Color.BLACK);
            this.filePathProperty.set(this.fileSelection.getPath());//.getCanonicalPath());
            System.out.println(this.fileSelection.getPath());
        } else {
            this.filePath.setTextFill(Color.RED);
            this.filePathProperty.set("This file couldn't be found");
        }
    }

    /**
     * L action d annuler la selection de file
     */
    public void cancelFileAction(){
        this.filePath.setTextFill(Color.BLACK);
        this.fileSelection = null;
        this.filePathProperty.set("Select/a/file");
    }


    private TextFormatter<String> getIntFormater(){
        UnaryOperator<TextFormatter.Change> intFilter = change -> {
            String text = change.getText();
            if (text.matches("[0-9]*")) {
                return change;
            }
            return null;
        };
        return new TextFormatter<String>(intFilter);
    }


    /**
     * Cree un nouveau text formatter ne prenant que des doubles.
     * <br/>Un textformatter ne peut etre utilise qu une seule fois
     * <br/><a href= "https://stackoverflow.com/questions/45977390/how-to-force-a-double-input-in-a-textfield-in-javafx">Source</a>
     * @return
     */
    private TextFormatter<Double> getDoubleFormater(){
        Pattern validEditingState = Pattern.compile("-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?");

        UnaryOperator<TextFormatter.Change> filter = c -> {
            String text = c.getControlNewText();
            if (validEditingState.matcher(text).matches()) {
                return c ;
            } else {
                return null ;
            }
        };

        StringConverter<Double> converter = new StringConverter<Double>() {

            @Override
            public Double fromString(String s) {
                if (s.isEmpty() || "-".equals(s) || ".".equals(s) || "-.".equals(s)) {
                    return 0.0 ;
                } else {
                    return Double.valueOf(s);
                }
            }


            @Override
            public String toString(Double d) {
                return d.toString();
            }
        };

        return new TextFormatter<>(converter, 0.0, filter);
    }
}
