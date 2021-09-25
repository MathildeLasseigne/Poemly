package model;

import widgets.tools.FileListManager;

import java.util.ArrayList;

public class Poem extends Encodable {

    /**The name of the poem**/
    private String name;

    /**The path to load to find the .txt file of the poem*/
    private String path;

    private ArrayList<String> text = new ArrayList<>();


    /**
     * Construct a poem containing its name, path & text
     * @param name
     * @param path
     */
    public Poem(String name, String path){
        super("#Poem#");
        this.name = name;
        setText();
    }

    /**
     *
     * <br/>This constructor decode the data given to it to find its own name & path
     * @param encodedDataLine
     */
    public Poem(String encodedDataLine){
        super("#Poem#");
        decodeDataLine(encodedDataLine);
        setText();
    }

    /**
     * Return an empty poem
     * @return
     */
    public static Poem createEmptyPoem(){
        return new Poem("","");
    }

    /**
     * Delete all empty lines of the text
     * @see Poem#getText()
     */
    public void cleanText(){
        for(int i = 0; i < this.text.size(); i++){
            String str = this.text.get(i);
            if(str == ""){
                this.text.remove(i);
            }
        }
    }

    /**
     * Get the name of the poem
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Get a copy of the text of the poem.
     * One line correspond to 1 item in the ArrayList
     * @return
     */
    public ArrayList<String> getText() {
        return (ArrayList<String>) text.clone();
    }

    /**
     * Set the text from the file from the path
     */
    private void setText(){
        this.text = (ArrayList<String>) FileListManager.readFileInList(this.path);
        cleanText();
    }

    /**
     * Change the path and write the poem into the new file
     * <br/> The name of the file will be the name of the song.
     * @param newDirectoryPath the path toward the directory where the new file will be created
     */
    protected void createNewFile(String newDirectoryPath){
        String path = newDirectoryPath + this.name + ".txt";
        this.path = newDirectoryPath;
        FileListManager.writeFileFromList(path, true, this.getText());
    }

    /*----------Data---------------*/


    /**
     * Check if this dataLine was generated by the generateDataLine methode and thus is decodable
     * @param encodedDataLine the unknown data line
     * @return true if it is decodable else false
     */
    public boolean recogniseDataLine(String encodedDataLine){
        return encodedDataLine.contains(header);
    }

    /**
     * Return the string defining the poem in the ProjectData file
     * <br/> Line format : <b>#Poem#name;path</b>
     * @return
     */
    public String generateDataLine(){
        String data = header + this.name + ";" + this.path;
        return data;
    }

    /**
     * Decode the encoded data to fill the poem data
     * @param encodedDataLine
     */
    protected void decodeDataLine(String encodedDataLine){
        String[] decode = encodedDataLine.replaceAll(header, "").replaceAll("\n", "").split(";");
        this.name = decode[0];
        this.path = decode[1];
    }
}
