package model;

import widgets.SoundPlayer;

public class Song extends Encodable{

    /**The name of the sound*/
    private String name;

    /**The path to load to find the .mp3 file of the song*/
    private String path;


    private SoundPlayer soundPlayer = null;


    private SoundPlayer loopingSoundPlayer = null;

    /**
     * A class containing all informations of a particular song and its SoundPlayer
     * @param name  the name of the sound
     * @param path  The path to load to find the .mp3 file of the song
     */
    public Song(String name, String path){
        super("#Song#");
        this.name = name;
        this.path = path;
    }

    /**
     * A class containing all informations of a particular song and its SoundPlayer
     * <br/>This constructor decode the data given to it to find its own name & path
     * @param encodedProjectDataLine
     */
    public Song(String encodedProjectDataLine){
        super("#Song#");
        decodeDataLine(encodedProjectDataLine);
    }

    /**
     * Return an empty song
     * @return
     */
    public static Song createEmptySong(){
        return new Song("","");
    }

    /**
     * Get the name of the song
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Return the SoundPlayer associated with the sound.
     * Only load the SoundPlayer if asked. If the SoundPlayer was closed, will create a new one
     * @return
     */
    public SoundPlayer getSoundPlayer() {
        if(this.path == null){
            return null;
        }
        if(this.soundPlayer == null || this.soundPlayer.isClosed()){
            this.soundPlayer = new SoundPlayer(this.path);
            this.soundPlayer.setName(this.name);
        }
        return this.soundPlayer;
    }



    /**
     * Return the SoundPlayer associated with the sound. The SoundPlayer is looped.
     * Only load the SoundPlayer if asked. If the SoundPlayer was closed, will create a new one
     * @return
     */
    public SoundPlayer getLoopingSoundPlayer() {
        if(this.path == null){
            return null;
        }
        if(this.loopingSoundPlayer == null || this.loopingSoundPlayer.isClosed()){
            this.loopingSoundPlayer = new SoundPlayer(this.path, true);
            this.loopingSoundPlayer.setName(this.name);
        }
        return this.loopingSoundPlayer;
    }

    /**
     * Close all sound players from the Song
     * @see SoundPlayer#close()
     */
    public void closeAllSoundPlayers(){
        if(this.soundPlayer != null & ! this.soundPlayer.isClosed()){
            this.soundPlayer.close();
        }
        if(this.loopingSoundPlayer != null & ! this.loopingSoundPlayer.isClosed()){
            this.loopingSoundPlayer.close();
        }
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
     * Return the string defining the song in the ProjectData file
     * <br/> Line format : <b>#Song#name;path</b>
     * @return
     */
    public String generateDataLine(){
        String data = header + this.name + ";" + this.path;
        return data;
    }

    /**
     * Decode the encoded data to fill the song data
     * @param encodedDataLine
     */
    protected void decodeDataLine(String encodedDataLine){
        String[] decode = encodedDataLine.replaceAll(header, "").replaceAll("\n", "").split(";");
        this.name = decode[0];
        this.path = decode[1];
    }
}

