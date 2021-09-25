package prototypeGame.widgets.Karaoke;

import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

/**
 * From https://stackoverflow.com/questions/15081892/javafx-text-multi-word-colorization
 */
public class KaraokeColorizer {




    private String[] wordList = {};

    /**
     * Line of words. Words are in an HBox to prevent warping in the middle of a word
     */
    private ArrayList<HBox> wordOutList = new ArrayList<>();

    /**
     * List of all the lines. Lines are in flowpane to allow warping
     */
    private ArrayList<FlowPane> lineOutList = new ArrayList<>();

    /**
     * The index of the separator within the text
     */
    private int separatorIdx = -1;

    /**
     * Colorize a text with 3 colors. A separator char has a specific color, and separate the 2 other parts of the text
     * <br/> The index of the separator begin at -1, meaning all the text is colored with the 3rd color
     * @param text the text to colorize
     */
    public KaraokeColorizer(ArrayList<String> text){

    }

    public void setSeparatorIdx(int separatorIdx) {
        this.separatorIdx = separatorIdx;
    }

    public int getSeparatorIdx() {
        return separatorIdx;
    }

    /**
     * Return the text rendered. Each row of the arraylist correspond to a line.
     * @return
     */
    public ArrayList<FlowPane> getRenderText() {
        return lineOutList;
    }

    public FlowPane parseInputToArray(String input) {
        wordList = input.trim().split("[ ]+");


        return colorize();
    }

    /**
     * Parse the string into words
     * @param input
     */
    public void parseInputToArrayToWords(String input) {
        wordList = input.trim().split("(?<= )|(?<=\\.)");
    }

    /**
     * Parse the string into words
     * @param word
     */
    public char[] parseWordToCharacterArray(String word) {
        char[] charList = new char[word.length()];

        for(int i = 0; i<word.length(); i++) {
            charList[i] = word.charAt(i);
            // access each character
        }
        //Text text = new Text();
        //text.setText(String.valueOf(charList[0]));
        return charList;
    }

    public FlowPane colorize() {

        ArrayList<Text> textChunks = new ArrayList<>();
        FlowPane bundle = new FlowPane();
            /*
            //Todo: use regex to check for valid words
            for (String word : wordList) {
                String spaced = word + " ";
                switch (word) {
                    case "Hello": case "hello":
                        textChunks.add(customize(spaced, "purple"));
                        break;
                    case "World": case "world":
                        textChunks.add(customize(spaced, "blue"));
                        break;
                    case "Stack Overflow":
                        textChunks.add(customize(spaced, "orange", "Arial Bold", 15));
                    default:
                        textChunks.add(customize(spaced, "black", "Arial",  13));
                        break;
                }
            }

             */

        bundle.getChildren().addAll(textChunks);
        return bundle;
    }

    public Text customize(String word, Color color) {
        Text text = new Text("word");
        text.setFill(color);
        return text;
    }

    public Text customize(String word, Color color, Font font) {
        Text txt = customize(word, color);
        txt.setFont(font);
        return txt;
    }


    public class TextRender {

        private Color color;
        private Font font;

        public TextRender(Color color, Font font){

            this.color = color;
            this.font = font;
        }

        public Text customize(String text){
            Text renderedText = new Text(text);
            if(this.color != null){
                //renderedText.se
            }
            return renderedText;
        }

    }

}
