package model;

import widgets.SoundPlayer;
import widgets.tools.FileListManager;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Data file must be of these format:
 * <ul>
 *     <li>#Score#pathToScore</li>
 *     <li>#PoemDirectory#pathToPoemDirectory</li>
 *     <li>#Poem#name;path</li>
 *     <li>#Song#name;path</li>
 * </ul>
 *
 *
 */
public class ProjectDataManager {


    public enum Theme {School, Beach, City}

    public ArrayList<Song> songList = new ArrayList<>();

    public ArrayList<Poem> poemList = new ArrayList<>();

    /**The list of poems not yet saved in the data*/
    private ArrayList<Poem> newPoemsList = new ArrayList<>();

    final private String projectDataPath = "";

    String poemsDirectoryPath = null;

    private String scorePath = null;


    /**
     * Load all the data into the ProjectDataManager
     */
    public ProjectDataManager(){
        loadData();
    }

    /**
     * Load all the data into the class from the dataFile
     */
    private void loadData(){
        List<String> data = FileListManager.readFileInList(this.projectDataPath);

        for(String dataLine : data){

            if(dataLine.contains("#PoemDirectory#")){
                this.poemsDirectoryPath = dataLine.replaceAll("#PoemDirectory#", ""); //Remove the line header to keep the data
            } else if(dataLine.contains("#Score#")){
                this.scorePath = dataLine.replaceAll("#Score#", ""); //Remove the line header to keep the data
            } else if(Poem.createEmptyPoem().recogniseDataLine(dataLine)){
                this.poemList.add(new Poem(dataLine));
            } else if (Song.createEmptySong().recogniseDataLine(dataLine)){
                this.songList.add(new Song(dataLine));
            }
        }

    }

    /**
     * Save the new the data into a specified file
     * <br/> Must be called only once before closing the project
     */
    public void saveData(){
        ArrayList<String> toSave = new ArrayList<>();
        for(Poem p : this.newPoemsList){
            toSave.add(p.generateDataLine());
        }

        FileListManager.writeFileFromList(this.projectDataPath, true, toSave);
    }


    /**
     * Add a new poem to the base
     * @param poem
     * @return  true if the poem was added successfully else false
     * @throws Exception if the poem was not added. Consult exception message to know why
     */
    public boolean addNewPoem(Poem poem) throws Exception {
        if(new PoemChecker().checkConditions(poem)){
            this.poemList.add(poem);
            this.newPoemsList.add(poem);
            poem.createNewFile(this.poemsDirectoryPath);
            return true;
        }
        return false;
    }

    /**
     * Check if the song name given is not already in use
     * @param songName
     * @return true if the song name is not used, else false
     */
    public boolean isSongNameAvailable(String songName){
        for (Song s : songList) {
            if(songName == s.getName()){
                return false;
            }
        }
        return true;
    }

    /**
     * Check if the poem name given is not already in use
     * @param poemName
     * @return true if the poem name is not used, else false
     */
    public boolean isPoemNameAvailable(String poemName){
        for (Poem p : poemList) {
            if(poemName == p.getName()){
                return false;
            }
        }
        return true;
    }


    /**
     * Return the path to the file saving the score
     * @return
     */
    public String getScorePath() {
        return scorePath;
    }

    class PoemChecker{

        public boolean checkConditions(Poem poem) throws Exception {
            if(! isPoemNameAvailable(poem.getName())){
                throw new Exception("Poem name not available");
            }
            return true;
        }

    }

}
