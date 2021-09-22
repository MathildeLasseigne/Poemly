package widgets;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.*;

/*----------This class is made with the JLayer library, an add-on from Swing------------*/

/**
 * Create a sound player which play a unique sound.
 * <br/>This sound can be paused, resumed, stopped, reinitialised and looped.
 * <br/>To use this class, the JLayer library is necessary:
 * <br/><a href="https://openjfx.io/">Download JLayer here</a>
 * <br/><a href="https://github.com/umjammer/jlayer">Find JLayer github project here</a>
 * @author Mathilde LASSEIGNE
 * @version 1.0
 */
public class SoundPlayer {

    /**Main stop flag of the class**/
    private boolean playing = true;

    private FileInputStream fileInputStream;
    private BufferedInputStream bufferedInputStream;
    private File myFile = null;

    private long totalLength;
    private long pause;

    /**Main component of sound**/
    private Player player;

    private PlayingAudio runningSound = null;

    /**Is paused by the pause method**/
    private boolean enPause = false;
    /**Is stopped by the stop method**/
    private boolean stopped = false;
    /**The sound is looping**/
    private boolean looping;

    /**
     * Create a sound player which play a unique sound.
     * <br/>This sound can be paused, resumed, stopped and reinitialised.
     * @param file the file containing the sound
     * @param looping is the sound looping ?
     */
    public SoundPlayer(File file, boolean looping){
        setId();
        init(file, looping);
    }

    /**
     * Create a sound player which play a unique sound.
     * <br/>This sound can be paused, resumed, stopped and reinitialised.
     * @param path the path from where the file can be extracted
     * @param looping is the sound looping ?
     */
    public SoundPlayer(String path, boolean looping){
        setId();
        File f = new File(path);
        init(f, looping);
    }

    /**
     * Create a sound player which play a unique sound.
     * <br/>This sound can be paused, resumed, stopped and reinitialised.
     * <br/> By default, the sound isn't looping.
     * @param file the file containing the sound
     */
    public SoundPlayer(File file){
        init(file, false);
    }

    /**
     * Create a sound player which play a unique sound.
     * <br/>This sound can be paused, resumed, stopped and reinitialised.
     * <br/> By default, the sound isn't looping.
     * @param path the path from where the file can be extracted
     */
    public SoundPlayer(String path){
        File f = new File(path);
        init(f, false);
    }

    /**
     * Init
     * @param file
     * @param looping
     */
    private void init(File file, boolean looping){
        this.looping = looping;
        this.myFile = file;
    }

    /**
     * Stop definitively the SoundPlayer
     */
    public void close() {
        if (player != null) player.close();
        if(runningSound != null) runningSound.stopRun();
        this.playing = false;
    }

    /**
     * Stop the sound currently playing.
     * <br/>Stopping after a pause make <i>resume()</i> ineffective
     */
    public void stop(){
        if(player!=null){
            player.close();
            stopped = true;
        }
    }

    /**
     * Pause the sound currently playing.
     * <br/>Can be resumed with <i>resume()</i>
     */
    public void pause(){
        if(playing){
            if(player!=null && !this.enPause && !this.stopped){
                try {
                    pause=fileInputStream.available();
                    player.close();
                    this.enPause = true;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * Check if the sound is paused
     * @return true if paused else false
     */
    public boolean isPaused() {
        return enPause;
    }

    /**
     * Check if the sound is playing, meaning it is neither stopped nor paused nor completed.
     * @return true if playing else false
     */
    public boolean isSoundPlaying(){
        return !enPause && !stopped && !isCompleted();
    }

    /**
     * Check if the sound is completed.
     * <br/> If looping, always false
     * @return true if completed else false
     */
    public boolean isCompleted(){
        if(player != null && !looping){
            return player.isComplete();
        }
        return false;
    }

    /**
     * Return the main component of the sound
     * @return the player from the JLayer library
     */
    public Player getPlayer(){
        return this.player;
    }

    /**
     * Play the sound from the beginning.
     */
    public void play(){
        if(playing){
            stopped = false;
            enPause = false;
            if(runningSound != null){
                runningSound.stopRun();
            }

            runningSound = new PlayingAudio(false, looping);

            runningSound.start();
        }

    }

    /**
     * Resume the sound currently playing where it was stopped.
     */
    public void resume(){
        if(this.playing){
            if(enPause && !stopped){

                if(runningSound != null){
                    runningSound.stopRun();
                }
                runningSound = new PlayingAudio(true, looping);

                runningSound.start();

                this.enPause = false;
            }
        }


    }



/*-----------------------------------------Class intern-----------------------------------------------------*/





    private class PlayingAudio extends Thread{

        private boolean restart;
        /**Flag to stop the thread**/
        private boolean run = true;

        private boolean looping;

        /**
         * Allow looping sounds and restarting from a point
         * @param isRestarting is the sound resuming
         * @param looping must the sound loop ?
         */
        public PlayingAudio(boolean isRestarting, boolean looping){
            restart = isRestarting;
            this.looping = looping;
        }

        /**
         * Stop the run. This operation is definitive
         */
        public void stopRun(){
            this.run = false;
            if (player != null){
                player.close();
            }
        }

        @Override
        public void run() {
            try {
                do {
                    if(player != null){
                        player.close();
                    }
                    fileInputStream=new FileInputStream(myFile);
                    bufferedInputStream = null;
                    bufferedInputStream=new BufferedInputStream(fileInputStream);
                    player=new Player(bufferedInputStream);
                    if(restart){
                        fileInputStream.skip(totalLength-pause);
                    } else {
                        totalLength=fileInputStream.available();
                    }
                    player.play();
                    if(restart)
                        restart = false;
                } while (this.looping && !enPause && !stopped && this.run);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) { System.out.println(e); }
        }
    }



    /*-------------------Identification----------------------------------*/

    /**The name of the SoundPlayer**/
    private String name = "";

    /**
     * Set the name of the SoundPlayer
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the name of the SoundPlayer
     * @return
     */
    public String getName() {
        return name;
    }

    /**The number of SoundPlayers created in this project*/
    private static int nbSoundPlayersCreated = 0;
    /**The personal id of this SoundPlayer*/
    private int id;

    /**Set the id of this SoundPlayer. It is unique*/
    private void setId(){
        this.id = nbSoundPlayersCreated;
        nbSoundPlayersCreated ++;
    }

    /**
     * Return the id of this SoundPlayer.
     * <br/> Its id consist of its name and its serial number :
     * <br/> ex : "myFirstSoundPlayer-1"
     * <br/> If the SoundPlayer doesn't have a name, its id will be its serial number
     * @return the id of the sound player
     */
    public String getID(){
        if(getName() != "" & getName() != null){
            return getName()+"-"+this.id;
        } else {
            return Integer.toString(this.id);
        }
    }

}
