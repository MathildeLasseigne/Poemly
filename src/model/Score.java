package model;

public class Score extends Encodable {

    private String songName;

    private String poemName;

    private Difficulty.DifficultyLevel difficulty;

    private double score = 0;

    /**
     * An instance of score
     * @param song
     * @param poem
     * @param difficulty
     * @param score in %
     */
    public Score(Song song, Poem poem, Difficulty.DifficultyLevel difficulty, double score){
        songName = song.getName();
        poemName = poem.getName();

        this.difficulty = difficulty;

        this.score = score;
    }


    public Score(String encodedScoreDataLine){
        decodeDataLine(encodedScoreDataLine);
    }


    public String getSong(){
        return songName;
    }

    public String getPoem(){
        return poemName;
    }

    public Difficulty.DifficultyLevel getDifficulty() {
        return difficulty;
    }

    public double getScore() {
        return score;
    }

    @Override
    public boolean recogniseDataLine(String encodedDataLine) {
        return false;
    }

    /**
     * Return the string defining the score in the ScoreData file
     * <br/> Line format : <b>songName;poemName;difficulty;score</b>
     * @return
     */
    @Override
    public String generateDataLine() {
        String data = this.songName + ";" + this.poemName + ";" + Difficulty.toString(this.difficulty) + ";" + this.score;
        return data;
    }

    @Override
    protected void decodeDataLine(String encodedDataLine) {
        String[] decode = encodedDataLine.replaceAll("\n", "").split(";");
        this.songName = decode[0];
        this.poemName = decode[1];
        this.difficulty = Difficulty.fromString(decode[2]);
        this.score = Double.valueOf(decode[3]);
    }
}
