package prototypeGame.widgets.Karaoke;

import controller.FXMLController;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Difficulty;
import widgets.tools.Utilities;

import java.util.ArrayList;

public class KaraokeController extends FXMLController {

    private Karaoke karaoke;

    private KaraokeColorizer karaokeColorizer;

    private ArrayList<String> poemText;

    private BooleanProperty finished = new SimpleBooleanProperty(false);


    //Preview separator -> Used to predict future character
    private int previewSeparatorIdx = -1;
    private BooleanProperty previewFinished = new SimpleBooleanProperty(false);


    @FXML
    private Label poemName;

    @FXML
    private VBox poemContainer;

    @FXML
    private StackPane poemTitlePane;

    KaraokeController(Karaoke karaoke){
        this.karaoke = karaoke;

        this.poemText = this.karaoke.poem.getText();
        Font globalFont = new Font("Comic Sans MS", 25);

        KaraokeColorizer.TextRender before = new KaraokeColorizer.TextRender(Color.GREEN, globalFont);
        KaraokeColorizer.TextRender separator = new KaraokeColorizer.TextRender(Color.BLUE, globalFont, true);
        KaraokeColorizer.TextRender after = new KaraokeColorizer.TextRender(Color.RED, globalFont);
        this.karaokeColorizer = new KaraokeColorizer(poemText, before, separator, after);


    }


    @FXML
    public void initialize(){
        Utilities.clipChildren(poemContainer, 0); //Prevent poem from going out of the box

        this.poemName.setText(this.karaoke.poem.getName());
        updateKaraoke();//Initialize
    }


    /**
     * Add all text from the poem into poemContainer
     */
    private void updateKaraoke(){

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                int widthParagraph = 285;

                poemContainer.getChildren().clear();


                poemContainer.getChildren().add(poemTitlePane);
                ArrayList<FlowPane> newPoem = karaokeColorizer.getRenderedText();
                for(FlowPane line : newPoem){
                    line.setPrefWidth(widthParagraph); //Set width of new lines
                    line.setMaxWidth(widthParagraph);
                    poemContainer.getChildren().add(line);
                }


            }

        });

    }

    /**
     * Return the current char highlighted.
     * @return
     * @throws Exception Using this methode when the text is finished will throws an error
     */
    public char getCurrentChar() throws Exception {
        if(! isFinished().get()){
            return getCharAt(this.karaokeColorizer.getSeparatorIdx());
        } else {
            throw new Exception("Text finished");
        }
    }

    /**
     * Select the next highlighted char according to the difficulty.
     * Space is treated differently. Do not select space if it is before punctuation
     * <br/>Set the karaoke to finished if necessary
     * <br/>Rules by difficulty :
     * <ul>
     *     <li><b>Easy :</b> Do not take case and punctuation into account. Spaces counts</li>
     *     <li><b>Medium :</b> + Take case into account</li>
     *     <li><b>Hard :</b> + Take punctuation into account</li>
     * </ul>
     */
    public void nextSeparatorChar(){
        if(! isFinished().get()){
            this.karaokeColorizer.setSeparatorIdx(nextChar(this.karaokeColorizer.getSeparatorIdx(), isFinished()));
        }
        updateKaraoke();
    }


    /**
     * Return the current char of the parallel list
     * @return
     * @throws Exception  Using this methode when the text is finished will throws an error
     */
    public char getPreviewChar() throws Exception {
        if(! this.previewFinished.get()){
            return getCharAt(this.previewSeparatorIdx);
        } else {
            throw new Exception("Text finished");
        }
    }

    /**
     * Return the next char of the parallel separator
     */
    public void nextPreviewChar(){
        if(! isPreviewFinished().get()){
            this.previewSeparatorIdx = nextChar(this.previewSeparatorIdx, isPreviewFinished());
        }
    }


    /**
     * Check if the key event is the same as the current separator, depending on difficulty
     * <br/>Rules by difficulty :
     * <ul>
     *     <li><b>Easy :</b> Do not take case and punctuation into account. Spaces counts</li>
     *     <li><b>Medium :</b> + Take case into account</li>
     *     <li><b>Hard :</b> + Take punctuation into account</li>
     * </ul>
     * @param keyEvent
     * @return If the current separator idx is -1 or the text is finished return false
     * @throws Exception  Using this methode when the text is finished will throws an error
     */
    public boolean checkKeyEvent(KeyEvent keyEvent) throws Exception {
        if(this.karaokeColorizer.getSeparatorIdx() == -1 || isFinished().get()){
            return false;
        }
        try{
            char currentChar = getCurrentChar();
            String currentString = String.valueOf(currentChar);
            String keyString = keyEvent.getCharacter();
            if(this.karaoke.difficulty == Difficulty.DifficultyLevel.Easy){
                return currentString.equalsIgnoreCase(keyString);
            } else {
                return currentString.equals(keyString);
            }
        } catch (Exception e) {
            throw e;
        }
    }


    /**
     * Check if the key event is the same as the current separator, depending on difficulty.
     * Must not be called on a finished text. If new separator is at the end of the text, set textFinished at true
     * <br/>Rules by difficulty :
     * <ul>
     *     <li><b>Easy :</b> Do not take case and punctuation into account. Spaces counts</li>
     *     <li><b>Medium :</b> + Take case into account</li>
     *     <li><b>Hard :</b> + Take punctuation into account</li>
     * </ul>
     * @param currentSeparator the separator to increment
     * @param textFinished the boolean defining if the currentSeparator is at the end of the text
     * @return the incremented separator
     */
    private int nextChar(int currentSeparator, BooleanProperty textFinished){

        int newSeparator = currentSeparator;
        boolean notFound = true;
        newSeparator++;

        do {
            char newChar = getCharAt(newSeparator);
            //Space is treated differently. Do not select space if before punctuation
            if(newChar != ' ') {
                if(this.karaoke.difficulty == Difficulty.DifficultyLevel.Hard){
                    notFound = false; //return the newSeparator
                } else { //Do not take punctuation
                    switch (newChar) {
                        case '.': case '!': case '?': case ':': case ';': case ',':  //List of punctuation
                            newSeparator++; //Skip the punctuation
                            break;
                        default: //If 'a' for example
                            notFound = false; //Register the character as the new separator
                            break;
                    }
                }


            } else {//Check if next value is not punctuation. Never last of line because poem trim lines
                char nextChar = getCharAt(newSeparator+1);
                String combination = String.valueOf(newChar)+String.valueOf(nextChar);

                switch (combination) {
                    case " .": case " !": case " ?": case " :": case " ;":  //List of punctuation invalidating the space
                        newSeparator++; //Skip the space
                        break;
                    default: //If " a" for example
                        notFound = false; //Register the space as current separator
                        break;
                }
            }


        } while (notFound);

        if(newSeparator >= this.karaoke.poem.getLenght()-1){
            textFinished.set(true);
        }


        return newSeparator;
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
     * Is the Karaoke finished
     * @return
     */
    public BooleanProperty isFinished() {
        return finished;
    }

    /**
     * Is the preview list finished
     * @return
     */
    public BooleanProperty isPreviewFinished() {
        return previewFinished;
    }

    /**
     * Return the length of the poem (number of valid characters) depending on the difficulty of the karaoke
     * @return the length of the poem
     */
    public int getLengthForDifficulty(){
        BooleanProperty proxyFinished = new SimpleBooleanProperty(false);
        int proxySeparator = -1;
        int length = 0;
        while(! proxyFinished.getValue()){
            proxySeparator = nextChar(proxySeparator, proxyFinished);
            length++;
        }
        return length;

    }
}
