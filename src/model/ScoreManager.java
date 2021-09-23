package model;

import widgets.tools.FileListManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreManager {

    private String path;

    private ArrayList<Score> scoreList = new ArrayList<>();

    private ArrayList<Score> newScoreList = new ArrayList<>();


    public ScoreManager(ProjectDataManager projectDataManager){
        try{
            loadPath(projectDataManager);
            loadScores();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Try to load the path to the score file.
     * @param projectDataManager
     * @throws Exception  If path is null or empty
     */
    private void loadPath(ProjectDataManager projectDataManager) throws Exception {
        String path = projectDataManager.getScorePath();
        if(path == "" | path == null){
            throw new Exception("Score file not found");
        }
        this.path = path;
    }

    private void loadScores(){
        List<String> data = FileListManager.readFileInList(this.path);

        for(String dataLine : data){
            this.scoreList.add(new Score(dataLine));
        }
    }

    /**
     * Add a new score to the list
     * @param score
     */
    public void addScore(Score score){
        this.scoreList.add(score);
        this.newScoreList.add(score);

    }

    /**
     * Save the new the data into a specified file
     * <br/> Must be called only once before closing the project
     */
    public void saveScore(){
        ArrayList<String> toSave = new ArrayList<>();
        for(Score s : this.newScoreList){
            toSave.add(s.generateDataLine());
        }

        FileListManager.writeFileFromList(this.path, true, toSave);
    }

    /**
     * Return score with idex 0 being the most recent one
     * @return
     */
    public ArrayList<Score> getScoreList(){
        ArrayList<Score> newList = (ArrayList<Score>)this.scoreList.clone();
        Collections.reverse(newList);
        return  newList;
    }

}
