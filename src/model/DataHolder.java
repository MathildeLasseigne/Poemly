package model;

public class DataHolder {

    public static ProjectDataManager projectDataManager;

    public static ScoreManager scoreManager;

    /**
     * Initialize the dataHolders
     */
    public static void init(){
        projectDataManager = new ProjectDataManager();
        scoreManager = new ScoreManager(projectDataManager);
    }

    /**
     * Save all data to their files. Must be called before closing the project
     */
    public static void saveAllNewData(){
        projectDataManager.saveData();
        scoreManager.saveScore();
    }

}
