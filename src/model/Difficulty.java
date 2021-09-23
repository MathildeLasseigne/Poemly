package model;

public class Difficulty {

    public static enum DifficultyLevel{Easy, Medium, Hard}


    public static DifficultyLevel fromString(String difficulty){
        DifficultyLevel difficultyLevel;
            switch (difficulty) {
                case "Easy":
                    difficultyLevel = DifficultyLevel.Easy;
                    break;
                case "Medium":
                    difficultyLevel = DifficultyLevel.Medium;
                    break;
                case "Hard":
                    difficultyLevel = DifficultyLevel.Hard;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + difficulty);
            }
            return difficultyLevel;
    }

    public static String toString(DifficultyLevel difficultyLevel){
        String difficulty = "";
        switch (difficultyLevel) {
            case Easy:
                difficulty = "Easy";
            break;
            case Medium:
                difficulty = "Medium";
                break;
            case Hard:
                difficulty = "Hard";
        }
        return difficulty;
    }
}
