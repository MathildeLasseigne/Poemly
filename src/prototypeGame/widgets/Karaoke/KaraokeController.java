package prototypeGame.widgets.Karaoke;

import controller.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import widgets.tools.Utilities;

import java.util.ArrayList;

public class KaraokeController extends FXMLController {

    private Karaoke karaoke;

    private KaraokeColorizer karaokeColorizer;

    private ArrayList<String> poemText;

    private ArrayList<FlowPane> currentRenderedPoemLines;

    private boolean finished = false;


    @FXML
    private Label poemName;

    @FXML
    private VBox poemContainer;

    KaraokeController(Karaoke karaoke){
        this.karaoke = karaoke;

        this.poemText = this.karaoke.poem.getText();
        KaraokeColorizer.TextRender before = new KaraokeColorizer.TextRender(Color.DARKRED);
        KaraokeColorizer.TextRender separator = new KaraokeColorizer.TextRender(Color.DARKBLUE);
        KaraokeColorizer.TextRender after = new KaraokeColorizer.TextRender(Color.DARKGREEN);
        this.karaokeColorizer = new KaraokeColorizer(poemText, before, separator, after);


    }


    @FXML
    public void initialize(){
        Utilities.clipChildren(poemContainer, 0); //Prevent poem from going out of the box

        this.poemName.setText(this.karaoke.poem.getName());
        updateKaroke();//Initialize
    }


    /**
     * Return the current char highlighted
     * @return
     */
    public char getCurrentChar(){

        return 0;
    }

    /**
     * Select the next highlighted char according to the difficulty.
     * <br/>Set the karaoke to finished if necessary
     */
    public void nextChar(){
        //TODO
        if(! isFinished()){

        }
        updateKaroke();
    }

    /**
     * Return the char at position idx within the poem
     * @param idx
     * @return
     */
    private char getCharAt(int idx){
        int currentIdx = 0;
        for(String line : this.poemText){
            char[] chars = KaraokeColorizer.parseInputToCharacterArray(line);
            for(int i = 0; i < chars.length; i++){
                if(currentIdx == idx){
                    return chars[i];
                }
                currentIdx++;
            }
        }
        return ' ';
    }


    /**
     * Add all text from the poem into poemContainer
     */
    void updateKaroke(){
        int widthParagraph = 285;
        //remove old lines from VBOX
        if(this.currentRenderedPoemLines != null){
            for(FlowPane fp : currentRenderedPoemLines){
                this.poemContainer.getChildren().remove(fp);
            }
        }
        ArrayList<FlowPane> newPoem = karaokeColorizer.getRenderedText();
        for(FlowPane line : newPoem){
            line.setPrefWidth(widthParagraph); //Set widt of new lines
            line.setMaxWidth(widthParagraph);
            this.poemContainer.getChildren().add(line);
        }
        this.currentRenderedPoemLines = newPoem; //Register new lines
    }

    /**
     * Is the Karaoke finished
     * @return
     */
    public boolean isFinished() {
        return finished;
    }
}
