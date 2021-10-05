package prototypeGame.widgets.Karaoke;

import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

/**
 * Colorize a text with 3 colors. A separator char has a specific color, and separate the 2 other parts of the text
 * From https://stackoverflow.com/questions/15081892/javafx-text-multi-word-colorization
 */
public class KaraokeColorizer {

    /**The text to colorize*/
    private final ArrayList<String> text;


    /**
     * List of all the rendered lines. Lines are in FlowPane to allow warping
     */
    private ArrayList<FlowPane> lineOutList = new ArrayList<>();

    /**
     * The index of the separator within the text
     */
    private int separatorIdx = -1;


    private final TextRender renderBefore, renderSeparator, renderAfter;

    /**
     * Colorize a text with 3 colors. A separator char has a specific color, and separate the 2 other parts of the text
     * <br/> The index of the separator begin at -1, meaning all the text is colored with the 3rd color
     * @param text the text to colorize
     * @param renderBefore render before the separator
     * @param renderSeparator render of the separator
     * @param renderAfter render after the separator (if separator not initialized, will be the render for the whole text)
     */
    public KaraokeColorizer(ArrayList<String> text, TextRender renderBefore, TextRender renderSeparator, TextRender renderAfter){

        this.text = text;
        this.renderBefore = renderBefore;
        this.renderSeparator = renderSeparator;
        this.renderAfter = renderAfter;
    }

    public void setSeparatorIdx(int separatorIdx) {
        this.separatorIdx = separatorIdx;
    }

    public int getSeparatorIdx() {
        return separatorIdx;
    }

    /**
     * Return the text rendered. Each row of the arraylist correspond to a line.
     * @return all the rendered text in FlowPanes
     */
    public ArrayList<FlowPane> getRenderedText() {
        colorize();
        return lineOutList;
    }



    /**
     * Parse the string (the line) into words. All spaces and dots are kept but the String is trimmed
     * <br/> The following strings
     * <ul>
     *     <li>"Hello, my name is Blanc! I'm a student. Can you help me? Thanks."</li>
     *     <li>"      Hello, my name is Blanc! I'm a student. Can you help me? Thanks.     "</li>
     * </ul>
     *  will be parsed the same way :
     * <br/> "|Hello, |my |name |is |Blanc!| |I'm |a |student.| |Can |you |help |me?| |Thanks.|"
     * @param input the string to parse
     * @return the words of the input as an array
     * @see String#split(String)
     * @see String#trim()
     */
    public static String[] parseInputToWordsArray(String input) {
        return input.trim().split("(?<= )|(?<=\\.)|(?<=\\?)|(?<=!)");
    }

    /**
     * Parse the string into characters
     * @param word the word to extract
     * @return the characters of the word as a char array
     * @see String#charAt(int)
     */
    public static char[] parseInputToCharacterArray(String word) {
        char[] charList = new char[word.length()];

        for(int i = 0; i<word.length(); i++) {
            charList[i] = word.charAt(i);
            // access each character
        }
        return charList;
    }

    /**
     * Compute the render for each character output it into the lineOutList var
     * @see KaraokeColorizer#lineOutList
     */
    public void colorize() {

        this.lineOutList.clear();

        int currentIdx = 0; //To check the separator
        for(String str : this.text){

            String[] words = parseInputToWordsArray(str);
            FlowPane line = new FlowPane(); //Line box
            for(int i = 0; i< words.length; i++){
                char[] character = parseInputToCharacterArray(words[i]);
                HBox word = new HBox(); //Word box. Words are in an HBox to prevent warping in the middle of a word

                for(int j = 0; j< character.length; j++){
                    Text c = null;
                    if(currentIdx < this.separatorIdx){
                        c = this.renderBefore.customize(String.valueOf(character[j]));
                    } else if(currentIdx == this.separatorIdx){
                        c = this.renderSeparator.customize(String.valueOf(character[j]));
                    } else if(currentIdx > this.separatorIdx){
                        c = this.renderAfter.customize(String.valueOf(character[j]));
                    }

                    word.getChildren().add(c);
                    currentIdx++;
                }
                line.getChildren().add(word);

            }
            this.lineOutList.add(line);
        }

    }


    /**
     * Render the text depending on the specification.
     * <br/> Use this class to render multiple texts the same way
     */
    public static class TextRender {

        private final Color color;
        private final Font font;

        private final boolean underline;

        public TextRender(Color color, Font font, boolean underline){

            this.color = color;
            this.font = font;
            this.underline = underline;
        }

        public TextRender(Color color, Font font){
            this(color, font, false);
        }

        public TextRender(Color color){
            this(color, null, false);
        }

        public TextRender(Color color, boolean underline){
            this(color, null, underline);
        }

        public TextRender(Font font){
            this(null, font, false);
        }

        public TextRender(Font font, boolean underline){
            this(null, font, underline);
        }

        /**
         * Add this TextRenderer properties to the given text
         * @param text a Text to customize
         */
        public void customize(Text text){
            Text renderedText = text;
            if(this.color != null){
                renderedText.setFill(this.color);
            }
            if(this.font != null){
                renderedText.setFont(this.font);
            }
            renderedText.setUnderline(this.underline);

        }

        /**
         * Create a Text from the given String and add it this TextRenderer properties
         * @param text the text property of the return Text
         * @return a Text object with the parameter text as text property, customized
         */
        public Text customize(String text){
            Text renderedText = new Text(text);
            customize(renderedText);
            return renderedText;
        }

    }

}
