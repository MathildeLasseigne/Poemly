package prototypeGame.widgets.Karaoke;

import controller.FXMLController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Difficulty;
import widgets.tools.Utilities;

import java.util.ArrayList;

public class KaraokeController extends FXMLController {

    private Karaoke karaoke;

    private KaraokeColorizer karaokeColorizer;

    private ArrayList<String> poemText;

    private ArrayList<FlowPane> currentRenderedPoemLines; //To be able to remove old ones from the VBox

    private BooleanProperty finished = new SimpleBooleanProperty(false);


    //Preview separator -> Used to predict future character
    private int previewSeparatorIdx = -1;
    private BooleanProperty previewFinished = new SimpleBooleanProperty(false);


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
     * Add all text from the poem into poemContainer
     */
    private void updateKaroke(){
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
        updateKaroke();
    }


    /**
     * Return the current char of the parallele list
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
     * Return the next char of the parallele separator
     */
    public void nextPreviewChar(){
        if(! isPreviewFinished().get()){
            this.karaokeColorizer.setSeparatorIdx(nextChar(this.karaokeColorizer.getSeparatorIdx(), isPreviewFinished()));
        }
        updateKaroke();
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
                return currentString == keyString;
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
        //if(this.karaoke.difficulty == Difficulty.DifficultyLevel.Hard)
        newSeparator++;
        char newChar = getCharAt(newSeparator);

        do {
            //Space is treated differently. Do not select space if before punctuation
            if(newChar != ' ') {
                if(this.karaoke.difficulty == Difficulty.DifficultyLevel.Hard){
                    notFound = false; //return the newSeparator
                } else { //Do not take punctuation
                    switch (newChar) {
                        case '.': case '!': case '?': case ':': case ';':  //List of punctuation
                            newSeparator++; //Skip the punctuation
                            break;
                        default: //If 'a' for exemple
                            notFound = false; //Register the character as the new separator
                            break;
                    }
                }


            } else {//Check if next value is not punctuation. Never last of line because poem trim lines
                char nextChar = getCharAt(newSeparator+1);
                String combinaison = String.valueOf(newChar)+String.valueOf(nextChar);

                switch (combinaison) {
                    case " .": case " !": case " ?": case " :": case " ;":  //List of punctuation invalidating the space
                        newSeparator++; //Skip the space
                        break;
                    default: //If " a" for exemple
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
}
